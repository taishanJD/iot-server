package com.quarkdata.data.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.quarkdata.data.model.dataobj.*;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCursor;
import com.quarkdata.data.dal.dao.DatasetMapper;
import com.quarkdata.data.dal.dao.DatasetSchemaMapper;
import com.quarkdata.data.dal.dao.DatasourceMapper;
import com.quarkdata.data.dal.dao.WorkflowNodeMapper;
import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.vo.AddNodeRelVO;
import com.quarkdata.data.model.vo.DatasetNode;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.model.vo.ProcessNode;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.service.PreprocessService;
import com.quarkdata.data.service.PreviewService;
import com.quarkdata.data.service.WorkflowService;
import com.quarkdata.data.util.RegexValidation;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.util.StringUtils;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;
import com.quarkdata.data.util.common.preprocess.PreporocessFactory;
import com.quarkdata.data.util.db.MongoUtils;
import com.quarkdata.data.util.db.MySqlUtils;

@Service
public class PreprocessServiceImpl implements PreprocessService {

	@Autowired
	private WorkflowNodeMapper workflowNodeMapper;

	@Autowired
	private DatasetMapper datasetMapper;

	@Autowired
	private DatasetSchemaMapper datasetSchemaMapper;

	@Autowired
	private PreviewService previewService;

	@Autowired
	private WorkflowService workflowService;

	@Autowired
	private DatasourceMapper datasourceMapper;

	@Autowired
	private DataSetService DataSetService;

