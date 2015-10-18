package pers.ash.shiro.mapper.draft;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import pers.ash.shiro.model.draft.Draft;
import pers.ash.shiro.model.draft.state.AbstractDraftStateHandler;
import pers.ash.shiro.model.draft.state.DraftState;
import pers.ash.shiro.model.system.User;
import pers.ash.shiro.service.DraftContextService;
import pers.ash.shiro.vo.DraftVo;

public class DraftStateTest {
	
	
	@Test
	public void testDraftState(){
		Assert.assertEquals("DRAFTING", DraftState.Drafting.name());
		Assert.assertEquals(DraftState.Drafting, DraftState.valueOf(DraftState.Drafting.name()));
		Assert.assertEquals(DraftState.Drafting, DraftState.valueOf("DRAFTING"));
	}
	
	@Test
	public void test() throws Exception{
		User user = (User) Class.forName("pers.ash.shiro.model.system.User").newInstance();
		Assert.assertNotNull(user);
	}
	
	@Test
	public void testEnumSearch() throws Exception{
		Map<String, String> stateMapHandler = new HashMap<String, String>();
		String packagePath = "pers.ash.shiro.model.draft.state.";
		String suffix = "StateHandler";
		for(DraftState state : DraftState.values()){
			if(!state.name().contains("Stage") && !state.equals(DraftState.UnknowState)){
				String draftStateHandlerClass = packagePath + state.name() + suffix;
				stateMapHandler.put(state.name(), draftStateHandlerClass);
			}
		}
		Iterator<String> itr = stateMapHandler.keySet().iterator();
		while(itr.hasNext()){
			String key = itr.next();
			String draftStateHandlerClass = stateMapHandler.get(key);
			System.out.println(key + "---->" + draftStateHandlerClass);
			/*AbstractDraftStateHandler draftStateHandler = (AbstractDraftStateHandler) Class.forName(draftStateHandlerClass).newInstance();
			Assert.assertNotNull(draftStateHandler);*/
		}
	}
	
}
