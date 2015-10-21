package pers.ash.shiro.mapper.draft;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.mapper.draftaudit.DraftMapper;
import pers.ash.shiro.mapper.systemmanage.UserMapper;
import pers.ash.shiro.model.draftaudit.Draft;
import pers.ash.shiro.model.draftaudit.DraftExample;
import pers.ash.shiro.model.draftaudit.DraftState;
import pers.ash.shiro.model.systemmanage.User;
import pers.ash.shiro.util.DateUtils;
import pers.ash.shiro.util.UUIDUtils;

public class DraftMapperTest extends AbstractTransactionalConfig {

	@Autowired
	private DraftMapper draftMapper;
	@Autowired
	private UserMapper userMapper;

	@Before
	public void init() {
		addDrafts();
	}

	@Test //条件查询
	public void testCountByExample() {
		DraftExample draftExample = new DraftExample();
		draftExample.createCriteria().andCurrentStateEqualTo(DraftState.Drafting.name());
		Assert.assertEquals(6, draftMapper.countByExample(draftExample));
	}

	@Test
	public void testDeleteByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteByPrimaryKey() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsert() {
		Draft draft = new Draft();
		draft.setId(UUIDUtils.createUUID());
		draft.setTitle("测试用例-文稿标题一");
		draft.setAuditorId("b8a3a9c5ed554c7089300afa92e4f90a");
		draft.setCurrentState(DraftState.Drafting.name());
		draft.setReadTag(1);
		draft.setCreateDate(DateUtils.now());
		draft.setLastUpdateDate(DateUtils.now());
		int affectedRows = draftMapper.insert(draft);
		Assert.assertEquals(1, affectedRows);
	}

	@Test
	public void testInsertSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByExampleWithBLOBs() {
		fail("Not yet implemented");
	}

	@Test
	public void testSelectByExample() {
		DraftExample draftExample = new DraftExample();
		draftExample.createCriteria().andAuthorIdEqualTo("xxxxx");
		List<Draft> drafts = draftMapper.selectByExample(draftExample);
	}

	@Test
	public void testSelectByPrimaryKey() {
		Draft draft = draftMapper.selectByPrimaryKey(null);
		Assert.assertNull(draft);
	}

	@Test
	public void testUpdateByExampleSelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByExampleWithBLOBs() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByExample() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKeySelective() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKeyWithBLOBs() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateByPrimaryKey() {
		fail("Not yet implemented");
	}

	public void addDrafts() {
		List<User> users = userMapper.findAll();
		for (int i = 0; i < users.size(); i++) {
			addDraft("测试用例-文稿标题" + i + 1, users.get(i).getId(),
					DraftState.Drafting.name(), 1);
		}
	}

	public void addDraft(String title, String authorId, String currentState,
			int readTag) {
		Draft draft = new Draft();
		draft.setId(UUIDUtils.createUUID());
		draft.setTitle(title);
		draft.setAuthorId(authorId);
		draft.setCurrentState(DraftState.Drafting.name());
		draft.setReadTag(1);
		draft.setCreateDate(DateUtils.now());
		draft.setLastUpdateDate(DateUtils.now());
		draftMapper.insert(draft);
	}

}
