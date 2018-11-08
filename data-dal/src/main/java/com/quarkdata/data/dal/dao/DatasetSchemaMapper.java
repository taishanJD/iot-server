package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.DatasetSchema;
import com.quarkdata.data.model.dataobj.DatasetSchemaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DatasetSchemaMapper {
    long countByExample(DatasetSchemaExample example);

    int deleteByExample(DatasetSchemaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DatasetSchema record);

    int insertSelective(DatasetSchema record);

    List<DatasetSchema> selectByExample(DatasetSchemaExample example);

    DatasetSchema selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DatasetSchema record, @Param("example") DatasetSchemaExample example);

    int updateByExample(@Param("record") DatasetSchema record, @Param("example") DatasetSchemaExample example);

    int updateByPrimaryKeySelective(DatasetSchema record);

    int updateByPrimaryKey(DatasetSchema record);

    DatasetSchema selectForUpdate(Long id);
}