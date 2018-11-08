package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.WorkflowNodeRel;
import com.quarkdata.data.model.dataobj.WorkflowNodeRelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkflowNodeRelMapper {
    long countByExample(WorkflowNodeRelExample example);

    int deleteByExample(WorkflowNodeRelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WorkflowNodeRel record);

    int insertSelective(WorkflowNodeRel record);

    List<WorkflowNodeRel> selectByExample(WorkflowNodeRelExample example);

    WorkflowNodeRel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WorkflowNodeRel record, @Param("example") WorkflowNodeRelExample example);

    int updateByExample(@Param("record") WorkflowNodeRel record, @Param("example") WorkflowNodeRelExample example);

    int updateByPrimaryKeySelective(WorkflowNodeRel record);

    int updateByPrimaryKey(WorkflowNodeRel record);

    WorkflowNodeRel selectForUpdate(Long id);
}