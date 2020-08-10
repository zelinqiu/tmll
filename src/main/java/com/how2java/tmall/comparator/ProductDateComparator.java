package com.how2java.tmall.comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

/**
 * 新品比较器
 * 将创建日期晚的方前面
 * @author qiuzelin
 *
 */
public class ProductDateComparator implements Comparator<Product> {
	
	public int compare(Product p1, Product p2) {
		return p2.getCreateDate().compareTo(p1.getCreateDate());
	}
	
}
