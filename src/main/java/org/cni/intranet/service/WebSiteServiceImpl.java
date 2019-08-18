package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.WebSiteDao;
import org.cni.intranet.entities.WebSite;

@Transactional
public class WebSiteServiceImpl implements WebSiteService {

	@Autowired
	private WebSiteDao webSiteDao;

	public void setWebSiteDao(WebSiteDao webSiteDao) {
		this.webSiteDao = webSiteDao;
	}
	
	@Override
	public WebSite addWebSite(WebSite webSite) {
		return webSiteDao.addWebSite(webSite);
	}

	@Override
	public WebSite deleteWebSite(int id) {
		return webSiteDao.deleteWebSite(id);
	}

	@Override
	public WebSite updateWebSite(WebSite webSite) {
		return webSiteDao.updateWebSite(webSite);
	}

	@Override
	public WebSite getWebSiteById(int id) {
		return webSiteDao.getWebSiteById(id);
	}

	@Override
	public List<WebSite> getAllWebSites() {
		return webSiteDao.getAllWebSites();
	}

}
