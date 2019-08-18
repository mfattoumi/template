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

import org.cni.intranet.entities.Local;
import org.cni.intranet.service.LocalService;

@Controller
public class LocalController {

	@Autowired
	private LocalService localService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(LocalController.class);

	@RequestMapping(value = "/rest/locals/{localId}", method = RequestMethod.GET)
	public ModelAndView getLocal(@PathVariable("localId") int localId_p) {
		Local local = null;

		/* validate local Id parameter */
		if (localId_p<=0) {
			String sMessage = "Error invoking getLocal - Invalid local Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			local = localService.getLocalById(localId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getLocal. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Local: " + local.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, local);
	}

	@RequestMapping(value = "/rest/locals/", method = RequestMethod.GET)
	public ModelAndView getLocals() {
		List<Local> locals = null;

		try {
			locals = localService.getAllLocals();
		} catch (Exception e) {
			String sMessage = "Error getting all locals. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Locals: " + locals.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, locals);
	}

	@RequestMapping(value = { "/rest/locals/" }, method = { RequestMethod.POST })
	public ModelAndView createLocal(@RequestBody Local local_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Local: " + local_p.toString() );
		System.out.println( "Creating Local: " + local_p.getLocalId() );

		Local createdLocal;
		logger_c.debug("Creating Local: " + local_p.toString());

		try {
			createdLocal = localService.addLocal(local_p);
		} catch (Exception e) {
			String sMessage = "Error creating new local. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/locals/" + local_p.getLocalId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdLocal);
	}

	@RequestMapping(value = { "/rest/locals/{localId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateLocal(@RequestBody Local local_p, @PathVariable("localId") String localId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Local: " + local_p.toString());

		/* validate local Id parameter */
		if (isEmpty(localId_p) || localId_p.length() < 5) {
			String sMessage = "Error updating local - Invalid local Id parameter";
			return createErrorResponse(sMessage);
		}

		Local local = null;

		try {
			local = localService.updateLocal(local_p);
		} catch (Exception e) {
			String sMessage = "Error updating local. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, local);
	}

	@RequestMapping(value = "/rest/locals/{localId}", method = RequestMethod.DELETE)
	public ModelAndView removeLocal(@PathVariable("localId") int localId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Local Id: " + localId_p);

		/* validate local Id parameter */
		if (localId_p<= 0) {
			String sMessage = "Error deleting local - Invalid local Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			localService.deleteLocal(localId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getLocals. [%1$s]";
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

	public void setLocalService(LocalService localService_p) {
		localService = localService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
