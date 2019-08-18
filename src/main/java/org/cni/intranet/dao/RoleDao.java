package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Role;

public interface RoleDao {
	public Role addRole(Role role);
	public Role deleteRole(int id);
	public Role updateRole(Role role);
	public Role getRoleById(int id);
	public List<Role> getAllRoles();
}