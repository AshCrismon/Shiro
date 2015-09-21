package pers.ash.shiro.service;

import java.util.List;

import pers.ash.shiro.exception.DuplicationException;
import pers.ash.shiro.model.Permission;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;

public interface UserService {
	
	public User createUser(User user) throws DuplicationException;
	
	public void changePassword(String userId, String newPassword);
	
	public void correlationRoles(String userId, String... roleIds);
	
	public void unCorrelationRoles(String userId, String... roleIds);
	
	public User findByUsername(String username);
	
	public List<Role> findRoles(String username);
	
	public List<Permission> findPermissions(String username);
}
