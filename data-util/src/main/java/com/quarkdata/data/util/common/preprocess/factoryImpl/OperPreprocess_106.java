package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step106 移除有空值的记录
 * 
 * useColumnList: [], // 可以使用的字段
 * 
 * isLightHight: 0,// 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 2 打开
 * 
 * stepType 1：移除记录 2： 保留记录
 * 
 * @author WangHao 2018年7月5日
 */
public class OperPreprocess_106 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		// 判断开关是否打开，如果开关未打开，则直接关闭
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Document> operDocumentList = new ArrayList<Document>();
		// step1 循环数据列表
		for (Document document : dataList) {
			for (String column : operStep.getUseColumnList()) {
				if (document.get(column) == null || document.get(column).toString() == "") {
					operDocumentList.add(document);
				}
			}
		}
		if (operStep.getStepType() == 1) {// 移除记录
			dataList.removeAll(operDocumentList);
		} else {// 保留记录
			dataList = operDocumentList;
		}
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}
}
