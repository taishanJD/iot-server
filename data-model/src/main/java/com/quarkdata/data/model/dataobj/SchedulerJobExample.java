package com.quarkdata.data.model.dataobj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchedulerJobExample implements Serializable {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    private static final long serialVersionUID = 1L;

    public SchedulerJobExample() {
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

        public Criteria andTenantIdIsNull() {
            addCriterion("tenant_id is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("tenant_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(Long value) {
            addCriterion("tenant_id =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(Long value) {
            addCriterion("tenant_id <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(Long value) {
            addCriterion("tenant_id >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tenant_id >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(Long value) {
            addCriterion("tenant_id <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(Long value) {
            addCriterion("tenant_id <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<Long> values) {
            addCriterion("tenant_id in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<Long> values) {
            addCriterion("tenant_id not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(Long value1, Long value2) {
            addCriterion("tenant_id between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(Long value1, Long value2) {
            addCriterion("tenant_id not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andJobNameIsNull() {
            addCriterion("job_name is null");
            return (Criteria) this;
        }

        public Criteria andJobNameIsNotNull() {
            addCriterion("job_name is not null");
            return (Criteria) this;
        }

        public Criteria andJobNameEqualTo(String value) {
            addCriterion("job_name =", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotEqualTo(String value) {
            addCriterion("job_name <>", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameGreaterThan(String value) {
            addCriterion("job_name >", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameGreaterThanOrEqualTo(String value) {
            addCriterion("job_name >=", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLessThan(String value) {
            addCriterion("job_name <", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLessThanOrEqualTo(String value) {
            addCriterion("job_name <=", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLike(String value) {
            addCriterion("job_name like", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotLike(String value) {
            addCriterion("job_name not like", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameIn(List<String> values) {
            addCriterion("job_name in", values, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotIn(List<String> values) {
            addCriterion("job_name not in", values, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameBetween(String value1, String value2) {
            addCriterion("job_name between", value1, value2, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotBetween(String value1, String value2) {
            addCriterion("job_name not between", value1, value2, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNull() {
            addCriterion("job_type is null");
            return (Criteria) this;
        }

        public Criteria andJobTypeIsNotNull() {
            addCriterion("job_type is not null");
            return (Criteria) this;
        }

        public Criteria andJobTypeEqualTo(String value) {
            addCriterion("job_type =", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotEqualTo(String value) {
            addCriterion("job_type <>", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThan(String value) {
            addCriterion("job_type >", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeGreaterThanOrEqualTo(String value) {
            addCriterion("job_type >=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThan(String value) {
            addCriterion("job_type <", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLessThanOrEqualTo(String value) {
            addCriterion("job_type <=", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeLike(String value) {
            addCriterion("job_type like", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotLike(String value) {
            addCriterion("job_type not like", value, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeIn(List<String> values) {
            addCriterion("job_type in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotIn(List<String> values) {
            addCriterion("job_type not in", values, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeBetween(String value1, String value2) {
            addCriterion("job_type between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobTypeNotBetween(String value1, String value2) {
            addCriterion("job_type not between", value1, value2, "jobType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeIsNull() {
            addCriterion("job_biz_type is null");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeIsNotNull() {
            addCriterion("job_biz_type is not null");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeEqualTo(String value) {
            addCriterion("job_biz_type =", value, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeNotEqualTo(String value) {
            addCriterion("job_biz_type <>", value, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeGreaterThan(String value) {
            addCriterion("job_biz_type >", value, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeGreaterThanOrEqualTo(String value) {
            addCriterion("job_biz_type >=", value, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeLessThan(String value) {
            addCriterion("job_biz_type <", value, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeLessThanOrEqualTo(String value) {
            addCriterion("job_biz_type <=", value, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeLike(String value) {
            addCriterion("job_biz_type like", value, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeNotLike(String value) {
            addCriterion("job_biz_type not like", value, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeIn(List<String> values) {
            addCriterion("job_biz_type in", values, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeNotIn(List<String> values) {
            addCriterion("job_biz_type not in", values, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeBetween(String value1, String value2) {
            addCriterion("job_biz_type between", value1, value2, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizTypeNotBetween(String value1, String value2) {
            addCriterion("job_biz_type not between", value1, value2, "jobBizType");
            return (Criteria) this;
        }

        public Criteria andJobBizIdIsNull() {
            addCriterion("job_biz_id is null");
            return (Criteria) this;
        }

        public Criteria andJobBizIdIsNotNull() {
            addCriterion("job_biz_id is not null");
            return (Criteria) this;
        }

        public Criteria andJobBizIdEqualTo(Long value) {
            addCriterion("job_biz_id =", value, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdNotEqualTo(Long value) {
            addCriterion("job_biz_id <>", value, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdGreaterThan(Long value) {
            addCriterion("job_biz_id >", value, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdGreaterThanOrEqualTo(Long value) {
            addCriterion("job_biz_id >=", value, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdLessThan(Long value) {
            addCriterion("job_biz_id <", value, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdLessThanOrEqualTo(Long value) {
            addCriterion("job_biz_id <=", value, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdIn(List<Long> values) {
            addCriterion("job_biz_id in", values, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdNotIn(List<Long> values) {
            addCriterion("job_biz_id not in", values, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdBetween(Long value1, Long value2) {
            addCriterion("job_biz_id between", value1, value2, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andJobBizIdNotBetween(Long value1, Long value2) {
            addCriterion("job_biz_id not between", value1, value2, "jobBizId");
            return (Criteria) this;
        }

        public Criteria andIsFrozenIsNull() {
            addCriterion("is_frozen is null");
            return (Criteria) this;
        }

        public Criteria andIsFrozenIsNotNull() {
            addCriterion("is_frozen is not null");
            return (Criteria) this;
        }

        public Criteria andIsFrozenEqualTo(String value) {
            addCriterion("is_frozen =", value, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenNotEqualTo(String value) {
            addCriterion("is_frozen <>", value, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenGreaterThan(String value) {
            addCriterion("is_frozen >", value, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenGreaterThanOrEqualTo(String value) {
            addCriterion("is_frozen >=", value, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenLessThan(String value) {
            addCriterion("is_frozen <", value, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenLessThanOrEqualTo(String value) {
            addCriterion("is_frozen <=", value, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenLike(String value) {
            addCriterion("is_frozen like", value, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenNotLike(String value) {
            addCriterion("is_frozen not like", value, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenIn(List<String> values) {
            addCriterion("is_frozen in", values, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenNotIn(List<String> values) {
            addCriterion("is_frozen not in", values, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenBetween(String value1, String value2) {
            addCriterion("is_frozen between", value1, value2, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsFrozenNotBetween(String value1, String value2) {
            addCriterion("is_frozen not between", value1, value2, "isFrozen");
            return (Criteria) this;
        }

        public Criteria andIsNotifyIsNull() {
            addCriterion("is_notify is null");
            return (Criteria) this;
        }

        public Criteria andIsNotifyIsNotNull() {
            addCriterion("is_notify is not null");
            return (Criteria) this;
        }

        public Criteria andIsNotifyEqualTo(String value) {
            addCriterion("is_notify =", value, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyNotEqualTo(String value) {
            addCriterion("is_notify <>", value, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyGreaterThan(String value) {
            addCriterion("is_notify >", value, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyGreaterThanOrEqualTo(String value) {
            addCriterion("is_notify >=", value, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyLessThan(String value) {
            addCriterion("is_notify <", value, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyLessThanOrEqualTo(String value) {
            addCriterion("is_notify <=", value, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyLike(String value) {
            addCriterion("is_notify like", value, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyNotLike(String value) {
            addCriterion("is_notify not like", value, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyIn(List<String> values) {
            addCriterion("is_notify in", values, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyNotIn(List<String> values) {
            addCriterion("is_notify not in", values, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyBetween(String value1, String value2) {
            addCriterion("is_notify between", value1, value2, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsNotifyNotBetween(String value1, String value2) {
            addCriterion("is_notify not between", value1, value2, "isNotify");
            return (Criteria) this;
        }

        public Criteria andIsPublishIsNull() {
            addCriterion("is_publish is null");
            return (Criteria) this;
        }

        public Criteria andIsPublishIsNotNull() {
            addCriterion("is_publish is not null");
            return (Criteria) this;
        }

        public Criteria andIsPublishEqualTo(String value) {
            addCriterion("is_publish =", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishNotEqualTo(String value) {
            addCriterion("is_publish <>", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishGreaterThan(String value) {
            addCriterion("is_publish >", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishGreaterThanOrEqualTo(String value) {
            addCriterion("is_publish >=", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishLessThan(String value) {
            addCriterion("is_publish <", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishLessThanOrEqualTo(String value) {
            addCriterion("is_publish <=", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishLike(String value) {
            addCriterion("is_publish like", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishNotLike(String value) {
            addCriterion("is_publish not like", value, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishIn(List<String> values) {
            addCriterion("is_publish in", values, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishNotIn(List<String> values) {
            addCriterion("is_publish not in", values, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishBetween(String value1, String value2) {
            addCriterion("is_publish between", value1, value2, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIsPublishNotBetween(String value1, String value2) {
            addCriterion("is_publish not between", value1, value2, "isPublish");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeIsNull() {
            addCriterion("interval_type is null");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeIsNotNull() {
            addCriterion("interval_type is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeEqualTo(String value) {
            addCriterion("interval_type =", value, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeNotEqualTo(String value) {
            addCriterion("interval_type <>", value, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeGreaterThan(String value) {
            addCriterion("interval_type >", value, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeGreaterThanOrEqualTo(String value) {
            addCriterion("interval_type >=", value, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeLessThan(String value) {
            addCriterion("interval_type <", value, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeLessThanOrEqualTo(String value) {
            addCriterion("interval_type <=", value, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeLike(String value) {
            addCriterion("interval_type like", value, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeNotLike(String value) {
            addCriterion("interval_type not like", value, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeIn(List<String> values) {
            addCriterion("interval_type in", values, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeNotIn(List<String> values) {
            addCriterion("interval_type not in", values, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeBetween(String value1, String value2) {
            addCriterion("interval_type between", value1, value2, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalTypeNotBetween(String value1, String value2) {
            addCriterion("interval_type not between", value1, value2, "intervalType");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesIsNull() {
            addCriterion("interval_values is null");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesIsNotNull() {
            addCriterion("interval_values is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesEqualTo(String value) {
            addCriterion("interval_values =", value, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesNotEqualTo(String value) {
            addCriterion("interval_values <>", value, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesGreaterThan(String value) {
            addCriterion("interval_values >", value, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesGreaterThanOrEqualTo(String value) {
            addCriterion("interval_values >=", value, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesLessThan(String value) {
            addCriterion("interval_values <", value, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesLessThanOrEqualTo(String value) {
            addCriterion("interval_values <=", value, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesLike(String value) {
            addCriterion("interval_values like", value, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesNotLike(String value) {
            addCriterion("interval_values not like", value, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesIn(List<String> values) {
            addCriterion("interval_values in", values, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesNotIn(List<String> values) {
            addCriterion("interval_values not in", values, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesBetween(String value1, String value2) {
            addCriterion("interval_values between", value1, value2, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalValuesNotBetween(String value1, String value2) {
            addCriterion("interval_values not between", value1, value2, "intervalValues");
            return (Criteria) this;
        }

        public Criteria andIntervalHourIsNull() {
            addCriterion("interval_hour is null");
            return (Criteria) this;
        }

        public Criteria andIntervalHourIsNotNull() {
            addCriterion("interval_hour is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalHourEqualTo(Integer value) {
            addCriterion("interval_hour =", value, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourNotEqualTo(Integer value) {
            addCriterion("interval_hour <>", value, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourGreaterThan(Integer value) {
            addCriterion("interval_hour >", value, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourGreaterThanOrEqualTo(Integer value) {
            addCriterion("interval_hour >=", value, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourLessThan(Integer value) {
            addCriterion("interval_hour <", value, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourLessThanOrEqualTo(Integer value) {
            addCriterion("interval_hour <=", value, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourIn(List<Integer> values) {
            addCriterion("interval_hour in", values, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourNotIn(List<Integer> values) {
            addCriterion("interval_hour not in", values, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourBetween(Integer value1, Integer value2) {
            addCriterion("interval_hour between", value1, value2, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalHourNotBetween(Integer value1, Integer value2) {
            addCriterion("interval_hour not between", value1, value2, "intervalHour");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteIsNull() {
            addCriterion("interval_minute is null");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteIsNotNull() {
            addCriterion("interval_minute is not null");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteEqualTo(Integer value) {
            addCriterion("interval_minute =", value, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteNotEqualTo(Integer value) {
            addCriterion("interval_minute <>", value, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteGreaterThan(Integer value) {
            addCriterion("interval_minute >", value, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("interval_minute >=", value, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteLessThan(Integer value) {
            addCriterion("interval_minute <", value, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteLessThanOrEqualTo(Integer value) {
            addCriterion("interval_minute <=", value, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteIn(List<Integer> values) {
            addCriterion("interval_minute in", values, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteNotIn(List<Integer> values) {
            addCriterion("interval_minute not in", values, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteBetween(Integer value1, Integer value2) {
            addCriterion("interval_minute between", value1, value2, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andIntervalMinuteNotBetween(Integer value1, Integer value2) {
            addCriterion("interval_minute not between", value1, value2, "intervalMinute");
            return (Criteria) this;
        }

        public Criteria andValidStartdateIsNull() {
            addCriterion("valid_startdate is null");
            return (Criteria) this;
        }

        public Criteria andValidStartdateIsNotNull() {
            addCriterion("valid_startdate is not null");
            return (Criteria) this;
        }

        public Criteria andValidStartdateEqualTo(Date value) {
            addCriterion("valid_startdate =", value, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateNotEqualTo(Date value) {
            addCriterion("valid_startdate <>", value, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateGreaterThan(Date value) {
            addCriterion("valid_startdate >", value, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateGreaterThanOrEqualTo(Date value) {
            addCriterion("valid_startdate >=", value, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateLessThan(Date value) {
            addCriterion("valid_startdate <", value, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateLessThanOrEqualTo(Date value) {
            addCriterion("valid_startdate <=", value, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateIn(List<Date> values) {
            addCriterion("valid_startdate in", values, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateNotIn(List<Date> values) {
            addCriterion("valid_startdate not in", values, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateBetween(Date value1, Date value2) {
            addCriterion("valid_startdate between", value1, value2, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidStartdateNotBetween(Date value1, Date value2) {
            addCriterion("valid_startdate not between", value1, value2, "validStartdate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateIsNull() {
            addCriterion("valid_enddate is null");
            return (Criteria) this;
        }

        public Criteria andValidEnddateIsNotNull() {
            addCriterion("valid_enddate is not null");
            return (Criteria) this;
        }

        public Criteria andValidEnddateEqualTo(Date value) {
            addCriterion("valid_enddate =", value, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateNotEqualTo(Date value) {
            addCriterion("valid_enddate <>", value, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateGreaterThan(Date value) {
            addCriterion("valid_enddate >", value, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateGreaterThanOrEqualTo(Date value) {
            addCriterion("valid_enddate >=", value, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateLessThan(Date value) {
            addCriterion("valid_enddate <", value, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateLessThanOrEqualTo(Date value) {
            addCriterion("valid_enddate <=", value, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateIn(List<Date> values) {
            addCriterion("valid_enddate in", values, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateNotIn(List<Date> values) {
            addCriterion("valid_enddate not in", values, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateBetween(Date value1, Date value2) {
            addCriterion("valid_enddate between", value1, value2, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andValidEnddateNotBetween(Date value1, Date value2) {
            addCriterion("valid_enddate not between", value1, value2, "validEnddate");
            return (Criteria) this;
        }

        public Criteria andStartHourIsNull() {
            addCriterion("start_hour is null");
            return (Criteria) this;
        }

        public Criteria andStartHourIsNotNull() {
            addCriterion("start_hour is not null");
            return (Criteria) this;
        }

        public Criteria andStartHourEqualTo(Integer value) {
            addCriterion("start_hour =", value, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourNotEqualTo(Integer value) {
            addCriterion("start_hour <>", value, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourGreaterThan(Integer value) {
            addCriterion("start_hour >", value, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_hour >=", value, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourLessThan(Integer value) {
            addCriterion("start_hour <", value, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourLessThanOrEqualTo(Integer value) {
            addCriterion("start_hour <=", value, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourIn(List<Integer> values) {
            addCriterion("start_hour in", values, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourNotIn(List<Integer> values) {
            addCriterion("start_hour not in", values, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourBetween(Integer value1, Integer value2) {
            addCriterion("start_hour between", value1, value2, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartHourNotBetween(Integer value1, Integer value2) {
            addCriterion("start_hour not between", value1, value2, "startHour");
            return (Criteria) this;
        }

        public Criteria andStartMinuteIsNull() {
            addCriterion("start_minute is null");
            return (Criteria) this;
        }

        public Criteria andStartMinuteIsNotNull() {
            addCriterion("start_minute is not null");
            return (Criteria) this;
        }

        public Criteria andStartMinuteEqualTo(Integer value) {
            addCriterion("start_minute =", value, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteNotEqualTo(Integer value) {
            addCriterion("start_minute <>", value, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteGreaterThan(Integer value) {
            addCriterion("start_minute >", value, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("start_minute >=", value, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteLessThan(Integer value) {
            addCriterion("start_minute <", value, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteLessThanOrEqualTo(Integer value) {
            addCriterion("start_minute <=", value, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteIn(List<Integer> values) {
            addCriterion("start_minute in", values, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteNotIn(List<Integer> values) {
            addCriterion("start_minute not in", values, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteBetween(Integer value1, Integer value2) {
            addCriterion("start_minute between", value1, value2, "startMinute");
            return (Criteria) this;
        }

        public Criteria andStartMinuteNotBetween(Integer value1, Integer value2) {
            addCriterion("start_minute not between", value1, value2, "startMinute");
            return (Criteria) this;
        }

        public Criteria andEndHourIsNull() {
            addCriterion("end_hour is null");
            return (Criteria) this;
        }

        public Criteria andEndHourIsNotNull() {
            addCriterion("end_hour is not null");
            return (Criteria) this;
        }

        public Criteria andEndHourEqualTo(Integer value) {
            addCriterion("end_hour =", value, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourNotEqualTo(Integer value) {
            addCriterion("end_hour <>", value, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourGreaterThan(Integer value) {
            addCriterion("end_hour >", value, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_hour >=", value, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourLessThan(Integer value) {
            addCriterion("end_hour <", value, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourLessThanOrEqualTo(Integer value) {
            addCriterion("end_hour <=", value, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourIn(List<Integer> values) {
            addCriterion("end_hour in", values, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourNotIn(List<Integer> values) {
            addCriterion("end_hour not in", values, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourBetween(Integer value1, Integer value2) {
            addCriterion("end_hour between", value1, value2, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndHourNotBetween(Integer value1, Integer value2) {
            addCriterion("end_hour not between", value1, value2, "endHour");
            return (Criteria) this;
        }

        public Criteria andEndMinuteIsNull() {
            addCriterion("end_minute is null");
            return (Criteria) this;
        }

        public Criteria andEndMinuteIsNotNull() {
            addCriterion("end_minute is not null");
            return (Criteria) this;
        }

        public Criteria andEndMinuteEqualTo(Integer value) {
            addCriterion("end_minute =", value, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteNotEqualTo(Integer value) {
            addCriterion("end_minute <>", value, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteGreaterThan(Integer value) {
            addCriterion("end_minute >", value, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteGreaterThanOrEqualTo(Integer value) {
            addCriterion("end_minute >=", value, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteLessThan(Integer value) {
            addCriterion("end_minute <", value, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteLessThanOrEqualTo(Integer value) {
            addCriterion("end_minute <=", value, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteIn(List<Integer> values) {
            addCriterion("end_minute in", values, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteNotIn(List<Integer> values) {
            addCriterion("end_minute not in", values, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteBetween(Integer value1, Integer value2) {
            addCriterion("end_minute between", value1, value2, "endMinute");
            return (Criteria) this;
        }

        public Criteria andEndMinuteNotBetween(Integer value1, Integer value2) {
            addCriterion("end_minute not between", value1, value2, "endMinute");
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