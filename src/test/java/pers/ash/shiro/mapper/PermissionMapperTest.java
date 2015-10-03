package pers.ash.shiro.mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.mapper.PermissionMapper;
import pers.ash.shiro.model.Permission;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class PermissionMapperTest extends AbstractTransactionalConfig {

	@Autowired
	private PermissionMapper permissionMapper;

	private List<Permission> permissions = new ArrayList<Permission>();

	@Before
	public void init() {
		clear();
		addPermissions();
	}

	@Test
	public void testAdd() {
		Permission permission = new Permission("权限-1", "测试用例-权限1");
		int affectedRows = permissionMapper.add(permission);
		Assert.assertEquals(1, affectedRows);
	}

	@Test
	public void testDelete() {
		for (int i = 0; i < permissions.size(); i++) {
			int affectedRows = permissionMapper.delete(permissions.get(i)
					.getId());
			Assert.assertEquals(1, affectedRows);
		}
	}

	@Test
	public void testUpdate() {
		for (int i = 0; i < permissions.size(); i++) {
			Permission permission = permissions.get(i);
			permission.setName("新权限名");
			permission.setDescription("权限描述信息");
			int affectedRows = permissionMapper.update(permission);
			Assert.assertEquals(1, affectedRows);
		}
	}

	@Test
	public void testFindPermissionById() {
		for (int i = 0; i < permissions.size(); i++) {
			Permission permission = permissionMapper.findById(permissions
					.get(i).getId());
			Assert.assertNotNull(permission);
		}
	}

	@Test
	// @Rollback(false)
	public void testFindAllPermissions() {
		List<Permission> permissions = permissionMapper.findAll();
		Assert.assertEquals(5, permissions.size());
	}

	@Test
	public void testFindPage() {
		PageHelper.startPage(0, 10);
		List<Permission> permissions = permissionMapper.findAll();
		Assert.assertTrue(permissions.size() <= 10);
	}

	@Test
	public void testFindPageInfo() {
		PageHelper.startPage(1, 10);
		List<Permission> permissions = permissionMapper.findAll();
		PageInfo<Permission> pageInfo = new PageInfo<Permission>(permissions);
		System.out.println("total pages : " + pageInfo.getPages());
		System.out.println("total rows : " + pageInfo.getTotal());
		System.out.println("current page size : " + pageInfo.getSize());
		System.out.println("pagination : " + pageInfo.getPageNum());
		System.out.println("previous pagination : " + pageInfo.getPrePage());
		System.out.println("next pagination : " + pageInfo.getNextPage());
		System.out.println("first pagination : " + pageInfo.getFirstPage());
		System.out.println("last pagination : " + pageInfo.getLastPage());
	}

	public void clear() {
		permissionMapper.deleteAll();
	}

	public void addPermissions() {
		addPermission("测试权限-1", "测试用例-权限1");
		addPermission("测试权限-2", "测试用例-权限2");
		addPermission("测试权限-3", "测试用例-权限3");
		addPermission("测试权限-4", "测试用例-权限4");
		addPermission("测试权限-5", "测试用例-权限5");
	}

	public void addPermission(String name, String description) {
		Permission permission = new Permission();
		permission.setName(name);
		permission.setDescription(description);
		permissionMapper.add(permission);
		permissions.add(permission);
	}
}
