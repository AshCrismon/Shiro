package pers.ash.shiro.service;

import java.util.List;

import pers.ash.shiro.model.draftaudit.AuditRecord;

public interface AuditRecordService {

	public void addAuditRecord(AuditRecord auditRecord);

	public void deleteAuditRecord(String auditRecordId);

	public void updateAuditRecord(AuditRecord auditRecord);

	public void saveOrUpdate(AuditRecord auditRecord);

	public List<AuditRecord> findAuditRecordByAuditorId(String auditorId);

	public List<AuditRecord> findAuditRecordByDraftId(String draftId);
	
	public List<AuditRecord> findAuditRecordByState(String draftId, String auditState);

}
