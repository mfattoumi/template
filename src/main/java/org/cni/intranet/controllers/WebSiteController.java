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

import org.cni.intranet.entities.WebSite;
import org.cni.intranet.service.WebSiteService;

@Controller
public class WebSiteController {

	@Autowired
	private WebSiteService webSiteService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(WebSiteController.class);

	@RequestMapping(value = "/rest/webSites/{webSiteId}", method = RequestMethod.GET)
	public ModelAndView getWebSite(@PathVariable("webSiteId") int webSiteId_p) {
		WebSite webSite = null;

		/* validate webSite Id parameter */
		if (webSiteId_p<=0) {
			String sMessage = "Error invoking getWebSite - Invalid webSite Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			webSite = webSiteService.getWebSiteById(webSiteId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getWebSite. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing WebSite: " + webSite.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, webSite);
	}

	@RequestMapping(value = "/rest/webSites/", method = RequestMethod.GET)
	public ModelAndView getWebSites() {
		List<WebSite> webSites = null;

		try {
			webSites = webSiteService.getAllWebSites();
		} catch (Exception e) {
			String sMessage = "Error getting all webSites. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing WebSites: " + webSites.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, webSites);
	}

	@RequestMapping(value = { "/rest/webSites/" }, method = { RequestMethod.POST })
	public ModelAndView createWebSite(@RequestBody WebSite webSite_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating WebSite: " + webSite_p.toString() );
		System.out.println( "Creating WebSite: " + webSite_p.getWebSiteId() );

		WebSite createdWebSite;
		logger_c.debug("Creating WebSite: " + webSite_p.toString());

		try {
			createdWebSite = webSiteService.addWebSite(webSite_p);
		} catch (Exception e) {
			String sMessage = "Error creating new webSite. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/webSites/" + webSite_p.getWebSiteId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdWebSite);
	}

	@RequestMapping(value = { "/rest/webSites/{webSiteId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateWebSite(@RequestBody WebSite webSite_p, @PathVariable("webSiteId") String webSiteId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating WebSite: " + webSite_p.toString());

		/* validate webSite Id parameter */
		if (isEmpty(webSiteId_p) || webSiteId_p.length() < 5) {
			String sMessage = "Error updating webSite - Invalid webSite Id parameter";
			return createErrorResponse(sMessage);
		}

		WebSite webSite = null;

		try {
			webSite = webSiteService.updateWebSite(webSite_p);
		} catch (Exception e) {
			String sMessage = "Error updating webSite. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, webSite);
	}

	@RequestMapping(value = "/rest/webSites/{webSiteId}", method = RequestMethod.DELETE)
	public ModelAndView removeWebSite(@PathVariable("webSiteId") int webSiteId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting WebSite Id: " + webSiteId_p);

		/* validate webSite Id parameter */
		if (webSiteId_p<= 0) {
			String sMessage = "Error deleting webSite - Invalid webSite Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			webSiteService.deleteWebSite(webSiteId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getWebSites. [%1$s]";
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

	public void setWebSiteService(WebSiteService webSiteService_p) {
		webSiteService = webSiteService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
