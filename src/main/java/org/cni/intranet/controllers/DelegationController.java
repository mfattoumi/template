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

import org.cni.intranet.entities.Delegation;
import org.cni.intranet.service.DelegationService;

@Controller
public class DelegationController {

	@Autowired
	private DelegationService delegationService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(DelegationController.class);

	@RequestMapping(value = "/rest/delegations/{delegationId}", method = RequestMethod.GET)
	public ModelAndView getDelegation(@PathVariable("delegationId") int delegationId_p) {
		Delegation delegation = null;

		/* validate delegation Id parameter */
		if (delegationId_p<=0) {
			String sMessage = "Error invoking getDelegation - Invalid delegation Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			delegation = delegationService.getDelegationById(delegationId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getDelegation. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Delegation: " + delegation.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, delegation);
	}

	@RequestMapping(value = "/rest/delegations/", method = RequestMethod.GET)
	public ModelAndView getDelegations() {
		List<Delegation> delegations = null;

		try {
			delegations = delegationService.getAllDelegations();
		} catch (Exception e) {
			String sMessage = "Error getting all delegations. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Delegations: " + delegations.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, delegations);
	}

	@RequestMapping(value = { "/rest/delegations/" }, method = { RequestMethod.POST })
	public ModelAndView createDelegation(@RequestBody Delegation delegation_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Delegation: " + delegation_p.toString() );
		System.out.println( "Creating Delegation: " + delegation_p.getDelegationId() );

		Delegation createdDelegation;
		logger_c.debug("Creating Delegation: " + delegation_p.toString());

		try {
			createdDelegation = delegationService.addDelegation(delegation_p);
		} catch (Exception e) {
			String sMessage = "Error creating new delegation. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/delegations/" + delegation_p.getDelegationId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdDelegation);
	}

	@RequestMapping(value = { "/rest/delegations/{delegationId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateDelegation(@RequestBody Delegation delegation_p, @PathVariable("delegationId") String delegationId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Delegation: " + delegation_p.toString());

		/* validate delegation Id parameter */
		if (isEmpty(delegationId_p) || delegationId_p.length() < 5) {
			String sMessage = "Error updating delegation - Invalid delegation Id parameter";
			return createErrorResponse(sMessage);
		}

		Delegation delegation = null;

		try {
			delegation = delegationService.updateDelegation(delegation_p);
		} catch (Exception e) {
			String sMessage = "Error updating delegation. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, delegation);
	}

	@RequestMapping(value = "/rest/delegations/{delegationId}", method = RequestMethod.DELETE)
	public ModelAndView removeDelegation(@PathVariable("delegationId") int delegationId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Delegation Id: " + delegationId_p);

		/* validate delegation Id parameter */
		if (delegationId_p<= 0) {
			String sMessage = "Error deleting delegation - Invalid delegation Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			delegationService.deleteDelegation(delegationId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getDelegations. [%1$s]";
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

	public void setDelegationService(DelegationService delegationService_p) {
		delegationService = delegationService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
