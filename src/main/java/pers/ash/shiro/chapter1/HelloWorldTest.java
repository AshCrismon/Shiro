package pers.ash.shiro.chapter1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class HelloWorldTest {

	Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(
			"classpath:shiro-jdbc-realm.ini");
	org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
	
	@Test
	public void test(){
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken("ash", "712513");
		
		try{
			subject.login(token);
		}catch(AuthenticationException e){
			e.printStackTrace();
			throw new AuthenticationException("用户名和密码不匹配");
		}
	}
}
