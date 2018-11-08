package com.quarkdata.data.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.model.vo.DataSetListVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quarkdata.data.dal.rest.quarkshare.UserApi;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.OperateObjectTypeConstants;
import com.quarkdata.data.model.common.OperateTypeConstants;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.Dataset;
import com.quarkdata.data.model.dataobj.DatasetExample;
import com.quarkdata.data.model.dataobj.Project;
import com.quarkdata.data.model.dataobj.ProjectComment;
import com.quarkdata.data.model.dataobj.ProjectCommentExample;
import com.quarkdata.data.model.dataobj.ProjectExample;
import com.quarkdata.data.model.dataobj.ProjectTag;
import com.quarkdata.data.model.dataobj.ProjectTagExample;
import com.quarkdata.data.model.dataobj.ProjectTagRelExample;
import com.quarkdata.data.model.dataobj.ProjectTagRelKey;
import com.quarkdata.data.model.dataobj.ProjectTimeline;
import com.quarkdata.data.model.dataobj.ProjectTimelineExample;
import com.quarkdata.data.model.dataobj.ProjectWithBLOBs;
import com.quarkdata.data.model.dataobj.Workflow;
import com.quarkdata.data.model.dataobj.WorkflowExample;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.service.ProjectService;
import com.quarkdata.data.service.ProjectTimelineService;
import com.quarkdata.data.service.WorkflowService;
import com.quarkdata.data.util.ResultUtil;

