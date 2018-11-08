package com.quarkdata.data.dal.dao;

import com.quarkdata.data.model.dataobj.WorkflowInst;
import com.quarkdata.data.model.dataobj.WorkflowInstExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WorkflowInstMapper {
    long countByExample(WorkflowInstExample example);

    int deleteByExample(WorkflowInstExample example);

    int deleteByPrimaryKey(Long id);

    int insert(WorkflowInst record);

    int insertSelective(WorkflowInst record);

    List<WorkflowInst> selectByExample(WorkflowInstExample example);

    WorkflowInst selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") WorkflowInst record, @Param("example") WorkflowInstExample example);

    int updateByExample(@Param("record") WorkflowInst record, @Param("example") WorkflowInstExample example);

    int updateByPrimaryKeySelective(WorkflowInst record);

    int updateByPrimaryKey(WorkflowInst record);

    WorkflowInst selectForUpdate(Long id);
}