package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SchedulerNotifyExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    private static final long serialVersionUID = 1L;

    public SchedulerNotifyExample() {
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

        public Criteria andSchedulerJobIdIsNull() {
            addCriterion("scheduler_job_id is null");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdIsNotNull() {
            addCriterion("scheduler_job_id is not null");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdEqualTo(Long value) {
            addCriterion("scheduler_job_id =", value, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdNotEqualTo(Long value) {
            addCriterion("scheduler_job_id <>", value, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdGreaterThan(Long value) {
            addCriterion("scheduler_job_id >", value, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdGreaterThanOrEqualTo(Long value) {
            addCriterion("scheduler_job_id >=", value, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdLessThan(Long value) {
            addCriterion("scheduler_job_id <", value, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdLessThanOrEqualTo(Long value) {
            addCriterion("scheduler_job_id <=", value, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdIn(List<Long> values) {
            addCriterion("scheduler_job_id in", values, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdNotIn(List<Long> values) {
            addCriterion("scheduler_job_id not in", values, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdBetween(Long value1, Long value2) {
            addCriterion("scheduler_job_id between", value1, value2, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andSchedulerJobIdNotBetween(Long value1, Long value2) {
            addCriterion("scheduler_job_id not between", value1, value2, "schedulerJobId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdIsNull() {
            addCriterion("receive_user_id is null");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdIsNotNull() {
            addCriterion("receive_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdEqualTo(Long value) {
            addCriterion("receive_user_id =", value, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdNotEqualTo(Long value) {
            addCriterion("receive_user_id <>", value, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdGreaterThan(Long value) {
            addCriterion("receive_user_id >", value, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("receive_user_id >=", value, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdLessThan(Long value) {
            addCriterion("receive_user_id <", value, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdLessThanOrEqualTo(Long value) {
            addCriterion("receive_user_id <=", value, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdIn(List<Long> values) {
            addCriterion("receive_user_id in", values, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdNotIn(List<Long> values) {
            addCriterion("receive_user_id not in", values, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdBetween(Long value1, Long value2) {
            addCriterion("receive_user_id between", value1, value2, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andReceiveUserIdNotBetween(Long value1, Long value2) {
            addCriterion("receive_user_id not between", value1, value2, "receiveUserId");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIsNull() {
            addCriterion("notice_type is null");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIsNotNull() {
            addCriterion("notice_type is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeEqualTo(String value) {
            addCriterion("notice_type =", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotEqualTo(String value) {
            addCriterion("notice_type <>", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeGreaterThan(String value) {
            addCriterion("notice_type >", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeGreaterThanOrEqualTo(String value) {
            addCriterion("notice_type >=", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLessThan(String value) {
            addCriterion("notice_type <", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLessThanOrEqualTo(String value) {
            addCriterion("notice_type <=", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeLike(String value) {
            addCriterion("notice_type like", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotLike(String value) {
            addCriterion("notice_type not like", value, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeIn(List<String> values) {
            addCriterion("notice_type in", values, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotIn(List<String> values) {
            addCriterion("notice_type not in", values, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeBetween(String value1, String value2) {
            addCriterion("notice_type between", value1, value2, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeTypeNotBetween(String value1, String value2) {
            addCriterion("notice_type not between", value1, value2, "noticeType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeIsNull() {
            addCriterion("notice_reason_type is null");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeIsNotNull() {
            addCriterion("notice_reason_type is not null");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeEqualTo(String value) {
            addCriterion("notice_reason_type =", value, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeNotEqualTo(String value) {
            addCriterion("notice_reason_type <>", value, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeGreaterThan(String value) {
            addCriterion("notice_reason_type >", value, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeGreaterThanOrEqualTo(String value) {
            addCriterion("notice_reason_type >=", value, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeLessThan(String value) {
            addCriterion("notice_reason_type <", value, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeLessThanOrEqualTo(String value) {
            addCriterion("notice_reason_type <=", value, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeLike(String value) {
            addCriterion("notice_reason_type like", value, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeNotLike(String value) {
            addCriterion("notice_reason_type not like", value, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeIn(List<String> values) {
            addCriterion("notice_reason_type in", values, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeNotIn(List<String> values) {
            addCriterion("notice_reason_type not in", values, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeBetween(String value1, String value2) {
            addCriterion("notice_reason_type between", value1, value2, "noticeReasonType");
            return (Criteria) this;
        }

        public Criteria andNoticeReasonTypeNotBetween(String value1, String value2) {
            addCriterion("notice_reason_type not between", value1, value2, "noticeReasonType");
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