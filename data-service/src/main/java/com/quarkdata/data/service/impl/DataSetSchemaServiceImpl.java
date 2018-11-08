package com.quarkdata.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quarkdata.data.dal.dao.ResourceMapper;
import com.quarkdata.data.util.TypeUtil;
import com.quarkdata.data.model.common.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mongodb.BasicDBObject;
import com.quarkdata.data.dal.dao.DatasetMapper;
import com.quarkdata.data.dal.dao.DatasetSchemaMapper;
import com.quarkdata.data.dal.dao.DatasetSchemaMapper2;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.DatasetSchema;
import com.quarkdata.data.model.dataobj.DatasetSchemaExample;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.service.DataSetSchemaService;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.util.MeaningUtils;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.util.StringUtils;


@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class DataSetSchemaServiceImpl implements DataSetSchemaService {

    static Logger logger = Logger.getLogger(DataSetSchemaServiceImpl.class);

    @Autowired
    DatasetMapper datasetMapper;

    @Autowired
    DatasetSchemaMapper datasetSchemaMapper;

    @Autowired
    DatasetSchemaMapper2 datasetSchemaMapper2;

    @Autowired
    DataSetService dataSetService;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    ResourceMapper resourceMapper;


    /**
     * 获取数据集结构，从dataset_schema查询
     *
     * @param dataSetId  数据集id
     * @param filter     字段名过滤
     * @param columnType 类型过滤
     * @param meaning    语义过滤
     * @param subMeaning 语义过滤
     * @return {
     * "msg": "success",
     * "code": 0,
     * "data": [
     * {
     * “id”:1,
     * “dataSetId”:121,
     * “columnName”:”id”,
     * “columnType”:1,
     * “columnLength”:10,
     * “columnComment”:”ddddadadsa”,
     * “meaning”: 1,
     * “proportion”:0.50
     * },{},{},...
     * ]
     * }
     */
    @Override
    public ResultCode getSchema(Long dataSetId, String filter, String columnType, String meaning, String subMeaning) {
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
        for ( DatasetSchema datasetSchema: datasetSchemas) {
            DatasetSchemaVO datasetSchemaVO = new DatasetSchemaVO();
            datasetSchemaVO.setAllPropoty(datasetSchema);
            datasetSchemaVOS.add(datasetSchemaVO);
        }
        return ResultUtil.success(datasetSchemaVOS);
    }


    /**
     * 检查数据集表结构与源表是否一致，主要检查字段有没有增减，字段名称有没有改动
     *
     * @param dataSetId
     * @return
     * {
     *     "msg": "success",
     *     "code": 0,
     *     "data": {
     *         "isSame": false, // 是否一致，如果是true，则没有下面三个字段
     *         "difference": 0, // 不一致的类型：0 不同 1 多 2 少
     *         "columnIndex": 2, // 不一致的字段序号
     *         "columnName": "device_id" //不一致的字段名
     *     }
     * }
     */
    @Override
    public ResultCode checkSchema(Long dataSetId) {

        Map<String, Object> res = new HashMap<>();
        Boolean isSame = true;
        String columnName = null;
        Integer columnIndex = null;
        Integer difference = null;

        List<String> dataSetColumnNames = datasetSchemaMapper2.getColumnNames(dataSetId);
        List<String> sourceColumnNameList = new ArrayList<>();
        //查询数据集对应的源表配置
//        logger.info("--------------------开始查询数据集信息 data_set id==" + dataSetId + "---------------------------------");
        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        String tableName = dataset.getTableName();//数据集对应的数据表
        Long datasourceId = dataset.getDatasourceId();
        List<Map<String, Object>> sourceColumnNames = (List<Map<String, Object>>) dataSetService.getSourceTableStructure(datasourceId, tableName).getData();

        for (Map<String, Object> sourceColumnName : sourceColumnNames) {
            sourceColumnNameList.add((String) sourceColumnName.get("columnName"));
        }

        // 比对字符串list
        int dataSetColumnCount = dataSetColumnNames.size();
        int sourceColumnCount = sourceColumnNameList.size();
        if (dataSetColumnCount < sourceColumnCount) {
            boolean flag = true;
            for (int i = 0; i < dataSetColumnCount; i++) {
                if (!dataSetColumnNames.get(i).equals(sourceColumnNameList.get(i))) {
                    logger.info("在第" + (i + 1) + "行有不同：" + "数据集字段名：" + dataSetColumnNames.get(i) + ",源表字段名" + sourceColumnNameList.get(i));
                    isSame = false;
                    columnName = dataSetColumnNames.get(i);
                    columnIndex = i;
                    difference = 0; // 不一致
                    flag = false;
                    break;
                }
            }
            if (flag) {
                logger.info("数据集结构缺失：" + sourceColumnNameList.get(dataSetColumnCount));
                isSame = false;
                columnName = sourceColumnNameList.get(dataSetColumnCount);
                difference = 2; // 少
            }
        }
        if (dataSetColumnCount > sourceColumnCount) {
            boolean flag = true;
            for (int i = 0; i < sourceColumnCount; i++) {
                if (!dataSetColumnNames.get(i).equals(sourceColumnNameList.get(i))) {
                    logger.info("在第" + (i + 1) + "行有不同：" + "数据集字段名：" + dataSetColumnNames.get(i) + ",源表字段名" + sourceColumnNameList.get(i));
                    isSame = false;
                    columnName = dataSetColumnNames.get(i);
                    columnIndex = i;
                    difference = 0;
                    flag = false;
                    break;
                }
            }
            if (flag) {
                logger.info("数据集结构多出：" + dataSetColumnNames.get(sourceColumnCount));
                isSame = false;
                columnName = dataSetColumnNames.get(sourceColumnCount);
                difference = 1; // 多
            }
        }
        if (dataSetColumnCount == sourceColumnCount) {
            boolean flag = true;
            for (int i = 0; i < sourceColumnCount; i++) {
                if (!dataSetColumnNames.get(i).equals(sourceColumnNameList.get(i))) {
                    logger.info("在第" + (i + 1) + "行有不同：" + "数据集字段名：" + dataSetColumnNames.get(i) + ",源表字段名" + sourceColumnNameList.get(i));
                    isSame = false;
                    columnName = dataSetColumnNames.get(i);
                    columnIndex = i;
                    difference = 0;
                    flag = false;
                    break;
                }
            }
            if (flag) {
                logger.info("数据集结构和源表结构一致");
            }
        }

        res.put("isSame", isSame);
        if (null != columnName){
            res.put("columnName", columnName);
        }
        if (null != columnIndex){
            res.put("columnIndex", columnIndex + 1);
        }
        if (null != difference){
            res.put("difference", difference);
        }
        return ResultUtil.success(res);
    }

    /**
     * 删除旧的表结构，拉取并添加新的表结构
     *
     * @param dataSetId
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode reloadSchema(Long dataSetId) {
        ResultCode resultCode = new ResultCode();
        DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
        DatasetSchemaExample.Criteria criteria = datasetSchemaExample.createCriteria();
        criteria.andDatasetIdEqualTo(dataSetId);
        datasetSchemaMapper.deleteByExample(datasetSchemaExample);

        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        String tableName = dataset.getTableName();//数据集对应的数据表
        Long datasourceId = dataset.getDatasourceId();
        List<Map<String, Object>> sourceStructures =
                (List<Map<String, Object>>) dataSetService.getSourceTableStructure(datasourceId, tableName).getData();

        logger.info("--------------------开始解析表结构并存入dataset_schema表中---------------------------------");
        if (null != sourceStructures && !sourceStructures.isEmpty()) {
            for (Map<String, Object> sourceStructure : sourceStructures) {
                DatasetSchema datasetSchema = new DatasetSchema();
                datasetSchema.setDatasetId(dataSetId);
                datasetSchema.setColumnType((String) sourceStructure.get("columnType"));
                datasetSchema.setColumnName((String) sourceStructure.get("columnName"));
                //其余字段都用默认值
                datasetSchemaMapper.insertSelective(datasetSchema);
                logger.info("--------------------dataset_schema新添表字段记录：" + datasetSchema.getColumnName());
            }

            //修改data_set的is_sync字段
            dataset.setIsSync("1");
            datasetMapper.updateByPrimaryKeySelective(dataset);
            resultCode = getSchema(dataSetId,null,null,null,null);
        }
        return resultCode;
    }

    /**
     * 更新数据集字段类型
     *
     * @param dataSetId
     * @param columnName
     * @param columnType 数据集列类型( 0:tinyint(8 bit), 1:smallint(16 bit), 2:int, 3:bigint(64 bit), 4:float, 5:double, 6:boolean, 7:string, 8:date)
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode updateSchemaType(Long dataSetId, String columnName, String columnType) {

        ResultCode resultCode = null;
        DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
        DatasetSchemaExample.Criteria criteria = datasetSchemaExample.createCriteria();
        criteria.andDatasetIdEqualTo(dataSetId).andColumnNameEqualTo(columnName);
        List<DatasetSchema> datasetSchema = datasetSchemaMapper.selectByExample(datasetSchemaExample);
        if (null != datasetSchema && !datasetSchema.isEmpty()) {
            datasetSchema.get(0).setColumnType(columnType);
            datasetSchemaMapper.updateByPrimaryKeySelective(datasetSchema.get(0));
            resultCode = ResultUtil.success();
        } else {
            resultCode = ResultUtil.error(Messages.DATASET_COLUMN_NOT_EXIST_CODE, Messages.DATASET_COLUMN_NOT_EXIST_MSG);
        }
        return resultCode;
    }

    /**
     * 更新数据集语义,重新计算语义占比
     *
     * @param dataSetId
     * @param columnName
     * @param meaning
     * @param subMeaning
     * @return
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public ResultCode updateSchemaMeaning(Long dataSetId, String columnName, String meaning, String subMeaning) {

        ResultCode resultCode = null;
        String[] meanings;

        Dataset dataset = datasetMapper.selectByPrimaryKey(dataSetId);
        Long projectId = dataset.getProjectId(); //数据集所在的项目

        //查询数据集结构集合
        DatasetSchemaExample datasetSchemaExample = new DatasetSchemaExample();
        DatasetSchemaExample.Criteria criteria = datasetSchemaExample.createCriteria();
        criteria.andDatasetIdEqualTo(dataSetId);
        criteria.andColumnNameEqualTo(columnName);
        List<DatasetSchema> datasetSchemas = datasetSchemaMapper.selectByExample(datasetSchemaExample);

        //数据集对应的mongo collection命名规则：'proj_'+project_id+'_ds_'+dataset_id
        String mongoColName = Constants.MONGO_COL_NAME_PREFIX_PROJ +projectId+ Constants.MONGO_COL_NAME_PREFIX_DS + dataSetId;

        if (null != datasetSchemas && 1 == datasetSchemas.size()){
            DatasetSchema datasetSchema = datasetSchemas.get(0);

            //mongo查询数据语法： db.[documentName].find({条件},{键指定})
            BasicDBObject filter = new BasicDBObject();
            BasicDBObject column = new BasicDBObject(columnName,1);
            column.append("_id",0);

            Query query=new BasicQuery(filter,column);
            List<HashMap> list = mongoOperations.find(query,HashMap.class,mongoColName);

            if (null != list && !list.isEmpty()){
                List<Object> datas = new ArrayList<>();
                for (HashMap<String, Object> m:list) {
                    datas.add(m.get(columnName));
                }
                logger.info("查询列：："+columnName+" 的数据完成, 数量："+datas.size());


                // 确定语义
                if ("0".equals(meaning)){
                    logger.info("自动检测语义："+columnName+":");
                    String newMeaning = MeaningUtils.autoDetectMeanings(datas).toString();
                    if ("-1".equals(newMeaning)){//空
                        meaning = "1";
                    }else {
                        meaning = newMeaning;
                    }
                } else if (StringUtils.isNotBlank(subMeaning)) {
                    meaning = meaning + subMeaning;
                }

                logger.info("确定列：："+columnName+ "的语义完成，meaning =="+meaning);

                // 计算各种占比各种值
                double validProportion = MeaningUtils.validProportion(datas,Integer.valueOf(meaning));
                double invalidProportion = 1 - validProportion;

                // update dataset_schema表
                if (2 == meaning.length()){
                    datasetSchema.setMeaning(String.valueOf(meaning.charAt(0)));
                    datasetSchema.setSubMeaning(String.valueOf(meaning.charAt(1)));
                    meanings = new String[2];
                    meanings[0] = String.valueOf(meaning.charAt(0));
                    meanings[1] = String.valueOf(meaning.charAt(1));
                }else {
                    datasetSchema.setMeaning(meaning);
                    datasetSchema.setSubMeaning(null);
                    meanings = new String[1];
                    meanings[0] = meaning;
                }

                datasetSchema.setValidProportion((float) validProportion * 100);
                datasetSchema.setInvalidProportion((float) invalidProportion * 100);

                logger.info("更新数据库：");
                datasetSchemaMapper.updateByPrimaryKey(datasetSchema);

                Map<String,Object> result = new HashMap<>();
                result.put("meanings",meanings);
                result.put("validProportion",(float) validProportion * 100);
                result.put("invalidProportion",(float) invalidProportion * 100);
                resultCode = ResultUtil.success(result);
            }
        }
        return resultCode;
    }

    /**
     * 获取数据集字段名称 类型 及对应数据表名称
     *
     * @param datasetId
     * @return
     */
    @Override
    public ResultCode getStructure(Long datasetId) {
        ResultCode resultCode;
        // 获取数据集的所有字段
        List<Map<String, String>> columnMapList = resourceMapper.getDataSetColumnById(datasetId);
        for (Map<String, String> columnMap : columnMapList) {
            String columnType = columnMap.get("columnType");
            String typeName = TypeUtil.dataSet2Mysql(columnType);
            columnMap.put("columnTypeName", typeName);
        }
        //获取对应数据表名
        Dataset dataset = datasetMapper.selectByPrimaryKey(datasetId);
        String tableName = dataset.getTableName();
        String datasetName = dataset.getDatasetName();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("structure", columnMapList);
        resultMap.put("tableName", tableName);
        resultMap.put("datasetName", datasetName);
        resultCode = ResultUtil.success(resultMap);
        return resultCode;
    }
}
