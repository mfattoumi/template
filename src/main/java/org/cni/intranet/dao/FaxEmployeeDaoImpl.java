package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.FaxEmployee;

@Repository
public class FaxEmployeeDaoImpl implements FaxEmployeeDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public FaxEmployee addFaxEmployee(FaxEmployee faxEmployee) {
		em.persist(faxEmployee);
		return faxEmployee;
	}

	@Transactional
	public FaxEmployee deleteFaxEmployee(int id) {
		FaxEmployee faxEmployee = getFaxEmployeeById(id);
		em.remove(faxEmployee);
		return faxEmployee;
	}

	@Transactional
	public FaxEmployee updateFaxEmployee(FaxEmployee faxEmployee) {
		em.merge(faxEmployee);
		return faxEmployee;
	}

	@Transactional
	public FaxEmployee getFaxEmployeeById(int id) {
		FaxEmployee as = em.find(FaxEmployee.class, id);
		if(as==null) throw new RuntimeException("FaxEmployee introuvable");
		return as;
	}

	@Transactional
	public List<FaxEmployee> getAllFaxEmployees() {
		Query req = em.createQuery("select x from FaxEmployee");
		return req.getResultList();
	}

}
