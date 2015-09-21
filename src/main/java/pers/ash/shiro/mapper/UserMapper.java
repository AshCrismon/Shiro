package pers.ash.shiro.mapper;

import org.apache.ibatis.annotations.Param;

import pers.ash.shiro.model.Role;
import pers.ash.shiro.model.User;
import pers.ash.shiro.vo.UserVo;

public interface UserMapper extends Mapper<User>{
	
	public int correlationRole(@Param("userId")String userId, @Param("roleId")String roleId);
	
	public int unCorrelationRole(@Param("userId")String userId, @Param("roleId")String roleId);
	
	public Role findUserRole(@Param("userId")String userId, @Param("roleId")String roleId);

	public User findByUsername(String username);
	
	public UserVo findUserRoles(String id);
	
}
