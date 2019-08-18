package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.FaxLocal;

@Repository
public class FaxLocalDaoImpl implements FaxLocalDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public FaxLocal addFaxLocal(FaxLocal faxLocal) {
		em.persist(faxLocal);
		return faxLocal;
	}

	@Transactional
	public FaxLocal deleteFaxLocal(int id) {
		FaxLocal faxLocal = getFaxLocalById(id);
		em.remove(faxLocal);
		return faxLocal;
	}

	@Transactional
	public FaxLocal updateFaxLocal(FaxLocal faxLocal) {
		em.merge(faxLocal);
		return faxLocal;
	}

	@Transactional
	public FaxLocal getFaxLocalById(int id) {
		FaxLocal as = em.find(FaxLocal.class, id);
		if(as==null) throw new RuntimeException("FaxLocal introuvable");
		return as;
	}

	@Transactional
	public List<FaxLocal> getAllFaxLocals() {
		Query req = em.createQuery("select x from FaxLocal");
		return req.getResultList();
	}

}
