package pers.ash.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.model.User;

@WebAppConfiguration
public class UserControllerTest extends AbstractTransactionalConfig {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Autowired
	private org.apache.shiro.mgt.SecurityManager securityManager;
	private Subject subject;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(wac).build();
	}

	@Test
	public void testFindAllUsers() throws Exception {
		mockMvc.perform(
				get("/user/findAllUsers").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testFindRoles() throws Exception {
		mockMvc.perform(
				get("/user/findRoles/5ae2baf4f2dd436b9395309a16a40816").accept(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void testCreateUser() throws Exception {
		User user = new User(null, "xiaoxiao654321");
		mockMvc.perform(
				post("/user").accept(MediaType.APPLICATION_JSON)
						.param("username", user.getUsername())
						.param("password", "").param("email", user.getEmail()))
				.andExpect(status().isInternalServerError()).andDo(print());
	}

	@Test
	public void testUpdateUser() throws Exception {
		User user = new User();
		user.setId("5ae2baf4f2dd436b9395309a16a40816");
		user.setUsername("阿修");
		user.setPassword("9988xxhhyy");
		user.setEmail("xxhhyy@ww.com");
		user.setPhone("14536784321");
		mockMvc.perform(
				put("/user").accept(MediaType.APPLICATION_JSON)
						.param("id", user.getId())
						.param("username", user.getUsername())
						.param("password", user.getPassword())
						.param("email", user.getEmail())
						.param("phone", user.getPhone()))
				.andExpect(status().isOk()).andDo(print());
	}

	@Test
	public void testDeleteUser() throws Exception {
		mockMvc.perform(
				delete("/user/5ae2baf4f2dd436b9395309a16a40816").accept(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Test
	public void testFindUser() throws Exception {
		mockMvc.perform(
				get("/user/5ae2baf4f2dd436b9395309a16a40816").accept(
						MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andDo(print());
	}

	@Before
	public void init() {
		SecurityUtils.setSecurityManager(securityManager);
		subject = SecurityUtils.getSubject();
	}

	@Test
	public void testLogin() throws Exception {
		String username = "测试用户-1";
		String password = "123456";
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		try {
			subject.login(token);
		} catch (UnknownAccountException ex) {
			System.out.println(ex.getMessage());
		} catch (IncorrectCredentialsException ex) {
			System.out.println(ex.getMessage());
		} catch (LockedAccountException ex) {
			System.out.println(ex.getMessage());
		}
		Assert.assertTrue(subject.isAuthenticated());
	}

//	@Test
	public void testLogin2() throws Exception {
		String username = "测试用户-1";
		String password = "123456";
		mockMvc.perform(
				post("/user/admin/login.html").param("username", username)
						.param("password", password)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(redirectedUrl("/user/admin/index.html"))
				.andDo(print());
		Assert.assertTrue(subject.isAuthenticated());
	}

	@Test
	public void testPermissionFilter() {
		String username = "测试用户-1";
		String password = "123456";
		UsernamePasswordToken token = new UsernamePasswordToken(username,
				password);
		subject.login(token);
		Assert.assertTrue(subject.isAuthenticated());

		String testPermission1 = "12a523b34db644e990a26410e6583382";
		String testPermission2 = "7a41cde8ad224832aa4d3c0bf4052ea0";
		String testPermission3 = "28dc0f19adac4451be22b76a457e721b";
		String testPermission4 = "bc70c65dfa9e4e31adc2db08364b15f0";
		String testPermission5 = "54a4d999420148c6a57b5a49e2a92686";

		Assert.assertTrue(subject.isPermitted(testPermission1));
		Assert.assertTrue(subject.isPermitted(testPermission2));
		Assert.assertFalse(subject.isPermitted(testPermission3));
		Assert.assertTrue(subject.isPermitted(testPermission4));
		Assert.assertTrue(subject.isPermitted(testPermission5));
	}
}
