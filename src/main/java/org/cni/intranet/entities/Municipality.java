package org.cni.intranet.entities;

// Generated 20 nov. 2014 12:28:53 by Hibernate Tools 4.0.0

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Municipality generated by hbm2java
 */
@Entity
@Table(name = "municipality", schema = "public")
public class Municipality implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int municipalityId;
	private Gouvernorat gouvernorat;
	private String code;
	private String latinName;
	private String arabicName;
	private Collection<Local> locals;

	public Municipality() {
	
	}

	public Municipality(String code, String latinName, String arabicName) {
		this.code = code;
		this.latinName = latinName;
		this.arabicName = arabicName;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "municipalityId", unique = true, nullable = false)
	public int getMunicipalityId() {
		return this.municipalityId;
	}

	public void setMunicipalityId(int municipalityId) {
		this.municipalityId = municipalityId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gouvernoratId")
	public Gouvernorat getGouvernorat() {
		return this.gouvernorat;
	}

	public void setGouvernorat(Gouvernorat gouvernorat) {
		this.gouvernorat = gouvernorat;
	}

	@Column(name = "code", length = 254)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "latinName", length = 254)
	public String getLatinName() {
		return this.latinName;
	}

	public void setLatinName(String latinName) {
		this.latinName = latinName;
	}

	@Column(name = "arabicName", length = 254)
	public String getArabicName() {
		return this.arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "municipality")
	public Collection<Local> getLocals() {
		return this.locals;
	}

	public void setLocals(Collection<Local> locals) {
		this.locals = locals;
	}

}
