package com.shopping.cart.app.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.shopping.cart.app.exception.AuthenticationFailedException;
import com.shopping.cart.app.model.Customer;

public interface CustomerService {

	Customer authentication(String username, String password) 
			throws NoSuchAlgorithmException, AuthenticationFailedException;
	Long addCustomer(Customer customer) throws NoSuchAlgorithmException;
	Optional<Customer> getUserByLoginName(String username) throws UsernameNotFoundException;
}
