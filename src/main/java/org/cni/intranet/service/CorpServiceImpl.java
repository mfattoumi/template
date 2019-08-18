package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.CorpDao;
import org.cni.intranet.entities.Corp;

@Transactional
public class CorpServiceImpl implements CorpService {

	@Autowired
	private CorpDao corpDao;

	public void setCorpDao(CorpDao corpDao) {
		this.corpDao = corpDao;
	}
	
	@Override
	public Corp addCorp(Corp corp) {
		return corpDao.addCorp(corp);
	}

	@Override
	public Corp deleteCorp(int id) {
		return corpDao.deleteCorp(id);
	}

	@Override
	public Corp updateCorp(Corp corp) {
		return corpDao.updateCorp(corp);
	}

	@Override
	public Corp getCorpById(int id) {
		return corpDao.getCorpById(id);
	}

	@Override
	public List<Corp> getAllCorps() {
		return corpDao.getAllCorps();
	}

}
