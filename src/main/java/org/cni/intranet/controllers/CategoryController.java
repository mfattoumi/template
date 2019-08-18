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

import org.cni.intranet.entities.Category;
import org.cni.intranet.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(CategoryController.class);

	@RequestMapping(value = "/rest/categorys/{categoryId}", method = RequestMethod.GET)
	public ModelAndView getCategory(@PathVariable("categoryId") int categoryId_p) {
		Category category = null;

		/* validate category Id parameter */
		if (categoryId_p<=0) {
			String sMessage = "Error invoking getCategory - Invalid category Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			category = categoryService.getCategoryById(categoryId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getCategory. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Category: " + category.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, category);
	}

	@RequestMapping(value = "/rest/categorys/", method = RequestMethod.GET)
	public ModelAndView getCategorys() {
		List<Category> categorys = null;

		try {
			categorys = categoryService.getAllCategorys();
		} catch (Exception e) {
			String sMessage = "Error getting all categorys. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Categorys: " + categorys.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, categorys);
	}

	@RequestMapping(value = { "/rest/categorys/" }, method = { RequestMethod.POST })
	public ModelAndView createCategory(@RequestBody Category category_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Category: " + category_p.toString() );
		System.out.println( "Creating Category: " + category_p.getCategoryId() );

		Category createdCategory;
		logger_c.debug("Creating Category: " + category_p.toString());

		try {
			createdCategory = categoryService.addCategory(category_p);
		} catch (Exception e) {
			String sMessage = "Error creating new category. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/categorys/" + category_p.getCategoryId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdCategory);
	}

	@RequestMapping(value = { "/rest/categorys/{categoryId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateCategory(@RequestBody Category category_p, @PathVariable("categoryId") String categoryId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Category: " + category_p.toString());

		/* validate category Id parameter */
		if (isEmpty(categoryId_p) || categoryId_p.length() < 5) {
			String sMessage = "Error updating category - Invalid category Id parameter";
			return createErrorResponse(sMessage);
		}

		Category category = null;

		try {
			category = categoryService.updateCategory(category_p);
		} catch (Exception e) {
			String sMessage = "Error updating category. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, category);
	}

	@RequestMapping(value = "/rest/categorys/{categoryId}", method = RequestMethod.DELETE)
	public ModelAndView removeCategory(@PathVariable("categoryId") int categoryId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Category Id: " + categoryId_p);

		/* validate category Id parameter */
		if (categoryId_p<= 0) {
			String sMessage = "Error deleting category - Invalid category Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			categoryService.deleteCategory(categoryId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getCategorys. [%1$s]";
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

	public void setCategoryService(CategoryService categoryService_p) {
		categoryService = categoryService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
