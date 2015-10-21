package pers.ash.shiro.service;

import java.util.List;

import pers.ash.shiro.model.draftaudit.Attachment;

public interface AttachmentService {

	public void addAttachment(Attachment attachment);

	public void deleteAttachment(String attachmentId);

	public void updateAttachment(Attachment attachment);

	public void saveOrUpdate(Attachment attachment);

	public List<Attachment> findAttachmentByDraftId(String draftId);

	public Attachment findAttachmentByAttachmentId(String attachmentId);

}
