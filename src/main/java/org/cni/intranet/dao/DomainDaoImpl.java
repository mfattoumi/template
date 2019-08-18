package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Domain;

@Repository
public class DomainDaoImpl implements DomainDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Domain addDomain(Domain domain) {
		em.persist(domain);
		return domain;
	}

	@Transactional
	public Domain deleteDomain(int id) {
		Domain domain = getDomainById(id);
		em.remove(domain);
		return domain;
	}

	@Transactional
	public Domain updateDomain(Domain domain) {
		em.merge(domain);
		return domain;
	}

	@Transactional
	public Domain getDomainById(int id) {
		Domain as = em.find(Domain.class, id);
		if(as==null) throw new RuntimeException("Domain introuvable");
		return as;
	}

	@Transactional
	public List<Domain> getAllDomains() {
		Query req = em.createQuery("select x from Domain");
		return req.getResultList();
	}

}
