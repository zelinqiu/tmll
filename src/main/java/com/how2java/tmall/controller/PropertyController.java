package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.util.Page;


@Controller
public class PropertyController {
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	PropertyService propertyService;
	
	@RequestMapping("admin_property_add")
	public String add(Model model, Property p) {
		propertyService.add(p);
		//客户端跳转到根据id显示属性的页面
		return "redirect:admin_property_list?cid=" + p.getCid();
	}
	
	@RequestMapping("admin_property_delete")
	public String delete(int id) {
		Property p = propertyService.get(id);
		propertyService.delete(id);
		return "redirect:admin_property_list?cid=" + p.getCid();
	}
	
	@RequestMapping("admin_property_edit")
	public String edit(Model model, int id) {
		/* property在数据库中不存在category这个类，只是存在这个类的id，因此这里需要使用id将property对应
		 * 的category赋值给property。因为在jsp文件中有用到category。
		 * 事实上，如果删除了jsp中用到的category，这里就不需要设置p.setCategory(c)也可以改变property中
		 * name属性。因为category在property中存在的就是id，通过id来获取category，而不是其本身。*/
		Property p = propertyService.get(id);
		Category c = categoryService.get(p.getCid());
		p.setCategory(c);
		model.addAttribute("p", p);
		return "admin/editProperty";
	}
	
	@RequestMapping("admin_property_update")
	public String update(Property p) {
		//传递进来的p是修改之后的p，id与cid没变，变化的是name。可以从打印结果看出来
		System.out.println(p.getName());
		
		propertyService.update(p);
		return "redirect:admin_property_list?cid=" + p.getCid();
	}
	
	/**
	 * 显示某个Category类的属性
	 * @param cid 指定的想要获取属性的Category类id
	 * @param model 视图类
	 * @param page 页面类
	 * @return
	 */
	@RequestMapping("admin_property_list")
	public String list(int cid, Model model, Page page) {
		
		Category c = categoryService.get(cid);
		
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Property> coll = propertyService.list(cid);
		int total = (int)new PageInfo<>(coll).getTotal();
		
		page.setTotal(total);
		//这里的参数是adminPage.jsp中出现的${page.param}
		page.setParam("&cid=" + c.getId());
		
		model.addAttribute("ps", coll);
		model.addAttribute("c", c);
		model.addAttribute("page", page);
		//跳转到listProperty.jsp
		return "admin/listProperty";
	}
}



















