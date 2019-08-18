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

import org.cni.intranet.entities.FaxEmployee;
import org.cni.intranet.service.FaxEmployeeService;

@Controller
public class FaxEmployeeController {

	@Autowired
	private FaxEmployeeService faxEmployeeService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(FaxEmployeeController.class);

	@RequestMapping(value = "/rest/faxEmployees/{faxEmployeeId}", method = RequestMethod.GET)
	public ModelAndView getFaxEmployee(@PathVariable("faxEmployeeId") int faxEmployeeId_p) {
		FaxEmployee faxEmployee = null;

		/* validate faxEmployee Id parameter */
		if (faxEmployeeId_p<=0) {
			String sMessage = "Error invoking getFaxEmployee - Invalid faxEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			faxEmployee = faxEmployeeService.getFaxEmployeeById(faxEmployeeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getFaxEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing FaxEmployee: " + faxEmployee.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, faxEmployee);
	}

	@RequestMapping(value = "/rest/faxEmployees/", method = RequestMethod.GET)
	public ModelAndView getFaxEmployees() {
		List<FaxEmployee> faxEmployees = null;

		try {
			faxEmployees = faxEmployeeService.getAllFaxEmployees();
		} catch (Exception e) {
			String sMessage = "Error getting all faxEmployees. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing FaxEmployees: " + faxEmployees.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, faxEmployees);
	}

	@RequestMapping(value = { "/rest/faxEmployees/" }, method = { RequestMethod.POST })
	public ModelAndView createFaxEmployee(@RequestBody FaxEmployee faxEmployee_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating FaxEmployee: " + faxEmployee_p.toString() );
		System.out.println( "Creating FaxEmployee: " + faxEmployee_p.getFaxEmployeeId() );

		FaxEmployee createdFaxEmployee;
		logger_c.debug("Creating FaxEmployee: " + faxEmployee_p.toString());

		try {
			createdFaxEmployee = faxEmployeeService.addFaxEmployee(faxEmployee_p);
		} catch (Exception e) {
			String sMessage = "Error creating new faxEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/faxEmployees/" + faxEmployee_p.getFaxEmployeeId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdFaxEmployee);
	}

	@RequestMapping(value = { "/rest/faxEmployees/{faxEmployeeId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateFaxEmployee(@RequestBody FaxEmployee faxEmployee_p, @PathVariable("faxEmployeeId") String faxEmployeeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating FaxEmployee: " + faxEmployee_p.toString());

		/* validate faxEmployee Id parameter */
		if (isEmpty(faxEmployeeId_p) || faxEmployeeId_p.length() < 5) {
			String sMessage = "Error updating faxEmployee - Invalid faxEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		FaxEmployee faxEmployee = null;

		try {
			faxEmployee = faxEmployeeService.updateFaxEmployee(faxEmployee_p);
		} catch (Exception e) {
			String sMessage = "Error updating faxEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, faxEmployee);
	}

	@RequestMapping(value = "/rest/faxEmployees/{faxEmployeeId}", method = RequestMethod.DELETE)
	public ModelAndView removeFaxEmployee(@PathVariable("faxEmployeeId") int faxEmployeeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting FaxEmployee Id: " + faxEmployeeId_p);

		/* validate faxEmployee Id parameter */
		if (faxEmployeeId_p<= 0) {
			String sMessage = "Error deleting faxEmployee - Invalid faxEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			faxEmployeeService.deleteFaxEmployee(faxEmployeeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getFaxEmployees. [%1$s]";
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

	public void setFaxEmployeeService(FaxEmployeeService faxEmployeeService_p) {
		faxEmployeeService = faxEmployeeService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
