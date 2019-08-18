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

import org.cni.intranet.entities.User;
import org.cni.intranet.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(UserController.class);

	@RequestMapping(value = "/rest/users/{userId}", method = RequestMethod.GET)
	public ModelAndView getUser(@PathVariable("userId") int userId_p) {
		User user = null;

		/* validate user Id parameter */
		if (userId_p<=0) {
			String sMessage = "Error invoking getUser - Invalid user Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			user = userService.getUserById(userId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getUser. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing User: " + user.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, user);
	}

	@RequestMapping(value = "/rest/users/", method = RequestMethod.GET)
	public ModelAndView getUsers() {
		List<User> users = null;

		try {
			users = userService.getAllUser();
		} catch (Exception e) {
			String sMessage = "Error getting all users. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Users: " + users.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, users);
	}

	@RequestMapping(value = { "/rest/users/" }, method = { RequestMethod.POST })
	public ModelAndView createUser(@RequestBody User user_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating User: " + user_p.toString() );
		System.out.println( "Creating User: " + user_p.getUserId() );

		User createdUser;
		logger_c.debug("Creating User: " + user_p.toString());

		try {
			createdUser = userService.addUser(user_p);
		} catch (Exception e) {
			String sMessage = "Error creating new user. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/users/" + user_p.getUserId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdUser);
	}

	@RequestMapping(value = { "/rest/users/{userId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateUser(@RequestBody User user_p, @PathVariable("userId") String userId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating User: " + user_p.toString());

		/* validate user Id parameter */
		if (isEmpty(userId_p) || userId_p.length() < 5) {
			String sMessage = "Error updating user - Invalid user Id parameter";
			return createErrorResponse(sMessage);
		}

		User user = null;

		try {
			user = userService.updateUser(user_p);
		} catch (Exception e) {
			String sMessage = "Error updating user. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, user);
	}

	@RequestMapping(value = "/rest/users/{userId}", method = RequestMethod.DELETE)
	public ModelAndView removeUser(@PathVariable("userId") int userId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting User Id: " + userId_p);

		/* validate user Id parameter */
		if (userId_p<= 0) {
			String sMessage = "Error deleting user - Invalid user Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			userService.deleteUser(userId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getUsers. [%1$s]";
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

	public void setUserService(UserService userService_p) {
		userService = userService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
