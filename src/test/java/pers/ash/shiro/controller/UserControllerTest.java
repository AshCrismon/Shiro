package pers.ash.shiro.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import pers.ash.shiro.config.AbstractTransactionalConfig;

@WebAppConfiguration
public class UserControllerTest extends AbstractTransactionalConfig {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(wac).build();
	}

	@Test
	public void testFindAllUsers() throws Exception {
		mockMvc.perform(
				get("/user/findAllUsers").param("id",
						"019d5d377834411d94d80d9c5de47e0f")).andExpect(
				status().isOk()).andDo(print());
	}
}
