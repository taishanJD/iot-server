package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.model.dataobj.SingleNodeTestExecuteLog;
import com.quarkdata.data.model.vo.WorkflowNodeRelVO;
import com.quarkdata.data.util.common.Logget.RootLoggerUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.model.common.ListResult;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.SingleNodeTestExecuteLogService;
import com.quarkdata.data.web.api.controller.RouteKey;

import java.util.List;

/**
 * 单节点测试执行日志表
 * 
 * @author typ 2018年5月11日
 */
@RequestMapping(RouteKey.PREFIX_API + "/single_node_test_execute_log")
@RestController
public class SingleNodeTestExecuteLogController {
	static public Logger logger = Logger.getLogger(SingleNodeTestExecuteLogController.class);
	@Autowired
	SingleNodeTestExecuteLogService singleNodeTestExecuteLogService;

	@RequestMapping(value = "/list")
	public ResultCode<ListResult<SingleNodeTestExecuteLog>> list(
			Long schedulerJobId,
			String name,
			String status,
			String type,
			String responsibleType,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
			Long tenantId = UserInfoUtil.getIncorporation().getId();
			Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
			ResultCode<ListResult<SingleNodeTestExecuteLog>> resultCode = singleNodeTestExecuteLogService
					.getList(schedulerJobId, tenantId, userId, name, status, type,
							responsibleType, page, pageSize);
			//logger.info("|----------------");
			return resultCode;
	}

	/**
	 * @Description: 通过单节点的ID获得节点间的关系
	 * @Param:
	 * @return:
	 * @Author: ning
	 * @Date: 2018/7/23
	 */

	@RequestMapping(value="/get_node_list_single/{nodeId}",method = RequestMethod.GET)
	public ResultCode<List<WorkflowNodeRelVO>> getNodeListSingle(@PathVariable("nodeId") Long nodeId){
		ResultCode<List<WorkflowNodeRelVO>> nodeRelList = singleNodeTestExecuteLogService.getNodeListSingle(nodeId);
		return nodeRelList;
	}
	/**
	* @Description:  删除单节点测试(顺带删除运行日志与操作日志)
	* @Param:  单节点测试的Id
	* @return:
	* @Author: ning
	* @Date: 2018/8/7
	*/
	@RequestMapping(value="/delete_single_test",method = RequestMethod.GET)
	public ResultCode<String> deleteSingleTest(String testIds){
		ResultCode<String> resultCode = singleNodeTestExecuteLogService.deleteSingleTest(testIds);
		return resultCode;
	}

}
