package pers.ash.shiro.service.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import pers.ash.shiro.exception.DuplicationException;
import pers.ash.shiro.exception.EntityNotAvailableException;
import pers.ash.shiro.exception.EntityNotFoundException;
import pers.ash.shiro.helper.ModelHelper;
import pers.ash.shiro.helper.PasswordHelper;
import pers.ash.shiro.mapper.system.RoleMapper;
import pers.ash.shiro.mapper.system.UserMapper;
import pers.ash.shiro.model.ModelState;
import pers.ash.shiro.model.system.Permission;
import pers.ash.shiro.model.system.Role;
import pers.ash.shiro.model.system.User;
import pers.ash.shiro.service.UserService;
import pers.ash.shiro.util.UUIDUtils;
import pers.ash.shiro.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleMapper roleMapper;
	
	private static final Logger logger = LoggerFactory.getLogger("UserServiceImpl");
	
	/* ========================business methods========================= */
	@Override
	public List<User> findAllUsers(){
		return userMapper.findAll();
	}
	
	@Override
	public void createUser(User... users) throws DuplicationException {
		testValidity(users);
		for(int i = 0; i < users.length; i++){
			createUser(users[i]);
		}
	}
	
	public User createUser(User user) throws DuplicationException {
		testValidity(user);
		PasswordHelper.encrypt(user);
		userMapper.add(user);
		return user;
	}
	
	@Override
	public void deleteUser(String userId) {
		User user = testValidity(userId);
		switch (ModelHelper.getState()) {
		case LOCKED:
			user.setState(ModelState.LOCKED);
			userMapper.update(user);
			break;
		case REMOVE:
			user.setState(ModelState.REMOVE);
			userMapper.update(user);
			break;
		case DELETE:
			userMapper.delete(userId);
			break;
		}
	}

	@Override
	public void updateUser(User user) {
		testValidity(user.getId());
		userMapper.update(user);
	}

	@Override
	public void changePassword(String userId, String newPassword) {
		User user = testValidity(userId);
		user.setPassword(newPassword);
		PasswordHelper.encrypt(user);
		userMapper.update(user);
	}

	@Override
	public void correlationRoles(String userId, String... roleIds) {
		for (int i = 0; i < roleIds.length; i++) {
			if (testValidity(userId, roleIds[i])) {
				userMapper.correlationRole(userId, roleIds[i]);
			}
		}
	}

	@Override
	public void uncorrelationRoles(String userId, String... roleIds) {
		for (int i = 0; i < roleIds.length; i++) {
			userMapper.uncorrelationRole(userId, roleIds[i]);
		}
	}
	
	@Override
	public User findByUserId(String userId) {
		return userMapper.findById(userId);
	}

	@Override
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	@Override
	public UserVo findUserRoles(String userId) {
		return userMapper.findUserRoles(userId);
	}
	
	@Override
	public List<Role> findRoles(String userId) {
		testValidity(userId);
		List<Role> roles = userMapper.findRoles(userId);
		return roles == null ? Collections.<Role>emptyList() : roles;
	}
	
	@Override
	public List<String> findStringRoles(String userId) {
		testValidity(userId);
		List<String> roles = userMapper.findStringRoles(userId);
		return roles == null ? Collections.<String>emptyList() : roles;
	}

	@Override
	public List<Permission> findPermissions(String userId) {
		testValidity(userId);
		List<Permission> permissions = userMapper.findPermissions(userId);
		return permissions == null ? Collections.<Permission>emptyList() : permissions;
	}
	
	@Override
	public List<String> findStringPermissions(String userId) {
		testValidity(userId);
		List<String> permissions = userMapper.findStringPermissions(userId);
		return permissions == null ? Collections.<String>emptyList() : permissions;
	}
	
	@Override
	public List<String> findPermissionUris(String userId) {
		testValidity(userId);
		List<String> permissions = userMapper.findPermissionUris(userId);
		return permissions == null ? Collections.<String>emptyList() : permissions;
	}
	
	@Override
	public List<String> findAbsolutePermissions(String userId) {
		testValidity(userId);
		List<String> permissions = userMapper.findAbsolutePermissions(userId);
		return permissions == null ? Collections.<String>emptyList() : permissions;
	}

	/* =============================testValidity============================ */

	/**
	 * 验证用户名是否被占用
	 * 
	 * @param user
	 */
	public void testValidity(User user) {
		if(StringUtils.isEmpty(user.getUsername())){
			logger.error("------------>用户名不能为空");
			throw new IllegalArgumentException("用户名不能为空");
		}
		if (userMapper.findByUsername(user.getUsername()) != null) {
			logger.error("------------>用户名已被使用");
			throw new DuplicationException("用户名已被使用");
		}
		if(StringUtils.isEmpty(user.getId())){
			user.setId(UUIDUtils.createUUID());
		}
	}
	
	public void testValidity(User... users){
		if(null == users){
			logger.error("------------>创建的用户不能为null");
			throw new NullPointerException("创建的用户不能为null");
		}
	}

	/**
	 * 验证用户是否存在，存在则返回用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public User testValidity(String userId){
		User user = userMapper.findById(userId);
		if(null == user){
			logger.error("------------>用户不存在或已经被删除");
			throw new EntityNotFoundException("用户不存在或已经被删除");
		}
		return user;
	}

	/**
	 * 验证用户-角色表是否已存在某个对应关系
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	private boolean testValidity(String userId, String roleId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleId)) {
			logger.error("------------>用户或角色id不能为空");
			throw new NullPointerException("用户或角色id不能为空");
		}
		if(!userIsAvailable(userId) || !roleIsAvailable(roleId)){
			logger.error("------------>用户或角色不可用");
			throw new EntityNotAvailableException("用户或角色不可用");
		}
		return userMapper.findUserRole(userId, roleId) == null;
	}
	
	
	/**
	 * 某个用户是否有效
	 * @param userId
	 * @return
	 */
	public boolean userIsAvailable(String userId) {
		User user = userMapper.findById(userId);
		if(null == user){
			return false;
		}else{
			return user.getState() == ModelState.NORMAL;
		}
	}
	
	/**
	 * 某个角色是否有效
	 * @param userId
	 * @return
	 */
	public boolean roleIsAvailable(String roleId){
		Role role = roleMapper.findById(roleId);
		if(null == role){
			return false;
		}else{
			return role.getState() == ModelState.NORMAL;
		}
	}

	/* =======================setters and getters======================= */

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

}
