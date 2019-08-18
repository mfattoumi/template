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

import org.cni.intranet.entities.Grade;
import org.cni.intranet.service.GradeService;

@Controller
public class GradeController {

	@Autowired
	private GradeService gradeService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(GradeController.class);

	@RequestMapping(value = "/rest/grades/{gradeId}", method = RequestMethod.GET)
	public ModelAndView getGrade(@PathVariable("gradeId") int gradeId_p) {
		Grade grade = null;

		/* validate grade Id parameter */
		if (gradeId_p<=0) {
			String sMessage = "Error invoking getGrade - Invalid grade Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			grade = gradeService.getGradeById(gradeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getGrade. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Grade: " + grade.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, grade);
	}

	@RequestMapping(value = "/rest/grades/", method = RequestMethod.GET)
	public ModelAndView getGrades() {
		List<Grade> grades = null;

		try {
			grades = gradeService.getAllGrades();
		} catch (Exception e) {
			String sMessage = "Error getting all grades. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Grades: " + grades.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, grades);
	}

	@RequestMapping(value = { "/rest/grades/" }, method = { RequestMethod.POST })
	public ModelAndView createGrade(@RequestBody Grade grade_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Grade: " + grade_p.toString() );
		System.out.println( "Creating Grade: " + grade_p.getGradeId() );

		Grade createdGrade;
		logger_c.debug("Creating Grade: " + grade_p.toString());

		try {
			createdGrade = gradeService.addGrade(grade_p);
		} catch (Exception e) {
			String sMessage = "Error creating new grade. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/grades/" + grade_p.getGradeId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdGrade);
	}

	@RequestMapping(value = { "/rest/grades/{gradeId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateGrade(@RequestBody Grade grade_p, @PathVariable("gradeId") String gradeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Grade: " + grade_p.toString());

		/* validate grade Id parameter */
		if (isEmpty(gradeId_p) || gradeId_p.length() < 5) {
			String sMessage = "Error updating grade - Invalid grade Id parameter";
			return createErrorResponse(sMessage);
		}

		Grade grade = null;

		try {
			grade = gradeService.updateGrade(grade_p);
		} catch (Exception e) {
			String sMessage = "Error updating grade. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, grade);
	}

	@RequestMapping(value = "/rest/grades/{gradeId}", method = RequestMethod.DELETE)
	public ModelAndView removeGrade(@PathVariable("gradeId") int gradeId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Grade Id: " + gradeId_p);

		/* validate grade Id parameter */
		if (gradeId_p<= 0) {
			String sMessage = "Error deleting grade - Invalid grade Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			gradeService.deleteGrade(gradeId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getGrades. [%1$s]";
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

	public void setGradeService(GradeService gradeService_p) {
		gradeService = gradeService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
