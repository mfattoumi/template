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

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;

/**
 * Local generated by hbm2java
 */
@Entity
@Table(name = "local", schema = "public")
public class Local implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int localId;
	private Country country;
	private Delegation delegation;
	private Municipality municipality;
	private String code;
	private int number;
	private String street;
	private int postalCode;
	private String latinAdress;
	private String arabicAdress;
	private Collection<PhoneLocal> phoneLocals;
	private Collection<FaxLocal> faxLocals;
	private Structure structure;
	
	public Local() {
	
	}

	public Local(String code, int number, String street, int postalCode, 
			String latinAdress, String arabicAdress) {
		this.code = code;
		this.number = number;
		this.street = street;
		this.postalCode = postalCode;
		this.latinAdress = latinAdress;
		this.arabicAdress = arabicAdress;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "localId", unique = true, nullable = false)
	public int getLocalId() {
		return this.localId;
	}

	public void setLocalId(int localId) {
		this.localId = localId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "countryId")
	public Country getCountry() {
		return this.country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@IndexedEmbedded
	@JoinColumn(name = "delegationId")
	public Delegation getDelegation() {
		return this.delegation;
	}

	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "structureId")
	public Structure getStructure() {
		return this.structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "municipalityId")
	public Municipality getMunicipality() {
		return this.municipality;
	}

	public void setMunicipality(Municipality municipality) {
		this.municipality = municipality;
	}

	@Column(name = "code", length = 254)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "number")
	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Column(name = "street", length = 254)
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Column(name = "postalCode")
	public int getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "latinAdress", length = 254)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	public String getLatinAdress() {
		return this.latinAdress;
	}

	public void setLatinAdress(String latinAdress) {
		this.latinAdress = latinAdress;
	}

	@Column(name = "arabicAdress", length = 254)
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
	public String getArabicAdress() {
		return this.arabicAdress;
	}

	public void setArabicAdress(String arabicAdress) {
		this.arabicAdress = arabicAdress;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "local")
	public Collection<PhoneLocal> getPhoneLocals() {
		return this.phoneLocals;
	}

	public void setPhoneLocals(Collection<PhoneLocal> phoneLocals) {
		this.phoneLocals = phoneLocals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "local")
	public Collection<FaxLocal> getFaxLocals() {
		return this.faxLocals;
	}

	public void setFaxLocals(Collection<FaxLocal> faxLocals) {
		this.faxLocals = faxLocals;
	}

}
