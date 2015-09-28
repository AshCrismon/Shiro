package pers.ash.shiro.helper;

import org.junit.Test;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.User;

public class PasswordHelperTest extends AbstractTransactionalConfig{
	
	@Test
	public void testEncrypt(){
		User user = new User("zhaosi", "123456");
		PasswordHelper.encrypt(user);
		System.out.println("密码(md5+salt)：" + user.getPassword());
	}
}
