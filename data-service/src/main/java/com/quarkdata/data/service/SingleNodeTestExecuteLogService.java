package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.model.dataobj.SingleNodeTestExecuteLog;
import com.quarkdata.data.model.vo.SingleNodeTestExecuteLogVO;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;

import java.util.List;

public interface SingleNodeTestExecuteLogService {

	ResultCode<ListResult<SingleNodeTestExecuteLog>> getList(
			Long schedulerJobId, Long tenantId,Long userId, String name, String status, String type,
			String responsibleType, int page, int pageSize);

	ResultCode<List<WorkflowNodeRelVO>> getNodeListSingle(Long nodeId);

	ResultCode<String> deleteSingleTest(String testId);
}
