package com.quarkdata.data.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SourceDataSetDetailVO implements Serializable{

	private static final long serialVersionUID = 2742598494888420115L;
	
	// 数据集ID
	private Long datasetId;
	// 创建时间
	private Date createTime;
	// 创建人ID
	private Long createUserId;
	// 创建人名称
	private String createUserName;
	// 更新时间
	private Date updateTime;
	// 更新人ID
	private Long updateUserId;
	// 更新人名称
	private String updateUserName;
	// 项目ID
	private Integer projectId;
	// 项目名称
	private String projectName;
	// 数据集字段
	List<Map<String,String>> columnList;
	// 上级数据集
	List<Map<String,Object>> parentDatesetList;
	// 下级数据集
	List<Map<String,Object>> childDatesetList;
	public Long getDatasetId() {
		return datasetId;
	}
	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public List<Map<String, String>> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<Map<String, String>> columnList) {
		this.columnList = columnList;
	}
	public List<Map<String, Object>> getParentDatesetList() {
		return parentDatesetList;
	}
	public void setParentDatesetList(List<Map<String, Object>> parentDatesetList) {
		this.parentDatesetList = parentDatesetList;
	}
	public List<Map<String, Object>> getChildDatesetList() {
		return childDatesetList;
	}
	public void setChildDatesetList(List<Map<String, Object>> childDatesetList) {
		this.childDatesetList = childDatesetList;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
