package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.MailEmployeeDao;
import org.cni.intranet.entities.MailEmployee;

@Transactional
public class MailEmployeeServiceImpl implements MailEmployeeService {

	@Autowired
	private MailEmployeeDao mailEmployeeDao;

	public void setMailEmployeeDao(MailEmployeeDao mailEmployeeDao) {
		this.mailEmployeeDao = mailEmployeeDao;
	}
	
	@Override
	public MailEmployee addMailEmployee(MailEmployee mailEmployee) {
		return mailEmployeeDao.addMailEmployee(mailEmployee);
	}

	@Override
	public MailEmployee deleteMailEmployee(int id) {
		return mailEmployeeDao.deleteMailEmployee(id);
	}

	@Override
	public MailEmployee updateMailEmployee(MailEmployee mailEmployee) {
		return mailEmployeeDao.updateMailEmployee(mailEmployee);
	}

	@Override
	public MailEmployee getMailEmployeeById(int id) {
		return mailEmployeeDao.getMailEmployeeById(id);
	}

	@Override
	public List<MailEmployee> getAllMailEmployees() {
		return mailEmployeeDao.getAllMailEmployees();
	}

}
