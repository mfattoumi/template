package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.PhoneEmployeeDao;
import org.cni.intranet.entities.PhoneEmployee;

@Transactional
public class PhoneEmployeeServiceImpl implements PhoneEmployeeService {

	@Autowired
	private PhoneEmployeeDao phoneEmployeeDao;

	public void setPhoneEmployeeDao(PhoneEmployeeDao phoneEmployeeDao) {
		this.phoneEmployeeDao = phoneEmployeeDao;
	}
	
	@Override
	public PhoneEmployee addPhoneEmployee(PhoneEmployee phoneEmployee) {
		return phoneEmployeeDao.addPhoneEmployee(phoneEmployee);
	}

	@Override
	public PhoneEmployee deletePhoneEmployee(int id) {
		return phoneEmployeeDao.deletePhoneEmployee(id);
	}

	@Override
	public PhoneEmployee updatePhoneEmployee(PhoneEmployee phoneEmployee) {
		return phoneEmployeeDao.updatePhoneEmployee(phoneEmployee);
	}

	@Override
	public PhoneEmployee getPhoneEmployeeById(int id) {
		return phoneEmployeeDao.getPhoneEmployeeById(id);
	}

	@Override
	public List<PhoneEmployee> getAllPhoneEmployees() {
		return phoneEmployeeDao.getAllPhoneEmployees();
	}

}
