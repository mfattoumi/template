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

import org.cni.intranet.entities.MailEmployee;
import org.cni.intranet.service.MailEmployeeService;

@Controller
public class MailEmployeeController {

	@Autowired
	private MailEmployeeService mailEmployeeService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(MailEmployeeController.class);

	@RequestMapping(value = "/rest/mailEmployees/{mailEmployeeId}", method = RequestMethod.GET)
	public ModelAndView getMailEmployee(@PathVariable("mailEmployeeId") int mailEmployeeId_p) {
		MailEmployee mailEmployee = null;

		/* validate mailEmployee Id parameter */
		if (mailEmployeeId_p<=0) {
			String sMessage = "Error invoking getMailEmployee - Invalid mailEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			mailEmployee = mailEmployeeService.getMailEmployeeById(mailEmployeeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getMailEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing MailEmployee: " + mailEmployee.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, mailEmployee);
	}

	@RequestMapping(value = "/rest/mailEmployees/", method = RequestMethod.GET)
	public ModelAndView getMailEmployees() {
		List<MailEmployee> mailEmployees = null;

		try {
			mailEmployees = mailEmployeeService.getAllMailEmployees();
		} catch (Exception e) {
			String sMessage = "Error getting all mailEmployees. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing MailEmployees: " + mailEmployees.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, mailEmployees);
	}

	@RequestMapping(value = { "/rest/mailEmployees/" }, method = { RequestMethod.POST })
	public ModelAndView createMailEmployee(@RequestBody MailEmployee mailEmployee_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating MailEmployee: " + mailEmployee_p.toString() );
		System.out.println( "Creating MailEmployee: " + mailEmployee_p.getMailEmployeeId() );

		MailEmployee createdMailEmployee;
		logger_c.debug("Creating MailEmployee: " + mailEmployee_p.toString());

		try {
			createdMailEmployee = mailEmployeeService.addMailEmployee(mailEmployee_p);
		} catch (Exception e) {
			String sMessage = "Error creating new mailEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/mailEmployees/" + mailEmployee_p.getMailEmployeeId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdMailEmployee);
	}

	@RequestMapping(value = { "/rest/mailEmployees/{mailEmployeeId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateMailEmployee(@RequestBody MailEmployee mailEmployee_p, @PathVariable("mailEmployeeId") String mailEmployeeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating MailEmployee: " + mailEmployee_p.toString());

		/* validate mailEmployee Id parameter */
		if (isEmpty(mailEmployeeId_p) || mailEmployeeId_p.length() < 5) {
			String sMessage = "Error updating mailEmployee - Invalid mailEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		MailEmployee mailEmployee = null;

		try {
			mailEmployee = mailEmployeeService.updateMailEmployee(mailEmployee_p);
		} catch (Exception e) {
			String sMessage = "Error updating mailEmployee. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, mailEmployee);
	}

	@RequestMapping(value = "/rest/mailEmployees/{mailEmployeeId}", method = RequestMethod.DELETE)
	public ModelAndView removeMailEmployee(@PathVariable("mailEmployeeId") int mailEmployeeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting MailEmployee Id: " + mailEmployeeId_p);

		/* validate mailEmployee Id parameter */
		if (mailEmployeeId_p<= 0) {
			String sMessage = "Error deleting mailEmployee - Invalid mailEmployee Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			mailEmployeeService.deleteMailEmployee(mailEmployeeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getMailEmployees. [%1$s]";
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

	public void setMailEmployeeService(MailEmployeeService mailEmployeeService_p) {
		mailEmployeeService = mailEmployeeService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
