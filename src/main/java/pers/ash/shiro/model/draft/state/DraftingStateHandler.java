package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.service.DraftContextService;

public class DraftingStateHandler extends AbstractDraftStateHandler {

	private String[] validCommitTypes = {"1"}; // 拟稿中：只能提交审核

	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}

}
