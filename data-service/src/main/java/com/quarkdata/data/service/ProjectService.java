package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 *
 * @author luohl0703
 *
 */
public interface ProjectService {

    /**
     * 添加项目
     * @param projectName 项目名称
     * @param projectKey  项目标识
     * @param projectPicture  项目图片
     * @param tenantId  租户id
     * @param createUser  创建人id
     * @return
     */
    ResultCode addProject(String projectName, String projectKey, MultipartFile projectPicture, Long tenantId, Long createUser, String createUserName) throws IOException;

    /**
     * 编辑项目
     * @param projectId 项目id
     * @param projectName 项目名称（非）
     * @param projectInfo 项目简介（非）
     * @param projectDesc 项目描述（非）
     * @param tenantId 租户id
     * @param updateUser 修改人id
     * @return
     */
    ResultCode updateProject(Long projectId, String projectName, String projectInfo, String projectDesc, String projectStatus, Long tenantId, Long updateUser, String createUserName);

    /**
     * 获取项目详情
     * @param projectId 项目id
     * @param tenantId 租户id
     * @return
     */
    ResultCode getDetails(Long projectId, Long tenantId);

    /**
     * 获取项目列表
     * @param tenantId 租户id
     * @param filter 搜索条件
     * @param status 项目状态
     * @param tag 项目标签
     * @param user  创建者id
     * @param sortType 排序类型
     * @param sortMethod 排序方式
     * @return
     */
    ResultCode getList(Long tenantId, String filter, String[] status, Long[] tag, Long[] user, String sortType, String sortMethod);

    /**
     * 添加项目评论
     * @param projectId 项目id
     * @param tenantId 租户id
     * @param comment 评论内容
     * @param createUser 评论人id
     * @return
     */
    ResultCode addComment(Long projectId, Long tenantId, String comment, Long createUser, String createUserName);

    /**
     * 获取评论列表
     * @param projectId 项目id
     * @param tenantId 租户id
     * @return
     */
    ResultCode getCommentList(Long projectId, Long tenantId);

    /**
     * 获取时间轴列表
     * @param projectId 项目id
     * @param tenantId 租户id
     * @return
     */
    ResultCode getTimelineList(Long projectId, Long tenantId);

    /**
     * 删除项目
     * @param projectId 项目id
     * @param tenantId 租户id
     * @param userName 
     * @param userId 
     * @return
     */
    ResultCode deleteProject(Long projectId, Long tenantId, Long userId, String userName);

    /**
     * 编辑标签
     * @param projectId 项目id
     * @param tagNames 标签名
     * @param tenantId 租户id
     * @param updateUser 操作人id
     * @return
     */
//    ResultCode editTag(Long projectId, String[] tagNames, Long tenantId, Long updateUser, String updateUserName);

    /**
     * 获取租户下标签列表
     * @param tenantId 租户id
     * @param tenantId 租户id
     * @return
     */
//    ResultCode getTagList(Long tenantId);

    /**
     * 上传项目图片
     * @param projectId 项目id
     * @param tenantId 租户id
     * @param projectLogo 项目图片
     * @param updateUser 操作人id
     * @return
     */
    ResultCode uploadProjectImage(Long projectId, Long tenantId, MultipartFile projectLogo, Long updateUser, String updateUserName) throws IOException;

    /**
     * 获取工作流、数据处理、数据集等时间轴
     * @param projectId 项目id
     * @param tenantId 租户id
     * @param objectParentType 时间轴对象类型
     * @param objectId 时间轴对象id
     * @return
     */
    ResultCode getObjectTimelineList(Long projectId, Long tenantId, String objectParentType, Long objectId);

    /**
     * 添加租户标签，添加标签回车时调用
     * @param tagName
     * @param tenantId
     * @return
     */
    ResultCode addTenantTag(String  tagName, Long tenantId);

    ResultCode addTag(String tagName, Long tenantId, Long projectId, Long updateUser, String updateUserName);

    /**
     * 添加项目标签，保存标签时调用
     * @param projectId
     * @param tagId
     * @return
     */
    ResultCode addProjectTag(Long projectId, Long tenantId, Long tagId, Long updateUser, String updateUserName);

    /**
     * 删除项目标签
     * @param projectId
     * @param tagId
     * @return
     */
    ResultCode deleteProjectTag(Long projectId, Long tenantId, Long tagId, Long updateUser, String updateUserName);

    /**
     * 查询已使用标签列表，过滤项目时使用
     * @param tenantId
     * @return
     */
    ResultCode getUsedTagList(Long tenantId);

    /**
     * 查询所有标签列表，添加标签时使用联想
     * @param tenantId
     * @param filter
     * @return
     */
    ResultCode getAllTagList(Long tenantId, String filter);

    /**
     * 获取创建项目的用户名称列表
     * @param tenantId
     * @return
     */
    ResultCode getNameList(Long tenantId);

    /**
     * 获取项目所有的名称列表
     * @param
     */
	ResultCode getProjectAllName();
}
