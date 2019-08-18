package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Permission;

public interface PermissionDao {
	public Permission addPermission(Permission permission);
	public Permission deletePermission(int id);
	public Permission updatePermission(Permission permission);
	public Permission getPermissionById(int id);
	public List<Permission> getAllPermissions();
}