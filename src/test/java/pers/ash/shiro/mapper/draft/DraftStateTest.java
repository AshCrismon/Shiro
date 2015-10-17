package pers.ash.shiro.mapper.draft;


import org.junit.Assert;
import org.junit.Test;

import pers.ash.shiro.model.DraftState;

public class DraftStateTest {
	
	@Test
	public void testDraftState(){
		Assert.assertEquals("DRAFTING", DraftState.DRAFTING.name());
	}
}
