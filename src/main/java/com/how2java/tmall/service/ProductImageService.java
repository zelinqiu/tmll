package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.ProductImage;

public interface ProductImageService {
	
	//表示单个图片
	String type_single = "type_single";
	//表示详细图片
	String type_detail = "type_detail";
	
	void add(ProductImage pi);
	void delete(int id);
	void update(ProductImage pi);
	ProductImage get(int id);
	List<ProductImage> list(int pid, String type);
	
	
}
