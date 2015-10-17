package pers.ash.shiro.model.draft;

public class AuditRecord {
    private String id;

    private String draftId;

    private String auditorId;

    private String submitDate;

    private String auditDate;

    private String auditOpinion;

    private String auditState;

    private Integer readTag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDraftId() {
        return draftId;
    }

    public void setDraftId(String draftId) {
        this.draftId = draftId == null ? null : draftId.trim();
    }

    public String getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(String auditorId) {
        this.auditorId = auditorId == null ? null : auditorId.trim();
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate == null ? null : submitDate.trim();
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate == null ? null : auditDate.trim();
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion == null ? null : auditOpinion.trim();
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState == null ? null : auditState.trim();
    }

    public Integer getReadTag() {
        return readTag;
    }

    public void setReadTag(Integer readTag) {
        this.readTag = readTag;
    }
}