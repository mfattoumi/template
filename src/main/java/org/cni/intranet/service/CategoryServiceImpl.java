package org.cni.intranet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.cni.intranet.dao.CategoryDao;
import org.cni.intranet.entities.Category;

@Transactional
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	@Override
	public Category addCategory(Category category) {
		return categoryDao.addCategory(category);
	}

	@Override
	public Category deleteCategory(int id) {
		return categoryDao.deleteCategory(id);
	}

	@Override
	public Category updateCategory(Category category) {
		return categoryDao.updateCategory(category);
	}

	@Override
	public Category getCategoryById(int id) {
		return categoryDao.getCategoryById(id);
	}

	@Override
	public List<Category> getAllCategorys() {
		return categoryDao.getAllCategorys();
	}

}
