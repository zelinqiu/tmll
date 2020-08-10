package com.how2java.tmall.comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

/**
 * 人气比较器
 * 将评价数量多的放前面
 * @author qiuzelin
 *
 */
public class ProductReviewComparator implements Comparator<Product> {
	
	public int compare(Product p1, Product p2) {
		return p2.getReviewCount() - p1.getReviewCount();
	}
	
}
