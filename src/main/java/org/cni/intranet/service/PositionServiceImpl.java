package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.PositionDao;
import org.cni.intranet.entities.Position;

@Transactional
public class PositionServiceImpl implements PositionService {

	@Autowired
	private PositionDao positionDao;

	public void setPositionDao(PositionDao positionDao) {
		this.positionDao = positionDao;
	}
	
	@Override
	public Position addPosition(Position position) {
		return positionDao.addPosition(position);
	}

	@Override
	public Position deletePosition(int id) {
		return positionDao.deletePosition(id);
	}

	@Override
	public Position updatePosition(Position position) {
		return positionDao.updatePosition(position);
	}

	@Override
	public Position getPositionById(int id) {
		return positionDao.getPositionById(id);
	}

	@Override
	public List<Position> getAllPositions() {
		return positionDao.getAllPositions();
	}

}
