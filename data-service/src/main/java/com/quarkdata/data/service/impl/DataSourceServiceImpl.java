package com.quarkdata.data.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.datastax.driver.core.Cluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.dal.dao.DatasetMapper;
import com.quarkdata.data.dal.dao.DatasetMapper2;
import com.quarkdata.data.dal.dao.DatasetSchemaMapper;
import com.quarkdata.data.dal.dao.DatasourceMapper;
import com.quarkdata.data.dal.dao.DatasourceMapper2;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.DatasetExample;
import com.quarkdata.data.model.dataobj.DatasetSchema;
import com.quarkdata.data.model.dataobj.DatasetSchemaExample;
import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.model.dataobj.DatasourceExample;
import com.quarkdata.data.model.dataobj.ImportTable;
import com.quarkdata.data.model.vo.DatasetVO;
import com.quarkdata.data.service.DataSourceService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.util.TypeUtil;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;
import com.quarkdata.data.util.db.MySqlUtils2;
import com.quarkdata.tenant.model.dataobj.Tenant;
import com.quarkdata.tenant.model.vo.UserInfoVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author huliang
 *
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

	@Autowired
	DatasourceMapper datasourceMapper;

	@Autowired
	DatasourceMapper2 datasourceMapper2;

	@Autowired
	DatasetMapper datasetMapper;

	@Autowired
	DatasetSchemaMapper datasetSchemaMapper;

	@Autowired
	DatasetMapper2 datasetMapper2;

	@Override
	public ResultCode getList(String param, Integer sortType, Integer dataType) {
		// 获取当前会话租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		Long tenantId = 1L;
		if (null != tenant) {
			tenantId = tenant.getId();
		}
		DatasourceExample example = new DatasourceExample();
		if (null == param || "".equals(param)) {
			if (null == dataType) {
				example.createCriteria().andTenantIdEqualTo(tenantId);
			} else {
				example.createCriteria().andTenantIdEqualTo(tenantId).andDataTypeEqualTo(dataType.toString());
			}
		} else {
			if (null == dataType) {
				example.createCriteria().andConnNameLike("%" + param + "%").andTenantIdEqualTo(tenantId);
			} else {
				example.createCriteria().andConnNameLike("%" + param + "%").andTenantIdEqualTo(tenantId)
						.andDataTypeEqualTo(dataType.toString());
			}

		}
		switch (sortType) {
		case 0:
			example.setOrderByClause("create_time");
			break;
		case 1:
			example.setOrderByClause("data_type");
			break;
		case 2:
			example.setOrderByClause("conn_name");
			break;
		}
		List<Datasource> dataSourceList = datasourceMapper.selectByExample(example);

		// 清除返回对象中的密码信息
		for (Datasource datasource : dataSourceList) {
			datasource.setPassword(null);
		}
		return ResultUtil.success(dataSourceList);
	}

	@Override
	public ResultCode add(String connName, String host, String port, String db, String userName, String password,
			Integer isWrite, Integer isCreateDataSet, Integer isAllUserCreateDataSet, Integer dataType) {
		ResultCode resultCode = null;
		// 获取当前会话租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		Long tenantId = 1L;
		if (null != tenant) {
			tenantId = tenant.getId();
		}
		// 判断传参
		if (null == connName || null == host || null == port || null == db || null == userName || null == password
				|| null == isWrite || null == isCreateDataSet || null == isAllUserCreateDataSet || null == dataType) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}
		Datasource dataSource = new Datasource();
		dataSource.setTenantId(tenantId);
		dataSource.setConnName(connName);
		dataSource.setHost(host);
		dataSource.setPort(Integer.valueOf(port));
		dataSource.setDb(db);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setIsWrite(isWrite + "");
		dataSource.setIsCreateDataset(isCreateDataSet + "");
		dataSource.setIsAllUserCreateDataset(isAllUserCreateDataSet + "");
		dataSource.setDataType(dataType + "");
		dataSource.setDatasourceType(getDatasourceType(dataType));
		dataSource.setCreateUser(1L);
		dataSource.setCreateTime(new Date());
		dataSource.setUpdateUser(1L);
		dataSource.setUpdateTime(new Date());
		// 判断数据源链接名称是否重复
		if (datasourceMapper2.checkDataSourceNameIsHave(connName) > 0) {
			resultCode = ResultUtil.error(Messages.DATASOURCE_NAME_REPEAT, Messages.DATASOURCE_NAME_REPEAT_MSG);
		} else {
			int resultCount = datasourceMapper.insert(dataSource);
			if (resultCount > 0) {
				resultCode = ResultUtil.success();
			} else {
				resultCode = ResultUtil.error(Messages.EDIT_DATASOURCE_CODE, Messages.EDIT_DATASOURCE_MSG);
			}
		}
		return resultCode;
	}

	@Override
	public ResultCode edit(String connName, String host, String port, String db, String userName, String password,
			Integer isWrite, Integer isCreateDataSet, Integer isAllUserCreateDataSet, Long id) {
		ResultCode resultCode = null;
		// 判断传参
		if (null == connName || null == host || null == port || null == db || null == userName || null == password
				|| null == isWrite || null == isCreateDataSet || null == isAllUserCreateDataSet || null == id) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}
		Datasource dataSource = new Datasource();
		dataSource.setId(id);
		dataSource.setConnName(connName);
		dataSource.setHost(host);
		dataSource.setPort(Integer.valueOf(port));
		dataSource.setDb(db);
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setIsWrite(isWrite + "");
		dataSource.setIsCreateDataset(isCreateDataSet + "");
		dataSource.setIsAllUserCreateDataset(isAllUserCreateDataSet + "");
		int resultCount = datasourceMapper.updateByPrimaryKeySelective(dataSource);
		if (resultCount > 0) {
			resultCode = ResultUtil.success(id);
		} else {
			resultCode = ResultUtil.error(Messages.EDIT_DATASOURCE_CODE, Messages.EDIT_DATASOURCE_MSG);
		}
		return resultCode;
	}

	@Override
	public ResultCode delete(Long id) {
		ResultCode resultCode = null;
		// 判断传参
		if (null == id) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}
		// 判断关联数据集是否存在，如果存在，提示“存在关联数据集”，不能删除成功
		DatasetExample example = new DatasetExample();
		example.createCriteria().andDatasourceIdEqualTo(id);
		long datasetCount = datasetMapper.countByExample(example);
		if (datasetCount > 0) {
			resultCode = ResultUtil.error(Messages.DATASOURCE_DATASET_EXIST_CODE,
					Messages.DATASOURCE_DATASET_EXIST_MSG);
			return resultCode;
		}
		int resultCount = datasourceMapper.deleteByPrimaryKey(id);
		if (resultCount > 0) {
			resultCode = ResultUtil.success();
		} else {
			resultCode = ResultUtil.error(Messages.DELETE_DATASOURCE_CODE, Messages.DELETE_DATASOURCE_MSG);
		}
		return resultCode;
	}

	@Override
	public ResultCode testDataSource(String host, String port, String db, String userName, String password,
			String dataType) {
		ResultCode resultCode = null;
		// 判断传参
		if (null == host || null == port || null == db || null == userName || null == password || null == dataType) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}
		try {
			switch (dataType) {
			case "0":
				// Mysql
				MySqlUtils2 dbMysqlConn = new MySqlUtils2();

				if (dbMysqlConn.isConnection(host, port, db, userName, password)) {
					resultCode = ResultUtil.success();
				} else {
					resultCode = ResultUtil.error(Messages.CONNECT_DATASOURCE_ERR_CODE,
							Messages.CONNECT_DATASOURCE_ERR_MSG);
				}

				break;
			case "6":
				// Cassandra
				Cluster cluster = CassandraUtil2.createCluster(host, null, userName, password);
				if (null != cluster) {
					CassandraUtil2.closeCluster(cluster);
					resultCode = ResultUtil.success();
				} else {
					resultCode = ResultUtil.error(Messages.CONNECT_DATASOURCE_ERR_CODE,
							Messages.CONNECT_DATASOURCE_ERR_MSG);
				}
				break;
			case "2":
				// Hive
				break;
			default:
				// 其他未知数据源
				resultCode = ResultUtil.error(Messages.DATASOURCE_NOT_FOUND_CODE, Messages.DATASOURCE_NOT_FOUND_MSG);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultCode = ResultUtil.error(Messages.CONNECT_DATASOURCE_ERR_CODE, Messages.CONNECT_DATASOURCE_ERR_MSG);
		}
		return resultCode;
	}

	@Override
	public ResultCode detail(Long id) {
		ResultCode resultCode = null;
		if (null == id) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}
		Datasource datasource = datasourceMapper.selectByPrimaryKey(id);
		datasource.setPassword(null);
		resultCode = ResultUtil.success(datasource);
		return resultCode;
	}

	/**
	 * 通过数据类型获取数据源类型
	 * 
	 * @param dataType
	 *            数据类型（0：MySQL、1：Oracle、2：PostgreSQL、3：MS SQL
	 *            Server、4：HANA、5：MongoDB、6：Cassandra、7：ElasticSearch、8：Hive、9：Hbase、10：HDFS、11：HTTP、12：FTP、13：SFTP）
	 * @return 数据源类型（0：SQL、1：NoSQL、2：Hadoop、3：Files、4：Cloud）
	 */
	private String getDatasourceType(Integer dataType) {
		String datasourceType = "";
		switch (dataType) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			datasourceType = 0 + "";
			break;
		case 5:
		case 6:
			datasourceType = 1 + "";
			break;
		case 7:
		case 8:
		case 9:
		case 10:
			datasourceType = 2 + "";
			break;
		case 11:
		case 12:
		case 13:
			datasourceType = 4 + "";
			break;

		default:
			break;
		}
		return datasourceType;
	}

	@Override
	@Transactional(rollbackFor= { Exception.class })
	public ResultCode importTable(List<ImportTable> importTableList) {
		ResultCode resultCode = null;
		// 获取用户
		UserInfoVO userInfoVO = UserInfoUtil.getUserInfoVO();
		Long userId = userInfoVO.getUser().getId();
		try {
			for (ImportTable importTable : importTableList) {
				// 拼装dataset数据
				Dataset dataSet = new Dataset();
				dataSet.setDatasourceId(importTable.getDataSourceId());
				dataSet.setProjectId(importTable.getProjectId());
				dataSet.setDatasetName(importTable.getDatasetName());
				dataSet.setTableName(importTable.getTableName());
				dataSet.setDescription(null);
				dataSet.setIsWrite(0 + "");
				dataSet.setIsSync(0 + "");
				dataSet.setIsFloatToInt(0 + "");
				dataSet.setSampleType(0 + "");
				dataSet.setSampleTypeValue(10000);
				dataSet.setIsSampleFilter(0 + "");
				dataSet.setCreateUser(userId);
				dataSet.setCreateTime(new Date());
				dataSet.setUpdateUser(userId);
				dataSet.setUpdateTime(new Date());
				datasetMapper.insert(dataSet);
				Long dataSetId = dataSet.getId();
				// 保存该dataset的表结构 通过数据源及表名获取对应的表结构
				Map<String, Object> tableStruct = getTableStruct(importTable.getDataSourceId(),
						importTable.getTableName());
				if (tableStruct != null) {
					String dataType = (String) tableStruct.get("dataType");
					List<Map<String, String>> structList = (List<Map<String, String>>) tableStruct.get("structList");
					for (Map<String, String> struct : structList) {
						DatasetSchema datasetSchema = new DatasetSchema();
						datasetSchema.setDatasetId(Long.valueOf(dataSetId));
						datasetSchema.setColumnName(struct.get("columnName"));
						if (dataType.equals("0")) {
							datasetSchema.setColumnType(TypeUtil.mysql2DataSet(struct.get("columnType")));
						} else if (dataType.equals("6")) {
							String columnType = struct.get("columnType");
							if (columnType.contains("list")) {
								columnType = "list";
							} else if (columnType.contains("map")) {
								columnType = "map";
							} else if (columnType.contains("set")) {
								columnType = "set";
							}
							datasetSchema.setColumnType(TypeUtil.cassandra2DataSet(columnType));
						}
						datasetSchema.setColumnComment(struct.get("columnComment"));
						datasetSchema.setColumnLength(null);
						datasetSchema.setMeaning("0");
						datasetSchema.setSubMeaning(null);
						datasetSchema.setValidProportion(0F);
						datasetSchema.setInvalidProportion(0F);
						datasetSchema.setNullProportion(0F);
						datasetSchema.setNotNullProportion(0F);
						datasetSchema.setMaxLength("0");
//						datasetSchema.setProportion(0f);
						datasetSchemaMapper.insert(datasetSchema);
					}
				}
			}
			resultCode = ResultUtil.success();
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultCode = ResultUtil.error(Messages.EDIT_DATASOURCE_CODE, Messages.EDIT_DATASOURCE_MSG);
		}
		return resultCode;
	}

	private Map<String, Object> getTableStruct(Long dataSourceId, String tableName) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Datasource dataSource = datasourceMapper.selectByPrimaryKey(dataSourceId);
		if (null != dataSource) {
			String dataType = dataSource.getDataType();
			String host = dataSource.getHost();
			String port = dataSource.getPort() + "";
			String dbName = dataSource.getDb();
			String userName = dataSource.getUsername();
			String password = dataSource.getPassword();
			resultMap.put("dataType", dataType);
			switch (dataType) {
			case "0":
				// Mysql
				try {
					List<Map<String, String>> columnNamesList = MySqlUtils2.getColumnNamesList(tableName, host, port,
							dbName, userName, password);
					resultMap.put("structList", columnNamesList);
				} catch (Exception e) {
					return null;
				}
				break;
			case "6":
				// Cassandra
				try {
					List<Map<String, String>> columnNamesList = CassandraUtil2.getTableColumn(host, userName, password,
							dbName, tableName);
					resultMap.put("structList", columnNamesList);
				} catch (Exception e) {
					return null;
				}
				break;
			default:
				break;
			}
			return resultMap;
		}
		return null;
	}

	@Override
	public ResultCode datasetSchemaStruct(Long datasetId) {
		ResultCode resultCode = null;
		// 判断传参
		if (null == datasetId) {
			resultCode = ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
			return resultCode;
		}
		DatasetSchemaExample example = new DatasetSchemaExample();
		example.createCriteria().andDatasetIdEqualTo(datasetId);
		List<DatasetSchema> datasetSchemasList = datasetSchemaMapper.selectByExample(example);
		if (!datasetSchemasList.isEmpty()) {
			resultCode = ResultUtil.success(datasetSchemasList.get(0));
		}
		return resultCode;
	}

	@Override
	public ResultCode datasetList(String search) {
		ResultCode resultCode = null;
		List<DatasetVO> dataSourceList = datasetMapper2.datasetList(search);
		resultCode = ResultUtil.success(dataSourceList);
		return resultCode;
	}
}
