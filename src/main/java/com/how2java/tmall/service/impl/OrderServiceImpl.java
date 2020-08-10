package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.how2java.tmall.mapper.OrderMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderExample;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.UserService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderMapper orderMapper;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OrderItemService orderItemService;


	public void add(Order o) {
		orderMapper.insert(o);
	}

	public void delete(int id) {
		orderMapper.deleteByPrimaryKey(id);
	}
	
	public void update(Order o) {
		orderMapper.updateByPrimaryKeySelective(o);
	}
	
	public Order get(int id) {
		return orderMapper.selectByPrimaryKey(id);
	}
	
	
	
	public List<Order> list() {
		OrderExample e = new OrderExample();
		e.setOrderByClause("id desc");
		List<Order> result = orderMapper.selectByExample(e);
		setUser(result);
		return result;
	}
	

	public void setUser(List<Order> os) {
		for (Order o : os) {
			setUser(o);
		}
	}
	
	
	/* 为Order设置User类，此时Order中是不存在User属性的，需要根据Order中存在的属性Uid来获得User类，
	 * 然后再赋值回去。*/
	public void setUser(Order o) {
		
		int uid = o.getUid();
		
		User u = userService.get(uid);
		
		o.setUser(u);
	}
	
	
    /**
     * 该方法通过注解进行事务管理
     */
    @Transactional(propagation= Propagation.REQUIRED,rollbackForClassName="Exception")
    public float add(Order order, List<OrderItem> orderItems) {
        float total = 0;
        //在订单数据库中增加这个order
        add(order);

        /* 这里只是模拟出现问题的时候的反应，即报告错误*/
        //if(false) throw new RuntimeException();
 
        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(order.getId());
            orderItemService.update(orderItem);
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        //total是购买物品总金额
        return total;
    }
    
    public List<Order> list(int uid, String excludedStatus) {
    	OrderExample oe = new OrderExample();
    	oe.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
    	oe.setOrderByClause("id desc");
    	
    	return orderMapper.selectByExample(oe);
    }
	
}



















