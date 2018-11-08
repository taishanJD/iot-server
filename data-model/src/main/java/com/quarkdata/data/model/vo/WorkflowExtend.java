package com.quarkdata.data.model.vo;

import com.quarkdata.data.model.dataobj.Workflow;

public class WorkflowExtend extends Workflow{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String jobType;//作业类型（0：周期性、1：手动）

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
}
