package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ash.shiro.exception.DuplicateNameException;
import pers.ash.shiro.mapper.UserMapper;
import pers.ash.shiro.model.Permission;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/* ========================business methods========================= */

	@Override
	public User createUser(User user) throws DuplicateNameException {
		validateUser(user);
		userMapper.add(user);
		return user;
	}

	@Override
	public void changePassword(String userId, String newPassword) {

	}

	@Override
	public void correlationRoles(String userId, String roleIds) {

	}

	@Override
	public void unCorrelationRoles(String userId, String roleIds) {

	}

	@Override
	public User findByUsername(String username) {
		return null;
	}

	@Override
	public List<Role> findRoles(String username) {
		return null;
	}

	@Override
	public List<Permission> findPermissions(String username) {
		return null;
	}

	public void validateUser(User user){
		if (userMapper.findByUsername(user.getUsername()) != null) {
			throw new DuplicateNameException("用户名已被使用");
		}
	}

	/* =======================setters and getters======================= */

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

}
