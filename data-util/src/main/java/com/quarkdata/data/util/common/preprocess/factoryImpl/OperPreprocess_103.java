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
 * step103 移除/保留某一数值范围的记录/值
 * 
 * stepType: 1.保留匹配的记录 2.清除匹配的记录 3.清除匹配的值 4.清除不匹配的值
 * 
 * useColumnList: [], // 可以使用的字段
 * 
 * isLightHight: 0,// 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 2 打开
 * 
 * operParamList:[], // 匹配的数值
 * 
 * "paramRange":0：大于 1：小于
 * 
 * @author WangHao 2018年7月5日
 */
public class OperPreprocess_103 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		// 判断开关是否打开，如果开关未打开，则直接关闭
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<Document> operDocumentList = new ArrayList<Document>();

		Integer stepType = operStep.getStepType();
		// 判断是哪种操作 并进行处理 stepType 1 ： 保留匹配的记录 2 ： 清除匹配的记录 3：清除匹配的值 4：清除不匹配的值
		if (stepType == 1 || stepType == 2) {
			// step1 循环数据列表
			for (Document document : dataList) {
				// step2 循环需要操作的列表
				for (String useColumn : operStep.getUseColumnList()) {
					// step3 取出需要验证的字段
					String dataColumn = document.get(useColumn).toString();
					boolean checkColumn = checkColumn(operStep, dataColumn);
					// 保留匹配的记录
					if (checkColumn) {
						operDocumentList.add(document);
						break;
					}
				}
			}
			if (stepType == 1) {
				resultMap.put("dataSetSchemaList", dataSetSchemaList);
				resultMap.put("dataList", operDocumentList);
			} else {
				dataList.removeAll(operDocumentList);
				resultMap.put("dataSetSchemaList", dataSetSchemaList);
				resultMap.put("dataList", dataList);
			}
		}
		if (stepType == 3 || stepType == 4) {
			// step1 循环数据列表
			for (Document document : dataList) {
				// step2 循环需要操作的列表
				for (String useColumn : operStep.getUseColumnList()) {
					// step3 取出需要验证的字段
					String dataColumn = document.get(useColumn).toString();
					boolean checkColumn = checkColumn(operStep, dataColumn);
					if (stepType == 3) {
						// 清除匹配的值
						if (checkColumn) {
							document.put(useColumn, null);
						}
					} else {
						// 清除不匹配的值
						if (!checkColumn) {
							document.put(useColumn, null);
						}
					}

				}
				resultMap.put("dataSetSchemaList", dataSetSchemaList);
				resultMap.put("dataList", dataList);
			}
		}
		return resultMap;
	}

	private boolean checkColumn(Preprocess operStep, String dataColumn) {
		// 0 大于
		if (operStep.getParamRange() == 0) {
			for (String param : operStep.getOperParamList()) {
				if (Float.parseFloat(dataColumn) > Float.parseFloat(param)) {
					return true;
				}
			}
		} else {
			// 小于
			for (String param : operStep.getOperParamList()) {
				if (Float.parseFloat(dataColumn) < Float.parseFloat(param)) {
					return true;
				}
			}
		}
		return false;
	}

}
