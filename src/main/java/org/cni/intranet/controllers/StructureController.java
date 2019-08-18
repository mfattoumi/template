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

import org.cni.intranet.entities.Structure;
import org.cni.intranet.service.StructureService;

@Controller
public class StructureController {

	@Autowired
	private StructureService structureService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(StructureController.class);

	@RequestMapping(value = "/rest/structures/{structureId}", method = RequestMethod.GET)
	public ModelAndView getStructure(@PathVariable("structureId") int structureId_p) {
		Structure structure = null;

		/* validate structure Id parameter */
		if (structureId_p<=0) {
			String sMessage = "Error invoking getStructure - Invalid structure Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			structure = structureService.getStructureById(structureId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getStructure. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Structure: " + structure.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, structure);
	}

	@RequestMapping(value = "/rest/structures/", method = RequestMethod.GET)
	public ModelAndView getStructures() {
		List<Structure> structures = null;

		try {
			structures = structureService.getAllStructures();
		} catch (Exception e) {
			String sMessage = "Error getting all structures. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Structures: " + structures.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, structures);
	}

	@RequestMapping(value = { "/rest/structures/" }, method = { RequestMethod.POST })
	public ModelAndView createStructure(@RequestBody Structure structure_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Structure: " + structure_p.toString() );
		System.out.println( "Creating Structure: " + structure_p.getStructureId() );

		Structure createdStructure;
		logger_c.debug("Creating Structure: " + structure_p.toString());

		try {
			createdStructure = structureService.addStructure(structure_p);
		} catch (Exception e) {
			String sMessage = "Error creating new structure. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/structures/" + structure_p.getStructureId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdStructure);
	}

	@RequestMapping(value = { "/rest/structures/{structureId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateStructure(@RequestBody Structure structure_p, @PathVariable("structureId") String structureId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Structure: " + structure_p.toString());

		/* validate structure Id parameter */
		if (isEmpty(structureId_p) || structureId_p.length() < 5) {
			String sMessage = "Error updating structure - Invalid structure Id parameter";
			return createErrorResponse(sMessage);
		}

		Structure structure = null;

		try {
			structure = structureService.updateStructure(structure_p);
		} catch (Exception e) {
			String sMessage = "Error updating structure. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, structure);
	}

	@RequestMapping(value = "/rest/structures/{structureId}", method = RequestMethod.DELETE)
	public ModelAndView removeStructure(@PathVariable("structureId") int structureId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Structure Id: " + structureId_p);

		/* validate structure Id parameter */
		if (structureId_p<= 0) {
			String sMessage = "Error deleting structure - Invalid structure Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			structureService.deleteStructure(structureId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getStructures. [%1$s]";
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

	public void setStructureService(StructureService structureService_p) {
		structureService = structureService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
