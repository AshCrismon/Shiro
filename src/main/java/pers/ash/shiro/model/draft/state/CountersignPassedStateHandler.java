package pers.ash.shiro.model.draft.state;

import pers.ash.shiro.model.draft.Draft;
import pers.ash.shiro.service.DraftContextService;
import pers.ash.shiro.util.DateUtils;

public class CountersignPassedStateHandler extends AbstractDraftStateHandler {

	private String[] validCommitTypes = { "2", "3" }; // 会签通过：可以提交会签,签发

	@Override
	public void handleDraft(DraftContextService draftContext) {
		super.handleDraft(draftContext);
	}

	@Override
	protected void saveOrUpdateDraft() {
		String draftId = draftVo.getId();
		String commitType = draftVo.getCommitType();
		Draft draft = draftService.findDraftByDraftId(draftId);
		draft.setLastUpdateDate(DateUtils.now());
		if (commitType.equals(validCommitTypes[0])) {
			draft.setAuditorTotalCount(draftVo.getCommitPersonIds().length);
			draft.setAuditorPassedCount(0);
			draft.setCurrentState(DraftState.Countersigning.name());
		}
		if (commitType.equals(validCommitTypes[1])) {
			draft.setAuditorId(draftVo.getCommitPersonIds()[0]);//设置当前审阅人：[审核，签发，编号，打印]
			draft.setCurrentState(DraftState.Issuing.name());
		}
		draftService.saveOrUpdate(draft);
	}

	@Override
	public String[] getValidCommitTypes() {
		return validCommitTypes;
	}

}
