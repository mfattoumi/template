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

import org.cni.intranet.entities.Country;
import org.cni.intranet.service.CountryService;

@Controller
public class CountryController {

	@Autowired
	private CountryService countryService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(CountryController.class);

	@RequestMapping(value = "/rest/countrys/{countryId}", method = RequestMethod.GET)
	public ModelAndView getCountry(@PathVariable("countryId") int countryId_p) {
		Country country = null;

		/* validate country Id parameter */
		if (countryId_p<=0) {
			String sMessage = "Error invoking getCountry - Invalid country Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			country = countryService.getCountryById(countryId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getCountry. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Country: " + country.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, country);
	}

	@RequestMapping(value = "/rest/countrys/", method = RequestMethod.GET)
	public ModelAndView getCountrys() {
		List<Country> countrys = null;

		try {
			countrys = countryService.getAllCountrys();
		} catch (Exception e) {
			String sMessage = "Error getting all countrys. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Countrys: " + countrys.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, countrys);
	}

	@RequestMapping(value = { "/rest/countrys/" }, method = { RequestMethod.POST })
	public ModelAndView createCountry(@RequestBody Country country_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Country: " + country_p.toString() );
		System.out.println( "Creating Country: " + country_p.getCountryId() );

		Country createdCountry;
		logger_c.debug("Creating Country: " + country_p.toString());

		try {
			createdCountry = countryService.addCountry(country_p);
		} catch (Exception e) {
			String sMessage = "Error creating new country. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/countrys/" + country_p.getCountryId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdCountry);
	}

	@RequestMapping(value = { "/rest/countrys/{countryId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateCountry(@RequestBody Country country_p, @PathVariable("countryId") String countryId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Country: " + country_p.toString());

		/* validate country Id parameter */
		if (isEmpty(countryId_p) || countryId_p.length() < 5) {
			String sMessage = "Error updating country - Invalid country Id parameter";
			return createErrorResponse(sMessage);
		}

		Country country = null;

		try {
			country = countryService.updateCountry(country_p);
		} catch (Exception e) {
			String sMessage = "Error updating country. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, country);
	}

	@RequestMapping(value = "/rest/countrys/{countryId}", method = RequestMethod.DELETE)
	public ModelAndView removeCountry(@PathVariable("countryId") int countryId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Country Id: " + countryId_p);

		/* validate country Id parameter */
		if (countryId_p<= 0) {
			String sMessage = "Error deleting country - Invalid country Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			countryService.deleteCountry(countryId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getCountrys. [%1$s]";
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

	public void setCountryService(CountryService countryService_p) {
		countryService = countryService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
