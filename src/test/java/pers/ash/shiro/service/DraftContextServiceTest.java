package pers.ash.shiro.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.draft.Draft;
import pers.ash.shiro.util.DateUtils;
import pers.ash.shiro.vo.DraftVo;

public class DraftContextServiceTest extends AbstractTransactionalConfig{

	@Autowired
	private DraftContextService draftContextService;
	@Autowired
	private DraftService draftService;
	
	@Test
	public void testCommitForAudit() throws Exception{
		DraftVo draftVo = new DraftVo();
		draftVo.setTitle("测试用例-文稿标题001");
		draftVo.setAuthorId("作者1-id");
		draftVo.setRemark("备注");
		draftVo.setContent("内容");
		draftVo.setAuditorId("审核人1-id");
		draftVo.setAttachmentName("附件1-标题");
		draftVo.setCommitType("1");
		draftVo.setCreateDate(DateUtils.now());
		draftVo.setLastUpdateDate(DateUtils.now());
		
		draftContextService.setDraftVo(draftVo);
		draftContextService.initalize();
		draftContextService.handleRequest();
		
		List<Draft> draftsOfAuthor = draftService.findDraftByAuditorId("审核人1-id");
		List<Draft> draftsOfAuditor = draftService.findDraftByAuthorId("作者1-id");
		
		Assert.assertEquals("测试用例-文稿标题001", draftsOfAuthor.get(0).getTitle());
		Assert.assertEquals("测试用例-文稿标题001", draftsOfAuditor.get(0).getTitle());
		
	}
}
