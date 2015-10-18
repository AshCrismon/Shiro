package pers.ash.shiro.temp;

public class TestClass1 extends AbstractClass1{

	private String commitType = "1";
	private String commitState = "auditing";
	
	@Override
	public String getCommitType() {
		return commitType;
	}
	@Override
	public String getCommitState() {
		return commitState;
	}
	
}
