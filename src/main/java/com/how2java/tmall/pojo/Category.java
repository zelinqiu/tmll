package com.how2java.tmall.pojo;

import java.util.List;

public class Category {
    private Integer id;

    private String name;

    
    /* 为了开发出更多在前端可以看得到的推荐分类和产品，这里新增一些属性*/
    /* products是前端显示的一个分类下下面的多种商品*/
    private List<Product> products;
    /* productBtRow是每个商品可以细分为例如“变频，非变频”等推荐商品*/
    private List<List<Product>> productsByRow;
    public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<List<Product>> getProductsByRow() {
		return productsByRow;
	}
	public void setProductsByRow(List<List<Product>> productsByRow) {
		this.productsByRow = productsByRow;
	}

	
	
	
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}