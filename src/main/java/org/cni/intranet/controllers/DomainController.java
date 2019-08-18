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

import org.cni.intranet.entities.Domain;
import org.cni.intranet.service.DomainService;

@Controller
public class DomainController {

	@Autowired
	private DomainService domainService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(DomainController.class);

	@RequestMapping(value = "/rest/domains/{domainId}", method = RequestMethod.GET)
	public ModelAndView getDomain(@PathVariable("domainId") int domainId_p) {
		Domain domain = null;

		/* validate domain Id parameter */
		if (domainId_p<=0) {
			String sMessage = "Error invoking getDomain - Invalid domain Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			domain = domainService.getDomainById(domainId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getDomain. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Domain: " + domain.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, domain);
	}

	@RequestMapping(value = "/rest/domains/", method = RequestMethod.GET)
	public ModelAndView getDomains() {
		List<Domain> domains = null;

		try {
			domains = domainService.getAllDomains();
		} catch (Exception e) {
			String sMessage = "Error getting all domains. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Domains: " + domains.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, domains);
	}

	@RequestMapping(value = { "/rest/domains/" }, method = { RequestMethod.POST })
	public ModelAndView createDomain(@RequestBody Domain domain_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Domain: " + domain_p.toString() );
		System.out.println( "Creating Domain: " + domain_p.getDomainId() );

		Domain createdDomain;
		logger_c.debug("Creating Domain: " + domain_p.toString());

		try {
			createdDomain = domainService.addDomain(domain_p);
		} catch (Exception e) {
			String sMessage = "Error creating new domain. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/domains/" + domain_p.getDomainId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdDomain);
	}

	@RequestMapping(value = { "/rest/domains/{domainId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateDomain(@RequestBody Domain domain_p, @PathVariable("domainId") String domainId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Domain: " + domain_p.toString());

		/* validate domain Id parameter */
		if (isEmpty(domainId_p) || domainId_p.length() < 5) {
			String sMessage = "Error updating domain - Invalid domain Id parameter";
			return createErrorResponse(sMessage);
		}

		Domain domain = null;

		try {
			domain = domainService.updateDomain(domain_p);
		} catch (Exception e) {
			String sMessage = "Error updating domain. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, domain);
	}

	@RequestMapping(value = "/rest/domains/{domainId}", method = RequestMethod.DELETE)
	public ModelAndView removeDomain(@PathVariable("domainId") int domainId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Domain Id: " + domainId_p);

		/* validate domain Id parameter */
		if (domainId_p<= 0) {
			String sMessage = "Error deleting domain - Invalid domain Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			domainService.deleteDomain(domainId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getDomains. [%1$s]";
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

	public void setDomainService(DomainService domainService_p) {
		domainService = domainService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
