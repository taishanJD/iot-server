package com.quarkdata.data.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quarkdata.data.dal.dao.DatasetMapper;
import com.quarkdata.data.dal.dao.DatasourceMapper;
import com.quarkdata.data.dal.dao.ProjectMapper;
import com.quarkdata.data.dal.dao.SchedulerJobMapper;
import com.quarkdata.data.dal.dao.SchedulerJobResponsibleMapper;
import com.quarkdata.data.dal.dao.SchedulerNotifyMapper;
import com.quarkdata.data.dal.dao.SchedulerTaskMapper;
import com.quarkdata.data.dal.dao.WorkflowInstMapper;
import com.quarkdata.data.dal.dao.WorkflowMapper;
import com.quarkdata.data.dal.dao.WorkflowNodeInstMapper;
import com.quarkdata.data.dal.dao.WorkflowNodeMapper;
import com.quarkdata.data.dal.dao.WorkflowNodeRelMapper;
import com.quarkdata.data.dal.rest.quarkshare.EmailApi;
import com.quarkdata.data.dal.rest.quarkshare.UserApi;
import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.model.dataobj.SchedulerJob;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsible;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsibleExample;
import com.quarkdata.data.model.dataobj.SchedulerNotify;
import com.quarkdata.data.model.dataobj.SchedulerNotifyExample;
import com.quarkdata.data.model.dataobj.SchedulerTask;
import com.quarkdata.data.model.dataobj.Workflow;
import com.quarkdata.data.model.dataobj.WorkflowExample;
import com.quarkdata.data.model.dataobj.WorkflowInst;
import com.quarkdata.data.model.dataobj.WorkflowNode;
import com.quarkdata.data.model.dataobj.WorkflowNodeInst;
import com.quarkdata.data.model.dataobj.WorkflowNodeInstExample;
import com.quarkdata.data.model.dataobj.WorkflowNodeRel;
import com.quarkdata.data.model.dataobj.WorkflowNodeRelExample;
import com.quarkdata.data.model.vo.ExecNodeTaskVO;
import com.quarkdata.data.service.WorkflowExecutorRunnable;
import com.quarkdata.data.service.WorkflowExecutorService;
import com.quarkdata.tenant.model.dataobj.User;

