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

import org.cni.intranet.entities.Type;
import org.cni.intranet.service.TypeService;

@Controller
public class TypeController {

	@Autowired
	private TypeService typeService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(TypeController.class);

	@RequestMapping(value = "/rest/types/{typeId}", method = RequestMethod.GET)
	public ModelAndView getType(@PathVariable("typeId") int typeId_p) {
		Type type = null;

		/* validate type Id parameter */
		if (typeId_p<=0) {
			String sMessage = "Error invoking getType - Invalid type Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			type = typeService.getTypeById(typeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getType. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Type: " + type.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, type);
	}

	@RequestMapping(value = "/rest/types/", method = RequestMethod.GET)
	public ModelAndView getTypes() {
		List<Type> types = null;

		try {
			types = typeService.getAllTypes();
		} catch (Exception e) {
			String sMessage = "Error getting all types. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Types: " + types.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, types);
	}

	@RequestMapping(value = { "/rest/types/" }, method = { RequestMethod.POST })
	public ModelAndView createType(@RequestBody Type type_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Type: " + type_p.toString() );
		System.out.println( "Creating Type: " + type_p.getTypeId() );

		Type createdType;
		logger_c.debug("Creating Type: " + type_p.toString());

		try {
			createdType = typeService.addType(type_p);
		} catch (Exception e) {
			String sMessage = "Error creating new type. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/types/" + type_p.getTypeId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdType);
	}

	@RequestMapping(value = { "/rest/types/{typeId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateType(@RequestBody Type type_p, @PathVariable("typeId") String typeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Type: " + type_p.toString());

		/* validate type Id parameter */
		if (isEmpty(typeId_p) || typeId_p.length() < 5) {
			String sMessage = "Error updating type - Invalid type Id parameter";
			return createErrorResponse(sMessage);
		}

		Type type = null;

		try {
			type = typeService.updateType(type_p);
		} catch (Exception e) {
			String sMessage = "Error updating type. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, type);
	}

	@RequestMapping(value = "/rest/types/{typeId}", method = RequestMethod.DELETE)
	public ModelAndView removeType(@PathVariable("typeId") int typeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Type Id: " + typeId_p);

		/* validate type Id parameter */
		if (typeId_p<= 0) {
			String sMessage = "Error deleting type - Invalid type Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			typeService.deleteType(typeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getTypes. [%1$s]";
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

	public void setTypeService(TypeService typeService_p) {
		typeService = typeService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
