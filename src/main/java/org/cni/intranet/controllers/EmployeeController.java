package org.cni.intranet.controllers;

import org.apache.log4j.Logger;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import org.cni.intranet.entities.Employee;
import org.cni.intranet.service.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(EmployeeController.class);

	@RequestMapping(value = "/rest/employees/{employeeId}", method = RequestMethod.GET)
	public ModelAndView getEmployee(@PathVariable("employeeId") int employeeId_p) {
		Employee employee = null;

		/* validate employee Id parameter */
		if (employeeId_p<=0) {
			String sMessage = "Error invoking getEmployee - Invalid employee Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			employee = employeeService.getEmployeeById(employeeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Employee: " + employee.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, employee);
	}

	@RequestMapping(value = "/rest/employees/", method = RequestMethod.GET)
	public ModelAndView getEmployees() {
		List<Employee> employees = null;

		try {
			employees = employeeService.getAllEmployees();
		} catch (Exception e) {
			String sMessage = "Error getting all employees. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Employees: " + employees.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, employees);
	}

	@RequestMapping(value = { "/rest/employees/" }, method = { RequestMethod.POST })
	public ModelAndView createEmployee(@RequestBody Employee employee_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Employee: " + employee_p.toString() );
		System.out.println( "Creating Employee: " + employee_p.getEmployeeId() );

		Employee createdEmployee;
		logger_c.debug("Creating Employee: " + employee_p.toString());

		try {
			createdEmployee = employeeService.addEmployee(employee_p);
		} catch (Exception e) {
			String sMessage = "Error creating new employee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/employees/" + employee_p.getEmployeeId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdEmployee);
	}

	@RequestMapping(value = { "/rest/employees/{employeeId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateEmployee(@RequestBody Employee employee_p, @PathVariable("employeeId") String employeeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Employee: " + employee_p.toString());

		/* validate employee Id parameter */
		if (isEmpty(employeeId_p) || employeeId_p.length() < 5) {
			String sMessage = "Error updating employee - Invalid employee Id parameter";
			return createErrorResponse(sMessage);
		}

		Employee employee = null;

		try {
			employee = employeeService.updateEmployee(employee_p);
		} catch (Exception e) {
			String sMessage = "Error updating employee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, employee);
	}

	@RequestMapping(value = "/rest/employees/{employeeId}", method = RequestMethod.DELETE)
	public ModelAndView removeEmployee(@PathVariable("employeeId") int employeeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Employee Id: " + employeeId_p);

		/* validate employee Id parameter */
		if (employeeId_p<= 0) {
			String sMessage = "Error deleting employee - Invalid employee Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			employeeService.deleteEmployee(employeeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getEmployees. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, null);
	}

	public static boolean isEmpty(String s) {
		return (null == s) || s.trim().length() == 0;
	}

	private ModelAndView createErrorResponse(String s) {
		return new ModelAndView(jsonView_i, ERROR_FIELD, s);
	}

	public void setEmployeeService(EmployeeService employeeService_p) {
		employeeService = employeeService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
