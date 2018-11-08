package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step 201 填充空值
 * <p>
 * useColumnList 操作的字段
 * <p>
 * isLightHight: 0,// 0 非高亮 1 高亮
 * <p>
 * switchStatus: 0, // 0 未打开 2 打开
 * <p>
 * operParam：要填充的值
 *
 * @author guoning 2018年7月5日
 */
public class OperPreprocess_201 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获得要操作的字段值
		List<String> useColumnList = operStep.getUseColumnList();
		// 获得为空需要替换的值
		String operParam = operStep.getOperParam();
		// 循环将dataList中为空的的值的替换为operParam
		for (Document document : dataList) {
			for (String need : useColumnList) {
				if (null == document.get(need) || "" == document.get(need)) {
					document.put(need, operParam);
				}
			}
		}
		// 将操作后的值，赋值给Map返回
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}
}
