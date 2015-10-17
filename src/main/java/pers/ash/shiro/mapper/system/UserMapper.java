package pers.ash.shiro.mapper.system;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import pers.ash.shiro.mapper.Mapper;
import pers.ash.shiro.model.system.Permission;
import pers.ash.shiro.model.system.Role;
import pers.ash.shiro.model.system.User;
import pers.ash.shiro.vo.UserVo;

public interface UserMapper extends Mapper<User> {
	
	public int correlationRole(@Param("userId") String userId,
			@Param("roleId") String roleId);

	public int uncorrelationRole(@Param("userId") String userId,
			@Param("roleId") String roleId);

	public Role findUserRole(@Param("userId") String userId,
			@Param("roleId") String roleId);

	public User findByUsername(String username);

	public List<Role> findRoles(String userId);
	
	public List<String> findStringRoles(String userId);
	
	public List<Permission> findPermissions(String userId);
	
	public List<String> findStringPermissions(String userId);
	
	public List<String> findPermissionUris(String userId);
	
	public List<String> findAbsolutePermissions(String userId);
	
	public UserVo findUserRoles(String userId);

}
