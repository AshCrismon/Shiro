package pers.ash.shiro.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.User;
import pers.ash.shiro.util.DateUtils;
import pers.ash.shiro.util.UUIDUtils;
import pers.ash.shiro.exception.*;

public class UserServiceTest extends AbstractTransactionalConfig{
	
	@Autowired
	private UserService userService;
	
	@Test(expected = DuplicateNameException.class)
	public void testCreateUser(){
		User user = createUser("克丽丝","123456",23,"女","13434477752","cris@163.com");
		userService.createUser(user);
		userService.createUser(user);
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
