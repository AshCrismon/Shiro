package pers.ash.shiro.vo;

import java.util.List;

import pers.ash.shiro.model.system.Permission;
import pers.ash.shiro.model.system.Role;


public class RoleVo extends Role {
	
	private List<Permission> permissions;

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
