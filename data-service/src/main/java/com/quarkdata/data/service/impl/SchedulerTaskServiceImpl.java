package com.quarkdata.data.service.impl;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.dal.rest.quarkshare.UserApi;
import com.quarkdata.data.model.dataobj.*;
import com.quarkdata.data.model.vo.TaskLogDetail;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;
import com.quarkdata.data.service.UserService;
import com.quarkdata.data.service.WorkflowService;
import com.quarkdata.data.util.common.Logget.RootLoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.vo.SchedulerTaskVO;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.SchedulerTaskService;

@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class SchedulerTaskServiceImpl implements SchedulerTaskService {
	
	@Autowired
	SchedulerTaskMapper2 schedulerTaskMapper2;
	@Autowired
	SchedulerTaskMapper schedulerTaskMapper;
	@Autowired
	SchedulerJobMapper schedulerJobMapper;
	@Autowired
	WorkflowMapper workflowMapper;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	UserService userService;

	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public ResultCode<ListResult<SchedulerTaskVO>> getTaskList(Long jobId,
			Long tenantId,Long projectId,Long userId,String name, String jobType, String taskStatus,
			String responsibleType, String bizDate,int page,int pageSize) {
		//RootLoggerUtils.OpenLogRecord();
		Long responsibleTypeParam = null;
		// 我的
		if (Constants.JOB_RESPONSIBLE_TYPE_MY.equals(responsibleType)){
			responsibleTypeParam = userId;
		}
		Integer limitStart = (page - 1) * pageSize;
		Integer limitEnd = pageSize;
		List<SchedulerTaskVO> taskList = schedulerTaskMapper2.getTaskList(tenantId,projectId,name, jobType, taskStatus, bizDate, responsibleTypeParam, jobId,limitStart,limitEnd);
		// 无从下手，取巧的做法，
		for (int i = 0; i < taskList.size(); i++) {
			List<SchedulerJobResponsible> tasklist = taskList.get(i).getUsers();  // 获得一个数组 []
			SchedulerJobResponsibleKey needs = tasklist.get(0); //  得到数组的第一个值
			String[] snNameIds = ((SchedulerJobResponsible) needs).getUserName().split(",");  //得到userName并分割字符串
			tasklist.remove(0);
			for (int j = 0; j < snNameIds.length; j++) {
				String[] NameIds = snNameIds[j].split(":");    // name:id
				SchedulerJobResponsible sjr = new SchedulerJobResponsible();
				if (NameIds.length >= 2) {
					sjr.setUserName(NameIds[0]);

					sjr.setUserId(Long.valueOf(NameIds[1]));
					tasklist.add(sjr);
				}
			}
		}
		logger.info("获得所有的Task列表");
		// count
		Long listCount = schedulerTaskMapper2.getTaskListCount(tenantId,projectId,name, jobType, taskStatus, bizDate, responsibleTypeParam, jobId);
		
		ListResult<SchedulerTaskVO> listResult = new ListResult<SchedulerTaskVO>();
		listResult.setListData(taskList);
		listResult.setPageNum(page);
		listResult.setPageSize(pageSize);
		listResult.setTotalNum(listCount.intValue());
		ResultCode<ListResult<SchedulerTaskVO>> resultCode = new ResultCode<ListResult<SchedulerTaskVO>>();
		resultCode.setData(listResult);
		return resultCode;
	}

	@Override
	public ResultCode<List<WorkflowNodeRelVO>> getNodeRelList(Long taskId) {
		// 获得task得到jobid，再通过jonID获得workflow的id
		SchedulerTask task = schedulerTaskMapper.selectByPrimaryKey(taskId);
		SchedulerJob job = schedulerJobMapper.selectByPrimaryKey(task.getSchedulerJobId());
		WorkflowExample example = new WorkflowExample();
		example.createCriteria().andSchedulerJobIdEqualTo(job.getId());
		List<Workflow> workflows = workflowMapper.selectByExample(example);
		//调用已经写好的获得节点间关系的Service的方法
		ResultCode<List<WorkflowNodeRelVO>> nodeRelList = workflowService.getNodeRelList(workflows.get(0).getId());
		return nodeRelList;
	}

	@Override
	public ResultCode<TaskLogDetail> getTaskDetail(Long taskId, Long jobId,Long tenantId) {
		// 查询数据在此封装
		TaskLogDetail taskLogDetail = new TaskLogDetail();
		SchedulerTask task = schedulerTaskMapper.selectByPrimaryKey(taskId);
		taskLogDetail.setTaskStatus(task.getTaskStatus());
		taskLogDetail.setScheduleDatetime(task.getAlarmTime());
		taskLogDetail.setStartTime(task.getStartTime());
		taskLogDetail.setEndTime(task.getEndTime());
		taskLogDetail.setTaskType(task.getIsManual());
		// 获得创建人的userid
		SchedulerJob job = schedulerJobMapper.selectByPrimaryKey(jobId);
		WorkflowExample example = new WorkflowExample();
		taskLogDetail.setInterval(job.getIntervalValues());
		example.createCriteria().andSchedulerJobIdEqualTo(job.getId());
		List<Workflow> workflows = workflowMapper.selectByExample(example);
		// 获得创建人
		String userName = getUserName(tenantId, workflows.get(0).getCreateUser());
		taskLogDetail.setCreateUser(userName);
		ResultCode<TaskLogDetail> resultCode = new ResultCode<>();
		resultCode.setData(taskLogDetail);
		return resultCode;
	}

	@Override
	public ResultCode<String> deleteTask(String taskIds) {
		System.err.println(taskIds);
		String[] splitId = taskIds.split(",");
		for (int i = 0; i <splitId.length ; i++) {
			System.err.println(splitId[i]);
		}
		int code=90000;
		for (String id : splitId) {
			long taskId = Long.parseLong(id);
			Long cantDelCount = schedulerTaskMapper2.getcantDelTask(taskId, "2", "6");
			if(cantDelCount>0){   //
				code = 1;
				break;
			}else{
				schedulerTaskMapper.deleteByPrimaryKey(taskId);
				code=0;
			}
			//TODO 删除task后的其他操作
		}
		ResultCode<String> resultCode = new ResultCode<>();
		resultCode.setCode(code);
		return resultCode;
	}

	@Override
	public ResultCode<Long> getcantDelTasknumber(Long jobId, String statusOne, String statusTwo) {
		Long number = schedulerTaskMapper2.getcantDelTasknumber(jobId, statusOne, statusTwo);
		ResultCode<Long> resultCode = new ResultCode<>();
		resultCode.setData(number);
		return resultCode;
	}


	/**
	 * 根据userId获取userName
	 *   TODO 获得Username的方法
	 * @param tenantId
	 * @param userId
	 * @return
	 */
	private String getUserName(Long tenantId, Long userId) {
		UserApi userApi = new UserApi();
		ResultCode resultCode = userApi.getInfo(tenantId, userId);
		HashMap<String,Object> userFullInfo = (HashMap<String, Object>) resultCode.getData();
		String userName = ((JSONObject)userFullInfo.get("basicInfo")).getString("name");
		return userName;
	}
}
