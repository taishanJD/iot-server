package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Date;

public class Dataset implements Serializable {
    private Long id;

    private Long datasourceId;

    private Long projectId;

    private String datasetName;

    private String tableName;

    private String isWrite;

    private String isSync;

    private String isFloatToInt;

    private String sampleType;

    private Integer sampleTypeValue;

    private String isSampleFilter;

    private String sampleFilterType;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

    private Date syncTime;

    private Long dataCount;

    private String description;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName == null ? null : datasetName.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getIsWrite() {
        return isWrite;
    }

    public void setIsWrite(String isWrite) {
        this.isWrite = isWrite == null ? null : isWrite.trim();
    }

    public String getIsSync() {
        return isSync;
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync == null ? null : isSync.trim();
    }

    public String getIsFloatToInt() {
        return isFloatToInt;
    }

    public void setIsFloatToInt(String isFloatToInt) {
        this.isFloatToInt = isFloatToInt == null ? null : isFloatToInt.trim();
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType == null ? null : sampleType.trim();
    }

    public Integer getSampleTypeValue() {
        return sampleTypeValue;
    }

    public void setSampleTypeValue(Integer sampleTypeValue) {
        this.sampleTypeValue = sampleTypeValue;
    }

    public String getIsSampleFilter() {
        return isSampleFilter;
    }

    public void setIsSampleFilter(String isSampleFilter) {
        this.isSampleFilter = isSampleFilter == null ? null : isSampleFilter.trim();
    }

    public String getSampleFilterType() {
        return sampleFilterType;
    }

    public void setSampleFilterType(String sampleFilterType) {
        this.sampleFilterType = sampleFilterType == null ? null : sampleFilterType.trim();
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

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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
        Dataset other = (Dataset) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDatasourceId() == null ? other.getDatasourceId() == null : this.getDatasourceId().equals(other.getDatasourceId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getDatasetName() == null ? other.getDatasetName() == null : this.getDatasetName().equals(other.getDatasetName()))
            && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
            && (this.getIsWrite() == null ? other.getIsWrite() == null : this.getIsWrite().equals(other.getIsWrite()))
            && (this.getIsSync() == null ? other.getIsSync() == null : this.getIsSync().equals(other.getIsSync()))
            && (this.getIsFloatToInt() == null ? other.getIsFloatToInt() == null : this.getIsFloatToInt().equals(other.getIsFloatToInt()))
            && (this.getSampleType() == null ? other.getSampleType() == null : this.getSampleType().equals(other.getSampleType()))
            && (this.getSampleTypeValue() == null ? other.getSampleTypeValue() == null : this.getSampleTypeValue().equals(other.getSampleTypeValue()))
            && (this.getIsSampleFilter() == null ? other.getIsSampleFilter() == null : this.getIsSampleFilter().equals(other.getIsSampleFilter()))
            && (this.getSampleFilterType() == null ? other.getSampleFilterType() == null : this.getSampleFilterType().equals(other.getSampleFilterType()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getSyncTime() == null ? other.getSyncTime() == null : this.getSyncTime().equals(other.getSyncTime()))
            && (this.getDataCount() == null ? other.getDataCount() == null : this.getDataCount().equals(other.getDataCount()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDatasourceId() == null) ? 0 : getDatasourceId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getDatasetName() == null) ? 0 : getDatasetName().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getIsWrite() == null) ? 0 : getIsWrite().hashCode());
        result = prime * result + ((getIsSync() == null) ? 0 : getIsSync().hashCode());
        result = prime * result + ((getIsFloatToInt() == null) ? 0 : getIsFloatToInt().hashCode());
        result = prime * result + ((getSampleType() == null) ? 0 : getSampleType().hashCode());
        result = prime * result + ((getSampleTypeValue() == null) ? 0 : getSampleTypeValue().hashCode());
        result = prime * result + ((getIsSampleFilter() == null) ? 0 : getIsSampleFilter().hashCode());
        result = prime * result + ((getSampleFilterType() == null) ? 0 : getSampleFilterType().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getSyncTime() == null) ? 0 : getSyncTime().hashCode());
        result = prime * result + ((getDataCount() == null) ? 0 : getDataCount().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", datasourceId=").append(datasourceId);
        sb.append(", projectId=").append(projectId);
        sb.append(", datasetName=").append(datasetName);
        sb.append(", tableName=").append(tableName);
        sb.append(", isWrite=").append(isWrite);
        sb.append(", isSync=").append(isSync);
        sb.append(", isFloatToInt=").append(isFloatToInt);
        sb.append(", sampleType=").append(sampleType);
        sb.append(", sampleTypeValue=").append(sampleTypeValue);
        sb.append(", isSampleFilter=").append(isSampleFilter);
        sb.append(", sampleFilterType=").append(sampleFilterType);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", syncTime=").append(syncTime);
        sb.append(", dataCount=").append(dataCount);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}