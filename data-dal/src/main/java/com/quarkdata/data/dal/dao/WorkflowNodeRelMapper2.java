package com.quarkdata.data.dal.dao;

import java.util.List;

import com.quarkdata.data.model.vo.WorkflowNodeRelVO;
import org.apache.ibatis.annotations.Param;

public interface WorkflowNodeRelMapper2 {

	List<WorkflowNodeRelVO> getNodeRelList(Long workflowId);

	List<Long> getNodeRelId(@Param("workflowId") Long workflowId,
							@Param("datasetId") Long datasetId);
}
