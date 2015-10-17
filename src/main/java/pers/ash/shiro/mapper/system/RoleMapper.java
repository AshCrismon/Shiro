package pers.ash.shiro.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pers.ash.shiro.mapper.Mapper;
import pers.ash.shiro.model.system.Permission;
import pers.ash.shiro.model.system.Role;

public interface RoleMapper extends Mapper<Role> {

	public Role findByRoleName(String roleName);

	public int correlationPermission(@Param("roleId") String roleId,
			@Param("permissionId") String permissionId);

	public int uncorrelationPermission(@Param("roleId") String roleId,
			@Param("permissionId") String permissionId);

	public Permission findRolePermission(@Param("roleId") String roleId,
			@Param("permissionId") String permissionId);
	
	public List<Permission> findPermissions(String roleId);
	
	public List<String> findStringPermissions(String roleId);
}
