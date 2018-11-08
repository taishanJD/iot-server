package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.quarkdata.data.model.vo.DatasetSchemaVO;

/**
 * 预处理页面处理步骤Model
 * 
 * @author WangHao 2018年7月2日
 */
public class Preprocess implements Serializable {

	private static final long serialVersionUID = 4076892577676470016L;
	
	// 预处理ID
	private Integer id;
	// 处理类型 1:过滤数据 2：数据清洗
	private Integer typeId;
	// 操作类型 根据不同处理步骤 value不同
	private Integer stepType;
	// 操作名称
	private String stepName;
	// 操作描述
	private String stepMsg;
	// 操作字段/最后操作字段
	private String useColumn;
	// 操作字段列表
	private List<String> useColumnList;
	// 是否高亮显示 0 非高亮 1 高亮
	private Integer isLightHight;
	// 步骤开关 0 未打开 2 打开
	private Integer switchStatus;
	// 参数类型 0 单个参数 1 多个参数 2 所有参数
	private Integer paramType;
	// ID:102 匹配方式 完全匹配 0 包含 1
	private Integer matchType;
	// ID:102 是否区分大小写 0 否 1 是
	private Integer matchCase;
	// 步骤参数列表
	private List<String> operParamList;
	// ID:103 数值范围 大于 0 小于 1
	private Integer paramRange;
	// ID:104 步骤参数时间
	private String operTime;
	// ID:105 语义
	private Integer semantics;
	// ID:107 新字段
	private String newColumnName;
	// ID 204 新字段类型 1 多字段 2 JSON
	private Integer newColumnType;
	// ID 204 分割所用的字符串
	private String  splitStr;
	// ID:107 统计参数
	private String operParam;
	// ID:107 正则表达式
	private String regular;
	// ID:108 polymerList: [] map： column、polymer
	private List<Map<String, String>> polymerList;
	// ID:203 polymerList: [] map： findParam、replaceParam
	private List<Map<String, String>> paramList;
	// ID:203 查询类别 1 完全匹配 2 子字符串 3 正则表达式
	private Integer findType;
	// ID:205 保留个数 当stepType = 5
	private Integer splitNum;
	// ID:206 日期标准
	private String dateFormat;
	// ID:207 链接数据集字段列表
	private List<String> dataSetColumnList;
	// ID:207 链接的数据集ID
	private Long joinSetId;
	// ID:207 被连接字段
	private String joinColumnName;
	// ID:207 被链接字段列表
	List<DatasetSchemaVO> dataSetSchemaList;
	// ID:207 被链接数据列表
	List<Document> dataList;
	// ID:207 字段前缀
	private String newColumnPrefix;
	// ID:208 经度
	private String longitude;
	// ID:208 纬度
	private String latitude;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public Integer getStepType() {
		return stepType;
	}
	public void setStepType(Integer stepType) {
		this.stepType = stepType;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public String getStepMsg() {
		return stepMsg;
	}
	public void setStepMsg(String stepMsg) {
		this.stepMsg = stepMsg;
	}
	public String getUseColumn() {
		return useColumn;
	}
	public void setUseColumn(String useColumn) {
		this.useColumn = useColumn;
	}
	public List<String> getUseColumnList() {
		return useColumnList;
	}
	public void setUseColumnList(List<String> useColumnList) {
		this.useColumnList = useColumnList;
	}
	public Integer getIsLightHight() {
		return isLightHight;
	}
	public void setIsLightHight(Integer isLightHight) {
		this.isLightHight = isLightHight;
	}
	public Integer getSwitchStatus() {
		return switchStatus;
	}
	public void setSwitchStatus(Integer switchStatus) {
		this.switchStatus = switchStatus;
	}
	public Integer getParamType() {
		return paramType;
	}
	public void setParamType(Integer paramType) {
		this.paramType = paramType;
	}
	public Integer getMatchType() {
		return matchType;
	}
	public void setMatchType(Integer matchType) {
		this.matchType = matchType;
	}
	public Integer getMatchCase() {
		return matchCase;
	}
	public void setMatchCase(Integer matchCase) {
		this.matchCase = matchCase;
	}
	public List<String> getOperParamList() {
		return operParamList;
	}
	public void setOperParamList(List<String> operParamList) {
		this.operParamList = operParamList;
	}
	public Integer getParamRange() {
		return paramRange;
	}
	public void setParamRange(Integer paramRange) {
		this.paramRange = paramRange;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	public Integer getSemantics() {
		return semantics;
	}
	public void setSemantics(Integer semantics) {
		this.semantics = semantics;
	}
	public String getNewColumnName() {
		return newColumnName;
	}
	public void setNewColumnName(String newColumnName) {
		this.newColumnName = newColumnName;
	}
	public Integer getNewColumnType() {
		return newColumnType;
	}
	public void setNewColumnType(Integer newColumnType) {
		this.newColumnType = newColumnType;
	}
	public String getOperParam() {
		return operParam;
	}
	public void setOperParam(String operParam) {
		this.operParam = operParam;
	}
	public String getRegular() {
		return regular;
	}
	public void setRegular(String regular) {
		this.regular = regular;
	}
	public List<Map<String, String>> getPolymerList() {
		return polymerList;
	}
	public void setPolymerList(List<Map<String, String>> polymerList) {
		this.polymerList = polymerList;
	}
	public List<Map<String, String>> getParamList() {
		return paramList;
	}
	public void setParamList(List<Map<String, String>> paramList) {
		this.paramList = paramList;
	}
	public Integer getFindType() {
		return findType;
	}
	public void setFindType(Integer findType) {
		this.findType = findType;
	}
	public Integer getSplitNum() {
		return splitNum;
	}
	public void setSplitNum(Integer splitNum) {
		this.splitNum = splitNum;
	}
	public String getDateFormat() {
		return dateFormat;
	}
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}
	public List<String> getDataSetColumnList() {
		return dataSetColumnList;
	}
	public void setDataSetColumnList(List<String> dataSetColumnList) {
		this.dataSetColumnList = dataSetColumnList;
	}
	public Long getJoinSetId() {
		return joinSetId;
	}
	public void setJoinSetId(Long joinSetId) {
		this.joinSetId = joinSetId;
	}
	public String getJoinColumnName() {
		return joinColumnName;
	}
	public void setJoinColumnName(String joinColumnName) {
		this.joinColumnName = joinColumnName;
	}
	public String getNewColumnPrefix() {
		return newColumnPrefix;
	}
	public void setNewColumnPrefix(String newColumnPrefix) {
		this.newColumnPrefix = newColumnPrefix;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setSplitStr(String splitStr) {
		this.splitStr = splitStr;
	}
	public String getSplitStr() {
		return splitStr;
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

}
