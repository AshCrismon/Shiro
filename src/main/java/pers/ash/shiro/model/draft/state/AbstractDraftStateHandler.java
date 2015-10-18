package pers.ash.shiro.model.draft.state;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtils;

import pers.ash.shiro.exception.CommitForAuditException;
import pers.ash.shiro.model.draft.Attachment;
import pers.ash.shiro.model.draft.AuditRecord;
import pers.ash.shiro.model.draft.RawDraft;
import pers.ash.shiro.service.AttachmentService;
import pers.ash.shiro.service.AuditRecordService;
import pers.ash.shiro.service.DraftContextService;
import pers.ash.shiro.service.DraftService;
import pers.ash.shiro.service.RawDraftService;
import pers.ash.shiro.util.DateUtils;
import pers.ash.shiro.vo.DraftVo;

public abstract class AbstractDraftStateHandler {

	public void handleDraft(DraftContextService draftContext) {
		DraftVo draftVo = draftContext.getDraftVo();
		DraftService draftService = draftContext.getDraftService();
		RawDraftService rawDraftService = draftContext.getRawDraftService();
		AttachmentService attachmentService = draftContext
				.getAttachmentService();
		AuditRecordService auditRecordService = draftContext
				.getAuditRecordService();

		String[] validCommitTypes = getValidCommitTypes(); // 根据当前文稿状态获取能够提交的类型
		String committedNextState = getCommittedNextState(draftVo
				.getCommitType());// 根据提交类型获取提交后的下一个状态

		if (commitTypeIsValid(draftVo.getCommitType(), validCommitTypes)) {
			draftVo.setCurrentState(committedNextState);
			draftService.saveOrUpdate(draftVo);
			AuditRecord auditRecord = createAuditRecord(draftVo);
			auditRecordService.addAuditRecord(auditRecord);
		} else {
			throw new CommitForAuditException("文稿当前提交类型只能为："
					+ getValidCommitTypeValues());
		}

		RawDraft rawDraft = createRawDraft(draftVo);
		rawDraftService.saveOrUpdate(rawDraft);

		Attachment attachment = createAttachment(draftVo);
		attachmentService.addAttachment(attachment);
	}

	private RawDraft createRawDraft(DraftVo draftVo) {
		RawDraft rawDraft = new RawDraft();
		try {
			BeanUtils.copyProperties(rawDraft, draftVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		rawDraft.setDraftId(draftVo.getId());
		return rawDraft;
	}

	private AuditRecord createAuditRecord(DraftVo draftVo) {
		AuditRecord auditRecord = new AuditRecord();
		auditRecord.setDraftId(draftVo.getId());
		auditRecord.setAuditorId(draftVo.getAuditorId());
		auditRecord.setSubmitDate(DateUtils.now());
		auditRecord.setReadTag(0);
		auditRecord.setAuditState(getCommittedNextState(draftVo.getCommitType()));
		return auditRecord;
	}

	private Attachment createAttachment(DraftVo draftVo) {
		Attachment attachment = new Attachment();
		attachment.setName(draftVo.getAttachmentName());
		attachment.setDraftId(draftVo.getId());
		return attachment;
	}

	public abstract String[] getValidCommitTypes(); // 特定文稿状态所能提交的类型,返回1,2,3,4,5

	public boolean commitTypeIsValid(String commitType,
			String[] validCommitTypes) {
		return Arrays.asList(validCommitTypes).contains(commitType);
	}

	public String getCommittedNextState(String commitType) { // 根据提交类型确定下一个状态
		switch (commitType) {
		case "1":
			return DraftState.Auditing.name();
		case "2":
			return DraftState.Countersigning.name();
		case "3":
			return DraftState.Issuing.name();
		case "4":
			return DraftState.Numbering.name();
		case "5":
			return DraftState.Printing.name();
		default:
			return DraftState.UnknowState.name();
		}
	}

	public StringBuffer getValidCommitTypeValues() {// 返回1,2,3,4,5对应的中文：审核，会签，签发，编号，打印
		String[] validCommitTypes = getValidCommitTypes();
		StringBuffer commitTypes = new StringBuffer();
		for (int i = 0; i < validCommitTypes.length; i++) {
			switch (validCommitTypes[i]) {
			case "1":
				commitTypes.append(DraftState.AuditStage.getValue() + ",");
				break;
			case "2":
				commitTypes.append(DraftState.CountersignStage.getValue() + ",");
				break;
			case "3":
				commitTypes.append(DraftState.Issue_Stage.getValue() + ",");
				break;
			case "4":
				commitTypes.append(DraftState.NumberStage.getValue() + ",");
				break;
			case "5":
				commitTypes.append(DraftState.PrintStage.getValue() + ",");
				break;
			default:
				commitTypes.append(DraftState.UnknowState.getValue() + ",");
				break;
			}
		}
		commitTypes.deleteCharAt(commitTypes.length() - 1);
		return commitTypes;
	}

}
