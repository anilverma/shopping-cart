package com.shopping.cart.app.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopping.cart.app.model.Customer;

public interface CustomerService {

	
	Long addCustomer(Customer customer) throws NoSuchAlgorithmException;
	Optional<Customer> getUserByLoginName(String username) throws UsernameNotFoundException;
}
