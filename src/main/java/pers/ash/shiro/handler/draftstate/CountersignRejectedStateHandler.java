package pers.ash.shiro.handler.draftstate;

import pers.ash.shiro.model.draftaudit.Draft;
import pers.ash.shiro.model.draftaudit.DraftState;
import pers.ash.shiro.service.DraftContextService;

public class CountersignRejectedStateHandler extends AbstractDraftStateHandler{

	private String[] validCommitTypes = {"2"}; // 会签驳回：只能提交会签
	
	
	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	protected void saveOrUpdateDraft() {
		String draftId = draftVo.getId();
		Draft draft = draftService.findDraftByDraftId(draftId);
		draft.setCurrentState(DraftState.Countersigning.name());
		draft.setAuditorTotalCount(draftVo.getCommitPersonIds().length);
		draft.setAuditorPassedCount(0);
		draftService.saveOrUpdate(draft);
	}
	
	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}
	
}
