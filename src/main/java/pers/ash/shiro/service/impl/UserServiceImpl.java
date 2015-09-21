package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pers.ash.shiro.exception.DuplicationException;
import pers.ash.shiro.helper.ModelHelper;
import pers.ash.shiro.helper.PasswordHelper;
import pers.ash.shiro.mapper.UserMapper;
import pers.ash.shiro.model.Permission;
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
	public void deleteUser(String id) {
		switch(ModelHelper.getState()){
		
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
			if(validate(userId, roleIds[i])){
				userMapper.correlationRole(userId, roleIds[i]);
			}
		}
	}

	@Override
	public void unCorrelationRoles(String userId, String... roleIds) {
		for(int i = 0; i < roleIds.length; i++){
			userMapper.unCorrelationRole(userId, roleIds[i]);
		}
	}

	@Override
	public User findByUsername(String username) {
		return userMapper.findByUsername(username);
	}

	@Override
	public UserVo findUserRoles(String id) {
		return userMapper.findUserRoles(id);
	}

	@Override
	public List<Permission> findPermissions(String username) {
		return null;
	}

	public void validate(User user) {
		if (userMapper.findByUsername(user.getUsername()) != null) {
			throw new DuplicationException("用户名已被使用");
		}
	}

	public User validate(String userId) {
		User user = userMapper.findById(userId);
		if (user == null) {
			throw new NullPointerException("用户不存在");
		}
		return user;
	}

	private boolean validate(String userId, String roleId) {
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
