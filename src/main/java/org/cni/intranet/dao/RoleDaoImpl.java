package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Role;

@Repository
public class RoleDaoImpl implements RoleDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Role addRole(Role role) {
		em.persist(role);
		return role;
	}

	@Transactional
	public Role deleteRole(int id) {
		Role role = getRoleById(id);
		em.remove(role);
		return role;
	}

	@Transactional
	public Role updateRole(Role role) {
		em.merge(role);
		return role;
	}

	@Transactional
	public Role getRoleById(int id) {
		Role as = em.find(Role.class, id);
		if(as==null) throw new RuntimeException("Role introuvable");
		return as;
	}

	@Transactional
	public List<Role> getAllRoles() {
		Query req = em.createQuery("select x from Role");
		return req.getResultList();
	}

}
