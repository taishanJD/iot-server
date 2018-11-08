package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.Workflow;
import com.quarkdata.data.model.dataobj.WorkflowExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkflowMapper {
    long countByExample(WorkflowExample example);

    int deleteByExample(WorkflowExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Workflow record);

    int insertSelective(Workflow record);

    List<Workflow> selectByExampleWithBLOBs(WorkflowExample example);

    List<Workflow> selectByExample(WorkflowExample example);

    Workflow selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Workflow record, @Param("example") WorkflowExample example);

    int updateByExampleWithBLOBs(@Param("record") Workflow record, @Param("example") WorkflowExample example);

    int updateByExample(@Param("record") Workflow record, @Param("example") WorkflowExample example);

    int updateByPrimaryKeySelective(Workflow record);

    int updateByPrimaryKeyWithBLOBs(Workflow record);

    int updateByPrimaryKey(Workflow record);

    Workflow selectForUpdate(Long id);
}