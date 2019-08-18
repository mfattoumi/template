package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.GradeDao;
import org.cni.intranet.entities.Grade;

@Transactional
public class GradeServiceImpl implements GradeService {

	@Autowired
	private GradeDao gradeDao;

	public void setGradeDao(GradeDao gradeDao) {
		this.gradeDao = gradeDao;
	}
	
	@Override
	public Grade addGrade(Grade grade) {
		return gradeDao.addGrade(grade);
	}

	@Override
	public Grade deleteGrade(int id) {
		return gradeDao.deleteGrade(id);
	}

	@Override
	public Grade updateGrade(Grade grade) {
		return gradeDao.updateGrade(grade);
	}

	@Override
	public Grade getGradeById(int id) {
		return gradeDao.getGradeById(id);
	}

	@Override
	public List<Grade> getAllGrades() {
		return gradeDao.getAllGrades();
	}

}
