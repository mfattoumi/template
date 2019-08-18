package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.SubTypeDao;
import org.cni.intranet.entities.SubType;

@Transactional
public class SubTypeServiceImpl implements SubTypeService {

	@Autowired
	private SubTypeDao subTypeDao;

	public void setSubTypeDao(SubTypeDao subTypeDao) {
		this.subTypeDao = subTypeDao;
	}
	
	@Override
	public SubType addSubType(SubType subType) {
		return subTypeDao.addSubType(subType);
	}

	@Override
	public SubType deleteSubType(int id) {
		return subTypeDao.deleteSubType(id);
	}

	@Override
	public SubType updateSubType(SubType subType) {
		return subTypeDao.updateSubType(subType);
	}

	@Override
	public SubType getSubTypeById(int id) {
		return subTypeDao.getSubTypeById(id);
	}

	@Override
	public List<SubType> getAllSubTypes() {
		return subTypeDao.getAllSubTypes();
	}

}
