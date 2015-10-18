package pers.ash.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ash.shiro.mapper.draft.RawDraftMapper;
import pers.ash.shiro.model.draft.RawDraft;
import pers.ash.shiro.service.RawDraftService;
import pers.ash.shiro.util.UUIDUtils;

@Service
public class RawDraftServiceImpl implements RawDraftService {

	@Autowired
	private RawDraftMapper rawDraftMapper;
	
	@Override
	public void addRawDraft(RawDraft rawDraft) {
		testValidity(rawDraft);
		if(null == rawDraft.getId()){
			rawDraft.setId(UUIDUtils.createUUID());
		}
		rawDraftMapper.insert(rawDraft);
	}

	@Override
	public void deleteRawDraft(String rawDraftId) {
		rawDraftMapper.deleteByPrimaryKey(rawDraftId);
	}

	@Override
	public void updateRawDraft(RawDraft rawDraft) {
		rawDraftMapper.updateByPrimaryKey(rawDraft);
	}

	@Override
	public void saveOrUpdate(RawDraft rawDraft) {
		testValidity(rawDraft);
		if(null == rawDraft.getId() || null == rawDraftMapper.selectByPrimaryKey(rawDraft.getId())){
			rawDraft.setId(UUIDUtils.createUUID());
			rawDraftMapper.insert(rawDraft);
		}else{
			rawDraftMapper.updateByPrimaryKey(rawDraft);
		}
	}

	@Override
	public RawDraft findRawDraftByRawDraftId(String rawDraftId) {
		return rawDraftMapper.selectByPrimaryKey(rawDraftId);
	}
	
	public void testValidity(RawDraft rawDraft){
		if(null == rawDraft){
			return;
		}
	}

}
