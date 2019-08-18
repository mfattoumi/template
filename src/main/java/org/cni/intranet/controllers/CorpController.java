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

import org.cni.intranet.entities.Corp;
import org.cni.intranet.service.CorpService;

@Controller
public class CorpController {

	@Autowired
	private CorpService corpService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(CorpController.class);

	@RequestMapping(value = "/rest/corps/{corpId}", method = RequestMethod.GET)
	public ModelAndView getCorp(@PathVariable("corpId") int corpId_p) {
		Corp corp = null;

		/* validate corp Id parameter */
		if (corpId_p<=0) {
			String sMessage = "Error invoking getCorp - Invalid corp Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			corp = corpService.getCorpById(corpId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getCorp. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Corp: " + corp.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, corp);
	}

	@RequestMapping(value = "/rest/corps/", method = RequestMethod.GET)
	public ModelAndView getCorps() {
		List<Corp> corps = null;

		try {
			corps = corpService.getAllCorps();
		} catch (Exception e) {
			String sMessage = "Error getting all corps. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Corps: " + corps.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, corps);
	}

	@RequestMapping(value = { "/rest/corps/" }, method = { RequestMethod.POST })
	public ModelAndView createCorp(@RequestBody Corp corp_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Corp: " + corp_p.toString() );
		System.out.println( "Creating Corp: " + corp_p.getCorpId() );

		Corp createdCorp;
		logger_c.debug("Creating Corp: " + corp_p.toString());

		try {
			createdCorp = corpService.addCorp(corp_p);
		} catch (Exception e) {
			String sMessage = "Error creating new corp. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/corps/" + corp_p.getCorpId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdCorp);
	}

	@RequestMapping(value = { "/rest/corps/{corpId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateCorp(@RequestBody Corp corp_p, @PathVariable("corpId") String corpId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Corp: " + corp_p.toString());

		/* validate corp Id parameter */
		if (isEmpty(corpId_p) || corpId_p.length() < 5) {
			String sMessage = "Error updating corp - Invalid corp Id parameter";
			return createErrorResponse(sMessage);
		}

		Corp corp = null;

		try {
			corp = corpService.updateCorp(corp_p);
		} catch (Exception e) {
			String sMessage = "Error updating corp. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, corp);
	}

	@RequestMapping(value = "/rest/corps/{corpId}", method = RequestMethod.DELETE)
	public ModelAndView removeCorp(@PathVariable("corpId") int corpId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Corp Id: " + corpId_p);

		/* validate corp Id parameter */
		if (corpId_p<= 0) {
			String sMessage = "Error deleting corp - Invalid corp Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			corpService.deleteCorp(corpId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getCorps. [%1$s]";
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

	public void setCorpService(CorpService corpService_p) {
		corpService = corpService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
