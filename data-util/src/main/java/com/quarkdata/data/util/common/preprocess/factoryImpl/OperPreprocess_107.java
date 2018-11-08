package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step 107 统计字段中符合的字符出现的次数
 * 
 * useColumn 操作的字段
 * 
 * isLightHight: 0,// 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 2 打开
 * 
 * newColumnName ： 新字段名
 * 
 * operParam ： 匹配的参数
 * 
 * @author WangHao 2018年7月5日
 */
public class OperPreprocess_107 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		// 判断开关是否打开，如果开关未打开，则直接关闭
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		// 创建新字段
		DatasetSchemaVO datasetSchemaVO = new DatasetSchemaVO();
		datasetSchemaVO.setColumnName(operStep.getNewColumnName());
		datasetSchemaVO.setColumnType("2");
		datasetSchemaVO.setInvalidProportion(100f);
		datasetSchemaVO.setMeaning("3");
		String[] meanList = new String[2];
		meanList[0] = "3";
		datasetSchemaVO.setMeanings(meanList);
		dataSetSchemaList.add(datasetSchemaVO);
		// 操作的字段
		String useColumn = operStep.getUseColumn();
		// 循环列表
		for (Document document : dataList) {
			String column = document.get(useColumn).toString();
			String[] paramList = null;
			if (null != column && !column.equals("")) {
				paramList = column.split(operStep.getOperParam());
			}
			if (paramList != null) {
				document.put(operStep.getNewColumnName(), String.valueOf(paramList.length - 1));
			} else {
				document.put(operStep.getNewColumnName(), "0");
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}
}
