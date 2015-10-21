package pers.ash.activiti;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

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

public class ActivitiStartTest extends AbstractTransactionalConfig {

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
		deploy(new String[] { "workflow/auditAndApprove.bpmn", "workflow/auditAndApprove.png" }, "审批流转");
		draftVo.setCommitType("1");
		draftVo.setAuthorId("zhangsan");
	}
	
	@Test
	// @Rollback(false)
	public void testDeploy() {
		System.out.println("部署工作流ID：------------>" + deployment.getId());
		System.out.println("部署工作流名称：------------>" + deployment.getName());
	}

	@Test
	public void testStartProcess() {
		ProcessInstance processInstance = startProcess("auditAndApprove");
		System.out.println("工作流实例ID：------------>" + processInstance.getId());
		System.out.println("工作流定义ID：------------>" + processInstance.getProcessDefinitionId());
	}
	
	@Test
	public void testExecuteTask(){
		deploy(new String[] { "workflow/auditAndApprove.bpmn", "workflow/auditAndApprove.png" }, "审批流转");
		startProcess("auditAndApprove");
//		completeTask();
	}

	public void test() {
		
		Task task = taskService.createTaskQuery().singleResult();
		// assertEquals("auditAndApprove", task.getName());

		taskService.complete(task.getId());
		assertEquals(0, runtimeService.createProcessInstanceQuery().count());
	}

	public void deploy(String[] resources, String deployName) {
		deployment = repositoryService.createDeployment()//
				.addClasspathResource(resources[0])//
				.addClasspathResource(resources[1])//
				.name(deployName).deploy();
	}
	
	public ProcessInstance startProcess(String processDefinitionKey) {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("authorId", draftVo.getAuthorId());
		return runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
	}
	
	public void assignTask(String taskId, String userId){
		
		taskService.setAssignee(taskId, userId);
	}
	
	public void findTasksOfAssignee(String userId){
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		for(Task task : tasks){
			System.out.println("  ProcessInstanceId	-------->" + task.getProcessInstanceId());
			System.out.println("ProcessDefinitionId	-------->" + task.getProcessDefinitionId());
			System.out.println("				 Id	-------->" + task.getId());
			System.out.println("	  	ExecutionId	-------->" + task.getExecutionId());
			System.out.println("			   Name	-------->" + task.getName());
			System.out.println("			  Owner	-------->" + task.getOwner());
			System.out.println("	    Description	-------->" + task.getDescription());
			System.out.println("	     CreateTime	-------->" + task.getCreateTime());
		}
	}

	public void completeCommitTask(String taskId, DraftVo draftVo){
		taskService.createTaskQuery();
//		taskService.complete(taskId);
	}
	
	@Test
	public void testAuditAndApprove(){
		ProcessInstance processInstance = startProcess("auditAndApprove");
		assignTask(processInstance.getActivityId(), "zhangsan");
		findTasksOfAssignee("zhangsan");
	}

	@Test
	@Rollback(false)
	public void TestDeleteDeploy() throws Exception {
		repositoryService.deleteDeployment("3101", true);
	}
}
