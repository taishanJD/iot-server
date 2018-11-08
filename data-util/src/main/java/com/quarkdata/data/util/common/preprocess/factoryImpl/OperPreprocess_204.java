package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.alibaba.fastjson.JSONArray;
import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step 204 根据分隔符分割字段
 * <p>
 * useColumn 要操作的字段
 * <p>
 * isLightHight: 0,// 0 非高亮 1 高亮
 * <p>
 * switchStatus: 0, // 0 未打开 2 打开
 * <p>
 * splitStr ： 要分割的符号
 * <p>
 * newColumnName：新的字段名
 * <p>
 * newColumnType :新字段类型 0 多字段 1 Json
 *
 * @author guoning 2018年7月6日
 */
public class OperPreprocess_204 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int max = 0; // 加一个max，用来存储分割后最长的那个量
		int flag = 0; // 加一个标志位
		// 获得要分割的字符串
		String useColumn = operStep.getUseColumn();
		// 获得分割的符号
		String splitStr = operStep.getSplitStr();
		// 获得新字段的类型
		Integer newColumnType = operStep.getNewColumnType(); // 1 多字段 2 json
		// 获得新字段的名字
		String newColumnName = operStep.getNewColumnName();

		for (Document document : dataList) {
			// 先做滤空的判断，看否包含此要分割的字符串，再要判断此要分割的字符串是否在最前面或者最后面
			if (document.get(useColumn).toString().contains(splitStr)) {
				int splitSize = splitStr.length();
				int useColumnSize = document.get(useColumn).toString().length();
				if (document.get(useColumn).toString().substring(0, splitSize).equals(splitStr)
						|| document.get(useColumn).toString().substring(useColumnSize - splitSize, useColumnSize)
								.equals(splitStr)) {
					// 符合前后相同的就被替换为空串
					document.get(useColumn).toString().replace(splitStr, "");
				}
				String[] split = document.get(useColumn).toString().trim().split(splitStr);
				int reSplitLenth = split.length;
				for (int i = 0; i < split.length; i++) {
					if (split[i].equals("")) {
						reSplitLenth -= 1;
					}
				}
				// 查找除分割后最长的哪一个
				if (reSplitLenth > max) {
					max = reSplitLenth;
				}
			} else {
			}
		}
		// 再循环一次
		for (Document document : dataList) {
			if (document.get(useColumn).toString().contains(splitStr)) {
				String[] split = document.get(useColumn).toString().split(splitStr);
				if (newColumnType == 1) {
					// 多字段
					if (flag == 0) {
						for (int i = 0; i < max; i++) {
							DatasetSchemaVO schemaVO = new DatasetSchemaVO();
							if (max == 1) {
								schemaVO.setColumnName(newColumnName);
							} else {
								schemaVO.setColumnName(newColumnName + i);
							}
							addDatasetSchemaVO(schemaVO, dataSetSchemaList);
						}
						flag = 1;
					}
					for (int i = 0; i < split.length; i++) {
						if (!split[i].equals("") && split[i] != null) {
							if (max == 1) {
								document.put(newColumnName, split[i]);
							} else {
								document.put(newColumnName + i, split[i]);
							}
						}
					} // json
				} else if (newColumnType == 2) {
					// 添加json
					if (flag == 0) {
						DatasetSchemaVO schemaVO = new DatasetSchemaVO();
						schemaVO.setColumnName(newColumnName);
						// 代码提取
						addDatasetSchemaVO(schemaVO, dataSetSchemaList);
						flag = 1;
					}
					Object json = JSONArray.toJSON(split);
					document.put(newColumnName, json.toString());
				}
			}

		}
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}

	// 添加schemaVO
	private void addDatasetSchemaVO(DatasetSchemaVO schemaVO, List<DatasetSchemaVO> dataSetSchemaList) {
		schemaVO.setColumnType("7");
		schemaVO.setInvalidProportion(0F);
		schemaVO.setMeaning("1");
		String[] meanList = new String[2];
		meanList[0] = "1";
		schemaVO.setMeanings(meanList);
		dataSetSchemaList.add(schemaVO);
	}
}
