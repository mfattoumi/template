package org.cni.intranet.entities;

// Generated 20 nov. 2014 12:28:53 by Hibernate Tools 4.0.0

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SubPosition generated by hbm2java
 */
@Entity
@Table(name = "subPosition", schema = "public")
public class SubPosition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SubPositionId id;
	private Position position;
	private String code;
	private String latinName;
	private String arabicName;
	private Collection<Employee> employees;

	public SubPosition() {
		
	}

	public SubPosition(SubPositionId id, Position position) {
		this.id = id;
		this.position = position;
	}

	public SubPosition(SubPositionId id,String code,
			String latinName, String arabicName) {
		this.id = id;
		this.code = code;
		this.latinName = latinName;
		this.arabicName = arabicName;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "positionId", column = @Column(name = "positionId", nullable = false)),
			@AttributeOverride(name = "subPositionId", column = @Column(name = "subPositionId", nullable = false)) })
	public SubPositionId getId() {
		return this.id;
	}

	public void setId(SubPositionId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "positionId", nullable = false, insertable = false, updatable = false)
	public Position getPosition() {
		return this.position;
	}

	public void setPosition(Position position) {
		this.position = position;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subPosition")
	public Collection<Employee> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;
	}

}
