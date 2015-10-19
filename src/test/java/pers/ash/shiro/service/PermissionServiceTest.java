package pers.ash.shiro.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.helper.ModelHelper;
import pers.ash.shiro.model.ModelState;
import pers.ash.shiro.model.system.Permission;

public class PermissionServiceTest extends AbstractTransactionalConfig{
	
	@Autowired
	private PermissionService permissionService;
	
	@Test
	public void testCreatePermission(){
		Permission permission = new Permission("权限一", "/controller/user/test-x.do", "测试用例-权限一");
		permissionService.createPermission(permission);
		Assert.assertNotNull(permissionService.findByPermissionId(permission.getId()));
	}
	
	@Test
	public void testDeletePermission(){
		Permission permission = new Permission("权限一", "/controller/user/test-x.do", "测试用例-权限一");
		permissionService.createPermission(permission);
		//锁定权限
		ModelHelper.setState(ModelState.LOCKED);
		permissionService.deletePermission(permission.getId());
		permission = permissionService.findByPermissionId(permission.getId());
		Assert.assertEquals(ModelState.LOCKED, permission.getState());
		
		//移入回收站
		ModelHelper.setState(ModelState.REMOVE);
		permissionService.deletePermission(permission.getId());
		Assert.assertEquals(ModelState.REMOVE, permissionService.findByPermissionId(permission.getId()).getState());
		
		//彻底删除
		ModelHelper.setState(ModelState.DELETE);
		permissionService.deletePermission(permission.getId());
		Assert.assertNull(permissionService.findByPermissionId(permission.getId()));
	}
	
	@Test
	public void testFindByPermissionId(){
		Permission permission = new Permission("权限一", "/controller/user/test-x.do", "测试用例-权限一");
		permissionService.createPermission(permission);
		Assert.assertNotNull(permissionService.findByPermissionId(permission.getId()));
	}
	
	@Test
	public void testFindByPermissionName(){
		Permission permission = new Permission("权限一", "/controller/user/test-x.do", "测试用例-权限一");
		permissionService.createPermission(permission);
		Assert.assertNotNull(permissionService.findByPermissionName(permission.getName()));
	}
}
