package com.shopping.cart.app.dao;

import java.util.List;

import com.shopping.cart.app.model.Product;

public interface ProductDao {

	Product findBy(Long idProduct);
	Product findBy(String description);
	List<Product> findByCategory(String category);
	List<Product> findAll();
	Long save(Product product);
	
}
