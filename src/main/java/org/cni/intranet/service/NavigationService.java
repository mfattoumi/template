package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.dto.StructuresByGouvernoratDTO;
import org.cni.intranet.entities.Employee;
import org.cni.intranet.entities.Structure;

public interface NavigationService {
	
	public List<Object[]> getStructuresByGouvernoratByType(int id);

	public List<Object[]> getStructuresByDelegationByGouv(int id);

	List<Object[]> getStructuresByDomainByGouv(int id);
	
	List<Employee> getAllEmployeeByStructureId(int id);

	List<Employee> getAllResponsablesByStructureId(int id);

	public List<Employee> getAllContactsByStructureId(int id);

	List<Structure> getAllSubstrutursByStructureId(int id);

	
	
	

}
