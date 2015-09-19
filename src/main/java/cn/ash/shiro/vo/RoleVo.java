package cn.ash.shiro.vo;

import java.util.List;

import cn.ash.shiro.model.Permission;
import cn.ash.shiro.model.Role;

public class RoleVo extends Role {
	
	private List<Permission> permissions;

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
