package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.WebSite;

@Repository
public class WebSiteDaoImpl implements WebSiteDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public WebSite addWebSite(WebSite webSite) {
		em.persist(webSite);
		return webSite;
	}

	@Transactional
	public WebSite deleteWebSite(int id) {
		WebSite webSite = getWebSiteById(id);
		em.remove(webSite);
		return webSite;
	}

	@Transactional
	public WebSite updateWebSite(WebSite webSite) {
		em.merge(webSite);
		return webSite;
	}

	@Transactional
	public WebSite getWebSiteById(int id) {
		WebSite as = em.find(WebSite.class, id);
		if(as==null) throw new RuntimeException("WebSite introuvable");
		return as;
	}

	@Transactional
	public List<WebSite> getAllWebSites() {
		Query req = em.createQuery("select x from WebSite");
		return req.getResultList();
	}

}
