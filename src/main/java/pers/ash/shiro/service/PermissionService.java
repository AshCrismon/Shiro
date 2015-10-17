package pers.ash.shiro.service;

import java.util.List;

import pers.ash.shiro.model.system.Permission;

public interface PermissionService {
	
	/**
	 * 获取所有权限
	 * @return
	 */
	public List<Permission> findAllPermissions();
	/**
	 * 增加权限
	 * @param permissions
	 */
	public void createPermission(Permission... permissions);
	
	/**
	 * 根据id删除权限
	 * @param permissionId
	 */
	public void deletePermission(String permissionId);
	
	/**
	 * 根据id获取权限
	 * @param permissionId
	 * @return
	 */
	public Permission findByPermissionId(String permissionId);
	
	/**
	 * 根据权限名获取权限
	 * @param permissionName
	 * @return
	 */
	public Permission findByPermissionName(String permissionName);
}
