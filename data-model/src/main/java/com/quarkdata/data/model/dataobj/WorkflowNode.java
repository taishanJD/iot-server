package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Date;

public class WorkflowNode implements Serializable {
    private Long id;

    private Long workflowId;

    private String nodeName;

    private String nodeType;

    private Long datasetId;

    private String datasetType;

    private String nodeProcessType;

    private String nodeProcessSubType;

    private String sqlStatement;

    private String preprocessJson;

    private String isAppend;

    private String isDel;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType == null ? null : datasetType.trim();
    }

    public String getNodeProcessType() {
        return nodeProcessType;
    }

    public void setNodeProcessType(String nodeProcessType) {
        this.nodeProcessType = nodeProcessType == null ? null : nodeProcessType.trim();
    }

    public String getNodeProcessSubType() {
        return nodeProcessSubType;
    }

    public void setNodeProcessSubType(String nodeProcessSubType) {
        this.nodeProcessSubType = nodeProcessSubType == null ? null : nodeProcessSubType.trim();
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement == null ? null : sqlStatement.trim();
    }

    public String getPreprocessJson() {
        return preprocessJson;
    }

    public void setPreprocessJson(String preprocessJson) {
        this.preprocessJson = preprocessJson == null ? null : preprocessJson.trim();
    }

    public String getIsAppend() {
        return isAppend;
    }

    public void setIsAppend(String isAppend) {
        this.isAppend = isAppend == null ? null : isAppend.trim();
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel == null ? null : isDel.trim();
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
        WorkflowNode other = (WorkflowNode) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getWorkflowId() == null ? other.getWorkflowId() == null : this.getWorkflowId().equals(other.getWorkflowId()))
            && (this.getNodeName() == null ? other.getNodeName() == null : this.getNodeName().equals(other.getNodeName()))
            && (this.getNodeType() == null ? other.getNodeType() == null : this.getNodeType().equals(other.getNodeType()))
            && (this.getDatasetId() == null ? other.getDatasetId() == null : this.getDatasetId().equals(other.getDatasetId()))
            && (this.getDatasetType() == null ? other.getDatasetType() == null : this.getDatasetType().equals(other.getDatasetType()))
            && (this.getNodeProcessType() == null ? other.getNodeProcessType() == null : this.getNodeProcessType().equals(other.getNodeProcessType()))
            && (this.getNodeProcessSubType() == null ? other.getNodeProcessSubType() == null : this.getNodeProcessSubType().equals(other.getNodeProcessSubType()))
            && (this.getSqlStatement() == null ? other.getSqlStatement() == null : this.getSqlStatement().equals(other.getSqlStatement()))
            && (this.getPreprocessJson() == null ? other.getPreprocessJson() == null : this.getPreprocessJson().equals(other.getPreprocessJson()))
            && (this.getIsAppend() == null ? other.getIsAppend() == null : this.getIsAppend().equals(other.getIsAppend()))
            && (this.getIsDel() == null ? other.getIsDel() == null : this.getIsDel().equals(other.getIsDel()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getWorkflowId() == null) ? 0 : getWorkflowId().hashCode());
        result = prime * result + ((getNodeName() == null) ? 0 : getNodeName().hashCode());
        result = prime * result + ((getNodeType() == null) ? 0 : getNodeType().hashCode());
        result = prime * result + ((getDatasetId() == null) ? 0 : getDatasetId().hashCode());
        result = prime * result + ((getDatasetType() == null) ? 0 : getDatasetType().hashCode());
        result = prime * result + ((getNodeProcessType() == null) ? 0 : getNodeProcessType().hashCode());
        result = prime * result + ((getNodeProcessSubType() == null) ? 0 : getNodeProcessSubType().hashCode());
        result = prime * result + ((getSqlStatement() == null) ? 0 : getSqlStatement().hashCode());
        result = prime * result + ((getPreprocessJson() == null) ? 0 : getPreprocessJson().hashCode());
        result = prime * result + ((getIsAppend() == null) ? 0 : getIsAppend().hashCode());
        result = prime * result + ((getIsDel() == null) ? 0 : getIsDel().hashCode());
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
        sb.append(", workflowId=").append(workflowId);
        sb.append(", nodeName=").append(nodeName);
        sb.append(", nodeType=").append(nodeType);
        sb.append(", datasetId=").append(datasetId);
        sb.append(", datasetType=").append(datasetType);
        sb.append(", nodeProcessType=").append(nodeProcessType);
        sb.append(", nodeProcessSubType=").append(nodeProcessSubType);
        sb.append(", sqlStatement=").append(sqlStatement);
        sb.append(", preprocessJson=").append(preprocessJson);
        sb.append(", isAppend=").append(isAppend);
        sb.append(", isDel=").append(isDel);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}