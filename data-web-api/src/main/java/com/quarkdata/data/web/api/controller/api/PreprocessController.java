package com.quarkdata.data.web.api.controller.api;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.PreprocessService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.BaseController;
import com.quarkdata.data.web.api.controller.RouteKey;

/**
 * 预处理controller step : 获取预处理列表-执行预处理步骤-返回
 * 
 * @author WangHao 2018年7月2日
 */
@RestController
@RequestMapping(RouteKey.PREFIX_API + RouteKey.PREPROCESS)
public class PreprocessController extends BaseController {

	@Autowired
	private PreprocessService preprocessService;

	/**
	 * 获取预处理页面列表数据
	 * @param dataSetId:数据集ID
	 * @param nodeId:数据集节点ID
	 * @param operStepList:预处理步骤列表
	 */
	@RequestMapping(value = "/get_preprocess_list", method = RequestMethod.POST)
	public ResultCode getPreprocessList(@RequestBody Map<String, Object> paramMap) {
		if (paramMap.get("dataSetId") == null) {
			return ResultUtil.error(-1, "数据集ID不能为空");
		}
		return preprocessService.getPreprocessList(paramMap);
	}

	/**
	 * 获取预处理页面处理步骤数据
	 */
	@ResponseBody
	@RequestMapping(value = "/get_preprocess_step_list", method = RequestMethod.POST)
	public ResultCode<JSONArray> getProcessStepList(@RequestBody Map<String, Object> paramMap) {
		return preprocessService.getProcessStepList(paramMap);
	}

	/**
	 * 保存预处理节点及预处理步骤
	 * @param
	 */
	@RequestMapping(value = "/savePreprocess", method = RequestMethod.POST)
	public ResultCode savePreprocess(@RequestBody Map<String, Object> paramMap){
		if (paramMap.get("inputSetId") == null) {
			return ResultUtil.error(-1, "输入数据集ID不能为空");
		}
		if (paramMap.get("workflowid") == null) {
			return ResultUtil.error(-1, "工作流ID不能为空");
		}
		Long tenantId = UserInfoUtil.getIncorporation().getId();
		Long userId = UserInfoUtil.getUserInfoVO().getUser().getId();
		String userName = UserInfoUtil.getUserInfoVO().getUser().getName();
		paramMap.put("tenantId", tenantId);
		paramMap.put("userId", userId);
		paramMap.put("userName", userName);
		return preprocessService.savePreprocess(paramMap);
	}
}
