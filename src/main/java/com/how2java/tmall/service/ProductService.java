package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;

public interface ProductService {
	
	void add(Product p);
	void delete(int id);
	void update(Product p);
	Product get(int id);
	List<Product> list(int cid);
	void setFirstProductImage(Product p);
	
	void fill(List<Category> cs);
	void fill(Category c);
	void fillByRow(List<Category> cs);
	
	/* 为产品设置销量和评价数量
	 * 设置销量和评价数量分别使用到了OrderItemService和ReviewService接口*/
	void setSaleAndReviewNumber(Product p);
	void setSaleAndReviewNumber(List<Product> ps);
	
	/* 根据关键词进行搜索*/
	List<Product> search(String keyword);
}
