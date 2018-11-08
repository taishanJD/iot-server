package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.SchedulerJobResponsible;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsibleExample;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsibleKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchedulerJobResponsibleMapper {
    long countByExample(SchedulerJobResponsibleExample example);

    int deleteByExample(SchedulerJobResponsibleExample example);

    int deleteByPrimaryKey(SchedulerJobResponsibleKey key);

    int insert(SchedulerJobResponsible record);

    int insertSelective(SchedulerJobResponsible record);

    List<SchedulerJobResponsible> selectByExample(SchedulerJobResponsibleExample example);

    SchedulerJobResponsible selectByPrimaryKey(SchedulerJobResponsibleKey key);

    int updateByExampleSelective(@Param("record") SchedulerJobResponsible record, @Param("example") SchedulerJobResponsibleExample example);

    int updateByExample(@Param("record") SchedulerJobResponsible record, @Param("example") SchedulerJobResponsibleExample example);

    int updateByPrimaryKeySelective(SchedulerJobResponsible record);

    int updateByPrimaryKey(SchedulerJobResponsible record);

    SchedulerJobResponsible selectForUpdate(SchedulerJobResponsibleKey key);
}