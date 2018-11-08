package com.quarkdata.data.dal.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.quarkdata.data.model.vo.WorkflowExtend;

public interface WorkflowMapper2 {

	List<WorkflowExtend> getWorkflowList(
			@Param("tenantId") Long tenantId,
			@Param("projectId") Long projectId,
			@Param("name") String name,
			@Param("limitStart") int limitStart,
			@Param("limitEnd") int limitEnd);
	
	Long getWorkflowListCount(
			@Param("tenantId") Long tenantId,
			@Param("projectId") Long projectId,
			@Param("name") String name);
}
