package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step208 根据经纬度合成坐标
 * 
 * longitude： 经度字段
 * 
 * latitude ： 纬度字段
 * 
 * newColumnName ： 合成的字段
 * 
 * isLightHight: 0,// 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 2 打开
 * 
 * @author WangHao 2018年7月6日
 */
public class OperPreprocess_208 extends OperPreprocess {

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
		datasetSchemaVO.setColumnType("7");
		datasetSchemaVO.setInvalidProportion(100f);
		datasetSchemaVO.setMeaning("1");
		String[] meanList = new String[2];
		meanList[0] = "1";
		datasetSchemaVO.setMeanings(meanList);
		dataSetSchemaList.add(datasetSchemaVO);
		for (Document document : dataList) {
			String longitude = document.get(operStep.getLongitude()).toString();
			String latitude = document.get(operStep.getLatitude()).toString();
			document.put(operStep.getNewColumnName(), "(" + longitude + "," + latitude + ")");
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}
}
