package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Grade;

@Repository
public class GradeDaoImpl implements GradeDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Grade addGrade(Grade grade) {
		em.persist(grade);
		return grade;
	}

	@Transactional
	public Grade deleteGrade(int id) {
		Grade grade = getGradeById(id);
		em.remove(grade);
		return grade;
	}

	@Transactional
	public Grade updateGrade(Grade grade) {
		em.merge(grade);
		return grade;
	}

	@Transactional
	public Grade getGradeById(int id) {
		Grade as = em.find(Grade.class, id);
		if(as==null) throw new RuntimeException("Grade introuvable");
		return as;
	}

	@Transactional
	public List<Grade> getAllGrades() {
		Query req = em.createQuery("select x from Grade");
		return req.getResultList();
	}

}
