package pers.ash.shiro.service;

import java.util.List;

import pers.ash.shiro.exception.DuplicationException;
import pers.ash.shiro.model.Permission;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.vo.UserVo;

public interface UserService {
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 * @throws DuplicationException
	 */
	public User createUser(User user) throws DuplicationException;
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteUser(String id);
	
	/**
	 * 修改用户
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 * 更改用户密码
	 * @param userId
	 * @param newPassword
	 */
	public void changePassword(String userId, String newPassword);
	
	/**
	 * 给用户添加角色
	 * @param userId
	 * @param roleIds
	 */
	public void correlationRoles(String userId, String... roleIds);
	
	/**
	 * 删除用户的角色
	 * @param userId
	 * @param roleIds
	 */
	public void unCorrelationRoles(String userId, String... roleIds);
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User findByUsername(String username);
	
	/**
	 * 查找用户的角色
	 * @param username
	 * @return
	 */
	public UserVo findUserRoles(String username);
	
	/**
	 * 查找用户的权限
	 * @param username
	 * @return
	 */
	public List<Permission> findPermissions(String username);
}
