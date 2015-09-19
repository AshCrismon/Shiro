package cn.ash.shiro.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ash.shiro.abstractTest.AbstractTransactionalTest;
import cn.ash.shiro.model.Permission;
import cn.ash.shiro.util.UUIDUtils;

public class PermissionMapperTest extends AbstractTransactionalTest{

	@Autowired
	private PermissionMapper permissionMapper;
	
	public void init(){
		add("获取系统用户列表", "权限描述信息");
		add("获取普通用户列表", "权限描述信息");
		add("获取所有用户列表", "权限描述信息");
		add("获取vip用户列表", "权限描述信息");
	}
	
	@Test
	public void testFindPermissionById(){
		String id = add("获取用户角色", "权限描述信息");
		Permission permission = permissionMapper.findById(id);
		Assert.assertEquals(true, permission != null);
	}
	
	@Test
//	@Rollback(false)
	public void testFindAllPermissions(){
		init();
		List<Permission> permissions = permissionMapper.findAll();
		Assert.assertEquals(true, !permissions.isEmpty());
	}
	
	@Test
	public void testFindPage() {
		PageHelper.startPage(0, 10);
		List<Permission> permissions = permissionMapper.findAll();
		Assert.assertEquals(true, permissions.size() <= 10);
	}
	
	@Test
	public void testFindPageInfo(){
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
	
	@Test
	public void testAdd(){
		String id = add("获取用户角色", "权限描述信息");
		Assert.assertEquals(true, id != null);
	}
	
	@Test
	public void testDelete(){
		String id = add("获取用户角色", "权限描述信息");
		int affectedRows = permissionMapper.delete(id);
		Assert.assertEquals(true, affectedRows == 1);
	}
	
	@Test
	public void testUpdate(){
		String id = add("获取用户角色", "权限描述信息");
		Permission permission = permissionMapper.findById(id);
		permission.setName("获取用户角色123");
		permission.setDescription("权限描述信息123");
		int affectedRows = permissionMapper.update(permission);
		Assert.assertEquals(true, affectedRows == 1);
	}
	
	public String add(String name, String description){
		Permission permission = new Permission();
		permission.setId(UUIDUtils.createUUID());
		permission.setName(name);
		permission.setDescription(description);
		int affectedRows = permissionMapper.add(permission);
		return affectedRows == 1 ? permission.getId() : null;
	}
	
}
