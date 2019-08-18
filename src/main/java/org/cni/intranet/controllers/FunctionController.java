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

import org.cni.intranet.entities.Function;
import org.cni.intranet.service.FunctionService;

@Controller
public class FunctionController {

	@Autowired
	private FunctionService functionService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(FunctionController.class);

	@RequestMapping(value = "/rest/functions/{functionId}", method = RequestMethod.GET)
	public ModelAndView getFunction(@PathVariable("functionId") int functionId_p) {
		Function function = null;

		/* validate function Id parameter */
		if (functionId_p<=0) {
			String sMessage = "Error invoking getFunction - Invalid function Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			function = functionService.getFunctionById(functionId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getFunction. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Function: " + function.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, function);
	}

	@RequestMapping(value = "/rest/functions/", method = RequestMethod.GET)
	public ModelAndView getFunctions() {
		List<Function> functions = null;

		try {
			functions = functionService.getAllFunctions();
		} catch (Exception e) {
			String sMessage = "Error getting all functions. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Functions: " + functions.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, functions);
	}

	@RequestMapping(value = { "/rest/functions/" }, method = { RequestMethod.POST })
	public ModelAndView createFunction(@RequestBody Function function_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Function: " + function_p.toString() );
		System.out.println( "Creating Function: " + function_p.getFunctionId() );

		Function createdFunction;
		logger_c.debug("Creating Function: " + function_p.toString());

		try {
			createdFunction = functionService.addFunction(function_p);
		} catch (Exception e) {
			String sMessage = "Error creating new function. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/functions/" + function_p.getFunctionId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdFunction);
	}

	@RequestMapping(value = { "/rest/functions/{functionId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateFunction(@RequestBody Function function_p, @PathVariable("functionId") String functionId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Function: " + function_p.toString());

		/* validate function Id parameter */
		if (isEmpty(functionId_p) || functionId_p.length() < 5) {
			String sMessage = "Error updating function - Invalid function Id parameter";
			return createErrorResponse(sMessage);
		}

		Function function = null;

		try {
			function = functionService.updateFunction(function_p);
		} catch (Exception e) {
			String sMessage = "Error updating function. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, function);
	}

	@RequestMapping(value = "/rest/functions/{functionId}", method = RequestMethod.DELETE)
	public ModelAndView removeFunction(@PathVariable("functionId") int functionId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Function Id: " + functionId_p);

		/* validate function Id parameter */
		if (functionId_p<= 0) {
			String sMessage = "Error deleting function - Invalid function Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			functionService.deleteFunction(functionId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getFunctions. [%1$s]";
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

	public void setFunctionService(FunctionService functionService_p) {
		functionService = functionService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
