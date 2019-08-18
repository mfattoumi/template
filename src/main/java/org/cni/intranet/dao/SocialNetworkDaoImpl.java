package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.SocialNetwork;

@Repository
public class SocialNetworkDaoImpl implements SocialNetworkDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public SocialNetwork addSocialNetwork(SocialNetwork socialNetwork) {
		em.persist(socialNetwork);
		return socialNetwork;
	}

	@Transactional
	public SocialNetwork deleteSocialNetwork(int id) {
		SocialNetwork socialNetwork = getSocialNetworkById(id);
		em.remove(socialNetwork);
		return socialNetwork;
	}

	@Transactional
	public SocialNetwork updateSocialNetwork(SocialNetwork socialNetwork) {
		em.merge(socialNetwork);
		return socialNetwork;
	}

	@Transactional
	public SocialNetwork getSocialNetworkById(int id) {
		SocialNetwork as = em.find(SocialNetwork.class, id);
		if(as==null) throw new RuntimeException("SocialNetwork introuvable");
		return as;
	}

	@Transactional
	public List<SocialNetwork> getAllSocialNetworks() {
		Query req = em.createQuery("select x from SocialNetwork");
		return req.getResultList();
	}

}
