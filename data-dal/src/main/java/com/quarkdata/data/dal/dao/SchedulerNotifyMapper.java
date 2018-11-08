package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.SchedulerNotify;
import com.quarkdata.data.model.dataobj.SchedulerNotifyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchedulerNotifyMapper {
    long countByExample(SchedulerNotifyExample example);

    int deleteByExample(SchedulerNotifyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SchedulerNotify record);

    int insertSelective(SchedulerNotify record);

    List<SchedulerNotify> selectByExample(SchedulerNotifyExample example);

    SchedulerNotify selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SchedulerNotify record, @Param("example") SchedulerNotifyExample example);

    int updateByExample(@Param("record") SchedulerNotify record, @Param("example") SchedulerNotifyExample example);

    int updateByPrimaryKeySelective(SchedulerNotify record);

    int updateByPrimaryKey(SchedulerNotify record);

    SchedulerNotify selectForUpdate(Long id);
}