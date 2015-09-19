package pers.ash.shiro.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.abstractTest.AbstractTransactionalTest;
import pers.ash.shiro.mapper.RoleMapper;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.util.UUIDUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


public class RoleMapperTest extends AbstractTransactionalTest{
	
	@Autowired
	private RoleMapper roleMapper;
	
	public void init(){
		add("root", "超级用户");
		add("admin", "管理员");
		add("Jack", "普通用户");
		add("sys_user", "系统用户");
		add("vip_user", "vip用户");
	}
	
	@Test
	public void testFindRoleById(){
		String id = add("Rose", "普通用户");
		Role role = roleMapper.findById(id);
		Assert.assertEquals(true, role != null);
	}
	
	@Test
//	@Rollback(false)
	public void testFindAllRoles(){
		init();
		List<Role> roles = roleMapper.findAll();
		Assert.assertEquals(true, !roles.isEmpty());
	}
	
	@Test
	public void testFindPage() {
		PageHelper.startPage(0, 10);
		List<Role> roles = roleMapper.findAll();
		Assert.assertEquals(true, roles.size() <= 10);
	}
	
	@Test
	public void testFindPageInfo(){
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
	public void testAdd(){
		String id = add("Rose", "普通用户");
		Assert.assertEquals(true, id != null);
	}
	
	@Test
	public void testDelete(){
		String id = add("Rose", "普通用户");
		int affectedRows = roleMapper.delete(id);
		Assert.assertEquals(true, affectedRows == 1);
	}
	
	@Test
	public void testUpdate(){
		String id = add("Rose", "普通用户");
		Role role = roleMapper.findById(id);
		role.setName("Role123");
		role.setDescription("vip用户");
		int affectedRows = roleMapper.update(role);
		Assert.assertEquals(true, affectedRows == 1);
	}
	
	public String add(String name, String description){
		Role role = new Role();
		role.setId(UUIDUtils.createUUID());
		role.setName(name);
		role.setDescription(description);
		int affectedRows = roleMapper.add(role);
		return affectedRows == 1 ? role.getId() : null;
	}
}
