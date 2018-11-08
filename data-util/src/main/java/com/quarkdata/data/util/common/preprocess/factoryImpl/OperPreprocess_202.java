package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;
import com.quarkdata.data.util.MeaningUtils;

/**
 * step 202 将无效值存入新字段
 * <p>
 * useColumnList 要操作的字段
 * <p>
 * isLightHight: 0,// 0 非高亮 1 高亮
 * <p>
 * switchStatus: 0, // 0 未打开 2 打开
 * <p>
 * newColumnName ： 新字段名
 * <p>
 * semantics：所选择的语义
 *
 * @author guoning 2018年7月5日
 */
public class OperPreprocess_202 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		int flag = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获得当前的字段
		String useColumn = operStep.getUseColumn();
		// 获得数据无效时的新字段
		String newColumnName = operStep.getNewColumnName();
		// 获得数据无效时的语义
		Integer semantics = operStep.getSemantics();
		// 循环字段值
		for (Document document : dataList) {
			// 获得当前字段对应的语义
			int curentsemantics = MeaningUtils.autoDetectMeaning(document.get(useColumn));
			// 判断当前字段的语义，与传递的语义是否相同?相同不操作:不相同添加新字段，并给新字段添加传入的语义和字段名称
			if (curentsemantics != semantics) {
				if (flag == 0) {
					// 字段只添加一次
					DatasetSchemaVO datasetSchemaVO = new DatasetSchemaVO();
					datasetSchemaVO.setColumnName(newColumnName);
					// 设置为默认语义预览时可以得到字段的语义
					datasetSchemaVO.setMeaning("1");
					dataSetSchemaList.add(datasetSchemaVO);
					flag = 1;
				}
				document.put(newColumnName, document.get(useColumn));
			}
		}

		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}
}
