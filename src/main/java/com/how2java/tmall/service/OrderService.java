package com.how2java.tmall.service;

import java.util.List;

import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;

public interface OrderService {
	
	//订单状态的常量信息
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";
 
    void add(Order c);
 
    void delete(int id);
    void update(Order c);
    Order get(int id);
    List<Order> list();
	
    float add(Order o, List<OrderItem> ois);
    
    /**
     * 根据user的id和excludedStatus来获取Order类
     * @param uid
     * @param excludedStatus
     * @return
     */
    List<Order> list(int uid, String excludedStatus);
}
