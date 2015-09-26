package pers.ash.shiro.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.ModelState;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.util.DateUtils;
import pers.ash.shiro.util.UUIDUtils;
import pers.ash.shiro.exception.*;
import pers.ash.shiro.helper.ModelHelper;

public class UserServiceTest extends AbstractTransactionalConfig{
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	
	@Test
	public void testCreateUser(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		Assert.assertNotNull(userService.findByUserId(user.getId()));
	}
	
	@Test(expected = DuplicationException.class)
	public void testCreateUserException(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		userService.createUser(user);
	}
	
	@Test
	public void testDeleteUser(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		
		//锁定用户
		ModelHelper.setState(ModelState.LOCKED);
		userService.deleteUser(user.getId());
		Assert.assertEquals(ModelState.LOCKED, userService.findByUserId(user.getId()).getState());
		
		//移入回收站
		ModelHelper.setState(ModelState.REMOVE);
		userService.deleteUser(user.getId());
		Assert.assertEquals(ModelState.REMOVE, userService.findByUserId(user.getId()).getState());
		
		//彻底删除
		ModelHelper.setState(ModelState.DELETE);
		userService.deleteUser(user.getId());
		Assert.assertNull(userService.findByUserId(user.getId()));
	}
	
	@Test
	public void testChangePassword(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		userService.changePassword(user.getId(), "998877445566");
	}
	
	@Test(expected = NullPointerException.class)
	public void testChangePasswordException(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.changePassword(user.getId(), "998877445566");
	}
	
	@Test
	public void testCorrelationRoles(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		Role role1 = new Role(UUIDUtils.createUUID(), "普通用户");
		Role role2 = new Role(UUIDUtils.createUUID(), "系统用户");
		Role role3 = new Role(UUIDUtils.createUUID(), "VIP用户");
//		roleService.add(role1);
//		roleService.add(role2);
//		roleService.add(role3);
	}
	
	public User createUser(String username, String password, int age,
			String gender, String phone, String email){
		User user = new User();
		user.setId(UUIDUtils.createUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setAge(age);
		user.setCreateDate(DateUtils.now());
		user.setGender(gender);
		user.setPhone(phone);
		user.setEmail(email);
		return user;
	}
}
