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

import org.cni.intranet.entities.Permission;
import org.cni.intranet.service.PermissionService;

@Controller
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private View jsonView_i;

	private static final String DATA_FIELD = "data";
	private static final String ERROR_FIELD = "error";

	private static final Logger logger_c = Logger.getLogger(PermissionController.class);

	@RequestMapping(value = "/rest/permissions/{permissionId}", method = RequestMethod.GET)
	public ModelAndView getPermission(@PathVariable("permissionId") int permissionId_p) {
		Permission permission = null;

		/* validate permission Id parameter */
		if (permissionId_p<=0) {
			String sMessage = "Error invoking getPermission - Invalid permission Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			permission = permissionService.getPermissionById(permissionId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getPermission. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Permission: " + permission.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, permission);
	}

	@RequestMapping(value = "/rest/permissions/", method = RequestMethod.GET)
	public ModelAndView getPermissions() {
		List<Permission> permissions = null;

		try {
			permissions = permissionService.getAllPermissions();
		} catch (Exception e) {
			String sMessage = "Error getting all permissions. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		logger_c.debug("Returing Permissions: " + permissions.toString());
		return new ModelAndView(jsonView_i, DATA_FIELD, permissions);
	}

	@RequestMapping(value = { "/rest/permissions/" }, method = { RequestMethod.POST })
	public ModelAndView createPermission(@RequestBody Permission permission_p,
			HttpServletResponse httpResponse_p, WebRequest request_p) {

		System.out.println( "\n\n***********Here POST**********\n\n"	 );
		System.out.println( "Creating Permission: " + permission_p.toString() );
		System.out.println( "Creating Permission: " + permission_p.getPermissionId() );

		Permission createdPermission;
		logger_c.debug("Creating Permission: " + permission_p.toString());

		try {
			createdPermission = permissionService.addPermission(permission_p);
		} catch (Exception e) {
			String sMessage = "Error creating new permission. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		/* set HTTP response code */
		httpResponse_p.setStatus(HttpStatus.CREATED.value());

		/* set location of created resource */
		httpResponse_p.setHeader("Location", request_p.getContextPath() + "/rest/permissions/" + permission_p.getPermissionId());

		/**
		 * Return the view
		 */
		return new ModelAndView(jsonView_i, DATA_FIELD, createdPermission);
	}

	@RequestMapping(value = { "/rest/permissions/{permissionId}" }, method = { RequestMethod.PUT })
	public ModelAndView updatePermission(@RequestBody Permission permission_p, @PathVariable("permissionId") String permissionId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Updating Permission: " + permission_p.toString());

		/* validate permission Id parameter */
		if (isEmpty(permissionId_p) || permissionId_p.length() < 5) {
			String sMessage = "Error updating permission - Invalid permission Id parameter";
			return createErrorResponse(sMessage);
		}

		Permission permission = null;

		try {
			permission = permissionService.updatePermission(permission_p);
		} catch (Exception e) {
			String sMessage = "Error updating permission. [%1$s]";
			return createErrorResponse(String.format(sMessage, e.toString()));
		}

		httpResponse_p.setStatus(HttpStatus.OK.value());
		return new ModelAndView(jsonView_i, DATA_FIELD, permission);
	}

	@RequestMapping(value = "/rest/permissions/{permissionId}", method = RequestMethod.DELETE)
	public ModelAndView removePermission(@PathVariable("permissionId") int permissionId_p,
								   HttpServletResponse httpResponse_p) {

		logger_c.debug("Deleting Permission Id: " + permissionId_p);

		/* validate permission Id parameter */
		if (permissionId_p<= 0) {
			String sMessage = "Error deleting permission - Invalid permission Id parameter";
			return createErrorResponse(sMessage);
		}

		try {
			permissionService.deletePermission(permissionId_p);
		} catch (Exception e) {
			String sMessage = "Error invoking getPermissions. [%1$s]";
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

	public void setPermissionService(PermissionService permissionService_p) {
		permissionService = permissionService_p;
	}

	public void setJsonView(View view) {
		jsonView_i = view;
	}

}
