package org.cni.intranet.dao;
/**/
import java.util.List;

import org.cni.intranet.entities.Employee;

public interface EmployeeDao {
	public Employee addEmployee(Employee employee);
	public Employee deleteEmployee(int id);
	public Employee updateEmployee(Employee employee);
	public Employee getEmployeeById(int id);
	public List<Employee> getAllEmployees();
}