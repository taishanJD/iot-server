package com.quarkdata.data.model.vo;

import java.io.Serializable;
import java.util.Date;

public class SourceWorkflowDetailVO implements Serializable{

	
	private static final long serialVersionUID = -3751713286578554534L;
	
	// 工作流ID
	private Long workflowId;
	// 工作流名称
	private String workflowName;
	// 创建时间
	private Date createTime;
	// 创建人ID
	private Long createUserId;
	// 创建人名称
	private String createUserName;
	// 修改时间
	private Date updateTime;
	// 修改人ID
	private Long updateUserId;
	// 修改人名称
	private String updateUserName;
	// 调度类型
	private String jobType;
	// 工作流包含数据集数量
	private Integer datasetNum;

	public Long getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(Long workflowId) {
		this.workflowId = workflowId;
	}

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
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

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Integer getDatasetNum() {
		return datasetNum;
	}

	public void setDatasetNum(Integer datasetNum) {
		this.datasetNum = datasetNum;
	}
	
	
	
}
