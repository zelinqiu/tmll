package com.how2java.tmall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	
	/* 服务器跳转 这些访问的jsp文件都是位于 ../jsp/fore/..  */
	
	
	@RequestMapping("registerPage")
	public String registerPage() {
		/* 浏览器访问/registerPage的时候，服务器会跳转到fore文件下的register.jsp文件*/
		return "fore/register";
	}
	
	@RequestMapping("registerSuccessPage")
	public String registerSuccessPage() {
		return "fore/registerSuccess";
	}
	
	/* 这个函数存在的时候，在top.jsp中点击‘用户登录’的时候会调用这个方法*/
	@RequestMapping("loginPage")
	public String loginPage() {
		return "fore/login";
	}
	
	@RequestMapping("forealipay")
	public String alipay() {
		return "fore/alipay";
	}
	
}
