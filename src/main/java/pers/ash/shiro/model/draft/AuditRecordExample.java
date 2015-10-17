package pers.ash.shiro.model.draft;

import java.util.ArrayList;
import java.util.List;

public class AuditRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AuditRecordExample() {
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

    protected abstract static class GeneratedCriteria {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDraftIdIsNull() {
            addCriterion("draftId is null");
            return (Criteria) this;
        }

        public Criteria andDraftIdIsNotNull() {
            addCriterion("draftId is not null");
            return (Criteria) this;
        }

        public Criteria andDraftIdEqualTo(String value) {
            addCriterion("draftId =", value, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdNotEqualTo(String value) {
            addCriterion("draftId <>", value, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdGreaterThan(String value) {
            addCriterion("draftId >", value, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdGreaterThanOrEqualTo(String value) {
            addCriterion("draftId >=", value, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdLessThan(String value) {
            addCriterion("draftId <", value, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdLessThanOrEqualTo(String value) {
            addCriterion("draftId <=", value, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdLike(String value) {
            addCriterion("draftId like", value, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdNotLike(String value) {
            addCriterion("draftId not like", value, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdIn(List<String> values) {
            addCriterion("draftId in", values, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdNotIn(List<String> values) {
            addCriterion("draftId not in", values, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdBetween(String value1, String value2) {
            addCriterion("draftId between", value1, value2, "draftId");
            return (Criteria) this;
        }

        public Criteria andDraftIdNotBetween(String value1, String value2) {
            addCriterion("draftId not between", value1, value2, "draftId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdIsNull() {
            addCriterion("auditorId is null");
            return (Criteria) this;
        }

        public Criteria andAuditorIdIsNotNull() {
            addCriterion("auditorId is not null");
            return (Criteria) this;
        }

        public Criteria andAuditorIdEqualTo(String value) {
            addCriterion("auditorId =", value, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdNotEqualTo(String value) {
            addCriterion("auditorId <>", value, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdGreaterThan(String value) {
            addCriterion("auditorId >", value, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdGreaterThanOrEqualTo(String value) {
            addCriterion("auditorId >=", value, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdLessThan(String value) {
            addCriterion("auditorId <", value, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdLessThanOrEqualTo(String value) {
            addCriterion("auditorId <=", value, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdLike(String value) {
            addCriterion("auditorId like", value, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdNotLike(String value) {
            addCriterion("auditorId not like", value, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdIn(List<String> values) {
            addCriterion("auditorId in", values, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdNotIn(List<String> values) {
            addCriterion("auditorId not in", values, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdBetween(String value1, String value2) {
            addCriterion("auditorId between", value1, value2, "auditorId");
            return (Criteria) this;
        }

        public Criteria andAuditorIdNotBetween(String value1, String value2) {
            addCriterion("auditorId not between", value1, value2, "auditorId");
            return (Criteria) this;
        }

        public Criteria andSubmitDateIsNull() {
            addCriterion("submitDate is null");
            return (Criteria) this;
        }

        public Criteria andSubmitDateIsNotNull() {
            addCriterion("submitDate is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitDateEqualTo(String value) {
            addCriterion("submitDate =", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateNotEqualTo(String value) {
            addCriterion("submitDate <>", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateGreaterThan(String value) {
            addCriterion("submitDate >", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateGreaterThanOrEqualTo(String value) {
            addCriterion("submitDate >=", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateLessThan(String value) {
            addCriterion("submitDate <", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateLessThanOrEqualTo(String value) {
            addCriterion("submitDate <=", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateLike(String value) {
            addCriterion("submitDate like", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateNotLike(String value) {
            addCriterion("submitDate not like", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateIn(List<String> values) {
            addCriterion("submitDate in", values, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateNotIn(List<String> values) {
            addCriterion("submitDate not in", values, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateBetween(String value1, String value2) {
            addCriterion("submitDate between", value1, value2, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateNotBetween(String value1, String value2) {
            addCriterion("submitDate not between", value1, value2, "submitDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateIsNull() {
            addCriterion("auditDate is null");
            return (Criteria) this;
        }

        public Criteria andAuditDateIsNotNull() {
            addCriterion("auditDate is not null");
            return (Criteria) this;
        }

        public Criteria andAuditDateEqualTo(String value) {
            addCriterion("auditDate =", value, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateNotEqualTo(String value) {
            addCriterion("auditDate <>", value, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateGreaterThan(String value) {
            addCriterion("auditDate >", value, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateGreaterThanOrEqualTo(String value) {
            addCriterion("auditDate >=", value, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateLessThan(String value) {
            addCriterion("auditDate <", value, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateLessThanOrEqualTo(String value) {
            addCriterion("auditDate <=", value, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateLike(String value) {
            addCriterion("auditDate like", value, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateNotLike(String value) {
            addCriterion("auditDate not like", value, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateIn(List<String> values) {
            addCriterion("auditDate in", values, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateNotIn(List<String> values) {
            addCriterion("auditDate not in", values, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateBetween(String value1, String value2) {
            addCriterion("auditDate between", value1, value2, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditDateNotBetween(String value1, String value2) {
            addCriterion("auditDate not between", value1, value2, "auditDate");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionIsNull() {
            addCriterion("auditOpinion is null");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionIsNotNull() {
            addCriterion("auditOpinion is not null");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionEqualTo(String value) {
            addCriterion("auditOpinion =", value, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionNotEqualTo(String value) {
            addCriterion("auditOpinion <>", value, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionGreaterThan(String value) {
            addCriterion("auditOpinion >", value, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionGreaterThanOrEqualTo(String value) {
            addCriterion("auditOpinion >=", value, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionLessThan(String value) {
            addCriterion("auditOpinion <", value, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionLessThanOrEqualTo(String value) {
            addCriterion("auditOpinion <=", value, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionLike(String value) {
            addCriterion("auditOpinion like", value, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionNotLike(String value) {
            addCriterion("auditOpinion not like", value, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionIn(List<String> values) {
            addCriterion("auditOpinion in", values, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionNotIn(List<String> values) {
            addCriterion("auditOpinion not in", values, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionBetween(String value1, String value2) {
            addCriterion("auditOpinion between", value1, value2, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditOpinionNotBetween(String value1, String value2) {
            addCriterion("auditOpinion not between", value1, value2, "auditOpinion");
            return (Criteria) this;
        }

        public Criteria andAuditStateIsNull() {
            addCriterion("auditState is null");
            return (Criteria) this;
        }

        public Criteria andAuditStateIsNotNull() {
            addCriterion("auditState is not null");
            return (Criteria) this;
        }

        public Criteria andAuditStateEqualTo(String value) {
            addCriterion("auditState =", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotEqualTo(String value) {
            addCriterion("auditState <>", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateGreaterThan(String value) {
            addCriterion("auditState >", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateGreaterThanOrEqualTo(String value) {
            addCriterion("auditState >=", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateLessThan(String value) {
            addCriterion("auditState <", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateLessThanOrEqualTo(String value) {
            addCriterion("auditState <=", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateLike(String value) {
            addCriterion("auditState like", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotLike(String value) {
            addCriterion("auditState not like", value, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateIn(List<String> values) {
            addCriterion("auditState in", values, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotIn(List<String> values) {
            addCriterion("auditState not in", values, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateBetween(String value1, String value2) {
            addCriterion("auditState between", value1, value2, "auditState");
            return (Criteria) this;
        }

        public Criteria andAuditStateNotBetween(String value1, String value2) {
            addCriterion("auditState not between", value1, value2, "auditState");
            return (Criteria) this;
        }

        public Criteria andReadTagIsNull() {
            addCriterion("readTag is null");
            return (Criteria) this;
        }

        public Criteria andReadTagIsNotNull() {
            addCriterion("readTag is not null");
            return (Criteria) this;
        }

        public Criteria andReadTagEqualTo(Integer value) {
            addCriterion("readTag =", value, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagNotEqualTo(Integer value) {
            addCriterion("readTag <>", value, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagGreaterThan(Integer value) {
            addCriterion("readTag >", value, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagGreaterThanOrEqualTo(Integer value) {
            addCriterion("readTag >=", value, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagLessThan(Integer value) {
            addCriterion("readTag <", value, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagLessThanOrEqualTo(Integer value) {
            addCriterion("readTag <=", value, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagIn(List<Integer> values) {
            addCriterion("readTag in", values, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagNotIn(List<Integer> values) {
            addCriterion("readTag not in", values, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagBetween(Integer value1, Integer value2) {
            addCriterion("readTag between", value1, value2, "readTag");
            return (Criteria) this;
        }

        public Criteria andReadTagNotBetween(Integer value1, Integer value2) {
            addCriterion("readTag not between", value1, value2, "readTag");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
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