package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step 205 标准化数据
 * <p>
 * useColumnList 要操作的字段
 * <p>
 * isLightHight: 0,// 0 非高亮 1 高亮
 * <p>
 * switchStatus: 0, // 0 未打开 2 打开
 * <p>
 * paramType ： 要操作的类型
 *
 * @author guoning 2018年7月6日
 */
public class OperPreprocess_205 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获得要操作的字段
		List<String> columnList = operStep.getUseColumnList();
		// 获得要操作的的类型
		Integer paramType = operStep.getStepType();

		for (String colum : columnList) { // 循环要操作的字段
			for (Document document : dataList) { // 循环获得要操作的字段对应的数据
				if (paramType == 1) { // 转大写
					document.put(colum, document.get(colum).toString().toUpperCase());
				} else if (paramType == 2) { // 转小写
					document.put(colum, document.get(colum).toString().toLowerCase());
				} else if (paramType == 3) { // 首字母大写
					String s = document.get(colum).toString(); // 获得字段中的参数值
					if (Character.isUpperCase(s.charAt(0))) { // 判断首字母是否大写
						document.put(colum, document.get(colum).toString().toLowerCase());
					} else {
						document.put(colum, new StringBuilder().append(Character.toUpperCase(s.charAt(0)))
								.append(s.substring(1)).toString()); // 转换为大写字母存入document中
					}
				} else if (paramType == 4) { // 去除头尾空格
					document.put(colum, document.get(colum).toString().trim());
				} else if (paramType == 5) { // 保留前几个字符串
					Integer splitNum = operStep.getSplitNum(); // 获得要截取的个数
					document.put(colum, document.get(colum).toString().substring(0, splitNum));
				}
			}
		}
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}
}