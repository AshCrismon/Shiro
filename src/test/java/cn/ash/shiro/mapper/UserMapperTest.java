package cn.ash.shiro.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.ash.shiro.abstractTest.AbstractTransactionalTest;
import cn.ash.shiro.model.Role;
import cn.ash.shiro.model.User;
import cn.ash.shiro.model.UserRole;
import cn.ash.shiro.util.DateUtils;
import cn.ash.shiro.util.UUIDUtils;
import cn.ash.shiro.vo.UserVo;

public class UserMapperTest extends AbstractTransactionalTest {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;

	public void init(){
		add("张三","123456",23,"男","13434488752","zhangsan@163.com");
		add("李四","000000",25,"男","13434486635","lisi@163.com");
		add("王五","456789",22,"男","13434487412","wangwu@163.com");
		add("赵六","123789",26,"男","13434488562","zhaoliu@163.com");
		add("周七","456123",33,"男","13434484265","zhouqi@163.com");
	}
	
	@Test
	public void testFindUserById() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		User user = userMapper.findById(id);
		Assert.assertEquals(true, user != null);
	}

	@Test
//	@Rollback(false)
	public void testFindAllUsers() {
		init();
		List<User> users = userMapper.findAll();
		Assert.assertEquals(true, !users.isEmpty());
	}

	@Test
	public void testFindPage() {
		PageHelper.startPage(0, 10);
		List<User> users = userMapper.findAll();
		Assert.assertEquals(true, users.size() <= 10);
	}

	@Test
	public void testFindPageInfo() {
		PageHelper.startPage(1, 10);
		List<User> users = userMapper.findAll();
		PageInfo<User> pageInfo = new PageInfo<User>(users);
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
	public void testAdd() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		Assert.assertEquals(true, id != null);
	}

	@Test
	public void testDelete() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		int affectedRows = userMapper.delete(id);
		Assert.assertEquals(true, affectedRows == 1);
	}

	@Test
	public void testUpdate() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		User user = userMapper.findById(id);
		user.setUsername("Crismon");
		user.setGender("女");
		user.setPassword("987456");
		user.setEmail("qiqi123@yeah.net");
		int affectedRows = userMapper.update(user);
		Assert.assertEquals(true, affectedRows == 1);
	}

	public String add(String username, String password, int age,
			String gender, String phone, String email) {
		User user = new User();
		user.setId(UUIDUtils.createUUID());
		user.setUsername(username);
		user.setPassword(password);
		user.setAge(age);
		user.setCreateDate(DateUtils.now());
		user.setGender(gender);
		user.setPhone(phone);
		user.setEmail(email);
		int affectedRows = userMapper.add(user);
		return affectedRows == 1 ? user.getId() : null;
	}

	@Test
	public void testAssignUserRoles() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		Role role = new Role(UUIDUtils.createUUID(), "普通用户");
		roleMapper.add(role);
		UserRole userRole = new UserRole(id, role.getId());
		int affectedRows = userMapper.assignRoles(userRole);
		Assert.assertEquals(true, affectedRows == 1);
	}

	@Test
	public void testFindUserRoles() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		Role role = new Role(UUIDUtils.createUUID(), "普通用户");
		roleMapper.add(role);
		UserRole userRole = new UserRole(id, role.getId());
		userMapper.assignRoles(userRole);
		UserVo userVo = userMapper.findUserRoles(id);
		Assert.assertEquals(true, !userVo.getRoles().isEmpty());
	}
}
