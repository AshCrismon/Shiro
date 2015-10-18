package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.exception.CommitForAuditException;
import pers.ash.shiro.service.DraftContextService;

public class AuditingStateHandler extends AbstractDraftStateHandler{

	@Override
	public void handleDraft(DraftContextService draftContext){
		throw new CommitForAuditException("文稿当前正在审核中，不能再次提交");
	}

	@Override
	public String[] getValidCommitTypes() {
		return null;
	}

}
