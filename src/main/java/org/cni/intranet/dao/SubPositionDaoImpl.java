package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.SubPosition;

@Repository
public class SubPositionDaoImpl implements SubPositionDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public SubPosition addSubPosition(SubPosition subPosition) {
		em.persist(subPosition);
		return subPosition;
	}

	@Transactional
	public SubPosition deleteSubPosition(int id) {
		SubPosition subPosition = getSubPositionById(id);
		em.remove(subPosition);
		return subPosition;
	}

	@Transactional
	public SubPosition updateSubPosition(SubPosition subPosition) {
		em.merge(subPosition);
		return subPosition;
	}

	@Transactional
	public SubPosition getSubPositionById(int id) {
		SubPosition as = em.find(SubPosition.class, id);
		if(as==null) throw new RuntimeException("SubPosition introuvable");
		return as;
	}

	@Transactional
	public List<SubPosition> getAllSubPositions() {
		Query req = em.createQuery("select x from SubPosition");
		return req.getResultList();
	}

}
