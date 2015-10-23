package pers.ash.activiti;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.vo.DraftVo;

public class AuditAndApproveTest extends AbstractTransactionalConfig {

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	@Autowired
	@Rule
	public ActivitiRule activitiRule;

	private org.activiti.engine.repository.Deployment deployment;
	private DraftVo draftVo = new DraftVo();
	
	@Before
	public void deploy(){
		deploy(new String[] { "workflow2/auditAndApprove.bpmn", "workflow2/auditAndApprove.png" }, "审批流转");
		draftVo.setAuthorId("zhangsan");
	}
	
	@Test
	public void testDeploy() {
		System.out.println("部署工作流ID：------------>" + deployment.getId());
		System.out.println("部署工作流名称：------------>" + deployment.getName());
	}

	@Test
	public void testStartProcess() {
		ProcessInstance processInstance = startProcess("auditAndApprove", draftVo.getAuthorId());
		System.out.println("工作流实例ID：------------>" + processInstance.getId());
		System.out.println("工作流定义ID：------------>" + processInstance.getProcessDefinitionId());
	}
	
	@Test
	public void testQueryTasks(){
		startProcess("auditAndApprove", draftVo.getAuthorId());	//----->启动流程实例并为开始任务分派代理人
		printTasksOfAssignee("zhangsan");						//----->查找指定用户的任务列表
	}
	
	@Test
	public void testQueryTasks2(){
		startProcess("auditAndApprove", draftVo.getAuthorId());	//----->启动流程实例并为开始任务分派代理人
		startProcess("auditAndApprove", draftVo.getAuthorId());	//----->启动流程实例并为开始任务分派代理人
		startProcess("auditAndApprove", draftVo.getAuthorId());	//----->启动流程实例并为开始任务分派代理人
		printTasksOfAssignee("zhangsan");						//----->查找指定用户的任务列表
	}
	
	/**
	 * 拟稿人：【张三】; 
	 * 审核人：【李四】; 
	 * 会签人：【王五1,王五2,王五3】;
	 * 签发人：【赵六】;
	 * 编号人：【周七】;
	 * 打印人：【胡八】;
	 */
	@Test
	public void testAuditAndApprove(){
		String author = "张三";
		String auditor = "李四";
		String[] countersigners = {"王五1", "王五2", "王五3"};
		String issuer = "赵六";
		String numberPerson = "周七";
		String printer = "胡八";
		Task taskOfAuthor, taskOfAuditor, taskOfIssuer, taskOfNumberPerson, taskOfPrinter;
		Task[] taskOfCountersigners = new Task[3];
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		//1.初始化：启动流程实例，指定开始任务代理人:张三
		startProcess("auditAndApprove", author);
		taskOfAuthor = queryTaskOf(author);
		assertEquals("拟稿人拟稿", taskOfAuthor.getName());
		
		//2.张三拟稿，然后提交【李四】审阅
		//【拟稿人拟稿】->【提交审阅】,注意serviceTask会在userTask执行完之后直接执行
		taskOfAuthor = queryTaskOf(author);
		taskOfAuditor = queryTaskOf(auditor);
		
		draftVo.setTitle("文稿标题一");
		draftVo.setContent("文稿内容");
		draftVo.setRemark("文稿备注");
		draftVo.setAuthorId("拟稿人张三");
		draftVo.setCommitType("1");
		draftVo.setCommitPersonIds(new String[]{auditor});
		taskService.setVariable(taskOfAuthor.getId(), "draftVo", draftVo);
		
		variables.put("auditorId", auditor);
		variables.put("commitType", "1");
		taskService.complete(taskOfAuthor.getId(), variables);
		
		taskOfAuthor = queryTaskOf(author);
		taskOfAuditor = queryTaskOf(auditor);
		assertNull(taskOfAuthor);							//------------->张三提交拟稿后，任务为空
		assertEquals("审核人审阅", taskOfAuditor.getName());	//------------->张三提交拟稿给李四，李四有一个任务
		
		//3.李四提交审阅结果，回到开始任务【拟稿人拟稿】
		taskOfAuthor = queryTaskOf(author);
		taskOfAuditor = queryTaskOf(auditor);
		
		draftVo.setAuditOpinion("满意，通过");
		variables.clear();
		variables.put("draftState", 0);
		taskService.setVariable(taskOfAuditor.getId(), "draftVo", draftVo);
		taskService.complete(taskOfAuditor.getId(), variables);
		
		taskOfAuthor = queryTaskOf(author);
		taskOfAuditor = queryTaskOf(auditor);
		assertNull(taskOfAuditor);							//---------->李四提交审阅结果，任务完成
		assertEquals("拟稿人拟稿", taskOfAuthor.getName());		//---------->张三收到一个新任务
		
		//4.张三继续拟稿，提交【王五1,王五2,王五3】会签
		taskOfAuthor = queryTaskOf(author);
		
		draftVo.setCommitPersonIds(countersigners);
		variables.clear();
		variables.put("commitType", "2");
		variables.put("countersignerIds", Arrays.asList(countersigners));
		taskService.setVariable(taskOfAuthor.getId(), "draftVo", draftVo);
		taskService.complete(taskOfAuthor.getId(), variables);
		
		taskOfAuthor = queryTaskOf(author);
		assertNull(taskOfAuthor);							//---------->张三提交会签，任务完成
		for(int i = 0; i < countersigners.length; i++){		//---------->三个会签人收到一个新任务
			taskOfCountersigners[i] = queryTaskOf(countersigners[i]);
			assertEquals("会签人审阅", taskOfCountersigners[i].getName());
		}
		
		//5.【王五1,王五2,王五3】提交会签结果
		variables.clear();
		variables.put("draftState", 0);
		for(int i = 0; i < countersigners.length; i++){
			taskOfCountersigners[i] = queryTaskOf(countersigners[i]);
			
			taskService.setVariable(taskOfCountersigners[i].getId(), "draftVo", draftVo);
			taskService.complete(taskOfCountersigners[i].getId(), variables);
			
			taskOfCountersigners[i] = queryTaskOf(countersigners[i]);
			assertNull(taskOfCountersigners[i]);			//---------->会签人完成会签任务
		}
		taskOfAuthor = queryTaskOf(author);
		assertEquals("拟稿人拟稿", taskOfAuthor.getName());		//---------->会签结束，张三收到一个新任务
	}
	
