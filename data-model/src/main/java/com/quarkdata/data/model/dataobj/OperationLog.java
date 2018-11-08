package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Date;

public class OperationLog implements Serializable {
    private Long id;

    private Integer operationType;

    private Long operationUser;

    private Integer operayionContent;

    private Long operationTypeId;

    private Date operationDate;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Long getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(Long operationUser) {
        this.operationUser = operationUser;
    }

    public Integer getOperayionContent() {
        return operayionContent;
    }

    public void setOperayionContent(Integer operayionContent) {
        this.operayionContent = operayionContent;
    }

    public Long getOperationTypeId() {
        return operationTypeId;
    }

    public void setOperationTypeId(Long operationTypeId) {
        this.operationTypeId = operationTypeId;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
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
        OperationLog other = (OperationLog) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getOperationType() == null ? other.getOperationType() == null : this.getOperationType().equals(other.getOperationType()))
            && (this.getOperationUser() == null ? other.getOperationUser() == null : this.getOperationUser().equals(other.getOperationUser()))
            && (this.getOperayionContent() == null ? other.getOperayionContent() == null : this.getOperayionContent().equals(other.getOperayionContent()))
            && (this.getOperationTypeId() == null ? other.getOperationTypeId() == null : this.getOperationTypeId().equals(other.getOperationTypeId()))
            && (this.getOperationDate() == null ? other.getOperationDate() == null : this.getOperationDate().equals(other.getOperationDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getOperationType() == null) ? 0 : getOperationType().hashCode());
        result = prime * result + ((getOperationUser() == null) ? 0 : getOperationUser().hashCode());
        result = prime * result + ((getOperayionContent() == null) ? 0 : getOperayionContent().hashCode());
        result = prime * result + ((getOperationTypeId() == null) ? 0 : getOperationTypeId().hashCode());
        result = prime * result + ((getOperationDate() == null) ? 0 : getOperationDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", operationType=").append(operationType);
        sb.append(", operationUser=").append(operationUser);
        sb.append(", operayionContent=").append(operayionContent);
        sb.append(", operationTypeId=").append(operationTypeId);
        sb.append(", operationDate=").append(operationDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}