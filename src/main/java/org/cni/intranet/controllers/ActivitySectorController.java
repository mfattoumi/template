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

import org.cni.intranet.entities.ActivitySector;
import org.cni.intranet.service.ActivitySectorService;

@Controller
public class ActivitySectorController {

	@Autowired
	private ActivitySectorService activitySectorService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(ActivitySectorController.class);

	@RequestMapping(value = "/rest/activitySectors/{activitySectorId}", method = RequestMethod.GET)
	public ModelAndView getActivitySector(@PathVariable("activitySectorId") int activitySectorId_p) {
		ActivitySector activitySector = null;

		/* validate activitySector Id parameter */
		if (activitySectorId_p<=0) {
			String sMessage = "Error invoking getActivitySector - Invalid activitySector Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			activitySector = activitySectorService.getActivitySectorById(activitySectorId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getActivitySector. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing ActivitySector: " + activitySector.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, activitySector);
	}

	@RequestMapping(value = "/rest/activitySectors/", method = RequestMethod.GET)
	public ModelAndView getActivitySectors() {
		List<ActivitySector> activitySectors = null;

		try {
			activitySectors = activitySectorService.getAllActivitySectors();
		} catch (Exception e) {
			String sMessage = "Error getting all activitySectors. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing ActivitySectors: " + activitySectors.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, activitySectors);
	}

	@RequestMapping(value = { "/rest/activitySectors/" }, method = { RequestMethod.POST })
	public ModelAndView createActivitySector(@RequestBody ActivitySector activitySector_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating ActivitySector: " + activitySector_p.toString() );
		
		ActivitySector createdActivitySector;
		logger_c.debug("Creating ActivitySector: " + activitySector_p.toString());

		try {
			createdActivitySector = activitySectorService.addActivitySector(activitySector_p);
		} catch (Exception e) {
			String sMessage = "Error creating new activitySector. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/activitySectors/" + activitySector_p.getActivitySectorid());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdActivitySector);
	}

	@RequestMapping(value = { "/rest/activitySectors/{activitySectorId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateActivitySector(@RequestBody ActivitySector activitySector_p, @PathVariable("activitySectorId") String activitySectorId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating ActivitySector: " + activitySector_p.toString());

		/* validate activitySector Id parameter */
		if (isEmpty(activitySectorId_p) || activitySectorId_p.length() < 5) {
			String sMessage = "Error updating activitySector - Invalid activitySector Id parameter";
			return createErrorResponse(sMessage);
		}

		ActivitySector activitySector = null;

		try {
			activitySector = activitySectorService.updateActivitySector(activitySector_p);
		} catch (Exception e) {
			String sMessage = "Error updating activitySector. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, activitySector);
	}

	@RequestMapping(value = "/rest/activitySectors/{activitySectorId}", method = RequestMethod.DELETE)
	public ModelAndView removeActivitySector(@PathVariable("activitySectorId") int activitySectorId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting ActivitySector Id: " + activitySectorId_p);

		/* validate activitySector Id parameter */
		if (activitySectorId_p<= 0) {
			String sMessage = "Error deleting activitySector - Invalid activitySector Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			activitySectorService.deleteActivitySector(activitySectorId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getActivitySectors. [%1$s]";
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

	public void setActivitySectorService(ActivitySectorService activitySectorService_p) {
		activitySectorService = activitySectorService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
