package com.quarkdata.data.service;

import java.util.List;

import com.quarkdata.data.model.common.ResultCode;

public interface OutsideService {

	/**
	 * 获取外部数据库名和个数
	 * 
	 * @param dataType
	 *            数据类型（0：MySQL、1：Oracle、2：PostgreSQL、3：MS SQL
	 *            Server、4：HANA、5：MongoDB、6：Cassandra、7：ElasticSearch、8：Hive、9：Hbase、10：HDFS、11：HTTP、12：FTP、13：SFTP）
	 * @return
	 */
	ResultCode getDb(Integer dataType);

	/**
	 * 外部：数据表列表
	 * 
	 * @param dataSourceId
	 *            数据源ID
	 * @param search
	 *            模糊搜索内容；如果为空，获取所有数据
	 * @return
	 */
	ResultCode getTableList(Long dataSourceId, String search);

	/**
	 * 获取外部资源数据表中结构
	 * 
	 * @param
	 */
	ResultCode getDbTableStruct(Long dataSourceId, String tableName, String structName);

	/**
	 * 校验导入的表名是否存在
	 * @param
	 */
	ResultCode checkTableNameList(List<String> checkTableNameList);
}
