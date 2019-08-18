package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.TypeDao;
import org.cni.intranet.entities.Type;

@Transactional
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeDao typeDao;

	public void setTypeDao(TypeDao typeDao) {
		this.typeDao = typeDao;
	}
	
	@Override
	public Type addType(Type type) {
		return typeDao.addType(type);
	}

	@Override
	public Type deleteType(int id) {
		return typeDao.deleteType(id);
	}

	@Override
	public Type updateType(Type type) {
		return typeDao.updateType(type);
	}

	@Override
	public Type getTypeById(int id) {
		return typeDao.getTypeById(id);
	}

	@Override
	public List<Type> getAllTypes() {
		return typeDao.getAllTypes();
	}

}
