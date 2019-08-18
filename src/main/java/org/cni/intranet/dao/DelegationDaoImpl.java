package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Delegation;

@Repository
public class DelegationDaoImpl implements DelegationDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Delegation addDelegation(Delegation delegation) {
		em.persist(delegation);
		return delegation;
	}

	@Transactional
	public Delegation deleteDelegation(int id) {
		Delegation delegation = getDelegationById(id);
		em.remove(delegation);
		return delegation;
	}

	@Transactional
	public Delegation updateDelegation(Delegation delegation) {
		em.merge(delegation);
		return delegation;
	}

	@Transactional
	public Delegation getDelegationById(int id) {
		Delegation as = em.find(Delegation.class, id);
		if(as==null) throw new RuntimeException("Delegation introuvable");
		return as;
	}

	@Transactional
	public List<Delegation> getAllDelegations() {
		Query req = em.createQuery("select x from Delegation");
		return req.getResultList();
	}

}
