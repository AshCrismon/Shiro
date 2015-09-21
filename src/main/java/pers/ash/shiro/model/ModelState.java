package pers.ash.shiro.model;

public enum ModelState {
	
	LOCKED("锁定"),
	DELETE("删除"),
	REMOVE("回收站");
	
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
