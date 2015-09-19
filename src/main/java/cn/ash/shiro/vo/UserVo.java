package cn.ash.shiro.vo;

import java.util.List;

import cn.ash.shiro.model.Role;
import cn.ash.shiro.model.User;

public class UserVo extends User {
	
	private List<Role> roles;

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
