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

import org.cni.intranet.entities.PhoneEmployee;
import org.cni.intranet.service.PhoneEmployeeService;

@Controller
public class PhoneEmployeeController {

	@Autowired
	private PhoneEmployeeService phoneEmployeeService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(PhoneEmployeeController.class);

	@RequestMapping(value = "/rest/phoneEmployees/{phoneEmployeeId}", method = RequestMethod.GET)
	public ModelAndView getPhoneEmployee(@PathVariable("phoneEmployeeId") int phoneEmployeeId_p) {
		PhoneEmployee phoneEmployee = null;

		/* validate phoneEmployee Id parameter */
		if (phoneEmployeeId_p<=0) {
			String sMessage = "Error invoking getPhoneEmployee - Invalid phoneEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			phoneEmployee = phoneEmployeeService.getPhoneEmployeeById(phoneEmployeeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getPhoneEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing PhoneEmployee: " + phoneEmployee.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, phoneEmployee);
	}

	@RequestMapping(value = "/rest/phoneEmployees/", method = RequestMethod.GET)
	public ModelAndView getPhoneEmployees() {
		List<PhoneEmployee> phoneEmployees = null;

		try {
			phoneEmployees = phoneEmployeeService.getAllPhoneEmployees();
		} catch (Exception e) {
			String sMessage = "Error getting all phoneEmployees. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing PhoneEmployees: " + phoneEmployees.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, phoneEmployees);
	}

	@RequestMapping(value = { "/rest/phoneEmployees/" }, method = { RequestMethod.POST })
	public ModelAndView createPhoneEmployee(@RequestBody PhoneEmployee phoneEmployee_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating PhoneEmployee: " + phoneEmployee_p.toString() );
		System.out.println( "Creating PhoneEmployee: " + phoneEmployee_p.getPhoneEmployeeId() );

		PhoneEmployee createdPhoneEmployee;
		logger_c.debug("Creating PhoneEmployee: " + phoneEmployee_p.toString());

		try {
			createdPhoneEmployee = phoneEmployeeService.addPhoneEmployee(phoneEmployee_p);
		} catch (Exception e) {
			String sMessage = "Error creating new phoneEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/phoneEmployees/" + phoneEmployee_p.getPhoneEmployeeId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdPhoneEmployee);
	}

	@RequestMapping(value = { "/rest/phoneEmployees/{phoneEmployeeId}" }, method = { RequestMethod.PUT })
	public ModelAndView updatePhoneEmployee(@RequestBody PhoneEmployee phoneEmployee_p, @PathVariable("phoneEmployeeId") String phoneEmployeeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating PhoneEmployee: " + phoneEmployee_p.toString());

		/* validate phoneEmployee Id parameter */
		if (isEmpty(phoneEmployeeId_p) || phoneEmployeeId_p.length() < 5) {
			String sMessage = "Error updating phoneEmployee - Invalid phoneEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		PhoneEmployee phoneEmployee = null;

		try {
			phoneEmployee = phoneEmployeeService.updatePhoneEmployee(phoneEmployee_p);
		} catch (Exception e) {
			String sMessage = "Error updating phoneEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, phoneEmployee);
	}

	@RequestMapping(value = "/rest/phoneEmployees/{phoneEmployeeId}", method = RequestMethod.DELETE)
	public ModelAndView removePhoneEmployee(@PathVariable("phoneEmployeeId") int phoneEmployeeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting PhoneEmployee Id: " + phoneEmployeeId_p);

		/* validate phoneEmployee Id parameter */
		if (phoneEmployeeId_p<= 0) {
			String sMessage = "Error deleting phoneEmployee - Invalid phoneEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			phoneEmployeeService.deletePhoneEmployee(phoneEmployeeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getPhoneEmployees. [%1$s]";
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

	public void setPhoneEmployeeService(PhoneEmployeeService phoneEmployeeService_p) {
		phoneEmployeeService = phoneEmployeeService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
