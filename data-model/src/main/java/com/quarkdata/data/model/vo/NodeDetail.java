package com.quarkdata.data.model.vo;

import java.util.Date;
import java.util.List;

/**
 * 节点详情
 * @author typ
 * 	2018年5月22日
 */
public class NodeDetail {

	private String nodeName; // 节点名称
	private String nodeProcessSubType;
	private String displayProcessType; // 展示的处理类型
	private Date createTime;
	private Date updateTime;
	private String isAppend;
	private String preprocessJson;
	private List<DatasetNodeDTO> inputDatasetNode; // 输入
	private List<DatasetNodeDTO> outputDatasetNode; // 输出
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getDisplayProcessType() {
		return displayProcessType;
	}
	public void setDisplayProcessType(String displayProcessType) {
		this.displayProcessType = displayProcessType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public List<DatasetNodeDTO> getInputDatasetNode() {
		return inputDatasetNode;
	}
	public void setInputDatasetNode(List<DatasetNodeDTO> inputDatasetNode) {
		this.inputDatasetNode = inputDatasetNode;
	}
	public List<DatasetNodeDTO> getOutputDatasetNode() {
		return outputDatasetNode;
	}
	public void setOutputDatasetNode(List<DatasetNodeDTO> outputDatasetNode) {
		this.outputDatasetNode = outputDatasetNode;
	}
	public String getIsAppend() {
		return isAppend;
	}
	public void setIsAppend(String isAppend) {
		this.isAppend = isAppend;
	}

	public String getNodeProcessSubType() {
		return nodeProcessSubType;
	}

	public void setNodeProcessSubType(String nodeProcessSubType) {
		this.nodeProcessSubType = nodeProcessSubType;
	}
	public String getPreprocessJson() {
		return preprocessJson;
	}
	public void setPreprocessJson(String preprocessJson) {
		this.preprocessJson = preprocessJson;
	}
	
}
