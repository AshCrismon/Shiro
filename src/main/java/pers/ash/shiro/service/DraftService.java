package pers.ash.shiro.service;

import java.util.List;

import pers.ash.shiro.model.draft.Draft;
import pers.ash.shiro.model.draft.state.DraftState;

public interface DraftService {
	
	public void addDraft(Draft draft);
	
	public void deleteDraft(String draftId);
	
	public void updateDraft(Draft draft);
	
	public void saveOrUpdate(Draft draft);
	
	public List<Draft> findDraftByAuthorId(String authorId);
	
	public List<Draft> findDraftByAuditorId(String auditorId);
	
	public Draft findDraftByDraftId(String draftId);
	
	public List<Draft> findDraftByState(DraftState draftState);
	
}
