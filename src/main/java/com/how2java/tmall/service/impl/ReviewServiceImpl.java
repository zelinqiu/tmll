package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ReviewMapper;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.ReviewExample;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Autowired
	ReviewMapper reviewMapper;
	
	@Autowired
	UserService userService;
	
	public void add(Review r) {
		reviewMapper.insert(r);
	}
	
	
	public void delete(int id) {
		reviewMapper.deleteByPrimaryKey(id);
	}
	
	public void update(Review r) {
		reviewMapper.updateByPrimaryKeySelective(r);
	}
	
	public Review get(int id) {
		return reviewMapper.selectByPrimaryKey(id);
	}
	
	public List<Review> list(int pid) {
		ReviewExample e = new ReviewExample();
		e.createCriteria().andPidEqualTo(pid);
		e.setOrderByClause("id desc");
		
		List<Review> coll = reviewMapper.selectByExample(e);
		setUser(coll);
		return coll;
	}
	
	/**
	 * 这个方法的含义是给某个评论设置其User属性
	 * @param review
	 */
	private void setUser(Review review) {
		int uid = review.getUid();
		User user = userService.get(uid);
		review.setUser(user);
	}
	
	private void setUser(List<Review> coll) {
		for (Review r : coll) {
			setUser(r);
		}
	}
	
	
	public int getCount(int pid) {
		return list(pid).size();
	}
}






