package com.quarkdata.data.model.vo;

import java.util.Date;

public class WorkflowDetail {

	private Long id;
	private String name;
	private String desc;
	private Date createTime;
	private Date updateTime;
	
	private String schedulerType;
	private int datasetNodeCount;
	private int processNodeCount;
	private String isDependParent;
	private Long schedulerJobId;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
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
	public String getSchedulerType() {
		return schedulerType;
	}
	public void setSchedulerType(String schedulerType) {
		this.schedulerType = schedulerType;
	}
	public int getDatasetNodeCount() {
		return datasetNodeCount;
	}
	public void setDatasetNodeCount(int datasetNodeCount) {
		this.datasetNodeCount = datasetNodeCount;
	}
	public int getProcessNodeCount() {
		return processNodeCount;
	}
	public void setProcessNodeCount(int processNodeCount) {
		this.processNodeCount = processNodeCount;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIsDependParent() {
		return isDependParent;
	}
	public void setIsDependParent(String isDependParent) {
		this.isDependParent = isDependParent;
	}
	public Long getSchedulerJobId() {
		return schedulerJobId;
	}
	public void setSchedulerJobId(Long schedulerJobId) {
		this.schedulerJobId = schedulerJobId;
	}
}
