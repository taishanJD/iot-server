package com.quarkdata.data.model.vo;

/**
 * 工作流-处理节点
 * 
 * @author typ 2018年5月18日
 */
public class ProcessNode {

	private Long nodeId;
	private String nodeProcessType;
	private String nodeProcessSubType;
	private String nodePreprocessJson; // 预处理步骤Json

	private String isAppend; // 追加还是覆盖，0-覆盖 1-追加

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
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

	public String getIsAppend() {
		return isAppend;
	}

	public void setIsAppend(String isAppend) {
		this.isAppend = isAppend;
	}

	public String getNodePreprocessJson() {
		return nodePreprocessJson;
	}

	public void setNodePreprocessJson(String nodePreprocessJson) {
		this.nodePreprocessJson = nodePreprocessJson;
	}
}
