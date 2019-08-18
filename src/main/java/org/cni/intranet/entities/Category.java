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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

/**
 * Category generated by hbm2java
 */
@Entity
@Table(name = "category", schema = "public")
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int categoryId;
	private String code;
	private String latinName;
	private String arabicName;
	private Collection<Structure> structures;

	public Category() {
	}

	public Category(String code, String latinName, String arabicName) {
		this.code = code;
		this.latinName = latinName;
		this.arabicName = arabicName;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "categoryId", unique = true, nullable = false)
	public int getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Collection<Structure> getStructures() {
		return this.structures;
	}

	public void setStructures(Collection<Structure> structures) {
		this.structures = structures;
	}

}