	public void deploy(String[] resources, String deployName) {
		deployment = repositoryService.createDeployment()//
				.addClasspathResource(resources[0])//
				.addClasspathResource(resources[1])//
				.name(deployName).deploy();
	}
	
	public ProcessInstance startProcess(String processDefinitionKey, String authorId) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("authorId", authorId);
		return runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
	}
	
	public Task queryTaskOf(String userId){
		return taskService.createTaskQuery().taskAssignee(userId).singleResult();
	}
	
	public void printTasksOfAssignee(String userId){
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		if(null == tasks || tasks.isEmpty()){
			System.out.println("/////////////////////////////////result////////////////////////////////////");
			System.out.println("there not not tasks assigned to user " + userId);
			System.out.println("///////////////////////////////////////////////////////////////////////////");
			return;
		}
		for(Task task : tasks){
			System.out.println("/////////////////////////////////result////////////////////////////////////");
			System.out.println("the tasks assigned to user " + userId + " are as follows : ");
			System.out.println("\tAssignee\t\t	-------->" + task.getAssignee());
			System.out.println("\tProcessInstanceId\t	-------->" + task.getProcessInstanceId());
			System.out.println("\tProcessDefinitionId\t	-------->" + task.getProcessDefinitionId());
			System.out.println("\tId\t\t\t	-------->" + task.getId());
			System.out.println("\tExecutionId\t\t	-------->" + task.getExecutionId());
			System.out.println("\tName\t\t\t	-------->" + task.getName());
			System.out.println("\tOwner\t\t\t	-------->" + task.getOwner());
			System.out.println("\tDescription\t\t	-------->" + task.getDescription());
			System.out.println("\tCreateTime\t\t	-------->" + task.getCreateTime());
			System.out.println("///////////////////////////////////////////////////////////////////////////");
		}
	}

	@Test
	@Rollback(false)
	public void TestDeleteDeploy() throws Exception {
		repositoryService.deleteDeployment("3101", true);
	}
}
