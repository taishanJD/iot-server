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
 * step102 参数 移除/保留符合的记录/值
 * 
 * stepType: 0,// 1.保留匹配的记录 2.清除匹配的记录 3.清除匹配的值 4.清除不匹配的值
 * 
 * useColumnList: [], // 可以使用的字段
 * 
 * isLightHight: 0,// 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 2 打开
 * 
 * matchType: 0, // 匹配方式 完全匹配 0 包含 1
 * 
 * matchCase: 0, // 是否区分大小写 0 否 1 是
 * 
 * operParamList: []
 * 
 * @author WangHao 2018年7月4日
 */
public class OperPreprocess_102 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		List<Document> operDocumentList = new ArrayList<Document>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 判断开关是否打开，如果开关未打开，则直接关闭
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}

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

	// 检查该字段是否匹配
	private boolean checkColumn(Preprocess operStep, String dataColumn) {
		// 是否区分大小写 0 否 1 是 // 是否完全匹配或包含 匹配方式 完全匹配 0 包含 1
		if (operStep.getMatchCase() == 0 && operStep.getMatchType() == 0) {
			if (checkWordNoCaseAndFull(dataColumn, operStep.getOperParamList())) {
				return true;
			}
		}
		if (operStep.getMatchCase() == 0 && operStep.getMatchType() == 1) {
			if (checkWordNCaseAndContain(dataColumn, operStep.getOperParamList())) {
				return true;
			}
		}
		if (operStep.getMatchCase() == 1 && operStep.getMatchType() == 0) {
			if (checkWordCaseAndFull(dataColumn, operStep.getOperParamList())) {
				return true;
			}

		}
		if (operStep.getMatchCase() == 1 && operStep.getMatchType() == 1) {
			if (checkWordCaseAndContain(dataColumn, operStep.getOperParamList())) {
				return true;
			}

		}
		return false;
	}

	// 检查 区分大小写 且 全词匹配
	private boolean checkWordCaseAndFull(String dataColumn, List<String> operParamList) {
		return operParamList.contains(dataColumn);
	}

	// 检查 不区分大小写 且 全词匹配
	private boolean checkWordNoCaseAndFull(String dataColumn, List<String> operParamList) {
		boolean flag = false;
		for (String param : operParamList) {
			if (param.toUpperCase().equals(dataColumn.toUpperCase())) {
				flag = true;
			}
		}
		return flag;
	}

	// 检查 区分大小写 且 包含
	private boolean checkWordCaseAndContain(String dataColumn, List<String> operParamList) {
		boolean flag = false;
		for (String param : operParamList) {
			if (dataColumn.contains(param)) {
				flag = true;
			}
		}
		return flag;
	}

	// 检查 不区分大小写 且 包含
	private boolean checkWordNCaseAndContain(String dataColumn, List<String> operParamList) {
		boolean flag = false;
		for (String param : operParamList) {
			if (dataColumn.toUpperCase().contains(param.toUpperCase())) {
				flag = true;
			}
		}
		return flag;
	}
}
