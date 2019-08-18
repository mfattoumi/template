package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.MailStructureDao;
import org.cni.intranet.entities.MailStructure;

@Transactional
public class MailStructureServiceImpl implements MailStructureService {

	@Autowired
	private MailStructureDao mailStructureDao;

	public void setMailStructureDao(MailStructureDao mailStructureDao) {
		this.mailStructureDao = mailStructureDao;
	}
	
	@Override
	public MailStructure addMailStructure(MailStructure mailStructure) {
		return mailStructureDao.addMailStructure(mailStructure);
	}

	@Override
	public MailStructure deleteMailStructure(int id) {
		return mailStructureDao.deleteMailStructure(id);
	}

	@Override
	public MailStructure updateMailStructure(MailStructure mailStructure) {
		return mailStructureDao.updateMailStructure(mailStructure);
	}

	@Override
	public MailStructure getMailStructureById(int id) {
		return mailStructureDao.getMailStructureById(id);
	}

	@Override
	public List<MailStructure> getAllMailStructures() {
		return mailStructureDao.getAllMailStructures();
	}

}
