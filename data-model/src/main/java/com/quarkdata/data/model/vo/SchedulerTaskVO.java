package com.quarkdata.data.model.vo;

import java.util.Date;
import java.util.List;

import com.quarkdata.data.model.dataobj.SchedulerJobResponsible;

/**
 * 任务列表
 * @author typ
 * 	2018年5月10日
 */
public class SchedulerTaskVO {

	private Long id;
	private String taskName;
	
	private String taskStatus; // 任务状态
	
	private Date scheduleDatetime;//定时时间
	
	private Date startTime;//开始执行时间
	
	private Date endTime;//结束执行时间
	
	private String bizDate;//业务时间
	
	private Integer retryTimes;//重试次数
	
	// 责任人
	private List<SchedulerJobResponsible> users;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getScheduleDatetime() {
		return scheduleDatetime;
	}

	public void setScheduleDatetime(Date scheduleDatetime) {
		this.scheduleDatetime = scheduleDatetime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBizDate() {
		return bizDate;
	}

	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}

	public Integer getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(Integer retryTimes) {
		this.retryTimes = retryTimes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<SchedulerJobResponsible> getUsers() {
		return users;
	}

	public void setUsers(List<SchedulerJobResponsible> users) {
		this.users = users;
	}
	
}
