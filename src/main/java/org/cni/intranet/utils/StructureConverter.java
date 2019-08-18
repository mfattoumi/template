package org.cni.intranet.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.cni.intranet.entities.Structure;
import org.cni.intranet.service.StructureService;

@FacesConverter("structureConverter")
public class StructureConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		// TODO Auto-generated method stub
		 if(arg2 != null && arg2.trim().length() > 0) {
	            try {
	                StructureService service = (StructureService) arg0.getExternalContext().getApplicationMap().get("strcturService");
	                return service.getAllStructures().get(Integer.parseInt(arg2));
	            } catch(NumberFormatException e) {
	                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid strcutr."));
	            }
	        }
	        else {
	            return null;
	        }
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		// TODO Auto-generated method stub
		 if(arg2 != null) {
	            return String.valueOf(((Structure) arg2).getStructureId());
	        }
	        else {
	            return null;
	        }
	}

}
