package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatasetSchemaExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    private static final long serialVersionUID = 1L;

    public DatasetSchemaExample() {
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

        public Criteria andDatasetIdIsNull() {
            addCriterion("dataset_id is null");
            return (Criteria) this;
        }

        public Criteria andDatasetIdIsNotNull() {
            addCriterion("dataset_id is not null");
            return (Criteria) this;
        }

        public Criteria andDatasetIdEqualTo(Long value) {
            addCriterion("dataset_id =", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdNotEqualTo(Long value) {
            addCriterion("dataset_id <>", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdGreaterThan(Long value) {
            addCriterion("dataset_id >", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdGreaterThanOrEqualTo(Long value) {
            addCriterion("dataset_id >=", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdLessThan(Long value) {
            addCriterion("dataset_id <", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdLessThanOrEqualTo(Long value) {
            addCriterion("dataset_id <=", value, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdIn(List<Long> values) {
            addCriterion("dataset_id in", values, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdNotIn(List<Long> values) {
            addCriterion("dataset_id not in", values, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdBetween(Long value1, Long value2) {
            addCriterion("dataset_id between", value1, value2, "datasetId");
            return (Criteria) this;
        }

        public Criteria andDatasetIdNotBetween(Long value1, Long value2) {
            addCriterion("dataset_id not between", value1, value2, "datasetId");
            return (Criteria) this;
        }

        public Criteria andColumnNameIsNull() {
            addCriterion("column_name is null");
            return (Criteria) this;
        }

        public Criteria andColumnNameIsNotNull() {
            addCriterion("column_name is not null");
            return (Criteria) this;
        }

        public Criteria andColumnNameEqualTo(String value) {
            addCriterion("column_name =", value, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameNotEqualTo(String value) {
            addCriterion("column_name <>", value, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameGreaterThan(String value) {
            addCriterion("column_name >", value, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameGreaterThanOrEqualTo(String value) {
            addCriterion("column_name >=", value, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameLessThan(String value) {
            addCriterion("column_name <", value, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameLessThanOrEqualTo(String value) {
            addCriterion("column_name <=", value, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameLike(String value) {
            addCriterion("column_name like", value, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameNotLike(String value) {
            addCriterion("column_name not like", value, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameIn(List<String> values) {
            addCriterion("column_name in", values, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameNotIn(List<String> values) {
            addCriterion("column_name not in", values, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameBetween(String value1, String value2) {
            addCriterion("column_name between", value1, value2, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnNameNotBetween(String value1, String value2) {
            addCriterion("column_name not between", value1, value2, "columnName");
            return (Criteria) this;
        }

        public Criteria andColumnTypeIsNull() {
            addCriterion("column_type is null");
            return (Criteria) this;
        }

        public Criteria andColumnTypeIsNotNull() {
            addCriterion("column_type is not null");
            return (Criteria) this;
        }

        public Criteria andColumnTypeEqualTo(String value) {
            addCriterion("column_type =", value, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeNotEqualTo(String value) {
            addCriterion("column_type <>", value, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeGreaterThan(String value) {
            addCriterion("column_type >", value, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeGreaterThanOrEqualTo(String value) {
            addCriterion("column_type >=", value, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeLessThan(String value) {
            addCriterion("column_type <", value, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeLessThanOrEqualTo(String value) {
            addCriterion("column_type <=", value, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeLike(String value) {
            addCriterion("column_type like", value, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeNotLike(String value) {
            addCriterion("column_type not like", value, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeIn(List<String> values) {
            addCriterion("column_type in", values, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeNotIn(List<String> values) {
            addCriterion("column_type not in", values, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeBetween(String value1, String value2) {
            addCriterion("column_type between", value1, value2, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnTypeNotBetween(String value1, String value2) {
            addCriterion("column_type not between", value1, value2, "columnType");
            return (Criteria) this;
        }

        public Criteria andColumnLengthIsNull() {
            addCriterion("column_length is null");
            return (Criteria) this;
        }

        public Criteria andColumnLengthIsNotNull() {
            addCriterion("column_length is not null");
            return (Criteria) this;
        }

        public Criteria andColumnLengthEqualTo(Integer value) {
            addCriterion("column_length =", value, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthNotEqualTo(Integer value) {
            addCriterion("column_length <>", value, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthGreaterThan(Integer value) {
            addCriterion("column_length >", value, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthGreaterThanOrEqualTo(Integer value) {
            addCriterion("column_length >=", value, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthLessThan(Integer value) {
            addCriterion("column_length <", value, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthLessThanOrEqualTo(Integer value) {
            addCriterion("column_length <=", value, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthIn(List<Integer> values) {
            addCriterion("column_length in", values, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthNotIn(List<Integer> values) {
            addCriterion("column_length not in", values, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthBetween(Integer value1, Integer value2) {
            addCriterion("column_length between", value1, value2, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnLengthNotBetween(Integer value1, Integer value2) {
            addCriterion("column_length not between", value1, value2, "columnLength");
            return (Criteria) this;
        }

        public Criteria andColumnCommentIsNull() {
            addCriterion("column_comment is null");
            return (Criteria) this;
        }

        public Criteria andColumnCommentIsNotNull() {
            addCriterion("column_comment is not null");
            return (Criteria) this;
        }

        public Criteria andColumnCommentEqualTo(String value) {
            addCriterion("column_comment =", value, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentNotEqualTo(String value) {
            addCriterion("column_comment <>", value, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentGreaterThan(String value) {
            addCriterion("column_comment >", value, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentGreaterThanOrEqualTo(String value) {
            addCriterion("column_comment >=", value, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentLessThan(String value) {
            addCriterion("column_comment <", value, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentLessThanOrEqualTo(String value) {
            addCriterion("column_comment <=", value, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentLike(String value) {
            addCriterion("column_comment like", value, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentNotLike(String value) {
            addCriterion("column_comment not like", value, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentIn(List<String> values) {
            addCriterion("column_comment in", values, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentNotIn(List<String> values) {
            addCriterion("column_comment not in", values, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentBetween(String value1, String value2) {
            addCriterion("column_comment between", value1, value2, "columnComment");
            return (Criteria) this;
        }

        public Criteria andColumnCommentNotBetween(String value1, String value2) {
            addCriterion("column_comment not between", value1, value2, "columnComment");
            return (Criteria) this;
        }

        public Criteria andMeaningIsNull() {
            addCriterion("meaning is null");
            return (Criteria) this;
        }

        public Criteria andMeaningIsNotNull() {
            addCriterion("meaning is not null");
            return (Criteria) this;
        }

        public Criteria andMeaningEqualTo(String value) {
            addCriterion("meaning =", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotEqualTo(String value) {
            addCriterion("meaning <>", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningGreaterThan(String value) {
            addCriterion("meaning >", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningGreaterThanOrEqualTo(String value) {
            addCriterion("meaning >=", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLessThan(String value) {
            addCriterion("meaning <", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLessThanOrEqualTo(String value) {
            addCriterion("meaning <=", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningLike(String value) {
            addCriterion("meaning like", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotLike(String value) {
            addCriterion("meaning not like", value, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningIn(List<String> values) {
            addCriterion("meaning in", values, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotIn(List<String> values) {
            addCriterion("meaning not in", values, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningBetween(String value1, String value2) {
            addCriterion("meaning between", value1, value2, "meaning");
            return (Criteria) this;
        }

        public Criteria andMeaningNotBetween(String value1, String value2) {
            addCriterion("meaning not between", value1, value2, "meaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningIsNull() {
            addCriterion("sub_meaning is null");
            return (Criteria) this;
        }

        public Criteria andSubMeaningIsNotNull() {
            addCriterion("sub_meaning is not null");
            return (Criteria) this;
        }

        public Criteria andSubMeaningEqualTo(String value) {
            addCriterion("sub_meaning =", value, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningNotEqualTo(String value) {
            addCriterion("sub_meaning <>", value, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningGreaterThan(String value) {
            addCriterion("sub_meaning >", value, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningGreaterThanOrEqualTo(String value) {
            addCriterion("sub_meaning >=", value, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningLessThan(String value) {
            addCriterion("sub_meaning <", value, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningLessThanOrEqualTo(String value) {
            addCriterion("sub_meaning <=", value, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningLike(String value) {
            addCriterion("sub_meaning like", value, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningNotLike(String value) {
            addCriterion("sub_meaning not like", value, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningIn(List<String> values) {
            addCriterion("sub_meaning in", values, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningNotIn(List<String> values) {
            addCriterion("sub_meaning not in", values, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningBetween(String value1, String value2) {
            addCriterion("sub_meaning between", value1, value2, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andSubMeaningNotBetween(String value1, String value2) {
            addCriterion("sub_meaning not between", value1, value2, "subMeaning");
            return (Criteria) this;
        }

        public Criteria andValidProportionIsNull() {
            addCriterion("valid_proportion is null");
            return (Criteria) this;
        }

        public Criteria andValidProportionIsNotNull() {
            addCriterion("valid_proportion is not null");
            return (Criteria) this;
        }

        public Criteria andValidProportionEqualTo(Float value) {
            addCriterion("valid_proportion =", value, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionNotEqualTo(Float value) {
            addCriterion("valid_proportion <>", value, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionGreaterThan(Float value) {
            addCriterion("valid_proportion >", value, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionGreaterThanOrEqualTo(Float value) {
            addCriterion("valid_proportion >=", value, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionLessThan(Float value) {
            addCriterion("valid_proportion <", value, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionLessThanOrEqualTo(Float value) {
            addCriterion("valid_proportion <=", value, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionIn(List<Float> values) {
            addCriterion("valid_proportion in", values, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionNotIn(List<Float> values) {
            addCriterion("valid_proportion not in", values, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionBetween(Float value1, Float value2) {
            addCriterion("valid_proportion between", value1, value2, "validProportion");
            return (Criteria) this;
        }

        public Criteria andValidProportionNotBetween(Float value1, Float value2) {
            addCriterion("valid_proportion not between", value1, value2, "validProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionIsNull() {
            addCriterion("invalid_proportion is null");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionIsNotNull() {
            addCriterion("invalid_proportion is not null");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionEqualTo(Float value) {
            addCriterion("invalid_proportion =", value, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionNotEqualTo(Float value) {
            addCriterion("invalid_proportion <>", value, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionGreaterThan(Float value) {
            addCriterion("invalid_proportion >", value, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionGreaterThanOrEqualTo(Float value) {
            addCriterion("invalid_proportion >=", value, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionLessThan(Float value) {
            addCriterion("invalid_proportion <", value, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionLessThanOrEqualTo(Float value) {
            addCriterion("invalid_proportion <=", value, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionIn(List<Float> values) {
            addCriterion("invalid_proportion in", values, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionNotIn(List<Float> values) {
            addCriterion("invalid_proportion not in", values, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionBetween(Float value1, Float value2) {
            addCriterion("invalid_proportion between", value1, value2, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andInvalidProportionNotBetween(Float value1, Float value2) {
            addCriterion("invalid_proportion not between", value1, value2, "invalidProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionIsNull() {
            addCriterion("null_proportion is null");
            return (Criteria) this;
        }

        public Criteria andNullProportionIsNotNull() {
            addCriterion("null_proportion is not null");
            return (Criteria) this;
        }

        public Criteria andNullProportionEqualTo(Float value) {
            addCriterion("null_proportion =", value, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionNotEqualTo(Float value) {
            addCriterion("null_proportion <>", value, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionGreaterThan(Float value) {
            addCriterion("null_proportion >", value, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionGreaterThanOrEqualTo(Float value) {
            addCriterion("null_proportion >=", value, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionLessThan(Float value) {
            addCriterion("null_proportion <", value, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionLessThanOrEqualTo(Float value) {
            addCriterion("null_proportion <=", value, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionIn(List<Float> values) {
            addCriterion("null_proportion in", values, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionNotIn(List<Float> values) {
            addCriterion("null_proportion not in", values, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionBetween(Float value1, Float value2) {
            addCriterion("null_proportion between", value1, value2, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNullProportionNotBetween(Float value1, Float value2) {
            addCriterion("null_proportion not between", value1, value2, "nullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionIsNull() {
            addCriterion("not_null_proportion is null");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionIsNotNull() {
            addCriterion("not_null_proportion is not null");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionEqualTo(Float value) {
            addCriterion("not_null_proportion =", value, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionNotEqualTo(Float value) {
            addCriterion("not_null_proportion <>", value, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionGreaterThan(Float value) {
            addCriterion("not_null_proportion >", value, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionGreaterThanOrEqualTo(Float value) {
            addCriterion("not_null_proportion >=", value, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionLessThan(Float value) {
            addCriterion("not_null_proportion <", value, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionLessThanOrEqualTo(Float value) {
            addCriterion("not_null_proportion <=", value, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionIn(List<Float> values) {
            addCriterion("not_null_proportion in", values, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionNotIn(List<Float> values) {
            addCriterion("not_null_proportion not in", values, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionBetween(Float value1, Float value2) {
            addCriterion("not_null_proportion between", value1, value2, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andNotNullProportionNotBetween(Float value1, Float value2) {
            addCriterion("not_null_proportion not between", value1, value2, "notNullProportion");
            return (Criteria) this;
        }

        public Criteria andMaxValueIsNull() {
            addCriterion("max_value is null");
            return (Criteria) this;
        }

        public Criteria andMaxValueIsNotNull() {
            addCriterion("max_value is not null");
            return (Criteria) this;
        }

        public Criteria andMaxValueEqualTo(String value) {
            addCriterion("max_value =", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotEqualTo(String value) {
            addCriterion("max_value <>", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueGreaterThan(String value) {
            addCriterion("max_value >", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueGreaterThanOrEqualTo(String value) {
            addCriterion("max_value >=", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueLessThan(String value) {
            addCriterion("max_value <", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueLessThanOrEqualTo(String value) {
            addCriterion("max_value <=", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueLike(String value) {
            addCriterion("max_value like", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotLike(String value) {
            addCriterion("max_value not like", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueIn(List<String> values) {
            addCriterion("max_value in", values, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotIn(List<String> values) {
            addCriterion("max_value not in", values, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueBetween(String value1, String value2) {
            addCriterion("max_value between", value1, value2, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotBetween(String value1, String value2) {
            addCriterion("max_value not between", value1, value2, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMinValueIsNull() {
            addCriterion("min_value is null");
            return (Criteria) this;
        }

        public Criteria andMinValueIsNotNull() {
            addCriterion("min_value is not null");
            return (Criteria) this;
        }

        public Criteria andMinValueEqualTo(String value) {
            addCriterion("min_value =", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotEqualTo(String value) {
            addCriterion("min_value <>", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueGreaterThan(String value) {
            addCriterion("min_value >", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueGreaterThanOrEqualTo(String value) {
            addCriterion("min_value >=", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueLessThan(String value) {
            addCriterion("min_value <", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueLessThanOrEqualTo(String value) {
            addCriterion("min_value <=", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueLike(String value) {
            addCriterion("min_value like", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotLike(String value) {
            addCriterion("min_value not like", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueIn(List<String> values) {
            addCriterion("min_value in", values, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotIn(List<String> values) {
            addCriterion("min_value not in", values, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueBetween(String value1, String value2) {
            addCriterion("min_value between", value1, value2, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotBetween(String value1, String value2) {
            addCriterion("min_value not between", value1, value2, "minValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueIsNull() {
            addCriterion("avg_value is null");
            return (Criteria) this;
        }

        public Criteria andAvgValueIsNotNull() {
            addCriterion("avg_value is not null");
            return (Criteria) this;
        }

        public Criteria andAvgValueEqualTo(String value) {
            addCriterion("avg_value =", value, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueNotEqualTo(String value) {
            addCriterion("avg_value <>", value, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueGreaterThan(String value) {
            addCriterion("avg_value >", value, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueGreaterThanOrEqualTo(String value) {
            addCriterion("avg_value >=", value, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueLessThan(String value) {
            addCriterion("avg_value <", value, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueLessThanOrEqualTo(String value) {
            addCriterion("avg_value <=", value, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueLike(String value) {
            addCriterion("avg_value like", value, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueNotLike(String value) {
            addCriterion("avg_value not like", value, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueIn(List<String> values) {
            addCriterion("avg_value in", values, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueNotIn(List<String> values) {
            addCriterion("avg_value not in", values, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueBetween(String value1, String value2) {
            addCriterion("avg_value between", value1, value2, "avgValue");
            return (Criteria) this;
        }

        public Criteria andAvgValueNotBetween(String value1, String value2) {
            addCriterion("avg_value not between", value1, value2, "avgValue");
            return (Criteria) this;
        }

        public Criteria andMaxLengthIsNull() {
            addCriterion("max_length is null");
            return (Criteria) this;
        }

        public Criteria andMaxLengthIsNotNull() {
            addCriterion("max_length is not null");
            return (Criteria) this;
        }

        public Criteria andMaxLengthEqualTo(String value) {
            addCriterion("max_length =", value, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthNotEqualTo(String value) {
            addCriterion("max_length <>", value, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthGreaterThan(String value) {
            addCriterion("max_length >", value, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthGreaterThanOrEqualTo(String value) {
            addCriterion("max_length >=", value, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthLessThan(String value) {
            addCriterion("max_length <", value, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthLessThanOrEqualTo(String value) {
            addCriterion("max_length <=", value, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthLike(String value) {
            addCriterion("max_length like", value, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthNotLike(String value) {
            addCriterion("max_length not like", value, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthIn(List<String> values) {
            addCriterion("max_length in", values, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthNotIn(List<String> values) {
            addCriterion("max_length not in", values, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthBetween(String value1, String value2) {
            addCriterion("max_length between", value1, value2, "maxLength");
            return (Criteria) this;
        }

        public Criteria andMaxLengthNotBetween(String value1, String value2) {
            addCriterion("max_length not between", value1, value2, "maxLength");
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