package pers.ash.shiro.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.exception.DuplicationException;
import pers.ash.shiro.model.systemmanage.Role;

public class RoleServiceTest extends AbstractTransactionalConfig{
	
	@Autowired
	private RoleService roleService;
	
	@Test
//	@Rollback(false)
	public void testCreateRole(){
		Role role = new Role("角色1", "测试用例-角色1");
		roleService.createRole(role);
		Assert.assertNotNull(roleService.findByRoleId(role.getId()));
	}
	
	@Test(expected = DuplicationException.class)
	public void testCreateRole2(){
		Role role = new Role("角色1", "测试用例-角色1");
		roleService.createRole(role);
		roleService.createRole(role);
	}
	
}
