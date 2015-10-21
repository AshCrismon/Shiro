package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ash.shiro.mapper.draftaudit.RawDraftMapper;
import pers.ash.shiro.model.draftaudit.RawDraft;
import pers.ash.shiro.model.draftaudit.RawDraftExample;
import pers.ash.shiro.service.RawDraftService;
import pers.ash.shiro.util.UUIDUtils;

@Service
public class RawDraftServiceImpl implements RawDraftService {

	@Autowired
	private RawDraftMapper rawDraftMapper;

	@Override
	public void addRawDraft(RawDraft rawDraft) {
		testValidity(rawDraft);
		if (null == rawDraft.getId()) {
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
		if (null == rawDraft.getId()
				|| null == rawDraftMapper.selectByPrimaryKey(rawDraft.getId())) {
			rawDraft.setId(UUIDUtils.createUUID());
			rawDraftMapper.insert(rawDraft);
		} else {
			rawDraftMapper.updateByPrimaryKey(rawDraft);
		}
	}

	@Override
	public RawDraft findRawDraftByRawDraftId(String rawDraftId) {
		return rawDraftMapper.selectByPrimaryKey(rawDraftId);
	}

	@Override
	public RawDraft findRawDraftByDraftId(String draftId) {
		RawDraftExample rawDraftExample = new RawDraftExample();
		rawDraftExample.createCriteria().andDraftIdEqualTo(draftId);
		List<RawDraft> rawDrafts = rawDraftMapper
				.selectByExample(rawDraftExample);
		return !rawDrafts.isEmpty() ? rawDrafts.get(0) : null;
	}

	public void testValidity(RawDraft rawDraft) {
		if (null == rawDraft) {
			return;
		}
	}

}
