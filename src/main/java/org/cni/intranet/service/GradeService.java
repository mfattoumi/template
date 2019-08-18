package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Grade;

public interface GradeService {
	public Grade addGrade(Grade grade);
	public Grade deleteGrade(int id);
	public Grade updateGrade(Grade grade);
	public Grade getGradeById(int id);
	public List<Grade> getAllGrades();
}
