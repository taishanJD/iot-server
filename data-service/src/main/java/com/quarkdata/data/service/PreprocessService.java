package com.quarkdata.data.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.quarkdata.data.model.common.ResultCode;

/**
 * 预处理接口
 * 
 * @author WangHao 2018年7月2日
 */
public interface PreprocessService {

	/**
	 * 获取预处理列表
	 * 
	 * @param dataSetId:数据集ID
	 * @param nodeId:数据集节点ID
	 * @param operStepList:预处理步骤列表
	 */
	ResultCode getPreprocessList(Map<String, Object> paramMap);

	/**
	 * 获取预处理页面处理步骤数据
	 */
	ResultCode<JSONArray> getProcessStepList(Map<String, Object> paramMap);

	/**
	 * 保存、修改预处理节点
	 */
	ResultCode savePreprocess(Map<String, Object> paramMap);

}
