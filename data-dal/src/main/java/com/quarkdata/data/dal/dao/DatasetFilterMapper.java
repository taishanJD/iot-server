package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.DatasetFilter;
import com.quarkdata.data.model.dataobj.DatasetFilterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DatasetFilterMapper {
    long countByExample(DatasetFilterExample example);

    int deleteByExample(DatasetFilterExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DatasetFilter record);

    int insertSelective(DatasetFilter record);

    List<DatasetFilter> selectByExample(DatasetFilterExample example);

    DatasetFilter selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DatasetFilter record, @Param("example") DatasetFilterExample example);

    int updateByExample(@Param("record") DatasetFilter record, @Param("example") DatasetFilterExample example);

    int updateByPrimaryKeySelective(DatasetFilter record);

    int updateByPrimaryKey(DatasetFilter record);

    DatasetFilter selectForUpdate(Long id);
}