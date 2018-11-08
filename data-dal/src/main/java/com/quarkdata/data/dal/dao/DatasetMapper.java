package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.DatasetExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DatasetMapper {
    long countByExample(DatasetExample example);

    int deleteByExample(DatasetExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Dataset record);

    int insertSelective(Dataset record);

    List<Dataset> selectByExampleWithBLOBs(DatasetExample example);

    List<Dataset> selectByExample(DatasetExample example);

    Dataset selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Dataset record, @Param("example") DatasetExample example);

    int updateByExampleWithBLOBs(@Param("record") Dataset record, @Param("example") DatasetExample example);

    int updateByExample(@Param("record") Dataset record, @Param("example") DatasetExample example);

    int updateByPrimaryKeySelective(Dataset record);

    int updateByPrimaryKeyWithBLOBs(Dataset record);

    int updateByPrimaryKey(Dataset record);

    Dataset selectForUpdate(Long id);
}