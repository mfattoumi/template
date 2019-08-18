package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Gouvernorat;

@Repository
public class GouvernoratDaoImpl implements GouvernoratDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Gouvernorat addGouvernorat(Gouvernorat gouvernorat) {
		em.persist(gouvernorat);
		return gouvernorat;
	}

	@Transactional
	public Gouvernorat deleteGouvernorat(int id) {
		Gouvernorat gouvernorat = getGouvernoratById(id);
		em.remove(gouvernorat);
		return gouvernorat;
	}

	@Transactional
	public Gouvernorat updateGouvernorat(Gouvernorat gouvernorat) {
		em.merge(gouvernorat);
		return gouvernorat;
	}

	@Transactional
	public Gouvernorat getGouvernoratById(int id) {
		Gouvernorat as = em.find(Gouvernorat.class, id);
		if(as==null) throw new RuntimeException("Gouvernorat introuvable");
		return as;
	}

	@Transactional
	public List<Gouvernorat> getAllGouvernorats() {
		Query req = em.createQuery("select x from Gouvernorat");
		return req.getResultList();
	}

}
