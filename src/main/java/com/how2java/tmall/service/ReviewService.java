package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Review;

public interface ReviewService {
	void add(Review r);
	
	void update(Review r);
	
	void delete(int id);
	
	Review get(int id);
	
	/* 根据商品id获取评价类集合*/
	List<Review> list(int pid);
	
	/* 根据商品i获取评价数量*/
	int getCount(int pid);
}
