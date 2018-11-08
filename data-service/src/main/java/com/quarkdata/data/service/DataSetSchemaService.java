package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;

public interface DataSetSchemaService {

    /**
     * 获取数据集结构，从dataset_schema查询
     *
     * @param dataSetId 数据集id
     * @param filter 字段名过滤
     * @param columnType 类型过滤
     * @param meaning 语义过滤
     * @param subMeaning 语义过滤
     * @return {
     *     "msg": "success",
     *     "code": 0,
     *     "data": [
     *         {
     *             “id”:1,
     *            “dataSetId”:121,
     *           “columnName”:”id”,
     *           “columnType”:1,
     *           “columnLength”:10,
     *           “columnComment”:”ddddadadsa”,
     *           “meaning”: 1,
     *           “proportion”:0.50
     *         },{},{},...
     *     ]
     * }
     */
    ResultCode getSchema(Long dataSetId, String filter, String columnType, String meaning, String subMeaning);


    /**
     * 检查数据集表结构与源表是否一致，主要检查字段有没有增减，字段名称有没有改动
     *
     * @param dataSetId
     * @return
     */
    ResultCode checkSchema(Long dataSetId);

    /**
     * 删除旧的表结构，拉取并添加新的表结构
     *
     * @param dataSetId
     * @return
     */
    ResultCode reloadSchema(Long dataSetId);

    /**
     * 更新数据集列类型
     *
     * @param dataSetId
     * @param columnName
     * @param columnType 数据集列类型( 0:tinyint(8 bit), 1:smallint(16 bit), 2:int, 3:bigint(64 bit), 4:float, 5:double, 6:boolean, 7:string, 8:date)
     * @return
     */
    ResultCode updateSchemaType(Long dataSetId,String columnName,String columnType);

    /**
     * 更新数据集列语义和语义占比
     *
     * @param dataSetId
     * @param meaning
     * @param subMeaning
     * @return
     */
    ResultCode updateSchemaMeaning(Long dataSetId,String columnName,String meaning,String subMeaning);

    /**
     * 获取数据集字段名称 类型 及对应数据表名称
     * @param datasetId
     * @return
     */
    ResultCode getStructure(Long datasetId);
}