@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectMapper projectMapper;

	@Autowired
	ProjectMapper2 projectMapper2;

	@Autowired
	ProjectTagMapper projectTagMapper;

	@Autowired
	ProjectTagMapper2 projectTagMapper2;

	@Autowired
	ProjectTagRelMapper projectTagRelMapper;

	@Autowired
	DatasetMapper datasetMapper;

	@Autowired
	DatasetMapper2 datasetMapper2;

	@Autowired
	SchedulerJobMapper schedulerJobMapper;

	@Autowired
	WorkflowMapper workflowMapper;

	@Autowired
	ProjectCommentMapper projectCommentMapper;

	@Autowired
	ProjectTimelineMapper projectTimelineMapper;

	@Autowired
	ProjectTimelineService projectTimelineService;

	@Autowired
	DataSetService dataSetService;

	@Autowired
	WorkflowService workflowService;

	static Logger logger = Logger.getLogger(ProjectServiceImpl.class);

	/**
	 * 添加项目
	 *
	 * @param projectName
	 *            项目名称
	 * @param projectKey
	 *            项目标识
	 * @param projectPicture
	 *            项目图片
	 * @param tenantId
	 *            租户id
	 * @param createUser
	 *            创建人id
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode addProject(String projectName, String projectKey, MultipartFile projectPicture, Long tenantId,
			Long createUser, String createUserName) throws IOException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(now);
		ResultCode resultCode;
		ProjectWithBLOBs project = new ProjectWithBLOBs();
		resultCode = validateProject(tenantId,project, projectName, projectKey);
		if (0 == resultCode.getCode()) {
			project.setTenantId(tenantId);
			project.setProjectStatus("0");
			project.setProjectInfo("项目 " + projectName + " 由 " + createUserName + " 于 " + time + " 创建");
			project.setProjectPicture(projectPicture.getBytes());
			project.setIsDel("0");
			project.setCreateTime(now);
			project.setCreateUser(createUser);
			logger.info("开始创建项目 " + project.getProjectName());
			projectMapper.insertSelective(project);
			resultCode = update(project.getId(), tenantId, createUser);
			if (0 == resultCode.getCode()) {
				projectTimelineService.insertOptLog(project.getId(), tenantId, OperateTypeConstants.EDITED,
						OperateObjectTypeConstants.PROJECT, null, project.getId(), projectName, createUser,
						createUserName);
				resultCode = ResultUtil.success(project.getId());
			}
			logger.info("项目 " + project.getId() + " 创建完成");
		}
		return resultCode;
	}

	/**
	 * 验证项目名称和标识是否重复
	 * 
	 * @param project
	 *            项目
	 * @param projectName
	 *            项目名称
	 * @param projectKey
	 *            项目标识
	 * @return
	 */
	private ResultCode validateProject(Long tenantId,ProjectWithBLOBs project, String projectName, String projectKey) {
		ResultCode resultCode;
		ProjectExample example1 = new ProjectExample();
		example1.createCriteria().andTenantIdEqualTo(tenantId).andProjectNameEqualTo(projectName).andIsDelEqualTo("0");
				//andProjectNameEqualTo(projectName).andIsDelEqualTo("0");
		List<Project> projects1 = projectMapper.selectByExample(example1);
		if (projects1.isEmpty()) {
			project.setProjectName(projectName);
		} else {
			resultCode = ResultUtil.error(Messages.PROJECT_NAME_REPEAT_CODE, Messages.PROJECT_NAME_REPEAT_MSG);
			return resultCode;
		}
		ProjectExample example2 = new ProjectExample();
		example2.createCriteria().andTenantIdEqualTo(tenantId).andProjectKeyEqualTo(projectKey).andIsDelEqualTo("0");
		List<Project> projects2 = projectMapper.selectByExample(example2);
		if (projects2.isEmpty()) {
			project.setProjectKey(projectKey);
		} else {
			resultCode = ResultUtil.error(Messages.PROJECT_KEY_REPEAT_CODE, Messages.PROJECT_KEY_REPEAT_MSG);
			return resultCode;
		}
		resultCode = ResultUtil.success();
		return resultCode;
	}

	/**
	 * 编辑项目
	 *
	 * @param projectId
	 *            项目id
	 * @param projectName
	 *            项目名称（非）
	 * @param projectInfo
	 *            项目简介（非）
	 * @param projectDesc
	 *            项目描述（非）
	 * @param updateUser
	 *            修改人id
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode updateProject(Long projectId, String projectName, String projectInfo, String projectDesc,
			String projectStatus, Long tenantId, Long updateUser, String updateUserName) {
		ResultCode resultCode;
		if (null != projectName) {
			resultCode = validateProject2(projectId, projectName);
			if (0 == resultCode.getCode()) {
				projectMapper2.updateByParameter(projectId, projectName, projectInfo, projectDesc, projectStatus,
						tenantId, updateUser, new Date());
			} else {
				return resultCode;
			}
		} else {
			projectMapper2.updateByParameter(projectId, projectName, projectInfo, projectDesc, projectStatus, tenantId,
					updateUser, new Date());
		}
		Project project = projectMapper.selectByPrimaryKey(projectId);
		project.setUpdateTime(new Date());
		project.setUpdateUser(updateUser);
		projectMapper.updateByPrimaryKey(project);
		projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
				OperateObjectTypeConstants.PROJECT, null, projectId, projectName, updateUser, updateUserName);
		resultCode = ResultUtil.success();
		return resultCode;
	}

	/**
	 * 验证项目名称是否重复
	 * 
	 * @param projectId
	 *            项目id
	 * @param projectName
	 *            项目名称
	 * @return
	 */
	private ResultCode validateProject2(Long projectId, String projectName) {
		ResultCode resultCode;
		ProjectExample example = new ProjectExample();
		example.createCriteria().andProjectNameEqualTo(projectName).andIsDelEqualTo("0").andIdNotEqualTo(projectId);
		List<Project> projects1 = projectMapper.selectByExample(example);
		if (projects1.isEmpty()) {
			resultCode = ResultUtil.success();
		} else {
			resultCode = ResultUtil.error(Messages.PROJECT_NAME_REPEAT_CODE, Messages.PROJECT_NAME_REPEAT_MSG);
		}
		return resultCode;
	}

	/**
	 * 获取项目详情
	 * 
	 * @param projectId
	 *            项目id
	 * @param tenantId
	 *            租户id
	 * @return
	 */
	@Override
	public ResultCode getDetails(Long projectId, Long tenantId) {
		ResultCode resultCode;
		Map<String, Object> resultMap = new HashMap<>();
		ProjectWithBLOBs project = projectMapper2.selectProject(projectId, tenantId);
		resultMap.put("project", project);
		Map<String, Object> objectCount = getObjectCount(projectId, tenantId);
		resultMap.put("objectCount", objectCount);
		List<ProjectTag> tagList = projectTagMapper2.selectTagByProjectId(projectId, tenantId);
		resultMap.put("tags", tagList);
		resultCode = ResultUtil.success(resultMap);
		return resultCode;
	}

	/**
	 * 获取项目下数据集、工作流、任务数
	 * 
	 * @param projectId
	 * @param tenantId
	 * @return
	 */
	private Map<String, Object> getObjectCount(Long projectId, Long tenantId) {
		Map<String, Object> resultMap = new HashMap<>(3);
		List<DataSetListVO> list = datasetMapper2.getDataSetList(projectId, null,null);
		WorkflowExample workflowExample = new WorkflowExample();
		workflowExample.createCriteria().andTenantIdEqualTo(tenantId).andProjectIdEqualTo(projectId).andIsDelEqualTo("0");
		long workflowNum = workflowMapper.countByExample(workflowExample);
		long taskNum = projectMapper2.countTask(projectId, tenantId);
		resultMap.put("datasetNum", list.size());
		resultMap.put("workflowNum", workflowNum);
		resultMap.put("taskNum", taskNum);
		return resultMap;
	}

	/**
	 * 获取项目列表
	 * 
	 * @param tenantId
	 *            租户id
	 * @param filter
	 *            过滤条件
	 * @return
	 */
	@Override
	public ResultCode getList(Long tenantId, String filter, String[] status, Long[] tag, Long[] user, String sortType,
			String sortMethod) {
		ResultCode resultCode;
		List<ProjectWithBLOBs> projectList = projectMapper2.getList(tenantId, filter, status, tag, user, sortType,
				sortMethod);
		resultCode = ResultUtil.success(projectList);
		return resultCode;
	}

	/**
	 * 编辑标签
	 *
	 * @param projectId
	 *            项目id
	 * @param tagNames
	 *            标签名
	 * @param tenantId
	 *            租户id
	 * @param updateUser
	 *            操作人id
	 * @return
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false, rollbackFor = Exception.class) public
	 * ResultCode editTag(Long projectId, String[] tagNames, Long tenantId, Long
	 * updateUser, String updateUserName) { ResultCode resultCode;
	 * //删除tag_rel表中该项目的所有标签 ProjectTagRelExample tagRelExample = new
	 * ProjectTagRelExample();
	 * tagRelExample.createCriteria().andProjectIdEqualTo(projectId);
	 * projectTagRelMapper.deleteByExample(tagRelExample); //删除tag表中未被其他项目使用过的标签
	 * List<Long> tenantTagIds = projectTagMapper2.getTagIdByTenantId(tenantId);
	 * List<Long> projectTagIds =
	 * projectTagMapper2.getTagIdByProjectId(projectId); for (Long projectTagId
	 * : projectTagIds) { if(!tenantTagIds.contains(projectTagId)) {
	 * projectTagMapper.deleteByPrimaryKey(projectTagId); } } //添加合法标签到tag,
	 * 添加所有传入标签到tag_rel for(int i = 0; i < tagNames.length; i++) { String
	 * tagName = tagNames[i]; ProjectTagExample illegalTagExample = new
	 * ProjectTagExample();
	 * illegalTagExample.createCriteria().andTagNameEqualTo(tagName).
	 * andTenantIdEqualTo(tenantId); List<ProjectTag> illegalTagList =
	 * projectTagMapper.selectByExample(illegalTagExample); if(0 ==
	 * illegalTagList.size()) { ProjectTag tag = new ProjectTag();
	 * tag.setTagName(tagName); tag.setTenantId(tenantId);
	 * projectTagMapper.insert(tag); } List<Long> tagIds =
	 * projectTagMapper2.getTagIdByTagName(tenantId, tagName); for (Long tagId :
	 * tagIds) { ProjectTagRelKey tagRel = new ProjectTagRelKey();
	 * tagRel.setProjectId(projectId); tagRel.setTagId(tagId);
	 * projectTagRelMapper.insert(tagRel); } } resultCode = update(projectId,
	 * tenantId, updateUser); if(0 == resultCode.getCode()) { String projectName
	 * = projectMapper2.getProjectName(projectId,tenantId);
	 * projectTimelineService.insertOptLog(projectId, tenantId,
	 * OperateTypeConstants.EDITED, OperateObjectTypeConstants.PROJECT, null,
	 * projectId, projectName, updateUser,updateUserName); resultCode =
	 * ResultUtil.success(); } return resultCode; }
	 */

	/**
	 * 添加项目评论
	 *
	 * @param projectId
	 *            项目id
	 * @param tenantId
	 *            租户id
	 * @param comment
	 *            评论内容
	 * @param createUser
	 *            评论人id
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode addComment(Long projectId, Long tenantId, String comment, Long createUser,
			String createUserName) {
		ResultCode resultCode;
		ProjectComment projectComment = new ProjectComment();
		projectComment.setProjectId(projectId);
		projectComment.setTenantId(tenantId);
		projectComment.setComment(comment);
		projectComment.setCreateUser(createUser);
		projectComment.setCreateTime(new Date());
		projectComment.setCreateUserName(createUserName);
		projectCommentMapper.insert(projectComment);
		String projectName = projectMapper2.getProjectName(projectId, tenantId);
		projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.COMMENTED,
				OperateObjectTypeConstants.PROJECT, null, projectId, projectName, createUser, createUserName);
		resultCode = update(projectId, tenantId, createUser);
		if (0 == resultCode.getCode()) {
			resultCode = ResultUtil.success(projectComment.getId());
		}
		return resultCode;
	}

	/**
	 * 获取评论列表
	 *
	 * @param projectId
	 *            项目id
	 * @param tenantId
	 *            租户id
	 * @return
	 */
	@Override
	public ResultCode getCommentList(Long projectId, Long tenantId) {
		ResultCode resultCode;
		Map<String, Object> oneGroup;
		List<Map<String, Object>> groupList = new ArrayList<>();
		List<String> createDays = projectMapper2.getCommentCreateDays(projectId, tenantId);
		for (String createDay : createDays) {
			List<ProjectComment> oneDayComment = projectMapper2.getOneGroupComment(projectId, tenantId, createDay);
			oneGroup = new LinkedHashMap<>();
			oneGroup.put("createDay", createDay);
			oneGroup.put("oneDayComment", oneDayComment);
			groupList.add(oneGroup);
		}
		resultCode = ResultUtil.success(groupList);
		return resultCode;
	}

	/**
	 * 获取时间轴列表
	 *
	 * @param projectId
	 *            项目id
	 * @param tenantId
	 *            租户id
	 * @return
	 */
	@Override
	public ResultCode getTimelineList(Long projectId, Long tenantId) {
		ResultCode resultCode;
		List<ProjectTimeline> timelineList = projectMapper2.getTimelineList(projectId, tenantId);
		resultCode = ResultUtil.success(timelineList);
		return resultCode;
	}

	/**
	 * 删除项目
	 *
	 * @param projectId
	 *            项目id
	 * @param tenantId
	 *            租户id
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode deleteProject(Long projectId, Long tenantId, Long userId, String userName) {
		ResultCode resultCode;
		// 项目名称和标识增加随机数后缀，isDel置1
		ProjectExample example = new ProjectExample();
		example.createCriteria().andIdEqualTo(projectId).andTenantIdEqualTo(tenantId);
		List<Project> projectList = projectMapper.selectByExample(example);
		Project project = projectList.get(0);
		project.setProjectName(project.getProjectName() + Math.floor(Math.random() * 500));
		project.setProjectKey(project.getProjectKey() + Math.floor(Math.random() * 500));
		project.setIsDel("1");
		projectMapper.updateByPrimaryKey(project);
		// 删除项目标签
		logger.info("开始删除项目标签");
		ProjectTagRelExample tagRelExample = new ProjectTagRelExample();
		tagRelExample.createCriteria().andProjectIdEqualTo(projectId);
		projectTagRelMapper.deleteByExample(tagRelExample);
		List<Long> tagIdList = projectTagMapper2.getTagIdByProjectId(projectId);
		for (Long tagId : tagIdList) {
			projectTagMapper.deleteByPrimaryKey(tagId);
		}
		logger.info("项目标签删除完成");
		// 删除项目评论
		logger.info("开始删除项目评论");
		ProjectCommentExample commentExample = new ProjectCommentExample();
		commentExample.createCriteria().andProjectIdEqualTo(projectId).andTenantIdEqualTo(tenantId);
		projectCommentMapper.deleteByExample(commentExample);
		logger.info("项目评论删除完成");
		// 删除项目时间轴
		logger.info("开始删除项目时间轴");
		ProjectTimelineExample timelineExample = new ProjectTimelineExample();
		timelineExample.createCriteria().andProjectIdEqualTo(projectId).andTenantIdEqualTo(tenantId);
		projectTimelineMapper.deleteByExample(timelineExample);
		logger.info("项目时间轴删除完成");
		// 删除数据集
		DatasetExample datasetExample = new DatasetExample();
		datasetExample.createCriteria().andProjectIdEqualTo(projectId);
		List<Dataset> dataSets = datasetMapper.selectByExample(datasetExample);
		StringBuffer dataSetIds = new StringBuffer();
		for (Dataset dataset : dataSets) {
			dataSetIds.append(dataset.getId() + ",");
		}
		if (0 != dataSetIds.length()) {
			logger.info("开始删除项目数据集");
			resultCode = dataSetService.deleteDataSet(dataSetIds.toString().substring(0, dataSetIds.length() - 1));
			if (0 != resultCode.getCode()) {
				return resultCode;
			}
			logger.info("项目数据集删除完成");
		}
		// 删除工作流
		try {
			WorkflowExample workflowExample = new WorkflowExample();
			workflowExample.createCriteria().andProjectIdEqualTo(projectId).andTenantIdEqualTo(tenantId)
					.andIsDelEqualTo("0");
			List<Workflow> workflows = workflowMapper.selectByExample(workflowExample);
			StringBuffer workflowIds = new StringBuffer();
			for (Workflow workflow : workflows) {
				workflowIds.append(workflow.getId() + ",");
			}
			if (0 != workflowIds.length()) {
				logger.info("开始删除项目工作流");
				workflowService.delWorkflow(workflowIds.toString().substring(0, workflowIds.length() - 1), tenantId,
						userId, userName, projectId);
				logger.info("项目工作流删除完成");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultCode = ResultUtil.error(Messages.DELETE_WORKFLOW_FAILED_CODE, Messages.DELETE_WORKFLOW_FAILED_MSG);
			return resultCode;
		}
		resultCode = ResultUtil.success();
		return resultCode;
	}

	/**
	 * 获取租户下标签列表
	 *
	 * @param tenantId
	 *            租户id
	 * @return
	 */
	/*
	 * @Override public ResultCode getTagList(Long tenantId) { ResultCode
	 * resultCode; ProjectTagExample projectTagExample = new
	 * ProjectTagExample();
	 * projectTagExample.createCriteria().andTenantIdEqualTo(tenantId);
	 * List<ProjectTag> tagList =
	 * projectTagMapper.selectByExample(projectTagExample); resultCode =
	 * ResultUtil.success(tagList); return resultCode; }
	 */

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode uploadProjectImage(Long projectId, Long tenantId, MultipartFile projectLogo, Long updateUser,
			String updateUserName) throws IOException {
		ResultCode resultCode;
		ProjectExample example = new ProjectExample();
		example.createCriteria().andIdEqualTo(projectId).andTenantIdEqualTo(tenantId);
		ProjectWithBLOBs project = projectMapper.selectByExampleWithBLOBs(example).get(0);
		project.setProjectPicture(projectLogo.getBytes());
		projectMapper.updateByPrimaryKeySelective(project);
		resultCode = update(projectId, tenantId, updateUser);
		if (0 == resultCode.getCode()) {
			String projectName = projectMapper2.getProjectName(projectId, tenantId);
			projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
					OperateObjectTypeConstants.PROJECT, null, projectId, projectName, updateUser, updateUserName);
			resultCode = ResultUtil.success(projectId);
		}
		return resultCode;
	}

	/**
	 * 获取工作流、数据处理、数据集等时间轴,按天分组
	 *
	 * @param projectId
	 *            项目id
	 * @param tenantId
	 *            租户id
	 * @param objectParentType
	 *            时间轴对象类型
	 * @param objectId
	 *            时间轴对象id
	 * @return
	 */
	@Override
	public ResultCode getObjectTimelineList(Long projectId, Long tenantId, String objectParentType, Long objectId) {
		ResultCode resultCode;
		Map<String, Object> oneGroup;
		List<Map<String, Object>> groupList = new ArrayList<>();
		List<String> createDays = projectMapper2.getTimelineCreateDays(projectId, tenantId, objectParentType, objectId);
		for (String createDay : createDays) {
			List<ProjectTimeline> oneDayTimeline = projectMapper2.getOneGroupTimeline(projectId, tenantId, createDay,
					objectParentType, objectId);
			oneGroup = new LinkedHashMap<>();
			oneGroup.put("createDay", createDay);
			oneGroup.put("oneDayTimeline", oneDayTimeline);
			groupList.add(oneGroup);
		}
		resultCode = ResultUtil.success(groupList);
		return resultCode;
	}

	/**
	 * 添加租户标签，添加标签回车时调用
	 *
	 * @param tagName
	 * @param tenantId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode addTenantTag(String tagName, Long tenantId) {
		ResultCode resultCode;
		ProjectTagExample example = new ProjectTagExample();
		example.createCriteria().andTagNameEqualTo(tagName);
		List<ProjectTag> tagList = projectTagMapper.selectByExample(example);
		if (0 == tagList.size()) {
			ProjectTag projectTag = new ProjectTag();
			projectTag.setTenantId(tenantId);
			projectTag.setTagName(tagName);
			projectTagMapper.insert(projectTag);
			resultCode = ResultUtil.success(projectTag.getId());
		} else {
			resultCode = ResultUtil.error(Messages.TAG_ALREADY_EXIST_CODE, Messages.TAG_ALREADY_EXIST_MSG);
		}
		return resultCode;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode addTag(String tagName, Long tenantId, Long projectId, Long updateUser, String updateUserName) {
		ResultCode resultCode;
		ProjectTagExample example = new ProjectTagExample();
		example.createCriteria().andTagNameEqualTo(tagName);
		List<ProjectTag> tagList = projectTagMapper.selectByExample(example);
		if (0 == tagList.size()) {
			ProjectTag projectTag = new ProjectTag();
			projectTag.setTenantId(tenantId);
			projectTag.setTagName(tagName);
			projectTagMapper.insert(projectTag);
			ProjectTagRelKey relKey = new ProjectTagRelKey();
			relKey.setProjectId(projectId);
			relKey.setTagId(projectTag.getId());
			projectTagRelMapper.insert(relKey);
			resultCode = ResultUtil.success(projectTag.getId());
		} else {
			ProjectTagRelExample relExample = new ProjectTagRelExample();
			relExample.createCriteria().andTagIdEqualTo(tagList.get(0).getId()).andProjectIdEqualTo(projectId);
			List<ProjectTagRelKey> tagRelList = projectTagRelMapper.selectByExample(relExample);
			if (0 == tagRelList.size()) {
				ProjectTagRelKey relKey = new ProjectTagRelKey();
				relKey.setProjectId(projectId);
				relKey.setTagId(tagList.get(0).getId());
				projectTagRelMapper.insert(relKey);
				resultCode = ResultUtil.success(tagList.get(0).getId());
			} else {
				resultCode = ResultUtil.error(Messages.TAG_ALREADY_EXIST_CODE, Messages.TAG_ALREADY_EXIST_MSG);
				return resultCode;
			}
		}
		String projectName = projectMapper2.getProjectName(projectId, tenantId);
		projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
				OperateObjectTypeConstants.PROJECT, null, projectId, projectName, updateUser, updateUserName);
		return resultCode;
	}

	/**
	 * 添加项目标签，保存标签时调用
	 *
	 * @param projectId
	 * @param tagId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode addProjectTag(Long projectId, Long tenantId, Long tagId, Long updateUser, String updateUserName) {
		ResultCode resultCode;
		ProjectTagRelKey tagRel = new ProjectTagRelKey();
		tagRel.setTagId(tagId);
		tagRel.setProjectId(projectId);
		projectTagRelMapper.insert(tagRel);
		resultCode = update(projectId, tenantId, updateUser);
		if (0 == resultCode.getCode()) {
			String projectName = projectMapper2.getProjectName(projectId, tenantId);
			projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
					OperateObjectTypeConstants.PROJECT, null, projectId, projectName, updateUser, updateUserName);
			resultCode = ResultUtil.success();
		}
		return resultCode;
	}

	/**
	 * 删除项目标签
	 *
	 * @param projectId
	 * @param tagId
	 * @return
	 */
	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public ResultCode deleteProjectTag(Long projectId, Long tenantId, Long tagId, Long updateUser,
			String updateUserName) {
		ResultCode resultCode;
		ProjectTagRelExample example = new ProjectTagRelExample();
		example.createCriteria().andProjectIdEqualTo(projectId).andTagIdEqualTo(tagId);
		projectTagRelMapper.deleteByExample(example);
		resultCode = update(projectId, tenantId, updateUser);
		if (0 == resultCode.getCode()) {
			String projectName = projectMapper2.getProjectName(projectId, tenantId);
			projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
					OperateObjectTypeConstants.PROJECT, null, projectId, projectName, updateUser, updateUserName);
			resultCode = ResultUtil.success();
		}
		return resultCode;
	}

	/**
	 * 查询已使用标签列表，过滤项目时使用
	 *
	 * @param tenantId
	 * @return
	 */
	@Override
	public ResultCode getUsedTagList(Long tenantId) {
		ResultCode resultCode;
		List<ProjectTag> tagList = projectTagMapper2.getUsedTag(tenantId);
		resultCode = ResultUtil.success(tagList);
		return resultCode;
	}

	/**
	 * 查询所有标签列表，添加标签时使用联想
	 *
	 * @param tenantId
	 * @param filter
	 * @return
	 */
	@Override
	public ResultCode getAllTagList(Long tenantId, String filter) {
		ResultCode resultCode;
		List<ProjectTag> tagList = projectTagMapper2.getAllTag(tenantId, filter);
		resultCode = ResultUtil.success(tagList);
		return resultCode;
	}

	/**
	 * 编辑项目相关操作更新update_time和update_user
	 * 
	 * @param projectId
	 * @param tenantId
	 * @param userId
	 * @return
	 */
	private ResultCode update(Long projectId, Long tenantId, Long userId) {
		ResultCode resultCode;
		ProjectExample example = new ProjectExample();
		example.createCriteria().andIdEqualTo(projectId).andTenantIdEqualTo(tenantId);
		List<Project> projectList = projectMapper.selectByExample(example);
		if (0 != projectList.size()) {
			projectList.get(0).setUpdateTime(new Date());
			projectList.get(0).setUpdateUser(userId);
			projectMapper.updateByPrimaryKey(projectList.get(0));
			resultCode = ResultUtil.success();
		} else {
			resultCode = ResultUtil.error(Messages.PROJECT_NOT_EXIST_CODE, Messages.PROJECT_NOT_EXIST_MSG);
		}

		return resultCode;
	}

	/**
	 * 获取创建项目的用户名称列表
	 *
	 * @param tenantId
	 * @return
	 */
	@Override
	public ResultCode getNameList(Long tenantId) {
		ResultCode resultCode;
		List<Long> createUserIds = projectMapper2.getCreateuserId(tenantId);
		List<Map<String, Object>> userInfos = new ArrayList<>();
		Map<String, Object> userInfo;
		for (Long userId : createUserIds) {
			userInfo = new HashMap<>();
			String userName = getUserName(tenantId, userId);
			userInfo.put("userId", userId);
			userInfo.put("userName", userName);
			userInfos.add(userInfo);
		}
		resultCode = ResultUtil.success(userInfos);
		return resultCode;
	}

	/**
	 * 根据userId获取userName
	 *   TODO 获得Username的方法
	 * @param tenantId
	 * @param userId
	 * @return
	 */
	public String getUserName(Long tenantId, Long userId) {
		UserApi userApi = new UserApi();
		ResultCode resultCode = userApi.getInfo(tenantId, userId);
		HashMap<String,Object> userFullInfo = (HashMap<String, Object>) resultCode.getData();
		String userName = ((JSONObject)userFullInfo.get("basicInfo")).getString("name");
//		String userFullInfo = resultCode.getData().toString();
//		JSONObject userFullInfoJson = JSON.parseObject(userFullInfo);
//		String basicInfo = userFullInfoJson.getString("basicInfo");
//		JSONObject basicInfoJson = JSON.parseObject(basicInfo);
//		String userName = basicInfoJson.getString("name");
		return userName;
	}

	@Override
	public ResultCode getProjectAllName() {
		List<Project> resultList = projectMapper2.getAllProjectNameList();
		return ResultUtil.success(resultList);
	}

}
