package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Employee;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Employee addEmployee(Employee employee) {
		em.persist(employee);
		return employee;
	}

	@Transactional
	public Employee deleteEmployee(int id) {
		Employee employee = getEmployeeById(id);
		em.remove(employee);
		return employee;
	}

	@Transactional
	public Employee updateEmployee(Employee employee) {
		em.merge(employee);
		return employee;
	}

	@Transactional
	public Employee getEmployeeById(int id) {
		Employee as = em.find(Employee.class, id);
		if(as==null) throw new RuntimeException("Employee introuvable");
		return as;
	}

	@Transactional
	public List<Employee> getAllEmployees() {
		Query req = em.createQuery("select x from Employee");
		return req.getResultList();
	}

}
