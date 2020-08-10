package com.how2java.tmall.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.github.pagehelper.PageHelper;
import com.how2java.tmall.comparator.ProductAllComparator;
import com.how2java.tmall.comparator.ProductDateComparator;
import com.how2java.tmall.comparator.ProductPriceComparator;
import com.how2java.tmall.comparator.ProductReviewComparator;
import com.how2java.tmall.comparator.ProductSaleCountComparator;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Order;
import com.how2java.tmall.pojo.OrderItem;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.Review;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.OrderService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueService;
import com.how2java.tmall.service.ReviewService;
import com.how2java.tmall.service.UserService;

@Controller
public class ForeController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ProductImageService productImageService;
	
	@Autowired
	PropertyValueService propertyValueService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderItemService orderItemService;
	
	@Autowired
	ReviewService reviewService;
	
	/**
	 * 前端的主页面
	 * @param model
	 * @return
	 */
	@RequestMapping("forehome")
	public String home(Model model) {
		List<Category> ccs = categoryService.list();
		
		//为ccs分类填充商品
		productService.fill(ccs);
		
		//为每个分类的商品按照8个一组分成不同组
		productService.fillByRow(ccs);
		
		model.addAttribute("cs", ccs);
		return "fore/home";
	}
	
	
	/**
	 * 前端注册的后端实现
	 * 用户传递过来的时候是action=foreregister，传递过来的是一个User。这是SpringMVC的基本用法，
	 * 这里方法中存在的参数会自动创建，并接收由jsp传递过来的参数。其中包含有name和password
	 * @param model
	 * @param user
	 * @return
	 */
	@RequestMapping("foreregister")
	public String register(Model model, User user) {
		
		String name = user.getName();
		name = HtmlUtils.htmlEscape(name);
		user.setName(name);
	
		//在数据库中查找用户是否存在
		boolean exist = userService.isExist(name);
		
		/* 当用户名已经存在的时候，会通过registerPage页面中的js功能显示m的内容*/
		if (exist) {
			String m = "用户名已存在!";
			model.addAttribute("msg", m);
			
			/* 这里如果不设置model.addAttribute("user", null)，那么
			 * springmvc会自动model.addAttribute("user", user) */
			model.addAttribute("user", null);
			
			return "fore/register";	
		}
		userService.add(user);
		
		return "redirect:registerSuccessPage";
	}
	
	/**
	 * 前端登录的页面后端实现
	 * @param name
	 * @param password
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("forelogin")
	public String login(String name, String password, Model model, HttpSession session) {
		name = HtmlUtils.htmlEscape(name);
		User user = userService.get(name, password);
		
		if (user == null) {
			model.addAttribute("msg", "帐号或密码错误!");
			return "fore/login";
		}
		
		/* 本次会话中使用user这个对象*/
		session.setAttribute("user", user);
		return "redirect:forehome";
	}
	
	/**
	 * 退出功能
	 * @param session
	 * @return
	 */
	@RequestMapping("forelogout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:forehome";
	}
	
	/**
	 * 点击商品的时候，显示出商品的评价和销量功能
	 * @param pid
	 * @param model
	 * @return
	 */
    @RequestMapping("foreproduct")
    public String product(int pid, Model model) {
    	//获取参数pid，根据pid获取对象p
        Product p = productService.get(pid);
        
        //根据对象p和图片单个类型，获取这个产品对应的单个图片集合
        List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
        //根据对象p，获取这个产品对应的详情图片集合
        List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
        
        //为p设置其single类型图片
        if (!productSingleImages.isEmpty()) p.setFirstProductImage(productSingleImages.get(0));
        
        //为p对象设置属性
        p.setProductSingleImages(productSingleImages);
        p.setProductDetailImages(productDetailImages);
        
        //p.setFirstProductImage(productSingleImages.get(0));
        //获取产品属性值
        List<PropertyValue> pvs = propertyValueService.list(p.getId());
        //获取产品评价
        List<Review> reviews = reviewService.list(p.getId());
        //设置产品销量和评价数量
        productService.setSaleAndReviewNumber(p);
        
        model.addAttribute("reviews", reviews);
        model.addAttribute("p", p);
        model.addAttribute("pvs", pvs);
        //服务器跳转到product.jsp
        return "fore/product";
    }
    
    
    /* 这个涉及到前端的JQuery的get方法，使用异步ajax的方式访问
     * forecheckLogin*/
    @RequestMapping("forecheckLogin")
    @ResponseBody
    public String checkLogin(HttpSession session) {
    	User user = (User)session.getAttribute("user");
    	return user == null ? "fail" : "success";
    }
    
    @RequestMapping("foreloginAjax")
    @ResponseBody
    public String loginAjax(String name, String password, HttpSession session) {
    	name = HtmlUtils.htmlEscape(name);
    	User user = userService.get(name, password);
    	
    	if (user == null) {
    		return "fail";
    	}
    	session.setAttribute("user", user);
    	return "success";
    }
    
    /**
     * 点击商品类别的时候，显示出所有商品，并且页面中可以根据想要的
     * 特征进行商品排序
     * @param cid
     * @param sort
     * @param model
     * @return
     */
    @RequestMapping("forecategory")
    public String category(int cid, String sort, Model model) {
    	//通过cid获取Category类
    	Category c = categoryService.get(cid);
    	
    	//为类别c设置其产品
    	productService.fill(c);
    	productService.setSaleAndReviewNumber(c.getProducts());
    	
    	if (sort != null) {
    		switch(sort) {
    			case "review" :
    				Collections.sort(c.getProducts(), new ProductReviewComparator());
    				break;
    			case "data" :
    				Collections.sort(c.getProducts(), new ProductDateComparator());
    				break;
    			case "saleCount" :
    				Collections.sort(c.getProducts(), new ProductSaleCountComparator());
    				break;
                case "price":
                    Collections.sort(c.getProducts(),new ProductPriceComparator());
                    break;
                case "all":
                    Collections.sort(c.getProducts(),new ProductAllComparator());
                    break;
    		}
    	}
    	model.addAttribute("c", c);
    	return "fore/category";
    }
    
    /**
     * 根据关键字keyword搜索结果，并显示到searchResult.jsp上
     * @param keyword
     * @param model
     * @return
     */
    @RequestMapping("foresearch")
    public String search(String keyword, Model model) {
    	
    	PageHelper.offsetPage(0, 20);
    	
    	//这个方法返回根据关键字搜索到的所有Product，且设置好了firstImage与其对应的Category
    	List<Product> ps = productService.search(keyword);
    	productService.setSaleAndReviewNumber(ps);
    	
    	model.addAttribute("ps", ps);
    	return "fore/searchResult";
    }
    
    /**
     * 点击「立即购买」之后，会跳转到这个方法中
     * @param pid
     * @param num
     * @param session
     * @return
     */
    @RequestMapping("forebuyone")
    public String buyone(int pid, int num, HttpSession session) {
    	Product p = productService.get(pid);
    	
    	//oiid为订单项id
    	int oiid = 0;
    	
    	User user = (User)session.getAttribute("user");
    	boolean found = false;
    	List<OrderItem> ois = orderItemService.listByUser(user.getId());
    	/* for循环的含义是，遍历由这个user创建的orderitem，查看是否存在该商品id，如果存在
    	 * 意味着user对应的orderitem中已经存在了这个商品，需要在原来订单项中商品数目的基础
    	 * 上增加num，num为本次新增的product数目*/
    	for (OrderItem oi : ois) {
    		if (oi.getProduct().getId().intValue() == p.getId().intValue()) {
    			oi.setNumber(oi.getNumber() + num);
    			orderItemService.update(oi);
    			oiid = oi.getId();
    			found = true;
    			break;
    		}
    	}
    	if (!found) {
    		OrderItem oi = new OrderItem();
    		oi.setUid(user.getId());
    		oi.setNumber(num);
    		oi.setPid(pid);
    		orderItemService.add(oi);
    		oiid = oi.getId();
    	}
    	//客户端跳转
    	return "redirect:forebuy?oiid=" + oiid;
    }
    
    /**
     * 进入forebuy方法是点击立即购买，此时可能会存在多个订单项，因为可以是从
     * 购物车发起的立即购买。
     * @param model
     * @param oiid
     * @param session
     * @return
     */
    @RequestMapping("forebuy")
    public String buy(Model model, String[] oiid, HttpSession session) {
    	List<OrderItem> ois = new ArrayList<>();
    	float total = 0;
    	
    	for (String strid : oiid) {
    		int id = Integer.parseInt(strid);
    		OrderItem oi = orderItemService.get(id);
    		total += oi.getProduct().getPromotePrice() * oi.getNumber();
    		ois.add(oi);
    	}
    	
    	//这里的OrderItem是新创建的订单项，因此需要对其中的Product重新设置其图片，不然在jsp文件中显示为空
    	for (OrderItem oi : ois) {
        	List<ProductImage> pis = productImageService.list(oi.getPid(), ProductImageService.type_single);
        	if (!pis.isEmpty()) {
        		oi.getProduct().setFirstProductImage(pis.get(0));
        	}
    	}
    	/* 从这里可以看出来，img保存的位置不在静态资源中，而是在Servlet自动保存的位置
    	 * 可以发现，如果在tmall_myself/img/...中设置了图片，jsp可能会优先从这里寻找，
    	 * 而不是Servlet自动保存的地址*/
    	System.out.println(session.getServletContext().getRealPath("/img"));
    	System.out.println(ois.size());
    	System.out.println("图片id为:" + ois.get(0).getProduct().getFirstProductImage().getId());
    	session.setAttribute("ois", ois);
    	model.addAttribute("total", total);
    	return "fore/buy";
    }
    
    /**
     * 将商品加入购物车 通过Ajax实现
     * @param pid
     * @param num
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("foreaddCart")
    @ResponseBody
    public String addCart(int pid, int num, Model model, HttpSession session) {
    	Product p = productService.get(pid);
    	User user = (User)session.getAttribute("user");
    	boolean found = false;
    	List<OrderItem> ois = orderItemService.listByUser(user.getId());
    	
    	for (OrderItem oi : ois) {
    		if (oi.getProduct().getId().intValue() == p.getId().intValue()) {
    			oi.setNumber(oi.getNumber() + num);
    			orderItemService.update(oi);
    			found = true;
    			break;
    		}
    	}
    	
    	if (!found) {
    		OrderItem oi = new OrderItem();
    		oi.setUid(user.getId());
    		oi.setNumber(num);
    		oi.setPid(pid);
    		orderItemService.add(oi);
    	}
    	return "success";
    }
    
    /**
     * 显示购物车
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forecart")
    public String cart(Model model, HttpSession session) {
    	User user = (User)session.getAttribute("user");
    	
    	//通过user获取OrderItem
    	List<OrderItem> ois = orderItemService.listByUser(user.getId());
    	
    	//OrderItem中的Product不存在ProductImage类，要重新设置
    	for (OrderItem oi : ois) {
    		Product p = oi.getProduct();
    		List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
    		p.setFirstProductImage(pis.get(0));
    		oi.setProduct(p);
    	}
    	
    	model.addAttribute("ois", ois);
    	return "fore/cart";
    }
    
    /**
     * 点击增加或者减少按钮之后，根据cartPage.jsp中的js代码，通过Ajax访问/forechangeOrderItem路径
     * 导致下面的方法被调用
     * @param model
     * @param session
     * @param pid
     * @param number
     * @return
     */
    @RequestMapping("forechangeOrderItem")
    @ResponseBody
    public String changeOrderItem(Model model, HttpSession session, int pid, int number) {
    	User user = (User)session.getAttribute("user");
    	if (user == null) return "fail";
    	
    	List<OrderItem> ois = orderItemService.listByUser(user.getId());
    	for (OrderItem oi : ois) {
    		if (oi.getProduct().getId().intValue() == pid) {
    			oi.setNumber(number);
    			orderItemService.update(oi);
    			break;
    		}
    	}
    	return "success";
    }
    
    /**
     * 也是使用Ajax删除订单项。
     * 可能等待时间过久，使得session失效，因此需要判断是否用户登录过。
     * @param model
     * @param session
     * @param oiid
     * @return
     */
    @RequestMapping("foredeleteOrderItem")
    @ResponseBody
    public String deleteOrderItem(Model model, HttpSession session, int oiid) {
    	User user = (User)session.getAttribute("user");
    	if (user == null) return "fail";
    	
    	orderItemService.delete(oiid);
    	return "success";
    }
    

    
    /**
     * 点击提交订单之后跳转到这个方法，进入forealipay.jsp页面.此时订单已经创建，但是未付款
     * 这个方法涉及到修改两个表格的内容，分别是OrderItem、Order，因此需要使用数据库的事务
     * 来进行操作。
     * @param model
     * @param order
     * @param session
     * @return
     */
    @RequestMapping("forecreateOrder")
    public String createOrder(Model model, Order order, HttpSession session) {
    	User user = (User)session.getAttribute("user");
    	String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").
    						   format(new Date()) + RandomUtils.nextInt(10000);
    	order.setOrderCode(orderCode);
    	order.setCreateDate(new Date());
    	order.setUid(user.getId());
    	order.setStatus(OrderService.waitPay);
    	
    	@SuppressWarnings("unchecked")
		List<OrderItem> ois = (List<OrderItem>)session.getAttribute("ois");
    	
    	//下面的方法中存在设置修改数据库中orderItem表格的操作，这个方法中存在事务管理
    	float total = orderService.add(order, ois);
    	return "redirect:forealipay?oid=" + order.getId() + "&total=" + total;
    }
    
    
    /**
     * 支付成功之后跳转到这个方法
     * @param oid
     * @param total
     * @param model
     * @return
     */
    @RequestMapping("forepayed")
    public String payed(int oid, float total, Model model) {
    	Order order = orderService.get(oid);
    	//在支付成功之后，状态为等待发货
    	order.setStatus(OrderService.waitDelivery);
    	order.setPayDate(new Date());
    	orderService.update(order);
    	model.addAttribute("o", order);
    	return "fore/payed";
    }
    
    
    /**
     * 点击我的订单跳转到这个方法
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("forebought")
    public String bought(Model model,HttpSession session) {
        User user = (User)session.getAttribute("user");
        
        /* 在OrderService的list方法中存在参数(int uid, String excludedStatus)，
         * 这个方法的目的是将所有订单状态不等于delete的全部检索出来，使用的是方法是：
         * orderExample.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
         * 而如果想要检索出状态等于某个情况下的集合，需要修改成.andStatusEqualTo()*/
        List<Order> os = orderService.list(user.getId(), OrderService.delete);
        
        orderItemService.fill(os);
        model.addAttribute("os", os);

        return "fore/bought";
    }
    
    


    
    /**
     * 点击确认收货之后跳转到这个方法
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("foreconfirmPay")
    public String confirmPay(Model model, int oid) {
    	Order o = orderService.get(oid);
    	
    	//为order增加orderitem
    	orderItemService.fill(o);
    	
    	model.addAttribute("o", o);
    	return "fore/confirmPay";
    }
    

    /**
     * 确认支付成功跳转到这个方法
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("foreorderConfirmed")
    public String orderConfirmed(Model model, int oid) {
    	Order o = orderService.get(oid);
    	o.setStatus(OrderService.waitReview);
    	o.setConfirmDate(new Date());
    	orderService.update(o);
    	
    	return "fore/orderConfirmed";
    }
    
    /** 
     * 删除订单
     * 在我的订单页 上点击删除按钮，根据 boughtPage.jsp 中的ajax操作，会访问路径/foredeleteOrder，
     * 导致ForeController.deleteOrder方法被调用
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("foredeleteOrder")
    @ResponseBody
    public String deleteOrder(Model model, int oid) {
    	Order o = orderService.get(oid);
    	
    	//一般来说，用户的订单不会被删除，只是调整订单的状态至delete
    	o.setStatus(OrderService.delete);
    	orderService.update(o);
    	return "success";
    }
    
    /**
     * 点击评价界面跳转到这个方法
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("forereview")
    public String review(Model model, int oid) {
    	//根据订单id获取订单
    	Order o = orderService.get(oid);
    	
    	//为订单设置其订单项
    	orderItemService.fill(o);
    	
    	//获取订单项中的商品
    	Product p = o.getOrderItems().get(0).getProduct();
    	
    	//根据商品id获取其评价
    	List<Review> reviews = reviewService.list(p.getId());
    	
    	//对商品设置其销量和评价量
    	productService.setSaleAndReviewNumber(p);
    	
    	model.addAttribute("p", p);
    	model.addAttribute("o", o);
    	model.addAttribute("reviews", reviews);
    	
    	return "fore/review";
    }
    
    /**
     * 点击提交评论之后跳转到这个方法
     * @param model
     * @param session
     * @param oid
     * @param pid
     * @param content
     * @return
     */
    @RequestMapping("foredoreview")
    public String doreview(Model model, HttpSession session, int oid, int pid, String content) {
    	Order o = orderService.get(oid);
    	System.out.println(o.getOrderCode());
    	
    	o.setStatus(OrderService.finish);
    	orderService.update(o);
    	
    	content = HtmlUtils.htmlEscape(content);
    	
    	User user = (User)session.getAttribute("user");
    	Review review = new Review();
    	review.setContent(content);
    	review.setPid(pid);
    	review.setCreateDate(new Date());
    	review.setUid(user.getId());
    	reviewService.add(review);
    	
    	return "redirect:forereview?oid="+oid+"&showonly=true";
    }
    
    public static void main(String[] args) {
		String s = "";
		s = "a";
	}
    
}
























