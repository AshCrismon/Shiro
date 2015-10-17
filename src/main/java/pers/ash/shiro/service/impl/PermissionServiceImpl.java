package pers.ash.shiro.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;

import pers.ash.shiro.exception.DuplicationException;
import pers.ash.shiro.exception.EntityNotFoundException;
import pers.ash.shiro.helper.ModelHelper;
import pers.ash.shiro.mapper.PermissionMapper;
import pers.ash.shiro.model.ModelState;
import pers.ash.shiro.model.system.Permission;
import pers.ash.shiro.service.PermissionService;
import pers.ash.shiro.util.UUIDUtils;

@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	/* ========================business methods========================= */
	
	@Override
	public List<Permission> findAllPermissions() {
		return permissionMapper.findAll();
	}
	
	@Override
	public void createPermission(Permission... permissions) {
		for(int i = 0; i < permissions.length; i++){
			createPermission(permissions[i]);
		}
	}
	
	public Permission createPermission(Permission permission) {
		testValidity(permission);
		permissionMapper.add(permission);
		return permission;
	}

	@Override
	public void deletePermission(String permissionId) {
		Permission permission = testValidity(permissionId);
		switch (ModelHelper.getState()) {
		case LOCKED:
			permission.setState(ModelState.LOCKED);
			permissionMapper.update(permission);
			break;
		case REMOVE:
			permission.setState(ModelState.REMOVE);
			permissionMapper.update(permission);
			break;
		case DELETE:
			permissionMapper.delete(permissionId);
			break;
		}
	}

	@Override
	public Permission findByPermissionId(String permissionId) {
		return permissionMapper.findById(permissionId);
	}

	@Override
	public Permission findByPermissionName(String permissionName) {
		return permissionMapper.findByPermissionName(permissionName);
	}

	/* =============================testValidity============================ */
	/**
	 * 验证权限名是否已经存在
	 * @param permission
	 */
	public void testValidity(Permission permission) {
		if (null == permission) {
			throw new NullPointerException("创建的权限不能为null");
		}
		if (StringUtils.isEmpty(permission.getName())) {
			throw new IllegalArgumentException("权限名不能为空");
		}
		if (permissionMapper.findByPermissionName(permission.getName()) != null) {
			throw new DuplicationException("权限名已经存在");
		}
		if (StringUtils.isEmpty(permission.getId())) {
			permission.setId(UUIDUtils.createUUID());
		}
	}
	
	/**
	 * 验证某个权限是否已经存在，存在则返回权限对象
	 * @param permissionId
	 * @return
	 */
	public Permission testValidity(String permissionId){
		Permission permission = permissionMapper.findById(permissionId);
		if(null == permission){
			throw new EntityNotFoundException("权限不存在或已经被删除");
		}
		return permission;
	}
	/* =======================setters and getters======================= */

}
