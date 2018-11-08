package com.quarkdata.data.model.vo;

import java.util.Date;

/**
 * 数据集列表元素，需要数据集的id,name,update_time,data_type
 */
public class DataSetListVO {
    private Long id;
	private String datasetName;
	private Date updateTime;
	private String datasetType;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDatasetType() {
        return datasetType;
    }

    public void setDatasetType(String datasetType) {
        this.datasetType = datasetType;
    }
}
