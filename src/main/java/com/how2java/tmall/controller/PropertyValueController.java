package com.how2java.tmall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.PropertyValueService;

@Controller
public class PropertyValueController {

	@Autowired
	PropertyValueService pvs;
	
	@Autowired
	ProductService ps;
	
	@RequestMapping("admin_propertyValue_edit")
	public String edit(Model model, int pid) {
		
		Product p = ps.get(pid);
		/* 初始化的目的：根据这个p获得cid，然后获取这个类别的所有property
		 * 如果不存在这个property，就创建，否则就不创建*/
		pvs.init(p);
		
		//ppvs中的PropertyValue是存在Property类的
		List<PropertyValue> ppvs = pvs.list(p.getId());
	
		model.addAttribute("p", p);
		model.addAttribute("pvs", ppvs);
		
		return "admin/editPropertyValue";
	}
	
	@RequestMapping("admin_propertyValue_update")
	@ResponseBody
	public String update(PropertyValue pv) {
		pvs.update(pv);
		//向浏览器返回字符串success
		return "success";
	}
	
}
