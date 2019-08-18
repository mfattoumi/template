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

import org.cni.intranet.entities.Gouvernorat;
import org.cni.intranet.service.GouvernoratService;

@Controller
public class GouvernoratController {

	@Autowired
	private GouvernoratService gouvernoratService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(GouvernoratController.class);

	@RequestMapping(value = "/rest/gouvernorats/{gouvernoratId}", method = RequestMethod.GET)
	public ModelAndView getGouvernorat(@PathVariable("gouvernoratId") int gouvernoratId_p) {
		Gouvernorat gouvernorat = null;

		/* validate gouvernorat Id parameter */
		if (gouvernoratId_p<=0) {
			String sMessage = "Error invoking getGouvernorat - Invalid gouvernorat Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			gouvernorat = gouvernoratService.getGouvernoratById(gouvernoratId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getGouvernorat. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Gouvernorat: " + gouvernorat.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, gouvernorat);
	}

	@RequestMapping(value = "/rest/gouvernorats/", method = RequestMethod.GET)
	public ModelAndView getGouvernorats() {
		List<Gouvernorat> gouvernorats = null;

		try {
			gouvernorats = gouvernoratService.getAllGouvernorats();
		} catch (Exception e) {
			String sMessage = "Error getting all gouvernorats. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Gouvernorats: " + gouvernorats.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, gouvernorats);
	}

	@RequestMapping(value = { "/rest/gouvernorats/" }, method = { RequestMethod.POST })
	public ModelAndView createGouvernorat(@RequestBody Gouvernorat gouvernorat_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Gouvernorat: " + gouvernorat_p.toString() );
		System.out.println( "Creating Gouvernorat: " + gouvernorat_p.getGouvernoratId() );

		Gouvernorat createdGouvernorat;
		logger_c.debug("Creating Gouvernorat: " + gouvernorat_p.toString());

		try {
			createdGouvernorat = gouvernoratService.addGouvernorat(gouvernorat_p);
		} catch (Exception e) {
			String sMessage = "Error creating new gouvernorat. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/gouvernorats/" + gouvernorat_p.getGouvernoratId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdGouvernorat);
	}

	@RequestMapping(value = { "/rest/gouvernorats/{gouvernoratId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateGouvernorat(@RequestBody Gouvernorat gouvernorat_p, @PathVariable("gouvernoratId") String gouvernoratId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Gouvernorat: " + gouvernorat_p.toString());

		/* validate gouvernorat Id parameter */
		if (isEmpty(gouvernoratId_p) || gouvernoratId_p.length() < 5) {
			String sMessage = "Error updating gouvernorat - Invalid gouvernorat Id parameter";
			return createErrorResponse(sMessage);
		}

		Gouvernorat gouvernorat = null;

		try {
			gouvernorat = gouvernoratService.updateGouvernorat(gouvernorat_p);
		} catch (Exception e) {
			String sMessage = "Error updating gouvernorat. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, gouvernorat);
	}

	@RequestMapping(value = "/rest/gouvernorats/{gouvernoratId}", method = RequestMethod.DELETE)
	public ModelAndView removeGouvernorat(@PathVariable("gouvernoratId") int gouvernoratId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Gouvernorat Id: " + gouvernoratId_p);

		/* validate gouvernorat Id parameter */
		if (gouvernoratId_p<= 0) {
			String sMessage = "Error deleting gouvernorat - Invalid gouvernorat Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			gouvernoratService.deleteGouvernorat(gouvernoratId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getGouvernorats. [%1$s]";
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

	public void setGouvernoratService(GouvernoratService gouvernoratService_p) {
		gouvernoratService = gouvernoratService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
