package com.quarkdata.data.util.common.preprocess.factoryImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;
import com.quarkdata.data.util.common.preprocess.OperPreprocess;

/**
 * step207 连接（加字段）
 * 
 * isLightHight: 0,// 0 非高亮 1 高亮
 * 
 * switchStatus: 0, // 0 未打开 2 打开
 * 
 * joinSetId : 链接数据集ID
 * 
 * joinColumnName： 链接数据集字段
 * 
 * dataSetColumnList[] ：链接数据集使用的字段
 * 
 * newColumnPrefix：新字段前缀
 * 
 * @author WangHao 2018年7月6日
 */
public class OperPreprocess_207 extends OperPreprocess {

	@Override
	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		// 判断开关是否打开，如果开关未打开，则直接关闭
		if (operStep.getSwitchStatus() == 0) {
			return null;
		}
		// 获取链接数据集的字段和数据
		List<DatasetSchemaVO> joinSchemaList = operStep.getDataSetSchemaList();
		List<Document> joinDataList = operStep.getDataList();
		// 取出连接的表中使用的字段
		List<String> joinColumnList = operStep.getDataSetColumnList();
		// 将链接的表中的字段追加至本身的字段列表中
		for (DatasetSchemaVO joinSchemaVO : joinSchemaList) {
			for (String joinColumn : joinColumnList) {
				if (joinColumn.equals(joinSchemaVO.getColumnName())) {
					joinSchemaVO.setColumnName(operStep.getNewColumnPrefix() + "_" + joinSchemaVO.getColumnName());
					dataSetSchemaList.add(joinSchemaVO);
				}

			}
		}
		// 获取需要链接的字段
		String joinColumnName = operStep.getJoinColumnName();
		for (Document document : dataList) {
			for (Document joinDocument : joinDataList) {
				if (document.get(joinColumnName).toString().equals(joinDocument.get(joinColumnName).toString())) {
					for (String joinColumn : joinColumnList) {
						document.put(operStep.getNewColumnPrefix() + "_" + joinColumn, joinDocument.get(joinColumn));
					}
					break;
				} else {
					for (String joinColumn : joinColumnList) {
						document.put(operStep.getNewColumnPrefix() + "_" + joinColumn, null);
					}
				}
			}
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("dataSetSchemaList", dataSetSchemaList);
		resultMap.put("dataList", dataList);
		return resultMap;
	}

}
