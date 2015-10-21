package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ash.shiro.mapper.draftaudit.AuditRecordMapper;
import pers.ash.shiro.model.draftaudit.AuditRecord;
import pers.ash.shiro.model.draftaudit.AuditRecordExample;
import pers.ash.shiro.service.AuditRecordService;
import pers.ash.shiro.util.UUIDUtils;

@Service
public class AuditRecordServiceImpl implements AuditRecordService {

	@Autowired
	private AuditRecordMapper auditRecordMapper;
	
	@Override
	public void addAuditRecord(AuditRecord auditRecord) {
		if(null == auditRecord){
			return;
		}
		if(null == auditRecord.getId()){
			auditRecord.setId(UUIDUtils.createUUID());
		}
		auditRecordMapper.insert(auditRecord);
	}

	@Override
	public void deleteAuditRecord(String auditRecordId) {
		auditRecordMapper.deleteByPrimaryKey(auditRecordId);
	}

	@Override
	public void updateAuditRecord(AuditRecord auditRecord) {
		auditRecordMapper.updateByPrimaryKey(auditRecord);
	}

	@Override
	public void saveOrUpdate(AuditRecord auditRecord) {
		if(null == auditRecord){
			return;
		}
		if(null == auditRecord.getId() || null == auditRecordMapper.selectByPrimaryKey(auditRecord.getId())){
			auditRecord.setId(UUIDUtils.createUUID());
			auditRecordMapper.insert(auditRecord);
		}else{
			auditRecordMapper.updateByPrimaryKey(auditRecord);
		}
	}

	@Override
	public List<AuditRecord> findAuditRecordByAuditorId(String auditorId) {
		AuditRecordExample auditExample = new AuditRecordExample();
		auditExample.createCriteria().andAuditorIdEqualTo(auditorId);
		return auditRecordMapper.selectByExample(auditExample);
	}

	@Override
	public List<AuditRecord> findAuditRecordByDraftId(String draftId) {
		AuditRecordExample auditExample = new AuditRecordExample();
		auditExample.createCriteria().andDraftIdEqualTo(draftId);
		return auditRecordMapper.selectByExample(auditExample);
	}

	@Override
	public List<AuditRecord> findAuditRecordByState(String draftId,
			String auditState) {
		AuditRecordExample auditExample = new AuditRecordExample();
		auditExample.createCriteria().andDraftIdEqualTo(draftId).andAuditStateEqualTo(auditState);
		return auditRecordMapper.selectByExample(auditExample);
	}

}
