package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

public interface OrderItemService {
	
	
	void add(OrderItem oi);
	void delete(int id);
	void update(OrderItem oi);
	OrderItem get(int id);
	List<OrderItem> list();
	
	/**
	 * 主要是确定一对多关系 一个订单可能对应多个订单项
	 * @param os
	 */
	void fill(List<Order> os);
	
	
	void fill(Order o);

	
	/* 根据pid获取产品销售量
	 * 之所以将getSaleCount方法放在OrderItemService中是因为OrderItem中存在属性Number，
	 * 这正是需要的某一个订单项中的商品数目。*/
	int getSaleCount(int pid);
	
	/**
	 * 这个方法相当于列出对于固定id的user对应的订单项
	 * @param uid
	 * @return
	 */
	List<OrderItem> listByUser(int uid);
}
