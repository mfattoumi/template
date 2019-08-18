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

import org.cni.intranet.entities.Position;
import org.cni.intranet.service.PositionService;

@Controller
public class PositionController {

	@Autowired
	private PositionService positionService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(PositionController.class);

	@RequestMapping(value = "/rest/positions/{positionId}", method = RequestMethod.GET)
	public ModelAndView getPosition(@PathVariable("positionId") int positionId_p) {
		Position position = null;

		/* validate position Id parameter */
		if (positionId_p<=0) {
			String sMessage = "Error invoking getPosition - Invalid position Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			position = positionService.getPositionById(positionId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getPosition. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Position: " + position.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, position);
	}

	@RequestMapping(value = "/rest/positions/", method = RequestMethod.GET)
	public ModelAndView getPositions() {
		List<Position> positions = null;

		try {
			positions = positionService.getAllPositions();
		} catch (Exception e) {
			String sMessage = "Error getting all positions. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Positions: " + positions.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, positions);
	}

	@RequestMapping(value = { "/rest/positions/" }, method = { RequestMethod.POST })
	public ModelAndView createPosition(@RequestBody Position position_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Position: " + position_p.toString() );
		System.out.println( "Creating Position: " + position_p.getPositionId() );

		Position createdPosition;
		logger_c.debug("Creating Position: " + position_p.toString());

		try {
			createdPosition = positionService.addPosition(position_p);
		} catch (Exception e) {
			String sMessage = "Error creating new position. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/positions/" + position_p.getPositionId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdPosition);
	}

	@RequestMapping(value = { "/rest/positions/{positionId}" }, method = { RequestMethod.PUT })
	public ModelAndView updatePosition(@RequestBody Position position_p, @PathVariable("positionId") String positionId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Position: " + position_p.toString());

		/* validate position Id parameter */
		if (isEmpty(positionId_p) || positionId_p.length() < 5) {
			String sMessage = "Error updating position - Invalid position Id parameter";
			return createErrorResponse(sMessage);
		}

		Position position = null;

		try {
			position = positionService.updatePosition(position_p);
		} catch (Exception e) {
			String sMessage = "Error updating position. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, position);
	}

	@RequestMapping(value = "/rest/positions/{positionId}", method = RequestMethod.DELETE)
	public ModelAndView removePosition(@PathVariable("positionId") int positionId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Position Id: " + positionId_p);

		/* validate position Id parameter */
		if (positionId_p<= 0) {
			String sMessage = "Error deleting position - Invalid position Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			positionService.deletePosition(positionId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getPositions. [%1$s]";
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

	public void setPositionService(PositionService positionService_p) {
		positionService = positionService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
