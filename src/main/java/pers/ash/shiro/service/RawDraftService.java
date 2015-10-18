package pers.ash.shiro.service;

import pers.ash.shiro.model.draft.RawDraft;

public interface RawDraftService {

	public void addRawDraft(RawDraft rawDraft);

	public void deleteRawDraft(String rawDraftId);

	public void updateRawDraft(RawDraft rawDraft);

	public void saveOrUpdate(RawDraft rawDraft);

	public RawDraft findRawDraftByRawDraftId(String rawDraftId);

}
