package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.service.DraftContextService;

public class PrintingStateHandler extends AbstractDraftStateHandler{

	private String[] validCommitTypes = { "51" }; // 打印中：只能提交打印完成

	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}
}
