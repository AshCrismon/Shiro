package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.service.DraftContextService;

public class IssueRejectedStateHandler extends AbstractDraftStateHandler {

	private String[] validCommitTypes = { "3" }; // 签发驳回：只能提交签发

	
	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}

}
