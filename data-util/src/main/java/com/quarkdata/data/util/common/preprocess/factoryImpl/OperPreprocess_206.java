package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step206 标准化日期（加字段）
 *
 * useColumnList: [], // 可以使用的字段
 * 
 * newColumnName: 新字段名
 * 
 * isLightHight: 0,// 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 2 打开
 * 
 * dateFormat： 格式化的日期
 * 
 * paramType: 0 // 0 单个参数 1 多个参数 2 所有参数
 * 
 * @author WangHao 2018年7月6日
 */
public class OperPreprocess_206 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		// 判断开关是否打开，如果开关未打开，则直接关闭
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		// 新增字段
		List<String> useColumnList = operStep.getUseColumnList();
		for (String column : useColumnList) {
			DatasetSchemaVO datasetSchemaVO = new DatasetSchemaVO();
			if (operStep.getParamType() == null || operStep.getParamType() == 0) {
				datasetSchemaVO.setColumnName(operStep.getNewColumnName());
			} else {
				datasetSchemaVO.setColumnName("fmt_" + column);
			}
			datasetSchemaVO.setColumnType("8");
			datasetSchemaVO.setMeaning("5");
			datasetSchemaVO.setInvalidProportion(100f);
			String[] meanList = new String[2];
			meanList[0] = "5";
			datasetSchemaVO.setMeanings(meanList);
			dataSetSchemaList.add(datasetSchemaVO);
		}
		// 循环数据
		try {
			for (Document document : dataList) {
				for (String column : useColumnList) {
					// 获取列中的时间
					String columnDateStr = document.get(column).toString();
					
					// 将字符串转为Date
					SimpleDateFormat columnDateStrsdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date columnDate = columnDateStrsdf.parse(columnDateStr);
					SimpleDateFormat columnDatesdf = new SimpleDateFormat(operStep.getDateFormat());
					String columnDateresult = columnDatesdf.format(columnDate);
					if (operStep.getParamType() == null || operStep.getParamType() == 0) {
						document.put(operStep.getNewColumnName(), columnDateresult);
					} else {
						document.put("fmt_" + column, columnDateresult);
					}
				}
			}
		} catch (Exception e) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}
}
