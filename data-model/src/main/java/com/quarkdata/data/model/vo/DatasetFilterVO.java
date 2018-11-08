package com.quarkdata.data.model.vo;

import com.quarkdata.data.model.dataobj.DatasetFilter;
import com.quarkdata.data.model.dataobj.DatasetSchema;

public class DatasetFilterVO extends DatasetFilter{

    private Column column;

    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }

    public void setAllPropoty(DatasetFilter datasetFilter){
        this.setId(datasetFilter.getId());
        this.setColumnName(datasetFilter.getColumnName());
        this.setDatasetId(datasetFilter.getDatasetId());
        this.setOperator(datasetFilter.getOperator());
        this.setValue1(datasetFilter.getValue1());
        this.setValue2(datasetFilter.getValue2());
    }

    public class Column {

        private String name;
        private String type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
