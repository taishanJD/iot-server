package com.quarkdata.data.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.quarkdata.data.dal.dao.SchedulerJobMapper;
import com.quarkdata.data.model.common.ScheduleJob;
import com.quarkdata.data.model.dataobj.SchedulerJob;
import com.quarkdata.data.service.SchedulerManageService;
import com.quarkdata.data.service.WorkflowService;

/**
 * 工作流调度作业
 * 
 * @author WangHao 2018年7月18日
 */
public class CustomForJob implements Job {

	@Autowired
	private SchedulerJobMapper schedulerJobMapper;

	@Autowired
	private SchedulerManageService schedulerManageService;
	
	@Autowired
	private WorkflowService workflowService;

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String jobName = jobExecutionContext.getJobDetail().getKey().getName();
		String jobGroup = jobExecutionContext.getJobDetail().getKey().getGroup();
		SchedulerJob schedulerJob = schedulerJobMapper.selectByPrimaryKey(Long.parseLong(jobName.split("_")[1]));
		Date validStartdate = schedulerJob.getValidStartdate();
		Date validEnddate = schedulerJob.getValidEnddate();
		String intervalType = schedulerJob.getIntervalType();
		Date currentDate = new Date();
		try {
			// 如果当前执行时间在生效日期之前，则跳过
			if (currentDate.before(validStartdate)) {
				return;
			}
			// 如果当前时间在生效日期之后 则删除任务
			if (currentDate.after(validEnddate)) {
				ScheduleJob scheduleJob = new ScheduleJob();
				scheduleJob.setJobName(jobName);
				scheduleJob.setJobGroup(jobGroup);
				schedulerManageService.deleteJob(scheduleJob);
			}
			// 如果调度任务时间类型为分钟 需要判断是否超出分钟数
			if (intervalType.equals("5")) {
				Integer endHour = schedulerJob.getEndHour();
				Integer endMinute = schedulerJob.getEndMinute();	
				SimpleDateFormat checkSdf = new SimpleDateFormat("yyyy-MM-dd");
				String checkDateStr = checkSdf.format(currentDate);
				checkDateStr = checkDateStr + " " + endHour + "-" + endMinute + "-" + "00";
				Date checkDate = dateFormat.parse(checkDateStr);
				if (currentDate.after(checkDate)) {
					return;
				}
			}
			workflowService.execWorkflow(schedulerJob.getId(), null, null, null, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
