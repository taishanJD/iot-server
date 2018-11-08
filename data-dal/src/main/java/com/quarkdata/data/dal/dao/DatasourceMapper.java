package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.Datasource;
import com.quarkdata.data.model.dataobj.DatasourceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DatasourceMapper {
    long countByExample(DatasourceExample example);

    int deleteByExample(DatasourceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Datasource record);

    int insertSelective(Datasource record);

    List<Datasource> selectByExample(DatasourceExample example);

    Datasource selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Datasource record, @Param("example") DatasourceExample example);

    int updateByExample(@Param("record") Datasource record, @Param("example") DatasourceExample example);

    int updateByPrimaryKeySelective(Datasource record);

    int updateByPrimaryKey(Datasource record);

    Datasource selectForUpdate(Long id);
}