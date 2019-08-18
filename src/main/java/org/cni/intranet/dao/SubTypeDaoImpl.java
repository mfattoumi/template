package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.SubType;

@Repository
public class SubTypeDaoImpl implements SubTypeDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public SubType addSubType(SubType subType) {
		em.persist(subType);
		return subType;
	}

	@Transactional
	public SubType deleteSubType(int id) {
		SubType subType = getSubTypeById(id);
		em.remove(subType);
		return subType;
	}

	@Transactional
	public SubType updateSubType(SubType subType) {
		em.merge(subType);
		return subType;
	}

	@Transactional
	public SubType getSubTypeById(int id) {
		SubType as = em.find(SubType.class, id);
		if(as==null) throw new RuntimeException("SubType introuvable");
		return as;
	}

	@Transactional
	public List<SubType> getAllSubTypes() {
		Query req = em.createQuery("select x from SubType");
		return req.getResultList();
	}

}
