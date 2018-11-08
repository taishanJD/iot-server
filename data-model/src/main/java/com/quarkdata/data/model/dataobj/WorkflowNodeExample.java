package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WorkflowNodeExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    private static final long serialVersionUID = 1L;

    public WorkflowNodeExample() {
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

        public Criteria andWorkflowIdIsNull() {
            addCriterion("workflow_id is null");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdIsNotNull() {
            addCriterion("workflow_id is not null");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdEqualTo(Long value) {
            addCriterion("workflow_id =", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotEqualTo(Long value) {
            addCriterion("workflow_id <>", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdGreaterThan(Long value) {
            addCriterion("workflow_id >", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdGreaterThanOrEqualTo(Long value) {
            addCriterion("workflow_id >=", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdLessThan(Long value) {
            addCriterion("workflow_id <", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdLessThanOrEqualTo(Long value) {
            addCriterion("workflow_id <=", value, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdIn(List<Long> values) {
            addCriterion("workflow_id in", values, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotIn(List<Long> values) {
            addCriterion("workflow_id not in", values, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdBetween(Long value1, Long value2) {
            addCriterion("workflow_id between", value1, value2, "workflowId");
            return (Criteria) this;
        }

        public Criteria andWorkflowIdNotBetween(Long value1, Long value2) {
            addCriterion("workflow_id not between", value1, value2, "workflowId");
            return (Criteria) this;
        }

        public Criteria andNodeNameIsNull() {
            addCriterion("node_name is null");
            return (Criteria) this;
        }

        public Criteria andNodeNameIsNotNull() {
            addCriterion("node_name is not null");
            return (Criteria) this;
        }

        public Criteria andNodeNameEqualTo(String value) {
            addCriterion("node_name =", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotEqualTo(String value) {
            addCriterion("node_name <>", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameGreaterThan(String value) {
            addCriterion("node_name >", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameGreaterThanOrEqualTo(String value) {
            addCriterion("node_name >=", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLessThan(String value) {
            addCriterion("node_name <", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLessThanOrEqualTo(String value) {
            addCriterion("node_name <=", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameLike(String value) {
            addCriterion("node_name like", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotLike(String value) {
            addCriterion("node_name not like", value, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameIn(List<String> values) {
            addCriterion("node_name in", values, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotIn(List<String> values) {
            addCriterion("node_name not in", values, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameBetween(String value1, String value2) {
            addCriterion("node_name between", value1, value2, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeNameNotBetween(String value1, String value2) {
            addCriterion("node_name not between", value1, value2, "nodeName");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIsNull() {
            addCriterion("node_type is null");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIsNotNull() {
            addCriterion("node_type is not null");
            return (Criteria) this;
        }

        public Criteria andNodeTypeEqualTo(String value) {
            addCriterion("node_type =", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeNotEqualTo(String value) {
            addCriterion("node_type <>", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeGreaterThan(String value) {
            addCriterion("node_type >", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("node_type >=", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeLessThan(String value) {
            addCriterion("node_type <", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeLessThanOrEqualTo(String value) {
            addCriterion("node_type <=", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeLike(String value) {
            addCriterion("node_type like", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeNotLike(String value) {
            addCriterion("node_type not like", value, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeIn(List<String> values) {
            addCriterion("node_type in", values, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeNotIn(List<String> values) {
            addCriterion("node_type not in", values, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeBetween(String value1, String value2) {
            addCriterion("node_type between", value1, value2, "nodeType");
            return (Criteria) this;
        }

        public Criteria andNodeTypeNotBetween(String value1, String value2) {
            addCriterion("node_type not between", value1, value2, "nodeType");
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

        public Criteria andDatasetTypeIsNull() {
            addCriterion("dataset_type is null");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeIsNotNull() {
            addCriterion("dataset_type is not null");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeEqualTo(String value) {
            addCriterion("dataset_type =", value, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeNotEqualTo(String value) {
            addCriterion("dataset_type <>", value, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeGreaterThan(String value) {
            addCriterion("dataset_type >", value, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeGreaterThanOrEqualTo(String value) {
            addCriterion("dataset_type >=", value, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeLessThan(String value) {
            addCriterion("dataset_type <", value, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeLessThanOrEqualTo(String value) {
            addCriterion("dataset_type <=", value, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeLike(String value) {
            addCriterion("dataset_type like", value, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeNotLike(String value) {
            addCriterion("dataset_type not like", value, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeIn(List<String> values) {
            addCriterion("dataset_type in", values, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeNotIn(List<String> values) {
            addCriterion("dataset_type not in", values, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeBetween(String value1, String value2) {
            addCriterion("dataset_type between", value1, value2, "datasetType");
            return (Criteria) this;
        }

        public Criteria andDatasetTypeNotBetween(String value1, String value2) {
            addCriterion("dataset_type not between", value1, value2, "datasetType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeIsNull() {
            addCriterion("node_process_type is null");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeIsNotNull() {
            addCriterion("node_process_type is not null");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeEqualTo(String value) {
            addCriterion("node_process_type =", value, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeNotEqualTo(String value) {
            addCriterion("node_process_type <>", value, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeGreaterThan(String value) {
            addCriterion("node_process_type >", value, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeGreaterThanOrEqualTo(String value) {
            addCriterion("node_process_type >=", value, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeLessThan(String value) {
            addCriterion("node_process_type <", value, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeLessThanOrEqualTo(String value) {
            addCriterion("node_process_type <=", value, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeLike(String value) {
            addCriterion("node_process_type like", value, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeNotLike(String value) {
            addCriterion("node_process_type not like", value, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeIn(List<String> values) {
            addCriterion("node_process_type in", values, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeNotIn(List<String> values) {
            addCriterion("node_process_type not in", values, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeBetween(String value1, String value2) {
            addCriterion("node_process_type between", value1, value2, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessTypeNotBetween(String value1, String value2) {
            addCriterion("node_process_type not between", value1, value2, "nodeProcessType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeIsNull() {
            addCriterion("node_process_sub_type is null");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeIsNotNull() {
            addCriterion("node_process_sub_type is not null");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeEqualTo(String value) {
            addCriterion("node_process_sub_type =", value, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeNotEqualTo(String value) {
            addCriterion("node_process_sub_type <>", value, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeGreaterThan(String value) {
            addCriterion("node_process_sub_type >", value, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeGreaterThanOrEqualTo(String value) {
            addCriterion("node_process_sub_type >=", value, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeLessThan(String value) {
            addCriterion("node_process_sub_type <", value, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeLessThanOrEqualTo(String value) {
            addCriterion("node_process_sub_type <=", value, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeLike(String value) {
            addCriterion("node_process_sub_type like", value, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeNotLike(String value) {
            addCriterion("node_process_sub_type not like", value, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeIn(List<String> values) {
            addCriterion("node_process_sub_type in", values, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeNotIn(List<String> values) {
            addCriterion("node_process_sub_type not in", values, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeBetween(String value1, String value2) {
            addCriterion("node_process_sub_type between", value1, value2, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andNodeProcessSubTypeNotBetween(String value1, String value2) {
            addCriterion("node_process_sub_type not between", value1, value2, "nodeProcessSubType");
            return (Criteria) this;
        }

        public Criteria andSqlStatementIsNull() {
            addCriterion("sql_statement is null");
            return (Criteria) this;
        }

        public Criteria andSqlStatementIsNotNull() {
            addCriterion("sql_statement is not null");
            return (Criteria) this;
        }

        public Criteria andSqlStatementEqualTo(String value) {
            addCriterion("sql_statement =", value, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementNotEqualTo(String value) {
            addCriterion("sql_statement <>", value, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementGreaterThan(String value) {
            addCriterion("sql_statement >", value, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementGreaterThanOrEqualTo(String value) {
            addCriterion("sql_statement >=", value, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementLessThan(String value) {
            addCriterion("sql_statement <", value, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementLessThanOrEqualTo(String value) {
            addCriterion("sql_statement <=", value, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementLike(String value) {
            addCriterion("sql_statement like", value, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementNotLike(String value) {
            addCriterion("sql_statement not like", value, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementIn(List<String> values) {
            addCriterion("sql_statement in", values, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementNotIn(List<String> values) {
            addCriterion("sql_statement not in", values, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementBetween(String value1, String value2) {
            addCriterion("sql_statement between", value1, value2, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andSqlStatementNotBetween(String value1, String value2) {
            addCriterion("sql_statement not between", value1, value2, "sqlStatement");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonIsNull() {
            addCriterion("preprocess_json is null");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonIsNotNull() {
            addCriterion("preprocess_json is not null");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonEqualTo(String value) {
            addCriterion("preprocess_json =", value, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonNotEqualTo(String value) {
            addCriterion("preprocess_json <>", value, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonGreaterThan(String value) {
            addCriterion("preprocess_json >", value, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonGreaterThanOrEqualTo(String value) {
            addCriterion("preprocess_json >=", value, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonLessThan(String value) {
            addCriterion("preprocess_json <", value, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonLessThanOrEqualTo(String value) {
            addCriterion("preprocess_json <=", value, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonLike(String value) {
            addCriterion("preprocess_json like", value, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonNotLike(String value) {
            addCriterion("preprocess_json not like", value, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonIn(List<String> values) {
            addCriterion("preprocess_json in", values, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonNotIn(List<String> values) {
            addCriterion("preprocess_json not in", values, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonBetween(String value1, String value2) {
            addCriterion("preprocess_json between", value1, value2, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andPreprocessJsonNotBetween(String value1, String value2) {
            addCriterion("preprocess_json not between", value1, value2, "preprocessJson");
            return (Criteria) this;
        }

        public Criteria andIsAppendIsNull() {
            addCriterion("is_append is null");
            return (Criteria) this;
        }

        public Criteria andIsAppendIsNotNull() {
            addCriterion("is_append is not null");
            return (Criteria) this;
        }

        public Criteria andIsAppendEqualTo(String value) {
            addCriterion("is_append =", value, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendNotEqualTo(String value) {
            addCriterion("is_append <>", value, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendGreaterThan(String value) {
            addCriterion("is_append >", value, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendGreaterThanOrEqualTo(String value) {
            addCriterion("is_append >=", value, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendLessThan(String value) {
            addCriterion("is_append <", value, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendLessThanOrEqualTo(String value) {
            addCriterion("is_append <=", value, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendLike(String value) {
            addCriterion("is_append like", value, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendNotLike(String value) {
            addCriterion("is_append not like", value, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendIn(List<String> values) {
            addCriterion("is_append in", values, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendNotIn(List<String> values) {
            addCriterion("is_append not in", values, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendBetween(String value1, String value2) {
            addCriterion("is_append between", value1, value2, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsAppendNotBetween(String value1, String value2) {
            addCriterion("is_append not between", value1, value2, "isAppend");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNull() {
            addCriterion("is_del is null");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNotNull() {
            addCriterion("is_del is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelEqualTo(String value) {
            addCriterion("is_del =", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotEqualTo(String value) {
            addCriterion("is_del <>", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThan(String value) {
            addCriterion("is_del >", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThanOrEqualTo(String value) {
            addCriterion("is_del >=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThan(String value) {
            addCriterion("is_del <", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThanOrEqualTo(String value) {
            addCriterion("is_del <=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLike(String value) {
            addCriterion("is_del like", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotLike(String value) {
            addCriterion("is_del not like", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelIn(List<String> values) {
            addCriterion("is_del in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotIn(List<String> values) {
            addCriterion("is_del not in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelBetween(String value1, String value2) {
            addCriterion("is_del between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotBetween(String value1, String value2) {
            addCriterion("is_del not between", value1, value2, "isDel");
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