package pers.ash.shiro.mapper;

import cn.ash.shiro.model.User;
import cn.ash.shiro.model.UserRole;
import cn.ash.shiro.vo.UserVo;

public interface UserMapper extends Mapper<User>{
	
	public int assignRoles(UserRole userRole);
	
	public UserVo findUserRoles(String id);
	
}
