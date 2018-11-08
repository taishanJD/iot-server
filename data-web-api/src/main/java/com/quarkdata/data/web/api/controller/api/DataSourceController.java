package com.quarkdata.data.web.api.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.DataSourceService;
import com.quarkdata.data.web.api.controller.RouteKey;

/**
 * @author huliang
 *
 */
@Controller
@RequestMapping(RouteKey.PREFIX_API + RouteKey.DATA_SOURCE)
public class DataSourceController {

	@Autowired
	DataSourceService dataSourceService;

	/**
	 * 数据源列表，默认按照修改时间倒序，不分页，搜索关键字为数据连接名称和连接类型
	 * 
	 * @param search
	 *            模糊搜索内容；如果为空，获取所有数据
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody ResultCode getList(@RequestParam(value = "param", required = false) String param,
			@RequestParam(value = "sortType", required = false) Integer sortType,
			@RequestParam(value = "dataType", required = false) Integer dataType) {
		return dataSourceService.getList(param, sortType, dataType);
	}

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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public @ResponseBody ResultCode add(@RequestParam(value = "connName", required = false) String connName,
			@RequestParam(value = "host", required = false) String host,
			@RequestParam(value = "port", required = false) String port,
			@RequestParam(value = "db", required = false) String db,
			@RequestParam(value = "username", required = false) String userName,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "isWrite", required = false) String isWrite,
			@RequestParam(value = "isCreateDataset", required = false) String isCreateDataSet,
			@RequestParam(value = "isAllUserCreateDataset", required = false) String isAllUserCreateDataSet,
			@RequestParam(value = "dataType", required = false) Integer dataType) {
		return dataSourceService.add(connName, host, port, db, userName, password, isWrite.equals("true") ? 1 : 0,
				isCreateDataSet.equals("true") ? 1 : 0, isAllUserCreateDataSet.equals("true") ? 1 : 0, dataType);
	}

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
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public @ResponseBody ResultCode edit(@RequestParam(value = "connName", required = false) String connName,
			@RequestParam(value = "host", required = false) String host,
			@RequestParam(value = "port", required = false) String port,
			@RequestParam(value = "db", required = false) String db,
			@RequestParam(value = "username", required = false) String userName,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "isWrite", required = false) String isWrite,
			@RequestParam(value = "isCreateDataset", required = false) String isCreateDataSet,
			@RequestParam(value = "isAllUserCreateDataset", required = false) String isAllUserCreateDataSet,
			@RequestParam(value = "id", required = false) Long id) {
		return dataSourceService.edit(connName, host, port, db, userName, password, isWrite.equals("true") ? 1 : 0,
				isCreateDataSet.equals("true") ? 1 : 0, isAllUserCreateDataSet.equals("true") ? 1 : 0, id);
	}

	/**
	 * 删除数据源
	 * 
	 * @param id
	 *            数据源ID
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody ResultCode delete(@RequestParam(value = "id", required = false) Long id) {
		return dataSourceService.delete(id);
	}

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
	 *            数据源类型（０：Mysql、１：Cassandra、２：Hive）
	 * @return
	 */
	@RequestMapping(value = "/test_datasource", method = RequestMethod.POST)
	public @ResponseBody ResultCode testDataSource(@RequestParam(value = "host", required = false) String host,
			@RequestParam(value = "port", required = false) String port,
			@RequestParam(value = "db", required = false) String db,
			@RequestParam(value = "username", required = false) String username,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "dataType", required = false) String dataType) {
		return dataSourceService.testDataSource(host, port, db, username, password, dataType);
	}

	/**
	 * 数据源详情
	 * 
	 * @param id
	 *            数据源ID
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public @ResponseBody ResultCode detail(@RequestParam(value = "id", required = false) Long id) {
		return dataSourceService.detail(id);
	}
}
