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

import org.cni.intranet.entities.MailStructure;
import org.cni.intranet.service.MailStructureService;

@Controller
public class MailStructureController {

	@Autowired
	private MailStructureService mailStructureService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(MailStructureController.class);

	@RequestMapping(value = "/rest/mailStructures/{mailStructureId}", method = RequestMethod.GET)
	public ModelAndView getMailStructure(@PathVariable("mailStructureId") int mailStructureId_p) {
		MailStructure mailStructure = null;

		/* validate mailStructure Id parameter */
		if (mailStructureId_p<=0) {
			String sMessage = "Error invoking getMailStructure - Invalid mailStructure Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			mailStructure = mailStructureService.getMailStructureById(mailStructureId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getMailStructure. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing MailStructure: " + mailStructure.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, mailStructure);
	}

	@RequestMapping(value = "/rest/mailStructures/", method = RequestMethod.GET)
	public ModelAndView getMailStructures() {
		List<MailStructure> mailStructures = null;

		try {
			mailStructures = mailStructureService.getAllMailStructures();
		} catch (Exception e) {
			String sMessage = "Error getting all mailStructures. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing MailStructures: " + mailStructures.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, mailStructures);
	}

	@RequestMapping(value = { "/rest/mailStructures/" }, method = { RequestMethod.POST })
	public ModelAndView createMailStructure(@RequestBody MailStructure mailStructure_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating MailStructure: " + mailStructure_p.toString() );
		System.out.println( "Creating MailStructure: " + mailStructure_p.getMailStructureId() );

		MailStructure createdMailStructure;
		logger_c.debug("Creating MailStructure: " + mailStructure_p.toString());

		try {
			createdMailStructure = mailStructureService.addMailStructure(mailStructure_p);
		} catch (Exception e) {
			String sMessage = "Error creating new mailStructure. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/mailStructures/" + mailStructure_p.getMailStructureId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdMailStructure);
	}

	@RequestMapping(value = { "/rest/mailStructures/{mailStructureId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateMailStructure(@RequestBody MailStructure mailStructure_p, @PathVariable("mailStructureId") String mailStructureId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating MailStructure: " + mailStructure_p.toString());

		/* validate mailStructure Id parameter */
		if (isEmpty(mailStructureId_p) || mailStructureId_p.length() < 5) {
			String sMessage = "Error updating mailStructure - Invalid mailStructure Id parameter";
			return createErrorResponse(sMessage);
		}

		MailStructure mailStructure = null;

		try {
			mailStructure = mailStructureService.updateMailStructure(mailStructure_p);
		} catch (Exception e) {
			String sMessage = "Error updating mailStructure. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, mailStructure);
	}

	@RequestMapping(value = "/rest/mailStructures/{mailStructureId}", method = RequestMethod.DELETE)
	public ModelAndView removeMailStructure(@PathVariable("mailStructureId") int mailStructureId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting MailStructure Id: " + mailStructureId_p);

		/* validate mailStructure Id parameter */
		if (mailStructureId_p<= 0) {
			String sMessage = "Error deleting mailStructure - Invalid mailStructure Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			mailStructureService.deleteMailStructure(mailStructureId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getMailStructures. [%1$s]";
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

	public void setMailStructureService(MailStructureService mailStructureService_p) {
		mailStructureService = mailStructureService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
