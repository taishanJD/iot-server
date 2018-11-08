package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Date;

public class SchedulerTask implements Serializable {
    private Long id;

    private Long schedulerJobId;

    private Long projectId;

    private String taskStatus;

    private Date startTime;

    private Date endTime;

    private Date alarmTime;

    private Integer retryTimes;

    private String isManual;

    private String bizDateParam;

    private String sysTimeParam;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchedulerJobId() {
        return schedulerJobId;
    }

    public void setSchedulerJobId(Long schedulerJobId) {
        this.schedulerJobId = schedulerJobId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus == null ? null : taskStatus.trim();
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

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public String getIsManual() {
        return isManual;
    }

    public void setIsManual(String isManual) {
        this.isManual = isManual == null ? null : isManual.trim();
    }

    public String getBizDateParam() {
        return bizDateParam;
    }

    public void setBizDateParam(String bizDateParam) {
        this.bizDateParam = bizDateParam == null ? null : bizDateParam.trim();
    }

    public String getSysTimeParam() {
        return sysTimeParam;
    }

    public void setSysTimeParam(String sysTimeParam) {
        this.sysTimeParam = sysTimeParam == null ? null : sysTimeParam.trim();
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SchedulerTask other = (SchedulerTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSchedulerJobId() == null ? other.getSchedulerJobId() == null : this.getSchedulerJobId().equals(other.getSchedulerJobId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getTaskStatus() == null ? other.getTaskStatus() == null : this.getTaskStatus().equals(other.getTaskStatus()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getAlarmTime() == null ? other.getAlarmTime() == null : this.getAlarmTime().equals(other.getAlarmTime()))
            && (this.getRetryTimes() == null ? other.getRetryTimes() == null : this.getRetryTimes().equals(other.getRetryTimes()))
            && (this.getIsManual() == null ? other.getIsManual() == null : this.getIsManual().equals(other.getIsManual()))
            && (this.getBizDateParam() == null ? other.getBizDateParam() == null : this.getBizDateParam().equals(other.getBizDateParam()))
            && (this.getSysTimeParam() == null ? other.getSysTimeParam() == null : this.getSysTimeParam().equals(other.getSysTimeParam()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSchedulerJobId() == null) ? 0 : getSchedulerJobId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getTaskStatus() == null) ? 0 : getTaskStatus().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getAlarmTime() == null) ? 0 : getAlarmTime().hashCode());
        result = prime * result + ((getRetryTimes() == null) ? 0 : getRetryTimes().hashCode());
        result = prime * result + ((getIsManual() == null) ? 0 : getIsManual().hashCode());
        result = prime * result + ((getBizDateParam() == null) ? 0 : getBizDateParam().hashCode());
        result = prime * result + ((getSysTimeParam() == null) ? 0 : getSysTimeParam().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", schedulerJobId=").append(schedulerJobId);
        sb.append(", projectId=").append(projectId);
        sb.append(", taskStatus=").append(taskStatus);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", alarmTime=").append(alarmTime);
        sb.append(", retryTimes=").append(retryTimes);
        sb.append(", isManual=").append(isManual);
        sb.append(", bizDateParam=").append(bizDateParam);
        sb.append(", sysTimeParam=").append(sysTimeParam);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}