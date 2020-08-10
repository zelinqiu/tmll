package com.how2java.tmall.comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

/**
 * 销量比较器
 * 销量高的放前面
 * @author qiuzelin
 *
 */
public class ProductSaleCountComparator implements Comparator<Product> {

	public int compare(Product p1, Product p2) {
		return p2.getSaleCount() - p1.getSaleCount();
	}
	
}
