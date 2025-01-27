package pers.ash.shiro.vo;

import pers.ash.shiro.model.draftaudit.Attachment;
import pers.ash.shiro.model.draftaudit.Draft;

public class DraftVo extends Draft{

	private static final long serialVersionUID = -6374024693794506197L;
	
	private String commitType;	// 1:审核 11:审核通过 10:审核驳回 2:会签 21:会签通过 20:会签驳回 3:签发
								// 31:签发通过 30:签发驳回 4:申请编号 41:分配编号 5:申请打印 51:打印完成
	private String[] commitPersonIds;// 拟稿人提交使用这个字段，表示提交人ids，审阅人批复，使用auditorId,传递审阅人的id过来

	private Attachment[] attachments;

	private String auditOpinion;

	public String getCommitType() {
		return commitType;
	}

	public void setCommitType(String commitType) {
		this.commitType = commitType;
	}

	public String[] getCommitPersonIds() {
		return commitPersonIds;
	}

	public void setCommitPersonIds(String[] commitPersonIds) {
		this.commitPersonIds = commitPersonIds;
	}

	public Attachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(Attachment[] attachments) {
		this.attachments = attachments;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}

}
