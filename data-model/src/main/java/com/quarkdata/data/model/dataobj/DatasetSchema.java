package com.quarkdata.data.model.dataobj;

import java.io.Serializable;

public class DatasetSchema implements Serializable {
    private Long id;

    private Long datasetId;

    private String columnName;

    private String columnType;

    private Integer columnLength;

    private String columnComment;

    private String meaning;

    private String subMeaning;

    private Float validProportion;

    private Float invalidProportion;

    private Float nullProportion;

    private Float notNullProportion;

    private String maxValue;

    private String minValue;

    private String avgValue;

    private String maxLength;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDatasetId() {
        return datasetId;
    }

    public void setDatasetId(Long datasetId) {
        this.datasetId = datasetId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType == null ? null : columnType.trim();
    }

    public Integer getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(Integer columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment == null ? null : columnComment.trim();
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning == null ? null : meaning.trim();
    }

    public String getSubMeaning() {
        return subMeaning;
    }

    public void setSubMeaning(String subMeaning) {
        this.subMeaning = subMeaning == null ? null : subMeaning.trim();
    }

    public Float getValidProportion() {
        return validProportion;
    }

    public void setValidProportion(Float validProportion) {
        this.validProportion = validProportion;
    }

    public Float getInvalidProportion() {
        return invalidProportion;
    }

    public void setInvalidProportion(Float invalidProportion) {
        this.invalidProportion = invalidProportion;
    }

    public Float getNullProportion() {
        return nullProportion;
    }

    public void setNullProportion(Float nullProportion) {
        this.nullProportion = nullProportion;
    }

    public Float getNotNullProportion() {
        return notNullProportion;
    }

    public void setNotNullProportion(Float notNullProportion) {
        this.notNullProportion = notNullProportion;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue == null ? null : maxValue.trim();
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue == null ? null : minValue.trim();
    }

    public String getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(String avgValue) {
        this.avgValue = avgValue == null ? null : avgValue.trim();
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength == null ? null : maxLength.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        DatasetSchema other = (DatasetSchema) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDatasetId() == null ? other.getDatasetId() == null : this.getDatasetId().equals(other.getDatasetId()))
            && (this.getColumnName() == null ? other.getColumnName() == null : this.getColumnName().equals(other.getColumnName()))
            && (this.getColumnType() == null ? other.getColumnType() == null : this.getColumnType().equals(other.getColumnType()))
            && (this.getColumnLength() == null ? other.getColumnLength() == null : this.getColumnLength().equals(other.getColumnLength()))
            && (this.getColumnComment() == null ? other.getColumnComment() == null : this.getColumnComment().equals(other.getColumnComment()))
            && (this.getMeaning() == null ? other.getMeaning() == null : this.getMeaning().equals(other.getMeaning()))
            && (this.getSubMeaning() == null ? other.getSubMeaning() == null : this.getSubMeaning().equals(other.getSubMeaning()))
            && (this.getValidProportion() == null ? other.getValidProportion() == null : this.getValidProportion().equals(other.getValidProportion()))
            && (this.getInvalidProportion() == null ? other.getInvalidProportion() == null : this.getInvalidProportion().equals(other.getInvalidProportion()))
            && (this.getNullProportion() == null ? other.getNullProportion() == null : this.getNullProportion().equals(other.getNullProportion()))
            && (this.getNotNullProportion() == null ? other.getNotNullProportion() == null : this.getNotNullProportion().equals(other.getNotNullProportion()))
            && (this.getMaxValue() == null ? other.getMaxValue() == null : this.getMaxValue().equals(other.getMaxValue()))
            && (this.getMinValue() == null ? other.getMinValue() == null : this.getMinValue().equals(other.getMinValue()))
            && (this.getAvgValue() == null ? other.getAvgValue() == null : this.getAvgValue().equals(other.getAvgValue()))
            && (this.getMaxLength() == null ? other.getMaxLength() == null : this.getMaxLength().equals(other.getMaxLength()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDatasetId() == null) ? 0 : getDatasetId().hashCode());
        result = prime * result + ((getColumnName() == null) ? 0 : getColumnName().hashCode());
        result = prime * result + ((getColumnType() == null) ? 0 : getColumnType().hashCode());
        result = prime * result + ((getColumnLength() == null) ? 0 : getColumnLength().hashCode());
        result = prime * result + ((getColumnComment() == null) ? 0 : getColumnComment().hashCode());
        result = prime * result + ((getMeaning() == null) ? 0 : getMeaning().hashCode());
        result = prime * result + ((getSubMeaning() == null) ? 0 : getSubMeaning().hashCode());
        result = prime * result + ((getValidProportion() == null) ? 0 : getValidProportion().hashCode());
        result = prime * result + ((getInvalidProportion() == null) ? 0 : getInvalidProportion().hashCode());
        result = prime * result + ((getNullProportion() == null) ? 0 : getNullProportion().hashCode());
        result = prime * result + ((getNotNullProportion() == null) ? 0 : getNotNullProportion().hashCode());
        result = prime * result + ((getMaxValue() == null) ? 0 : getMaxValue().hashCode());
        result = prime * result + ((getMinValue() == null) ? 0 : getMinValue().hashCode());
        result = prime * result + ((getAvgValue() == null) ? 0 : getAvgValue().hashCode());
        result = prime * result + ((getMaxLength() == null) ? 0 : getMaxLength().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", datasetId=").append(datasetId);
        sb.append(", columnName=").append(columnName);
        sb.append(", columnType=").append(columnType);
        sb.append(", columnLength=").append(columnLength);
        sb.append(", columnComment=").append(columnComment);
        sb.append(", meaning=").append(meaning);
        sb.append(", subMeaning=").append(subMeaning);
        sb.append(", validProportion=").append(validProportion);
        sb.append(", invalidProportion=").append(invalidProportion);
        sb.append(", nullProportion=").append(nullProportion);
        sb.append(", notNullProportion=").append(notNullProportion);
        sb.append(", maxValue=").append(maxValue);
        sb.append(", minValue=").append(minValue);
        sb.append(", avgValue=").append(avgValue);
        sb.append(", maxLength=").append(maxLength);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}