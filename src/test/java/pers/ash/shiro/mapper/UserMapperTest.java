package pers.ash.shiro.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import pers.ash.shiro.config.AbstractTransactionalConfig;
import pers.ash.shiro.mapper.RoleMapper;
import pers.ash.shiro.mapper.UserMapper;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.util.DateUtils;
import pers.ash.shiro.util.UUIDUtils;
import pers.ash.shiro.vo.UserVo;

import com.github.orderbyhelper.OrderByHelper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;


public class UserMapperTest extends AbstractTransactionalConfig {

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
	public void testFindByUsername(){
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		User user = userMapper.findByUsername("琪琪");
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
		print(pageInfo);
	}
	
	@Test
	public void testFindByOrder(){
		OrderByHelper.orderBy("CONVERT(username USING gbk) asc");
		List<User> users = userMapper.findAll();
		System.out.println("====================order by username asc====================");
		print(users);
		System.out.println("=============================================================");
	}
	
	@Test
	public void testFindByOrder2(){
		OrderByHelper.orderBy("age asc, phone asc");
		List<User> users = userMapper.findAll();
		System.out.println("=================order by age asc, phone asc=================");
		print(users);
		System.out.println("=============================================================");
	}
	
	@Test
	public void testFindPageByOrder(){
		PageHelper.startPage(1, 10, "CONVERT(username USING gbk) asc");
		List<User> users = userMapper.findAll();
		System.out.println("====================order by username asc====================");
		print(users);
		System.out.println("=============================================================");
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
	public void testCorrelationRole() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		Role role = new Role(UUIDUtils.createUUID(), "普通用户");
		roleMapper.add(role);
		int affectedRows = userMapper.correlationRole(id, role.getId());
		Assert.assertEquals(true, affectedRows == 1);
	}

	@Test
	public void testUnCorrelationRole() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		Role role = new Role(UUIDUtils.createUUID(), "普通用户");
		roleMapper.add(role);
		userMapper.correlationRole(id, role.getId());
		int affectedRows = userMapper.unCorrelationRole(id, role.getId());
		Assert.assertEquals(true, affectedRows == 1);
	}
	
	@Test
	public void testFindUserRole() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		Role role = new Role(UUIDUtils.createUUID(), "普通用户");
		roleMapper.add(role);
		userMapper.correlationRole(id, role.getId());
		Role r = userMapper.findUserRole(id, role.getId());
		Assert.assertEquals(true, r != null);
	}
	
	@Test
	public void testFindUserRoles() {
		String id = add("琪琪","123456",23,"女","13434477752","qiqi@163.com");
		Role role = new Role(UUIDUtils.createUUID(), "普通用户");
		roleMapper.add(role);
		userMapper.correlationRole(id, role.getId());
		UserVo userVo = userMapper.findUserRoles(id);
		Assert.assertEquals(true, !userVo.getRoles().isEmpty());
	}
	
	public void print(List<User> users){
		for(int i = 0; i < users.size(); i++){
			System.out.println(users.get(i));
		}
	}
	
	public <T> void print(PageInfo<T> pageInfo){
		System.out.println("======================page infomation======================");
		System.out.println("total pages : " + pageInfo.getPages());
		System.out.println("total rows : " + pageInfo.getTotal());
		System.out.println("current page size : " + pageInfo.getSize());
		System.out.println("pagination : " + pageInfo.getPageNum());
		System.out.println("previous pagination : " + pageInfo.getPrePage());
		System.out.println("next pagination : " + pageInfo.getNextPage());
		System.out.println("first pagination : " + pageInfo.getFirstPage());
		System.out.println("last pagination : " + pageInfo.getLastPage());
		System.out.println("===========================================================");
	}
}
