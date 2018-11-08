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
 * step101参数 移除/保留字段
 * 
 * stepType: 0, // 1 移除 2 保留;
 * 
 * useColumnList: [], // 操作字段的列表
 * 
 * isLightHight: 0, // 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 1 打开
 * 
 * paramType: 0 // 0 单个参数 1 多个参数 2 所有参数
 * 
 * @author WangHao 2018年7月4日
 */
public class OperPreprocess_101 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		// 判断开关是否打开，如果开关未打开，则直接关闭
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		// 判断是否是保留所有字段
		if (operStep.getParamType() == 2 && operStep.getStepType() == 2) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<String> useColumnList = operStep.getUseColumnList();
		// 匹配到的字段List
		List<DatasetSchemaVO> matchingDataSetSchemaList = new ArrayList<DatasetSchemaVO>();
		for (String useColumn : useColumnList) {
			for (DatasetSchemaVO datasetSchemaVO : dataSetSchemaList) {
				if (datasetSchemaVO.getColumnName().equals(useColumn)) {
					matchingDataSetSchemaList.add(datasetSchemaVO);
					break;
				}
			}
		}
		if (operStep.getStepType() == 1) {// 移除匹配到的字段
			dataSetSchemaList.removeAll(matchingDataSetSchemaList);
		} else if (operStep.getStepType() == 2) { // 仅保留匹配到的字段
			dataSetSchemaList = matchingDataSetSchemaList;
		}
		// 对数据进行操作
		List<Document> operDataList = new ArrayList<Document>();
		for (Document operDocument : dataList) {
			Document document = new Document();
			for (DatasetSchemaVO operSchema : dataSetSchemaList) {
				document.put(operSchema.getColumnName(), operDocument.get(operSchema.getColumnName()));
			}
			operDataList.add(document);
		}
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", operDataList);
		return resultMap;
	}
}
