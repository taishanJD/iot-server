package com.quarkdata.data.model.vo;

import java.io.Serializable;

/**
 * 资源管理 工作流列表
 * @author WangHao
 * 2018年5月17日
 */
public class SourceWorkflowListVO implements Serializable{

	private static final long serialVersionUID = -1926755053310178982L;
	
	// 工作流ID
	private Long workflowId;
	// 工作流名称
	private String workflowName;
	// 项目ID
	private Long projectId;
	// 项目名称
	private String projectName;
	// 作业类型
	private String jobType;
	
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
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	
}
