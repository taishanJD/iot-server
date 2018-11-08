package com.quarkdata.data.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DataSetDetailVO implements Serializable {
    private Long id;
    private String datasetName;
    private String dataType;
    private String description;
    private Date updateTime;
    private String updateUserName;//
    private Date createTime;
    private String createUserName;//
    private Long dataCount;//
    private Date syncTime;//
    private Long workflowId;
    private String workflowName;
    private List<WorkFlowNodeVO> workflowNodes;
    // todo 图表类型 List<ChartsVO>


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Long getDataCount() {
        return dataCount;
    }

    public void setDataCount(Long dataCount) {
        this.dataCount = dataCount;
    }

    public Date getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Date syncTime) {
        this.syncTime = syncTime;
    }

    public Long getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Long workflowId) {
        this.workflowId = workflowId;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public List<WorkFlowNodeVO> getWorkflowNodes() {
        return workflowNodes;
    }

    public void setWorkflowNodes(List<WorkFlowNodeVO> workflowNodes) {
        this.workflowNodes = workflowNodes;
    }
}
