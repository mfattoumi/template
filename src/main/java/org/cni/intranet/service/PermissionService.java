package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Permission;

public interface PermissionService {
	public Permission addPermission(Permission permission);
	public Permission deletePermission(int id);
	public Permission updatePermission(Permission permission);
	public Permission getPermissionById(int id);
	public List<Permission> getAllPermissions();
}
