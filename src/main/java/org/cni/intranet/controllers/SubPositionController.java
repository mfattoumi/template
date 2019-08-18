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

import org.cni.intranet.entities.SubPosition;
import org.cni.intranet.service.SubPositionService;

@Controller
public class SubPositionController {

	@Autowired
	private SubPositionService subPositionService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(SubPositionController.class);

	@RequestMapping(value = "/rest/subPositions/{subPositionId}", method = RequestMethod.GET)
	public ModelAndView getSubPosition(@PathVariable("subPositionId") int subPositionId_p) {
		SubPosition subPosition = null;

		/* validate subPosition Id parameter */
		if (subPositionId_p<=0) {
			String sMessage = "Error invoking getSubPosition - Invalid subPosition Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			subPosition = subPositionService.getSubPositionById(subPositionId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getSubPosition. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing SubPosition: " + subPosition.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, subPosition);
	}

	@RequestMapping(value = "/rest/subPositions/", method = RequestMethod.GET)
	public ModelAndView getSubPositions() {
		List<SubPosition> subPositions = null;

		try {
			subPositions = subPositionService.getAllSubPositions();
		} catch (Exception e) {
			String sMessage = "Error getting all subPositions. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing SubPositions: " + subPositions.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, subPositions);
	}

	@RequestMapping(value = { "/rest/subPositions/" }, method = { RequestMethod.POST })
	public ModelAndView createSubPosition(@RequestBody SubPosition subPosition_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating SubPosition: " + subPosition_p.toString() );
		
		SubPosition createdSubPosition;
		logger_c.debug("Creating SubPosition: " + subPosition_p.toString());

		try {
			createdSubPosition = subPositionService.addSubPosition(subPosition_p);
		} catch (Exception e) {
			String sMessage = "Error creating new subPosition. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/subPositions/" + subPosition_p.getId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdSubPosition);
	}

	@RequestMapping(value = { "/rest/subPositions/{subPositionId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateSubPosition(@RequestBody SubPosition subPosition_p, @PathVariable("subPositionId") String subPositionId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating SubPosition: " + subPosition_p.toString());

		/* validate subPosition Id parameter */
		if (isEmpty(subPositionId_p) || subPositionId_p.length() < 5) {
			String sMessage = "Error updating subPosition - Invalid subPosition Id parameter";
			return createErrorResponse(sMessage);
		}

		SubPosition subPosition = null;

		try {
			subPosition = subPositionService.updateSubPosition(subPosition_p);
		} catch (Exception e) {
			String sMessage = "Error updating subPosition. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, subPosition);
	}

	@RequestMapping(value = "/rest/subPositions/{subPositionId}", method = RequestMethod.DELETE)
	public ModelAndView removeSubPosition(@PathVariable("subPositionId") int subPositionId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting SubPosition Id: " + subPositionId_p);

		/* validate subPosition Id parameter */
		if (subPositionId_p<= 0) {
			String sMessage = "Error deleting subPosition - Invalid subPosition Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			subPositionService.deleteSubPosition(subPositionId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getSubPositions. [%1$s]";
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

	public void setSubPositionService(SubPositionService subPositionService_p) {
		subPositionService = subPositionService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
