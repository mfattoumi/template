package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.RoleDao;
import org.cni.intranet.entities.Role;

@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	@Override
	public Role addRole(Role role) {
		return roleDao.addRole(role);
	}

	@Override
	public Role deleteRole(int id) {
		return roleDao.deleteRole(id);
	}

	@Override
	public Role updateRole(Role role) {
		return roleDao.updateRole(role);
	}

	@Override
	public Role getRoleById(int id) {
		return roleDao.getRoleById(id);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleDao.getAllRoles();
	}

}
