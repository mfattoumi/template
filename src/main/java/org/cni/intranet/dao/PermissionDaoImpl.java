package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Permission;

@Repository
public class PermissionDaoImpl implements PermissionDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Permission addPermission(Permission permission) {
		em.persist(permission);
		return permission;
	}

	@Transactional
	public Permission deletePermission(int id) {
		Permission permission = getPermissionById(id);
		em.remove(permission);
		return permission;
	}

	@Transactional
	public Permission updatePermission(Permission permission) {
		em.merge(permission);
		return permission;
	}

	@Transactional
	public Permission getPermissionById(int id) {
		Permission as = em.find(Permission.class, id);
		if(as==null) throw new RuntimeException("Permission introuvable");
		return as;
	}

	@Transactional
	public List<Permission> getAllPermissions() {
		Query req = em.createQuery("select x from Permission");
		return req.getResultList();
	}

}
