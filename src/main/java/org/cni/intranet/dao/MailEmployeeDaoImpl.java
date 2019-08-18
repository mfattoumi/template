package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.MailEmployee;

@Repository
public class MailEmployeeDaoImpl implements MailEmployeeDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public MailEmployee addMailEmployee(MailEmployee mailEmployee) {
		em.persist(mailEmployee);
		return mailEmployee;
	}

	@Transactional
	public MailEmployee deleteMailEmployee(int id) {
		MailEmployee mailEmployee = getMailEmployeeById(id);
		em.remove(mailEmployee);
		return mailEmployee;
	}

	@Transactional
	public MailEmployee updateMailEmployee(MailEmployee mailEmployee) {
		em.merge(mailEmployee);
		return mailEmployee;
	}

	@Transactional
	public MailEmployee getMailEmployeeById(int id) {
		MailEmployee as = em.find(MailEmployee.class, id);
		if(as==null) throw new RuntimeException("MailEmployee introuvable");
		return as;
	}

	@Transactional
	public List<MailEmployee> getAllMailEmployees() {
		Query req = em.createQuery("select x from MailEmployee");
		return req.getResultList();
	}

}
