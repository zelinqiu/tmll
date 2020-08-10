package com.how2java.tmall.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.util.ImageUtil;
import com.how2java.tmall.util.Page;
import com.how2java.tmall.util.UploadedImageFile;

@Controller
public class CategoryController {
	
	@Autowired //这个注解将CategoryServiceImpl自动装配进了CategoryService接口
	CategoryService categoryService;
	
	@RequestMapping("listCategory")
	/* springmvc框架中，只要方法参数中有它，例如这里的model、page，系统就会自动
	 * 创建对象，如果浏览器没有传递任何参数过来，那么这个对象就是默认值。*/
	public String list(Model model, Page page) {
//		List<Category> cs = categoryService.list(page);
//		int total = categoryService.total();
		
		//通过Page类获取start, count
		PageHelper.offsetPage(page.getStart(), page.getCount());
		List<Category> cs = categoryService.list();
		int total = (int)new PageInfo<>(cs).getTotal();
		
		page.setTotal(total);
		/* 下面的代码，会把数据放在request中，这样jsp页面就可以获取了*/
		model.addAttribute("cs", cs);
		model.addAttribute("page", page);
				
		/* 这里可以返回字符串，也可以返回ModelAndView类型。前者是告诉springmvc去寻找
		 * 哪个jsp文件用来作为视图输出，后者就把数据和jsp直接放在了ModelAndView对象里
		 * 了*/
		//服务端跳转到admin/listCategory.jsp
		return "admin/listCategory";
	}
	
    /* 上传图像到浏览器之后，会初始化add函数中的参数
     * 参数Category c接收页面提交的分类名称
     * 参数session用于在后续获取当前应用的路径
     * UploadedImageFile用于接收上传的文件
     * 
     * 当图片上传之后，就会被保存在固定的位置以供listCategory.jsp中展示*/
    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
    	
        categoryService.add(c);
        
        /* 这里的session.getServletContext().getRealPath("img/category")为
         * /home/qiuzelin/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/
         * tmp0/wtpwebapps/tmall_ssm/img/category*/
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder, c.getId() + ".jpg");
        if(!file.getParentFile().exists())  //再添加新的图片的时候，这个if下面的语句不会执行
            file.getParentFile().mkdirs();
        /* 下面的file为：
         * /home/qiuzelin/eclipse-workspace/.metadata/.plugins/org.eclipse.wst.server.core/
         * tmp0/wtpwebapps/tmall_ssm/img/category/14.jpg*/
        //将用户上传的图片传到file定义的地址
        uploadedImageFile.getImage().transferTo(file);
        //将地址图片转换为.jpg格式
        BufferedImage img = ImageUtil.change2jpg(file);
        //将图片修改成二进制jpg之后，再覆盖掉之前的图片地址
        ImageIO.write(img, "jpg", file);
 
        /* 客户端跳转到admin_category_list之后，类上的某些图片的源地址还是在RealPath中*/
        return "redirect:/listCategory";
        /* 使用服务端跳转失败会报错*/
        //return "admin/listCategory";
    }
    
    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) throws IOException {
    	categoryService.delete(id);
    	File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
    	File file = new File(imageFolder, id + ".jpg");
    	file.delete();
    	
    	return "redirect:/listCategory";
    }
    
    @RequestMapping("admin_category_edit")
    public String edit(int id, Model model) throws IOException {
    	Category c = categoryService.get(id);
    	model.addAttribute("c", c);
    	return "admin/editCategory";
    }
    
    @RequestMapping("admin_category_update")
    public String update(Category c, HttpSession session, 
    					 UploadedImageFile uploadedImageFile) throws IOException {
    	//首先更新数据库对应c的name
    	categoryService.update(c);
    	//获取上传的图片
    	MultipartFile image = uploadedImageFile.getImage();
    	if (image != null && !image.isEmpty()) {
    		//获取存放图片路径的文件
    		File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
    		//根据路径文件创建图片文件
    		File file = new File(imageFolder, c.getId() + ".jpg");
    		//将浏览器传递过来的图片保存在上述位置（保存到服务器，即Servlet）
    		image.transferTo(file);
    		BufferedImage img = ImageUtil.change2jpg(file);
    		//将图片修改成二进制jpg之后，再覆盖掉之前的图片地址

    		ImageIO.write(img, "jpg", file);
    	}
    	return "redirect:/listCategory";
    }
}












