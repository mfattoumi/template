package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Employee;
import org.cni.intranet.entities.Structure;


public interface EmployeeService {

	Employee addEmployee(Employee employee);

	Employee deleteEmployee(int id);

	Employee updateEmployee(Employee employee);

	Employee getEmployeeById(int id);

	List<Employee> getAllEmployees();

	void indexEmployees();

	List<Employee> searchForEmployee(String searchText);
	
	List<Employee> searchRapidForEmployee( String function, String nomEtPrenom);

	List<Employee> searchAdvForEmployee(String searchedGrade,
			String searchedFunction, String empSearchAdv,
			String selectedGouvernoratLatinName,
			String selectedStructureLatinName);

}
