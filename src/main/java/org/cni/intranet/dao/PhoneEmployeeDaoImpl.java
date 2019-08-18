package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.PhoneEmployee;

@Repository
public class PhoneEmployeeDaoImpl implements PhoneEmployeeDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public PhoneEmployee addPhoneEmployee(PhoneEmployee phoneEmployee) {
		em.persist(phoneEmployee);
		return phoneEmployee;
	}

	@Transactional
	public PhoneEmployee deletePhoneEmployee(int id) {
		PhoneEmployee phoneEmployee = getPhoneEmployeeById(id);
		em.remove(phoneEmployee);
		return phoneEmployee;
	}

	@Transactional
	public PhoneEmployee updatePhoneEmployee(PhoneEmployee phoneEmployee) {
		em.merge(phoneEmployee);
		return phoneEmployee;
	}

	@Transactional
	public PhoneEmployee getPhoneEmployeeById(int id) {
		PhoneEmployee as = em.find(PhoneEmployee.class, id);
		if(as==null) throw new RuntimeException("PhoneEmployee introuvable");
		return as;
	}

	@Transactional
	public List<PhoneEmployee> getAllPhoneEmployees() {
		Query req = em.createQuery("select x from PhoneEmployee");
		return req.getResultList();
	}

}
