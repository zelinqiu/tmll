package com.how2java.tmall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.how2java.tmall.mapper.OrderItemMapper;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.OrderItemExample;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;


@Service
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	OrderItemMapper orderItemMapper;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	ProductImageService productImageService;
	
	public void add(OrderItem oi) {
		orderItemMapper.insert(oi);
	}
	
	public void delete(int id) {
		orderItemMapper.deleteByPrimaryKey(id);
	}
	
    public void update(OrderItem c) {
        orderItemMapper.updateByPrimaryKeySelective(c);
    }
	
	public OrderItem get(int id) {
		OrderItem result = orderItemMapper.selectByPrimaryKey(id);
		
		//在得到orderitem之后，再将它的Product属性设置值
		setProduct(result);
		return result;
	}
	
	
    public List<OrderItem> list() {
        OrderItemExample example = new OrderItemExample();
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }
 
    @Override
    public void fill(List<Order> os) {
        for (Order o : os) {
            fill(o);
        }
    }
    

    public void fill(Order o) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(o.getId());
        example.setOrderByClause("id desc");
        
        //根据订单获取订单项。一个订单可以包含多个订单项
        List<OrderItem> ois = orderItemMapper.selectByExample(example);
        setProduct(ois);
 
        //为订单项设置图片
        for (OrderItem oi : ois) {
        	Product p = productService.get(oi.getPid());
        	List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        	oi.getProduct().setFirstProductImage(pis.get(0));
        }
        
        float total = 0;
        int totalNumber = 0;
        for (OrderItem oi : ois) {
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
            totalNumber += oi.getNumber();
        }
        o.setTotal(total);
        o.setTotalNumber(totalNumber);
        o.setOrderItems(ois);
    }
 
    public void setProduct(List<OrderItem> ois){
        for (OrderItem oi: ois) {
            setProduct(oi);
        }
    }
 
    private void setProduct(OrderItem oi) {
        Product p = productService.get(oi.getPid());
        oi.setProduct(p);
    }
	
    /* 根据pid获取某件商品的销售量*/
    public int getSaleCount(int pid) {
    	OrderItemExample oie = new OrderItemExample();
    	oie.createCriteria().andPidEqualTo(pid);
    	List<OrderItem> ois = orderItemMapper.selectByExample(oie);
    	
    	int res = 0;
    	for (OrderItem oi : ois) {
    		res += oi.getNumber();
    	}
    	
    	return res;
    }
    
    
    public List<OrderItem> listByUser(int uid) {
    	OrderItemExample oie = new OrderItemExample();
    	
    	//设置OrderItem中的uid为参数中的uid，oid为null
    	oie.createCriteria().andUidEqualTo(uid).andOidIsNull();
    	
    	List<OrderItem> res = orderItemMapper.selectByExample(oie);
    	setProduct(res);
    	return res;
    }
    
}

















