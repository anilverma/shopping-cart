package com.shopping.cart.app.service;

import java.util.List;

import com.shopping.cart.app.exception.ProductNotFoundException;
import com.shopping.cart.app.model.Product;

public interface ProductService {

	Product findBy(Long idProduct) throws ProductNotFoundException;
	Product findBy(String description) throws ProductNotFoundException;
	List<Product> findByCategory(String category) throws ProductNotFoundException;
	List<Product> findAll() throws ProductNotFoundException;
	Long addProduct(Product product);
	
}

