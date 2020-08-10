package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserMapper um;

	public void add(User u) {
		um.insert(u);
	}
	
	public void delete(int id) {
		um.deleteByPrimaryKey(id);
	}
	
	public void update(User u) {
		um.updateByPrimaryKeySelective(u);
	}
	
	public User get(int id) {
		return um.selectByPrimaryKey(id);
	}
	
	public List<User> list() {
		UserExample e = new UserExample();
		e.setOrderByClause("id desc");
		return um.selectByExample(e);
	}
	
	
	/* 判断用户是否存在*/
	public boolean isExist(String name) {
		UserExample e = new UserExample();
		e.createCriteria().andNameEqualTo(name);
		List<User> r = um.selectByExample(e);
		
		return !r.isEmpty();
	}
	
	/* 通过用户名和密码获取用户类*/
	public User get(String name, String password) {
		
		UserExample e = new UserExample();
		e.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
		
		//这里传递给集合是UserExample的用法，实际上里面只有一个元素
		List<User> r = um.selectByExample(e);
		System.out.println(r.size());
		
		return r.isEmpty() ? null : r.get(0);
	}
	
	
	
	
	
	
	
}







