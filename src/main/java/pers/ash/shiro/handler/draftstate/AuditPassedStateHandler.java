package pers.ash.shiro.handler.draftstate;

import pers.ash.shiro.model.draftaudit.Draft;
import pers.ash.shiro.model.draftaudit.DraftState;
import pers.ash.shiro.service.DraftContextService;
import pers.ash.shiro.util.DateUtils;

public class AuditPassedStateHandler extends AbstractDraftStateHandler{

	private String[] validCommitTypes = {"1", "2", "3"}; // 审核通过：可以提交审核,会签,签发
	
	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}
	
	@Override
	protected void saveOrUpdateDraft() {
		String draftId = draftVo.getId();
		String commitType = draftVo.getCommitType();
		Draft draft = draftService.findDraftByDraftId(draftId);
		draft.setLastUpdateDate(DateUtils.now());
		if(commitType.equals(validCommitTypes[0]) || commitType.equals(validCommitTypes[2])){
			super.saveOrUpdateDraft();
		}
		if(commitType.equals(validCommitTypes[1])){
			draft.setAuditorPassedCount(0);
			draft.setAuditorTotalCount(draftVo.getCommitPersonIds().length);
			draft.setLastUpdateDate(DateUtils.now());
			draft.setCurrentState(DraftState.Countersigning.name());
			draftService.saveOrUpdate(draft);
		}
	}
	
	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}

}
