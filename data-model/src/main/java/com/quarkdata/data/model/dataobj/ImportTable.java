package com.quarkdata.data.model.dataobj;

import java.io.Serializable;

public class ImportTable implements Serializable{
	
	private static final long serialVersionUID = -8835570790335843797L;

	private Long projectId;
	
	private Long dataSourceId;
	
	private String tableName;
	
	private String datasetName;

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getDataSourceId() {
		return dataSourceId;
	}

	public void setDataSourceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}
	


}
