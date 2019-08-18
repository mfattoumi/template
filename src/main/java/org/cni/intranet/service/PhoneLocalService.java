package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.PhoneLocal;

public interface PhoneLocalService {
	public PhoneLocal addPhoneLocal(PhoneLocal phoneLocal);
	public PhoneLocal deletePhoneLocal(int id);
	public PhoneLocal updatePhoneLocal(PhoneLocal phoneLocal);
	public PhoneLocal getPhoneLocalById(int id);
	public List<PhoneLocal> getAllPhoneLocals();
}
