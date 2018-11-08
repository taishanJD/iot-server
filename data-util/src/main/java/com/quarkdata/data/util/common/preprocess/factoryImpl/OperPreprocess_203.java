package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.bson.Document;
import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step 203 查找和替换
 * <p>
 * useColumnList 要操作的字段
 * <p>
 * isLightHight: 0,// 0 非高亮 1 高亮
 * <p>
 * switchStatus: 0, // 0 未打开 2 打开
 * <p>
 * paramList ： 要查找和替换的值
 * <p>
 * findType：查询的类别
 *
 * @author guoning 2018年7月6日
 */
public class OperPreprocess_203 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}

		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获得查找的类型
		Integer findType = operStep.getFindType();
		// 获得要查找替换字段名
		List<String> useColumnList = operStep.getUseColumnList();
		// 获得要替换的值的列表
		List<Map<String, String>> paramList = operStep.getParamList();
		for (String useColumn : useColumnList) { // 循环要替换的字段名
			for (Map<String, String> param : paramList) { // 循环替换值的列表
				for (Document document : dataList) { // 循环要替换的值
					if (findType == 1) { // 完全匹配
						if (document.get(useColumn).equals(param.get("findParam"))) { // Document中需要查找替换的字段名获得的值与需要查找的完全相同
							document.put(useColumn, param.get("replaceParam")); // 将需要查找替换的字段名作为key，存入value
						}
					} else if (findType == 2) { // 子字符串
						if (document.get(useColumn).toString().contains(param.get("findParam"))) {
							document.put(useColumn, document.get(useColumn).toString().replace(param.get("findParam"),
									param.get("replaceParam")));
						}
					} else if (findType == 3) { // 正则表达式匹配
						String regular = param.get("findParam");
						if (Pattern.compile(jsToJavaRegular(regular)).matcher(document.get(useColumn).toString().trim())
								.find()) {
							document.put(useColumn, param.get("replaceParam"));
						}
					}
				}
			}
		}
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}

	// 将js的正则转换成java能够使用的正则
	private String jsToJavaRegular(String Regular) {
		if (Regular.contains("\\\\")) {
			return Regular.replace("\\\\", "\\");
		} else {
			return Regular;
		}
	}
}
