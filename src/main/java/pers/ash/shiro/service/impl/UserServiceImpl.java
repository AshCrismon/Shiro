package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import pers.ash.shiro.exception.DuplicationException;
import pers.ash.shiro.helper.ModelHelper;
import pers.ash.shiro.helper.PasswordHelper;
import pers.ash.shiro.mapper.UserMapper;
import pers.ash.shiro.model.ModelState;
import pers.ash.shiro.model.Permission;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.service.UserService;
import pers.ash.shiro.vo.UserVo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/* ========================business methods========================= */

	@Override
	public User createUser(User user) throws DuplicationException {
		validate(user);
		PasswordHelper.encrypt(user);
		userMapper.add(user);
		return user;
	}
	
	@Override
	public void deleteUser(String userId) {
		User user = validate(userId);
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
		validate(user.getId());
		userMapper.update(user);
	}

	@Override
	public void changePassword(String userId, String newPassword) {
		User user = validate(userId);
		user.setPassword(newPassword);
		PasswordHelper.encrypt(user);
		userMapper.update(user);
	}

	@Override
	public void correlationRoles(String userId, String... roleIds) {
		validate(userId);
		for (int i = 0; i < roleIds.length; i++) {
			if (validate(userId, roleIds[i])) {
				userMapper.correlationRole(userId, roleIds[i]);
			}
		}
	}

	@Override
	public void unCorrelationRoles(String userId, String... roleIds) {
		for (int i = 0; i < roleIds.length; i++) {
			userMapper.unCorrelationRole(userId, roleIds[i]);
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
		validate(userId);
		List<Role> roles = userMapper.findRoles(userId);
		return roles == null ? ;
	}

	@Override
	public List<Permission> findPermissions(String username) {
		return null;
	}

	/* =============================validate============================ */

	/**
	 * 验证用户名是否被占用
	 * 
	 * @param user
	 */
	public void validate(User user) {
		if (userMapper.findByUsername(user.getUsername()) != null) {
			throw new DuplicationException("用户名已被使用");
		}
	}

	/**
	 * 验证用户是否存在，存在则返回用户对象
	 * 
	 * @param userId
	 * @return
	 */
	public User validate(String userId) {
		if (StringUtils.isEmpty(userId)) {
			throw new NullPointerException("用户id不能为空");
		}
		User user = userMapper.findById(userId);
		if (user == null) {
			throw new NullPointerException("用户不存在");
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
	private boolean validate(String userId, String roleId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(roleId)) {
			throw new NullPointerException("用户或角色id不能为空");
		}
		return userMapper.findUserRole(userId, roleId) == null;
	}

	/* =======================setters and getters======================= */

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

}
