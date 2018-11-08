package com.quarkdata.data.service;

public interface ProjectTimelineService {

	void insertOptLog(Long projectId, Long tenantId, String operateType,
			String operateObjectParentType, String operateObjectType,
			Long operateObjectId, String operateObjectName, Long createUser, String createUserName);
}
