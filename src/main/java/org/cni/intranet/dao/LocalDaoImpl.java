package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Local;

@Repository
public class LocalDaoImpl implements LocalDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Local addLocal(Local local) {
		em.persist(local);
		return local;
	}

	@Transactional
	public Local deleteLocal(int id) {
		Local local = getLocalById(id);
		em.remove(local);
		return local;
	}

	@Transactional
	public Local updateLocal(Local local) {
		em.merge(local);
		return local;
	}

	@Transactional
	public Local getLocalById(int id) {
		Local as = em.find(Local.class, id);
		if(as==null) throw new RuntimeException("Local introuvable");
		return as;
	}

	@Transactional
	public List<Local> getAllLocals() {
		Query req = em.createQuery("select x from Local");
		return req.getResultList();
	}

}
