package pers.ash.shiro.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.mapper.RoleMapper;
import pers.ash.shiro.model.Permission;
import pers.ash.shiro.model.Role;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class RoleMapperTest extends AbstractTransactionalConfig {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;

	private List<Role> roles = new ArrayList<Role>();
	private List<Permission> permissions = new ArrayList<Permission>();

	@Before
	public void init() {
		clear();
		addRoles();
		addPermissions();
	}

	@Test
	public void testAdd() {
		Role role = new Role("Rose", "普通用户");
		int affectedRows = roleMapper.add(role);
		Assert.assertEquals(1, affectedRows);
	}

	@Test
	public void testDelete() {
		for (int i = 0; i < roles.size(); i++) {
			int affectedRows = roleMapper.delete(roles.get(i).getId());
			Assert.assertEquals(1, affectedRows);
		}
	}

	@Test
	public void testUpdate() {
		for (int i = 0; i < roles.size(); i++) {
			Role role = roleMapper.findById(roles.get(i).getId());
			role.setName("新角色名");
			role.setDescription("vip用户");
			int affectedRows = roleMapper.update(role);
			Assert.assertEquals(true, affectedRows == 1);
		}
	}

	@Test
	public void testFindByRoleId() {
		for (int i = 0; i < roles.size(); i++) {
			Role role = roleMapper.findById(roles.get(i).getId());
			Assert.assertNotNull(role);
		}
	}

	@Test
	public void testFindByRoleName() {
		for (int i = 0; i < roles.size(); i++) {
			Role role = roleMapper.findByRoleName(roles.get(i).getName());
			Assert.assertNotNull(role);
		}
	}

	@Test
	// @Rollback(false)
	public void testFindAllRoles() {
		List<Role> roles = roleMapper.findAll();
		Assert.assertEquals(5, roles.size());
	}

	@Test
	public void testFindPage() {
		PageHelper.startPage(1, 10);
		List<Role> roles = roleMapper.findAll();
		Assert.assertTrue(roles.size() <= 10);
	}

	@Test
	public void testFindPageInfo() {
		PageHelper.startPage(1, 10);
		List<Role> roles = roleMapper.findAll();
		PageInfo<Role> pageInfo = new PageInfo<Role>(roles);
		System.out.println("total pages : " + pageInfo.getPages());
		System.out.println("total rows : " + pageInfo.getTotal());
		System.out.println("current page size : " + pageInfo.getSize());
		System.out.println("pagination : " + pageInfo.getPageNum());
		System.out.println("previous pagination : " + pageInfo.getPrePage());
		System.out.println("next pagination : " + pageInfo.getNextPage());
		System.out.println("first pagination : " + pageInfo.getFirstPage());
		System.out.println("last pagination : " + pageInfo.getLastPage());
	}

	@Test
	public void testCorrelationPermissions() {
		correlationPermissions();
	}

	@Test
	public void testUncorrelationPermissions() {
		correlationPermissions();
		uncorrelationPermissions();
	}

	@Test
	public void testFindRolePermission() {
		correlationPermissions();
		for (int i = 0; i < roles.size(); i++) {
			for (int j = 0; j < permissions.size(); j++) {
				Permission permission = roleMapper.findRolePermission(roles
						.get(i).getId(), permissions.get(j).getId());
				Assert.assertNotNull(permission);
			}
		}
	}

	@Test
	public void testFindPermissions() {
		correlationPermissions();
		for (int i = 0; i < roles.size(); i++) {
			List<Permission> permissions = roleMapper.findPermissions(roles
					.get(i).getId());
			Assert.assertEquals(5, permissions.size());
		}
	}
	
	@Test
	public void testFindStringPermissions() {
		correlationPermissions();
		for (int i = 0; i < roles.size(); i++) {
			List<String> permissions = roleMapper.findStringPermissions(roles
					.get(i).getId());
			Assert.assertEquals(5, permissions.size());
		}
	}

	public void clear() {
		roleMapper.deleteAll();
		permissionMapper.deleteAll();
	}

	public void addRoles() {
		addRole("测试角色-1", "超级用户");
		addRole("测试角色-2", "管理员");
		addRole("测试角色-3", "普通用户");
		addRole("测试角色-4", "系统用户");
		addRole("测试角色-5", "vip用户");
	}

	public void addPermissions() {
		addPermission("测试权限-1", "测试用例-权限1");
		addPermission("测试权限-2", "测试用例-权限2");
		addPermission("测试权限-3", "测试用例-权限3");
		addPermission("测试权限-4", "测试用例-权限4");
		addPermission("测试权限-5", "测试用例-权限5");
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

	public void addRole(String name, String description) {
		Role role = new Role();
		role.setName(name);
		role.setDescription(description);
		roleMapper.add(role);
		roles.add(role);
	}

	public void addPermission(String name, String description) {
		Permission permission = new Permission();
		permission.setName(name);
		permission.setDescription(description);
		permissionMapper.add(permission);
		permissions.add(permission);
	}
}
