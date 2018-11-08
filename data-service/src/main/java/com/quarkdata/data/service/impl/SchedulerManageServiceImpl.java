package com.quarkdata.data.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quarkdata.data.dal.dao.ScheduleJobMapper;
import com.quarkdata.data.model.common.ScheduleJob;
import com.quarkdata.data.service.SchedulerManageService;

@Service
public class SchedulerManageServiceImpl implements SchedulerManageService {

	@Autowired
	private Scheduler scheduler;
	@Autowired
	private ScheduleJobMapper scheduleJobMapper;

	/**
	 * 新增任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addJobCron(ScheduleJob scheduleJob) throws Exception {
		TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		// 任务触发
		Trigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		if (null == trigger) {
			JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(scheduleJob.getClazz()))
					.withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
			jobDetail.getJobDataMap().put("scheduleJob", scheduleJob);
			CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
			trigger = TriggerBuilder.newTrigger().withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
					.withSchedule(cronScheduleBuilder.withMisfireHandlingInstructionDoNothing()).build();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduleJobMapper.saveJob(scheduleJob);
		} else {
			this.updateJobCron(scheduleJob);
		}
	}

	/**
	 * 更新任务的时间表达式
	 * 
	 * @param scheduleJob
	 */
	public void updateJobCron(ScheduleJob scheduleJob) throws Exception {
		this.deleteJob(scheduleJob);
		this.addJobCron(scheduleJob);
	}

	/**
	 * 暂停任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */

	public void pauseJob(ScheduleJob scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);
	}

	/**
	 * 暂停全部任务
	 * 
	 * @throws SchedulerException
	 */

	public void pauseAll() throws Exception {
		scheduler.pauseAll();
	}

	/**
	 * 恢复任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */

	public void resumeJob(ScheduleJob scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.resumeJob(jobKey);
	}

	/**
	 * 恢复所有任务
	 * 
	 * @throws Exception
	 */

	public void resumeAll() throws Exception {
		scheduler.resumeAll();
	}

	/**
	 * 删除任务后，所对应的trigger也将被删除
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */

	public void deleteJob(ScheduleJob scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.pauseJob(jobKey);// 先暂停任务
		scheduler.deleteJob(jobKey);// 再删除任务
		scheduleJobMapper.deleteScheduler(scheduleJob);
	}

	/**
	 * 立即运行任务
	 * 
	 * @param scheduleJob
	 * @throws Exception
	 */

	public void triggerJob(ScheduleJob scheduleJob) throws Exception {
		JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
		scheduler.triggerJob(jobKey);
	}

	/**
	 * 获取quartz调度器的计划任务
	 * 
	 * @return
	 */

	public List<ScheduleJob> getScheduleJobList() {
		List<ScheduleJob> jobList = null;
		try {
			GroupMatcher<JobKey> matcher = GroupMatcher.anyJobGroup();
			Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
			jobList = new ArrayList<ScheduleJob>();
			for (JobKey jobKey : jobKeys) {
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				for (Trigger trigger : triggers) {
					ScheduleJob job = new ScheduleJob();
					job.setJobName(jobKey.getName());
					job.setJobGroup(jobKey.getGroup());
					job.setClazz(jobKey.getClass().toString());
					job.setJobDesc("触发器:" + trigger.getKey());
					Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
					job.setJobStatus(triggerState.name());
					CronTrigger cronTrigger = (CronTrigger) trigger;
					String cronExpression = cronTrigger.getCronExpression();
					job.setCronExpression(cronExpression);
					jobList.add(job);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}

	/**
	 * 获取quartz调度器的运行任务
	 * 
	 * @return
	 */

	public List<ScheduleJob> getScheduleJobRunningList() {
		List<ScheduleJob> jobList = null;
		try {
			List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
			jobList = new ArrayList<ScheduleJob>(executingJobs.size());
			for (JobExecutionContext executingJob : executingJobs) {
				ScheduleJob job = new ScheduleJob();
				JobDetail jobDetail = executingJob.getJobDetail();
				JobKey jobKey = jobDetail.getKey();
				Trigger trigger = executingJob.getTrigger();
				job.setJobName(jobKey.getName());
				job.setJobGroup(jobKey.getGroup());
				job.setClazz(jobKey.getClass().toString());
				job.setJobDesc("触发器:" + trigger.getKey());
				Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
				job.setJobStatus(triggerState.name());
				CronTrigger cronTrigger = (CronTrigger) trigger;
				String cronExpression = cronTrigger.getCronExpression();
				job.setCronExpression(cronExpression);
				jobList.add(job);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jobList;
	}
}
