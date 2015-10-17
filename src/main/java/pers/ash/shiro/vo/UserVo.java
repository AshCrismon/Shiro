package pers.ash.shiro.vo;

import java.util.List;

import pers.ash.shiro.model.system.Role;
import pers.ash.shiro.model.system.User;


public class UserVo extends User {
	
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
