package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ash.shiro.mapper.draft.DraftMapper;
import pers.ash.shiro.model.draft.Draft;
import pers.ash.shiro.model.draft.DraftExample;
import pers.ash.shiro.model.draft.state.DraftState;
import pers.ash.shiro.service.DraftService;
import pers.ash.shiro.util.UUIDUtils;

@Service
public class DraftServiceImpl implements DraftService {

	@Autowired
	private DraftMapper draftMapper;
	
	@Override
	public void addDraft(Draft draft) {
		testValidity(draft);
		if(null == draft.getId()){
			draft.setId(UUIDUtils.createUUID());
		}
		draftMapper.insert(draft);
	}

	@Override
	public void deleteDraft(String draftId) {
		draftMapper.deleteByPrimaryKey(draftId);
	}

	@Override
	public void updateDraft(Draft draft) {
		draftMapper.updateByPrimaryKey(draft);
	}
	
	@Override
	public void saveOrUpdate(Draft draft){
		testValidity(draft);
		if(null == draft.getId() || null == draftMapper.selectByPrimaryKey(draft.getId())){
			draft.setId(UUIDUtils.createUUID());
			draftMapper.insert(draft);
		}else{
			draftMapper.updateByPrimaryKey(draft);
		}
	}

	@Override
	public List<Draft> findDraftByAuthorId(String authorId) {
		DraftExample draftExample = new DraftExample();
		draftExample.createCriteria().andAuthorIdEqualTo(authorId);
		return draftMapper.selectByExample(draftExample);
	}
	
	@Override
	public List<Draft> findDraftByAuditorId(String auditorId) {
		DraftExample draftExample = new DraftExample();
		draftExample.createCriteria().andAuditorIdEqualTo(auditorId);
		return draftMapper.selectByExample(draftExample);
	}
	
	@Override
	public Draft findDraftByDraftId(String draftId) {
		return draftMapper.selectByPrimaryKey(draftId);
	}

	@Override
	public List<Draft> findDraftByState(DraftState draftState) {
		DraftExample draftExample = new DraftExample();
		draftExample.createCriteria().andCurrentStateEqualTo(draftState.name());
		return draftMapper.selectByExample(draftExample);
	}
	
	public void testValidity(Draft draft){
		if(null == draft){
			return;
		}
	}

}
