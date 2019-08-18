package org.cni.intranet.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.cni.intranet.entities.Delegation;
import org.cni.intranet.entities.Domain;
import org.cni.intranet.entities.Gouvernorat;
import org.cni.intranet.entities.Structure;

public class StructuresByGouvernoratDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Gouvernorat gouvernorat;
	private Delegation delegation;
	private Domain domain;
	private List<Structure> listStructures;
	private List<String> listAlphabet;
	
	
	
	public StructuresByGouvernoratDTO() {
		this.listStructures = new ArrayList<Structure>();
		this.listAlphabet = new ArrayList<String>();
	}


	public Gouvernorat getGouvernorat() {
		return gouvernorat;
	}


	public void setGouvernorat(Gouvernorat gouvernorat) {
		this.gouvernorat = gouvernorat;
	}

	public Delegation getDelegation() {
		return delegation;
	}


	public void setDelegation(Delegation delegation) {
		this.delegation = delegation;
	}


	public Domain getDomain() {
		return domain;
	}


	public void setDomain(Domain domain) {
		this.domain = domain;
	}


	public List<Structure> getListStructures() {
		return listStructures;
	}


	public void setListStructures(List<Structure> listStructures) {
		this.listStructures = listStructures;
	}


	public List<String> getListAlphabet() {
		return listAlphabet;
	}


	public void setListAlphabet(List<String> listAlphabet) {
		this.listAlphabet = listAlphabet;
	}




	
	
	
	
	
	
	

}
