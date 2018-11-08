package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.SchedulerJob;
import com.quarkdata.data.model.dataobj.SchedulerJobExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchedulerJobMapper {
    long countByExample(SchedulerJobExample example);

    int deleteByExample(SchedulerJobExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SchedulerJob record);

    int insertSelective(SchedulerJob record);

    List<SchedulerJob> selectByExample(SchedulerJobExample example);

    SchedulerJob selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SchedulerJob record, @Param("example") SchedulerJobExample example);

    int updateByExample(@Param("record") SchedulerJob record, @Param("example") SchedulerJobExample example);

    int updateByPrimaryKeySelective(SchedulerJob record);

    int updateByPrimaryKey(SchedulerJob record);

    SchedulerJob selectForUpdate(Long id);
}