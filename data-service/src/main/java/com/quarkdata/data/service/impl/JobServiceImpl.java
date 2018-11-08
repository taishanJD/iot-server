package com.quarkdata.data.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.service.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.quarkdata.data.dal.rest.quarkshare.UserApi;
import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.OperateObjectTypeConstants;
import com.quarkdata.data.model.common.OperateTypeConstants;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.common.ScheduleJob;
import com.quarkdata.data.model.dataobj.SchedulerJob;
import com.quarkdata.data.model.dataobj.SchedulerJobExample;
import com.quarkdata.data.model.dataobj.SchedulerJobExample.Criteria;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsible;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsibleExample;
import com.quarkdata.data.model.dataobj.SchedulerJobResponsibleKey;
import com.quarkdata.data.model.dataobj.SchedulerNotify;
import com.quarkdata.data.model.dataobj.SchedulerNotifyExample;
import com.quarkdata.data.model.dataobj.Workflow;
import com.quarkdata.data.model.dataobj.WorkflowExample;
import com.quarkdata.data.model.vo.JobDetail;
import com.quarkdata.tenant.model.dataobj.User;

@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class JobServiceImpl implements JobService {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static final String NOTIFY_TYPE_RESPONSIBLE = "0";

	@Autowired
	SchedulerJobMapper schedulerJobMapper;
	@Autowired
	SchedulerJobMapper2 schedulerJobMapper2;
	@Autowired
	SchedulerTaskMapper2 schedulerTaskMapper2;
	@Autowired
	SchedulerJobResponsibleMapper schedulerJobResponsibleMapper;
	@Autowired
	SchedulerNotifyMapper schedulerNotifyMapper;
	@Autowired
	WorkflowMapper workflowMapper;
	@Autowired
	ProjectTimelineService projectTimelineService;
	@Autowired
	UserService userService;
	@Autowired
	UserApi userApi;
	@Autowired
	SchedulerManageService schedulerManageService;
	@Autowired
	SchedulerTaskService schedulerTaskService;

	@Override
	public ResultCode<ListResult<SchedulerJob>> getJobList(Long tenantId, Long userId, String search,
			String responsibleType, String jobType, String isPublish, int page, int pageSize) {
		SchedulerJobExample example = new SchedulerJobExample();
		Criteria criteria = example.createCriteria();
		criteria.andTenantIdEqualTo(tenantId).andIsDelEqualTo("0");
		if (StringUtils.isNotBlank(search)) {
			criteria.andJobNameLike("%" + search + "%");
		}
		if (StringUtils.isNotBlank(jobType)) {
			criteria.andJobTypeEqualTo(jobType);
		}
		// 是否发布状态
		if (StringUtils.isNotBlank(isPublish)) {
			criteria.andIsPublishEqualTo(isPublish);
		}

		if (StringUtils.isNotBlank(responsibleType) && responsibleType.equals(Constants.JOB_RESPONSIBLE_TYPE_MY)) {
			SchedulerJobResponsibleExample responsibleExample = new SchedulerJobResponsibleExample();
			responsibleExample.createCriteria().andUserIdEqualTo(userId);
			List<SchedulerJobResponsible> responsibleList = schedulerJobResponsibleMapper
					.selectByExample(responsibleExample);
			Long[] jobIds = getJobIds(responsibleList);
			if (jobIds != null) {
				criteria.andIdIn(Arrays.asList(jobIds));
			} else {// 没有我的job
				ResultCode<ListResult<SchedulerJob>> resultCode = new ResultCode<ListResult<SchedulerJob>>();
				ListResult<SchedulerJob> listResult = new ListResult<>();
				listResult.setPageNum(page);
				listResult.setPageSize(pageSize);
				resultCode.setData(listResult);
				return resultCode;
			}
		}
		Integer limitStart = (page - 1) * pageSize;
		Integer limitEnd = pageSize;
		long totalNum = schedulerJobMapper.countByExample(example);
		example.setLimitStart(limitStart);
		example.setLimitEnd(limitEnd);
		List<SchedulerJob> list = schedulerJobMapper.selectByExample(example);
		ResultCode<ListResult<SchedulerJob>> resultCode = new ResultCode<ListResult<SchedulerJob>>();
		ListResult<SchedulerJob> listResult = new ListResult<>();
		listResult.setListData(list);
		listResult.setPageNum(page);
		listResult.setPageSize(pageSize);
		listResult.setTotalNum((int) totalNum);
		resultCode.setData(listResult);
		return resultCode;
	}

	@Override
	public ResultCode<ListResult<SchedulerJob>> getJobListByProjectId(Long projectId, int page, int pageSize) {
		ResultCode<ListResult<SchedulerJob>> resultCode = new ResultCode<>();
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		Integer limitStart = (page - 1) * pageSize;
		Integer limitEnd = pageSize;
		List<SchedulerJob> list = schedulerJobMapper2.getJobListByProjectId(tenantId, projectId, limitStart, limitEnd);
		Integer count = schedulerJobMapper2.getJobCountByProjectId(tenantId, projectId);
		ListResult<SchedulerJob> listResult = new ListResult<>();
		listResult.setListData(list);
		listResult.setPageNum(page);
		listResult.setPageSize(pageSize);
		listResult.setTotalNum(count);
		resultCode.setData(listResult);
		return resultCode;
	}

	@Override
	public ResultCode getJobCountByProjectId(Long projectId) {
		ResultCode<Integer> resultCode = new ResultCode<>();
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		Integer count = schedulerJobMapper2.getJobCountByProjectId(tenantId, projectId);
		resultCode.setData(count);
		return resultCode;
	}

	private Long[] getJobIds(List<SchedulerJobResponsible> responsibleList) {
		if (CollectionUtils.isEmpty(responsibleList)) {
			return null;
		}
		Long[] jobIds = new Long[responsibleList.size()];
		int i = 0;
		for (SchedulerJobResponsibleKey responsible : responsibleList) {
			jobIds[i++] = responsible.getSchedulerJobId();
		}
		return jobIds;
	}

	@Override
	public ResultCode<JobDetail> getJobDetail(Long jobId, Long tenantId) {
		SchedulerJobExample example = new SchedulerJobExample();
		example.createCriteria().andIdEqualTo(jobId).andTenantIdEqualTo(tenantId);
		List<SchedulerJob> jobs = schedulerJobMapper.selectByExample(example);
		JobDetail job = null;
		if (CollectionUtils.isNotEmpty(jobs)) {
			job = new JobDetail();
			SchedulerJob schedulerJob = jobs.get(0);
			BeanUtils.copyProperties(schedulerJob, job);
			// 查询job的责任人
			SchedulerJobResponsibleExample responsibleExample = new SchedulerJobResponsibleExample();
			responsibleExample.createCriteria().andSchedulerJobIdEqualTo(job.getId());
			List<SchedulerJobResponsible> responsibles = schedulerJobResponsibleMapper
					.selectByExample(responsibleExample);
			job.setUsers(responsibles);
			// 查询workflow是否依赖上一个节点
			if (schedulerJob.getJobBizType().equals("0")) {
				// workflow
				Long workflowId = schedulerJob.getJobBizId();
				Workflow workflow = workflowMapper.selectByPrimaryKey(workflowId);
				job.setIsDependParent(workflow.getIsDependParent());
			}
			// 查询通知类型，通知原因类型
			SchedulerNotifyExample notifyExample = new SchedulerNotifyExample();

			notifyExample.createCriteria().andSchedulerJobIdEqualTo(jobId);

			List<SchedulerNotify> notifys = schedulerNotifyMapper.selectByExample(notifyExample);
			if (CollectionUtils.isNotEmpty(notifys)) {
				SchedulerNotify schedulerNotify = notifys.get(0);
				job.setNoticeType(schedulerNotify.getNoticeType());
				job.setNoticeReasonType(schedulerNotify.getNoticeReasonType());
			}

			/*
			 * List<UserVo> users = getUsers(responsibles); job.setUsers(users);
			 */
			// 查询job是否依赖上一个节点，针对工作流
		}
		ResultCode<JobDetail> resultCode = new ResultCode<JobDetail>();
		resultCode.setData(job);
		return resultCode;
	}

	/*
	 * private List<UserVo> getUsers(List<SchedulerJobResponsibleKey>
	 * responsibles) { if (CollectionUtils.isEmpty(responsibles)) { return null;
	 * } StringBuilder sb = new StringBuilder(); for (SchedulerJobResponsibleKey
	 * resKey : responsibles) { sb.append(resKey.getUserId()).append(","); } if
	 * (sb.length() > 0) { sb.deleteCharAt(sb.length() - 1); } List<User> users
	 * = userApi.getUsersByIds(sb.toString()).getData(); if
	 * (CollectionUtils.isEmpty(users)) { return null; } List<UserVo> result =
	 * new ArrayList<>(); for (User u : users) { UserVo vo = new UserVo();
	 * vo.setUserId(u.getId()); vo.setUserName(u.getName()); result.add(vo); }
	 *
	 * return result; }
	 */

	@Override
	public ResultCode<String> setJobNotify(String jobIds, String noticeReasonType, String noticeType, String receiveIds,
			Long projectId, Long tenantId, Long userId, String userName) {
		if (StringUtils.isBlank(receiveIds)) {
			throw new IllegalArgumentException("receiveIds can not be blank");
		}
		String[] ids = StringUtils.split(jobIds, ',');
		for (String jobId : ids) {
			setOneJobNotify(Long.valueOf(jobId), noticeReasonType, noticeType, receiveIds);
		}

		// TODO
		// 添加操作日
		String[] split = jobIds.split(",");
		for (String sp : split) {
			SchedulerJobExample example = new SchedulerJobExample();
			example.createCriteria().andIdEqualTo(Long.parseLong(sp)).andTenantIdEqualTo(tenantId);
			List<SchedulerJob> jobs = schedulerJobMapper.selectByExample(example);
			SchedulerJob job = jobs.get(0);
			projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
					OperateObjectTypeConstants.JOB, null, job.getId(), job.getJobName(), userId, userName);
		}
		return new ResultCode<String>();
	}

	private void setOneJobNotify(Long jobId, String noticeReasonType, String noticeType, String receiveIds) {
		// 修改job的is_notify为1
		SchedulerJob job = schedulerJobMapper.selectByPrimaryKey(jobId);
		String isNotify = job.getIsNotify();
		if (isNotify.equals("0")) {
			job.setIsNotify("1");
			schedulerJobMapper.updateByPrimaryKeySelective(job);
		}
		// 先清空,再填写数据就较为方便
		SchedulerNotifyExample delExample = new SchedulerNotifyExample();
		delExample.createCriteria().andSchedulerJobIdEqualTo(jobId);
		schedulerNotifyMapper.deleteByExample(delExample);
		if (NOTIFY_TYPE_RESPONSIBLE.equals(receiveIds)) {
			SchedulerNotify record = new SchedulerNotify();
			record.setNoticeReasonType(noticeReasonType);
			record.setNoticeType(noticeType);
			record.setReceiveUserId(0l);
			record.setSchedulerJobId(jobId);
			schedulerNotifyMapper.insert(record);
		} else {
			String[] receiveIdsArr = receiveIds.split(",");
			for (String receiveId : receiveIdsArr) {
				SchedulerNotify record = new SchedulerNotify();
				record.setNoticeReasonType(noticeReasonType);
				record.setNoticeType(noticeType);
				record.setReceiveUserId(Long.valueOf(receiveId));
				record.setSchedulerJobId(jobId);
				schedulerNotifyMapper.insert(record);
			}
		}
	}

	@Override
	public ResultCode<String> editJob(Long jobId, Long tenantId, Long userId, String userIds, Date validStartdate,
			Date validEnddate, String intervalType, String intervalValues, Integer intervalHour, Integer intervalMinute,
			Integer startHour, Integer startMinute, Integer endHour, Integer endMinute, String isDependParent,
			String isPublish, String userName, Long projectId) {
		SchedulerJobExample example = new SchedulerJobExample();
		example.createCriteria().andIdEqualTo(jobId).andTenantIdEqualTo(tenantId);
		List<SchedulerJob> jobs = schedulerJobMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(jobs)) {
			throw new IllegalArgumentException(
					"job can not found,param is -> jobId:" + jobId + ",tenantId:" + tenantId);
		}
		SchedulerJob job = jobs.get(0);
		Date now = new Date();

		logger.info("editJob before,job is : {}", JSON.toJSONString(job));

		// 修改job
		job.setIntervalType(intervalType);
		job.setIntervalValues(intervalValues);
		job.setIntervalHour(intervalHour);
		job.setIntervalMinute(intervalMinute);
		job.setValidStartdate(validStartdate);
		job.setValidEnddate(validEnddate);
		job.setStartHour(startHour);
		job.setStartMinute(startMinute);
		job.setEndHour(endHour);
		job.setEndMinute(endMinute);
		job.setIsPublish(isPublish);
		job.setUpdateTime(now);
		job.setUpdateUser(userId);
		job.setIsDel("0");        // 如果删除工作流对应的job时，再次保存发布时job的删除状态未更改，导致无法显示
		schedulerJobMapper.updateByPrimaryKey(job);

		logger.info("editJob after,job is : " + JSON.toJSONString(job));

		// 设置job责任人

		setJobRes(jobId, userIds); // 可能为空


		// 修改工作流isDependParent
		updateWorkflowIsDependParent(job.getId(), isDependParent, userId, now);

		// 添加操作日志(时间轴)
		projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
				OperateObjectTypeConstants.JOB, null, job.getId(), job.getJobName(), userId, userName);

		/**
		 * 如果该工作流是发布状态则添加定时任务 如果是取消发布 则删除定时任务 循环定时任务列表 查看是否存在该任务
		 * 不存在则跳过（防止默认为不发布时删除出错） WangHao 2018年7月18日16:57
		 */
		try {
			String saveJobGroup = "workflowjob";
			String saveJobName = "workflowjob_" + jobId;
			boolean delFlag = false;
			ScheduleJob saveScheduleJob = new ScheduleJob();
			saveScheduleJob.setJobName(saveJobName);
			saveScheduleJob.setJobGroup(saveJobGroup);
			saveScheduleJob.setClazz("com.quarkdata.data.job.CustomForJob");
			saveScheduleJob.setCreateTime(new Date());
			saveScheduleJob.setCreateUser(userId);
			saveScheduleJob.setJobDesc("Job_ID为：[" + jobId + "]的调度任务！");
			saveScheduleJob.setJobStatus("2");
			if (isPublish == null || isPublish.equals("0")) {
				List<ScheduleJob> scheduleJobList = schedulerManageService.getScheduleJobList();
				for (ScheduleJob scheduleJob : scheduleJobList) {
					String jobGroup = scheduleJob.getJobGroup();
					String jobName = scheduleJob.getJobName();
					if (jobGroup.equals(saveJobGroup) && jobName.equals(saveJobName)) {
						delFlag = true;
					}
				}
				if (delFlag) {
					schedulerManageService.deleteJob(saveScheduleJob);
				}
			} else if (isPublish != null || isPublish.equals("1")) {
				// 添加定时任务

				/**
				 * 如果是按月定时 会出现每月最后一天的情况 需要将字段中的0变更为L 添加两个定时任务 一个为固定日期 一个为最后一天
				 */
				String cron = "";

				if (job.getIntervalType().equals("1")
						&& (job.getIntervalValues().contains(",0") || job.getIntervalValues().contains("0,"))) {
					if (job.getIntervalValues().contains(",0")) {
						job.setIntervalValues(job.getIntervalValues().replaceAll(",0", ""));
						cron = getCron(job);
						saveScheduleJob.setCronExpression(cron);
						schedulerManageService.addJobCron(saveScheduleJob);
						job.setIntervalValues("L");
						cron = getCron(job);
						saveScheduleJob.setCronExpression(cron);
						schedulerManageService.addJobCron(saveScheduleJob);
					} else {
						job.setIntervalValues(job.getIntervalValues().replaceAll("0,", ""));
						cron = getCron(job);
						saveScheduleJob.setCronExpression(cron);
						schedulerManageService.addJobCron(saveScheduleJob);
						job.setIntervalValues("L");
						cron = getCron(job);
						saveScheduleJob.setCronExpression(cron);
						schedulerManageService.addJobCron(saveScheduleJob);
					}

				} else {
					cron = getCron(job);
					saveScheduleJob.setCronExpression(cron);
					schedulerManageService.addJobCron(saveScheduleJob);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResultCode<String>();
	}

	private String getCron(SchedulerJob job) {
		String cron = "";
		// 根据周期类型生成cron表达式
		String intervalType = job.getIntervalType();
		String intervalValues = job.getIntervalValues();
		Date validStartdate = job.getValidStartdate();
		Date validEnddate = job.getValidEnddate();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY");
		String startYear = sdf.format(validStartdate);
		String endYear = String.valueOf((Integer.parseInt(sdf.format(validEnddate)) + 1));
		// 开始时间
		// 时
		Integer intervalHour = job.getIntervalHour();
		// 分
		Integer intervalMinute = job.getIntervalMinute();
		Integer startHour = job.getStartHour();
		Integer startMinute = job.getStartMinute();
		Integer endHour = job.getEndHour();
		Integer endMinute = job.getEndMinute();
		switch (intervalType) {
		case "1": // 月
			cron = "0 " + intervalMinute + " " + intervalHour + " " + intervalValues + " * ? " + startYear + "-"
					+ endYear;
			break;
		case "2": // 周
			cron = "0 " + intervalMinute + " " + intervalHour + " ? * " + intervalValues + " " + startYear + "-"
					+ endYear;
			break;
		case "3": // 天
			cron = "0 " + intervalMinute + " " + intervalHour + " * * ? " + startYear + "-" + endYear;
			break;
		case "4": // 小时
			// 如果结束时间的分钟数比开始时间的分钟数小 则结束时间-1 避免如下情况 5:10 - 8:05分 8:10不作数 则
			// 将结果转为5:10-7:10 效果相同
			if (endMinute < startMinute) {
				endHour -= 1;
			}
			cron = "0 " + startMinute + " " + startHour + "-" + endHour + "/" + intervalValues + " * * ? " + startYear
					+ "-" + endYear;
			break;
		case "5": // 分钟
			cron = "0 " + startMinute + "/" + intervalValues + " " + startHour + "-" + endHour + " * * ? " + startYear
					+ "-" + endYear;
			break;
		}
		return cron;
	}

	// 修改workflow.isDependParent
	private void updateWorkflowIsDependParent(Long jobId, String isDependParent, Long userId, Date date) {
		WorkflowExample example = new WorkflowExample();
		example.createCriteria().andSchedulerJobIdEqualTo(jobId);

		Workflow workflow = workflowMapper.selectByExample(example).get(0);
		if (workflow == null) {
			throw new RuntimeException("can not found workflow,jobId is :" + jobId);
		}
		logger.info("update workflow before,workflow is : " + JSON.toJSONString(workflow));

		Workflow update = new Workflow();
		update.setId(workflow.getId());
		update.setIsDependParent(isDependParent);
		update.setUpdateUser(userId);
		update.setUpdateTime(date);

		workflowMapper.updateByPrimaryKeySelective(update);

		logger.info("update workflow obj is : " + JSON.toJSONString(update));
	}

	// 设置job责任人
	private void setJobRes(Long jobId, String userIds) {
		SchedulerJobResponsibleExample delResExample = new SchedulerJobResponsibleExample();
		delResExample.createCriteria().andSchedulerJobIdEqualTo(jobId);

		schedulerJobResponsibleMapper.deleteByExample(delResExample);

		List<User> users = userApi.getUsersByIds(userIds).getData();
		for (User user : users) {
			SchedulerJobResponsible record = new SchedulerJobResponsible();
			record.setSchedulerJobId(jobId);
			record.setUserId(user.getId());
			record.setUserName(user.getName());
			schedulerJobResponsibleMapper.insert(record);
		}

	}

	@Override
	public ResultCode<String> frozenJob(Long jobId, Long tenantId, Long userId, String isFrozen, String userName,
			Long projectId) {
		SchedulerJobExample example = new SchedulerJobExample();
		example.createCriteria().andIdEqualTo(jobId).andTenantIdEqualTo(tenantId);
		List<SchedulerJob> jobs = schedulerJobMapper.selectByExample(example);

		if (CollectionUtils.isEmpty(jobs)) {
			throw new RuntimeException(
					"can not found job,params -> jobId is : " + jobId + ",tenantId is : " + tenantId);
		}

		SchedulerJob job = jobs.get(0);

		SchedulerJob update = new SchedulerJob();

		update.setId(job.getId());
		update.setIsFrozen(isFrozen);
		update.setUpdateUser(userId);
		update.setUpdateTime(new Date());

		schedulerJobMapper.updateByPrimaryKeySelective(update);
		// 添加操作日志
		projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
				OperateObjectTypeConstants.JOB, null, job.getId(), job.getJobName(), userId, userName);

		return new ResultCode<String>();
	}

	@Override
	public ResultCode<List<SchedulerJobResponsible>> getJobResponsibles(Long jobId) {
		SchedulerJobResponsibleExample example = new SchedulerJobResponsibleExample();

		example.createCriteria().andSchedulerJobIdEqualTo(jobId);

		List<SchedulerJobResponsible> list = schedulerJobResponsibleMapper.selectByExample(example);
		ResultCode<List<SchedulerJobResponsible>> rc = new ResultCode<List<SchedulerJobResponsible>>();
		rc.setData(list);
		return rc;
	}

	@Override
	public ResultCode<String> editJobResponsibles(Long jobId, String userIds, Long projectId, Long tenantId,
			Long userId, String username) {
		setJobRes(jobId, userIds);
		SchedulerJobExample example = new SchedulerJobExample();
		example.createCriteria().andIdEqualTo(jobId).andTenantIdEqualTo(tenantId);
		List<SchedulerJob> jobs = schedulerJobMapper.selectByExample(example);
		SchedulerJob job = jobs.get(0);
		// 添加操作日志
		projectTimelineService.insertOptLog(projectId, tenantId, OperateTypeConstants.EDITED,
				OperateObjectTypeConstants.JOB, null, job.getId(), job.getJobName(), userId, username);
		return new ResultCode<String>();
	}

	@Override
	public ResultCode<List<SchedulerNotify>> getJobNotify(Long jobId) {
		// 先清空,再填写数据就较为方便
		SchedulerNotifyExample delExample = new SchedulerNotifyExample();
		delExample.createCriteria().andSchedulerJobIdEqualTo(jobId);
		List<SchedulerNotify> list = schedulerNotifyMapper.selectByExample(delExample);
		ResultCode<List<SchedulerNotify>> code = new ResultCode<>();
		code.setData(list);
		return code;
	}

	@Override
	public ResultCode<String> deleteJobs(String jobs) {
		String[] splitjob = jobs.split(",");
		//List<String> cantDeltask= new ArrayList<String>();
		for (int i = 0; i <splitjob.length; i++) {
			long jobId = Long.parseLong(splitjob[i]);
			schedulerJobMapper2.deleteJob(jobId);
			// 删除调度配置
			deleteScheduling(jobId);
		}
		return new ResultCode<String>();
	}
	// 删除调度任务
	void deleteScheduling (Long jobId) {
		String saveJobGroup = "workflowjob";
		String saveJobName = "workflowjob_" + jobId;
		ScheduleJob saveScheduleJob = new ScheduleJob();
		saveScheduleJob.setJobName(saveJobName);
		saveScheduleJob.setJobGroup(saveJobGroup);
		try{
			schedulerManageService.deleteJob(saveScheduleJob);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
