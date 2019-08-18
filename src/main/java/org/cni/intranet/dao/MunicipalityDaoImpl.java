package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Municipality;

@Repository
public class MunicipalityDaoImpl implements MunicipalityDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Municipality addMunicipality(Municipality municipality) {
		em.persist(municipality);
		return municipality;
	}

	@Transactional
	public Municipality deleteMunicipality(int id) {
		Municipality municipality = getMunicipalityById(id);
		em.remove(municipality);
		return municipality;
	}

	@Transactional
	public Municipality updateMunicipality(Municipality municipality) {
		em.merge(municipality);
		return municipality;
	}

	@Transactional
	public Municipality getMunicipalityById(int id) {
		Municipality as = em.find(Municipality.class, id);
		if(as==null) throw new RuntimeException("Municipality introuvable");
		return as;
	}

	@Transactional
	public List<Municipality> getAllMunicipalitys() {
		Query req = em.createQuery("select x from Municipality");
		return req.getResultList();
	}

}
