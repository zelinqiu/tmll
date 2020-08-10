package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.PropertyMapper;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyExample;
import com.how2java.tmall.service.PropertyServiceCopy;

//@Service
public class PropertyServiceCopyImpl implements PropertyServiceCopy {

	@Autowired
	PropertyMapper pm;
	
	public void add(Property p) {
		pm.insert(p);
	}
	
	public void delete(int id) {
		pm.deleteByPrimaryKey(id);
	}
	
	public void update(Property p) {
		pm.updateByPrimaryKeySelective(p);
	}
	
	public Property get(int id) {
		return pm.selectByPrimaryKey(id);
	}
	
	
	public List<Property> list(int cid) {
		
		PropertyExample pe = new PropertyExample();
		pe.createCriteria().andCidEqualTo(cid);
		pe.setOrderByClause("id desc");
		
		return pm.selectByExample(pe);
	}
	
}












