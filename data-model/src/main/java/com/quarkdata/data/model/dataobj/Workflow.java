package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Date;

public class Workflow implements Serializable {
    private Long id;

    private Long tenantId;

    private Long projectId;

    private Long schedulerJobId;

    private String workflowName;

    private String isDependParent;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

    private String isDel;

    private String workflowDesc;

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getSchedulerJobId() {
        return schedulerJobId;
    }

    public void setSchedulerJobId(Long schedulerJobId) {
        this.schedulerJobId = schedulerJobId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName == null ? null : workflowName.trim();
    }

    public String getIsDependParent() {
        return isDependParent;
    }

    public void setIsDependParent(String isDependParent) {
        this.isDependParent = isDependParent == null ? null : isDependParent.trim();
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

    public String getWorkflowDesc() {
        return workflowDesc;
    }

    public void setWorkflowDesc(String workflowDesc) {
        this.workflowDesc = workflowDesc == null ? null : workflowDesc.trim();
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
        Workflow other = (Workflow) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getSchedulerJobId() == null ? other.getSchedulerJobId() == null : this.getSchedulerJobId().equals(other.getSchedulerJobId()))
            && (this.getWorkflowName() == null ? other.getWorkflowName() == null : this.getWorkflowName().equals(other.getWorkflowName()))
            && (this.getIsDependParent() == null ? other.getIsDependParent() == null : this.getIsDependParent().equals(other.getIsDependParent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getWorkflowDesc() == null ? other.getWorkflowDesc() == null : this.getWorkflowDesc().equals(other.getWorkflowDesc()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getSchedulerJobId() == null) ? 0 : getSchedulerJobId().hashCode());
        result = prime * result + ((getWorkflowName() == null) ? 0 : getWorkflowName().hashCode());
        result = prime * result + ((getIsDependParent() == null) ? 0 : getIsDependParent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getWorkflowDesc() == null) ? 0 : getWorkflowDesc().hashCode());
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
        sb.append(", projectId=").append(projectId);
        sb.append(", schedulerJobId=").append(schedulerJobId);
        sb.append(", workflowName=").append(workflowName);
        sb.append(", isDependParent=").append(isDependParent);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", isDel=").append(isDel);
        sb.append(", workflowDesc=").append(workflowDesc);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}