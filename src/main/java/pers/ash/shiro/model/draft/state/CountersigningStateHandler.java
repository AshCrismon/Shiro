package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.model.draft.AuditRecord;
import pers.ash.shiro.model.draft.Draft;
import pers.ash.shiro.service.DraftContextService;
import pers.ash.shiro.util.DateUtils;

public class CountersigningStateHandler extends AbstractDraftStateHandler{

	private String[] validCommitTypes = {"21", "20"}; // 会签中：可以提交会签通过,会签驳回
	
	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}
	
	/**
	 * 会签是一次性提交给多人，会签人会签时：只要一人不通过，会签不通过；所有人会签通过，会签通过
	 * 这里需要重写saveOrUpdateDraft()
	 */
	@Override
	protected void saveOrUpdateDraft() {
		String draftId = draftVo.getId();
		String commitType = draftVo.getCommitType();
		Draft draft = draftService.findDraftByDraftId(draftId);
		draft.setLastUpdateDate(DateUtils.now());
		if(commitType.equals(validCommitTypes[0])){
			int auditorPassedCount = draft.getAuditorPassedCount() + 1;
			if(auditorPassedCount == draft.getAuditorTotalCount()){
				draft.setCurrentState(DraftState.CountersignPassed.name());
			}
		}
		if(commitType.equals(validCommitTypes[1])){
			draft.setCurrentState(DraftState.CountersignRejected.name());
		}
		draftVo.setLastUpdateDate(DateUtils.now());
		draftService.saveOrUpdate(draft);
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
