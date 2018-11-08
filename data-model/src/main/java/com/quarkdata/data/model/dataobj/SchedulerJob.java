package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Date;

public class SchedulerJob implements Serializable {
    private Long id;

    private Long tenantId;

    private String jobName;

    private String jobType;

    private String jobBizType;

    private Long jobBizId;

    private String isFrozen;

    private String isNotify;

    private String isPublish;

    private String intervalType;

    private String intervalValues;

    private Integer intervalHour;

    private Integer intervalMinute;

    private Date validStartdate;

    private Date validEnddate;

    private Integer startHour;

    private Integer startMinute;

    private Integer endHour;

    private Integer endMinute;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

    private String isDel;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType == null ? null : jobType.trim();
    }

    public String getJobBizType() {
        return jobBizType;
    }

    public void setJobBizType(String jobBizType) {
        this.jobBizType = jobBizType == null ? null : jobBizType.trim();
    }

    public Long getJobBizId() {
        return jobBizId;
    }

    public void setJobBizId(Long jobBizId) {
        this.jobBizId = jobBizId;
    }

    public String getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(String isFrozen) {
        this.isFrozen = isFrozen == null ? null : isFrozen.trim();
    }

    public String getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(String isNotify) {
        this.isNotify = isNotify == null ? null : isNotify.trim();
    }

    public String getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(String isPublish) {
        this.isPublish = isPublish == null ? null : isPublish.trim();
    }

    public String getIntervalType() {
        return intervalType;
    }

    public void setIntervalType(String intervalType) {
        this.intervalType = intervalType == null ? null : intervalType.trim();
    }

    public String getIntervalValues() {
        return intervalValues;
    }

    public void setIntervalValues(String intervalValues) {
        this.intervalValues = intervalValues == null ? null : intervalValues.trim();
    }

    public Integer getIntervalHour() {
        return intervalHour;
    }

    public void setIntervalHour(Integer intervalHour) {
        this.intervalHour = intervalHour;
    }

    public Integer getIntervalMinute() {
        return intervalMinute;
    }

    public void setIntervalMinute(Integer intervalMinute) {
        this.intervalMinute = intervalMinute;
    }

    public Date getValidStartdate() {
        return validStartdate;
    }

    public void setValidStartdate(Date validStartdate) {
        this.validStartdate = validStartdate;
    }

    public Date getValidEnddate() {
        return validEnddate;
    }

    public void setValidEnddate(Date validEnddate) {
        this.validEnddate = validEnddate;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public Integer getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(Integer startMinute) {
        this.startMinute = startMinute;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    public Integer getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(Integer endMinute) {
        this.endMinute = endMinute;
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

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
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
        SchedulerJob other = (SchedulerJob) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getJobName() == null ? other.getJobName() == null : this.getJobName().equals(other.getJobName()))
            && (this.getJobType() == null ? other.getJobType() == null : this.getJobType().equals(other.getJobType()))
            && (this.getJobBizType() == null ? other.getJobBizType() == null : this.getJobBizType().equals(other.getJobBizType()))
            && (this.getJobBizId() == null ? other.getJobBizId() == null : this.getJobBizId().equals(other.getJobBizId()))
            && (this.getIsFrozen() == null ? other.getIsFrozen() == null : this.getIsFrozen().equals(other.getIsFrozen()))
            && (this.getIsNotify() == null ? other.getIsNotify() == null : this.getIsNotify().equals(other.getIsNotify()))
            && (this.getIsPublish() == null ? other.getIsPublish() == null : this.getIsPublish().equals(other.getIsPublish()))
            && (this.getIntervalType() == null ? other.getIntervalType() == null : this.getIntervalType().equals(other.getIntervalType()))
            && (this.getIntervalValues() == null ? other.getIntervalValues() == null : this.getIntervalValues().equals(other.getIntervalValues()))
            && (this.getIntervalHour() == null ? other.getIntervalHour() == null : this.getIntervalHour().equals(other.getIntervalHour()))
            && (this.getIntervalMinute() == null ? other.getIntervalMinute() == null : this.getIntervalMinute().equals(other.getIntervalMinute()))
            && (this.getValidStartdate() == null ? other.getValidStartdate() == null : this.getValidStartdate().equals(other.getValidStartdate()))
            && (this.getValidEnddate() == null ? other.getValidEnddate() == null : this.getValidEnddate().equals(other.getValidEnddate()))
            && (this.getStartHour() == null ? other.getStartHour() == null : this.getStartHour().equals(other.getStartHour()))
            && (this.getStartMinute() == null ? other.getStartMinute() == null : this.getStartMinute().equals(other.getStartMinute()))
            && (this.getEndHour() == null ? other.getEndHour() == null : this.getEndHour().equals(other.getEndHour()))
            && (this.getEndMinute() == null ? other.getEndMinute() == null : this.getEndMinute().equals(other.getEndMinute()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getJobName() == null) ? 0 : getJobName().hashCode());
        result = prime * result + ((getJobType() == null) ? 0 : getJobType().hashCode());
        result = prime * result + ((getJobBizType() == null) ? 0 : getJobBizType().hashCode());
        result = prime * result + ((getJobBizId() == null) ? 0 : getJobBizId().hashCode());
        result = prime * result + ((getIsFrozen() == null) ? 0 : getIsFrozen().hashCode());
        result = prime * result + ((getIsNotify() == null) ? 0 : getIsNotify().hashCode());
        result = prime * result + ((getIsPublish() == null) ? 0 : getIsPublish().hashCode());
        result = prime * result + ((getIntervalType() == null) ? 0 : getIntervalType().hashCode());
        result = prime * result + ((getIntervalValues() == null) ? 0 : getIntervalValues().hashCode());
        result = prime * result + ((getIntervalHour() == null) ? 0 : getIntervalHour().hashCode());
        result = prime * result + ((getIntervalMinute() == null) ? 0 : getIntervalMinute().hashCode());
        result = prime * result + ((getValidStartdate() == null) ? 0 : getValidStartdate().hashCode());
        result = prime * result + ((getValidEnddate() == null) ? 0 : getValidEnddate().hashCode());
        result = prime * result + ((getStartHour() == null) ? 0 : getStartHour().hashCode());
        result = prime * result + ((getStartMinute() == null) ? 0 : getStartMinute().hashCode());
        result = prime * result + ((getEndHour() == null) ? 0 : getEndHour().hashCode());
        result = prime * result + ((getEndMinute() == null) ? 0 : getEndMinute().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", jobName=").append(jobName);
        sb.append(", jobType=").append(jobType);
        sb.append(", jobBizType=").append(jobBizType);
        sb.append(", jobBizId=").append(jobBizId);
        sb.append(", isFrozen=").append(isFrozen);
        sb.append(", isNotify=").append(isNotify);
        sb.append(", isPublish=").append(isPublish);
        sb.append(", intervalType=").append(intervalType);
        sb.append(", intervalValues=").append(intervalValues);
        sb.append(", intervalHour=").append(intervalHour);
        sb.append(", intervalMinute=").append(intervalMinute);
        sb.append(", validStartdate=").append(validStartdate);
        sb.append(", validEnddate=").append(validEnddate);
        sb.append(", startHour=").append(startHour);
        sb.append(", startMinute=").append(startMinute);
        sb.append(", endHour=").append(endHour);
        sb.append(", endMinute=").append(endMinute);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", isDel=").append(isDel);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}