package org.cni.intranet.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.entities.Category;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public Category addCategory(Category category) {
		em.persist(category);
		return category;
	}

	@Transactional
	public Category deleteCategory(int id) {
		Category category = getCategoryById(id);
		em.remove(category);
		return category;
	}

	@Transactional
	public Category updateCategory(Category category) {
		em.merge(category);
		return category;
	}

	@Transactional
	public Category getCategoryById(int id) {
		Category as = em.find(Category.class, id);
		if(as==null) throw new RuntimeException("Category introuvable");
		return as;
	}

	@Transactional
	public List<Category> getAllCategorys() {
		Query req = em.createQuery("select x from Category");
		return req.getResultList();
	}

}
