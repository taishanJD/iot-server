package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.SchedulerTask;
import com.quarkdata.data.model.dataobj.SchedulerTaskExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchedulerTaskMapper {
    long countByExample(SchedulerTaskExample example);

    int deleteByExample(SchedulerTaskExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SchedulerTask record);

    int insertSelective(SchedulerTask record);

    List<SchedulerTask> selectByExample(SchedulerTaskExample example);

    SchedulerTask selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SchedulerTask record, @Param("example") SchedulerTaskExample example);

    int updateByExample(@Param("record") SchedulerTask record, @Param("example") SchedulerTaskExample example);

    int updateByPrimaryKeySelective(SchedulerTask record);

    int updateByPrimaryKey(SchedulerTask record);

    SchedulerTask selectForUpdate(Long id);
}