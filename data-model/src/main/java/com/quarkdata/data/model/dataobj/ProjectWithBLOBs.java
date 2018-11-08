package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Arrays;

public class ProjectWithBLOBs extends Project implements Serializable {
    private String projectDesc;

    private byte[] projectPicture;

    private static final long serialVersionUID = 1L;

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc == null ? null : projectDesc.trim();
    }

    public byte[] getProjectPicture() {
        return projectPicture;
    }

    public void setProjectPicture(byte[] projectPicture) {
        this.projectPicture = projectPicture;
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
        ProjectWithBLOBs other = (ProjectWithBLOBs) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getProjectName() == null ? other.getProjectName() == null : this.getProjectName().equals(other.getProjectName()))
            && (this.getProjectKey() == null ? other.getProjectKey() == null : this.getProjectKey().equals(other.getProjectKey()))
            && (this.getProjectStatus() == null ? other.getProjectStatus() == null : this.getProjectStatus().equals(other.getProjectStatus()))
            && (this.getProjectInfo() == null ? other.getProjectInfo() == null : this.getProjectInfo().equals(other.getProjectInfo()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getProjectDesc() == null ? other.getProjectDesc() == null : this.getProjectDesc().equals(other.getProjectDesc()))
            && (Arrays.equals(this.getProjectPicture(), other.getProjectPicture()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getProjectName() == null) ? 0 : getProjectName().hashCode());
        result = prime * result + ((getProjectKey() == null) ? 0 : getProjectKey().hashCode());
        result = prime * result + ((getProjectStatus() == null) ? 0 : getProjectStatus().hashCode());
        result = prime * result + ((getProjectInfo() == null) ? 0 : getProjectInfo().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getProjectDesc() == null) ? 0 : getProjectDesc().hashCode());
        result = prime * result + (Arrays.hashCode(getProjectPicture()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectDesc=").append(projectDesc);
        sb.append(", projectPicture=").append(projectPicture);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}