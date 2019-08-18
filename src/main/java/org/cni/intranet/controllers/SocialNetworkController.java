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

import org.cni.intranet.entities.SocialNetwork;
import org.cni.intranet.service.SocialNetworkService;

@Controller
public class SocialNetworkController {

	@Autowired
	private SocialNetworkService socialNetworkService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(SocialNetworkController.class);

	@RequestMapping(value = "/rest/socialNetworks/{socialNetworkId}", method = RequestMethod.GET)
	public ModelAndView getSocialNetwork(@PathVariable("socialNetworkId") int socialNetworkId_p) {
		SocialNetwork socialNetwork = null;

		/* validate socialNetwork Id parameter */
		if (socialNetworkId_p<=0) {
			String sMessage = "Error invoking getSocialNetwork - Invalid socialNetwork Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			socialNetwork = socialNetworkService.getSocialNetworkById(socialNetworkId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getSocialNetwork. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing SocialNetwork: " + socialNetwork.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, socialNetwork);
	}

	@RequestMapping(value = "/rest/socialNetworks/", method = RequestMethod.GET)
	public ModelAndView getSocialNetworks() {
		List<SocialNetwork> socialNetworks = null;

		try {
			socialNetworks = socialNetworkService.getAllSocialNetworks();
		} catch (Exception e) {
			String sMessage = "Error getting all socialNetworks. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing SocialNetworks: " + socialNetworks.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, socialNetworks);
	}

	@RequestMapping(value = { "/rest/socialNetworks/" }, method = { RequestMethod.POST })
	public ModelAndView createSocialNetwork(@RequestBody SocialNetwork socialNetwork_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating SocialNetwork: " + socialNetwork_p.toString() );
		System.out.println( "Creating SocialNetwork: " + socialNetwork_p.getSocialNetworkId() );

		SocialNetwork createdSocialNetwork;
		logger_c.debug("Creating SocialNetwork: " + socialNetwork_p.toString());

		try {
			createdSocialNetwork = socialNetworkService.addSocialNetwork(socialNetwork_p);
		} catch (Exception e) {
			String sMessage = "Error creating new socialNetwork. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/socialNetworks/" + socialNetwork_p.getSocialNetworkId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdSocialNetwork);
	}

	@RequestMapping(value = { "/rest/socialNetworks/{socialNetworkId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateSocialNetwork(@RequestBody SocialNetwork socialNetwork_p, @PathVariable("socialNetworkId") String socialNetworkId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating SocialNetwork: " + socialNetwork_p.toString());

		/* validate socialNetwork Id parameter */
		if (isEmpty(socialNetworkId_p) || socialNetworkId_p.length() < 5) {
			String sMessage = "Error updating socialNetwork - Invalid socialNetwork Id parameter";
			return createErrorResponse(sMessage);
		}

		SocialNetwork socialNetwork = null;

		try {
			socialNetwork = socialNetworkService.updateSocialNetwork(socialNetwork_p);
		} catch (Exception e) {
			String sMessage = "Error updating socialNetwork. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, socialNetwork);
	}

	@RequestMapping(value = "/rest/socialNetworks/{socialNetworkId}", method = RequestMethod.DELETE)
	public ModelAndView removeSocialNetwork(@PathVariable("socialNetworkId") int socialNetworkId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting SocialNetwork Id: " + socialNetworkId_p);

		/* validate socialNetwork Id parameter */
		if (socialNetworkId_p<= 0) {
			String sMessage = "Error deleting socialNetwork - Invalid socialNetwork Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			socialNetworkService.deleteSocialNetwork(socialNetworkId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getSocialNetworks. [%1$s]";
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

	public void setSocialNetworkService(SocialNetworkService socialNetworkService_p) {
		socialNetworkService = socialNetworkService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
