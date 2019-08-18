package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.DelegationDao;
import org.cni.intranet.entities.Delegation;

@Transactional
public class DelegationServiceImpl implements DelegationService {

	@Autowired
	private DelegationDao delegationDao;

	public void setDelegationDao(DelegationDao delegationDao) {
		this.delegationDao = delegationDao;
	}
	
	@Override
	public Delegation addDelegation(Delegation delegation) {
		return delegationDao.addDelegation(delegation);
	}

	@Override
	public Delegation deleteDelegation(int id) {
		return delegationDao.deleteDelegation(id);
	}

	@Override
	public Delegation updateDelegation(Delegation delegation) {
		return delegationDao.updateDelegation(delegation);
	}

	@Override
	public Delegation getDelegationById(int id) {
		return delegationDao.getDelegationById(id);
	}

	@Override
	public List<Delegation> getAllDelegations() {
		return delegationDao.getAllDelegations();
	}

}
