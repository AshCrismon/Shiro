package pers.ash.shiro.service;

import java.util.List;

import pers.ash.shiro.model.system.Role;

public interface RoleService {
	
	/**
	 * 获取所有角色
	 * @return
	 */
	public List<Role> findAllRoles();
	/**
	 * 创建用户
	 * @param role
	 * @return
	 */
	public void createRole(Role... roles);
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteRole(String id);
	
	/**
	 * 给角色关联权限
	 * @param roleId
	 * @param PermissionIds
	 */
	public void correlationPermissions(String roleId, String... permissionIds);
	
	/**
	 * 取消角色的权限
	 * @param roleId
	 * @param PermissionIds
	 */
	public void uncorrelationPermissions(String roleId, String... permissionIds);
	
	/**
	 * 通过角色id查找角色
	 * @param roleId
	 * @return
	 */
	public Role findByRoleId(String roleId);
	
	/**
	 * 通过角色名查找角色
	 * @param roleName
	 * @return
	 */
	public Role findByRoleName(String roleName);
}
