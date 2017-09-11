package com.shopping.cart.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shopping.cart.app.model.CurrentUser;
import com.shopping.cart.app.model.Customer;



@Service
public class CurrentUserDetailsService implements UserDetailsService {

	private final CustomerService customerService;

	@Autowired
	public CurrentUserDetailsService(CustomerService customerService) {
		this.customerService = customerService;
	}

	@Override
	public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer user = customerService.getUserByLoginName(email).orElseThrow(
				() -> new UsernameNotFoundException(String.format("User with username=%s was not found", email)));
		return new CurrentUser(user);
	}
}
