package pers.ash.shiro.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ash.shiro.handler.draftstate.AbstractDraftStateHandler;
import pers.ash.shiro.model.draftaudit.Draft;
import pers.ash.shiro.model.draftaudit.DraftState;
import pers.ash.shiro.service.AttachmentService;
import pers.ash.shiro.service.AuditRecordService;
import pers.ash.shiro.service.DraftService;
import pers.ash.shiro.service.RawDraftService;
import pers.ash.shiro.vo.DraftVo;

@Service
public class DraftContextService {

	//提交过来的数据：draft，提交人，提交方式
	private DraftVo draftVo;
	private AbstractDraftStateHandler draftStateHandler;
	private Map<String, String> stateMapHandler = new HashMap<String, String>();
	
	@Autowired
	private DraftService draftService;
	@Autowired
	private RawDraftService rawDraftService;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private AuditRecordService auditRecordService;
	
	public DraftContextService(){}
	
	public void initalize(){
		initStateMapHandler(); //将DraftState中的状态以key:value形式放进map中：AuditPassed:pers.ash.shiro.model.draft.state.AuditPassedStateHandler
		setDraftStateHandler(draftVo.getId());//根据draft状态设置对应stateHandler
	}
	
	public void handleRequest() throws Exception{
		draftStateHandler.handleDraft(this);
	}
	
	private void initStateMapHandler() {
		String packagePath = "pers.ash.shiro.handler.draftstate."; //stateHandler所在的包
		String suffix = "StateHandler";
		for(DraftState state : DraftState.values()){
			if(!state.name().contains("Stage") && !state.equals(DraftState.UnknowState)){
				String draftStateHandlerClass = packagePath + state.name() + suffix;
				stateMapHandler.put(state.name(), draftStateHandlerClass);
			}
		}
	}
	
	public AbstractDraftStateHandler getDraftStateHandler(){
		return draftStateHandler;
	}
	
	private void setDraftStateHandler(String draftId){
		Draft draft = draftService.findDraftByDraftId(draftId);
		DraftState currentState = DraftState.UnknowState;
		if(null == draft){
			currentState = DraftState.Drafting;
		}else{
			currentState = DraftState.valueOf(draft.getCurrentState());
		}
		try {
			draftStateHandler = (AbstractDraftStateHandler) Class.forName(stateMapHandler.get(currentState.name())).newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public DraftVo getDraftVo() {
		return draftVo;
	}

	public void setDraftVo(DraftVo draftVo) {
		this.draftVo = draftVo;
	}

	public DraftService getDraftService() {
		return draftService;
	}

	public void setDraftService(DraftService draftService) {
		this.draftService = draftService;
	}

	public RawDraftService getRawDraftService() {
		return rawDraftService;
	}

	public void setRawDraftService(RawDraftService rawDraftService) {
		this.rawDraftService = rawDraftService;
	}

	public AttachmentService getAttachmentService() {
		return attachmentService;
	}

	public void setAttachmentService(AttachmentService attachmentService) {
		this.attachmentService = attachmentService;
	}

	public AuditRecordService getAuditRecordService() {
		return auditRecordService;
	}

	public void setAuditRecordService(AuditRecordService auditRecordService) {
		this.auditRecordService = auditRecordService;
	}
	
}
