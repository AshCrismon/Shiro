package pers.ash.shiro.mapper.system;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.helper.PasswordHelper;
import pers.ash.shiro.mapper.system.PermissionMapper;
import pers.ash.shiro.mapper.system.RoleMapper;
import pers.ash.shiro.mapper.system.UserMapper;
import pers.ash.shiro.model.system.Permission;
import pers.ash.shiro.model.system.Role;
import pers.ash.shiro.model.system.User;
import pers.ash.shiro.util.DateUtils;
import pers.ash.shiro.vo.UserVo;

import com.github.orderbyhelper.OrderByHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class UserMapperTest extends AbstractTransactionalConfig {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;

	private List<User> users = new ArrayList<User>();
	private List<Role> roles = new ArrayList<Role>();
	private List<Permission> permissions = new ArrayList<Permission>();

	@Before
	public void init() {
		clear();
		addUsers();
		addRoles();
		addPermissions();
	}

	@Test
	public void testAdd() {
		User user = new User("测试用户", "bulabulabula");
		int affectedRows = userMapper.add(user);
		Assert.assertEquals(1, affectedRows);
	}

	@Test
	public void testDelete() {
		for (int i = 0; i < users.size(); i++) {
			int affectedRows = userMapper.delete(users.get(i).getId());
			Assert.assertEquals(1, affectedRows);
		}
	}

	@Test
	public void testUpdate() {
		User user = users.get(0);
		user.setUsername("新用户名");
		user.setGender("女");
		user.setPassword("labulabulabu");
		user.setEmail("labulabu@yeah.net");
		int affectedRows = userMapper.update(user);
		Assert.assertEquals(1, affectedRows);
	}

	@Test
	public void testFindByUserId() {
		for (int i = 0; i < users.size(); i++) {
			User user = userMapper.findById(users.get(i).getId());
			Assert.assertNotNull(user);
		}
	}

	@Test
	public void testFindByUsername() {
		for (int i = 0; i < users.size(); i++) {
			User user = userMapper.findByUsername(users.get(i).getUsername());
			Assert.assertNotNull(user);
		}
	}

	@Test
	// @Rollback(false)
	public void testFindAllUsers() {
		List<User> users = userMapper.findAll();
		Assert.assertEquals(5, users.size());
	}

	@Test
	public void testFindPage() {
		PageHelper.startPage(1, 10);
		List<User> users = userMapper.findAll();
		Assert.assertTrue(users.size() <= 10);
	}

	@Test
	public void testFindPageInfo() {
		PageHelper.startPage(1, 10);
		List<User> users = userMapper.findAll();
		PageInfo<User> pageInfo = new PageInfo<User>(users);
		print(pageInfo);
	}

	@Test
	public void testFindByOrder() {
		OrderByHelper.orderBy("CONVERT(username USING gbk) asc");
		List<User> users = userMapper.findAll();
		System.out
				.println("====================order by username asc====================");
		print(users);
		System.out
				.println("=============================================================");
	}

	@Test
	public void testFindByOrder2() {
		OrderByHelper.orderBy("age asc, phone asc");
		List<User> users = userMapper.findAll();
		System.out
				.println("=================order by age asc, phone asc=================");
		print(users);
		System.out
				.println("=============================================================");
	}

	@Test
	public void testFindPageByOrder() {
		PageHelper.startPage(1, 10, "CONVERT(username USING gbk) asc");
		List<User> users = userMapper.findAll();
		System.out
				.println("====================order by username asc====================");
		print(users);
		System.out
				.println("=============================================================");
	}

	@Test
	public void testCorrelationRole() {
		correlationRoles();
	}

	@Test
	public void testUncorrelationRole() {
		correlationRoles();
		uncorrelationRoles();
	}

	@Test
	public void testFindRoles() {
		correlationRoles();
		for (int i = 0; i < users.size(); i++) {
			List<Role> roles = userMapper.findRoles(users.get(i).getId());
			Assert.assertEquals(5, roles.size());
		}
	}
	
	@Test
	public void testFindStringRoles() {
		correlationRoles();
		for (int i = 0; i < users.size(); i++) {
			List<String> roles = userMapper.findStringRoles(users.get(i).getId());
			Assert.assertEquals(5, roles.size());
		}
	}

	@Test
	public void testFindPermissions() {
		correlationRoles();
		correlationPermissions();
		for (int i = 0; i < users.size(); i++) {
			List<Permission> permissions = userMapper.findPermissions(users
					.get(i).getId());
			Assert.assertEquals(25, permissions.size());
		}
	}

	@Test
	public void testFindStringPermissions() {
		correlationRoles();
		correlationPermissions();
		for (int i = 0; i < users.size(); i++) {
			List<String> permissions = userMapper.findStringPermissions(users
					.get(i).getId());
			Assert.assertEquals(25, permissions.size());
		}
	}
	
	@Test
	public void testFindPermissionUris() {
		correlationRoles();
		correlationPermissions();
		for (int i = 0; i < users.size(); i++) {
			List<String> permissions = userMapper.findPermissionUris(users
					.get(i).getId());
			Assert.assertEquals(25, permissions.size());
		}
	}

	@Test
	public void testFindUserRole() {
		correlationRoles();
		for (int i = 0; i < users.size(); i++) {
			for (int j = 0; j < roles.size(); j++) {
				Role role = userMapper.findUserRole(users.get(i).getId(), roles
						.get(j).getId());
				Assert.assertNotNull(role);
			}
		}
	}

	@Test
	public void testFindUserRoles() {
		correlationRoles();
		for (int i = 0; i < users.size(); i++) {
			UserVo userVo = userMapper.findUserRoles(users.get(i).getId());
			Assert.assertEquals(5, userVo.getRoles().size());
		}
	}

	public void print(List<User> users) {
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i));
		}
	}
	
	public <T> void print(PageInfo<T> pageInfo) {
		System.out
				.println("======================page infomation======================");
		System.out.println("total pages : " + pageInfo.getPages());
		System.out.println("total rows : " + pageInfo.getTotal());
		System.out.println("current page size : " + pageInfo.getSize());
		System.out.println("pagination : " + pageInfo.getPageNum());
		System.out.println("previous pagination : " + pageInfo.getPrePage());
		System.out.println("next pagination : " + pageInfo.getNextPage());
		System.out.println("first pagination : " + pageInfo.getFirstPage());
		System.out.println("last pagination : " + pageInfo.getLastPage());
		System.out
				.println("===========================================================");
	}

	public void clear() {
		userMapper.deleteAll();
		roleMapper.deleteAll();
		permissionMapper.deleteAll();
	}

	public void addUsers() {
		addUser("测试用户-1", "000000", 23, "男", "13434488752", "testuser1@163.com");
		addUser("测试用户-2", "000000", 25, "女", "13434486635",
				"testuser2@sina.com");
		addUser("测试用户-3", "000000", 22, "女", "13434487412", "testuser3@qq.com");
		addUser("测试用户-4", "000000", 26, "男", "13434488562",
				"testuser4@gmail.com");
		addUser("测试用户-5", "000000", 33, "女", "13434484265",
				"testuser5@sohu.com");
	}

	public void addRoles() {
		addRole("测试角色-1", "测试用例-角色1");
		addRole("测试角色-2", "测试用例-角色2");
		addRole("测试角色-3", "测试用例-角色3");
		addRole("测试角色-4", "测试用例-角色4");
		addRole("测试角色-5", "测试用例-角色5");
	}

	public void addPermissions() {
		addPermission("测试权限-1", "/controller/user/test-1.do", "测试用例-权限1");
		addPermission("测试权限-2", "/controller/user/test-2.do", "测试用例-权限2");
		addPermission("测试权限-3", "/controller/user/test-3.do", "测试用例-权限3");
		addPermission("测试权限-4", "/controller/user/test-4.do", "测试用例-权限4");
		addPermission("测试权限-5", "/controller/user/test-5.do", "测试用例-权限5");
	}

	public void correlationRoles() {
		for (int i = 0; i < users.size(); i++) {
			for (int j = 0; j < roles.size(); j++) {
				int affectedRows = userMapper.correlationRole(users.get(i)
						.getId(), roles.get(j).getId());
				Assert.assertEquals(1, affectedRows);
			}
		}
	}

	public void uncorrelationRoles() {
		for (int i = 0; i < users.size(); i++) {
			for (int j = 0; j < roles.size(); j++) {
				int affectedRows = userMapper.uncorrelationRole(users.get(i)
						.getId(), roles.get(j).getId());
				Assert.assertEquals(1, affectedRows);
			}
		}
	}

	public void correlationPermissions() {
		for (int i = 0; i < roles.size(); i++) {
			for (int j = 0; j < permissions.size(); j++) {
				int affectedRows = roleMapper.correlationPermission(roles
						.get(i).getId(), permissions.get(j).getId());
				Assert.assertEquals(1, affectedRows);
			}
		}
	}

	public void uncorrelationPermissions() {
		for (int i = 0; i < roles.size(); i++) {
			for (int j = 0; j < permissions.size(); j++) {
				int affectedRows = roleMapper.uncorrelationPermission(roles
						.get(i).getId(), permissions.get(j).getId());
				Assert.assertEquals(1, affectedRows);
			}
		}
	}

	public void addUser(String username, String password, int age,
			String gender, String phone, String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setAge(age);
		user.setCreateDate(DateUtils.now());
		user.setGender(gender);
		user.setPhone(phone);
		user.setEmail(email);
		PasswordHelper.encrypt(user);
		userMapper.add(user);
		users.add(user);
	}

	public void addRole(String name, String description) {
		Role role = new Role();
		role.setName(name);
		role.setDescription(description);
		roleMapper.add(role);
		roles.add(role);
	}

	public void addPermission(String name, String permissionUri, String description) {
		Permission permission = new Permission();
		permission.setName(name);
		permission.setPermissionUri(permissionUri);
		permission.setDescription(description);
		permissionMapper.add(permission);
		permissions.add(permission);
	}
}
