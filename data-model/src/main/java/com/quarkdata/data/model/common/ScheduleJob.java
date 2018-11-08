package com.quarkdata.data.model.common;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时任务存储类
 * 
 * @author WangHao 2018年7月18日
 */
public class ScheduleJob implements Serializable {
	private static final long serialVersionUID = -5412947539722416648L;
	/** ID */
	private Long scheduleJobId;
	/** 任务名称 */
	private String jobName;
	/** 任务分组 */
	private String jobGroup;
	/** 定时任务对应的类（包括包路径） */
	private String clazz;
	/** 任务状态：1禁用 2启用 */
	private String jobStatus;
	/** 任务运行时间表达式 */
	private String cronExpression;
	/** 任务描述 */
	private String jobDesc;
	private Long createUser;
	private Date createTime;

	public Long getScheduleJobId() {
		return scheduleJobId;
	}

	public void setScheduleJobId(Long scheduleJobId) {
		this.scheduleJobId = scheduleJobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

}
