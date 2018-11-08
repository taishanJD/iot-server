package com.quarkdata.data.web.api.controller.api;

import java.util.Date;
import java.util.List;

import com.quarkdata.data.model.dataobj.SchedulerNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.SchedulerJob;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsible;
import com.quarkdata.data.model.vo.JobDetail;
import com.quarkdata.data.service.JobService;
import com.quarkdata.data.web.api.controller.BaseController;
import com.quarkdata.data.web.api.controller.RouteKey;

/**
 * @author typ 2018年5月3日
 */
@RequestMapping(RouteKey.PREFIX_API + "/job")
@RestController
public class JobController extends BaseController {

    @Autowired
    JobService jobService;

    /**
     * @param search          jobName
     * @param jobType         0-周期性 1-手动
     * @param responsibleType 0-全部责任人 1-我的
     * @param isPublish       是否已发布（0：否、1：是）默认1
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultCode<ListResult<SchedulerJob>> getJobList(
            String search,
            String jobType,
            String responsibleType,
            @RequestParam(value = "isPublish", required = false, defaultValue = "1") String isPublish,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();

        ResultCode<ListResult<SchedulerJob>> resultCode = jobService
                .getJobList(tenantId, userId, search, responsibleType, jobType,
                        isPublish, page, pageSize);
        return resultCode;
    }

    /**
     * 获取单个项目的作业列表
     *
     * @param page
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/get_list_by_project", method = RequestMethod.GET)
    public ResultCode<ListResult<SchedulerJob>> getJobListByProjectId(
            @RequestParam(value = "projectId") Long projectId,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {

        return jobService.getJobListByProjectId(projectId, page, pageSize);
    }

    /**
     * 获取单个项目的作业数量
     *
     * @param projectId
     * @return
     */
    @RequestMapping(value = "/get_count_by_project", method = RequestMethod.GET)
    public ResultCode getJobCountByProjectId(@RequestParam(value = "projectId") Long projectId) {
        return jobService.getJobCountByProjectId(projectId);
    }

    /**
     * @param jobId
     * @return
     */
    @RequestMapping(value = "/get_job_detail/{jobId}", method = RequestMethod.GET)
    public ResultCode<JobDetail> getJobDetail(@PathVariable Long jobId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<JobDetail> job = jobService.getJobDetail(jobId, tenantId);
        return job;
    }

    /**
     * @param  jobIds
     * @param noticeReasonType 通知原因类型（0：出错、1：完成、2：出错和完成）
     * @param noticeType       通知类型（0：邮件、1：短信、2：邮件和短信）
     * @param receiveIds       0-责任人 / 多个userId以,隔开
     * @return
     */
    @RequestMapping(value = "/set_job_notify", method = RequestMethod.POST)
    public ResultCode<String> setJobNotify(String jobIds,
                                           String noticeReasonType, String noticeType, String receiveIds, Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<String> resultCode = jobService.setJobNotify(jobIds,
                noticeReasonType, noticeType, receiveIds, projectId, tenantId, userId, userName);
        return resultCode;
    }

    /**
     * 修改job
     *
     * @param jobId
     * @param userIds
     * @param validStartdate
     * @param validEnddate
     * @param intervalType
     * @param intervalValues
     * @param startHour
     * @param startMinute
     * @param endHour
     * @param endMinute
     * @param isDependParent
     * @param isPublish
     * @return
     */
    @RequestMapping(value = "/edit_job", method = RequestMethod.POST)
    public ResultCode<String> editJob(Long jobId, String userIds,
                                      Date validStartdate, Date validEnddate, String intervalType,
                                      String intervalValues, Integer intervalHour,
                                      Integer intervalMinute, Integer startHour, Integer startMinute,
                                      Integer endHour, Integer endMinute, String isDependParent,
                                      String isPublish, Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<String> resultCode = jobService.editJob(jobId, tenantId,
                userId, userIds, validStartdate, validEnddate, intervalType,
                intervalValues, intervalHour, intervalMinute, startHour,
                startMinute, endHour, endMinute, isDependParent, isPublish,
                userName, projectId);
        return resultCode;
    }

    /**
     * 冻结/解冻
     *
     * @param jobId
     * @param isFrozen 是否冻结（0：否、1：是）
     * @return
     */
    @RequestMapping(value = "/frozen_job", method = RequestMethod.POST)
    public ResultCode<String> frozenJob(Long jobId, String isFrozen,
                                        Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<String> resultCode = jobService.frozenJob(jobId, tenantId,
                userId, isFrozen, userName, projectId);
        return resultCode;
    }

    /**
     * 获取作业的责任人接口
     */
    @RequestMapping(value = "/get_job_responsibles", method = RequestMethod.GET)
    public ResultCode<List<SchedulerJobResponsible>> getJobResponsibles(
            Long jobId) {
        ResultCode<List<SchedulerJobResponsible>> rc = jobService
                .getJobResponsibles(jobId);
        return rc;
    }

    /**
     * 编辑责任人
     */
    @RequestMapping(value = "/edit_job_responsibles", method = RequestMethod.POST)
    public ResultCode<String> editJobResponsibles(Long jobId, String userIds, Long projectId) {
        Long tenantId = UserInfoUtil.getIncorporation().getId();
        Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
        String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
        ResultCode<String> resultCode = jobService.editJobResponsibles(jobId, userIds, projectId, tenantId, userId, userName);
        return resultCode;
    }

    /**
     * 获得作业的通知人与通知类型
     */
    @RequestMapping(value = "/get_job_notify", method = RequestMethod.POST)
    public ResultCode<List<SchedulerNotify>> getJobNotify(Long jobId) {
        ResultCode<List<SchedulerNotify>> notify = jobService.getJobNotify(jobId);
        return notify;
    }
    /**
    * @Description: 删除job
    * @Param:  job的id  (单个和多个)
    * @return:
    * @Author: ning
    * @Date: 2018/8/13
    */
    @RequestMapping(value ="/delete_jobs",method = RequestMethod.GET)
    public ResultCode<String> deleteJobs(String jobIds){
        ResultCode<String> resultCode = jobService.deleteJobs(jobIds);
        return resultCode;
    }
}
