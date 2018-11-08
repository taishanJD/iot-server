package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.RunningLog;
import com.quarkdata.data.model.dataobj.RunningLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RunningLogMapper {
    long countByExample(RunningLogExample example);

    int deleteByExample(RunningLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RunningLog record);

    int insertSelective(RunningLog record);

    List<RunningLog> selectByExampleWithBLOBs(RunningLogExample example);

    List<RunningLog> selectByExample(RunningLogExample example);

    RunningLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RunningLog record, @Param("example") RunningLogExample example);

    int updateByExampleWithBLOBs(@Param("record") RunningLog record, @Param("example") RunningLogExample example);

    int updateByExample(@Param("record") RunningLog record, @Param("example") RunningLogExample example);

    int updateByPrimaryKeySelective(RunningLog record);

    int updateByPrimaryKeyWithBLOBs(RunningLog record);

    int updateByPrimaryKey(RunningLog record);

    RunningLog selectForUpdate(Long id);
}