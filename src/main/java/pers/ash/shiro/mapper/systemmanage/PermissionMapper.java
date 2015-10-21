package pers.ash.shiro.mapper.systemmanage;

import pers.ash.shiro.mapper.Mapper;
import pers.ash.shiro.model.systemmanage.Permission;

public interface PermissionMapper extends Mapper<Permission>{
	
	public Permission findByPermissionName(String permissionName);
}
