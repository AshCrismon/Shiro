package pers.ash.shiro.temp;

public abstract class AbstractClass1 {
	
	public void test(){
		System.out.println("----------->" + getCommitType());
		System.out.println("----------->" + getCommitState());
	}

	abstract String getCommitType();

	abstract String getCommitState();

}
