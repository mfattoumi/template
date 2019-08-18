package org.cni.intranet.entities;

// Generated 20 nov. 2014 12:28:53 by Hibernate Tools 4.0.0

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * WebSite generated by hbm2java
 */
@Entity
@Table(name = "webSite", schema = "public")
public class WebSite implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int webSiteId;
	private String adress;
	private Set<Structure> structures = new HashSet<Structure>(0);

	public WebSite() {
	}

	public WebSite(int webSiteId) {
		this.webSiteId = webSiteId;
	}

	public WebSite(int webSiteId, String adress, Set<Structure> structures) {
		this.webSiteId = webSiteId;
		this.adress = adress;
		this.structures = structures;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "webSiteId", unique = true, nullable = false)
	public int getWebSiteId() {
		return this.webSiteId;
	}

	public void setWebSiteId(int webSiteId) {
		this.webSiteId = webSiteId;
	}

	@Column(name = "adress", length = 254)
	public String getAdress() {
		return this.adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "webSites")
	public Set<Structure> getStructures() {
		return this.structures;
	}

	public void setStructures(Set<Structure> structures) {
		this.structures = structures;
	}

}
