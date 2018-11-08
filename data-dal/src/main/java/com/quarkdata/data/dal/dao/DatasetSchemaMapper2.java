package com.quarkdata.data.dal.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DatasetSchemaMapper2 {


    /**
     * 根据dataSetId获取字段名称列表
     *
     * @param dataSetId
     * @return
     */
    List<String> getColumnNames(@Param("dataSetId") Long dataSetId);

    /**
//     * 根据dataSetId和columnName更新某一个字段的列类型
//     *
//     * @param dataSetId
//     * @param columnName
//     * @param columnType
//     */
//    void updateColumnType(@Param("dataSetId") Long dataSetId,@Param("columnName")String columnName,@Param("columnType")String columnType);

    String getColumnType(@Param("dataSetId") Long dataSetId,@Param("columnName")String columnName);
}
