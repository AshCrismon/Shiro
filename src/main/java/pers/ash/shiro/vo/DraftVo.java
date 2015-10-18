package pers.ash.shiro.vo;

import pers.ash.shiro.model.draft.Draft;

public class DraftVo extends Draft{

	private String commitType;//1:审核 2:会签 3:签发 4:申请编号 5:申请打印
	
	private String attachmentName;
	
	public String getCommitType() {
		return commitType;
	}
	public void setCommitType(String commitType) {
		this.commitType = commitType;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	
}
