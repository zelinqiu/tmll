package com.how2java.tmall.service;

import com.how2java.tmall.pojo.Property;

import java.util.*;
/**
 * by myself
 * @author qiuzelin
 *
 */
public interface PropertyServiceCopy {

	void add(Property p);
	
	void delete(int id);
	
	void update(Property p);
	
	Property get(int id);
	
	//列出所有的Property需要确认是哪一个cid
	List<Property> list(int cid);
	
}










