package com.quarkdata.data.service;

import java.util.List;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.ImportTable;

/**
 * @author huliang
 *
 */
public interface DataSourceService {

	/**
	 * 获取数据源列表
	 * 
	 * @param search
	 *            模糊搜索内容；如果为空，获取所有数据
	 * @param sortType
	 *            排序方式
	 * @return
	 */
	ResultCode getList(String param, Integer sortType,Integer dataType);

	/**
	 * 添加数据源
	 * 
	 * @param connName
	 *            连接名称
	 * @param host
	 *            服务器或集群配置
	 * @param port
	 *            端口
	 * @param db
	 *            数据库
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param isWrite
	 *            是否允许对此连接的数据集进行写操作（0：否、1：是）
	 * @param isCreateDataSet
	 *            允许对此连接的原始数据集进行写操作（0：否、1：是）
	 * @param isAllUserCreateDataSet
	 *            是否允许所有用户使用此连接创建新的数据集（0：否、1：是）
	 * @param dataType
	 *            数据类型（0：MySQL、1：Oracle、2：PostgreSQL、3：MS SQL
	 *            Server、4：HANA、5：MongoDB、6：Cassandra、7：ElasticSearch、8：Hive、9：Hbase、10：HDFS、11：HTTP、12：FTP、13：SFTP）
	 * @return
	 */
	ResultCode add(String connName, String host, String port, String db, String userName, String password,
			Integer isWrite, Integer isCreateDataSet, Integer isAllUserCreateDataSet, Integer dataType);

	/**
	 * 编辑数据源
	 * 
	 * @param connName
	 *            连接名称
	 * @param host
	 *            服务器或集群配置
	 * @param port
	 *            端口
	 * @param db
	 *            数据库
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param isWrite
	 *            是否允许对此连接的数据集进行写操作（0：否、1：是）
	 * @param isCreateDataSet
	 *            允许对此连接的原始数据集进行写操作（0：否、1：是）
	 * @param isAllUserCreateDataSet
	 *            是否允许所有用户使用此连接创建新的数据集（0：否、1：是）
	 * @param id
	 *            数据源ID
	 * @return
	 */
	ResultCode edit(String connName, String host, String port, String db, String userName, String password,
			Integer isWrite, Integer isCreateDataSet, Integer isAllUserCreateDataSet, Long id);

	/**
	 * 删除数据源
	 * 
	 * @param id
	 *            数据源id
	 * @return
	 */
	ResultCode delete(Long userId);

	/**
	 * 测试数据源连接
	 * 
	 * @param host
	 *            服务器或集群配置
	 * @param port
	 *            端口
	 * @param db
	 *            数据库
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param status
	 *            数据源类型（０：Mysql、6：Cassandra）
	 * @return
	 */
	ResultCode testDataSource(String host, String port, String db, String userName, String password, String dataType);

	/**
	 * 数据源详情
	 * 
	 * @param id
	 *            数据源ID
	 * @return
	 */
	ResultCode detail(Long id);

	/**
	 * 数据表导入项目，创建对应数据集
	 * 
	 * @param ImportTable
	 * @return
	 */
	ResultCode importTable(List<ImportTable> importTableList);

	/**
	 * 数据表结构
	 * 
	 * @param datasetId
	 *            数据集ID
	 * @return
	 */
	ResultCode datasetSchemaStruct(Long datasetId);

	/**
	 * 数据表列表
	 * 
	 * @param search
	 *            模糊搜索内容；如果为空，获取所有数据
	 * @return
	 */
	ResultCode datasetList(String search);

}
