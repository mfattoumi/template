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

import org.cni.intranet.entities.Role;
import org.cni.intranet.service.RoleService;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(RoleController.class);

	@RequestMapping(value = "/rest/roles/{roleId}", method = RequestMethod.GET)
	public ModelAndView getRole(@PathVariable("roleId") int roleId_p) {
		Role role = null;

		/* validate role Id parameter */
		if (roleId_p<=0) {
			String sMessage = "Error invoking getRole - Invalid role Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			role = roleService.getRoleById(roleId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getRole. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Role: " + role.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, role);
	}

	@RequestMapping(value = "/rest/roles/", method = RequestMethod.GET)
	public ModelAndView getRoles() {
		List<Role> roles = null;

		try {
			roles = roleService.getAllRoles();
		} catch (Exception e) {
			String sMessage = "Error getting all roles. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Roles: " + roles.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, roles);
	}

	@RequestMapping(value = { "/rest/roles/" }, method = { RequestMethod.POST })
	public ModelAndView createRole(@RequestBody Role role_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Role: " + role_p.toString() );
		System.out.println( "Creating Role: " + role_p.getRoleId() );

		Role createdRole;
		logger_c.debug("Creating Role: " + role_p.toString());

		try {
			createdRole = roleService.addRole(role_p);
		} catch (Exception e) {
			String sMessage = "Error creating new role. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/roles/" + role_p.getRoleId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdRole);
	}

	@RequestMapping(value = { "/rest/roles/{roleId}" }, method = { RequestMethod.PUT })
	public ModelAndView updateRole(@RequestBody Role role_p, @PathVariable("roleId") String roleId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Role: " + role_p.toString());

		/* validate role Id parameter */
		if (isEmpty(roleId_p) || roleId_p.length() < 5) {
			String sMessage = "Error updating role - Invalid role Id parameter";
			return createErrorResponse(sMessage);
		}

		Role role = null;

		try {
			role = roleService.updateRole(role_p);
		} catch (Exception e) {
			String sMessage = "Error updating role. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, role);
	}

	@RequestMapping(value = "/rest/roles/{roleId}", method = RequestMethod.DELETE)
	public ModelAndView removeRole(@PathVariable("roleId") int roleId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Role Id: " + roleId_p);

		/* validate role Id parameter */
		if (roleId_p<= 0) {
			String sMessage = "Error deleting role - Invalid role Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			roleService.deleteRole(roleId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getRoles. [%1$s]";
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

	public void setRoleService(RoleService roleService_p) {
		roleService = roleService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
