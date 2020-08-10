package com.how2java.tmall.service;


import java.util.*;

import com.how2java.tmall.pojo.Property;



public interface PropertyService {
	
	void add(Property c);
	void delete(int id);
	void update(Property c);
	Property get(int id);
	List<Property> list(int cid);
	
	
}	
