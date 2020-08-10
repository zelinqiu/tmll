package com.how2java.tmall.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.util.Page;
 
@Controller
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;
 
    @RequestMapping("admin_order_list")
    public String list(Model model, Page page){
        PageHelper.offsetPage(page.getStart(),page.getCount());
 
        //获取的List<Order>是带有User属性的
        List<Order> os = orderService.list();
 
        int total = (int) new PageInfo<>(os).getTotal();
        page.setTotal(total);
 
        //订单项中保存着交易的数目和Product类型，可以用来设置Order中的一些信息
        orderItemService.fill(os);
 
        model.addAttribute("os", os);
        model.addAttribute("page", page);
 
        return "admin/listOrder";
    }
 
    @RequestMapping("admin_order_delivery")
    public String delivery(Order o) throws IOException {
        o.setDeliveryDate(new Date());
        o.setStatus(OrderService.waitConfirm);
        orderService.update(o);
        //客户端跳转
        return "redirect:admin_order_list";
    }
}
