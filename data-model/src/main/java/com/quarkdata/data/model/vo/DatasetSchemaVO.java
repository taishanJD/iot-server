package com.quarkdata.data.model.vo;

import com.quarkdata.data.model.dataobj.DatasetSchema;

import java.io.Serializable;

public class DatasetSchemaVO extends DatasetSchema implements Serializable {

    private String[] meanings;

    public String[] getMeanings() {
        return meanings;
    }

    public void setMeanings(String[] meanings) {
        this.meanings = meanings;
    }

    public void setAllPropoty(DatasetSchema datasetSchema){
        this.setId(datasetSchema.getId());
        this.setMeaning(datasetSchema.getMeaning());
        this.setSubMeaning(datasetSchema.getSubMeaning());
        this.setColumnComment(datasetSchema.getColumnComment());
        this.setColumnLength(datasetSchema.getColumnLength());
        this.setColumnName(datasetSchema.getColumnName());
        this.setColumnType(datasetSchema.getColumnType());
        this.setDatasetId(datasetSchema.getDatasetId());
        this.setValidProportion(datasetSchema.getValidProportion());
        this.setInvalidProportion(datasetSchema.getInvalidProportion());
        this.setNullProportion(datasetSchema.getNullProportion());
        this.setNotNullProportion(datasetSchema.getNotNullProportion());
        this.setMaxValue(datasetSchema.getMaxValue());
        this.setMinValue(datasetSchema.getMinValue());
        this.setAvgValue(datasetSchema.getAvgValue());
        this.setMaxLength(datasetSchema.getMaxLength());

        if (null != datasetSchema.getSubMeaning()){
            this.meanings = new String[2];
            this.meanings[0] = datasetSchema.getMeaning();
            this.meanings[1] = datasetSchema.getSubMeaning();
        }else {
            this.meanings = new String[1];
            this.meanings[0] = datasetSchema.getMeaning();
        }
    }
}
