package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.PhoneLocalDao;
import org.cni.intranet.entities.PhoneLocal;

@Transactional
public class PhoneLocalServiceImpl implements PhoneLocalService {

	@Autowired
	private PhoneLocalDao phoneLocalDao;

	public void setPhoneLocalDao(PhoneLocalDao phoneLocalDao) {
		this.phoneLocalDao = phoneLocalDao;
	}
	
	@Override
	public PhoneLocal addPhoneLocal(PhoneLocal phoneLocal) {
		return phoneLocalDao.addPhoneLocal(phoneLocal);
	}

	@Override
	public PhoneLocal deletePhoneLocal(int id) {
		return phoneLocalDao.deletePhoneLocal(id);
	}

	@Override
	public PhoneLocal updatePhoneLocal(PhoneLocal phoneLocal) {
		return phoneLocalDao.updatePhoneLocal(phoneLocal);
	}

	@Override
	public PhoneLocal getPhoneLocalById(int id) {
		return phoneLocalDao.getPhoneLocalById(id);
	}

	@Override
	public List<PhoneLocal> getAllPhoneLocals() {
		return phoneLocalDao.getAllPhoneLocals();
	}

}
