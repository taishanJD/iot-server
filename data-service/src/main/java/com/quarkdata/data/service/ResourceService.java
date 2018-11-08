package com.quarkdata.data.service;

import java.util.List;
import java.util.Map;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.vo.SourceDataSetDetailVO;
import com.quarkdata.data.model.vo.SourceDataSetListVO;
import com.quarkdata.data.model.vo.SourceProjectDetailVO;
import com.quarkdata.data.model.vo.SourceProjectListVO;
import com.quarkdata.data.model.vo.SourceWorkflowDetailVO;
import com.quarkdata.data.model.vo.SourceWorkflowListVO;

/**
 * 系统菜单->资源管理接口
 * @author WangHao
 * 2018年5月17日
 */
public interface ResourceService {

	/**
	 * 功能： 获取租户下数据集、工作流和项目个数
	 * @param null
	 */
	ResultCode<Map<String, Object>> getSysResourceCount(String paramValue);

	/**
	 * 功能：获取租户下不同数据源包含的数据集个数
	 * @param null
	 */
	ResultCode<List<Map<String, Object>>> getDatesetCountBySource(String paramValue);

	/**
	 * 功能：获取租户下不同项目包含的数据集个数
	 * @param null
	 */
	ResultCode<List<Map<String, Object>>> getDatesetCountByProject(String paramValue);

	/**
	 * 获取租户下数据集列表
	 * @param type (Integer)   查询类型  0：全部查询 | 1：通过数据源查询 | 2：通过项目查询
	 * @param paramId (Long)  如果type不等于0 则填写对应的数据 ID
	 */
	ResultCode<List<SourceDataSetListVO>> getDatesetList(Integer type, Long paramId,String paramValue);

	/**
	 * 获取当前租户下工作流列表
	 * @param null
	 */
	ResultCode<List<SourceWorkflowListVO>> getWorkflowList(String paramValue);

	/**
	 * 获取当前租户下项目列表
	 * @param null
	 */
	ResultCode<List<SourceProjectListVO>> getProjectList(String paramValue);
	
	/**
	 * 获取数据集详情
	 * @param dataSetId (Long) 数据集ID
	 */
	ResultCode<SourceDataSetDetailVO> getDatesetDetail(Long dataSetId);

	/**
	 * 获取工作流详情
	 * @param workflowId （Long） 工作流ID
	 */
	ResultCode<SourceWorkflowDetailVO> getWorkflowDetail(Long workflowId);

	/**
	 * 获取项目详情
	 * @param projectId (Long) 项目ID
	 */
	ResultCode<SourceProjectDetailVO> getProjectDetail(Long projectId);


}
