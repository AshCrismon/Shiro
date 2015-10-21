package pers.ash.shiro.service;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.draftaudit.Attachment;
import pers.ash.shiro.model.draftaudit.Draft;
import pers.ash.shiro.model.draftaudit.DraftState;
import pers.ash.shiro.util.DateUtils;
import pers.ash.shiro.vo.DraftVo;

public class DraftContextServiceTest extends AbstractTransactionalConfig {

	@Autowired
	private DraftContextService draftContextService;
	@Autowired
	private DraftService draftService;

	private DraftVo draftVo = new DraftVo();
	private String expectedState = DraftState.UnknowState.name();

	/**
	 * 1:审核 11:审核通过 10:审核驳回 2:会签 21:会签通过 20:会签驳回 3:签发 31:签发通过 30:签发驳回 4:申请编号
	 * 41:分配编号 5:申请打印 51:打印完成
	 */
	@Before
	public void init() {
		draftVo.setTitle("测试用例-文稿标题001");
		draftVo.setAuthorId("作者1-id");
		draftVo.setRemark("备注");
		draftVo.setContent("内容");
		draftVo.setAuditorId("审核人1-id");
		draftVo.setCreateDate(DateUtils.now());
		draftVo.setLastUpdateDate(DateUtils.now());

		Attachment attachment = new Attachment();
		attachment.setName("附件1-标题");

		draftVo.setAttachments(new Attachment[] { attachment });
	}

	@After
	public void testResult() {
		List<Draft> draftsOfAuditor = draftService.findDraftByAuditorId("审核人1-id");
		List<Draft> draftsOfAuthor = draftService.findDraftByAuthorId("作者1-id");

		Assert.assertEquals(expectedState, draftsOfAuthor.get(0).getCurrentState());
		Assert.assertEquals("测试用例-文稿标题001", draftsOfAuthor.get(0).getTitle());
		Assert.assertEquals("测试用例-文稿标题001", draftsOfAuditor.get(0).getTitle());
	}

	public void initalizeDraftContext() {
		draftContextService.setDraftVo(draftVo);
		draftContextService.initalize();
	}

	@Test
	public void testCommitForAudit() throws Exception {
		commitForAudit("1", DraftState.Auditing.name());		//提交审核-------->提交后的状态：审核中
		auditDraft("11", DraftState.AuditPassed.name());		//审核通过-------->提交后的状态：审核通过
		commitForAudit("2", DraftState.Countersigning.name());	//提交会签-------->提交后的状态：会签中
		auditDraft("21", DraftState.CountersignPassed.name());	//会签通过-------->提交后的状态：会签通过
		commitForAudit("3", DraftState.Issuing.name());			//提交签发-------->提交后的状态：签发中
		auditDraft("31", DraftState.IssuePassed.name());		//签发通过-------->提交后的状态：签发通过
		commitForAudit("4", DraftState.Numbering.name());		//提交编号-------->提交后的状态：编号中
		auditDraft("41", DraftState.Numbered.name());			//分配编号-------->提交后的状态：已分配编号
		commitForAudit("5", DraftState.Printing.name());		//提交打印-------->提交后的状态：打印中
		auditDraft("51", DraftState.PrintOver.name());			//打印完成-------->提交后的状态：打印完成
	}

	public void commitForAudit(String commitType, String expectedState) throws Exception {
		draftVo.setCommitPersonIds(new String[] { "审核人1-id" });
		draftVo.setCommitType(commitType);
		initalizeDraftContext();
		draftContextService.handleRequest();

		this.expectedState = expectedState;
	}

	public void auditDraft(String auditType, String expectedState) throws Exception {
		draftVo.setCommitType(auditType);
		draftVo.setAuditorId("审核人1-id");
		initalizeDraftContext();
		draftContextService.handleRequest();
		
		this.expectedState = expectedState;
	}

}
