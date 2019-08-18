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

import org.cni.intranet.entities.FaxLocal;
import org.cni.intranet.service.FaxLocalService;

@Controller
public class FaxLocalController {

	@Autowired
	private FaxLocalService faxLocalService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(FaxLocalController.class);

	@RequestMapping(value = "/rest/faxLocals/{faxLocalId}", method = RequestMethod.GET)
	public ModelAndView getFaxLocal(@PathVariable("faxLocalId") int faxLocalId_p) {
		FaxLocal faxLocal = null;

		/* validate faxLocal Id parameter */
		if (faxLocalId_p<=0) {
			String sMessage = "Error invoking getFaxLocal - Invalid faxLocal Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			faxLocal = faxLocalService.getFaxLocalById(faxLocalId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getFaxLocal. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing FaxLocal: " + faxLocal.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, faxLocal);
	}

	@RequestMapping(value = "/rest/faxLocals/", method = RequestMethod.GET)
	public ModelAndView getFaxLocals() {
		List<FaxLocal> faxLocals = null;

		try {
			faxLocals = faxLocalService.getAllFaxLocals();
		} catch (Exception e) {
			String sMessage = "Error getting all faxLocals. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing FaxLocals: " + faxLocals.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, faxLocals);
	}

	@RequestMapping(value = { "/rest/faxLocals/" }, method = { RequestMethod.POST })
	public ModelAndView createFaxLocal(@RequestBody FaxLocal faxLocal_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating FaxLocal: " + faxLocal_p.toString() );
		System.out.println( "Creating FaxLocal: " + faxLocal_p.getFaxLocalId() );

		FaxLocal createdFaxLocal;
		logger_c.debug("Creating FaxLocal: " + faxLocal_p.toString());

		try {
			createdFaxLocal = faxLocalService.addFaxLocal(faxLocal_p);
		} catch (Exception e) {
			String sMessage = "Error creating new faxLocal. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/faxLocals/" + faxLocal_p.getFaxLocalId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdFaxLocal);
	}

	@RequestMapping(value = { "/rest/faxLocals/{faxLocalId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateFaxLocal(@RequestBody FaxLocal faxLocal_p, @PathVariable("faxLocalId") String faxLocalId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating FaxLocal: " + faxLocal_p.toString());

		/* validate faxLocal Id parameter */
		if (isEmpty(faxLocalId_p) || faxLocalId_p.length() < 5) {
			String sMessage = "Error updating faxLocal - Invalid faxLocal Id parameter";
			return createErrorResponse(sMessage);
		}

		FaxLocal faxLocal = null;

		try {
			faxLocal = faxLocalService.updateFaxLocal(faxLocal_p);
		} catch (Exception e) {
			String sMessage = "Error updating faxLocal. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, faxLocal);
	}

	@RequestMapping(value = "/rest/faxLocals/{faxLocalId}", method = RequestMethod.DELETE)
	public ModelAndView removeFaxLocal(@PathVariable("faxLocalId") int faxLocalId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting FaxLocal Id: " + faxLocalId_p);

		/* validate faxLocal Id parameter */
		if (faxLocalId_p<= 0) {
			String sMessage = "Error deleting faxLocal - Invalid faxLocal Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			faxLocalService.deleteFaxLocal(faxLocalId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getFaxLocals. [%1$s]";
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

	public void setFaxLocalService(FaxLocalService faxLocalService_p) {
		faxLocalService = faxLocalService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
