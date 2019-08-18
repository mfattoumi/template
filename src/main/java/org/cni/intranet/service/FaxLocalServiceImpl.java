package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.FaxLocalDao;
import org.cni.intranet.entities.FaxLocal;

@Transactional
public class FaxLocalServiceImpl implements FaxLocalService {

	@Autowired
	private FaxLocalDao faxLocalDao;

	public void setFaxLocalDao(FaxLocalDao faxLocalDao) {
		this.faxLocalDao = faxLocalDao;
	}
	
	@Override
	public FaxLocal addFaxLocal(FaxLocal faxLocal) {
		return faxLocalDao.addFaxLocal(faxLocal);
	}

	@Override
	public FaxLocal deleteFaxLocal(int id) {
		return faxLocalDao.deleteFaxLocal(id);
	}

	@Override
	public FaxLocal updateFaxLocal(FaxLocal faxLocal) {
		return faxLocalDao.updateFaxLocal(faxLocal);
	}

	@Override
	public FaxLocal getFaxLocalById(int id) {
		return faxLocalDao.getFaxLocalById(id);
	}

	@Override
	public List<FaxLocal> getAllFaxLocals() {
		return faxLocalDao.getAllFaxLocals();
	}

}
