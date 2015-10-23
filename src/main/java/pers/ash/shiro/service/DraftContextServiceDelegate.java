package pers.ash.shiro.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

import pers.ash.shiro.vo.DraftVo;

@Service
public class DraftContextServiceDelegate implements JavaDelegate{

	private Expression expression;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		DraftVo draftVo = (DraftVo) execution.getVariable("draftVo");
		Object obj = expression.getValue(execution);
		if(obj instanceof DraftContextService){
			DraftContextService draftContextService = (DraftContextService) obj;
			draftContextService.setDraftVo(draftVo);
			draftContextService.initalize();
			draftContextService.handleRequest();
		}
	}

	public Expression getExpression() {
		return expression;
	}

	public void setExpression(Expression expression) {
		this.expression = expression;
	}
	
}
