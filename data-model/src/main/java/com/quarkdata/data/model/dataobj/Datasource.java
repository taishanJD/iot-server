package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.Date;

public class Datasource implements Serializable {
    private Long id;

    private Long tenantId;

    private String datasourceType;

    private String dataType;

    private String connName;

    private String host;

    private Integer port;

    private String db;

    private String username;

    private String password;

    private String isWrite;

    private String isCreateDataset;

    private String isAllUserCreateDataset;

    private Date createTime;

    private Date updateTime;

    private Long createUser;

    private Long updateUser;

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

    public String getDatasourceType() {
        return datasourceType;
    }

    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType == null ? null : datasourceType.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getConnName() {
        return connName;
    }

    public void setConnName(String connName) {
        this.connName = connName == null ? null : connName.trim();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host == null ? null : host.trim();
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db == null ? null : db.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getIsWrite() {
        return isWrite;
    }

    public void setIsWrite(String isWrite) {
        this.isWrite = isWrite == null ? null : isWrite.trim();
    }

    public String getIsCreateDataset() {
        return isCreateDataset;
    }

    public void setIsCreateDataset(String isCreateDataset) {
        this.isCreateDataset = isCreateDataset == null ? null : isCreateDataset.trim();
    }

    public String getIsAllUserCreateDataset() {
        return isAllUserCreateDataset;
    }

    public void setIsAllUserCreateDataset(String isAllUserCreateDataset) {
        this.isAllUserCreateDataset = isAllUserCreateDataset == null ? null : isAllUserCreateDataset.trim();
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
        Datasource other = (Datasource) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getDatasourceType() == null ? other.getDatasourceType() == null : this.getDatasourceType().equals(other.getDatasourceType()))
            && (this.getDataType() == null ? other.getDataType() == null : this.getDataType().equals(other.getDataType()))
            && (this.getConnName() == null ? other.getConnName() == null : this.getConnName().equals(other.getConnName()))
            && (this.getHost() == null ? other.getHost() == null : this.getHost().equals(other.getHost()))
            && (this.getPort() == null ? other.getPort() == null : this.getPort().equals(other.getPort()))
            && (this.getDb() == null ? other.getDb() == null : this.getDb().equals(other.getDb()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getIsWrite() == null ? other.getIsWrite() == null : this.getIsWrite().equals(other.getIsWrite()))
            && (this.getIsCreateDataset() == null ? other.getIsCreateDataset() == null : this.getIsCreateDataset().equals(other.getIsCreateDataset()))
            && (this.getIsAllUserCreateDataset() == null ? other.getIsAllUserCreateDataset() == null : this.getIsAllUserCreateDataset().equals(other.getIsAllUserCreateDataset()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getDatasourceType() == null) ? 0 : getDatasourceType().hashCode());
        result = prime * result + ((getDataType() == null) ? 0 : getDataType().hashCode());
        result = prime * result + ((getConnName() == null) ? 0 : getConnName().hashCode());
        result = prime * result + ((getHost() == null) ? 0 : getHost().hashCode());
        result = prime * result + ((getPort() == null) ? 0 : getPort().hashCode());
        result = prime * result + ((getDb() == null) ? 0 : getDb().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getIsWrite() == null) ? 0 : getIsWrite().hashCode());
        result = prime * result + ((getIsCreateDataset() == null) ? 0 : getIsCreateDataset().hashCode());
        result = prime * result + ((getIsAllUserCreateDataset() == null) ? 0 : getIsAllUserCreateDataset().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
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
        sb.append(", datasourceType=").append(datasourceType);
        sb.append(", dataType=").append(dataType);
        sb.append(", connName=").append(connName);
        sb.append(", host=").append(host);
        sb.append(", port=").append(port);
        sb.append(", db=").append(db);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", isWrite=").append(isWrite);
        sb.append(", isCreateDataset=").append(isCreateDataset);
        sb.append(", isAllUserCreateDataset=").append(isAllUserCreateDataset);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}