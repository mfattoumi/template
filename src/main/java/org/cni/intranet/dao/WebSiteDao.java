package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.WebSite;

public interface WebSiteDao {
	public WebSite addWebSite(WebSite webSite);
	public WebSite deleteWebSite(int id);
	public WebSite updateWebSite(WebSite webSite);
	public WebSite getWebSiteById(int id);
	public List<WebSite> getAllWebSites();
}