	@Override
	public ResultCode getPreprocessList(Map<String, Object> paramMap) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long dataSetId = Long.parseLong(paramMap.get("dataSetId") + "");
		Long nodeId = null;
		if (paramMap.get("nodeId") != null) {
			nodeId = Long.parseLong(paramMap.get("nodeId") + "");
		}
		Integer page = Integer.parseInt((String) paramMap.get("page"));
		Integer pageSize = Integer.parseInt((String) paramMap.get("pageSize"));
		String filter = (String) paramMap.get("filter");
		List<Preprocess> operStepList = null;
		List<Map<String, Object>> operStepMapList = (List<Map<String, Object>>) paramMap.get("operStepList");
		if (operStepMapList.size() > 0) {
			operStepList = new ArrayList<Preprocess>();
			for (Map<String, Object> operStepMap : operStepMapList) {
				String operStepMapJson = JSONArray.toJSONString(operStepMap);
				Preprocess preprocess = JSONArray.parseObject(operStepMapJson, Preprocess.class);
				operStepList.add(preprocess);
			}
		}
		// 判断nodeID是否不是空且步骤列表为空 如果是，则查询出当前node中保存的步骤
		if (nodeId != null && nodeId != 0 && operStepList != null && operStepList.size() == 0) {
			WorkflowNode currentWorkflowNode = workflowNodeMapper.selectByPrimaryKey(dataSetId);
			String preprocessJson = currentWorkflowNode.getPreprocessJson();
			// 获取当前节点中的处理步骤
			operStepList = (List<Preprocess>) JSONArray.parse(preprocessJson);
		}
		// 获取当前输入数据集的采样数据 先判断是否存在 不存在则同步
		if (!previewService.isMongoDataExist(dataSetId)) {
			previewService.syncData(dataSetId);
			previewService.syncProportion(dataSetId);
		}
		List<DatasetSchemaVO> dataSetSchemaList = this.getDataSetSchemaList(dataSetId, null, null, null, null);
		Map<String, Object> dataListInMongo = this.getDataListInMongo(dataSetId, page, pageSize, filter);
		List<Document> dataList = (List<Document>) dataListInMongo.get("dataList");
		// 获取所有数据 如果operStepList- 步骤列表不为空 则执行相关步骤
		if (dataList.size() > 0 && operStepList != null && operStepList.size() > 0) {
			for (Preprocess preprocess : operStepList) {
				// 如果是链接步骤 需将被链接的表的信息存入步骤对象
				if (preprocess.getId() == 207) {
					if (!previewService.isMongoDataExist(preprocess.getJoinSetId())) {
						previewService.syncData(preprocess.getJoinSetId());
						previewService.syncProportion(preprocess.getJoinSetId());
					}
					List<DatasetSchemaVO> joinDataSetSchemaList = this.getDataSetSchemaList(preprocess.getJoinSetId(),
							null, null, null, null);
					Map<String, Object> joinDataListInMongo = this.getDataListInMongo(preprocess.getJoinSetId(), page,
							pageSize, null);
					List<Document> joinDataList = (List<Document>) joinDataListInMongo.get("dataList");
					preprocess.setDataSetSchemaList(joinDataSetSchemaList);
					preprocess.setDataList(joinDataList);
				}
				OperPreprocess operPreprocess = PreporocessFactory.createOperPreprocess(preprocess.getId());
				Map<String, Object> stepResultMap = operPreprocess.goPreprocessStep(preprocess, dataSetSchemaList,
						dataList);
				if (stepResultMap != null) {
					dataSetSchemaList = (List<DatasetSchemaVO>) stepResultMap.get("dataSetSchemaList");
					dataList = (List<Document>) stepResultMap.get("dataList");
				}
			}
			dataListInMongo.put("dataList", dataList);
		}
		resultMap.put("data", dataListInMongo);
		resultMap.put("dataSchema", dataSetSchemaList);
		return ResultUtil.success(resultMap);
	}

	@Override
	public ResultCode<JSONArray> getProcessStepList(Map<String, Object> paramMap) {
		ResultCode<JSONArray> resultCode = new ResultCode<>();
		long nodeId = Long.parseLong(paramMap.get("nodeId") + "");
		WorkflowNode workflowNode = workflowNodeMapper.selectByPrimaryKey(nodeId);
		JSONArray jsonArray = JSONArray.parseArray(workflowNode.getPreprocessJson());
		resultCode.setData(jsonArray);
		return resultCode;
	}

	// 获取mongo中数据
	private Map<String, Object> getDataListInMongo(Long dataSetId, Integer pageNum, Integer pageSize, String filter) {
		Map<String, Object> resullMap = new HashMap<String, Object>();
		Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);

		// 查询数据集对应的字段列表，找出meaning == date 类型的字段名,用以格式化前端显示
		DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
		DatasetSchemaExample.Criteria criteria = datasetSchemaExample.createCriteria();
		criteria.andDatasetIdEqualTo(dataSetId).andMeaningEqualTo("5");
		List<DatasetSchema> schemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);
		Long projectId = dataset.getProjectId(); // 数据集所在的项目
		// 数据集对应的mongo collection命名规则：'proj_'+project_id+'_ds_'+dataset_id
		String mongoColName = Constants.MONGO_COL_NAME_PREFIX_PROJ + projectId + Constants.MONGO_COL_NAME_PREFIX_DS
				+ dataSetId;
		MongoUtils mongoUtils = MongoUtils.getInstance();
		List<Document> mongoRes = new ArrayList<>();

		int count = mongoUtils.getCount(mongoUtils.getCollection(mongoColName));
		MongoCursor<Document> cursor = mongoUtils.findByPage(mongoUtils.getCollection(mongoColName), MongoUtils.parseFilter(filter), pageNum, pageSize);
		while (cursor.hasNext()) {
			Document object = cursor.next();
			if (null != schemas && !schemas.isEmpty()) {
				for (DatasetSchema datasetSchema : schemas) {
					String columnName = datasetSchema.getColumnName();
					Object columnValue = object.get(columnName);
					if (null != columnValue && RegexValidation.isDate(columnValue.toString())) {
						object.put(columnName,
								new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object.getDate(columnName)));
					}
				}
			}
			mongoRes.add(object);
		}
		resullMap.put("dataList", mongoRes);
		resullMap.put("page", pageNum);
		resullMap.put("pageSize", pageSize);
		resullMap.put("totalNum", count);
		return resullMap;
	}

	// 获取数据集的字段列表
	private List<DatasetSchemaVO> getDataSetSchemaList(Long dataSetId, String filter, String columnType, String meaning,
			String subMeaning) {
		DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
		DatasetSchemaExample.Criteria criteria = datasetSchemaExample.createCriteria();
		criteria.andDatasetIdEqualTo(dataSetId);
		if (StringUtils.isNotBlank(filter)) {
			criteria.andColumnNameLike("%" + filter + "%");
		}
		if (StringUtils.isNotBlank(columnType)) {
			criteria.andColumnTypeEqualTo(columnType);
		}
		if (StringUtils.isNotBlank(meaning)) {
			criteria.andMeaningEqualTo(meaning);
		}
		if (StringUtils.isNotBlank(subMeaning)) {
			criteria.andSubMeaningEqualTo(subMeaning);
		}

		datasetSchemaExample.setOrderByClause("id");
		List<DatasetSchema> datasetSchemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);
		List<DatasetSchemaVO> datasetSchemaVOS = new ArrayList<>();
		for (DatasetSchema datasetSchema : datasetSchemas) {
			DatasetSchemaVO datasetSchemaVO = new DatasetSchemaVO();
			datasetSchemaVO.setAllPropoty(datasetSchema);
			datasetSchemaVOS.add(datasetSchemaVO);
		}
		return datasetSchemaVOS;
	}

	/**
	 * 保存预处理节点
	 */
	@Override
	public ResultCode savePreprocess(Map<String, Object> paramMap) {
		Long inputSetId = Long.parseLong(paramMap.get("inputSetId") + "");
		String stepListStr = paramMap.get("stepList") + "";
		List<Preprocess> preprocessList = JSONArray.parseArray(stepListStr, Preprocess.class);
		List<DatasetSchemaVO> dataSetSchemaList = this.getDataSetSchemaList(inputSetId, null, null, null, null);
		if (preprocessList.size() > 0) {
			Map<String, Object> dataListInMongo = this.getDataListInMongo(inputSetId, 0, 99999, null);
			List<Document> dataList = (List<Document>) dataListInMongo.get("dataList");
			for (Preprocess preprocess : preprocessList) {
				// 如果是链接步骤 需将被链接的表的信息存入步骤对象
				if (preprocess.getId() == 207) {
					if (!previewService.isMongoDataExist(preprocess.getJoinSetId())) {
						previewService.syncData(preprocess.getJoinSetId());
						previewService.syncProportion(preprocess.getJoinSetId());
					}
					List<DatasetSchemaVO> joinDataSetSchemaList = this.getDataSetSchemaList(preprocess.getJoinSetId(),
							null, null, null, null);
					Map<String, Object> joinDataListInMongo = this.getDataListInMongo(preprocess.getJoinSetId(), 0,
							99999, null);
					List<Document> joinDataList = (List<Document>) joinDataListInMongo.get("dataList");
					preprocess.setDataSetSchemaList(joinDataSetSchemaList);
					preprocess.setDataList(joinDataList);
				}
				OperPreprocess operPreprocess = PreporocessFactory.createOperPreprocess(preprocess.getId());
				Map<String, Object> stepResultMap = operPreprocess.goPreprocessStep(preprocess, dataSetSchemaList,
						dataList);
				if (stepResultMap != null) {
					dataSetSchemaList = (List<DatasetSchemaVO>) stepResultMap.get("dataSetSchemaList");
					dataList = (List<Document>) stepResultMap.get("dataList");
				}
			}
		}
		// 添加处理节点
		AddNodeRelVO addNodeRelVO = new AddNodeRelVO();
		DatasetNode inputNode = new DatasetNode();
		inputNode.setDatasetId(Long.parseLong(paramMap.get("inputSetId") + ""));
		inputNode.setNodeName((String) paramMap.get("inputSetName"));
		DatasetNode outputNode = new DatasetNode();
		outputNode.setDatasetId(Long.parseLong(paramMap.get("outputSetId") + ""));
		outputNode.setNodeName((String) paramMap.get("outputSetName"));
		ProcessNode processNode = new ProcessNode();
		processNode.setIsAppend((String)paramMap.get("isAppend"));
		processNode.setNodeProcessSubType("0");
		processNode.setNodeProcessType("0");
		processNode.setNodePreprocessJson(stepListStr);
		addNodeRelVO.setInputNode(inputNode);
		addNodeRelVO.setOutputNode(outputNode);
		addNodeRelVO.setProcessNode(processNode);
		addNodeRelVO.setProjectId(((Integer) paramMap.get("projectId")).longValue());
		addNodeRelVO.setWorkflowId(Long.parseLong((String) paramMap.get("workflowid")));

		WorkflowNodeExample workflowNodeExample = new WorkflowNodeExample();
		workflowNodeExample.createCriteria().andNodeNameEqualTo(Constants.PROCESSNODE_NAME_PREFIX + paramMap.get("outputSetName")).andWorkflowIdEqualTo(Long.parseLong((String) paramMap.get("workflowid")));
		List<WorkflowNode> workflowNodes=workflowNodeMapper.selectByExample(workflowNodeExample);
		ResultCode<Long> result;
		if(workflowNodes.isEmpty()){
			result = workflowService.addNodeRel(addNodeRelVO,(Long)paramMap.get("tenantId"),
					(Long)paramMap.get("userId"),(String)paramMap.get("userName"));
		}else{
			addNodeRelVO.getProcessNode().setNodeId(workflowNodes.get(0).getId());
			result = workflowService.updNodeRel(addNodeRelVO,(Long)paramMap.get("tenantId"),
					(Long)paramMap.get("userId"),(String)paramMap.get("userName"));
		}


//
//		ResultCode<Long> result = workflowService.addNodeRel(addNodeRelVO, (Long) paramMap.get("tenantId"),
//				(Long) paramMap.get("userId"), (String) paramMap.get("userName"));

		// 如果存在预处理步骤需要重新保存预处理的表
		if (preprocessList.size() > 0) {
			List<Map<String, Object>> tableStructures = null; // 输入表的表结构，用于写到dataset_schema表中
			// 查询输出表信息并更改字段
			Dataset outputDataSet = datasetMapper
					.selectByPrimaryKey(Long.parseLong(paramMap.get("outputSetId") + ""));
			String outputTableName = outputDataSet.getTableName();// 输入数据集对应的数据表
			Long outputDataSourceId = outputDataSet.getDatasourceId();
			Datasource outputDatasource = datasourceMapper.selectByPrimaryKey(outputDataSourceId); // 输出的数据源
			// 输出的数据类型（0：MySQL、、6：Cassandra
			String outputDataType = outputDatasource.getDataType();
			String outputHost = outputDatasource.getHost();// 输出数据源的域
			Integer outputPort = outputDatasource.getPort();// 输出数据源的端口
			String outputDbName = outputDatasource.getDb();// 输出数据源的库名
			String outputUserName = outputDatasource.getUsername();// 输出数据源的用户名
			String outputPassword = outputDatasource.getPassword();// 输出数据源的密码
			switch (outputDataType) {
			case "0":
				MySqlUtils outputDB = new MySqlUtils(outputHost, outputPort.toString(), outputDbName, outputUserName,
						outputPassword);
				tableStructures = new ArrayList<>();
				for (int i = 0; i < dataSetSchemaList.size(); i++) {
					Map<String, Object> tableStructure = new HashMap<>();
					tableStructure.put("index", i);
					tableStructure.put("columnName", dataSetSchemaList.get(i).getColumnName());
					tableStructure.put("columnType", datasetType2SqlType(dataSetSchemaList.get(i).getColumnType()));
					if (dataSetSchemaList.get(i).getColumnType().equals("7")) {
						tableStructure.put("precision", 255);
					} else {
						tableStructure.put("precision", 0);
					}
					tableStructure.put("scale", null);
					tableStructures.add(tableStructure);
				}

				// 在输出源建表
				try {
					boolean isSqlCreateSuccess = outputDB.createTableFromParamList(outputTableName, tableStructures);
					if (isSqlCreateSuccess) {
						// 追加biz_date_param和sys_time_param两个字段
						this.addBizAndSysColumnInMysql(outputDB, outputDbName, outputTableName);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					outputDB.closeDB();
				}
				break;
			case "6":
				String primaryKey = CassandraUtil2.getPrimaryKey(outputHost, outputUserName, outputPassword,
						outputDbName, outputTableName);
				tableStructures = new ArrayList<>();
				for (DatasetSchemaVO datasetSchemaVO : dataSetSchemaList) {
					Map<String, Object> tableStructure = new HashMap<>();
					tableStructure.put("columnName", datasetSchemaVO.getColumnName());
					tableStructure.put("columnType", datasetType2SqlType(datasetSchemaVO.getColumnType()));
					tableStructures.add(tableStructure);
				}
				boolean isCassCreateSuccess = CassandraUtil2.createTableFromParamList(outputHost, outputPort.toString(),
						outputUserName, outputPassword, outputDbName, outputTableName, primaryKey, tableStructures);
				if (isCassCreateSuccess) {
					this.addBizAndSysColumnInCassandra(outputHost, outputPort.toString(), outputUserName,
							outputPassword, outputDbName, outputTableName);
				}
				break;
			default:
				break;
			}
		}
		return result;
	}

	/**
	 * 0:tinyint(8 bit), 1:smallint(16 bit), 2:int, 3:bigint(64 bit), 4:float,
	 * 5:double, 6:boolean, 7:string, 8:date, 9:list,10:set 11:map, 12:object
	 * 
	 * @param
	 */
	private String datasetType2SqlType(String dataSetType) {
		String sqlType = "";
		switch (dataSetType) {
		case "0":
			sqlType = "TINYINT";
			break;
		case "1":
			sqlType = "SMALLINT";
			break;
		case "2":
		case "6":
			sqlType = "INT";
			break;
		case "3":
			sqlType = "BIGINT";
			break;
		case "4":
			sqlType = "FLOAT";
			break;
		case "5":
			sqlType = "DOUBLE";
			break;
		case "7":
			sqlType = "VARCHAR";
			break;
		case "8":
			sqlType = "DATETIME";
			break;
		case "9":
			sqlType = "LIST";
			break;
		case "10":
			sqlType = "SET";
			break;
		case "11":
			sqlType = "MAP";
			break;
		case "12":
			sqlType = "OBJECT";
			break;
		default:
			break;
		}
		return sqlType;
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
}
