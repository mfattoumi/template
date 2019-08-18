package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.PhoneLocal;

@Repository
public class PhoneLocalDaoImpl implements PhoneLocalDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public PhoneLocal addPhoneLocal(PhoneLocal phoneLocal) {
		em.persist(phoneLocal);
		return phoneLocal;
	}

	@Transactional
	public PhoneLocal deletePhoneLocal(int id) {
		PhoneLocal phoneLocal = getPhoneLocalById(id);
		em.remove(phoneLocal);
		return phoneLocal;
	}

	@Transactional
	public PhoneLocal updatePhoneLocal(PhoneLocal phoneLocal) {
		em.merge(phoneLocal);
		return phoneLocal;
	}

	@Transactional
	public PhoneLocal getPhoneLocalById(int id) {
		PhoneLocal as = em.find(PhoneLocal.class, id);
		if(as==null) throw new RuntimeException("PhoneLocal introuvable");
		return as;
	}

	@Transactional
	public List<PhoneLocal> getAllPhoneLocals() {
		Query req = em.createQuery("select x from PhoneLocal");
		return req.getResultList();
	}

}
