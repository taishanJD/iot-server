package com.quarkdata.data.service;

import java.util.Date;
import java.util.List;

import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.SchedulerJob;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsible;
import com.quarkdata.data.model.dataobj.SchedulerNotify;
import com.quarkdata.data.model.vo.JobDetail;

public interface JobService {
	
	// 作业业务类型（0：工作流、1：脚本、2：其他）
	public static final String JOB_BIZ_TYPE_WORKFLOW = "0";
	public static final String JOB_BIZ_TYPE_SCRIPT = "1";
	
	// 是否冻结（0：否、1：是）
	public static final String IS_FROZEN_NO ="0";
	public static final String IS_FROZEN_YES ="1";

	// 是否通知（0：否、1：是）
	public static final String IS_NOTIFY_NO ="0";
	public static final String IS_NOTIFY_YES ="1";
	
	// 是否已发布（0：否、1：是）默认0
	public static final String IS_PUBLISH_NO ="0";
	public static final String IS_PUBLISH_YES ="1";
	
	
	/**
	 * 作业列表
	 * 
	 * @param tenantId
	 * @param userId
	 * @param search
	 * @param responsibleType
	 * @param jobType
	 * @param page
	 * @param pageSize
	 * @return
	 */
	ResultCode<ListResult<SchedulerJob>> getJobList(Long tenantId, Long userId,
			String search, String responsibleType, String jobType,
			String isPublish, int page, int pageSize);

	/**
	 * 单个项目的作业列表
	 *
	 * @param projectId
	 * @param page
	 * @param pageSize
	 * @return
	 */
	ResultCode<ListResult<SchedulerJob>> getJobListByProjectId(Long projectId, int page, int pageSize);

	/**
	 * 单个项目的作业数量
	 *
	 * @param projectId
	 * @return
	 */
	ResultCode getJobCountByProjectId(Long projectId);

	/**
	 * 作业详情
	 * 
	 * @param jobId
	 * @param tenantId
	 * @return
	 */
	ResultCode<JobDetail> getJobDetail(Long jobId, Long tenantId);

	/**
	 * @param noticeReasonType
	 *            通知原因类型（0：出错、1：完成、2：出错和完成）
	 * @param noticeType
	 *            通知类型（0：邮件、1：短信、2：邮件和短信）
	 * @param receiveIds
	 *            0-责任人 / 多个userId以,隔开
	 */
	ResultCode<String> setJobNotify(String jobIds, String noticeReasonType,
			String noticeType, String receiveIds,Long projectId,Long tenantId,Long userId,String userName);

	/**
	 * 编辑job
	 * @param jobId
	 * @param tenantId
	 * @param userId
	 * @param userIds
	 * @param validStartdate
	 * @param validEnddate
	 * @param intervalType
	 * @param intervalValues
	 * @param isDependParent
	 * @param isPublish
	 * @param userName 
	 * @param projectId 
	 * @return
	 */
	ResultCode<String> editJob(Long jobId, Long tenantId, Long userId,
			String userIds, Date validStartdate, Date validEnddate,
			String intervalType, String intervalValues,
			Integer intervalHour,Integer intervalMinute,
			Integer startHour, Integer startMinute,
			Integer endHour, Integer endMinute,
			String isDependParent,
			String isPublish, String userName, Long projectId);

	/**
	 * 
	 * @param jobId
	 * @param tenantId
	 * @param isFrozen
	 * @param projectId 
	 * @param userName 
	 * @return
	 */
	ResultCode<String> frozenJob(Long jobId, Long tenantId,Long userId,String isFrozen, String userName, Long projectId);
	
	/**
	 * 获取责任人列表
	 * @param jobId
	 * @return
	 */
	ResultCode<List<SchedulerJobResponsible>> getJobResponsibles(Long jobId);
	/**
	* @Description:  编辑责任人
	* @Param:
	* @return:
	* @Author: ning
	* @Date: 2018/7/13
	*/
	ResultCode<String> editJobResponsibles(Long jobId, String userIds,Long projectId,Long tenantId,Long userId,String username);
	/**
	* @Description:  获得通知人
	* @Param:
	* @return:
	* @Author: ning
	* @Date: 2018/7/13
	*/
	ResultCode<List<SchedulerNotify>> getJobNotify(Long jobId);
	/**
	* @Description:  删除jobs
	* @Param:
	* @return:
	* @Author: ning
	* @Date: 2018/8/13
	*/
	ResultCode<String> deleteJobs(String jobs);
}
