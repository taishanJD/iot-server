package com.quarkdata.data.model.vo;

import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.model.dataobj.WorkflowNodeRel;

/**
 * 执行节点任务
 * @author typ
 * 	2018年6月6日
 */
public class ExecNodeTaskVO {
	
	private Long workflowId; // workflow id
	
	private Long instId; // 流程实例id
	
	private Long nodeInstId; // 节点实例id
	
	private Long schedulerJobId; // job id
	
	private Long schedulerTaskId; // task id
	
	private String bizDateParam;
	
	private String sysTimeParam;
	
	private String inputTableName; // 输入表名
	
	private Datasource inputDatasource; // 输入数据源
	
	private WorkflowNodeRel outputNodeRel; // 输出节点 关系对象
	
	private String outputTableName; // 输出表名
	
	private Datasource outputDatasource; //输出数据源
	
	private String nodeProcessSubType; // 处理类型 preprocess/sync
	
	private String isAppend; // 追加/覆盖 
	
	public ExecNodeTaskVO(Long workflowId, Long instId, Long nodeInstId,
			Long schedulerJobId, Long schedulerTaskId, String bizDateParam,
			String sysTimeParam, String inputTableName,
			Datasource inputDatasource, WorkflowNodeRel outputNodeRel,
			String outputTableName, Datasource outputDatasource,
			String nodeProcessSubType,String isAppend) {
		super();
		this.workflowId = workflowId;
		this.instId = instId;
		this.nodeInstId = nodeInstId;
		this.schedulerJobId = schedulerJobId;
		this.schedulerTaskId = schedulerTaskId;
		this.bizDateParam = bizDateParam;
		this.sysTimeParam = sysTimeParam;
		this.inputTableName = inputTableName;
		this.inputDatasource = inputDatasource;
		this.outputNodeRel = outputNodeRel;
		this.outputTableName = outputTableName;
		this.outputDatasource = outputDatasource;
		this.nodeProcessSubType = nodeProcessSubType;
		this.isAppend = isAppend;
	}

	public String getIsAppend() {
		return isAppend;
	}


	public void setIsAppend(String isAppend) {
		this.isAppend = isAppend;
	}


	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public Long getInstId() {
		return instId;
	}

	public void setInstId(Long instId) {
		this.instId = instId;
	}

	public Long getNodeInstId() {
		return nodeInstId;
	}

	public void setNodeInstId(Long nodeInstId) {
		this.nodeInstId = nodeInstId;
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

	public String getInputTableName() {
		return inputTableName;
	}

	public void setInputTableName(String inputTableName) {
		this.inputTableName = inputTableName;
	}

	public Datasource getInputDatasource() {
		return inputDatasource;
	}

	public void setInputDatasource(Datasource inputDatasource) {
		this.inputDatasource = inputDatasource;
	}

	public WorkflowNodeRel getOutputNodeRel() {
		return outputNodeRel;
	}

	public void setOutputNodeRel(WorkflowNodeRel outputNodeRel) {
		this.outputNodeRel = outputNodeRel;
	}

	public String getOutputTableName() {
		return outputTableName;
	}

	public void setOutputTableName(String outputTableName) {
		this.outputTableName = outputTableName;
	}

	public Datasource getOutputDatasource() {
		return outputDatasource;
	}

	public void setOutputDatasource(Datasource outputDatasource) {
		this.outputDatasource = outputDatasource;
	}

	public String getNodeProcessSubType() {
		return nodeProcessSubType;
	}

	public void setNodeProcessSubType(String nodeProcessSubType) {
		this.nodeProcessSubType = nodeProcessSubType;
	}
}
