package pers.ash.shiro.service;

import pers.ash.shiro.model.Role;

public interface RoleService {
	
	public Role createRole(Role role);
	
	public void deleteRole(String id);
}
