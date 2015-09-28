package pers.ash.shiro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pers.ash.shiro.model.Permission;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.vo.UserVo;

public interface UserMapper extends Mapper<User> {

	public int correlationRole(@Param("userId") String userId,
			@Param("roleId") String roleId);

	public int unCorrelationRole(@Param("userId") String userId,
			@Param("roleId") String roleId);

	public Role findUserRole(@Param("userId") String userId,
			@Param("roleId") String roleId);

	public User findByUsername(String username);

	public List<Role> findRoles(String userId);

	public List<Permission> findPermissions(String userId);

	public UserVo findUserRoles(String userId);

}
