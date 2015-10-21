package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ash.shiro.mapper.draftaudit.AttachmentMapper;
import pers.ash.shiro.model.draftaudit.Attachment;
import pers.ash.shiro.model.draftaudit.AttachmentExample;
import pers.ash.shiro.service.AttachmentService;
import pers.ash.shiro.util.UUIDUtils;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	private AttachmentMapper attachmentMapper;
	
	@Override
	public void addAttachment(Attachment attachment) {
		testValidity(attachment);
		if(null == attachment.getId()){
			attachment.setId(UUIDUtils.createUUID());
		}
		attachmentMapper.insert(attachment);
	}

	@Override
	public void deleteAttachment(String attachmentId) {
		attachmentMapper.deleteByPrimaryKey(attachmentId);
	}

	@Override
	public void updateAttachment(Attachment attachment) {
		testValidity(attachment);
		attachmentMapper.updateByPrimaryKey(attachment);
	}

	@Override
	public void saveOrUpdate(Attachment attachment) {
		testValidity(attachment);
		if(null == attachment.getId() || null == attachmentMapper.selectByPrimaryKey(attachment.getId())){
			attachment.setId(UUIDUtils.createUUID());
			attachmentMapper.insert(attachment);
		}else{
			attachmentMapper.updateByPrimaryKey(attachment);
		}
	}

	@Override
	public List<Attachment> findAttachmentByDraftId(String draftId) {
		AttachmentExample attachmentExample = new AttachmentExample();
		attachmentExample.createCriteria().andDraftIdEqualTo(draftId);
		return attachmentMapper.selectByExample(attachmentExample);
	}

	@Override
	public Attachment findAttachmentByAttachmentId(String attachmentId) {
		return attachmentMapper.selectByPrimaryKey(attachmentId);
	}
	
	public void testValidity(Attachment attachment){
		if(null == attachment){
			return;
		}
	}

}
