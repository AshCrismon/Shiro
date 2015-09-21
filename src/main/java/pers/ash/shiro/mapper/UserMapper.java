package pers.ash.shiro.mapper;

import pers.ash.shiro.model.User;
import pers.ash.shiro.model.UserRole;
import pers.ash.shiro.vo.UserVo;

public interface UserMapper extends Mapper<User>{
	
	public int assignRole(UserRole userRole);
	
	public UserVo findUserRoles(String id);
	
}
