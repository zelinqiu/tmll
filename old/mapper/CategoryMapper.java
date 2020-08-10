package com.how2java.tmall.mapper;

import java.util.List;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.util.Page;

public interface CategoryMapper {
	
	List<Category> list();
	
	void add(Category category);
	
	void delete(int id);
	
	Category get(int id);
	
	void update(Category category);
}
