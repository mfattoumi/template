package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Country;

@Repository
public class CountryDaoImpl implements CountryDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Country addCountry(Country country) {
		em.persist(country);
		return country;
	}

	@Transactional
	public Country deleteCountry(int id) {
		Country country = getCountryById(id);
		em.remove(country);
		return country;
	}

	@Transactional
	public Country updateCountry(Country country) {
		em.merge(country);
		return country;
	}

	@Transactional
	public Country getCountryById(int id) {
		Country as = em.find(Country.class, id);
		if(as==null) throw new RuntimeException("Country introuvable");
		return as;
	}

	@Transactional
	public List<Country> getAllCountrys() {
		Query req = em.createQuery("select x from Country");
		return req.getResultList();
	}

}
