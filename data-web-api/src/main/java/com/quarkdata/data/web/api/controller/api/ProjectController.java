package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.tenant.model.dataobj.User;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.ProjectService;
import com.quarkdata.data.service.impl.ProjectServiceImpl;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.RouteKey;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping(RouteKey.PREFIX_API + RouteKey.PROJECT)
public class ProjectController {

	static Logger logger = Logger.getLogger(ProjectServiceImpl.class);

	@Autowired
	ProjectService projectService;

	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResultCode addProject(@RequestParam(value = "projectName") String projectName,
			@RequestParam(value = "projectKey") String projectKey,
			@RequestParam(value = "projectPicture") MultipartFile projectPicture) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
		String createUserName = UserInfoUtil.getUserInfoVO().getUser().getName();
		// Long tenantId =1l;
		// Long createUser =2l;
		// String createUserName ="luohl";
		ResultCode resultCode;
		try {
			resultCode = projectService.addProject(projectName, projectKey, projectPicture, tenantId, createUser,
					createUserName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("创建产品失败", e);
			resultCode = ResultUtil.error(Messages.API_ADD_PROJECT_CODE, Messages.API_ADD_PROJECT_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResultCode updateProject(@RequestParam(value = "projectId") Long projectId,
			@RequestParam(value = "projectName", required = false) String projectName,
			@RequestParam(value = "projectInfo", required = false) String projectInfo,
			@RequestParam(value = "projectDesc", required = false) String projectDesc,
			@RequestParam(value = "projectStatus", required = false) String projectStatus) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		User user = UserInfoUtil.getUserInfoVO().getUser();
		// Long tenantId =1l;
		// Long createUser =2l;
		// String createUserName ="luohl";
		ResultCode resultCode;
		try {
			resultCode = projectService.updateProject(projectId, projectName, projectInfo, projectDesc, projectStatus,
					tenantId, user.getId(), user.getName());
			// resultCode = projectService.updateProject(projectId, projectName,
			// projectInfo, projectDesc, projectStatus, tenantId, createUser,
			// createUserName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("编辑产品失败", e);
			resultCode = ResultUtil.error(Messages.UPDATE_PROJECT_CODE, Messages.UPDATE_PROJECT_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/get_details", method = RequestMethod.GET)
	public ResultCode getDetails(@RequestParam(value = "projectId") Long projectId) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		// Long tenantId =1l;
		ResultCode resultCode;
		try {
			return projectService.getDetails(projectId, tenantId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取项目详情失败", e);
			resultCode = ResultUtil.error(Messages.GET_PROJECT_DETAIL_CODE, Messages.GET_PROJECT_DETAIL_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/get_list", method = RequestMethod.GET)
	public ResultCode getList(@RequestParam(value = "filter", required = false) String filter,
			@RequestParam(value = "status", required = false) String[] status,
			@RequestParam(value = "tag", required = false) Long[] tag,
			@RequestParam(value = "user", required = false) Long[] user,
			@RequestParam(value = "sortType", required = false) String sortType,
			@RequestParam(value = "sortMethod", required = false) String sortMethod) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		// Long tenantId =1l;
		ResultCode resultCode;
		try {
			return projectService.getList(tenantId, filter, status, tag, user, sortType, sortMethod);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取项目列表失败", e);
			resultCode = ResultUtil.error(Messages.GET_PROJECT_LIST_CODE, Messages.GET_PROJECT_LIST_MSG);
		}
		return resultCode;
	}

	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/edit_tag", method = RequestMethod.POST) public
	 * ResultCode editList(@RequestParam(value = "projectId") Long projectId,
	 * 
	 * @RequestParam(value = "tagNames") String[] tagNames) { Long tenantId =1l;
	 * Long createUser =2l; String createUserName ="luohl"; ResultCode
	 * resultCode; try { return projectService.editTag(projectId, tagNames,
	 * tenantId, createUser, createUserName); } catch (Exception e) {
	 * e.printStackTrace(); logger.error("编辑标签失败",e); resultCode =
	 * ResultUtil.error(Messages.EDIT_TAG_FAILED_CODE,Messages.
	 * EDIT_TAG_FAILED_MSG); } return resultCode; }
	 */

	@ResponseBody
	@RequestMapping(value = "/add_comment", method = RequestMethod.POST)
	public ResultCode addComment(@RequestParam(value = "projectId") Long projectId,
			@RequestParam(value = "comment") String comment) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		User user = UserInfoUtil.getUserInfoVO().getUser();
		// Long tenantId =1l;
		// Long createUser =2l;
		// String createUserName ="luohl";
		ResultCode resultCode;
		try {
			return projectService.addComment(projectId, tenantId, comment, user.getId(), user.getName());
			// return projectService.addComment(projectId, tenantId, comment,
			// createUser, createUserName);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加评论失败", e);
			resultCode = ResultUtil.error(Messages.ADD_COMMENT_CODE, Messages.ADD_COMMENT_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/get_comment_list", method = RequestMethod.GET)
	public ResultCode getCommentList(@RequestParam(value = "projectId") Long projectId) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		// Long tenantId =1l;
		ResultCode resultCode;
		try {
			return projectService.getCommentList(projectId, tenantId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取评论列表失败", e);
			resultCode = ResultUtil.error(Messages.GET_COMMENT_LIST_CODE, Messages.GET_COMMENT_LIST_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/get_project_timeline", method = RequestMethod.GET)
	public ResultCode getTimelineList(@RequestParam(value = "projectId") Long projectId) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		// Long tenantId =1l;
		ResultCode resultCode;
		try {
			return projectService.getTimelineList(projectId, tenantId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取项目时间轴列表失败", e);
			resultCode = ResultUtil.error(Messages.GET_TIMELINE_LIST_CODE, Messages.GET_TIMELINE_LIST_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/get_object_timeline", method = RequestMethod.GET)
	public ResultCode getObjectTimeline(@RequestParam(value = "projectId") Long projectId,
			@RequestParam(value = "objectParentType", required = false) String objectParentType,
			@RequestParam(value = "objectId", required = false) Long objectId) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		// Long tenantId =1l;
		ResultCode resultCode;
		try {
			return projectService.getObjectTimelineList(projectId, tenantId, objectParentType, objectId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取时间轴列表失败", e);
			resultCode = ResultUtil.error(Messages.GET_TIMELINE_LIST_CODE, Messages.GET_TIMELINE_LIST_MSG);
		}
		return resultCode;
	}

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultCode deleteProject(@RequestParam(value = "projectId") Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        
//        Long tenantId =1l;
        ResultCode resultCode;
        try {
            return projectService.deleteProject(projectId, tenantId,userId,userName);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除项目失败",e);
            resultCode = ResultUtil.error(Messages.DELETE_PROJECT_FAILED_CODE,Messages.DELETE_PROJECT_FAILED_MSG);
        }
        return  resultCode;
    }

	// 上传项目图片
	@ResponseBody
	@RequestMapping(value = "/upload_image", method = RequestMethod.POST)
	public ResultCode previewLogo(@RequestParam(value = "file") MultipartFile file) {
		ResultCode resultCode;
		try {
			file.getBytes();
			resultCode = ResultUtil.success();
		} catch (IOException e) {
			e.printStackTrace();
			resultCode = ResultUtil.error(Messages.UPLOAD_PROJECT_IMAGE_FAILED_CODE,
					Messages.UPLOAD_PROJECT_IMAGE_FAILED_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/change_image", method = RequestMethod.POST)
	public ResultCode changeProjectImage(@RequestParam(value = "projectId", required = false) Long projectId,
			@RequestParam(value = "file") MultipartFile file) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		User user = UserInfoUtil.getUserInfoVO().getUser();
		// Long tenantId =1l;
		// Long createUser =2l;
		// String createUserName ="luohl";
		ResultCode resultCode;
		try {
			return projectService.uploadProjectImage(projectId, tenantId, file, user.getId(), user.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传项目图片失败", e);
			resultCode = ResultUtil.error(Messages.UPLOAD_PROJECT_IMAGE_FAILED_CODE,
					Messages.UPLOAD_PROJECT_IMAGE_FAILED_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/add_tenant_tag", method = RequestMethod.POST)
	public ResultCode addTenantList(@RequestParam(value = "tagName") String tagName) {
		// Long tenantId = UserInfoUtil.getIncorporation().getId();
		Long tenantId = 1l;
		ResultCode resultCode;
		try {
			return projectService.addTenantTag(tagName, tenantId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加标签失败", e);
			resultCode = ResultUtil.error(Messages.ADD_TAG_FAILED_CODE, Messages.ADD_TAG_FAILED_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/add_project_tag", method = RequestMethod.POST)
	public ResultCode addProjectList(@RequestParam(value = "projectId") Long projectId,
			@RequestParam(value = "tagId") Long tagId) {
		 Long tenantId = UserInfoUtil.getIncorporation().getId();
		 User user = UserInfoUtil.getUserInfoVO().getUser();
//		Long tenantId = 1l;
//		Long createUser = 2l;
//		String createUserName = "luohl";
		ResultCode resultCode;
		try {
			return projectService.addProjectTag(projectId, tenantId, tagId, user.getId(), user.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加标签失败", e);
			resultCode = ResultUtil.error(Messages.ADD_TAG_FAILED_CODE, Messages.ADD_TAG_FAILED_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/delete_project_tag", method = RequestMethod.POST)
	public ResultCode deleteProjectList(@RequestParam(value = "projectId") Long projectId,
			@RequestParam(value = "tagId") Long tagId) {
		 Long tenantId = UserInfoUtil.getIncorporation().getId();
		 User user = UserInfoUtil.getUserInfoVO().getUser();
//		Long tenantId = 1l;
//		Long createUser = 2l;
//		String createUserName = "luohl";
		ResultCode resultCode;
		try {
			return projectService.deleteProjectTag(projectId, tenantId, tagId, user.getId(), user.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除标签失败", e);
			resultCode = ResultUtil.error(Messages.DELETE_TAG_FAILED_CODE, Messages.DELETE_TAG_FAILED_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/get_used_tag_list", method = RequestMethod.GET)
	public ResultCode getUsedTagList() {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		// Long tenantId =1l;
		ResultCode resultCode;
		try {
			return projectService.getUsedTagList(tenantId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取标签列表失败", e);
			resultCode = ResultUtil.error(Messages.GET_TAG_LIST_FAILED_CODE, Messages.GET_TAG_LIST_FAILED_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/get_all_tag_list", method = RequestMethod.GET)
	public ResultCode getAllTagList(@RequestParam(value = "filter") String filter) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		// Long tenantId =1l;
		ResultCode resultCode;
		try {
			return projectService.getAllTagList(tenantId, filter);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取标签列表失败", e);
			resultCode = ResultUtil.error(Messages.GET_TAG_LIST_FAILED_CODE, Messages.GET_TAG_LIST_FAILED_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/add_tag", method = RequestMethod.POST)
	public ResultCode editTag(@RequestParam(value = "projectId") Long projectId,
			@RequestParam(value = "tagName") String tagName) {
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		User user = UserInfoUtil.getUserInfoVO().getUser();
		// Long tenantId =1l;
		// Long createUser =2l;
		// String createUserName ="luohl";
		ResultCode resultCode;
		try {
			return projectService.addTag(tagName, tenantId, projectId, user.getId(), user.getName());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加标签失败", e);
			resultCode = ResultUtil.error(Messages.ADD_TAG_FAILED_CODE, Messages.ADD_TAG_FAILED_MSG);
		}
		return resultCode;
	}

	@ResponseBody
	@RequestMapping(value = "/get_user_name_list", method = RequestMethod.GET)
	public ResultCode getName() {
		ResultCode resultCode;
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		// Long tenantId =1l;
		try {
			return projectService.getNameList(tenantId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取用户名称列表失败", e);
			resultCode = ResultUtil.error(Messages.GET_USER_NAME_CODE, Messages.GET_USER_NAME_MSG);
		}
		return resultCode;
	}
	
	/**
	 * 获取所有的项目名称及ID
	 * WangHao
	 * 2018-6-13 13:50:24
	 */
	@ResponseBody
	@RequestMapping(value = "/allName", method = RequestMethod.GET)
	public ResultCode getProjectAllName() {
		return projectService.getProjectAllName();
	}
}
