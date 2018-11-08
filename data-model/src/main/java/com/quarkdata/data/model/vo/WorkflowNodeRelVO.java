package com.quarkdata.data.model.vo;

/**
 * 工作流节点关系
 * @author typ
 * 	2018年5月18日
 */
public class WorkflowNodeRelVO {

	private Long currentNodeId;
	
	private Long parentNodeId;
	
	private String nodeName;
	
	private String nodeType;
	
	private String nodeProcessType;
	
	private String nodeProcessSubType;
	
	// dataset
	private Long datasetId;
	private String datasetType;

	public Long getCurrentNodeId() {
		return currentNodeId;
	}

	public void setCurrentNodeId(Long currentNodeId) {
		this.currentNodeId = currentNodeId;
	}

	public Long getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(Long parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeProcessType() {
		return nodeProcessType;
	}

	public void setNodeProcessType(String nodeProcessType) {
		this.nodeProcessType = nodeProcessType;
	}

	public String getNodeProcessSubType() {
		return nodeProcessSubType;
	}

	public void setNodeProcessSubType(String nodeProcessSubType) {
		this.nodeProcessSubType = nodeProcessSubType;
	}

	public Long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public String getDatasetType() {
		return datasetType;
	}

	public void setDatasetType(String datasetType) {
		this.datasetType = datasetType;
	}
}
