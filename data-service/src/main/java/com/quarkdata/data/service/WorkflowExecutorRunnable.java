package com.quarkdata.data.service;

import com.quarkdata.data.dal.dao.SchedulerTaskMapper;
import com.quarkdata.data.dal.dao.WorkflowNodeInstMapper;
import com.quarkdata.data.model.dataobj.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.vo.ExecNodeTaskVO;
import com.quarkdata.data.util.SpringContextHolder;
import com.quarkdata.data.util.db.DataSyncUtils;

import java.util.Date;

public class WorkflowExecutorRunnable implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	WorkflowExecutorService executorService = SpringContextHolder
			.getApplicationContext().getBean(WorkflowExecutorService.class);

	SchedulerTaskMapper schedulerTaskMapper = SpringContextHolder
			.getApplicationContext().getBean(SchedulerTaskMapper.class);

	WorkflowNodeInstMapper workflowNodeInstMapper=SpringContextHolder
			.getApplicationContext().getBean(WorkflowNodeInstMapper .class);
	private WorkflowNodeRel firstNode;
	private Workflow workflow;
	private Long instId;
	private Long schedulerJobId;
	private Long schedulerTaskId;
	private String bizDateParam;
	private String sysTimeParam;

	public WorkflowExecutorRunnable(WorkflowNodeRel firstNode,
			Workflow workflow, Long instId, Long schedulerJobId,
			Long schedulerTaskId, String bizDateParam, String sysTimeParam) {
		super();
		this.firstNode = firstNode;
		this.workflow = workflow;
		this.instId = instId;
		this.schedulerJobId = schedulerJobId;
		this.schedulerTaskId = schedulerTaskId;
		this.bizDateParam = bizDateParam;
		this.sysTimeParam = sysTimeParam;
	}

	@Override
	public void run() {
		logger.info("start run node task...");
		executorService.callbackTask(this.schedulerTaskId,
				Constants.TASK_STATUS_RUNNING);
		WorkflowNodeRel currentStartNode = firstNode;

		while (true) {
			ExecNodeTaskVO prepare = executorService.preExecuteNextProcess(
					currentStartNode, this.workflow, this.instId,
					this.schedulerJobId, this.schedulerTaskId,
					this.bizDateParam, this.sysTimeParam);
			WorkflowNodeRel outputNodeRel = prepare.getOutputNodeRel();
			Long nodeInstId = prepare.getNodeInstId();

			// 找到查询到当前的task，判断状态如果是停止状态，结束当前循环
			SchedulerTask task = schedulerTaskMapper.selectByPrimaryKey(this.schedulerTaskId);
			if(task.getTaskStatus()==Constants.TASK_STATUS_TERMINATING){
				break;
			}
			try {
				_run(currentStartNode, prepare);
			} catch (Exception e) {
				// 节点执行失败
				logger.error("execute node task fail", e);
				boolean isContinue = executorService
						.handlerNodeTaskFail(nodeInstId, this.schedulerTaskId,this.schedulerJobId,this.workflow.getId());
				if(!isContinue){
					break;
				}
			}

			logger.info("exec node task success!");
			// node task exec success
			if (!executorService.checkNextProcess(outputNodeRel)) {
				// 判断所有节点是否执行成功
				executorService.handlerNodeTaskSucComplete(this.instId,
						nodeInstId, this.schedulerTaskId,this.schedulerJobId);
				break;
			}
			executorService.handlerNodeTaskSucTransNextTask(
					this.workflow.getId(), this.instId, nodeInstId,
					outputNodeRel);
			currentStartNode = outputNodeRel;
		}

		//查询到当前的task，判断状态如果是终止中的状态，改变为终止，并结束线程
		SchedulerTask task = schedulerTaskMapper.selectByPrimaryKey(this.schedulerTaskId);
		if(task.getTaskStatus()==Constants.TASK_STATUS_TERMINATING){
			task.setTaskStatus(Constants.TASK_STATUS_STOP);
			schedulerTaskMapper.updateByPrimaryKey(task);
			return;
		}
	}

	public void insertNodeInst(Long workflowId, Long workflowInstId,
							   Long workflowNodeId) {
		WorkflowNodeInst record = new WorkflowNodeInst();
		record.setWorkflowId(workflowId);
		record.setWorkflowInstId(workflowInstId);
		record.setWorkflowNodeId(workflowNodeId);
		record.setCreateTime(new Date());
		record.setNodeStatus(Constants.WORKFLOW_NODE_INST_STATUS_WAIT);
		workflowNodeInstMapper.insertSelective(record);
	}

	private void _run(WorkflowNodeRel firstNode, ExecNodeTaskVO prepare) {
		// 执行节点任务
		logger.info(
				"run node task,params --> firstNode : {},workflow : {},instId : {},"
						+ "schedulerJobId : {},schedulerTaskId : {},bizDateParam : {},sysTimeParam : {}",
				JSON.toJSONString(firstNode), JSON.toJSONString(this.workflow),
				this.instId, this.schedulerJobId, this.schedulerTaskId,
				this.bizDateParam, this.sysTimeParam);

		String nodeProcessSubType = prepare.getNodeProcessSubType();

		Datasource inputDatasource = prepare.getInputDatasource();

		Datasource outputDatasource = prepare.getOutputDatasource();

		String inputTableName = prepare.getInputTableName();

		String outputTableName = prepare.getOutputTableName();


		if (Constants.NODE_PROCESS_SUB_TYPE_PREPROCESS
				.equals(nodeProcessSubType)) {
			// 预处理
		} else if (Constants.NODE_PROCESS_SUB_TYPE_SYNC
				.equals(nodeProcessSubType)) {
			// 同步处理
			String inputHost = inputDatasource.getHost();
			Integer inputPort = inputDatasource.getPort();
			String inputDb = inputDatasource.getDb();
			String inputUsername = inputDatasource.getUsername();
			String inputPassword = inputDatasource.getPassword();

			// 获取输出
			String outputHost = outputDatasource.getHost();
			Integer outputPort = outputDatasource.getPort();
			String outputDb = outputDatasource.getDb();
			String outputUsername = outputDatasource.getUsername();
			String outputPassword = outputDatasource.getPassword();

			if (inputDatasource.getDataType().equals(
					Constants.DATASOURCE_DATA_TYPE_MYSQL)
					&& outputDatasource.getDataType().equals(
							Constants.DATASOURCE_DATA_TYPE_MYSQL)) {
				// mysql to mysql
				DataSyncUtils.syncDataFromMysqlToMysql(inputHost, inputPort,
						inputDb, inputUsername, inputPassword, inputTableName,
						outputHost, outputPort, outputDb, outputUsername,
						outputPassword, outputTableName, bizDateParam,
						sysTimeParam, prepare.getIsAppend());
			} else if (inputDatasource.getDataType().equals(
					Constants.DATASOURCE_DATA_TYPE_MYSQL)
					&& outputDatasource.getDataType().equals(
							Constants.DATASOURCE_DATA_TYPE_CASSANDRA)) {
				// mysql to cassandra
				DataSyncUtils.syncDataFromMysqlToCassandra(inputHost,
						inputPort, inputDb, inputUsername, inputPassword,
						inputTableName, outputHost, outputPort, outputDb,
						outputUsername, outputPassword, outputTableName,
						bizDateParam, sysTimeParam, prepare.getIsAppend());
			} else if (inputDatasource.getDataType().equals(
					Constants.DATASOURCE_DATA_TYPE_CASSANDRA)
					&& outputDatasource.getDataType().equals(
							Constants.DATASOURCE_DATA_TYPE_MYSQL)) {
				// cassandra to mysql
				DataSyncUtils.syncDataFromCassandraToMysql(inputHost,
						inputPort, inputDb, inputUsername, inputPassword,
						inputTableName, outputHost, outputPort, outputDb,
						outputUsername, outputPassword, outputTableName,
						bizDateParam, sysTimeParam, prepare.getIsAppend());
			} else if (inputDatasource.getDataType().equals(
					Constants.DATASOURCE_DATA_TYPE_CASSANDRA)
					&& outputDatasource.getDataType().equals(
							Constants.DATASOURCE_DATA_TYPE_CASSANDRA)) {
				// cassandra to cassandra
				DataSyncUtils.syncDataFromCassandraToCassandra(inputHost,
						inputPort, inputDb, inputUsername, inputPassword,
						inputTableName, outputHost, outputPort, outputDb,
						outputUsername, outputPassword, outputTableName,
						bizDateParam, sysTimeParam, prepare.getIsAppend());
			}

		}
	}

	public WorkflowNodeRel getFirstNode() {
		return firstNode;
	}

	public void setFirstNode(WorkflowNodeRel firstNode) {
		this.firstNode = firstNode;
	}

	public Long getSchedulerJobId() {
		return schedulerJobId;
	}

	public void setSchedulerJobId(Long schedulerJobId) {
		this.schedulerJobId = schedulerJobId;
	}

	public Long getSchedulerTaskId() {
		return schedulerTaskId;
	}

	public void setSchedulerTaskId(Long schedulerTaskId) {
		this.schedulerTaskId = schedulerTaskId;
	}

	public String getBizDateParam() {
		return bizDateParam;
	}

	public void setBizDateParam(String bizDateParam) {
		this.bizDateParam = bizDateParam;
	}

	public String getSysTimeParam() {
		return sysTimeParam;
	}

	public void setSysTimeParam(String sysTimeParam) {
		this.sysTimeParam = sysTimeParam;
	}

	public Workflow getWorkflow() {
		return workflow;
	}

	public void setWorkflow(Workflow workflow) {
		this.workflow = workflow;
	}

	public Long getInstId() {
		return instId;
	}

	public void setInstId(Long instId) {
		this.instId = instId;
	}
}
