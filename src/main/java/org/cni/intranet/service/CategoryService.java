package org.cni.intranet.service;

import java.util.List;

import org.cni.intranet.entities.Category;

public interface CategoryService {
	public Category addCategory(Category category);
	public Category deleteCategory(int id);
	public Category updateCategory(Category category);
	public Category getCategoryById(int id);
	public List<Category> getAllCategorys();
}
