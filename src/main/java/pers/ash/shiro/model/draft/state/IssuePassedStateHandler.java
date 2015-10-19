package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.service.DraftContextService;

public class IssuePassedStateHandler extends AbstractDraftStateHandler {

	private String[] validCommitTypes = { "4" }; // 签发通过：只能提交编号

	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}
}
