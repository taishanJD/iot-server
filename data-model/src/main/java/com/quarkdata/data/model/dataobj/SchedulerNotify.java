package com.quarkdata.data.model.dataobj;

import java.io.Serializable;

public class SchedulerNotify implements Serializable {
    private Long id;

    private Long schedulerJobId;

    private Long receiveUserId;

    private String noticeType;

    private String noticeReasonType;

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

    public Long getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Long receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType == null ? null : noticeType.trim();
    }

    public String getNoticeReasonType() {
        return noticeReasonType;
    }

    public void setNoticeReasonType(String noticeReasonType) {
        this.noticeReasonType = noticeReasonType == null ? null : noticeReasonType.trim();
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
        SchedulerNotify other = (SchedulerNotify) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSchedulerJobId() == null ? other.getSchedulerJobId() == null : this.getSchedulerJobId().equals(other.getSchedulerJobId()))
            && (this.getReceiveUserId() == null ? other.getReceiveUserId() == null : this.getReceiveUserId().equals(other.getReceiveUserId()))
            && (this.getNoticeType() == null ? other.getNoticeType() == null : this.getNoticeType().equals(other.getNoticeType()))
            && (this.getNoticeReasonType() == null ? other.getNoticeReasonType() == null : this.getNoticeReasonType().equals(other.getNoticeReasonType()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSchedulerJobId() == null) ? 0 : getSchedulerJobId().hashCode());
        result = prime * result + ((getReceiveUserId() == null) ? 0 : getReceiveUserId().hashCode());
        result = prime * result + ((getNoticeType() == null) ? 0 : getNoticeType().hashCode());
        result = prime * result + ((getNoticeReasonType() == null) ? 0 : getNoticeReasonType().hashCode());
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
        sb.append(", receiveUserId=").append(receiveUserId);
        sb.append(", noticeType=").append(noticeType);
        sb.append(", noticeReasonType=").append(noticeReasonType);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}