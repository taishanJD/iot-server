package com.quarkdata.data.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.model.dataobj.*;
import com.quarkdata.data.model.vo.*;
import com.quarkdata.data.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.OperateObjectTypeConstants;
import com.quarkdata.data.model.common.OperateTypeConstants;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.util.StringUtils;
import com.quarkdata.data.util.TypeUtil;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;
import com.quarkdata.data.util.db.MongoUtils;
import com.quarkdata.data.util.db.MySqlUtils;
import com.quarkdata.data.util.db.MySqlUtils2;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DataSetServiceImpl implements DataSetService {

	static Logger logger = Logger.getLogger(DataSetServiceImpl.class);

	@Autowired
	DatasetMapper datasetMapper;

	@Autowired
	DatasetMapper2 datasetMapper2;

	@Autowired
	DatasourceMapper datasourceMapper;

	@Autowired
	ProjectMapper projectMapper;

	@Autowired
	WorkflowNodeMapper2 workflowNodeMapper2;

	@Autowired
	DatasetSchemaMapper datasetSchemaMapper;

	@Autowired
	DatasetSchemaMapper2 datasetSchemaMapper2;

	@Autowired
	DatasetFilterMapper datasetFilterMapper;

	@Autowired
	UserService userService;

	@Autowired
	WorkflowMapper workflowMapper;

	@Autowired
	WorkflowNodeMapper workflowNodeMapper;

	@Autowired
	WorkflowNodeRelMapper workflowNodeRelMapper;

	@Autowired
	ProjectTimelineService projectTimelineService;

	@Autowired
	WorkflowNodeRelMapper2 workflowNodeRelMapper2;

	@Autowired
	DataSetSchemaService dataSetSchemaService;

	@Autowired
	WorkflowService workflowService;

	/**
	 * 请求参数用json写在requestbody中，添加dataset，包含表结构数据
	 *
	 * @param paramMap
	 *            请求json示例 { "projectId": 1, "dataSourceId": 2, "dataSetName":
	 *            "alarm_config_ds", "tableName": "alarm_config",
	 *            "isFloatToInt": "1", "workFlowId":1, // 前端非必填选项，在工作流中创建数据集
	 *            "tableStructure": [{ //表结构存入dataset_schema表中 "index": 1,
	 *            "columnType": "3", "columnName": "id", "meanings": ["0"] }, {
	 *            "index": 2, "columnType": "3", "columnName": "product_id",
	 *            "meanings": ["8","0"] },...{} ] }
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode addDataSet(Map<String, Object> paramMap, Long createUser) {

		// 登录态获取租户和用户信息
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		String createUserName = UserInfoUtil.getUserInfoVO().getUser().getName();
		// Long tenantId = 1L;
		// String createUserName = "写死的";

		Long projectId = Long.parseLong(String.valueOf((int) paramMap.get("projectId")));
		Long dataSourceId = Long.parseLong(String.valueOf((int) paramMap.get("dataSourceId")));
		String dataSetName = (String) paramMap.get("dataSetName");
		String tableName = (String) paramMap.get("tableName");
		String isFloatToInt = (String) paramMap.get("isFloatToInt");
		List<Map<String, Object>> tableStructures = (List<Map<String, Object>>) paramMap.get("tableStructure");

		// is_write字段：是否可写（0：否、1：是），根据对应的datasource的is_write字段值确定
		Datasource datasource = datasourceMapper.selectByPrimaryKey(dataSourceId);
		String isWrite = datasource.getIsWrite();
		String dataType = datasource.getDataType();

		Dataset dataset = this.newDataSet(projectId, dataSourceId, dataSetName, tableName, isWrite, isFloatToInt,
				createUser);
		logger.info("--------------------开始向数据库中添加data_set记录---------------------------------");
		datasetMapper.insertSelective(dataset);
		logger.info("--------------------data_set新添记录：" + dataset.getId());

		// 拿到表结构并存入dataset_schema表中
		logger.info("--------------------开始解析表结构并存入dataset_schema表中---------------------------------");
		if (null != dataset.getId() && null != tableStructures && !tableStructures.isEmpty()) {
			for (Map<String, Object> tableStructure : tableStructures) {
				DatasetSchema datasetSchema = new DatasetSchema();
				datasetSchema.setDatasetId(dataset.getId());
				datasetSchema.setColumnType((String) tableStructure.get("columnType"));
				datasetSchema.setColumnName((String) tableStructure.get("columnName"));

				if (tableStructure.get("meanings") instanceof JSONArray) {
					JSONArray meanings = (JSONArray) tableStructure.get("meanings");
					datasetSchema.setMeaning((String) meanings.get(0));
					if (2 == meanings.size()) {
						datasetSchema.setSubMeaning((String) meanings.get(1));
					}
				}

				if (tableStructure.get("meanings") instanceof ArrayList) {
					ArrayList<String> meanings = (ArrayList<String>) tableStructure.get("meanings");
					datasetSchema.setMeaning(meanings.get(0));
					if (2 == meanings.size()) {
						datasetSchema.setSubMeaning(meanings.get(1));
					}
				}

				datasetSchemaMapper.insertSelective(datasetSchema);
				logger.info("--------------------dataset_schema新添表字段记录：" + datasetSchema.getColumnName());
			}
		}

		// 添加时间轴事件
		if (null != dataset.getId()) {
			logger.info("--------------------开始添加时间轴事件---------------------------------");
			projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.CREATED,
					OperateObjectTypeConstants.DATASET, getOperateDatasetType(dataType), dataset.getId(), dataSetName,
					createUser, createUserName);
		}

		logger.info("--------------------完成---------------------------------");
		return ResultUtil.success(dataset.getId());
	}

	// /**
	// * 新建数据集，直接对应源数据库中的源表，不新建表
	// * 不包括表结构参数
	// *
	// * @param projectId 项目id
	// * @param dataSourceId 数据源id
	// * @param dataSetName 数据集名称
	// * @param tableName 对应数据源表名（mysql table、MongoDB collection等）
	// * @param isFloatToInt 是否浮点型转整型：0否1是
	// * @param createUser 创建人id
	// * @return
	// */
	// @Override
	// @Transactional(readOnly = false, rollbackFor = Exception.class)
	// public ResultCode addDataSet(Long projectId, Long dataSourceId, String
	// dataSetName, String tableName, String isFloatToInt, Long createUser) {
	//
	// //is_write字段：是否可写（0：否、1：是），根据对应的datasource的is_write字段值确定
	// Datasource datasource =
	// datasourceMapper.selectByPrimaryKey(dataSourceId);
	// String isWrite = datasource.getIsWrite();
	//
	// Dataset dataset = this.newDataSet(projectId, dataSourceId, dataSetName,
	// tableName, isWrite, isFloatToInt, createUser);
	// logger.info("--------------------开始向数据库中添加data_set记录---------------------------------");
	// datasetMapper.insertSelective(dataset);
	// logger.info("--------------------data_set新添记录：" + dataset);
	//
	// return ResultUtil.success(dataset.getId());
	// }

	/**
	 * 在工作流中创建某种数据处理时，新建数据集作为输出，
	 * 不指定已有的table名，而是新建一个空table，表结构复制输入数据集的表结构，表名为project key加data_set名
	 *
	 * @param projectId
	 *            项目id
	 * @param dataSourceId
	 *            数据源id
	 * @param workFlowId
	 *            工作流id
	 * @param inputDataSetId
	 *            输入的数据集id
	 * @param dataSetName
	 *            数据集名称
	 * @param createUser
	 *            创建人id
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode addDataSetInWorkFlow(Long projectId, Long dataSourceId, Long workFlowId, Long inputDataSetId,
			String dataSetName, Long createUser) {

		// 登录态获取租户和用户信息
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		String createUserName = UserInfoUtil.getUserInfoVO().getUser().getName();

		// Long tenantId = 1l;
		// String createUserName = "写死的";

		ResultCode<?> resultCode = new ResultCode<>();
		boolean isCopySuccess = false; // 复制输入表结构是否成功，如果在目标数据库成功建表则为true
		List<Map<String, Object>> tableStructures = null; // 输入表的表结构，用于写到dataset_schema表中

		logger.info("--------------------开始在workflow id ==" + workFlowId
				+ " 中添加data_set记录-----------------------------------------");
		// table_name : 对应数据源表名（mysql table、MongoDB
		// collection等）,处理节点生成的表名格式为：project key+"_"+dataSetName
		Project project = projectMapper.selectByPrimaryKey(projectId);
		String tableName = project.getProjectKey().trim() + Constants.TABLE_NAME_DELIMITER + dataSetName;

		// 输入数据集
		logger.info(
				"--------------------开始查询输入数据集信息 data_set id==" + inputDataSetId + "---------------------------------");
		Dataset inputDataSet = datasetMapper.selectByPrimaryKey(inputDataSetId);
		String inputTableName = inputDataSet.getTableName();// 输入数据集对应的数据表
		Long inputDataSourceId = inputDataSet.getDatasourceId();
		Datasource inputDataSource = datasourceMapper.selectByPrimaryKey(inputDataSourceId);
		String inputDataType = inputDataSource.getDataType(); // 输入数据源的数据类型
		String inputHost = inputDataSource.getHost(); // 输入数据源的域
		Integer inputPort = inputDataSource.getPort();// 输入数据源的端口
		String inputDbName = inputDataSource.getDb();// 输入数据源的库名
		String inputUserName = inputDataSource.getUsername();// 输入数据源的用户名
		String inputPassword = inputDataSource.getPassword();// 输入数据集的密码

		// 输出数据集
		logger.info("--------------------开始查询输出数据源信息 data_source id==" + dataSourceId
				+ "---------------------------------");
		Datasource outputDatasource = datasourceMapper.selectByPrimaryKey(dataSourceId); // 输出的数据源
		String outputDataType = outputDatasource.getDataType();// 输出的数据类型（0：MySQL、1：Oracle、2：PostgreSQL、3：MS
																// SQL
																// Server、4：HANA、5：MongoDB、6：Cassandra、7：ElasticSearch、8：Hive、9：Hbase、10：HDFS、11：HTTP、12：FTP、13：SFTP）
		String outputHost = outputDatasource.getHost();// 输出数据源的域
		Integer outputPort = outputDatasource.getPort();// 输出数据源的端口
		String outputDbName = outputDatasource.getDb();// 输出数据源的库名
		String outputUserName = outputDatasource.getUsername();// 输出数据源的用户名
		String outputPassword = outputDatasource.getPassword();// 输出数据源的密码

		switch (inputDataType) {

		/******** 输入源为mysql的情况开始 *********************************************************/
		case "0":
			MySqlUtils inputDB = new MySqlUtils(inputHost, inputPort.toString(), inputDbName, inputUserName,
					inputPassword);
			switch (outputDataType) {
			case "0":// 输出源为mysql的情况开始
				logger.info("mysql=====>mysql");
				// if (inputHost.equals(outputHost) &&
				// inputPort.equals(outputPort)) {
				// logger.info("在同一服务器下操作：host==" + inputHost + ",port==" +
				// inputPort);
				// // 在同一个mysql服务器下复制表结构，可能同一个库下复制，也可能不同库
				// MySqlUtils db = new MySqlUtils(outputHost,
				// outputPort.toString(), outputDbName, outputUserName,
				// outputPassword);
				// try {
				//
				// // 获取表结构，用于写到dataset_schema表中
				// tableStructures = db.getTableStructure(inputTableName);
				//
				// // 复制表结构
				// boolean isCreateSuccess =
				// db.createTableFromExist(outputDbName, tableName, inputDbName,
				// inputTableName);
				// if (isCreateSuccess) {
				// //添加biz_date_param和sys_time_param两个字段
				// this.addBizAndSysColumn(db, outputDbName, tableName);
				// isCopySuccess = true;
				// }
				// } catch (Exception e) {
				// e.printStackTrace();
				// } finally {
				// db.closeDB();
				// }
				// } else {

				MySqlUtils outputDB = new MySqlUtils(outputHost, outputPort.toString(), outputDbName, outputUserName,
						outputPassword);
				try {

					// 获取表结构，用于写到dataset_schema表中
					tableStructures = inputDB.getTableStructure(inputTableName);

					logger.info("inputHost==" + inputHost + ",inputPort==" + inputPort + ",outputHost==" + outputHost
							+ ",outputPort==" + outputPort);

					// 在输出源建表
					boolean isCreateSuccess = outputDB.createTableFromParamList(tableName, tableStructures);

					if (isCreateSuccess) {
						// 追加biz_date_param和sys_time_param两个字段
						this.addBizAndSysColumnInMysql(outputDB, outputDbName, tableName);
						isCopySuccess = true;
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					inputDB.closeDB();
					outputDB.closeDB();
				}
				// }
				break;// 输出源为mysql的情况结束
			case "6": // 输出源为Cassandra的情况开始
				logger.info("mysql=====>cassandra");
				// MySqlUtils inputDB = new MySqlUtils(inputHost,
				// inputPort.toString(), inputDbName, inputUserName,
				// inputPassword);
				try {

					// 获取表结构，用于复制到cassandra，并写到dataset_schema表中
					tableStructures = inputDB.getTableStructure(inputTableName);
					String primaryKey = inputDB.getTablePrimaryKey(inputTableName);

					boolean isCreateSuccess = CassandraUtil2.createTableFromMysql(outputHost, outputPort.toString(),
							outputUserName, outputPassword, outputDbName, tableName, primaryKey, tableStructures);

					if (isCreateSuccess) {
						this.addBizAndSysColumnInCassandra(outputHost, outputPort.toString(), outputUserName,
								outputPassword, outputDbName, tableName);
						isCopySuccess = true;
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					inputDB.closeDB();
				}
				break;// 输出源为Cassandra的情况结束
			case "8": // TODO 输出源为Hive的情况开始
				break;
			default:
				break;
			}
			break;
		/******** 输入源为mysql的情况结束 ***************************************************************/

		/******** 输入源为Cassandra的情况开始 *********************************************************/
		case "6": // Cassandra

			List<Map<String, String>> cassTableStructures = CassandraUtil2.getTableColumn(inputHost, inputUserName,
					inputPassword, inputDbName, inputTableName);

			// 统一加入tableStructures中
			if (null != cassTableStructures && !cassTableStructures.isEmpty()) {
				tableStructures = new ArrayList<>();
				logger.info(cassTableStructures);
				for (Map<String, String> cassTableStructure : cassTableStructures) {
					Map<String, Object> tableStructure = new HashMap<>();
					tableStructure.put("columnName", cassTableStructure.get("columnName"));
					tableStructure.put("columnType", cassTableStructure.get("columnType"));
					tableStructures.add(tableStructure);
				}
			}
			switch (outputDataType) {
			case "0":// 输出源为mysql的情况开始
				logger.info("cassandra=====>mysql");

				MySqlUtils outputDB = new MySqlUtils(outputHost, outputPort.toString(), outputDbName, outputUserName,
						outputPassword);
				try {
					// 在输出源建表
					boolean isCreateSuccess = outputDB.createTableFromParamList(tableName, tableStructures);

					if (isCreateSuccess) {
						// 追加biz_date_param和sys_time_param两个字段
						this.addBizAndSysColumnInMysql(outputDB, outputDbName, tableName);
						isCopySuccess = true;
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					outputDB.closeDB();
				}
				break; // 输出源为mysql的情况结束
			case "6": // 输出源为Cassandra的情况开始
				logger.info("cassandra=====>cassandra");
				String primaryKey = CassandraUtil2.getPrimaryKey(inputHost, inputUserName, inputPassword, inputDbName,
						inputTableName);

				boolean isCreateSuccess = CassandraUtil2.createTableFromParamList(outputHost, outputPort.toString(),
						outputUserName, outputPassword, outputDbName, tableName, primaryKey, tableStructures);

				if (isCreateSuccess) {
					this.addBizAndSysColumnInCassandra(outputHost, outputPort.toString(), outputUserName,
							outputPassword, outputDbName, tableName);
					isCopySuccess = true;
				}
				break;// 输出源为Cassandra的情况结束
			case "8": // TODO 输出源为Hive的情况开始
				break;
			default:
				break;
			}
			break;
		/******** 输入源为Cassandra的情况结束 *********************************************************/
		case "8": // Hive
			break;
		default:
			break;
		}

		if (isCopySuccess) {
			// 建表成功，dataset插入数据
			Dataset dataset = this.newDataSet(projectId, dataSourceId, dataSetName, tableName, "1", "0", createUser);
			logger.info("--------------------开始向数据库中添加data_set记录---------------------------------");
			datasetMapper.insertSelective(dataset);
			logger.info("--------------------data_set新添记录：" + dataset);

			// 拿到表结构并存入dataset_schema表中
			logger.info("--------------------开始解析表结构并存入dataset_schema表中---------------------------------");
			if (null != dataset.getId() && null != tableStructures && !tableStructures.isEmpty()) {
				for (Map<String, Object> tableStructure : tableStructures) {
					String columnName = (String) tableStructure.get("columnName");
					if (!"biz_date_param".equals(columnName) && !"sys_time_param".equals(columnName)) { // 剔除biz_date_param和sys_time_param两个字段
						DatasetSchema datasetSchema = new DatasetSchema();
						datasetSchema.setDatasetId(dataset.getId());

						switch (inputDataType) {
						case "0":// mysql
							datasetSchema
									.setColumnType(TypeUtil.mysql2DataSet((String) tableStructure.get("columnType")));
							break;
						case "6":// cassandra
							datasetSchema.setColumnType(
									TypeUtil.cassandra2DataSet((String) tableStructure.get("columnType")));
							break;
						case "8":// hive
							break;
						}
						datasetSchema.setColumnName(columnName);
						datasetSchema.setMeaning("0"); // 语义默认设置为自动检测
						datasetSchemaMapper.insertSelective(datasetSchema);
						logger.info("--------------------dataset_schema新添表字段记录：" + datasetSchema.getColumnName());
					}
				}
			}

			// 添加时间轴事件
			if (null != dataset.getId()) {
				logger.info("--------------------开始添加时间轴事件project_timeline---------------------------------");
				projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.CREATED,
						OperateObjectTypeConstants.DATASET, getOperateDatasetType(outputDataType), dataset.getId(),
						dataSetName, createUser, createUserName);
			}

			resultCode = ResultUtil.success(dataset.getId());
		}
		logger.info("--------------------完成！在workflow id ==" + workFlowId
				+ " 中添加data_set记录-----------------------------------------");

		return resultCode;
	}

	/**
	 * 获取dataset列表，可用dataset name过滤，或用workFlowId过滤,更新时间倒序，不分页
	 *
	 * @param projectId
	 *            项目id
	 * @param filter
	 *            根据数据集名称过滤
	 * @param workFlowId
	 *            根据工作流过滤
	 * @return
	 */
	@Override
	public ResultCode getDataSetList(Long projectId, String filter, Long workFlowId) {
		logger.info("开始获取dataset列表，projectId==" + projectId + ",filter==" + filter + ",workFlowId==" + workFlowId);
		List<DataSetListVO> list = datasetMapper2.getDataSetList(projectId, filter, workFlowId);
		logger.info("查询完成！");
		return ResultUtil.success(list);
	}

	/**
	 * 添加数据集前，需要拉出数据源下的所有的表名
	 *
	 * @param dataSourceId
	 * @return
	 */
	@Override
	public ResultCode getTableNamesFromDataSource(Long dataSourceId) {
		ResultCode resultCode = null;
		Datasource datasource = datasourceMapper.selectByPrimaryKey(dataSourceId);
		String dataType = datasource.getDataType(); // 数据源的数据类型
		String host = datasource.getHost(); // 数据源的域
		Integer port = datasource.getPort();// 数据源的端口
		String dbName = datasource.getDb();// 数据源的库名
		String userName = datasource.getUsername();// 数据源的用户名
		String password = datasource.getPassword();// 数据集的密码
		switch (dataType) {
		case "0": // mysql
			// 获取所有mysql表名称
			List<String> mysqlTableNames = MySqlUtils2.getTableNames(host, port.toString(), dbName, userName, password);
			// 获取mysql非原生表名称
			List<String> mysqlTableNameList = spliceTableName(mysqlTableNames);
			mysqlTableNames.removeAll(mysqlTableNameList);
			// 获取原生表名称
			resultCode = ResultUtil.success(mysqlTableNames);
			break;
		case "6": // Cassandra
			List<String> cassandraTableNames = CassandraUtil2.getTablesNameByDBName(host, userName, password, dbName);
			List<String> cassandraTableNameList = spliceTableName(cassandraTableNames);
			cassandraTableNames.removeAll(cassandraTableNameList);
			resultCode = ResultUtil.success(cassandraTableNames);
			break;
		case "8": // Hive
			break;
		default:
			break;
		}
		return resultCode;
	}

	/**
	 * 截取非原生表名称
	 * @param tableNames
	 * @return
	 */
	private List<String> spliceTableName(List<String> tableNames) {
		List<String> tableNameList = new ArrayList<>();
		List<Project> projectList = projectMapper.selectByExample(null);
		for(String tableName:tableNames){
			for(Project project:projectList){
				if(null != tableName){
					if(tableName.contains(project.getProjectKey())){
						tableNameList.add(tableName);
					}
				}
            }
        }
		return tableNameList;
	}

	/**
	 * 获取数据集对应的源表的数据数量，和同步时间
	 *
	 * @param dataSetId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode getDataSetDataCount(Long dataSetId) {
		logger.info("--------------------开始查询数据集数据数量 data_set id==" + dataSetId + "---------------------------------");

		ResultCode resultCode = null;
		// 查询数据集对应的源表配置
		Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
		String tableName = dataset.getTableName();// 数据集对应的数据表
		Long datasourceId = dataset.getDatasourceId();
		Datasource datasource = datasourceMapper.selectByPrimaryKey(datasourceId);
		String dataType = datasource.getDataType(); // 数据源的数据类型
		String host = datasource.getHost(); // 数据源的域
		Integer port = datasource.getPort();// 数据源的端口
		String dbName = datasource.getDb();// 数据源的库名
		String userName = datasource.getUsername();// 数据源的用户名
		String password = datasource.getPassword();// 数据集的密码

		long count = 0;
		switch (dataType) {
		case "0": // mysql

			MySqlUtils db = new MySqlUtils(host, port.toString(), dbName, userName, password);
			StringBuffer sql = new StringBuffer();
			sql.append("select count(*) from ");
			sql.append(tableName);
			List<Map<String, Object>> list = null;
			try {
				list = db.executeQuery(sql.toString(), null);
				if (null != list && !list.isEmpty()) {
					count = (long) list.get(0).get("count(*)");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				resultCode = ResultUtil.error(Messages.GET_DATASET_DATA_COUNT_FAILED_CODE,
						Messages.GET_DATASET_DATA_COUNT_FAILED_MSG);
				return resultCode;
			} finally {
				db.closeDB();
			}
			break;
		case "6": // Cassandra
			StringBuffer cql = new StringBuffer();
			cql.append("select count(*) from ");
			cql.append(tableName);
			List<Map<String, Object>> cassResult = null;
			cassResult = CassandraUtil2.executeQueryCql(host, port.toString(), userName, password, dbName,
					cql.toString());
			count = (long) cassResult.get(0).get("count");
			break;
		case "8": // Hive
			break;
		default:
			break;
		}

		Date syncTime = new Date();
		Map<String, Object> res = new HashMap<>();
		res.put("dataCount", count);
		res.put("syncTime", syncTime);
		dataset.setDataCount(count);
		dataset.setSyncTime(syncTime);
		logger.info("--------------------开始更新数据集sync_time字段 data_set id==" + dataSetId
				+ "---------------------------------");
		datasetMapper.updateByPrimaryKeySelective(dataset);
		resultCode = ResultUtil.success(res);

		return resultCode;
	}

	/**
	 * 获取数据集详情
	 *
	 * @param dataSetId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode getDataSetDetail(Long dataSetId) {
		logger.info("--------------------开始查询数据集详情 data_set id==" + dataSetId + "---------------------------------");

		// 查询基本详情，包括数据集的id,name,update_time,create_name,data_type,description,以及所在的workflow的名称
		DataSetDetailVO dataSetDetail = datasetMapper2.getDataSetDetail(dataSetId);
		if (dataSetDetail == null) {
			return ResultUtil.error(Messages.GET_DATASET_DETAIL_FAILED_CODE, Messages.GET_DATASET_DETAIL_FAILED_MSG);
		}
		// todo 查询创建人姓名以及最后修改人姓名
		// Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
		// Long createUserId = dataset.getCreateUser();
		// Long updateUserId = dataset.getUpdateUser();
		// ResultCode s = userService.getInfo(createUserId);
		String createUserName = "小明";
		String updateUserName = "xiaoming";

		dataSetDetail.setCreateUserName(createUserName);
		dataSetDetail.setUpdateUserName(updateUserName);

		// //获取数据集对应的源表的数据数量，和同步时间
		// Map<String, Object> map = (Map<String, Object>)
		// getDataSetDataCount(dataSetId).getData();
		// dataSetDetail.setDataCount((Long) map.get("dataCount"));
		// dataSetDetail.setSyncTime((Date) map.get("syncTime"));

		// 获取该dataset对应节点的所有父节点和子节点列表
		List<WorkFlowNodeVO> workflowNodes = new ArrayList<>();
		WorkFlowNodeVO pNode = workflowNodeMapper2.getDataSetPNode(dataSetId);// 查询父节点
		if (null != pNode) {
			pNode.setIsParent(true);
			workflowNodes.add(pNode);
		}
		List<WorkFlowNodeVO> cNodes = workflowNodeMapper2.getDataSetCNode(dataSetId);// 查询子节点
		if (null != cNodes && !cNodes.isEmpty()) {
			for (WorkFlowNodeVO cNode : cNodes) {
				cNode.setIsParent(false);
			}
			workflowNodes.addAll(cNodes);
		}
		dataSetDetail.setWorkflowNodes(workflowNodes);

		return ResultUtil.success(dataSetDetail);
	}

	@Override
	public ResultCode getSingleDetailById(Long dataSetId) {
		ResultCode<Dataset> resultCode = new ResultCode<>();
		Dataset datasetVO = datasetMapper.selectByPrimaryKey(dataSetId);
		if (datasetVO == null) {
			return ResultUtil.error(Messages.GET_DATASET_DETAIL_FAILED_CODE, Messages.GET_DATASET_DETAIL_FAILED_MSG);
		}
		resultCode.setData(datasetVO);
		return resultCode;
	}

	/**
	 * 删除数据集,批量,逻辑删除
	 *
	 * @param dataSetIds
	 *            数据集id，多个以逗号隔开
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode deleteDataSet(String dataSetIds) {

		// 登录态获取租户和用户信息
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
		String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
		String createUserName = UserInfoUtil.getUserInfoVO().getUser().getName();

		logger.info("开始删除数据集集合：dataSetIds==" + dataSetIds);
		if (StringUtils.isNotBlank(dataSetIds)) {
			String[] dataSetIdStr = dataSetIds.split(",");
			for (String dataSetId : dataSetIdStr) {
				if (StringUtils.isNotBlank(dataSetId)) {
					Dataset dataset = datasetMapper.selectByPrimaryKey(Long.valueOf(dataSetId));
					if (dataset == null) {
						continue;
					}
					String dataSetName = dataset.getDatasetName();
					String sourceTableName = dataset.getTableName();
					Long projectId = dataset.getProjectId();
					Long datasourceId = dataset.getDatasourceId();

					Datasource datasource = datasourceMapper.selectByPrimaryKey(datasourceId);
					String dataType = datasource.getDataType(); // 数据源的数据类型

					// 数据集对应的mongo
					// collection命名规则：'proj_'+project_id+'_ds_'+dataset_id
					String mongoCollectionName = Constants.MONGO_COL_NAME_PREFIX_PROJ + projectId
							+ Constants.MONGO_COL_NAME_PREFIX_DS + dataSetId;
					logger.info("开始删除mongo collection ==" + mongoCollectionName);
					MongoUtils mongodb = MongoUtils.getInstance();
					if (mongodb.isCollectionExist(mongoCollectionName)) {
						mongodb.dropCollection(mongoCollectionName);
						mongodb.close();
					}

					Project project = projectMapper.selectByPrimaryKey(projectId);
					if (sourceTableName.startsWith(project.getProjectKey().trim() + Constants.TABLE_NAME_DELIMITER)) {
						logger.info("开始删除数据集对应的源表：tableName==" + sourceTableName);
						this.deleteSourceTable(Long.valueOf(dataSetId));
					}

					logger.info("开始删除数据集：dataSetId==" + dataSetId);
					datasetMapper.deleteByPrimaryKey(Long.valueOf(dataSetId));

					DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
					DatasetSchemaExample.Criteria schemaCriteria = datasetSchemaExample.createCriteria();
					schemaCriteria.andDatasetIdEqualTo(Long.valueOf(dataSetId));
					logger.info("开始删除数据集结构：dataSetId==" + dataSetId);
					datasetSchemaMapper.deleteByExample(datasetSchemaExample);

					DatasetFilterExample datasetFilterExample = new DatasetFilterExample();
					DatasetFilterExample.Criteria filterCriteria = datasetFilterExample.createCriteria();
					filterCriteria.andDatasetIdEqualTo(Long.valueOf(dataSetId));
					logger.info("开始删除数据集过滤条件：dataSetId==" + dataSetId);
					datasetFilterMapper.deleteByExample(datasetFilterExample);

					logger.info("开始解除数据集相关联工作流关系：dataSetId==" + dataSetId);
					WorkflowNodeExample example = new WorkflowNodeExample();
					example.createCriteria().andDatasetIdEqualTo(Long.parseLong(dataSetId));
					List<WorkflowNode> workflowNodes = workflowNodeMapper.selectByExample(example);
					if (!workflowNodes.isEmpty()) {
						Long nodeId = workflowNodes.get(0).getId();
						workflowService.delNode(nodeId, tenantId, createUser, userName, projectId);
					}

					// 添加时间轴事件
					logger.info("--------------------开始添加时间轴事件project_timeline---------------------------------");
					projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.DELETED,
							OperateObjectTypeConstants.DATASET, getOperateDatasetType(dataType), dataset.getId(),
							dataSetName, createUser, createUserName);
				}
			}
		}

		logger.info("删除成功！");
		return ResultUtil.success();
	}

	/**
	 * 测试数据集连接,从源数据表中拉取100条数据做预览
	 *
	 * @param dataSourceId
	 * @param tableName
	 * @return
	 */
	@Override
	public ResultCode testConnect(Long dataSourceId, String tableName, Long dataSetId) {
		logger.info("开始进行数据集测试");
		ResultCode resultCode = null;
		Map<String, Object> result = new HashMap<>();
		List<Map<String, Object>> datas = null;
		List<Map<String, Object>> structure = null;

		Datasource datasource = datasourceMapper.selectByPrimaryKey(dataSourceId);
		String dataType = datasource.getDataType(); // 数据源的数据类型
		String host = datasource.getHost(); // 数据源的域
		Integer port = datasource.getPort();// 数据源的端口
		String dbName = datasource.getDb();// 数据源的库名
		String userName = datasource.getUsername();// 数据源的用户名
		String password = datasource.getPassword();// 数据集的密码
		switch (dataType) {
		case "0": // mysql
			logger.info("数据类型为：mysql");
			MySqlUtils db = new MySqlUtils(host, port.toString(), dbName, userName, password);
			StringBuffer sql = new StringBuffer();
			sql.append("select * from ");
			sql.append(tableName);
			sql.append(" limit 100");
			try {
				datas = db.executeQuery(sql.toString(), null); // 100条数据
				if (null == dataSetId) {
					structure = db.getTableStructure(tableName); // 表结构，包括序号、字段名、映射后的字段类型
				}
			} catch (MySQLSyntaxErrorException e1) {
				String s = e1.getMessage();
				resultCode = ResultUtil.error(Messages.TABLE_NOT_EXIST_CODE, s);
				return resultCode;
			} catch (SQLException e) {
				e.printStackTrace();
				resultCode = ResultUtil.error(Messages.DATA_SET_TEST_CONN_FAILED_CODE,
						Messages.DATA_SET_TEST_CONN_FAILED_MSG);
				return resultCode;
			} finally {
				db.closeDB();
			}
			break;
		case "6": // Cassandra
			logger.info("数据类型为：cassandra");
			StringBuffer cql = new StringBuffer();
			cql.append("select * from ");
			cql.append(tableName);
			cql.append(" limit 100");
			datas = CassandraUtil2.executeQueryCql(host, port.toString(), userName, password, dbName, cql.toString());
			if (null == dataSetId) {
				structure = CassandraUtil2.getTableStructure(host, port.toString(), userName, password, dbName,
						tableName); // 表结构，包括序号、字段名、映射后的字段类型
			}
			break;
		case "8": // Hive
			break;
		default:
			break;
		}

		if (null != structure && !structure.isEmpty()) {
			for (Map<String, Object> map : structure) {
				String columnType = (String) map.get("columnType");
				switch (dataType) {
				case "0": // mysql
					map.put("columnType", TypeUtil.mysql2DataSet(columnType));
					break;
				case "6": // Cassandra
					map.put("columnType", TypeUtil.cassandra2DataSet(columnType));
					break;
				case "8": // Hive
					break;
				default:
					break;
				}
				String[] meanings = { "0" };
				map.put("meanings", meanings);
			}
			result.put("tableData", datas);
			result.put("tableStructure", structure);
			resultCode = ResultUtil.success(result);
		} else if (null != dataSetId) {
			List<DatasetSchemaVO> schemas = (List<DatasetSchemaVO>) dataSetSchemaService
					.getSchema(dataSetId, null, null, null, null).getData();
			result.put("tableData", datas);
			result.put("tableStructure", schemas);
			resultCode = ResultUtil.success(result);
		} else {
			resultCode = ResultUtil.error(12345, "出错了");
		}
		return resultCode;
	}

	/**
	 * 获取源表结构信息,包括字段名,字段类型,类型长度等 忽略掉biz_date_param和sys_time_param两个字段
	 *
	 * @param dataSourceId
	 * @param tableName
	 * @return
	 */
	@Override
	public ResultCode getSourceTableStructure(Long dataSourceId, String tableName) {
		ResultCode resultCode = null;
		List<Map<String, Object>> tableStructure = new ArrayList<>();

		Datasource datasource = datasourceMapper.selectByPrimaryKey(dataSourceId);
		String dataType = datasource.getDataType(); // 数据源的数据类型
		String host = datasource.getHost(); // 数据源的域
		Integer port = datasource.getPort();// 数据源的端口
		String dbName = datasource.getDb();// 数据源的库名
		String userName = datasource.getUsername();// 数据源的用户名
		String password = datasource.getPassword();// 数据集的密码
		switch (dataType) {
		case "0": // mysql

			MySqlUtils db = new MySqlUtils(host, port.toString(), dbName, userName, password);

			List<Map<String, Object>> mysqlTableStructure = null;
			try {
				mysqlTableStructure = db.getTableStructure(tableName);
				if (null != mysqlTableStructure && !mysqlTableStructure.isEmpty()) {
					for (Map<String, Object> map : mysqlTableStructure) {
						String columnName = (String) map.get("columnName");
						if (!columnName.equals("biz_date_param") && !columnName.equals("sys_time_param")) {
							String columnType = (String) map.get("columnType");
							map.put("columnType", TypeUtil.mysql2DataSet(columnType));
							tableStructure.add(map);
						}
					}
					resultCode = ResultUtil.success(tableStructure);
				} else {

				}
			} catch (SQLException e) {
				e.printStackTrace();
				resultCode = ResultUtil.error(Messages.DATA_SET_TEST_CONN_FAILED_CODE,
						Messages.DATA_SET_TEST_CONN_FAILED_MSG);
			} finally {
				db.closeDB();
			}
			break;
		case "6": // Cassandra
			List<Map<String, Object>> cassTableStructure = CassandraUtil2.getTableStructure(host, port.toString(),
					userName, password, dbName, tableName);
			if (null != cassTableStructure && !cassTableStructure.isEmpty()) {
				for (Map<String, Object> map : cassTableStructure) {
					String columnName = (String) map.get("columnName");
					if (!columnName.equals("biz_date_param") && !columnName.equals("sys_time_param")) {
						String columnType = (String) map.get("columnType");
						map.put("columnType", TypeUtil.cassandra2DataSet(columnType));
						tableStructure.add(map);
					}
				}
				resultCode = ResultUtil.success(tableStructure);
			}
			break;
		case "8": // Hive
			break;
		default:
			break;
		}
		return resultCode;
	}

	/**
	 * 清除数据集数据,删除数据,保留表结构
	 *
	 * @param dataSetId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode clearData(Long dataSetId) {

		// 登录态获取租户和用户信息
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
		String createUserName = UserInfoUtil.getUserInfoVO().getUser().getName();

		ResultCode resultCode = null;
		// 查询数据集对应的源表配置
		logger.info("--------------------开始查询数据集信息 data_set id==" + dataSetId + "---------------------------------");
		Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
		String tableName = dataset.getTableName();// 数据集对应的数据表
		String dataSetName = dataset.getDatasetName();
		String isWrite = dataset.getIsWrite(); // 是否可写,（0：否、1：是）
		Long projectId = dataset.getProjectId();
		Project project = projectMapper.selectByPrimaryKey(projectId);

		if ("0".equals(isWrite)
				|| !tableName.startsWith(project.getProjectKey().trim() + Constants.TABLE_NAME_DELIMITER)) {
			resultCode = ResultUtil.error(Messages.DATASET_NOT_WRITED_CODE, Messages.DATASET_NOT_WRITED_MSG);
		} else {
			Long datasourceId = dataset.getDatasourceId();
			Datasource datasource = datasourceMapper.selectByPrimaryKey(datasourceId);
			String dataType = datasource.getDataType(); // 数据源的数据类型
			String host = datasource.getHost(); // 数据源的域
			Integer port = datasource.getPort();// 数据源的端口
			String dbName = datasource.getDb();// 数据源的库名
			String userName = datasource.getUsername();// 数据源的用户名
			String password = datasource.getPassword();// 数据集的密码

			switch (dataType) {
			case "0": // mysql

				MySqlUtils db = new MySqlUtils(host, port.toString(), dbName, userName, password);
				StringBuffer countSql = new StringBuffer();
				countSql.append("select count(*) from ");
				countSql.append(tableName);
				StringBuffer deleteSql = new StringBuffer();
				deleteSql.append("delete from ");
				deleteSql.append(tableName);

				List<Map<String, Object>> list = null;

				try {
					long count = 0;
					list = db.executeQuery(countSql.toString(), null);
					if (null != list && !list.isEmpty()) {
						count = (long) list.get(0).get("count(*)");
					}
					if (0 != count) {
						boolean res = db.executeUpdate(deleteSql.toString(), null);
						resultCode = res ? ResultUtil.success()
								: ResultUtil.error(Messages.DATASET_CLEAR_FAILED_CODE,
										Messages.DATASET_CLEAR_FAILED_MSG);
					} else {
						resultCode = ResultUtil.success();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					db.closeDB();
				}
				break;
			case "6": // Cassandra
				StringBuffer cql = new StringBuffer();
				cql.append("Truncate ");
				cql.append(tableName);
				cql.append(";");
				CassandraUtil2.executeCql(host, port.toString(), userName, password, dbName, cql.toString());
				resultCode = ResultUtil.success();
				break;
			case "8": // Hive
				break;
			default:
				break;
			}
			// 添加时间轴事件
			logger.info("--------------------开始添加时间轴事件project_timeline---------------------------------");
			projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
					OperateObjectTypeConstants.DATASET, getOperateDatasetType(dataType), dataset.getId(), dataSetName,
					createUser, createUserName);
			// 清除数据后dataset的is_sync字段改为1
			dataset.setIsSync("1");
			datasetMapper.updateByPrimaryKey(dataset);
		}
		return resultCode;
	}

	/**
	 * 查询输入数据集列表
	 *
	 * @param projectId
	 * @param workflowId
	 * @param page
	 * @param pageSize
	 * @param filter
	 * @return
	 */
	@Override
	public ResultCode inputList(Long projectId, Long workflowId, int page, int pageSize, String filter) {
		ResultCode resultCode;
		// 获取租户信息
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		List<Long> inputDatasetIds = getInputDatasetList(tenantId, projectId, workflowId);
		// fix bug wanghao 2018年7月11日 返回的id列表可能为空
		int start = (page - 1) * pageSize;
		List<DataSetListVO> inputList = null;
		List<DataSetListVO> inputListTotal = null;
		if (inputDatasetIds.size() > 0) {
			inputListTotal = datasetMapper2.getDatasetInfo1(inputDatasetIds, filter);
			inputList = datasetMapper2.getDatasetInfo(inputDatasetIds, filter, start, pageSize);
		}
		ListResult<DataSetListVO> returnList = new ListResult<>();
		returnList.setListData(inputList);
		returnList.setPageSize(pageSize);
		returnList.setPageNum(page);
		returnList.setTotalNum(inputListTotal.size());
		resultCode = ResultUtil.success(returnList);
		return resultCode;
	}

	/**
	 * 查询输出数据集列表
	 *
	 * @param projectId
	 * @param workflowId
	 * @param page
	 * @param pageSize
	 * @param filter
	 * @return
	 */
	@Override
	public ResultCode outputList(Long projectId, Long workflowId, int page, int pageSize,
			String filter) {
		ResultCode resultCode;
		// 获取租户信息
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		int start = (page - 1) * pageSize;

		Project project = projectMapper.selectByPrimaryKey(projectId);
		String projectKey = project.getProjectKey();
		// 获取输出数据集totalNumber
		List<DataSetListVO> dataSetListVOSCount = datasetMapper2.getOutputDatasetList1(projectId, projectKey, workflowId,
				filter);

		List<DataSetListVO> dataSetListVOS = datasetMapper2.getOutputDatasetList(projectId, projectKey, workflowId,
				filter, start, pageSize);
		ListResult<DataSetListVO> returnList = new ListResult<>();
		returnList.setListData(dataSetListVOS);
		returnList.setPageSize(pageSize);
		returnList.setPageNum(page);
		returnList.setTotalNum(dataSetListVOSCount.size());
		resultCode = ResultUtil.success(returnList);
		return resultCode;
	}

	/**
	 * 查询数据集对应的数据源及数据表信息
	 *
	 * @param dataSetId
	 * @return { "code":0, "message":success, "data":{ "datasourceId" : 123,
	 *         "datasourceName": "connection1", "tableName": "admin" } }
	 */
	@Override
	public ResultCode getDatasourceInfo(Long dataSetId) {
		Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
		String tableName = dataset.getTableName();
		Long datasourceId = dataset.getDatasourceId();
		Datasource datasource = datasourceMapper.selectByPrimaryKey(datasourceId);
		String datasourceName = datasource.getConnName();

		HashMap<String, Object> result = new HashMap<>();
		result.put("datasourceId", datasourceId);
		result.put("datasourceName", datasourceName);
		result.put("tableName", tableName);

		return ResultUtil.success(result);
	}

	/**
	 * 检查数据集名称是否重复，同一project下或同一datasource下的数据集名称都不能重复
	 *
	 * @param datasetName
	 * @param datasourceId
	 * @param projectId
	 * @return 0 不重复 1 project下已有同名dataset 2 datasource下已有同名dataset
	 */
	@Override
	public ResultCode isDatasetNameExist(String datasetName, Long datasourceId, Long projectId) {

		DatasetExample datasetExample1 = new DatasetExample();
		DatasetExample.Criteria criteria1 = datasetExample1.createCriteria();
		criteria1.andProjectIdEqualTo(projectId);
		criteria1.andDatasetNameEqualTo(datasetName);
		List<Dataset> list1 = datasetMapper.selectByExample(datasetExample1);
		if (!list1.isEmpty()) {
			// project下已有同名dataset
			return ResultUtil.success(1);
		}

		DatasetExample datasetExample2 = new DatasetExample();
		DatasetExample.Criteria criteria2 = datasetExample2.createCriteria();
		criteria2.andDatasetNameEqualTo(datasetName);
		criteria2.andDatasourceIdEqualTo(datasourceId);
		List<Dataset> list2 = datasetMapper.selectByExample(datasetExample2);
		if (!list2.isEmpty()) {
			// datasource下已有同名dataset
			return ResultUtil.success(2);
		}
		// 没有同名dataset
		return ResultUtil.success(0);
	}

	/**
	 * 查询输入数据集id列表
	 *
	 * @param tenantId
	 * @param projectId
	 * @return
	 */
	private List<Long> getInputDatasetList(Long tenantId, Long projectId, Long workflowId) {
		// 查询项目下其他工作流使用过的数据集
		List<Long> datasetId = workflowNodeMapper2.getDatasetId(tenantId, projectId, workflowId);
		// 查询项目下的数据集
		DatasetExample datasetExample = new DatasetExample();
		datasetExample.createCriteria().andProjectIdEqualTo(projectId);
		List<Dataset> datasets = datasetMapper.selectByExample(datasetExample);
		// 查询项目下所有有效的数据集
		List<DataSetListVO> validList = datasetMapper2.getDataSetList(projectId, null, null);
		// 查询项目下所有有效数据集的ID
		List<Long> validIDs = new ArrayList<>();
		for (DataSetListVO validDataSet : validList) {
			validIDs.add(validDataSet.getId());
		}
		// 获取有效数据实体
		List<Long> datasetIds = new ArrayList<>();
		for (Dataset dataset : datasets) {
			if (validIDs.contains(dataset.getId())) {
				datasetIds.add(dataset.getId());
			}
		}
		// 过滤使用过的数据集
		datasetIds.removeAll(datasetId);
		return datasetIds;
	}

	/**
	 * myqsl中追加biz_date_param和sys_time_param两个字段，如果表结构中已包含这两个字段，则不追加
	 *
	 * @param db
	 * @param dbName
	 * @param tableName
	 * @throws SQLException
	 */
	public void addBizAndSysColumnInMysql(MySqlUtils db, String dbName, String tableName) throws SQLException {

		// 是否需要追加biz_date_param和sys_time_param两个字段
		boolean needAdd = true;
		List<Map<String, Object>> copyedTableStructures = db.getTableStructure(tableName);
		for (Map<String, Object> copyedTableStructure : copyedTableStructures) {
			if (copyedTableStructure.containsValue("biz_date_param")
					|| copyedTableStructure.containsValue("sys_time_param")) {
				needAdd = false;
				break;
			}
		}

		if (needAdd) {
			logger.info("开始追加biz_date_param和sys_time_param两个字段");
			// 添加 biz_date_param和sys_time_param两个字段
			StringBuffer addColumn = new StringBuffer();
			addColumn.append("ALTER TABLE ");
			addColumn.append("`" + dbName + "`");
			addColumn.append(".");
			addColumn.append("`" + tableName + "` ");
			addColumn.append(
					"ADD COLUMN `biz_date_param` varchar(20) NOT NULL, ADD COLUMN `sys_time_param` VARCHAR(20) NOT NULL AFTER `biz_date_param`;");
			db.executeCreateTableSQL(addColumn.toString());
		}
	}

	/**
	 * cassandra中追加biz_date_param和sys_time_param两个字段，如果表结构中已包含这两个字段，则不追加
	 *
	 * @param dbName
	 * @param tableName
	 * @throws SQLException
	 */
	public void addBizAndSysColumnInCassandra(String host, String port, String userName, String password, String dbName,
			String tableName) {

		// 是否需要追加biz_date_param和sys_time_param两个字段
		boolean needAdd = true;
		List<Map<String, String>> copyedTableStructures = CassandraUtil2.getTableColumn(host, userName, password,
				dbName, tableName);
		for (Map<String, String> copyedTableStructure : copyedTableStructures) {
			if (copyedTableStructure.containsValue("biz_date_param")
					|| copyedTableStructure.containsValue("sys_time_param")) {
				needAdd = false;
				break;
			}
		}

		if (needAdd) {
			logger.info("开始追加biz_date_param和sys_time_param两个字段");
			// 语法：e.g.：ALTER TABLE student ADD student_email text;
			// 添加 biz_date_param和sys_time_param两个字段
			StringBuffer addColumn1 = new StringBuffer();
			addColumn1.append("ALTER TABLE ");
			addColumn1.append(tableName);
			addColumn1.append(" ADD biz_date_param varchar");
			CassandraUtil2.executeCql(host, port, userName, password, dbName, addColumn1.toString());
			StringBuffer addColumn2 = new StringBuffer();
			addColumn2.append("ALTER TABLE ");
			addColumn2.append(tableName);
			addColumn2.append(" ADD sys_time_param varchar");
			CassandraUtil2.executeCql(host, port, userName, password, dbName, addColumn2.toString());
		}
	}

	/**
	 * 删除数据集对应的源表
	 *
	 * @param dataSetId
	 */
	public void deleteSourceTable(Long dataSetId) {
		if (null != dataSetId) {
			Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
			Long datasourceId = dataset.getDatasourceId();
			String tableName = dataset.getTableName();

			Datasource datasource = datasourceMapper.selectByPrimaryKey(datasourceId);
			String dataType = datasource.getDataType(); // 数据源的数据类型
			String host = datasource.getHost(); // 数据源的域
			Integer port = datasource.getPort();// 数据源的端口
			String dbName = datasource.getDb();// 数据源的库名
			String userName = datasource.getUsername();// 数据源的用户名
			String password = datasource.getPassword();// 数据集的密码

			switch (dataType) {
			case "0": // mysql

				MySqlUtils db = new MySqlUtils(host, port.toString(), dbName, userName, password);
				// sql e.g.: DROP TABLE IF EXISTS `dataset`
				StringBuffer deleteSql = new StringBuffer();
				deleteSql.append("DROP TABLE IF EXISTS `");
				deleteSql.append(tableName);
				deleteSql.append("`");
				try {
					db.executeUpdate(deleteSql.toString(), null);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					db.closeDB();
				}
				break;
			case "6": // Cassandra
				// cql e.g.: DROP TABLE IF EXISTS dataset
				StringBuffer deleteCql = new StringBuffer();
				deleteCql.append("DROP TABLE IF EXISTS ");
				deleteCql.append(tableName);
				deleteCql.append(";");
				CassandraUtil2.executeCql(host, port.toString(), userName, password, dbName, deleteCql.toString());
				break;
			case "8": // Hive
				break;
			default:
				break;
			}
		}
	}

	private static String getOperateDatasetType(String dataType) {
		String operateObjectType = ""; // 用于时间轴操作目标的子类型，对应类
										// OperateObjectTypeConstants中定义的类型
		switch (dataType) {
		case "0": // mysql
			operateObjectType = "1";
			break;
		case "6": // cassandra
			operateObjectType = "0";
			break;
		case "8": // hive
			operateObjectType = "2";
			break;
		}
		return operateObjectType;
	}

	/**
	 * 新建dataset
	 *
	 * @param projectId
	 * @param dataSourceId
	 * @param dataSetName
	 * @param tableName
	 * @param isWrite
	 * @param createUser
	 * @return
	 */
	private Dataset newDataSet(Long projectId, Long dataSourceId, String dataSetName, String tableName, String isWrite,
			String isFloatToInt, Long createUser) {

		Dataset dataset = new Dataset();
		dataset.setCreateTime(new Date());
		dataset.setCreateUser(createUser);
		dataset.setDatasetName(dataSetName);
		dataset.setDatasourceId(dataSourceId);
		dataset.setProjectId(projectId);
		dataset.setUpdateUser(createUser);
		dataset.setUpdateTime(new Date());
		dataset.setTableName(tableName);
		dataset.setIsFloatToInt(isFloatToInt);

		// is_write : 新建的数据集，直接可写，不再根据对应数据源的is_write字段确定
		dataset.setIsWrite(isWrite);
		// dataset.setIsDel("0");

		// 以下非空字段在添加时使用默认值
		// is_sync：是否需要同步数据（0：否、1：是）默认0
		// is_float_to_int : 是否浮点型转整型（0：否、1：是），默认0
		// sample_type字段：采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））默认0
		// sample_type_value字段：采样方式对应值（采样个数、采样比例）默认采样个数10000
		// is_sample_filter字段：是否采样过滤（0：否、1：是）默认0
		// sample_filter_type：采样过滤方式（0：以下情况均符合，1：至少有一种情况符合）默认null
		// sync_time:同步时间，默认为null

		// datasetMapper.insertSelective(dataset);

		return dataset;
	}
}
