package pers.ash.shiro.handler.draftstate;

import pers.ash.shiro.model.draftaudit.AuditRecord;
import pers.ash.shiro.service.DraftContextService;
import pers.ash.shiro.util.DateUtils;

public class IssuingStateHandler extends AbstractDraftStateHandler{

	private String[] validCommitTypes = { "31", "30" }; // 签发中：可以提交签发通过,签发驳回
	
	
	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}
	
	/**
	 * 抽象方法中是拟稿人提交,生成审阅记录；这里是审阅人回复,需要重写生成审阅记录的方法
	 */
	@Override
	protected void createAuditRecord() {
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setDraftId(draftVo.getId());
		auditRecord.setAuditorId(draftVo.getAuditorId());
		auditRecord.setSubmitDate(DateUtils.now());
		auditRecord.setAuditDate(DateUtils.now());
		auditRecord.setAuditOpinion(draftVo.getAuditOpinion());
		auditRecord.setReadTag(0);
		auditRecord.setAuditState(getCommittedNextState(draftVo.getCommitType()));
		auditRecordService.addAuditRecord(auditRecord);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}

}
