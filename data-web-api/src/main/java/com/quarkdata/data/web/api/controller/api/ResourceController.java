package com.quarkdata.data.web.api.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.vo.SourceDataSetDetailVO;
import com.quarkdata.data.model.vo.SourceDataSetListVO;
import com.quarkdata.data.model.vo.SourceProjectDetailVO;
import com.quarkdata.data.model.vo.SourceProjectListVO;
import com.quarkdata.data.model.vo.SourceWorkflowDetailVO;
import com.quarkdata.data.model.vo.SourceWorkflowListVO;
import com.quarkdata.data.service.ResourceService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.BaseController;
import com.quarkdata.data.web.api.controller.RouteKey;

/**
 * 菜单：资源管理 功能：资源管理下->系统部分->API接口
 * 
 * @author WangHao 2018年5月16日
 */
@RestController
@RequestMapping(RouteKey.PREFIX_API + RouteKey.RESOURCE)
public class ResourceController extends BaseController {

	@Autowired
	ResourceService resourceService;

	/**
	 * 获取租户下数据集、工作流和项目个数
	 * 
	 * @param null
	 */
	@RequestMapping(value = "/get_sys_resource_count", method = RequestMethod.GET)
	public ResultCode<Map<String, Object>> getSysResourceCount(
			@RequestParam(value = "paramValue", required = false) String paramValue) {
		return resourceService.getSysResourceCount(paramValue);
	}

	/**
	 * 获取租户下不同数据源中包含的数据集个数
	 * 
	 * @param null
	 */
	@RequestMapping(value = "/get_dataset_by_datasource", method = RequestMethod.GET)
	public ResultCode<List<Map<String, Object>>> getDatesetCountBySource(
			@RequestParam(value = "paramValue", required = false) String paramValue) {
		return resourceService.getDatesetCountBySource(paramValue);
	}

	/**
	 * 获取租户下不同项目中包含的数据集个数
	 * 
	 * @param null
	 */
	@RequestMapping(value = "/get_dataset_by_project", method = RequestMethod.GET)
	public ResultCode<List<Map<String, Object>>> getDatesetCountByProject(
			@RequestParam(value = "paramValue", required = false) String paramValue) {
		return resourceService.getDatesetCountByProject(paramValue);
	}

	/**
	 * 获取租户下数据集列表
	 * 
	 * @param type
	 *            (Integer) 查询类型 0：全部查询 | 1：通过数据源查询 | 2：通过项目查询
	 * @param paramId
	 *            (Long) 如果type不等于0 则填写对应的数据 ID
	 */
	@RequestMapping(value = "/get_dataset_list", method = RequestMethod.GET)
	public ResultCode<List<SourceDataSetListVO>> getDatesetList(
			@RequestParam(value = "type", required = true) Integer type,
			@RequestParam(value = "paramId", required = false) Long paramId,
			@RequestParam(value = "paramValue", required = false) String paramValue) {
		if (null == type) {
			return ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
		}
		return resourceService.getDatesetList(type, paramId, paramValue);
	}

	/**
	 * 获取当前租户下工作流列表
	 * 
	 * @param null
	 */
	@RequestMapping(value = "/get_workflow_list", method = RequestMethod.GET)
	public ResultCode<List<SourceWorkflowListVO>> getWorkflowList(
			@RequestParam(value = "paramValue", required = false) String paramValue) {
		return resourceService.getWorkflowList(paramValue);
	}

	/**
	 * 获取当前租户下项目列表
	 * 
	 * @param null
	 */
	@RequestMapping(value = "/get_project_list", method = RequestMethod.GET)
	public ResultCode<List<SourceProjectListVO>> getProjectList(
			@RequestParam(value = "paramValue", required = false) String paramValue) {
		return resourceService.getProjectList(paramValue);
	}

	/**
	 * 获取数据集详情
	 * 
	 * @param dataSetId
	 *            (Long) 数据集ID
	 */
	@RequestMapping(value = "/get_dataset_detail", method = RequestMethod.GET)
	public ResultCode<SourceDataSetDetailVO> getDatesetDetail(
			@RequestParam(value = "dataSetId", required = true) Long dataSetId) {
		if (null == dataSetId) {
			return ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
		}
		return resourceService.getDatesetDetail(dataSetId);
	}

	/**
	 * 获取工作流详情
	 * 
	 * @param workflowId
	 *            (Long) 工作流ID
	 */
	@RequestMapping(value = "/get_workflow_detail", method = RequestMethod.GET)
	public ResultCode<SourceWorkflowDetailVO> getWorkflowDetail(
			@RequestParam(value = "workflowId", required = true) Long workflowId) {
		if (null == workflowId) {
			return ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
		}
		return resourceService.getWorkflowDetail(workflowId);
	}

	/**
	 * 获取项目详情
	 * 
	 * @param projectId
	 *            (Long) 项目ID
	 */
	@RequestMapping(value = "/get_project_detail", method = RequestMethod.GET)
	public ResultCode<SourceProjectDetailVO> getProjectDetail(
			@RequestParam(value = "projectId", required = true) Long projectId) {
		if (null == projectId) {
			return ResultUtil.error(Messages.MISSING_INPUT_CODE, Messages.MISSING_INPUT_MSG);
		}
		return resourceService.getProjectDetail(projectId);
	}
}
