package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Structure;

@Repository
public class StructureDaoImpl implements StructureDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Structure addStructure(Structure structure) {
		em.persist(structure);
		return structure;
	}

	@Transactional
	public Structure deleteStructure(int id) {
		Structure structure = getStructureById(id);
		em.remove(structure);
		return structure;
	}

	@Transactional
	public Structure updateStructure(Structure structure) {
		em.merge(structure);
		return structure;
	}

	@Transactional
	public Structure getStructureById(int id) {
		Structure as = em.find(Structure.class, id);
		if(as==null) throw new RuntimeException("Structure introuvable");
		return as;
	}

	@Transactional
	public List<Structure> getAllStructures() {
		Query req = em.createQuery("select x from Structure");
		return req.getResultList();
	}

}
