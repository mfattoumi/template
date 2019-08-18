package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.SubPositionDao;
import org.cni.intranet.entities.SubPosition;

@Transactional
public class SubPositionServiceImpl implements SubPositionService {

	@Autowired
	private SubPositionDao subPositionDao;

	public void setSubPositionDao(SubPositionDao subPositionDao) {
		this.subPositionDao = subPositionDao;
	}
	
	@Override
	public SubPosition addSubPosition(SubPosition subPosition) {
		return subPositionDao.addSubPosition(subPosition);
	}

	@Override
	public SubPosition deleteSubPosition(int id) {
		return subPositionDao.deleteSubPosition(id);
	}

	@Override
	public SubPosition updateSubPosition(SubPosition subPosition) {
		return subPositionDao.updateSubPosition(subPosition);
	}

	@Override
	public SubPosition getSubPositionById(int id) {
		return subPositionDao.getSubPositionById(id);
	}

	@Override
	public List<SubPosition> getAllSubPositions() {
		return subPositionDao.getAllSubPositions();
	}

}
