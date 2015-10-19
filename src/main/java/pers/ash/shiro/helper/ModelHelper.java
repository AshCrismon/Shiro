package pers.ash.shiro.helper;

import pers.ash.shiro.model.ModelState;

public class ModelHelper {
	
	private static ModelState state = ModelState.REMOVE;

	public static ModelState getState() {
		return state;
	}

	public static void setState(ModelState state) {
		ModelHelper.state = state;
	}
	
	
}
