package pers.ash.shiro.service;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.ModelState;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.util.DateUtils;
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
	public void testCreateUser2(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		userService.createUser(user);
	}
	
	@Test
	public void testDeleteUser() throws ParseException{
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		
		//锁定用户
		ModelHelper.setState(ModelState.LOCKED);
		userService.deleteUser(user.getId());
		user = userService.findByUserId(user.getId());
		Assert.assertEquals(ModelState.LOCKED, user.getState());
		
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
	public void testUpdateUser(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		
		user = userService.findByUserId(user.getId());
		user.setUsername("七七");
		user.setAge(24);
		user.setEmail("qiqi@sina.com");
		user.setPhone("15487456214");
		userService.updateUser(user);
		
		user = userService.findByUserId(user.getId());
		Assert.assertEquals("七七", user.getUsername());
		Assert.assertEquals(24, user.getAge().intValue());
		Assert.assertEquals("qiqi@sina.com", user.getEmail());
		Assert.assertEquals("15487456214", user.getPhone());
	}
	
	@Test
	public void testChangePassword(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		userService.changePassword(user.getId(), "998877445566");
	}
	
	@Test(expected = EntityNotFoundException.class)
	public void testChangePasswordException(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.changePassword(user.getId(), "998877445566");
	}
	
	@Test
	public void testCorrelationRoles(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		Role role1 = new Role("测试用户1", "测试用例-普通用户");
		Role role2 = new Role("测试用户2", "测试用例-系统用户");
		Role role3 = new Role("测试用户3", "测试用例-VIP用户");
		roleService.createRole(role1, role2, role3);
		userService.correlationRoles(user.getId(), role1.getId(), role2.getId(), role3.getId());
		Assert.assertEquals(3, userService.findRoles(user.getId()).size());
	}
	
	public User createUser(String username, String password, int age,
			String gender, String phone, String email){
		User user = new User();
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
