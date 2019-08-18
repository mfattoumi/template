package org.cni.intranet.entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "activitySector", schema = "public")
public class ActivitySector implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int activitySectorId;
	private Domain domain;
	private String code;
	private String latinName;
	private String arabicName;
	private Collection<Structure> structures;

	public ActivitySector() {
		
	}

	public ActivitySector(String code, String latinName, String arabicName) {
		this.code = code;
		this.latinName = latinName;
		this.arabicName = arabicName;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "activitySectorId", unique = true, nullable = false)
	public int getActivitySectorid() {
		return this.activitySectorId;
	}

	public void setActivitySectorid(int activitySectorId) {
		this.activitySectorId = activitySectorId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@IndexedEmbedded
	@JoinColumn(name = "domainId")
	public Domain getDomain() {
		return this.domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@Column(name = "code", length = 254)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "latinName", length = 254)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	public String getLatinName() {
		return this.latinName;
	}

	public void setLatinName(String latinName) {
		this.latinName = latinName;
	}

	@Column(name = "arabicName", length = 254)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	public String getArabicName() {
		return this.arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "activitySectors")
	public Collection<Structure> getStructures() {
		return this.structures;
	}

	public void setStructures(Collection<Structure> structures) {
		this.structures = structures;
	}
	
	

}
