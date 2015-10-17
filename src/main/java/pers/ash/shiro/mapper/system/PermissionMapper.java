package pers.ash.shiro.mapper.system;

import pers.ash.shiro.mapper.Mapper;
import pers.ash.shiro.model.system.Permission;

public interface PermissionMapper extends Mapper<Permission>{
	
	public Permission findByPermissionName(String permissionName);
}