@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class WorkflowExecutorServiceImpl implements WorkflowExecutorService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    WorkflowMapper workflowMapper;
    @Autowired
    WorkflowInstMapper workflowInstMapper;
    @Autowired
    WorkflowNodeRelMapper workflowNodeRelMapper;
    @Autowired
    SchedulerTaskMapper schedulerTaskMapper;
    @Resource(name = "scheduleTaskExecutor")
    ThreadPoolTaskExecutor scheduleTaskExecutor;
    @Resource(name = "manualTaskExecutor")
    ThreadPoolTaskExecutor manualTaskExecutor;
    @Autowired
    WorkflowNodeMapper workflowNodeMapper;
    @Autowired
    DatasetMapper datasetMapper;
    @Autowired
    DatasourceMapper datasourceMapper;
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    WorkflowNodeInstMapper workflowNodeInstMapper;
    @Autowired
    SchedulerNotifyMapper schedulerNotifyMapper;
    @Autowired
    SchedulerJobResponsibleMapper schedulerJobResponsibleMapper;
    @Autowired
    UserApi userApi;
    @Autowired
    EmailApi emailApi;
    @Autowired
    SchedulerJobMapper schedulerJobMapper;

    @Override
    public ResultCode<String> execute(Long schedulerJobId,
                                      Long schedulerTaskId, String bizDateParam, String sysTimeParam) {
        logger.info(
                "start exec workflow,params --> schedulerJobId : {},schedulerTaskId : {},"
                        + "bizDateParam : {},sysTimeParam : {}",
                schedulerJobId, schedulerTaskId, bizDateParam, sysTimeParam);

        ResultCode<String> resultCode = new ResultCode<String>();
        try {
            execute_(schedulerJobId, schedulerTaskId, bizDateParam,
                    sysTimeParam);
        } catch (Exception e) {
            logger.error("execute occur exception", e);
            // 回调通知task
            callbackTask(schedulerTaskId, Constants.TASK_STATUS_FAIL);
            resultCode.setCode(-1);
        }
        return resultCode;
    }

    private void execute_(Long schedulerJobId, Long schedulerTaskId,
                          String bizDateParam, String sysTimeParam) {
        // 查询workflow
        WorkflowExample example = new WorkflowExample();

        example.createCriteria().andSchedulerJobIdEqualTo(schedulerJobId)
                .andIsDelEqualTo(Constants.IS_DEL_NO);

        Workflow workflow = workflowMapper.selectByExample(example).get(0);

        // 生成workflow实例

        WorkflowInst record = new WorkflowInst();

        record.setSchedulerTaskId(schedulerTaskId);
        record.setWorkflowId(workflow.getId());

        workflowInstMapper.insertSelective(record);

        // 流程实例id
        Long instId = record.getId();

        // 查询顶级节点

        WorkflowNodeRelExample relExample = new WorkflowNodeRelExample();

        relExample.createCriteria().andWorkflowIdEqualTo(workflow.getId())
                .andParentNodeIdIsNull();

        List<WorkflowNodeRel> nodeRels = workflowNodeRelMapper
                .selectByExample(relExample);

        for (WorkflowNodeRel nodeRel : nodeRels) {

            if (checkNextProcess(nodeRel)) {

                run4Schedule(nodeRel, workflow, instId, schedulerJobId, schedulerTaskId,
                        bizDateParam, sysTimeParam);
            }
        }

    }

    public void callbackTask(Long schedulerTaskId, String status) {
        SchedulerTask task = new SchedulerTask();
        task.setId(schedulerTaskId);
        task.setTaskStatus(status);
        if (status.equals(Constants.TASK_STATUS_RUNNING)) {
            task.setStartTime(new Date());
        } else if (status.equals(Constants.TASK_STATUS_SUCCESS) || status.equals(Constants.TASK_STATUS_FAIL)) {
            task.setEndTime(new Date());
        }

        schedulerTaskMapper.updateByPrimaryKeySelective(task);
    }

    /**
     * 提交给定时调度线程池
     */
    private void run4Schedule(WorkflowNodeRel nodeRel, Workflow workflow, Long instId,
                              Long schedulerJobId, Long schedulerTaskId, String bizDateParam,
                              String sysTimeParam) {
        // 插入未执行状态的nodeInst
        Long nodeId = getNextNodeId(nodeRel.getCurrentNodeId());
        insertNodeInst(workflow.getId(), instId, nodeId);

        scheduleTaskExecutor.execute(new WorkflowExecutorRunnable(nodeRel, workflow,
                instId, schedulerJobId, schedulerTaskId, bizDateParam,
                sysTimeParam));
    }

    /**
     * 提交给手动执行线程池
     */
    public void run4manual(WorkflowNodeRel nodeRel, Workflow workflow, Long instId,
                           Long schedulerJobId, Long schedulerTaskId, String bizDateParam,
                           String sysTimeParam) {
        // 插入未执行状态的nodeInst
        Long nodeId = getNextNodeId(nodeRel.getCurrentNodeId());
        insertNodeInst(workflow.getId(), instId, nodeId);

        manualTaskExecutor.execute(new WorkflowExecutorRunnable(nodeRel, workflow,
                instId, schedulerJobId, schedulerTaskId, bizDateParam,
                sysTimeParam));
    }


    public boolean checkNextProcess(WorkflowNodeRel nodeRel) {
        // 需要满足是 inputDataNode -> processNode -> outputDataNode 模型才可以执行
        WorkflowNodeRelExample example = new WorkflowNodeRelExample();
        example.createCriteria().andParentNodeIdEqualTo(
                nodeRel.getCurrentNodeId());

        List<WorkflowNodeRel> nodeRels = workflowNodeRelMapper
                .selectByExample(example);
        if (CollectionUtils.isEmpty(nodeRels)) {
            return false;
        }
        WorkflowNodeRel processNodeRel = nodeRels.get(0);

        example.clear();
        example.createCriteria().andParentNodeIdEqualTo(
                processNodeRel.getCurrentNodeId());
        nodeRels = workflowNodeRelMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(nodeRels)) {
            return false;
        }

        return true;
    }

    @Override
    public ExecNodeTaskVO preExecuteNextProcess(WorkflowNodeRel firstNode,
                                                Workflow workflow, Long instId, Long schedulerJobId,
                                                Long schedulerTaskId, String bizDateParam, String sysTimeParam) {
        // 获取当前项目
        /*
         * ProjectWithBLOBs project = projectMapper.selectByPrimaryKey(workflow
         * .getProjectId());
         */
        // 获取输入
        WorkflowNode inputDataNode = workflowNodeMapper
                .selectByPrimaryKey(firstNode.getCurrentNodeId());

        Dataset inputDataset = datasetMapper.selectByPrimaryKey(inputDataNode
                .getDatasetId());

        Datasource inputDatasource = datasourceMapper
                .selectByPrimaryKey(inputDataset.getDatasourceId());

        // 获取处理节点
        WorkflowNodeRelExample example = new WorkflowNodeRelExample();

        example.createCriteria().andParentNodeIdEqualTo(
                firstNode.getCurrentNodeId());

        WorkflowNodeRel processNodeRel = workflowNodeRelMapper.selectByExample(
                example).get(0);

        WorkflowNode processNode = workflowNodeMapper
                .selectByPrimaryKey(processNodeRel.getCurrentNodeId());

        // 获取输出

        example.clear();
        example.createCriteria().andParentNodeIdEqualTo(
                processNodeRel.getCurrentNodeId());
        WorkflowNodeRel outputNodeRel = workflowNodeRelMapper.selectByExample(
                example).get(0);

        WorkflowNode outputDataNode = workflowNodeMapper
                .selectByPrimaryKey(outputNodeRel.getCurrentNodeId());

        Dataset outputDataset = datasetMapper.selectByPrimaryKey(outputDataNode
                .getDatasetId());

        Datasource outputDatasource = datasourceMapper
                .selectByPrimaryKey(outputDataset.getDatasourceId());

        // 处理
        String nodeProcessSubType = processNode.getNodeProcessSubType();

        // 执行任务 预处理/同步

        Long nodeInstId = getNodeInstId(instId, processNode.getId());
        // 修改执行状态为running
        updateNodeInstToRunning(nodeInstId);

        ExecNodeTaskVO execNodeTaskVO = new ExecNodeTaskVO(workflow.getId(),
                instId, nodeInstId, schedulerJobId, schedulerTaskId,
                bizDateParam, sysTimeParam, inputDataset.getTableName(),
                inputDatasource, outputNodeRel, outputDataset.getTableName(),
                outputDatasource, nodeProcessSubType, processNode.getIsAppend());

        return execNodeTaskVO;

        /*
         * _execNodeTask(workflow.getId(), instId, schedulerTaskId,
         * bizDateParam, sysTimeParam, inputDataset.getTableName(),
         * inputDatasource, outputNodeRel, outputDataset.getTableName(),
         * outputDatasource, nodeProcessSubType, nodeInstId);
         */
    }

	/*@Override
	public void execNodeTask(Long workflowId, Long instId,
			Long schedulerTaskId, String bizDateParam, String sysTimeParam,
			String inputTableName, Datasource inputDatasource,
			WorkflowNodeRel outputNodeRel, String outputTableName,
			Datasource outputDatasource, String nodeProcessSubType,
			Long nodeInstId) {
		try {
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
					DataSyncUtils.syncDataFromMysqlToMysql(inputHost, inputPort, inputDb,
							inputUsername, inputPassword, inputTableName,
							outputHost, outputPort, outputDb, outputUsername,
							outputPassword, outputTableName, bizDateParam,
							sysTimeParam);
				} else if (inputDatasource.getDataType().equals(
						Constants.DATASOURCE_DATA_TYPE_MYSQL)
						&& outputDatasource.getDataType().equals(
								Constants.DATASOURCE_DATA_TYPE_CASSANDRA)) {
					// mysql to cassandra
					DataSyncUtils.syncDataFromMysqlToCassandra(inputHost, inputPort,
							inputDb, inputUsername, inputPassword,
							inputTableName, outputHost, outputPort, outputDb,
							outputUsername, outputPassword, outputTableName,
							bizDateParam, sysTimeParam);
				} else if (inputDatasource.getDataType().equals(
						Constants.DATASOURCE_DATA_TYPE_CASSANDRA)
						&& outputDatasource.getDataType().equals(
								Constants.DATASOURCE_DATA_TYPE_MYSQL)) {
					// cassandra to mysql
					DataSyncUtils.syncDataFromCassandraToMysql(inputHost, inputPort,
							inputDb, inputUsername, inputPassword,
							inputTableName, outputHost, outputPort, outputDb,
							outputUsername, outputPassword, outputTableName,
							bizDateParam, sysTimeParam);
				} else if (inputDatasource.getDataType().equals(
						Constants.DATASOURCE_DATA_TYPE_CASSANDRA)
						&& outputDatasource.getDataType().equals(
								Constants.DATASOURCE_DATA_TYPE_CASSANDRA)) {
					// cassandra to cassandra
					DataSyncUtils.syncDataFromCassandraToCassandra(inputHost,
							inputPort, inputDb, inputUsername, inputPassword,
							inputTableName, outputHost, outputPort, outputDb,
							outputUsername, outputPassword, outputTableName,
							bizDateParam, sysTimeParam);
				}

			}
		} catch (Exception e) {
			// 节点执行失败
			logger.error("execute node task fail", e);
			// 修改节点实例状态
			updateNodeInstStatus(nodeInstId,
					Constants.WORKFLOW_NODE_INST_STATUS_FAIL);
			// 修改task状态
			callbackTask(schedulerTaskId, Constants.TASK_STATUS_FAIL);
		}
		// 执行成功 修改节点状态 ，判断有无下一节点继续执行，没有修改实例状态
		updateNodeInstStatus(nodeInstId,
				Constants.WORKFLOW_NODE_INST_STATUS_SUCCESS);
		//
		if (checkNextProcess(outputNodeRel)) {
			// 继续执行下一个节点任务
			Long nodeId = getNextNodeId(outputNodeRel.getCurrentNodeId());
			insertNodeInst(workflowId, instId, nodeId);
			// run next process
			// executeNextProcess(firstNode, workflow, nodeInstId,
			// schedulerJobId, schedulerTaskId, bizDateParam, sysTimeParam);
		} else {
			// 检查所有节点是否执行成功

		}
	}*/

    private Long getNodeInstId(Long workflowInstId, Long workflowNodeId) {
        WorkflowNodeInstExample example = new WorkflowNodeInstExample();

        example.createCriteria().andWorkflowInstIdEqualTo(workflowInstId)
                .andWorkflowNodeIdEqualTo(workflowNodeId);

        return workflowNodeInstMapper.selectByExample(example).get(0).getId();
    }

       private void insertNodeInst(Long workflowId, Long workflowInstId,
                                Long workflowNodeId) {
        WorkflowNodeInst record = new WorkflowNodeInst();

        record.setWorkflowId(workflowId);
        record.setWorkflowInstId(workflowInstId);
        record.setWorkflowNodeId(workflowNodeId);
        record.setCreateTime(new Date());
        record.setNodeStatus(Constants.WORKFLOW_NODE_INST_STATUS_WAIT);

        workflowNodeInstMapper.insertSelective(record);
    }

    private void updateNodeInstToRunning(Long nodeInstId) {
        WorkflowNodeInst record = new WorkflowNodeInst();

        record.setId(nodeInstId);
        record.setStartTime(new Date());
        record.setNodeStatus(Constants.WORKFLOW_NODE_INST_STATUS_RUNNING);

        workflowNodeInstMapper.updateByPrimaryKeySelective(record);
    }

    /*
     * private void updateNodeInstToRunning(Long workflowInstId, Long
     * workflowNodeId){ WorkflowNodeInstExample example = new
     * WorkflowNodeInstExample();
     *
     * example.createCriteria().andWorkflowInstIdEqualTo(workflowInstId)
     * .andWorkflowNodeIdEqualTo(workflowNodeId);
     *
     * WorkflowNodeInst record = new WorkflowNodeInst();
     *
     * record.setStartTime(new Date());
     * record.setNodeStatus(Constants.WORKFLOW_NODE_INST_STATUS_RUNNING);
     *
     * workflowNodeInstMapper.updateByExampleSelective(record, example); }
     */

    public Long getNextNodeId(Long nodeId) {
        WorkflowNodeRelExample example = new WorkflowNodeRelExample();
        example.createCriteria().andParentNodeIdEqualTo(nodeId);

        WorkflowNodeRel nodeRel = workflowNodeRelMapper
                .selectByExample(example).get(0);

        return nodeRel.getCurrentNodeId();
    }

    private void updateNodeInstStatus(Long nodeInstId, String status) {
        WorkflowNodeInst updateNodeInst = new WorkflowNodeInst();

        updateNodeInst.setId(nodeInstId);
        updateNodeInst.setNodeStatus(status);
        if (status.equals(Constants.WORKFLOW_NODE_INST_STATUS_SUCCESS) || status.equals(Constants.WORKFLOW_NODE_INST_STATUS_FAIL)) {
            updateNodeInst.setEndTime(new Date());
        }

        workflowNodeInstMapper.updateByPrimaryKeySelective(updateNodeInst);
    }

    @Override
    public boolean handlerNodeTaskFail(Long nodeInstId, Long schedulerTaskId, Long schedulerJobId, Long workflowId) {
        // 修改节点实例状态
        SchedulerTask task = schedulerTaskMapper.selectByPrimaryKey(schedulerTaskId);
        if(task.getTaskStatus().equals(Constants.TASK_STATUS_TERMINATING)){
            task.setTaskStatus(Constants.TASK_STATUS_STOP);
            schedulerTaskMapper.updateByPrimaryKey(task);
        }else{
            // 修改task状态
            callbackTask(schedulerTaskId, Constants.TASK_STATUS_FAIL);
        }
        updateNodeInstStatus(nodeInstId,
                Constants.WORKFLOW_NODE_INST_STATUS_FAIL);     // TODO 执行失败，如果
        // notify
        notify(schedulerJobId, schedulerTaskId, "0");
        // 查询workflow.is_depend_parent
        Workflow workflow = workflowMapper.selectByPrimaryKey(workflowId);
        // 是否依赖父节点执行状态（0：否、1：是）默认1
        if (workflow.getIsDependParent().equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    public void handlerNodeTaskSucTransNextTask(Long workflowId, Long instId,
                                                Long nodeInstId, WorkflowNodeRel outputNodeRel) {
        updateNodeInstStatus(nodeInstId,
                Constants.WORKFLOW_NODE_INST_STATUS_SUCCESS);
        Long nodeId = getNextNodeId(outputNodeRel.getCurrentNodeId());
        insertNodeInst(workflowId, instId, nodeId);
    }

    // 检查所有任务节点是否执行成功
    public void checkAllNodeTaskIsSuccess(Long instId, Long schedulerTaskId, Long schedulerJobId) {
        WorkflowNodeInstExample example = new WorkflowNodeInstExample();

        example.createCriteria().andWorkflowInstIdEqualTo(instId);

        List<WorkflowNodeInst> list = workflowNodeInstMapper
                .selectByExample(example);

        boolean allNodeTaskIsSuccess = true;
        for (WorkflowNodeInst nodeInst : list) {
            if (!nodeInst.getNodeStatus().equals(
                    Constants.WORKFLOW_NODE_INST_STATUS_SUCCESS)) {
                allNodeTaskIsSuccess = false;
                break;
            }
        }

        if (allNodeTaskIsSuccess) {
            callbackTask(schedulerTaskId, Constants.TASK_STATUS_SUCCESS);
            // notify
            notify(schedulerJobId, schedulerTaskId, "1");
        }
    }

    @Override
    public void handlerNodeTaskSucComplete(Long instId, Long nodeInstId,
                                           Long schedulerTaskId, Long schedulerJobId) {
        updateNodeInstStatus(nodeInstId, Constants.WORKFLOW_NODE_INST_STATUS_SUCCESS);
        checkAllNodeTaskIsSuccess(nodeInstId, schedulerTaskId, schedulerJobId);
    }

    @Override
    public void notify(Long jobId, Long taskId, String reasonType) {
        SchedulerNotifyExample example = new SchedulerNotifyExample();
        example.createCriteria().andSchedulerJobIdEqualTo(jobId);

        List<SchedulerNotify> notifys = schedulerNotifyMapper.selectByExample(example);

        // 以逗号隔开
        String userIds = null;
        if (CollectionUtils.isNotEmpty(notifys)) {
            // 是不是责任人
            if (notifys.get(0).getReceiveUserId().equals(0L)) {
                // 责任人
                SchedulerJobResponsibleExample jobResExample = new SchedulerJobResponsibleExample();

                jobResExample.createCriteria().andSchedulerJobIdEqualTo(jobId);
                List<SchedulerJobResponsible> jobRes = schedulerJobResponsibleMapper.selectByExample(jobResExample);
                userIds = getUserIds(jobRes);
            } else {
                userIds = getUserIds4Notify(notifys);
            }
            if (StringUtils.isNotBlank(userIds)) {
                String noticeReasonType = notifys.get(0).getNoticeReasonType();
                if (noticeReasonType.equals("2") || noticeReasonType.equals(reasonType)) {
                    // 发送通知
                    if (notifys.get(0).getNoticeType().equals("2") || notifys.get(0).getNoticeType().equals("0")) {
                        // 发送邮件
                        // 获取邮箱列表
                        List<User> users = userApi.getUsersByIds(userIds).getData();
                        String[] emails = getEmails(users);
                        if (emails != null) {
                            // 邮件内容格式：作业名称-作业id-taskid-运行失败
                            SchedulerJob job = schedulerJobMapper.selectByPrimaryKey(jobId);
                            String displayRunningStatus = "";
                            if (reasonType.equals("0")) {
                                displayRunningStatus = "出错";
                            } else if (reasonType.equals("1")) {
                                displayRunningStatus = "完成";
                            }
                            String emailSubject = "作业名称：" + job.getJobName() + "-作业id：" + jobId + "-任务id：" + taskId + "-运行" + displayRunningStatus;
                            // 发送邮件
                            emailApi.sendEmail(emails, emailSubject, emailSubject, "text/plain;charset=utf-8");
                        }
                    }
                }
            }
        }
    }

    private String[] getEmails(List<User> users) {
        if (CollectionUtils.isNotEmpty(users)) {
            String[] emails = new String[users.size()];
            for (int i = 0; i < users.size(); i++) {
                emails[i] = users.get(i).getEmail();
            }
            return emails;
        }
        return null;
    }

    private String getUserIds4Notify(List<SchedulerNotify> notifys) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(notifys)) {
            for (SchedulerNotify n : notifys) {
                sb.append(n.getReceiveUserId()).append(",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private String getUserIds(List<SchedulerJobResponsible> jobRes) {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(jobRes)) {
            for (SchedulerJobResponsible r : jobRes) {
                sb.append(r.getUserId()).append(",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
    @Override
    public void reRun4manual(WorkflowNodeRel nodeRel, Workflow workflow,
                             Long instId, Long schedulerJobId, Long schedulerTaskId,
                             String bizDateParam, String sysTimeParam) {
        // 更新node_inst的运行状态
        Long nodeId = getNextNodeId(nodeRel.getCurrentNodeId());
        // insertNodeInst(workflow.getId(), instId, nodeId);
        WorkflowNodeInstExample example = new WorkflowNodeInstExample();
        example.createCriteria().andWorkflowInstIdEqualTo(instId)
                .andWorkflowIdEqualTo(workflow.getId())
                .andWorkflowNodeIdEqualTo(nodeId);
        List<WorkflowNodeInst> workflowNodeInsts = workflowNodeInstMapper.selectByExample(example);
        workflowNodeInsts.get(0).setNodeStatus(Constants.WORKFLOW_NODE_INST_STATUS_WAIT);
        workflowNodeInstMapper.updateByPrimaryKey(workflowNodeInsts.get(0));

        manualTaskExecutor.execute(new WorkflowExecutorRunnable(nodeRel, workflow,
                instId, schedulerJobId, schedulerTaskId, bizDateParam,
                sysTimeParam));
    }

}
