package com.quarkdata.data.service.impl;

import java.util.List;

import com.quarkdata.data.dal.dao.*;
import com.quarkdata.data.model.dataobj.*;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;
import com.quarkdata.data.service.WorkflowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quarkdata.data.model.common.Constants;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.SingleNodeTestExecuteLogService;

@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class SingleNodeTestExecuteLogServiceImpl implements
		SingleNodeTestExecuteLogService {

	@Autowired
	SingleNodeTestExecuteLogMapper2 singleNodeTestExecuteLogMapper2;
	@Autowired
	SingleNodeTestExecuteLogMapper singleNodeTestExecuteLogMapper;
	@Autowired
	WorkflowNodeMapper workflowNodeMapper;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	OperationLogMapper2 operationLogMapper2;
	@Autowired
	RunningLogMapper2 runningLogMapper2;
	@Override
	public ResultCode<ListResult<SingleNodeTestExecuteLog>> getList(
			Long schedulerJobId, Long tenantId, Long userId, String name,
			String status, String type, String responsibleType, int page,
			int pageSize) {
		Long userIdParam = null;
		// 我的
		if (Constants.JOB_RESPONSIBLE_TYPE_MY.equals(responsibleType)) {
			userIdParam = userId;
		}
		Integer limitStart = (page - 1) * pageSize;
		Integer limitEnd = pageSize;

		List<SingleNodeTestExecuteLog> list = singleNodeTestExecuteLogMapper2
				.getList(tenantId, schedulerJobId, name, status, type,
						userIdParam, limitStart, limitEnd);

		Long listCount = singleNodeTestExecuteLogMapper2.getListCount(tenantId,
				schedulerJobId, name, status, type, userIdParam);
		ListResult<SingleNodeTestExecuteLog> listResult = new ListResult<SingleNodeTestExecuteLog>();
		listResult.setListData(list);
		listResult.setPageNum(page);
		listResult.setPageSize(pageSize);
		listResult.setTotalNum(listCount.intValue());
		ResultCode<ListResult<SingleNodeTestExecuteLog>> resultCode = new ResultCode<ListResult<SingleNodeTestExecuteLog>>();
		resultCode.setData(listResult);
		return resultCode;
	}

	@Override
	public ResultCode<List<WorkflowNodeRelVO>> getNodeListSingle(Long nodeId) {
		SingleNodeTestExecuteLog log = singleNodeTestExecuteLogMapper.selectByPrimaryKey(nodeId);
		WorkflowNode node = workflowNodeMapper.selectByPrimaryKey(log.getWorkflowNodeId());
		//调用已经写好的获得节点间关系的Service的方法
		ResultCode<List<WorkflowNodeRelVO>> nodeRelList = workflowService.getNodeRelList(node.getWorkflowId());
		return nodeRelList;
	}

	@Override
	public ResultCode<String> deleteSingleTest(String testIds) {
		System.err.println(testIds);
		String[] split = testIds.split(",");
		System.err.println(split.length);
		for(String testId:split){
			singleNodeTestExecuteLogMapper.deleteByPrimaryKey(Long.parseLong(testId));  // 删除单节点测试Log
			operationLogMapper2.deleteOperaLog(1l,Long.parseLong(testId));   // 0 task  1单节点
			runningLogMapper2.deleteRunningLog(2l,Long.parseLong(testId));   // 0 task 1task下的节点 2
		}
		ResultCode resultCode = new ResultCode();
		return resultCode;
	}

}
