package com.quarkdata.data.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.*;
import com.quarkdata.data.model.vo.DatasetFilterVO;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.service.PreviewService;
import com.quarkdata.data.util.*;
import com.quarkdata.data.util.common.cassandra.CassandraUtil2;
import com.quarkdata.data.util.db.MongoUtils;
import com.quarkdata.data.util.db.MySqlUtils;
import com.quarkdata.data.util.db.QueryStatementUtils;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PreviewServiceImpl implements PreviewService {

    static Logger logger = Logger.getLogger(PreviewServiceImpl.class);

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    DatasetFilterMapper datasetFilterMapper;

    @Autowired
    DatasourceMapper datasourceMapper;

    @Autowired
    DatasetSchemaMapper datasetSchemaMapper;

    @Autowired
    DatasetSchemaMapper2 datasetSchemaMapper2;

    @Autowired
    DataSetService dataSetService;

    @Autowired
    private MongoOperations mongoOperations;

    /**
     * 获取采样方式和过滤条件
     *
     * @param dataSetId
     * @return
     */
    @Override
    public ResultCode getSampleAndFilterMethod(Long dataSetId) {
        ResultCode resultCode = null;

        HashMap<String, Object> res = new HashMap<>();
        logger.info("--------------------开始查询数据集信息 data_set id==" + dataSetId + "---------------------------------");
        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        String sampleType = dataset.getSampleType(); //采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））
        Integer sampleTypeValue = dataset.getSampleTypeValue(); //采样方式对应值（采样个数、采样比例）默认采样个数10000
        String isSampleFilter = dataset.getIsSampleFilter(); //是否采样过滤（0：否、1：是）默认0

        res.put("dataSetId", dataSetId);
        res.put("sampleType", sampleType);
        res.put("sampleTypeValue", sampleTypeValue);
        res.put("isSampleFilter", isSampleFilter);


        if ("1".equals(isSampleFilter)) { //需要过滤
            String sampleFilterType = dataset.getSampleFilterType(); // 采样过滤方式（0：以下情况均符合，1：至少有一种情况符合）
            DatasetFilterExample datasetFilterExample = new DatasetFilterExample();
            DatasetFilterExample.Criteria criteria = datasetFilterExample.createCriteria();
            criteria.andDatasetIdEqualTo(dataSetId);
            List<DatasetFilter> filters = datasetFilterMapper.selectByExample(datasetFilterExample);
            List<DatasetFilterVO> datasetFilterVOS = new ArrayList<>();
            for (DatasetFilter datasetFilter : filters) {
                DatasetFilterVO datasetFilterVO = new DatasetFilterVO();
                datasetFilterVO.setAllPropoty(datasetFilter);
                String columnName = datasetFilter.getColumnName();
                String columnType = datasetSchemaMapper2.getColumnType(dataSetId, columnName);

                DatasetFilterVO.Column column = new DatasetFilterVO().new Column();
                column.setName(columnName);
                column.setType(columnType);
                datasetFilterVO.setColumn(column);
                datasetFilterVOS.add(datasetFilterVO);
            }

            res.put("sampleFilterType", sampleFilterType);
            res.put("filters", datasetFilterVOS);
        }

        return ResultUtil.success(res);
    }

    /**
     * 更新数据集预览抽样和过滤方式
     *
     * @param paramMap json 例子：
     *                 {
     *                 "dataSetId": 1,
     *                 "sampleType": 2, //采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））默认0
     *                 "sampleTypeValue": 1000,//采样数量（采样个数、采样比例）默认采样个数10000
     *                 "isSampleFilter": 0,//是否采样过滤（0：否、1：是）,如果为0则没有下面两项
     *                 "sampleFilterType": 1,//采样过滤方式（0：以下情况均符合，1：至少有一种情况符合）
     *                 "filters": [{ //具体过滤方式
     *                 "column": {"name":"id","type":"1"},
     *                 "operator": 2,//条件(0：不为空，1：为空，2：==，3：!=，4：>，5：<，6：>=，7：<=，8：等于，9：不等于，10：包含，11：介于，12：早于，13：晚于，14：true，15：false)；
     *                 "value1": 12345
     *                 },
     *                 {
     *                 "column": {"name":"create_time","type":"6"},
     *                 "operator": 11,
     *                 "value1": "2018 - 05 - 04 12: 34: 56",
     *                 "value2": "2018 - 05 - 05 12: 34: 56"
     *                 },
     *                 {
     *                 "columnName": "table_name",
     *                 "operator": 1
     *                 }
     *                 ]
     *                 }
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode updateSampleFilterMethod(Map<String, Object> paramMap) {
        Long dataSetId = Long.parseLong(paramMap.get("dataSetId") + "");
        String sampleType = (String) paramMap.get("sampleType");
        Integer sampleTypeValue = (Integer) paramMap.get("sampleTypeValue");

        String isSampleFilter = (String) paramMap.get("isSampleFilter");
        String sampleFilterType = (String) paramMap.get("sampleFilterType");
        List<Map<String, Object>> filters = (List<Map<String, Object>>) paramMap.get("filters");

        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        dataset.setIsSync("1");//需要同步数据
        dataset.setSampleType(sampleType);
        dataset.setSampleTypeValue(sampleTypeValue);
        dataset.setIsSampleFilter(isSampleFilter);
        dataset.setSampleFilterType(sampleFilterType);
        datasetMapper.updateByPrimaryKeySelective(dataset);


        // 删除旧的过滤条件
        DatasetFilterExample datasetFilterExample = new DatasetFilterExample();
        DatasetFilterExample.Criteria criteria = datasetFilterExample.createCriteria();
        criteria.andDatasetIdEqualTo(dataSetId);
        datasetFilterMapper.deleteByExample(datasetFilterExample);

        // 添加新的过滤条件
        if (null != filters && !filters.isEmpty()) {
            for (Map<String, Object> filter : filters) {
//                String columnName = (String) filter.get("columnName");

                JSONObject column = (JSONObject) filter.get("column");
                String columnName = (String) column.get("name");

                String operator = (String) filter.get("operator");
                String value1 = (String) filter.get("value1");
                String value2 = (String) filter.get("value2");
                DatasetFilter datasetFilter = new DatasetFilter();
                datasetFilter.setDatasetId(dataSetId);
                datasetFilter.setColumnName(columnName);
                datasetFilter.setOperator(operator);
                if (StringUtils.isNotBlank(value1)) {
                    datasetFilter.setValue1(value1);
                }
                if (StringUtils.isNotBlank(value2)) {
                    datasetFilter.setValue2(value2);
                }
                datasetFilterMapper.insertSelective(datasetFilter);
            }
        }
        return ResultUtil.success();
    }


    /**
     * 同步数据
     * 重新从源表按照采样和过滤规则拉取数据，放到mongo中
     *
     * @param dataSetId
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode syncData(Long dataSetId) {

        ResultCode resultCode = null;

        StringBuffer where = null;
        StringBuffer limit = null;
        List<Map<String, Object>> datas = null;
        //查询数据集对应的源表配置
        logger.info("--------------------开始查询数据集信息 data_set id==" + dataSetId + "---------------------------------");
        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        Long projectId = dataset.getProjectId(); //数据集所在的项目
        String tableName = dataset.getTableName();//数据集对应的数据表
        String sampleType = dataset.getSampleType(); //采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））
        Integer sampleTypeValue = dataset.getSampleTypeValue(); //采样方式对应值（采样个数、采样比例）默认采样个数10000
        String isSampleFilter = dataset.getIsSampleFilter(); //是否采样过滤（0：否、1：是）默认0

        if ("1".equals(isSampleFilter)) { //需要过滤
            String sampleFilterType = dataset.getSampleFilterType(); // 采样过滤方式（0：以下情况均符合，1：至少有一种情况符合）
            DatasetFilterExample datasetFilterExample = new DatasetFilterExample();
            DatasetFilterExample.Criteria criteria = datasetFilterExample.createCriteria();
            criteria.andDatasetIdEqualTo(dataSetId);
            List<DatasetFilter> filters = datasetFilterMapper.selectByExample(datasetFilterExample);

            where = QueryStatementUtils.makeWheres(sampleFilterType, filters);
        }


        if ("2".equals(sampleType)) {
            ResultCode<Map<String, Object>> countResultCode = dataSetService.getDataSetDataCount(dataSetId);
            int count = Integer.parseInt(String.valueOf(countResultCode.getData().get("dataCount")));
            sampleTypeValue = count * sampleTypeValue / 100;
        }

        limit = QueryStatementUtils.makeLimit(sampleType, sampleTypeValue);


        Long datasourceId = dataset.getDatasourceId();
        Datasource datasource = datasourceMapper.selectByPrimaryKey(datasourceId);
        String dataType = datasource.getDataType(); //数据源的数据类型
        String host = datasource.getHost(); //数据源的域
        Integer port = datasource.getPort();//数据源的端口
        String dbName = datasource.getDb();//数据源的库名
        String userName = datasource.getUsername();//数据源的用户名
        String password = datasource.getPassword();//数据集的密码
        switch (dataType) {
            case "0": //mysql

                MySqlUtils db = new MySqlUtils(host, port.toString(), dbName, userName, password);

                /**取数据时忽略biz_date_param和sys_time_param两个字段*/
                StringBuffer getSql = new StringBuffer();
                getSql.append("SELECT CONCAT(' select ',GROUP_CONCAT(COLUMN_NAME),' from ', TABLE_NAME) FROM information_schema.COLUMNS WHERE table_name = ");
                getSql.append("'" + tableName + "' ");
                getSql.append("AND TABLE_SCHEMA = ");
                getSql.append("'" + dbName + "' ");
                getSql.append("and COLUMN_NAME != 'biz_date_param' and COLUMN_NAME != 'sys_time_param';");

                List<Map<String, Object>> selectSql = null;
                try {
                    selectSql = db.executeQuery(getSql.toString(), null);
                    if (null != selectSql && !selectSql.isEmpty()) {
                        String sql = (String) selectSql.get(0).get("CONCAT(' select ',GROUP_CONCAT(COLUMN_NAME),' from ', TABLE_NAME)");
                        logger.info("获取原始查询sql::" + sql);

                        StringBuffer executeSQL = new StringBuffer(sql);
                        if (null != where) {
                            executeSQL.append(where);
                        }
                        executeSQL.append(limit);
                        logger.info("添加过滤条件后sql::" + executeSQL);

                        datas = db.executeQuery(executeSQL.toString(), null);

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    db.closeDB();
                }
                break;
            case "6": //Cassandra
                StringBuffer cql = new StringBuffer();
                cql.append("select * from ");
                cql.append(tableName);
                cql.append(limit);
                logger.info("添加过滤条件后sql::" + cql);
                datas = CassandraUtil2.executeQueryCql(host, port.toString(), userName, password, dbName, cql.toString());
                break;
            case "8": //Hive
                break;
            default:
                break;
        }

        if (null != datas && !datas.isEmpty()) {
            //数据集对应的mongo collection命名规则：'proj_'+project_id+'_ds_'+dataset_id
            String mongoColName = Constants.MONGO_COL_NAME_PREFIX_PROJ +projectId+ Constants.MONGO_COL_NAME_PREFIX_DS + dataSetId;

            MongoUtils mongoUtils = MongoUtils.getInstance();
            if (0 < mongoUtils.getCollection(mongoColName).count()) {
                mongoUtils.dropCollection(mongoColName);
            }
            mongoUtils.insertDatas(mongoColName, datas); //插入到mongo中
            logger.info("将数据插入到mongo中 =====>>> Mongo::[dbName == 默认db, collection == " + mongoColName + "]");

            resultCode = ResultUtil.success();
        } else { //没有数据
            resultCode = ResultUtil.error(Messages.DATA_NULL_CODE, Messages.DATA_NULL_MSG);
        }

        return resultCode;
    }

    /**
     * 同步语义
     * 重新计算语义及占比，并更新dataset_schema相关字段
     *
     * @param dataSetId
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode syncProportion(Long dataSetId) {

        ResultCode resultCode = null;

        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        Long projectId = dataset.getProjectId(); //数据集所在的项目

        //查询数据集结构集合
        DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
        DatasetSchemaExample.Criteria criteria = datasetSchemaExample.createCriteria();
        criteria.andDatasetIdEqualTo(dataSetId);
        datasetSchemaExample.setOrderByClause("id");
        List<DatasetSchema> datasetSchemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);

        //数据集对应的mongo collection命名规则：'proj_'+project_id+'_ds_'+dataset_id
        String mongoColName = Constants.MONGO_COL_NAME_PREFIX_PROJ +projectId+ Constants.MONGO_COL_NAME_PREFIX_DS + dataSetId;
//        MongoUtils mongodb = MongoUtils.getInstance();
//        MongoCollection<Document> collection = mongodb.getCollection(mongoColName);

        //遍历数据集结构集合，根据字段名去mongo查询每个字段的数据集合，结合meaning和subMeaning，计算出每个字段的语义占比
        if (null != datasetSchemas && !datasetSchemas.isEmpty()) {
            for (DatasetSchema datasetSchema : datasetSchemas) {
                String columnName = datasetSchema.getColumnName();
                String meaning = datasetSchema.getMeaning();
                String subMeaning = datasetSchema.getSubMeaning();

//                if ("id".equals(columnName)){
//                    columnName = "_id";
//                }

                //mongo查询数据语法： db.[documentName].find({条件},{键指定})
                BasicDBObject filter = new BasicDBObject();
                BasicDBObject column = new BasicDBObject(columnName, 1);
//                if (!"_id".equals(columnName)){
                column.append("_id", 0);
//                }
                Query query = new BasicQuery(filter, column);
                List<HashMap> list = mongoOperations.find(query, HashMap.class, mongoColName);

                if (null != list && !list.isEmpty()) {
                    List<Object> datas = new ArrayList<>();
                    for (HashMap<String, Object> m : list) {
                        datas.add(m.get(columnName));
                    }
                    logger.info("查询列：：" + columnName + " 的数据完成, 数量：" + datas.size());

                    // 确定语义
                    if ("0".equals(meaning)) {
                        logger.info("自动检测语义：" + columnName + ":");
                        String newMeaning = MeaningUtils.autoDetectMeanings(datas).toString();
                        if ("-1".equals(newMeaning)) {//空
                            meaning = "1";
                        } else {
                            meaning = newMeaning;
                        }
                    } else if (StringUtils.isNotBlank(subMeaning)) {
                        meaning = meaning + subMeaning;
                    }

                    logger.info("确定列：：" + columnName + "的语义完成，meaning ==" + meaning);

                    // 计算各种占比各种值
                    double validProportion = MeaningUtils.validProportion(datas, Integer.valueOf(meaning));
                    double invalidProportion = 1 - validProportion;

                    Map<String,Object> res = DataStatisticsUtils.dataStatistics(datas);
                    double nullProportion = (double) res.get("nullProportion");
                    String maxValue = (String) res.get("maxValue");
                    String minValue = (String) res.get("minValue");
                    String avgValue = (String) res.get("avgValue");
                    String maxLength = Integer.parseInt((String) res.get("maxLength")) > columnName.length() ? (String) res.get("maxLength") : String.valueOf(columnName.length());

//                    double nullProportion = MeaningUtils.nullProportion(datas);
                    double notNullProportion = 1 - nullProportion;

//                    if (meaning.equals("2") || meaning.equals("3")) {
//                        Map<String, String> dataStatistics = MeaningUtils.statisticsValue(datas);
//                        String maxValue = dataStatistics.get("maxValue");
//                        String minValue = dataStatistics.get("minValue");
//                        String avgValue = dataStatistics.get("avgValue");
//                        datasetSchema.setMaxValue(maxValue);
//                        datasetSchema.setMinValue(minValue);
//                        datasetSchema.setAvgValue(avgValue);
//                    }

                    // update dataset_schema表
                    if (2 == meaning.length()) {
                        datasetSchema.setMeaning(String.valueOf(meaning.charAt(0)));
                        datasetSchema.setSubMeaning(String.valueOf(meaning.charAt(1)));
                    } else {
                        datasetSchema.setMeaning(meaning);
                        datasetSchema.setSubMeaning(null);
                    }

                    datasetSchema.setValidProportion((float) validProportion * 100);
                    datasetSchema.setInvalidProportion((float) invalidProportion * 100);
                    datasetSchema.setNullProportion((float) nullProportion * 100);
                    datasetSchema.setNotNullProportion((float) notNullProportion * 100);
                    datasetSchema.setMaxValue(maxValue);
                    datasetSchema.setMinValue(minValue);
                    datasetSchema.setAvgValue(avgValue);
                    datasetSchema.setMaxLength(maxLength);

                    logger.info("更新数据库：");
                    datasetSchemaMapper.updateByPrimaryKey(datasetSchema);

                    //将data_set的is_sync字段改为‘0’
                    dataset.setIsSync("0");
                    datasetMapper.updateByPrimaryKeySelective(dataset);

                    resultCode = ResultUtil.success();
                }
            }
        }
        return resultCode;
    }

    @Override
    public boolean isMongoDataExist(Long dataSetId) {
        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        Long projectId = dataset.getProjectId(); //数据集所在的项目
        String isSync = dataset.getIsSync(); // 是否需要同步数据（0：否、1：是），默认0

        //数据集对应的mongo collection命名规则：'proj_'+project_id+'_ds_'+dataset_id
        String mongoColName = Constants.MONGO_COL_NAME_PREFIX_PROJ +projectId+ Constants.MONGO_COL_NAME_PREFIX_DS + dataSetId;

        MongoUtils mongoUtils = MongoUtils.getInstance();

        boolean mongoHasData = mongoUtils.getCollection(mongoColName).count() > 0; //mongo是否有数据

        return mongoHasData && "0".equals(isSync);
    }

    /**
     * 获取数据
     * 从mongo中分页获取数据
     *
     * @param dataSetId
     * @param pageNum
     * @param pageSize
     * @param filter
     */
    @Override
    public ResultCode getData(Long dataSetId, Integer pageNum, Integer pageSize, String filter) {

        ListResult<Document> result = new ListResult<>();
        List<Document> mongoRes = new ArrayList<>();

        //查询数据集对应的源表配置
        logger.info("--------------------开始查询数据集信息 data_set id==" + dataSetId + "---------------------------------");
        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        Long projectId = dataset.getProjectId(); //数据集所在的项目

        //查询数据集对应的字段列表，找出meaning == date 类型的字段名,用以格式化前端显示
        DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
        DatasetSchemaExample.Criteria criteria = datasetSchemaExample.createCriteria();
        criteria.andDatasetIdEqualTo(dataSetId).andMeaningEqualTo("5");
        List<DatasetSchema> schemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);

        //查询数据集对应的字段名列表,用以拼接mongo过滤条件
        List<String> dataSetColumnNames = datasetSchemaMapper2.getColumnNames(dataSetId);

        //数据集对应的mongo collection命名规则：'proj_'+project_id+'_ds_'+dataset_id
        String mongoColName = Constants.MONGO_COL_NAME_PREFIX_PROJ +projectId+ Constants.MONGO_COL_NAME_PREFIX_DS + dataSetId;

        MongoUtils mongoUtils = MongoUtils.getInstance();
        MongoCollection<Document> mongoCollection = mongoUtils.getCollection(mongoColName);
        int count = 0;

        Bson fliters = null;
        if (StringUtils.isNotBlank(filter)){
            fliters = mongoUtils.makeAllColumnFilter(dataSetColumnNames,filter);
            count = mongoUtils.getCount(mongoCollection,fliters);
        } else {
            count = mongoUtils.getCount(mongoCollection);
        }
        MongoCursor<Document> cursor = mongoUtils.findByPage(mongoCollection, fliters, pageNum, pageSize);
        while (cursor.hasNext()) {
            Document object = cursor.next();
            if (null != schemas && !schemas.isEmpty()){
                for (DatasetSchema datasetSchema:schemas) {
                    String columnName = datasetSchema.getColumnName();
                    Object columnValue = object.get(columnName);
                    if (null != columnValue && RegexValidation.isDate(columnValue.toString())){
                        object.put(columnName, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object.getDate(columnName)));
                    }
                }
            }
            mongoRes.add(object);
        }

        result.setListData(mongoRes);
        result.setPageNum(pageNum);
        result.setPageSize(pageSize);
        result.setTotalNum(count);

        return ResultUtil.success(result);
    }


}
