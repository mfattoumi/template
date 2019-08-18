package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Corp;

@Repository
public class CorpDaoImpl implements CorpDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Corp addCorp(Corp corp) {
		em.persist(corp);
		return corp;
	}

	@Transactional
	public Corp deleteCorp(int id) {
		Corp corp = getCorpById(id);
		em.remove(corp);
		return corp;
	}

	@Transactional
	public Corp updateCorp(Corp corp) {
		em.merge(corp);
		return corp;
	}

	@Transactional
	public Corp getCorpById(int id) {
		Corp as = em.find(Corp.class, id);
		if(as==null) throw new RuntimeException("Corp introuvable");
		return as;
	}

	@Transactional
	public List<Corp> getAllCorps() {
		Query req = em.createQuery("select x from Corp");
		return req.getResultList();
	}

}
