package com.quarkdata.data.web.api.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.ImportTable;
import com.quarkdata.data.service.DataSourceService;
import com.quarkdata.data.service.OutsideService;
import com.quarkdata.data.web.api.controller.RouteKey;

/**
 * @author huliang
 *
 */
@RestController
@RequestMapping(RouteKey.PREFIX_API + RouteKey.OUTSIDE)
public class OutsideController {

	@Autowired
	DataSourceService dataSourceService;

	@Autowired
	OutsideService outsideService;

	/**
	 * 数据表导入项目，创建对应数据集
	 * 
	 * @param dataSourceId
	 *            数据源ID
	 * @param projectId
	 *            项目ID
	 * @param datasetName
	 *            数据集名称
	 * @param tableName
	 *            对应数据源表名（mysql table、MongoDB collection等）
	 * @return
	 */
	@RequestMapping(value = "/import_table", method = RequestMethod.POST)
	public ResultCode importTable(@RequestBody List<ImportTable> importDataList) {
		return dataSourceService.importTable(importDataList);
	}

	/**
	 * 获取外部数据库名及个数
	 * 
	 * @param datasetId
	 *            数据类型（0：MySQL、1：Oracle、2：PostgreSQL、3：MS SQL
	 *            Server、4：HANA、5：MongoDB、6：Cassandra、7：ElasticSearch、8：Hive、9：Hbase、10：HDFS、11：HTTP、12：FTP、13：SFTP）
	 * @return
	 */
	@RequestMapping(value = "/db", method = RequestMethod.GET)
	public ResultCode getDbList(@RequestParam(value = "dataType", required = true) Integer dataType) {
		return outsideService.getDb(dataType);
	}

	/**
	 * 数据表列表
	 * 
	 * @param dataSourceId
	 *            数据源ID
	 * @param search
	 *            模糊搜索内容；如果为空，获取所有数据
	 * @return
	 */
	@RequestMapping(value = "/table_list", method = RequestMethod.GET)
	public ResultCode getDbTableList(@RequestParam(value = "dataSourceId", required = true) Long dataSourceId,
			@RequestParam(value = "search", required = false) String search) {
		return outsideService.getTableList(dataSourceId, search);
	}

	/**
	 * 获取表中字段及描述
	 */
	@RequestMapping(value = "/table_struct", method = RequestMethod.GET)
	public ResultCode getDbTableStruct(@RequestParam(value = "dataSourceId", required = true) Long dataSourceId,
			@RequestParam(value = "tableName", required = true) String tableName,
			@RequestParam(value = "structName", required = false) String structName) {
		return outsideService.getDbTableStruct(dataSourceId, tableName, structName);
	}

	/**
	 * 获取表中字段及描述
	 */
	@RequestMapping(value = "/checkTableNameList", method = RequestMethod.POST)
	public ResultCode checkTableNameList(@RequestBody List<String> checkTableNameList) {
		return outsideService.checkTableNameList(checkTableNameList);
	}
}
