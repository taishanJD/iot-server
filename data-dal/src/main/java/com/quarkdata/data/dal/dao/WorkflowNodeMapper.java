package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.WorkflowNode;
import com.quarkdata.data.model.dataobj.WorkflowNodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkflowNodeMapper {
    long countByExample(WorkflowNodeExample example);

    int deleteByExample(WorkflowNodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WorkflowNode record);

    int insertSelective(WorkflowNode record);

    List<WorkflowNode> selectByExample(WorkflowNodeExample example);

    WorkflowNode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WorkflowNode record, @Param("example") WorkflowNodeExample example);

    int updateByExample(@Param("record") WorkflowNode record, @Param("example") WorkflowNodeExample example);

    int updateByPrimaryKeySelective(WorkflowNode record);

    int updateByPrimaryKey(WorkflowNode record);

    WorkflowNode selectForUpdate(Long id);
}