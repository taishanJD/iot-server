package com.quarkdata.data.model.vo;

import java.io.Serializable;

/**
 * 资源管理 数据集列表
 * 
 * @author WangHao 2018年5月17日
 */
public class SourceDataSetListVO implements Serializable {

	private static final long serialVersionUID = -4494297479948617150L;

	// 数据集ID
	private Long datasetId;
	// 数据集名称
	private String datasetName;
	// 数据集表名
	private String tableName;
	// 数据集所在项目ID
	private Long projectId;
	// 数据集所在项目名称
	private String projectName;
	// 数据集所在数据源类型
	private String dataSourceType;

	public Long getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDataSourceType() {
		return dataSourceType;
	}

	public void setDataSourceType(String dataSourceType) {
		this.dataSourceType = dataSourceType;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
