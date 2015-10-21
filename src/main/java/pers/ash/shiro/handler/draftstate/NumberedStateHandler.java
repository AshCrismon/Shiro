package pers.ash.shiro.handler.draftstate;

import pers.ash.shiro.service.DraftContextService;

public class NumberedStateHandler extends AbstractDraftStateHandler {

	private String[] validCommitTypes = { "5" }; // 已分配编号：只能提交打印

	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}
}
