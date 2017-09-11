package com.shopping.cart.app.dao;

import com.shopping.cart.app.model.Customer;

public interface CustomerDao {

	Customer findBy(String username);
	Long save(Customer customer);
	
}
