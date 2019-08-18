package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Type;

@Repository
public class TypeDaoImpl implements TypeDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Type addType(Type type) {
		em.persist(type);
		return type;
	}

	@Transactional
	public Type deleteType(int id) {
		Type type = getTypeById(id);
		em.remove(type);
		return type;
	}

	@Transactional
	public Type updateType(Type type) {
		em.merge(type);
		return type;
	}

	@Transactional
	public Type getTypeById(int id) {
		Type as = em.find(Type.class, id);
		if(as==null) throw new RuntimeException("Type introuvable");
		return as;
	}

	@Transactional
	public List<Type> getAllTypes() {
		Query req = em.createQuery("select x from Type");
		return req.getResultList();
	}

}
