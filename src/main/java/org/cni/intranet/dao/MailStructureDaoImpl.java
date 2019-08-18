package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.MailStructure;

@Repository
public class MailStructureDaoImpl implements MailStructureDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public MailStructure addMailStructure(MailStructure mailStructure) {
		em.persist(mailStructure);
		return mailStructure;
	}

	@Transactional
	public MailStructure deleteMailStructure(int id) {
		MailStructure mailStructure = getMailStructureById(id);
		em.remove(mailStructure);
		return mailStructure;
	}

	@Transactional
	public MailStructure updateMailStructure(MailStructure mailStructure) {
		em.merge(mailStructure);
		return mailStructure;
	}

	@Transactional
	public MailStructure getMailStructureById(int id) {
		MailStructure as = em.find(MailStructure.class, id);
		if(as==null) throw new RuntimeException("MailStructure introuvable");
		return as;
	}

	@Transactional
	public List<MailStructure> getAllMailStructures() {
		Query req = em.createQuery("select x from MailStructure");
		return req.getResultList();
	}

}
