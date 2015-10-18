package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.service.DraftContextService;

public class AuditRejectedStateHandler extends AbstractDraftStateHandler{

	private String[] validCommitTypes = {"1"}; // 审核驳回：只能提交审核
	
	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}

}
