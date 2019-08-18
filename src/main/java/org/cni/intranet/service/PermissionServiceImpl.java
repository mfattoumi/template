package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.PermissionDao;
import org.cni.intranet.entities.Permission;

@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionDao permissionDao;

	public void setPermissionDao(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}
	
	@Override
	public Permission addPermission(Permission permission) {
		return permissionDao.addPermission(permission);
	}

	@Override
	public Permission deletePermission(int id) {
		return permissionDao.deletePermission(id);
	}

	@Override
	public Permission updatePermission(Permission permission) {
		return permissionDao.updatePermission(permission);
	}

	@Override
	public Permission getPermissionById(int id) {
		return permissionDao.getPermissionById(id);
	}

	@Override
	public List<Permission> getAllPermissions() {
		return permissionDao.getAllPermissions();
	}

}
