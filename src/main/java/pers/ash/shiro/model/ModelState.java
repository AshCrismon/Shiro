package pers.ash.shiro.model;

public enum ModelState {
	
	LOCKED("锁定"),
	NORMAL("正常");
	
	private String value;
	
	private ModelState(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
