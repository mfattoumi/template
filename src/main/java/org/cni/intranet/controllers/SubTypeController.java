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

import org.cni.intranet.entities.SubType;
import org.cni.intranet.service.SubTypeService;

@Controller
public class SubTypeController {

	@Autowired
	private SubTypeService subTypeService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(SubTypeController.class);

	@RequestMapping(value = "/rest/subTypes/{subTypeId}", method = RequestMethod.GET)
	public ModelAndView getSubType(@PathVariable("subTypeId") int subTypeId_p) {
		SubType subType = null;

		/* validate subType Id parameter */
		if (subTypeId_p<=0) {
			String sMessage = "Error invoking getSubType - Invalid subType Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			subType = subTypeService.getSubTypeById(subTypeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getSubType. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing SubType: " + subType.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, subType);
	}

	@RequestMapping(value = "/rest/subTypes/", method = RequestMethod.GET)
	public ModelAndView getSubTypes() {
		List<SubType> subTypes = null;

		try {
			subTypes = subTypeService.getAllSubTypes();
		} catch (Exception e) {
			String sMessage = "Error getting all subTypes. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing SubTypes: " + subTypes.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, subTypes);
	}

	@RequestMapping(value = { "/rest/subTypes/" }, method = { RequestMethod.POST })
	public ModelAndView createSubType(@RequestBody SubType subType_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating SubType: " + subType_p.toString() );
		System.out.println( "Creating SubType: " + subType_p.getSubTypeId() );

		SubType createdSubType;
		logger_c.debug("Creating SubType: " + subType_p.toString());

		try {
			createdSubType = subTypeService.addSubType(subType_p);
		} catch (Exception e) {
			String sMessage = "Error creating new subType. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/subTypes/" + subType_p.getSubTypeId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdSubType);
	}

	@RequestMapping(value = { "/rest/subTypes/{subTypeId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateSubType(@RequestBody SubType subType_p, @PathVariable("subTypeId") String subTypeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating SubType: " + subType_p.toString());

		/* validate subType Id parameter */
		if (isEmpty(subTypeId_p) || subTypeId_p.length() < 5) {
			String sMessage = "Error updating subType - Invalid subType Id parameter";
			return createErrorResponse(sMessage);
		}

		SubType subType = null;

		try {
			subType = subTypeService.updateSubType(subType_p);
		} catch (Exception e) {
			String sMessage = "Error updating subType. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, subType);
	}

	@RequestMapping(value = "/rest/subTypes/{subTypeId}", method = RequestMethod.DELETE)
	public ModelAndView removeSubType(@PathVariable("subTypeId") int subTypeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting SubType Id: " + subTypeId_p);

		/* validate subType Id parameter */
		if (subTypeId_p<= 0) {
			String sMessage = "Error deleting subType - Invalid subType Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			subTypeService.deleteSubType(subTypeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getSubTypes. [%1$s]";
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

	public void setSubTypeService(SubTypeService subTypeService_p) {
		subTypeService = subTypeService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
