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

import org.cni.intranet.entities.PhoneLocal;
import org.cni.intranet.service.PhoneLocalService;

@Controller
public class PhoneLocalController {

	@Autowired
	private PhoneLocalService phoneLocalService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(PhoneLocalController.class);

	@RequestMapping(value = "/rest/phoneLocals/{phoneLocalId}", method = RequestMethod.GET)
	public ModelAndView getPhoneLocal(@PathVariable("phoneLocalId") int phoneLocalId_p) {
		PhoneLocal phoneLocal = null;

		/* validate phoneLocal Id parameter */
		if (phoneLocalId_p<=0) {
			String sMessage = "Error invoking getPhoneLocal - Invalid phoneLocal Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			phoneLocal = phoneLocalService.getPhoneLocalById(phoneLocalId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getPhoneLocal. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing PhoneLocal: " + phoneLocal.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, phoneLocal);
	}

	@RequestMapping(value = "/rest/phoneLocals/", method = RequestMethod.GET)
	public ModelAndView getPhoneLocals() {
		List<PhoneLocal> phoneLocals = null;

		try {
			phoneLocals = phoneLocalService.getAllPhoneLocals();
		} catch (Exception e) {
			String sMessage = "Error getting all phoneLocals. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing PhoneLocals: " + phoneLocals.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, phoneLocals);
	}

	@RequestMapping(value = { "/rest/phoneLocals/" }, method = { RequestMethod.POST })
	public ModelAndView createPhoneLocal(@RequestBody PhoneLocal phoneLocal_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating PhoneLocal: " + phoneLocal_p.toString() );
		System.out.println( "Creating PhoneLocal: " + phoneLocal_p.getPhoneLocalId() );

		PhoneLocal createdPhoneLocal;
		logger_c.debug("Creating PhoneLocal: " + phoneLocal_p.toString());

		try {
			createdPhoneLocal = phoneLocalService.addPhoneLocal(phoneLocal_p);
		} catch (Exception e) {
			String sMessage = "Error creating new phoneLocal. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/phoneLocals/" + phoneLocal_p.getPhoneLocalId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdPhoneLocal);
	}

	@RequestMapping(value = { "/rest/phoneLocals/{phoneLocalId}" }, method = { RequestMethod.PUT })
	public ModelAndView updatePhoneLocal(@RequestBody PhoneLocal phoneLocal_p, @PathVariable("phoneLocalId") String phoneLocalId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating PhoneLocal: " + phoneLocal_p.toString());

		/* validate phoneLocal Id parameter */
		if (isEmpty(phoneLocalId_p) || phoneLocalId_p.length() < 5) {
			String sMessage = "Error updating phoneLocal - Invalid phoneLocal Id parameter";
			return createErrorResponse(sMessage);
		}

		PhoneLocal phoneLocal = null;

		try {
			phoneLocal = phoneLocalService.updatePhoneLocal(phoneLocal_p);
		} catch (Exception e) {
			String sMessage = "Error updating phoneLocal. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, phoneLocal);
	}

	@RequestMapping(value = "/rest/phoneLocals/{phoneLocalId}", method = RequestMethod.DELETE)
	public ModelAndView removePhoneLocal(@PathVariable("phoneLocalId") int phoneLocalId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting PhoneLocal Id: " + phoneLocalId_p);

		/* validate phoneLocal Id parameter */
		if (phoneLocalId_p<= 0) {
			String sMessage = "Error deleting phoneLocal - Invalid phoneLocal Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			phoneLocalService.deletePhoneLocal(phoneLocalId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getPhoneLocals. [%1$s]";
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

	public void setPhoneLocalService(PhoneLocalService phoneLocalService_p) {
		phoneLocalService = phoneLocalService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
