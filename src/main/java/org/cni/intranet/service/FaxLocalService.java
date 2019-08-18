package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.FaxLocal;

public interface FaxLocalService {
	public FaxLocal addFaxLocal(FaxLocal faxLocal);
	public FaxLocal deleteFaxLocal(int id);
	public FaxLocal updateFaxLocal(FaxLocal faxLocal);
	public FaxLocal getFaxLocalById(int id);
	public List<FaxLocal> getAllFaxLocals();
}
