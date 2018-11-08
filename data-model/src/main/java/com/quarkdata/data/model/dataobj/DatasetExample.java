package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatasetExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    private static final long serialVersionUID = 1L;

    public DatasetExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria implements Serializable {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIsNull() {
            addCriterion("datasource_id is null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIsNotNull() {
            addCriterion("datasource_id is not null");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdEqualTo(Long value) {
            addCriterion("datasource_id =", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotEqualTo(Long value) {
            addCriterion("datasource_id <>", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdGreaterThan(Long value) {
            addCriterion("datasource_id >", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("datasource_id >=", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdLessThan(Long value) {
            addCriterion("datasource_id <", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdLessThanOrEqualTo(Long value) {
            addCriterion("datasource_id <=", value, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdIn(List<Long> values) {
            addCriterion("datasource_id in", values, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotIn(List<Long> values) {
            addCriterion("datasource_id not in", values, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdBetween(Long value1, Long value2) {
            addCriterion("datasource_id between", value1, value2, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andDatasourceIdNotBetween(Long value1, Long value2) {
            addCriterion("datasource_id not between", value1, value2, "datasourceId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(Long value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(Long value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(Long value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(Long value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(Long value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(Long value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<Long> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<Long> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(Long value1, Long value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(Long value1, Long value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andDatasetNameIsNull() {
            addCriterion("dataset_name is null");
            return (Criteria) this;
        }

        public Criteria andDatasetNameIsNotNull() {
            addCriterion("dataset_name is not null");
            return (Criteria) this;
        }

        public Criteria andDatasetNameEqualTo(String value) {
            addCriterion("dataset_name =", value, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameNotEqualTo(String value) {
            addCriterion("dataset_name <>", value, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameGreaterThan(String value) {
            addCriterion("dataset_name >", value, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameGreaterThanOrEqualTo(String value) {
            addCriterion("dataset_name >=", value, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameLessThan(String value) {
            addCriterion("dataset_name <", value, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameLessThanOrEqualTo(String value) {
            addCriterion("dataset_name <=", value, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameLike(String value) {
            addCriterion("dataset_name like", value, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameNotLike(String value) {
            addCriterion("dataset_name not like", value, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameIn(List<String> values) {
            addCriterion("dataset_name in", values, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameNotIn(List<String> values) {
            addCriterion("dataset_name not in", values, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameBetween(String value1, String value2) {
            addCriterion("dataset_name between", value1, value2, "datasetName");
            return (Criteria) this;
        }

        public Criteria andDatasetNameNotBetween(String value1, String value2) {
            addCriterion("dataset_name not between", value1, value2, "datasetName");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNull() {
            addCriterion("table_name is null");
            return (Criteria) this;
        }

        public Criteria andTableNameIsNotNull() {
            addCriterion("table_name is not null");
            return (Criteria) this;
        }

        public Criteria andTableNameEqualTo(String value) {
            addCriterion("table_name =", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotEqualTo(String value) {
            addCriterion("table_name <>", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThan(String value) {
            addCriterion("table_name >", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameGreaterThanOrEqualTo(String value) {
            addCriterion("table_name >=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThan(String value) {
            addCriterion("table_name <", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLessThanOrEqualTo(String value) {
            addCriterion("table_name <=", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameLike(String value) {
            addCriterion("table_name like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotLike(String value) {
            addCriterion("table_name not like", value, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameIn(List<String> values) {
            addCriterion("table_name in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotIn(List<String> values) {
            addCriterion("table_name not in", values, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameBetween(String value1, String value2) {
            addCriterion("table_name between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andTableNameNotBetween(String value1, String value2) {
            addCriterion("table_name not between", value1, value2, "tableName");
            return (Criteria) this;
        }

        public Criteria andIsWriteIsNull() {
            addCriterion("is_write is null");
            return (Criteria) this;
        }

        public Criteria andIsWriteIsNotNull() {
            addCriterion("is_write is not null");
            return (Criteria) this;
        }

        public Criteria andIsWriteEqualTo(String value) {
            addCriterion("is_write =", value, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteNotEqualTo(String value) {
            addCriterion("is_write <>", value, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteGreaterThan(String value) {
            addCriterion("is_write >", value, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteGreaterThanOrEqualTo(String value) {
            addCriterion("is_write >=", value, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteLessThan(String value) {
            addCriterion("is_write <", value, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteLessThanOrEqualTo(String value) {
            addCriterion("is_write <=", value, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteLike(String value) {
            addCriterion("is_write like", value, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteNotLike(String value) {
            addCriterion("is_write not like", value, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteIn(List<String> values) {
            addCriterion("is_write in", values, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteNotIn(List<String> values) {
            addCriterion("is_write not in", values, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteBetween(String value1, String value2) {
            addCriterion("is_write between", value1, value2, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsWriteNotBetween(String value1, String value2) {
            addCriterion("is_write not between", value1, value2, "isWrite");
            return (Criteria) this;
        }

        public Criteria andIsSyncIsNull() {
            addCriterion("is_sync is null");
            return (Criteria) this;
        }

        public Criteria andIsSyncIsNotNull() {
            addCriterion("is_sync is not null");
            return (Criteria) this;
        }

        public Criteria andIsSyncEqualTo(String value) {
            addCriterion("is_sync =", value, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncNotEqualTo(String value) {
            addCriterion("is_sync <>", value, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncGreaterThan(String value) {
            addCriterion("is_sync >", value, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncGreaterThanOrEqualTo(String value) {
            addCriterion("is_sync >=", value, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncLessThan(String value) {
            addCriterion("is_sync <", value, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncLessThanOrEqualTo(String value) {
            addCriterion("is_sync <=", value, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncLike(String value) {
            addCriterion("is_sync like", value, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncNotLike(String value) {
            addCriterion("is_sync not like", value, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncIn(List<String> values) {
            addCriterion("is_sync in", values, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncNotIn(List<String> values) {
            addCriterion("is_sync not in", values, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncBetween(String value1, String value2) {
            addCriterion("is_sync between", value1, value2, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsSyncNotBetween(String value1, String value2) {
            addCriterion("is_sync not between", value1, value2, "isSync");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntIsNull() {
            addCriterion("is_float_to_int is null");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntIsNotNull() {
            addCriterion("is_float_to_int is not null");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntEqualTo(String value) {
            addCriterion("is_float_to_int =", value, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntNotEqualTo(String value) {
            addCriterion("is_float_to_int <>", value, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntGreaterThan(String value) {
            addCriterion("is_float_to_int >", value, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntGreaterThanOrEqualTo(String value) {
            addCriterion("is_float_to_int >=", value, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntLessThan(String value) {
            addCriterion("is_float_to_int <", value, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntLessThanOrEqualTo(String value) {
            addCriterion("is_float_to_int <=", value, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntLike(String value) {
            addCriterion("is_float_to_int like", value, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntNotLike(String value) {
            addCriterion("is_float_to_int not like", value, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntIn(List<String> values) {
            addCriterion("is_float_to_int in", values, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntNotIn(List<String> values) {
            addCriterion("is_float_to_int not in", values, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntBetween(String value1, String value2) {
            addCriterion("is_float_to_int between", value1, value2, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andIsFloatToIntNotBetween(String value1, String value2) {
            addCriterion("is_float_to_int not between", value1, value2, "isFloatToInt");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIsNull() {
            addCriterion("sample_type is null");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIsNotNull() {
            addCriterion("sample_type is not null");
            return (Criteria) this;
        }

        public Criteria andSampleTypeEqualTo(String value) {
            addCriterion("sample_type =", value, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNotEqualTo(String value) {
            addCriterion("sample_type <>", value, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeGreaterThan(String value) {
            addCriterion("sample_type >", value, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeGreaterThanOrEqualTo(String value) {
            addCriterion("sample_type >=", value, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeLessThan(String value) {
            addCriterion("sample_type <", value, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeLessThanOrEqualTo(String value) {
            addCriterion("sample_type <=", value, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeLike(String value) {
            addCriterion("sample_type like", value, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNotLike(String value) {
            addCriterion("sample_type not like", value, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeIn(List<String> values) {
            addCriterion("sample_type in", values, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNotIn(List<String> values) {
            addCriterion("sample_type not in", values, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeBetween(String value1, String value2) {
            addCriterion("sample_type between", value1, value2, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeNotBetween(String value1, String value2) {
            addCriterion("sample_type not between", value1, value2, "sampleType");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueIsNull() {
            addCriterion("sample_type_value is null");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueIsNotNull() {
            addCriterion("sample_type_value is not null");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueEqualTo(Integer value) {
            addCriterion("sample_type_value =", value, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueNotEqualTo(Integer value) {
            addCriterion("sample_type_value <>", value, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueGreaterThan(Integer value) {
            addCriterion("sample_type_value >", value, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueGreaterThanOrEqualTo(Integer value) {
            addCriterion("sample_type_value >=", value, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueLessThan(Integer value) {
            addCriterion("sample_type_value <", value, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueLessThanOrEqualTo(Integer value) {
            addCriterion("sample_type_value <=", value, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueIn(List<Integer> values) {
            addCriterion("sample_type_value in", values, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueNotIn(List<Integer> values) {
            addCriterion("sample_type_value not in", values, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueBetween(Integer value1, Integer value2) {
            addCriterion("sample_type_value between", value1, value2, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andSampleTypeValueNotBetween(Integer value1, Integer value2) {
            addCriterion("sample_type_value not between", value1, value2, "sampleTypeValue");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterIsNull() {
            addCriterion("is_sample_filter is null");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterIsNotNull() {
            addCriterion("is_sample_filter is not null");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterEqualTo(String value) {
            addCriterion("is_sample_filter =", value, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterNotEqualTo(String value) {
            addCriterion("is_sample_filter <>", value, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterGreaterThan(String value) {
            addCriterion("is_sample_filter >", value, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterGreaterThanOrEqualTo(String value) {
            addCriterion("is_sample_filter >=", value, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterLessThan(String value) {
            addCriterion("is_sample_filter <", value, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterLessThanOrEqualTo(String value) {
            addCriterion("is_sample_filter <=", value, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterLike(String value) {
            addCriterion("is_sample_filter like", value, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterNotLike(String value) {
            addCriterion("is_sample_filter not like", value, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterIn(List<String> values) {
            addCriterion("is_sample_filter in", values, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterNotIn(List<String> values) {
            addCriterion("is_sample_filter not in", values, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterBetween(String value1, String value2) {
            addCriterion("is_sample_filter between", value1, value2, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andIsSampleFilterNotBetween(String value1, String value2) {
            addCriterion("is_sample_filter not between", value1, value2, "isSampleFilter");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeIsNull() {
            addCriterion("sample_filter_type is null");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeIsNotNull() {
            addCriterion("sample_filter_type is not null");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeEqualTo(String value) {
            addCriterion("sample_filter_type =", value, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeNotEqualTo(String value) {
            addCriterion("sample_filter_type <>", value, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeGreaterThan(String value) {
            addCriterion("sample_filter_type >", value, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeGreaterThanOrEqualTo(String value) {
            addCriterion("sample_filter_type >=", value, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeLessThan(String value) {
            addCriterion("sample_filter_type <", value, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeLessThanOrEqualTo(String value) {
            addCriterion("sample_filter_type <=", value, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeLike(String value) {
            addCriterion("sample_filter_type like", value, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeNotLike(String value) {
            addCriterion("sample_filter_type not like", value, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeIn(List<String> values) {
            addCriterion("sample_filter_type in", values, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeNotIn(List<String> values) {
            addCriterion("sample_filter_type not in", values, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeBetween(String value1, String value2) {
            addCriterion("sample_filter_type between", value1, value2, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andSampleFilterTypeNotBetween(String value1, String value2) {
            addCriterion("sample_filter_type not between", value1, value2, "sampleFilterType");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(Long value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(Long value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(Long value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(Long value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(Long value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<Long> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<Long> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(Long value1, Long value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(Long value1, Long value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(Long value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(Long value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(Long value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(Long value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(Long value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(Long value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<Long> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<Long> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(Long value1, Long value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(Long value1, Long value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIsNull() {
            addCriterion("sync_time is null");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIsNotNull() {
            addCriterion("sync_time is not null");
            return (Criteria) this;
        }

        public Criteria andSyncTimeEqualTo(Date value) {
            addCriterion("sync_time =", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotEqualTo(Date value) {
            addCriterion("sync_time <>", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeGreaterThan(Date value) {
            addCriterion("sync_time >", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sync_time >=", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLessThan(Date value) {
            addCriterion("sync_time <", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeLessThanOrEqualTo(Date value) {
            addCriterion("sync_time <=", value, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeIn(List<Date> values) {
            addCriterion("sync_time in", values, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotIn(List<Date> values) {
            addCriterion("sync_time not in", values, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeBetween(Date value1, Date value2) {
            addCriterion("sync_time between", value1, value2, "syncTime");
            return (Criteria) this;
        }

        public Criteria andSyncTimeNotBetween(Date value1, Date value2) {
            addCriterion("sync_time not between", value1, value2, "syncTime");
            return (Criteria) this;
        }

        public Criteria andDataCountIsNull() {
            addCriterion("data_count is null");
            return (Criteria) this;
        }

        public Criteria andDataCountIsNotNull() {
            addCriterion("data_count is not null");
            return (Criteria) this;
        }

        public Criteria andDataCountEqualTo(Long value) {
            addCriterion("data_count =", value, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountNotEqualTo(Long value) {
            addCriterion("data_count <>", value, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountGreaterThan(Long value) {
            addCriterion("data_count >", value, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountGreaterThanOrEqualTo(Long value) {
            addCriterion("data_count >=", value, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountLessThan(Long value) {
            addCriterion("data_count <", value, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountLessThanOrEqualTo(Long value) {
            addCriterion("data_count <=", value, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountIn(List<Long> values) {
            addCriterion("data_count in", values, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountNotIn(List<Long> values) {
            addCriterion("data_count not in", values, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountBetween(Long value1, Long value2) {
            addCriterion("data_count between", value1, value2, "dataCount");
            return (Criteria) this;
        }

        public Criteria andDataCountNotBetween(Long value1, Long value2) {
            addCriterion("data_count not between", value1, value2, "dataCount");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria implements Serializable {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion implements Serializable {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}