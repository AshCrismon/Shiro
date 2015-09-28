package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pers.ash.shiro.exception.DuplicationException;
import pers.ash.shiro.exception.EntityNotAvailableException;
import pers.ash.shiro.exception.EntityNotFoundException;
import pers.ash.shiro.helper.ModelHelper;
import pers.ash.shiro.mapper.PermissionMapper;
import pers.ash.shiro.mapper.RoleMapper;
import pers.ash.shiro.model.ModelState;
import pers.ash.shiro.model.Permission;
import pers.ash.shiro.model.Role;
import pers.ash.shiro.service.RoleService;
import pers.ash.shiro.util.UUIDUtils;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private PermissionMapper permissionMapper;

	/* ========================business methods========================= */
	
	@Override
	public List<Role> findAllRoles() {
		return roleMapper.findAll();
	}
	
	@Override
	public void createRole(Role... roles) {
		for(int i = 0; i < roles.length; i++){
			createRole(roles[i]);
		}
	}
	
	public Role createRole(Role role) {
		validate(role);
		roleMapper.add(role);
		return role;
	}

	@Override
	public void deleteRole(String roleId) {
		Role role = validate(roleId);
		switch (ModelHelper.getState()) {
		case LOCKED:
			role.setState(ModelState.LOCKED);
			roleMapper.update(role);
			break;
		case REMOVE:
			role.setState(ModelState.REMOVE);
			roleMapper.update(role);
			break;
		case DELETE:
			roleMapper.delete(roleId);
			break;
		}
	}

	@Override
	public void correlationPermissions(String roleId, String... permissionIds) {
		for(int i = 0; i < permissionIds.length; i++){
			if(validate(roleId, permissionIds[i])){
				roleMapper.correlationPermission(roleId, permissionIds[i]);
			}
		}
	}

	@Override
	public void uncorrelationPermissions(String roleId, String... permissionIds) {
		for(int i = 0; i < permissionIds.length; i++){
			roleMapper.uncorrelationPermission(roleId, permissionIds[i]);
		}
	}
	
	@Override
	public Role findByRoleId(String roleId) {
		return roleMapper.findById(roleId);
	}

	@Override
	public Role findByRoleName(String roleName) {
		return roleMapper.findByRoleName(roleName);
	}

	/* =============================validate============================ */

	/**
	 * 验证角色名是否已经存在
	 * @param role
	 */
	public void validate(Role role){
		if(null == role){
			throw new NullPointerException("创建的角色不能为null");
		}
		if(StringUtils.isEmpty(role.getName())){
			throw new IllegalArgumentException("角色名不能为空");
		}
		if(roleMapper.findByRoleName(role.getName()) != null){
			throw new DuplicationException("角色名已经存在");
		}
		if(StringUtils.isEmpty(role.getId())){
			role.setId(UUIDUtils.createUUID());
		}
	}
	
	/**
	 * 验证角色是否存在，存在则返回角色对象
	 * @param roleId
	 * @return
	 */
	public Role validate(String roleId){
		Role role = roleMapper.findById(roleId);
		if(null == role){
			throw new EntityNotFoundException("角色不存在或已经被删除");
		}
		return role;
	}
	
	/**
	 * 验证角色与权限是否存在对应关系
	 * @param roleId
	 * @param permissionId
	 * @return
	 */
	public boolean validate(String roleId, String permissionId){
		if (StringUtils.isEmpty(roleId) || StringUtils.isEmpty(permissionId)) {
			throw new NullPointerException("角色或权限id不能为空");
		}
		if(!roleIsAvailable(roleId) || !permissionIsAvailable(permissionId)){
			throw new EntityNotAvailableException("用户或角色不可用");
		}
		return roleMapper.findRolePermission(roleId, permissionId) == null;
	}
	
	/**
	 * 某个角色是否有效
	 * @param userId
	 * @return
	 */
	public boolean roleIsAvailable(String roleId){
		Role role = roleMapper.findById(roleId);
		if(null == role){
			return false;
		}else{
			return role.getState() == ModelState.NORMAL;
		}
	}
	
	/**
	 * 某个权限是否有效
	 * @param userId
	 * @return
	 */
	public boolean permissionIsAvailable(String permissionId){
		Permission permission = permissionMapper.findById(permissionId);
		if(null == permission){
			return false;
		}else{
			return permission.getState() == ModelState.NORMAL;
		}
	}
	/* =======================setters and getters======================= */
	public RoleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(RoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

}
