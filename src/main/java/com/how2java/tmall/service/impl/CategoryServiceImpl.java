package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.CategoryMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.CategoryExample;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.Page;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired  //什么时候注入的CategoryMapper？
	CategoryMapper categoryMapper;
		
	@Override
	public List<Category> list() {
		CategoryExample ce = new CategoryExample();
		ce.setOrderByClause("id desc");
		return categoryMapper.selectByExample(ce);
	}

	
	public void add(Category category) {
		categoryMapper.insert(category);
	}
	
	public void delete(int id) {
		categoryMapper.deleteByPrimaryKey(id);
	}
	
	public Category get(int id) {
		return categoryMapper.selectByPrimaryKey(id);
	}
	
	public void update(Category category) {
		categoryMapper.updateByPrimaryKeySelective(category);
	}
}
