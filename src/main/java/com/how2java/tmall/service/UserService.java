package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.User;

public interface UserService {

	void add(User u);
	void delete(int id);
	void update(User u);
	User get(int id);
	List<User> list();
	
	/* 判断是否已经存在了该用户名*/
	boolean isExist(String name);
	
	
	/* 用户登录的时候，通过name和password获取用户类*/
	User get(String name, String password);
}
