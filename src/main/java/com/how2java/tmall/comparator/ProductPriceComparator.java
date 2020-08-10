package com.how2java.tmall.comparator;

import java.util.Comparator;

import com.how2java.tmall.pojo.Product;

/**
 * 价格比较器
 * 将价格低的放在前面
 * @author qiuzelin
 *
 */
public class ProductPriceComparator implements Comparator<Product> {

	public int compare(Product p1, Product p2) {
		return (int)(p1.getPromotePrice() - p2.getPromotePrice());
	}
	
}
