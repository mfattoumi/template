package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.FunctionDao;
import org.cni.intranet.entities.Function;

@Transactional
public class FunctionServiceImpl implements FunctionService {

	@Autowired
	private FunctionDao functionDao;

	public void setFunctionDao(FunctionDao functionDao) {
		this.functionDao = functionDao;
	}
	
	@Override
	public Function addFunction(Function function) {
		return functionDao.addFunction(function);
	}

	@Override
	public Function deleteFunction(int id) {
		return functionDao.deleteFunction(id);
	}

	@Override
	public Function updateFunction(Function function) {
		return functionDao.updateFunction(function);
	}

	@Override
	public Function getFunctionById(int id) {
		return functionDao.getFunctionById(id);
	}

	@Override
	public List<Function> getAllFunctions() {
		return functionDao.getAllFunctions();
	}

}
