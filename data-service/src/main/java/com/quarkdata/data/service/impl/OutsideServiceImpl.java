package com.quarkdata.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.dal.dao.DatasetMapper;
import com.quarkdata.data.dal.dao.DatasourceMapper;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.DatasetExample;
import com.quarkdata.data.model.dataobj.DatasetExample.Criteria;
import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.model.dataobj.DatasourceExample;
import com.quarkdata.data.service.OutsideService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;
import com.quarkdata.data.util.db.MySqlUtils2;
import com.quarkdata.tenant.model.dataobj.Tenant;

@Service
public class OutsideServiceImpl implements OutsideService {

	private static Logger logger = Logger.getLogger(OutsideServiceImpl.class);

	@Autowired
	DatasourceMapper datasourceMapper;

	@Autowired
	DatasetMapper datasetMapper;

	@Override
	public ResultCode getDb(Integer dataType) {
		ResultCode resultCode = null;
		// 获取当前会话租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		Long tenantId = 1L;
		if (null != tenant) {
			tenantId = tenant.getId();
		}
		// 判断传参
		if (null == dataType) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}

		List<HashMap<String, Object>> dbList = new ArrayList<HashMap<String, Object>>();
		DatasourceExample example = new DatasourceExample();
		example.createCriteria().andTenantIdEqualTo(tenantId).andDataTypeEqualTo(dataType + "");
		List<Datasource> dataSourceList = datasourceMapper.selectByExample(example);
		if (!dataSourceList.isEmpty()) {
			for (Datasource dataSource : dataSourceList) {
				HashMap<String, Object> dbMap = new HashMap<String, Object>();
				dbMap.put("id", dataSource.getId());
				dbMap.put("db", dataSource.getConnName());
				dbList.add(dbMap);
			}
		}

		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dataType", dataType);
		resultMap.put("datasourceNum", dataSourceList.size());
		resultMap.put("dbData", dbList);
		resultCode = ResultUtil.success(resultMap);
		return resultCode;
	}

	@Override
	public ResultCode getTableList(Long dataSourceId, String search) {
		ResultCode resultCode = null;
		// 判断传参
		if (null == dataSourceId) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}
		Datasource dataSource = datasourceMapper.selectByPrimaryKey(dataSourceId);
		if (null != dataSource) {
			HashMap<String, Object> resultMap = new HashMap<>(2);
			List<String> tableList;
			String dataType = dataSource.getDataType();
			String host = dataSource.getHost();
			String port = dataSource.getPort().toString();
			String dbName = dataSource.getDb();
			String userName = dataSource.getUsername();
			String password = dataSource.getPassword();
			logger.info("dataType = " + dataType + "\t host = " + host + "\t port = " + port + "\t dbName = " + dbName
					+ "\t userName = " + userName + "\t password = " + password);
			switch (dataType) {
			case "0":
				// Mysql
				try {
					tableList = MySqlUtils2.getTableNames(host, port, dbName, userName, password);
					if (!tableList.isEmpty() && null != search && !"".equals(search)) {
						Iterator<String> iterator = tableList.iterator();
						while (iterator.hasNext()) {
							if (!iterator.next().toLowerCase().contains(search.toLowerCase())) {
								iterator.remove();
							}
						}
					}
					resultMap.put("dataSourceId", dataSourceId);
					resultMap.put("tableName", tableList);
					resultCode = ResultUtil.success(resultMap);
				} catch (Exception e) {
					logger.error("数据源连接失败");
					resultCode = ResultUtil.error(Messages.CONNECT_DATASOURCE_ERR_CODE,
							Messages.CONNECT_DATASOURCE_ERR_MSG);
					return resultCode;
				}
				break;
			case "6":
				// Cassandra
				tableList = CassandraUtil2.getTablesNameByDBName(host, userName, password, dbName);
				if (null != tableList && !tableList.isEmpty() && null != search && !"".equals(search)) {
					Iterator<String> iterator = tableList.iterator();
					while (iterator.hasNext()) {
						if (!iterator.next().toLowerCase().contains(search.toLowerCase())) {
							iterator.remove();
						}
					}
				}
				resultMap.put("dataSourceId", dataSourceId);
				resultMap.put("tableName", tableList);
				resultCode = ResultUtil.success(resultMap);
				break;
			case "8":
				// Hive
				break;

			default:
				break;
			}
		}
		return resultCode;
	}

	@Override
	public ResultCode getDbTableStruct(Long dataSourceId, String tableName, String structName) {
		ResultCode resultCode = null;
		// 判断传参
		if (null == dataSourceId) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}
		Datasource dataSource = datasourceMapper.selectByPrimaryKey(dataSourceId);
		if (null != dataSource) {
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			List<String> tableList = new ArrayList<String>();
			String dataType = dataSource.getDataType();
			String host = dataSource.getHost();
			String port = dataSource.getPort() + "";
			String dbName = dataSource.getDb();
			String userName = dataSource.getUsername();
			String password = dataSource.getPassword();
			logger.info("dataType = " + dataType + "\t host = " + host + "\t port = " + port + "\t dbName = " + dbName
					+ "\t userName = " + userName + "\t password = " + password);
			switch (dataType) {
			case "0":
				// Mysql
				try {
					List<Map<String, String>> columnNamesList = MySqlUtils2.getColumnNamesList(tableName, host, port,
							dbName, userName, password);
					if (null != structName && !structName.equals("")) {
						Iterator<Map<String, String>> iterator = columnNamesList.iterator();
						while (iterator.hasNext()) {
							Map<String, String> next = iterator.next();
							if (!next.get("columnName").contains(structName)) {
								iterator.remove();
							}
						}
					}
					resultCode = ResultUtil.success(columnNamesList);
				} catch (Exception e) {
					logger.error("数据源连接失败");
					resultCode = ResultUtil.error(Messages.CONNECT_DATASOURCE_ERR_CODE,
							Messages.CONNECT_DATASOURCE_ERR_MSG);
					return resultCode;
				}
				break;
			case "6":
				// Cassandra
				List<Map<String, String>> columnNamesList = CassandraUtil2.getTableColumn(host, userName, password,
						dbName, tableName);
				if (null != structName && !structName.equals("")) {
					Iterator<Map<String, String>> iterator = columnNamesList.iterator();
					while (iterator.hasNext()) {
						Map<String, String> next = iterator.next();
						if (!next.get("columnName").contains(structName)) {
							iterator.remove();
						}
					}
				}
				resultCode = ResultUtil.success(columnNamesList);
				break;
			case "8":
				// Hive
				break;

			default:
				break;
			}
		}
		return resultCode;
	}

	@Override
	public ResultCode checkTableNameList(List<String> checkTableNameList) {
		DatasetExample datasetExample = new DatasetExample();
		datasetExample.createCriteria().andDatasetNameIn(checkTableNameList);
		long countByExample = datasetMapper.countByExample(datasetExample);
		if (countByExample > 0) {
			return ResultUtil.error(Messages.ADD_DATASET_FAILED_CODE, Messages.ADD_DATASET_FAILED_MSG);
		}
		return ResultUtil.success();
	}

}
