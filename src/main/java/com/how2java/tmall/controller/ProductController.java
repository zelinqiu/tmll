package com.how2java.tmall.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.util.Page;

@Controller
public class ProductController {

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	
	@RequestMapping("admin_product_add")
	public String add(Model model, Product p) {
		p.setCreateDate(new Date());
		productService.add(p);
		//客户端跳转
		return "redirect:admin_product_list?cid=" + p.getCid();
	}
	
	
	@RequestMapping("admin_product_delete")
	public String delete(int id) {
		Product p = productService.get(id);
		productService.delete(id);
		//客户端跳转
		return "redirect:admin_product_list?cid=" + p.getCid();
	}
	
	@RequestMapping("admin_product_edit")
	public String edit(Model model, int id) {
		Product p = productService.get(id);
		Category c = categoryService.get(p.getCid());
		p.setCategory(c);
		model.addAttribute("p", p);
		//服务端跳转
		return "admin/editProduct";
	}
	
	@RequestMapping("admin_product_update")
	public String update(Product p) {
		productService.update(p);
		//客户端跳转
		return "redirect:admin_product_list?cid=" + p.getCid();
	}
	
	@RequestMapping("admin_product_list")
	public String list(int cid, Model model, Page page) {
		Category c = categoryService.get(cid);
		
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Product> coll = productService.list(cid);
		int total = (int)new PageInfo<>(coll).getTotal();
		page.setTotal(total);
		page.setParam("&cid=" + c.getId());
		
		model.addAttribute("ps", coll);
		model.addAttribute("c", c);
		model.addAttribute("page", page);
		//服务端跳转
		return "admin/listProduct";
	}
}

