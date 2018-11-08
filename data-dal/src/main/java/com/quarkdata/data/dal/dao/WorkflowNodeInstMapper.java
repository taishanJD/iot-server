package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.WorkflowNodeInst;
import com.quarkdata.data.model.dataobj.WorkflowNodeInstExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkflowNodeInstMapper {
    long countByExample(WorkflowNodeInstExample example);

    int deleteByExample(WorkflowNodeInstExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WorkflowNodeInst record);

    int insertSelective(WorkflowNodeInst record);

    List<WorkflowNodeInst> selectByExample(WorkflowNodeInstExample example);

    WorkflowNodeInst selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WorkflowNodeInst record, @Param("example") WorkflowNodeInstExample example);

    int updateByExample(@Param("record") WorkflowNodeInst record, @Param("example") WorkflowNodeInstExample example);

    int updateByPrimaryKeySelective(WorkflowNodeInst record);

    int updateByPrimaryKey(WorkflowNodeInst record);

    WorkflowNodeInst selectForUpdate(Long id);
}