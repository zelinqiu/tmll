package com.how2java.tmall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.how2java.tmall.mapper.ProductMapper;
import com.how2java.tmall.pojo.Category;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.ProductExample;
import com.how2java.tmall.pojo.ProductImage;
import com.how2java.tmall.service.CategoryService;
import com.how2java.tmall.service.OrderItemService;
import com.how2java.tmall.service.ProductImageService;
import com.how2java.tmall.service.ProductService;
import com.how2java.tmall.service.ReviewService;

@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductImageService productImageService;
	
	@Autowired
	OrderItemService orderItemService;
	
	@Autowired
	ReviewService reviewService;
	
	private void setCategoryWithList(List<Product> ps) {
		for (Product p : ps) {
			setCategory(p);
		}
	}
	
	/**
	 * 这个方法是对product进行设置其category属性
	 * @param p
	 */
	private void setCategory(Product p) {
		int cid = p.getCid();
		Category c = categoryService.get(cid);
		/* 如果不在Product中设置属性Category，那么这种操作就不复存在*/
		p.setCategory(c);
	}
	
	public void add(Product p) {
		productMapper.insert(p);
	}
	
	public void delete(int id) {
		productMapper.deleteByPrimaryKey(id);
	}
	
	public void update(Product p) {
		productMapper.updateByPrimaryKeySelective(p);
	}
	
	public Product get(int id) {
		Product p = productMapper.selectByPrimaryKey(id);
		setCategory(p);
		return p;
	}
	
	public List<Product> list(int cid){
		ProductExample e = new ProductExample();
		e.createCriteria().andCidEqualTo(cid);
		e.setOrderByClause("id desc");
		List<Product> result = productMapper.selectByExample(e);
		setCategoryWithList(result);
		setFirstProductImage(result);
		return result;
	}
	
    public void setFirstProductImage(Product p) {
        List<ProductImage> pis = productImageService.list(p.getId(), ProductImageService.type_single);
        if (!pis.isEmpty()) {
            ProductImage pi = pis.get(0);
            p.setFirstProductImage(pi);
        }
    }
 
    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }
    
    
    
    
    public void fill(List<Category> cs) {
    	for (Category c : cs) {
    		fill(c);
    	}
    }
    
    /* 为分类填充产品集合*/
    public void fill(Category c) {
    	List<Product> ps = list(c.getId());
    	c.setProducts(ps);
    }
    /**
     * 为多个分类填充推荐产品集合，把分类下的产品集合，按照8个为一行，拆成多行，方便在后
     * 续页面上进行显示。
     */
    public void fillByRow(List<Category> cs) {
        int productNumberEachRow = 8;
        for (Category c : cs) {
        	
        	//获取每个分类的商品
            List<Product> products = c.getProducts();
            
            /* 这个是每8个产品成一组，分成不同组。组数是productByRow的长,
             * 实际上，它们全部都是products的子集，只不过又8个分为一组而已*/
            List<List<Product>> productsByRow =  new ArrayList<>();
            
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            c.setProductsByRow(productsByRow);
        }
    }
    
    
    public void setSaleAndReviewNumber(Product p) {
    	int saleCount = orderItemService.getSaleCount(p.getId());
    	p.setSaleCount(saleCount);
    	
    	int reviewCount = reviewService.getCount(p.getId());
    	p.setReviewCount(reviewCount);
    }
    
	public void setSaleAndReviewNumber(List<Product> ps) {
		for (Product p : ps) {
			setSaleAndReviewNumber(p);
		}
	}
	
	/**
	 * 根据关键词进行搜索
	 */
	public List<Product> search(String keyword) {
		ProductExample pe = new ProductExample();
		pe.createCriteria().andNameLike("%" + keyword + "%");
		pe.setOrderByClause("id desc");
		List<Product> res = productMapper.selectByExample(pe);
		setFirstProductImage(res);
		setCategoryWithList(res);
		return res;
	}
}
















