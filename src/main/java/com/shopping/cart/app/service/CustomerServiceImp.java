package com.shopping.cart.app.service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.cart.app.dao.CustomerDao;
import com.shopping.cart.app.model.Customer;


@Service
@Transactional
public class CustomerServiceImp implements CustomerService {

	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	
	
	
	@Override
	public Optional<Customer> getUserByLoginName(String username) 
			throws UsernameNotFoundException {
		Customer customer = customerDao.findBy(username);
		return Optional.ofNullable(customer);
	}

	@Override
	public Long addCustomer(Customer customer) throws NoSuchAlgorithmException {
		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		return customerDao.save(customer);
	}

}
