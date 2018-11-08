package com.quarkdata.data.model.vo;

/**
 * workflow-添加节点关系
 * @author typ
 * 	2018年5月18日
 */
public class AddNodeRelVO {

	private Long workflowId;
	
	private Long projectId;
	
	private DatasetNode inputNode;
	
	private DatasetNode outputNode;
	
	private ProcessNode processNode;
	
	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public DatasetNode getInputNode() {
		return inputNode;
	}

	public void setInputNode(DatasetNode inputNode) {
		this.inputNode = inputNode;
	}

	public DatasetNode getOutputNode() {
		return outputNode;
	}

	public void setOutputNode(DatasetNode outputNode) {
		this.outputNode = outputNode;
	}

	public ProcessNode getProcessNode() {
		return processNode;
	}

	public void setProcessNode(ProcessNode processNode) {
		this.processNode = processNode;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	
}
