package com.quarkdata.data.model.vo;

import java.util.Date;

public class SourceProjectDetailVO {

	// 项目ID
	private Long projectId;
	// 项目名称
	private String projectName;
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
	// 项目标签
	private String projectTag;
	// 项目下数据集数量
	private Integer datasetNum;
	// 项目下工作流数量
	private Integer workflowNum;
	// 项目下任务数量
	private Integer taskNum;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
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

	public String getProjectTag() {
		return projectTag;
	}

	public void setProjectTag(String projectTag) {
		this.projectTag = projectTag;
	}

	public Integer getDatasetNum() {
		return datasetNum;
	}

	public void setDatasetNum(Integer datasetNum) {
		this.datasetNum = datasetNum;
	}

	public Integer getWorkflowNum() {
		return workflowNum;
	}

	public void setWorkflowNum(Integer workflowNum) {
		this.workflowNum = workflowNum;
	}

	public Integer getTaskNum() {
		return taskNum;
	}

	public void setTaskNum(Integer taskNum) {
		this.taskNum = taskNum;
	}
	
	
	
}
