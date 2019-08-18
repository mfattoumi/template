package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.FaxEmployeeDao;
import org.cni.intranet.entities.FaxEmployee;

@Transactional
public class FaxEmployeeServiceImpl implements FaxEmployeeService {

	@Autowired
	private FaxEmployeeDao faxEmployeeDao;

	public void setFaxEmployeeDao(FaxEmployeeDao faxEmployeeDao) {
		this.faxEmployeeDao = faxEmployeeDao;
	}
	
	@Override
	public FaxEmployee addFaxEmployee(FaxEmployee faxEmployee) {
		return faxEmployeeDao.addFaxEmployee(faxEmployee);
	}

	@Override
	public FaxEmployee deleteFaxEmployee(int id) {
		return faxEmployeeDao.deleteFaxEmployee(id);
	}

	@Override
	public FaxEmployee updateFaxEmployee(FaxEmployee faxEmployee) {
		return faxEmployeeDao.updateFaxEmployee(faxEmployee);
	}

	@Override
	public FaxEmployee getFaxEmployeeById(int id) {
		return faxEmployeeDao.getFaxEmployeeById(id);
	}

	@Override
	public List<FaxEmployee> getAllFaxEmployees() {
		return faxEmployeeDao.getAllFaxEmployees();
	}

}
