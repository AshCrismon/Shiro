package pers.ash.shiro.mapper;

import pers.ash.shiro.model.Permission;

public interface PermissionMapper extends Mapper<Permission>{
	
	public Permission findByPermissionName(String permissionName);
}
