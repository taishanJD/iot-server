package com.quarkdata.data.web.api.controller.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.WorkflowExecutorService;
import com.quarkdata.data.web.api.controller.RouteKey;

/**
 * workflow executor controller
 * 
 * @author typ 2018年5月29日
 */
@RequestMapping(RouteKey.PREFIX_INTERNAL + "/workflow")
@RestController
public class WorkflowExecutorController {

	@Autowired
	WorkflowExecutorService workflowExecutorService;

	@RequestMapping("/exec")
	public ResultCode<String> execute(Long schedulerJobId,
			Long schedulerTaskId, String bizDateParam, String sysTimeParam) {
		ResultCode<String> resultCode = workflowExecutorService.execute(
				schedulerJobId, schedulerTaskId, bizDateParam, sysTimeParam);
		return resultCode;
	}
}
