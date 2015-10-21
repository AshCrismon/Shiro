package pers.ash.shiro.model.draftaudit;

public enum DraftState {
	
	UnknowState("未知状态"),
	
	DraftStage("拟稿"),
	Drafting("拟稿中"),
	
	AuditStage("审核"),
	Auditing("审核中"),
	AuditRejected("审核驳回"),
	AuditPassed("审核通过"),
	
	CountersignStage("会签"),
	Countersigning("会签中"),
	CountersignRejected("会签驳回"),
	CountersignPassed("会签通过"),
	
	Issue_Stage("签发"),
	Issuing("签发中"),
	IssueRejected("签发驳回"),
	IssuePassed("签发通过"),
	
	NumberStage("编号"),
	Numbering("编号中"),
	Numbered("已分配编号"),
	
	PrintStage("打印"),
	Printing("打印中"),
	PrintOver("打印完成");
	
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
