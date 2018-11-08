package com.quarkdata.data.model.vo;

/**
 * workflow-数据集节点
 * @author typ
 * 	2018年5月18日
 */
public class DatasetNode {

	private Long datasetId;
	private String nodeName;
	
	public Long getDatasetId() {
		return datasetId;
	}
	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
}
