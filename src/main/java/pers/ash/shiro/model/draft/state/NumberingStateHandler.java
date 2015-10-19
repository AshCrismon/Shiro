package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.service.DraftContextService;

public class NumberingStateHandler extends AbstractDraftStateHandler{

	private String[] validCommitTypes = { "41" }; // 编号中：只能提交分配编号

	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}
}
