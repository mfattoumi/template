package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Function;

@Repository
public class FunctionDaoImpl implements FunctionDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Function addFunction(Function function) {
		em.persist(function);
		return function;
	}

	@Transactional
	public Function deleteFunction(int id) {
		Function function = getFunctionById(id);
		em.remove(function);
		return function;
	}

	@Transactional
	public Function updateFunction(Function function) {
		em.merge(function);
		return function;
	}

	@Transactional
	public Function getFunctionById(int id) {
		Function as = em.find(Function.class, id);
		if(as==null) throw new RuntimeException("Function introuvable");
		return as;
	}

	@Transactional
	public List<Function> getAllFunctions() {
		Query req = em.createQuery("select x from Function");
		return req.getResultList();
	}

}
