package pers.ash.shiro.helper;

import org.junit.Assert;
import org.junit.Test;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.systemmanage.User;

public class PasswordHelperTest extends AbstractTransactionalConfig{
	
	@Test
	public void testEncrypt(){
		User user = new User("zhaosi", "123456");
		PasswordHelper.encrypt(user);
		
		String password = "123456";
		String hashedPassword = PasswordHelper.encrypt(password, user.getSalt());
		
		Assert.assertEquals(user.getPassword(), hashedPassword);
	}
	
	@Test
	public void testEncrypt2(){
		User user = new User("zhaosi", "123456");
		PasswordHelper.encrypt(user);
		
		char[] chs = {'1','2','3','4','5','6'};
		Object obj = chs;
		String password = String.valueOf((char[])obj);
		String hashedPassword = PasswordHelper.encrypt(password, user.getSalt());
		Assert.assertEquals(user.getPassword(), hashedPassword);
	}
	
}
