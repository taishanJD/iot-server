package com.quarkdata.data.dal.dao;

import java.util.List;
import java.util.Map;

import com.quarkdata.data.model.vo.SourceDataSetDetailVO;
import com.quarkdata.data.model.vo.SourceDataSetListVO;
import com.quarkdata.data.model.vo.SourceProjectDetailVO;
import com.quarkdata.data.model.vo.SourceProjectListVO;
import com.quarkdata.data.model.vo.SourceWorkflowDetailVO;
import com.quarkdata.data.model.vo.SourceWorkflowListVO;

public interface ResourceMapper {

	/**
	 * 根据租户ID获取当前租户下所有的数据集
	 * @param tenantId Long 租户ID
	 */
	List<SourceDataSetListVO> getDataSetListByTenantId(Map<String, Object> paramMap);

	/**
	 * 根据租户ID获取当前租户下所有的工作流
	 * @param tenantId Long 租户ID
	 */
	List<SourceWorkflowListVO> getWorkFlowListByTenantId(Map<String, Object> paramMap);
	
	/**
	 * 根据租户ID获取当前租户下所有的项目
	 * @param tenantId Long 租户ID
	 */
	List<SourceProjectListVO> getProjectListByTenantId(Map<String, Object> paramMap);

	/**
	 * 根据租户ID获取各数据源下包含数据集
	 * @param tenantId Long 租户ID
	 */
	List<Map<String, Object>> getDatesetCountInSource(Map<String, Object> paramMap);
	
	/**
	 * 根据租户ID获取各项目下包含数据集
	 * @param tenantId Long 租户ID
	 */
	List<Map<String, Object>> getDatesetCountInProject(Map<String, Object> paramMap);

	/**
	 * 获取数据集详情
	 * @param tenantId Long 数据集ID
	 */
	SourceDataSetDetailVO getDataSetDetailById(Long dataSetId);

	/**
	 * 获取数据集所有的字段
	 * @param tenantId Long 数据集ID
	 */
	List<Map<String, String>> getDataSetColumnById(Long dataSetId);

	/**
	 * 获取当前数据集上级数据集列表
	 * @param tenantId Long 数据集ID
	 */
	List<Map<String, Object>> getDataSetParentDataSet(Long dataSetId);

	/**
	 * 获取当前数据集下级数据集列表
	 * @param tenantId Long 数据集ID
	 */
	List<Map<String, Object>> getDataSetChildDataSet(Long dataSetId);

	/**
	 * 获取工作流详情
	 * @param workflowId Long 工作流ID
	 */
	SourceWorkflowDetailVO getWorkflowDetail(Long workflowId);

	/**
	 * 获取项目详情
	 * @param projectId Long 项目ID
	 */
	SourceProjectDetailVO getProjectDetail(Long projectId);

}
