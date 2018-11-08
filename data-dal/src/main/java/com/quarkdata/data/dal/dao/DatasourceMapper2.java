package com.quarkdata.data.dal.dao;

public interface DatasourceMapper2 {

	/**
	 * 查看数据源名称是否重复
	 * @param
	 */
	Integer checkDataSourceNameIsHave(String connName);

}
