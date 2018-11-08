package com.quarkdata.data.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.dal.dao.ResourceMapper;
import com.quarkdata.data.dal.rest.quarkshare.UserApi;
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
import com.quarkdata.data.util.TypeUtil;
import com.quarkdata.tenant.model.dataobj.Tenant;
import com.quarkdata.tenant.model.dataobj.User;

/**
 * 系统菜单->资源管理接口
 * 
 * @author WangHao 2018年5月17日
 */

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	ResourceMapper resourceMapper;

	private UserApi userApi = new UserApi();

	@Override
	public ResultCode getSysResourceCount(String paramValue) {
		ResultCode<Map<String, Object>> resultCode = null;
		// 获取当前租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		if (null == tenant) {
			resultCode = ResultUtil.error(Messages.API_AUTHENTICATION_FAILED_CODE,
					Messages.API_AUTHENTICATION_FAILED_MSG);
			return resultCode;
		}
		Long tenantId = tenant.getId();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取当前租户数据集列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenantId);
		paramMap.put("type", 0);
		paramMap.put("paramId", null);
		paramMap.put("paramValue", paramValue);
		List<SourceDataSetListVO> dataSetList = resourceMapper.getDataSetListByTenantId(paramMap);
		resultMap.put("datasetNum", dataSetList.size());
		// 获取当前租户工作流列表
		List<SourceWorkflowListVO> workflowList = resourceMapper.getWorkFlowListByTenantId(paramMap);
		resultMap.put("workflowNum", workflowList.size());
		// 获取当前租户项目列表
		List<SourceProjectListVO> projectList = resourceMapper.getProjectListByTenantId(paramMap);
		resultMap.put("projectNum", projectList.size());
		resultCode = ResultUtil.success(resultMap);
		return resultCode;
	}

	@Override
	public ResultCode<List<Map<String, Object>>> getDatesetCountBySource(String paramValue) {
		ResultCode<List<Map<String, Object>>> resultCode = null;
		// 获取当前租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		if (null == tenant) {
			resultCode = ResultUtil.error(Messages.API_AUTHENTICATION_FAILED_CODE,
					Messages.API_AUTHENTICATION_FAILED_MSG);
			return resultCode;
		}
		Long tenantId = tenant.getId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenantId);
		paramMap.put("paramValue", paramValue);
		List<Map<String, Object>> sourceDataSetCountMap = resourceMapper.getDatesetCountInSource(paramMap);
		resultCode = ResultUtil.success(sourceDataSetCountMap);
		return resultCode;
	}

	@Override
	public ResultCode<List<Map<String, Object>>> getDatesetCountByProject(String paramValue) {
		ResultCode<List<Map<String, Object>>> resultCode = null;
		// 获取当前租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		if (null == tenant) {
			resultCode = ResultUtil.error(Messages.API_AUTHENTICATION_FAILED_CODE,
					Messages.API_AUTHENTICATION_FAILED_MSG);
			return resultCode;
		}
		Long tenantId = tenant.getId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenantId);
		paramMap.put("paramValue", paramValue);
		List<Map<String, Object>> sourceDataSetCountMap = resourceMapper.getDatesetCountInProject(paramMap);
		resultCode = ResultUtil.success(sourceDataSetCountMap);
		return resultCode;
	}

	@Override
	public ResultCode<List<SourceDataSetListVO>> getDatesetList(Integer type, Long paramId, String paramValue) {
		ResultCode<List<SourceDataSetListVO>> resultCode = null;
		// 获取当前租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		if (null == tenant) {
			resultCode = ResultUtil.error(Messages.API_AUTHENTICATION_FAILED_CODE,
					Messages.API_AUTHENTICATION_FAILED_MSG);
			return resultCode;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenant.getId());
		paramMap.put("type", type);
		paramMap.put("paramId", paramId);
		paramMap.put("paramValue", paramValue);
		List<SourceDataSetListVO> dataSetList = resourceMapper.getDataSetListByTenantId(paramMap);
		resultCode = ResultUtil.success(dataSetList);
		return resultCode;
	}

	@Override
	public ResultCode<List<SourceWorkflowListVO>> getWorkflowList(String paramValue) {
		ResultCode<List<SourceWorkflowListVO>> resultCode = null;
		// 获取当前租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		if (null == tenant) {
			resultCode = ResultUtil.error(Messages.API_AUTHENTICATION_FAILED_CODE,
					Messages.API_AUTHENTICATION_FAILED_MSG);
			return resultCode;
		}
		Long tenantId = tenant.getId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenantId);
		paramMap.put("paramValue", paramValue);
		List<SourceWorkflowListVO> workflowList = resourceMapper.getWorkFlowListByTenantId(paramMap);
		return ResultUtil.success(workflowList);
	}

	@Override
	public ResultCode<List<SourceProjectListVO>> getProjectList(String paramValue) {
		ResultCode<List<SourceProjectListVO>> resultCode = null;
		// 获取当前租户信息
		Tenant tenant = UserInfoUtil.getIncorporation();
		if (null == tenant) {
			resultCode = ResultUtil.error(Messages.API_AUTHENTICATION_FAILED_CODE,
					Messages.API_AUTHENTICATION_FAILED_MSG);
			return resultCode;
		}
		Long tenantId = tenant.getId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tenantId", tenantId);
		paramMap.put("paramValue", paramValue);
		List<SourceProjectListVO> projectList = resourceMapper.getProjectListByTenantId(paramMap);
		return ResultUtil.success(projectList);
	}

	@Override
	public ResultCode<SourceDataSetDetailVO> getDatesetDetail(Long dataSetId) {
		// 获取数据集详情
		SourceDataSetDetailVO dataSetDetail = resourceMapper.getDataSetDetailById(dataSetId);
		Long createUserId = dataSetDetail.getCreateUserId();
		Long updateUserId = dataSetDetail.getUpdateUserId();
		StringBuffer sb = new StringBuffer();
		sb.append(createUserId.toString() + ",");
		sb.append(updateUserId.toString());
		ResultCode<List<User>> usersByIdsResult = userApi.getUsersByIds(sb.toString());
		List<User> userList = usersByIdsResult.getData();
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				if (user.getId() == dataSetDetail.getCreateUserId()) {
					dataSetDetail.setCreateUserName(user.getName());
				}
				if (user.getId() == dataSetDetail.getUpdateUserId()) {
					dataSetDetail.setUpdateUserName(user.getName());
				}
			}
		}
		// 获取数据集的所有字段
		List<Map<String, String>> columnMapList = resourceMapper.getDataSetColumnById(dataSetId);
		for (Map<String, String> columnMap : columnMapList) {
			String columnType = columnMap.get("columnType");
			String typeName = TypeUtil.typeConversion(columnType);
			columnMap.put("columnTypeName", typeName);
		}
		dataSetDetail.setColumnList(columnMapList);
		// 获取当前数据集上级数据集
		List<Map<String, Object>> parentDataSetList = resourceMapper.getDataSetParentDataSet(dataSetId);
		dataSetDetail.setParentDatesetList(parentDataSetList);
		// 获取当前数据集下级数据集
		List<Map<String, Object>> childDataSetList = resourceMapper.getDataSetChildDataSet(dataSetId);
		dataSetDetail.setChildDatesetList(childDataSetList);
		return ResultUtil.success(dataSetDetail);
	}

	@Override
	public ResultCode<SourceWorkflowDetailVO> getWorkflowDetail(Long workflowId) {
		SourceWorkflowDetailVO sourceWorkflowDetailVO = resourceMapper.getWorkflowDetail(workflowId);
		Long createUserId = sourceWorkflowDetailVO.getCreateUserId();
		Long updateUserId = sourceWorkflowDetailVO.getUpdateUserId();
		StringBuffer sb = new StringBuffer();
		sb.append(createUserId.toString() + ",");
		sb.append(updateUserId.toString());
		ResultCode<List<User>> usersByIdsResult = userApi.getUsersByIds(sb.toString());
		List<User> userList = usersByIdsResult.getData();
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				if (user.getId() == sourceWorkflowDetailVO.getCreateUserId()) {
					sourceWorkflowDetailVO.setCreateUserName(user.getName());
				}
				if (user.getId() == sourceWorkflowDetailVO.getUpdateUserId()) {
					sourceWorkflowDetailVO.setUpdateUserName(user.getName());
				}
			}
		}
		return ResultUtil.success(sourceWorkflowDetailVO);
	}

	@Override
	public ResultCode<SourceProjectDetailVO> getProjectDetail(Long projectId) {
		SourceProjectDetailVO sourceProjectDetailVO = resourceMapper.getProjectDetail(projectId);
		Long createUserId = sourceProjectDetailVO.getCreateUserId();
		Long updateUserId = sourceProjectDetailVO.getUpdateUserId();
		StringBuffer sb = new StringBuffer();
		sb.append(createUserId.toString() + ",");
		sb.append(updateUserId.toString());
		ResultCode<List<User>> usersByIdsResult = userApi.getUsersByIds(sb.toString());
		List<User> userList = usersByIdsResult.getData();
		if (userList != null && userList.size() > 0) {
			for (User user : userList) {
				if (user.getId() == sourceProjectDetailVO.getCreateUserId()) {
					sourceProjectDetailVO.setCreateUserName(user.getName());
				}
				if (user.getId() == sourceProjectDetailVO.getUpdateUserId()) {
					sourceProjectDetailVO.setUpdateUserName(user.getName());
				}
			}
		}
		return ResultUtil.success(sourceProjectDetailVO);
	}

}
