package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.DomainDao;
import org.cni.intranet.entities.Domain;

@Transactional
public class DomainServiceImpl implements DomainService {

	@Autowired
	private DomainDao domainDao;

	public void setDomainDao(DomainDao domainDao) {
		this.domainDao = domainDao;
	}
	
	@Override
	public Domain addDomain(Domain domain) {
		return domainDao.addDomain(domain);
	}

	@Override
	public Domain deleteDomain(int id) {
		return domainDao.deleteDomain(id);
	}

	@Override
	public Domain updateDomain(Domain domain) {
		return domainDao.updateDomain(domain);
	}

	@Override
	public Domain getDomainById(int id) {
		return domainDao.getDomainById(id);
	}

	@Override
	public List<Domain> getAllDomains() {
		return domainDao.getAllDomains();
	}

}
