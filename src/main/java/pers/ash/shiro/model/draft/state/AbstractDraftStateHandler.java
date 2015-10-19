package pers.ash.shiro.model.draft.state;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
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

	protected DraftVo draftVo;
	protected DraftService draftService;
	protected RawDraftService rawDraftService;
	protected AttachmentService attachmentService;
	protected AuditRecordService auditRecordService;
	protected String committedNextState;

	public void handleDraft(DraftContextService draftContext) {
		draftVo = draftContext.getDraftVo();
		draftService = draftContext.getDraftService();
		rawDraftService = draftContext.getRawDraftService();
		attachmentService = draftContext.getAttachmentService();
		auditRecordService = draftContext.getAuditRecordService();
		committedNextState = getCommittedNextState(draftVo.getCommitType());// 根据提交类型获取提交后的下一个状态
		
		String[] validCommitTypes = getValidCommitTypes(); // 根据当前文稿状态获取能够提交的类型
		
		if (commitTypeIsValid(draftVo.getCommitType(), validCommitTypes)) {
			saveOrUpdateDraft();
			saveOrUpdateRawDraft();
			saveOrUpdateAttachment();
			createAuditRecord();
		} else {
			throw new CommitForAuditException("文稿当前提交类型只能为："
					+ getValidCommitTypeValues());
		}

	}
	
	protected void saveOrUpdateDraft(){
		draftVo.setLastUpdateDate(DateUtils.now());
		draftVo.setCurrentState(committedNextState);
		draftService.saveOrUpdate(draftVo);
	}

	private void saveOrUpdateRawDraft() {
		RawDraft rawDraft = null;
		if(!rawDraftAlreadyExists()){
			rawDraft = new RawDraft();
			rawDraft.setDraftId(draftVo.getId());
		}else{
			rawDraft = rawDraftService.findRawDraftByDraftId(draftVo.getId());
		}
		try {
			BeanUtils.copyProperties(rawDraft, draftVo);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		rawDraftService.saveOrUpdate(rawDraft);
	}
	
	private boolean rawDraftAlreadyExists(){
		return rawDraftService.findRawDraftByDraftId(draftVo.getId()) != null;
	}
	
	private void saveOrUpdateAttachment() {
		Attachment[] attachments = draftVo.getAttachments();
		for(int i = 0; i < attachments.length; i++){
			if(!attachmentAlreadyExists(attachments[i].getId())){
				attachments[i].setDraftId(draftVo.getId());
				attachmentService.addAttachment(attachments[i]);
			}
		}
	}
	
	private boolean attachmentAlreadyExists(String attachmentId){
		return attachmentService.findAttachmentByAttachmentId(attachmentId) != null;
	}

	protected void createAuditRecord() {
		String[] commitPersonIds = draftVo.getCommitPersonIds();
		for(int i = 0; i < commitPersonIds.length; i++){
			AuditRecord auditRecord = new AuditRecord();
			auditRecord.setDraftId(draftVo.getId());
			auditRecord.setAuditorId(commitPersonIds[i]);
			auditRecord.setSubmitDate(DateUtils.now());
			auditRecord.setReadTag(0);
			auditRecord
					.setAuditState(getCommittedNextState(draftVo.getCommitType()));
			auditRecordService.addAuditRecord(auditRecord);
		}

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
		case "11":
			return DraftState.AuditPassed.name();
		case "10":
			return DraftState.AuditRejected.name();
		case "2":
			return DraftState.Countersigning.name();
		case "21":
			return DraftState.CountersignPassed.name();
		case "20":
			return DraftState.CountersignRejected.name();
		case "3":
			return DraftState.Issuing.name();
		case "31":
			return DraftState.IssuePassed.name();
		case "30":
			return DraftState.IssueRejected.name();
		case "4":
			return DraftState.Numbering.name();
		case "41":
			return DraftState.Numbered.name();
		case "5":
			return DraftState.Printing.name();
		case "51":
			return DraftState.PrintOver.name();
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
			case "11":
				commitTypes.append(DraftState.AuditPassed.getValue() + ",");
				break;
			case "10":
				commitTypes.append(DraftState.AuditRejected.getValue() + ",");
				break;
			case "2":
				commitTypes
						.append(DraftState.CountersignStage.getValue() + ",");
				break;
			case "21":
				commitTypes.append(DraftState.CountersignPassed.getValue()
						+ ",");
				break;
			case "20":
				commitTypes.append(DraftState.CountersignRejected.getValue()
						+ ",");
				break;
			case "3":
				commitTypes.append(DraftState.Issue_Stage.getValue() + ",");
				break;
			case "31":
				commitTypes.append(DraftState.IssuePassed.getValue() + ",");
				break;
			case "30":
				commitTypes.append(DraftState.IssueRejected.getValue() + ",");
				break;
			case "4":
				commitTypes.append(DraftState.NumberStage.getValue() + ",");
				break;
			case "41":
				commitTypes.append(DraftState.Numbered.getValue() + ",");
				break;
			case "5":
				commitTypes.append(DraftState.PrintStage.getValue() + ",");
				break;
			case "51":
				commitTypes.append(DraftState.PrintOver.getValue() + ",");
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
