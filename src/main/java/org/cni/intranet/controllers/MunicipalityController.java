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

import org.cni.intranet.entities.Municipality;
import org.cni.intranet.service.MunicipalityService;

@Controller
public class MunicipalityController {

	@Autowired
	private MunicipalityService municipalityService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(MunicipalityController.class);

	@RequestMapping(value = "/rest/municipalitys/{municipalityId}", method = RequestMethod.GET)
	public ModelAndView getMunicipality(@PathVariable("municipalityId") int municipalityId_p) {
		Municipality municipality = null;

		/* validate municipality Id parameter */
		if (municipalityId_p<=0) {
			String sMessage = "Error invoking getMunicipality - Invalid municipality Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			municipality = municipalityService.getMunicipalityById(municipalityId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getMunicipality. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Municipality: " + municipality.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, municipality);
	}

	@RequestMapping(value = "/rest/municipalitys/", method = RequestMethod.GET)
	public ModelAndView getMunicipalitys() {
		List<Municipality> municipalitys = null;

		try {
			municipalitys = municipalityService.getAllMunicipalitys();
		} catch (Exception e) {
			String sMessage = "Error getting all municipalitys. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Municipalitys: " + municipalitys.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, municipalitys);
	}

	@RequestMapping(value = { "/rest/municipalitys/" }, method = { RequestMethod.POST })
	public ModelAndView createMunicipality(@RequestBody Municipality municipality_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Municipality: " + municipality_p.toString() );
		System.out.println( "Creating Municipality: " + municipality_p.getMunicipalityId() );

		Municipality createdMunicipality;
		logger_c.debug("Creating Municipality: " + municipality_p.toString());

		try {
			createdMunicipality = municipalityService.addMunicipality(municipality_p);
		} catch (Exception e) {
			String sMessage = "Error creating new municipality. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/municipalitys/" + municipality_p.getMunicipalityId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdMunicipality);
	}

	@RequestMapping(value = { "/rest/municipalitys/{municipalityId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateMunicipality(@RequestBody Municipality municipality_p, @PathVariable("municipalityId") String municipalityId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Municipality: " + municipality_p.toString());

		/* validate municipality Id parameter */
		if (isEmpty(municipalityId_p) || municipalityId_p.length() < 5) {
			String sMessage = "Error updating municipality - Invalid municipality Id parameter";
			return createErrorResponse(sMessage);
		}

		Municipality municipality = null;

		try {
			municipality = municipalityService.updateMunicipality(municipality_p);
		} catch (Exception e) {
			String sMessage = "Error updating municipality. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, municipality);
	}

	@RequestMapping(value = "/rest/municipalitys/{municipalityId}", method = RequestMethod.DELETE)
	public ModelAndView removeMunicipality(@PathVariable("municipalityId") int municipalityId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Municipality Id: " + municipalityId_p);

		/* validate municipality Id parameter */
		if (municipalityId_p<= 0) {
			String sMessage = "Error deleting municipality - Invalid municipality Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			municipalityService.deleteMunicipality(municipalityId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getMunicipalitys. [%1$s]";
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

	public void setMunicipalityService(MunicipalityService municipalityService_p) {
		municipalityService = municipalityService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
