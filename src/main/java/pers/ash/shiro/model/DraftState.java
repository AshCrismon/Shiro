package pers.ash.shiro.model;

public enum DraftState {
	
	DRAFT_STAGE("拟稿"),
	DRAFTING("拟稿中"),
	
	AUDITING("审核"),
	AUDIT_REJECTED("审核驳回"),
	AUDIT_PASSED("审核通过"),
	
	COUNTERSIGN_STAGE("会签"),
	COUNTERSIGNING("会签中"),
	COUNTERSIGN_REJECTED("会签驳回"),
	COUNTERSIGN_PASSED("会签通过"),
	
	ISSUING("签发"),
	ISSUE_REJECTED("签发驳回"),
	ISSUE_PASSED("签发通过"),
	
	NUMBER_STAGE("编号"),
	NUMBERING("编号中"),
	NUMBERED("已分配编号"),
	
	PRINT_STAGE("打印"),
	PRINTING("打印中"),
	PRINT_OVER("打印完成");
	
	private String value;
	
	private DraftState(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
