package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step108 分组
 * 
 * stepType: 是否统计每组个数 1：统计 2：不统计
 * 
 * useColumnList： 参与分组的字段
 * 
 * polymerList：聚合字段列表 {column：字段、polymer：聚合类型}
 * 
 * isLightHight: 0,// 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 2 打开
 * 
 * @author WangHao 2018年7月10日
 */
public class OperPreprocess_108 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		// 判断开关是否打开，如果开关未打开，则直接关闭
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}

		// 聚合参数字段
		List<Map<String, String>> polymerList = operStep.getPolymerList();
		// 分组使用的字段
		List<String> useColumnList = operStep.getUseColumnList();
		// 修改最终的字段列表
		Iterator<DatasetSchemaVO> schemaIterator = dataSetSchemaList.iterator();
		// 循环删除不参与分组的字段，保留分组字段及新增聚合字段
		while (schemaIterator.hasNext()) {
			DatasetSchemaVO datasetSchemaVO = (DatasetSchemaVO) schemaIterator.next();
			boolean delFlag = false;
			for (String useColumn : useColumnList) {
				if (datasetSchemaVO.getColumnName().equals(useColumn)) {
					delFlag = true;
				}
			}
			if (!delFlag) {
				schemaIterator.remove();
			}
		}
		// 循环聚合列表 追加字段至字段列表
		for (Map<String, String> polymerMap : polymerList) {
			String column = polymerMap.get("column");
			String polymer = polymerMap.get("polymer");
			DatasetSchemaVO datasetSchemaVO = new DatasetSchemaVO();
			datasetSchemaVO.setColumnName(polymer + "_" + column);
			// 参与聚合 count为int其他的为浮点
			if (polymer.equals("count")) {
				datasetSchemaVO.setColumnType("2");
				datasetSchemaVO.setInvalidProportion(100f);
				datasetSchemaVO.setMeaning("3");
				String[] meanList = new String[2];
				meanList[0] = "3";
				datasetSchemaVO.setMeanings(meanList);
			} else {
				datasetSchemaVO.setColumnType("4");
				datasetSchemaVO.setInvalidProportion(100f);
				datasetSchemaVO.setMeaning("2");
				String[] meanList = new String[2];
				meanList[0] = "2";
				datasetSchemaVO.setMeanings(meanList);
			}
			dataSetSchemaList.add(datasetSchemaVO);
		}
		// 判断是否统计每组个数
		if (operStep.getStepType() == 1) {
			DatasetSchemaVO datasetSchemaVO = new DatasetSchemaVO();
			datasetSchemaVO.setColumnName("groupCount");
			datasetSchemaVO.setColumnType("2");
			datasetSchemaVO.setInvalidProportion(100f);
			datasetSchemaVO.setMeaning("3");
			String[] meanList = new String[2];
			meanList[0] = "3";
			datasetSchemaVO.setMeanings(meanList);
			dataSetSchemaList.add(datasetSchemaVO);
		}
		// 对数据进行分组
		List<Document> groupDocumentList = operGroupBy(operStep, dataList);
		// 对需要聚合的参数进行聚合
		for (Document groupDocument : groupDocumentList) {
			groupDocument = operPolymer(groupDocument, dataList, operStep);
		}
		// 对数据进行统计
		if (operStep.getStepType() == 1) {
			for (Document groupDocument : groupDocumentList) {
				groupDocument = countGroupDocument(groupDocument, dataList, operStep);
			}

		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", groupDocumentList);
		return resultMap;
	}

	/**
	 * 统计每组个数
	 */
	private Document countGroupDocument(Document groupDocument, List<Document> dataList, Preprocess operStep) {
		int count = 0;
		for (Document document : dataList) {
			if (checkDocumentSame(document, groupDocument, operStep.getUseColumnList())) {
				count++;
			}
		}
		groupDocument.put("groupCount", count);
		return groupDocument;
	}

	/**
	 * 对数据进行聚合
	 */
	private Document operPolymer(Document groupDocument, List<Document> dataList, Preprocess operStep) {
		List<Map<String, String>> polymerList = operStep.getPolymerList();
		for (Map<String, String> polymerMap : polymerList) {
			String column = polymerMap.get("column");
			String polymer = polymerMap.get("polymer");
			// 聚合函数操作 min max avg sum count
			switch (polymer) {
			case "min":
				float minValue = Float.parseFloat(dataList.get(0).get(column).toString());
				for (Document document : dataList) {
					if (checkDocumentSame(document, groupDocument, operStep.getUseColumnList())) {
						if (Float.parseFloat(document.get(column).toString()) < minValue) {
							minValue = Float.parseFloat(document.get(column).toString());
						}
					}
				}
				groupDocument.put(polymer + "_" + column, minValue);
				break;
			case "max":
				float maxValue = Float.parseFloat(dataList.get(0).get(column).toString());
				for (Document document : dataList) {
					if (checkDocumentSame(document, groupDocument, operStep.getUseColumnList())) {
						if (Float.parseFloat(document.get(column).toString()) > maxValue) {
							minValue = Float.parseFloat(document.get(column).toString());
						}
					}
				}
				groupDocument.put(polymer + "_" + column, maxValue);
				break;
			case "avg":
				float sumValue = 0f;
				int avgCount = 0;
				for (Document document : dataList) {
					if (checkDocumentSame(document, groupDocument, operStep.getUseColumnList())) {
						sumValue += Float.parseFloat(document.get(column).toString());
						avgCount++;
					}
				}
				groupDocument.put(polymer + "_" + column, sumValue / avgCount);
				break;
			case "sum":
				float _sumValue = 0f;
				for (Document document : dataList) {
					if (checkDocumentSame(document, groupDocument, operStep.getUseColumnList())) {
						_sumValue += Float.parseFloat(document.get(column).toString());
					}
				}
				groupDocument.put(polymer + "_" + column, _sumValue);
				break;
			case "count":
				int count = 0;
				for (Document document : dataList) {
					if (checkDocumentSame(document, groupDocument, operStep.getUseColumnList())) {
						if (document.get(column) != null) {
							count++;
						}
					}
				}
				groupDocument.put(polymer + "_" + column, count);
				break;
			default:
				break;
			}
		}
		return groupDocument;
	}

	/**
	 * 对数据进行分组
	 */
	private List<Document> operGroupBy(Preprocess operStep, List<Document> dataList) {
		// 分组后的列表
		List<Document> groupDocumentList = new ArrayList<Document>();
		// 参与分组的字段
		List<String> useColumnList = operStep.getUseColumnList();
		// 默认第一条参与分组
		groupDocumentList.add(dataList.get(0));
		// 循环数据列表，将每个对象放入分组后的列表内个中匹配，如果参与分组的字段都不一样，则将该对象追加至列表
		for (Document document : dataList) {
			boolean addFlag = true;
			for (Document groupDocument : groupDocumentList) {
				boolean checkDocumentSame = checkDocumentSame(document, groupDocument, useColumnList);
				// 如果有两个对象分组属性完全一致的 则说明已经进行了分组，则不处理该对象
				if (checkDocumentSame) {
					addFlag = false;
					break;
				}
			}
			if (addFlag) {
				groupDocumentList.add(document);
			}
		}
		return groupDocumentList;
	}

	/**
	 * 判断两个对象中的分组属性的值是否完全一致
	 */
	private boolean checkDocumentSame(Document document, Document groupDocument, List<String> useColumnList) {
		boolean sameFlag = true;
		for (String useColumn : useColumnList) {
			if (document.get(useColumn) != null && groupDocument.get(useColumn) != null) {
				if (!document.get(useColumn).toString().equals(groupDocument.get(useColumn).toString())) {
					sameFlag = false;
				}
			}
		}
		return sameFlag;
	}

}
