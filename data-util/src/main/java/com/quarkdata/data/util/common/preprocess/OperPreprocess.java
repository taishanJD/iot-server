package com.quarkdata.data.util.common.preprocess;

import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.dataobj.Preprocess;
import com.quarkdata.data.model.vo.DatasetSchemaVO;

public abstract class OperPreprocess {

	// 当前操作步骤
	private Preprocess operStep;

	// 当前列表字段List
	private List<DatasetSchemaVO> dataSetSchemaList;

	// 当前数据对象List
	private List<Document> dataList;

	public Preprocess getOperStep() {
		return operStep;
	}

	public void setOperStep(Preprocess operStep) {
		this.operStep = operStep;
	}

	public List<DatasetSchemaVO> getDataSetSchemaList() {
		return dataSetSchemaList;
	}

	public void setDataSetSchemaList(List<DatasetSchemaVO> dataSetSchemaList) {
		this.dataSetSchemaList = dataSetSchemaList;
	}

	public List<Document> getDataList() {
		return dataList;
	}

	public void setDataList(List<Document> dataList) {
		this.dataList = dataList;
	}

	public Map<String, Object> goPreprocessStep(Preprocess operStep, List<DatasetSchemaVO> dataSetSchemaList,
			List<Document> dataList) {
		this.operStep = operStep;
		this.dataSetSchemaList = dataSetSchemaList;
		this.dataList = dataList;
		return null;
	}
}
