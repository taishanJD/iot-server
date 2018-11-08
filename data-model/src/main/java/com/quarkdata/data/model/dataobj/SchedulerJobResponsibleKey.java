package com.quarkdata.data.model.dataobj;

import java.io.Serializable;

public class SchedulerJobResponsibleKey implements Serializable {
    private Long schedulerJobId;

    private Long userId;

    private static final long serialVersionUID = 1L;

    public Long getSchedulerJobId() {
        return schedulerJobId;
    }

    public void setSchedulerJobId(Long schedulerJobId) {
        this.schedulerJobId = schedulerJobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
        SchedulerJobResponsibleKey other = (SchedulerJobResponsibleKey) that;
        return (this.getSchedulerJobId() == null ? other.getSchedulerJobId() == null : this.getSchedulerJobId().equals(other.getSchedulerJobId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSchedulerJobId() == null) ? 0 : getSchedulerJobId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", schedulerJobId=").append(schedulerJobId);
        sb.append(", userId=").append(userId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}