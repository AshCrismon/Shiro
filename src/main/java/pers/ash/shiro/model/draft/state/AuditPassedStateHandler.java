package pers.ash.shiro.model.draft.state;

public class AuditPassedStateHandler extends AbstractDraftStateHandler{

	private String[] validCommitTypes = {"1", "2", "3"}; // 审核通过：可以提交审核,会签,签发
	
	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}

}
