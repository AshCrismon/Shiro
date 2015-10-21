package pers.ash.shiro.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.draftaudit.Draft;
import pers.ash.shiro.model.draftaudit.DraftState;
import pers.ash.shiro.util.DateUtils;

public class DraftServiceTest extends AbstractTransactionalConfig{

	@Autowired
	private DraftService draftService;
	
	private List<Draft> drafts = new ArrayList<Draft>();
	
	@Before
	public void init(){
		addDrafts();
	}
	
	@Test
	public void testAddDraft() {
		Draft draft = new Draft();
		draft.setTitle("测试用例-文稿标题一");
		draft.setAuthorId("作者id");
		draft.setRemark("备注");
		draft.setContent("内容");
		draft.setCreateDate(DateUtils.now());
		draft.setLastUpdateDate(DateUtils.now());
		draftService.addDraft(draft);
	}

	@Test
	public void testDeleteDraft() {
		draftService.deleteDraft(drafts.get(0).getId());
	}

	@Test
	public void testUpdateDraft() {
		Draft draft = drafts.get(0);
		draft.setTitle("新标题");
		draft.setRemark("备注更新");
		draft.setContent("内容更新");
		draft.setCurrentState(DraftState.Drafting.name());
		draftService.updateDraft(draft);
	}

	@Test
	public void testFindDraftByAuthorId() {
		List<Draft> drafts = draftService.findDraftByAuthorId("作者1-id");
		Assert.assertEquals(1, drafts.size());
	}
	
	@Test
	public void testFindDraftByAuditorId() {
		List<Draft> drafts = draftService.findDraftByAuditorId("审核人1-id");
		Assert.assertTrue(drafts.isEmpty());
	}
	
	@Test
	public void testFindDraftByDraftId() {
	}

	@Test
	public void testFindDraftByState() {
	}
	
	public void addDrafts(){
		addDraft("测试用例-文稿标题1", "作者1-id", "备注一", "内容一");
		addDraft("测试用例-文稿标题2", "作者2-id", "备注二", "内容二");
		addDraft("测试用例-文稿标题3", "作者3-id", "备注三", "内容三");
		addDraft("测试用例-文稿标题4", "作者4-id", "备注四", "内容四");
		addDraft("测试用例-文稿标题5", "作者5-id", "备注五", "内容五");
	}
	
	public void addDraft(String title, String authorId, String remark, String content){
		Draft draft = new Draft();
		draft.setTitle(title);
		draft.setAuthorId(authorId);
		draft.setRemark(remark);
		draft.setContent(content);
		draft.setCreateDate(DateUtils.now());
		draft.setLastUpdateDate(DateUtils.now());
		draftService.addDraft(draft);
		drafts.add(draft);
	}

}
