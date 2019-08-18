package org.cni.intranet.service;

import java.util.List;
import java.util.Set;

import org.cni.intranet.entities.FaxLocal;
import org.cni.intranet.entities.Local;
import org.cni.intranet.entities.PhoneLocal;


public interface LocalService {

	Local addLocal(Local local);

	Local deleteLocal(int id);

	Local updateLocal(Local local);

	Local getLocalById(int id);

	List<Local> getAllLocals();
	
	List<FaxLocal> getFaxesByLocal(int idLocal);
	
	List<PhoneLocal> getPhonesByLocal(int idLocal);

}
