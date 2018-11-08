package com.quarkdata.data.model.vo;

import java.util.Date;

/**
 * 
 * @author typ
 * 	2018年5月3日
 */
public class JobVO {
	private Long id;
	private String workflowName;
	private Date updateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWorkflowName() {
		return workflowName;
	}
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
