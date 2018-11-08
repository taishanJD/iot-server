package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OperationLogExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    private static final long serialVersionUID = 1L;

    public OperationLogExample() {
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

        public Criteria andOperationTypeIsNull() {
            addCriterion("operation_type is null");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIsNotNull() {
            addCriterion("operation_type is not null");
            return (Criteria) this;
        }

        public Criteria andOperationTypeEqualTo(Integer value) {
            addCriterion("operation_type =", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotEqualTo(Integer value) {
            addCriterion("operation_type <>", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeGreaterThan(Integer value) {
            addCriterion("operation_type >", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("operation_type >=", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeLessThan(Integer value) {
            addCriterion("operation_type <", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeLessThanOrEqualTo(Integer value) {
            addCriterion("operation_type <=", value, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIn(List<Integer> values) {
            addCriterion("operation_type in", values, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotIn(List<Integer> values) {
            addCriterion("operation_type not in", values, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeBetween(Integer value1, Integer value2) {
            addCriterion("operation_type between", value1, value2, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("operation_type not between", value1, value2, "operationType");
            return (Criteria) this;
        }

        public Criteria andOperationUserIsNull() {
            addCriterion("operation_user is null");
            return (Criteria) this;
        }

        public Criteria andOperationUserIsNotNull() {
            addCriterion("operation_user is not null");
            return (Criteria) this;
        }

        public Criteria andOperationUserEqualTo(Long value) {
            addCriterion("operation_user =", value, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserNotEqualTo(Long value) {
            addCriterion("operation_user <>", value, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserGreaterThan(Long value) {
            addCriterion("operation_user >", value, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserGreaterThanOrEqualTo(Long value) {
            addCriterion("operation_user >=", value, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserLessThan(Long value) {
            addCriterion("operation_user <", value, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserLessThanOrEqualTo(Long value) {
            addCriterion("operation_user <=", value, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserIn(List<Long> values) {
            addCriterion("operation_user in", values, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserNotIn(List<Long> values) {
            addCriterion("operation_user not in", values, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserBetween(Long value1, Long value2) {
            addCriterion("operation_user between", value1, value2, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperationUserNotBetween(Long value1, Long value2) {
            addCriterion("operation_user not between", value1, value2, "operationUser");
            return (Criteria) this;
        }

        public Criteria andOperayionContentIsNull() {
            addCriterion("operayion_content is null");
            return (Criteria) this;
        }

        public Criteria andOperayionContentIsNotNull() {
            addCriterion("operayion_content is not null");
            return (Criteria) this;
        }

        public Criteria andOperayionContentEqualTo(Integer value) {
            addCriterion("operayion_content =", value, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentNotEqualTo(Integer value) {
            addCriterion("operayion_content <>", value, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentGreaterThan(Integer value) {
            addCriterion("operayion_content >", value, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentGreaterThanOrEqualTo(Integer value) {
            addCriterion("operayion_content >=", value, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentLessThan(Integer value) {
            addCriterion("operayion_content <", value, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentLessThanOrEqualTo(Integer value) {
            addCriterion("operayion_content <=", value, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentIn(List<Integer> values) {
            addCriterion("operayion_content in", values, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentNotIn(List<Integer> values) {
            addCriterion("operayion_content not in", values, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentBetween(Integer value1, Integer value2) {
            addCriterion("operayion_content between", value1, value2, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperayionContentNotBetween(Integer value1, Integer value2) {
            addCriterion("operayion_content not between", value1, value2, "operayionContent");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdIsNull() {
            addCriterion("operation_type_id is null");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdIsNotNull() {
            addCriterion("operation_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdEqualTo(Long value) {
            addCriterion("operation_type_id =", value, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdNotEqualTo(Long value) {
            addCriterion("operation_type_id <>", value, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdGreaterThan(Long value) {
            addCriterion("operation_type_id >", value, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("operation_type_id >=", value, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdLessThan(Long value) {
            addCriterion("operation_type_id <", value, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdLessThanOrEqualTo(Long value) {
            addCriterion("operation_type_id <=", value, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdIn(List<Long> values) {
            addCriterion("operation_type_id in", values, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdNotIn(List<Long> values) {
            addCriterion("operation_type_id not in", values, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdBetween(Long value1, Long value2) {
            addCriterion("operation_type_id between", value1, value2, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationTypeIdNotBetween(Long value1, Long value2) {
            addCriterion("operation_type_id not between", value1, value2, "operationTypeId");
            return (Criteria) this;
        }

        public Criteria andOperationDateIsNull() {
            addCriterion("operation_date is null");
            return (Criteria) this;
        }

        public Criteria andOperationDateIsNotNull() {
            addCriterion("operation_date is not null");
            return (Criteria) this;
        }

        public Criteria andOperationDateEqualTo(Date value) {
            addCriterion("operation_date =", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateNotEqualTo(Date value) {
            addCriterion("operation_date <>", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateGreaterThan(Date value) {
            addCriterion("operation_date >", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateGreaterThanOrEqualTo(Date value) {
            addCriterion("operation_date >=", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateLessThan(Date value) {
            addCriterion("operation_date <", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateLessThanOrEqualTo(Date value) {
            addCriterion("operation_date <=", value, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateIn(List<Date> values) {
            addCriterion("operation_date in", values, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateNotIn(List<Date> values) {
            addCriterion("operation_date not in", values, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateBetween(Date value1, Date value2) {
            addCriterion("operation_date between", value1, value2, "operationDate");
            return (Criteria) this;
        }

        public Criteria andOperationDateNotBetween(Date value1, Date value2) {
            addCriterion("operation_date not between", value1, value2, "operationDate");
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