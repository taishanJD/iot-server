package com.quarkdata.data.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.quarkdata.data.dal.dao.ProjectTimelineMapper;
import com.quarkdata.data.model.common.OperateObjectTypeConstants;
import com.quarkdata.data.model.common.OperateTypeConstants;
import com.quarkdata.data.model.dataobj.ProjectTimeline;
import com.quarkdata.data.service.ProjectTimelineService;

@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class ProjectTimelineServiceImpl implements ProjectTimelineService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ProjectTimelineMapper projectTimelineMapper;

	@Override
	public void insertOptLog(Long projectId, Long tenantId, String operateType,
			String operateObjectParentType, String operateObjectType,
			Long operateObjectId, String operateObjectName, Long createUser, String createUserName) {
		ProjectTimeline record = new ProjectTimeline();
		record.setProjectId(projectId);
		record.setTenantId(tenantId);
		record.setOperateType(operateType);
		record.setOperateObjectParentType(operateObjectParentType);
		record.setOperateObjectType(operateObjectType);
		record.setOperateObjectId(operateObjectId);
		record.setOperateObjectName(operateObjectName);
		record.setCreateUser(createUser);
		record.setCreateTime(new Date());
		record.setCreateUserName(createUserName);
		projectTimelineMapper.insert(record);
		logger.info("add operation log,obj is : {}",JSON.toJSONString(record));
	}

	/**
	 * 添加操作日志demo: addOptLog
	 */
	public void addOptLogDemo() {
		Long projectId = 1L;
		Long tanantId = 1L;
		Long operateObjectId = 2L;
		Long createUser = 3L;
		String createUserName = "admin";
		String operateObjectName = "table_1";
		insertOptLog(projectId, tanantId, OperateTypeConstants.CREATED,
				OperateObjectTypeConstants.WORKFLOWNODE, OperateObjectTypeConstants.WORKFLOWNODE_SYNC, operateObjectId,
				operateObjectName , createUser, createUserName);
	}

}
