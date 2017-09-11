package com.shopping.cart.app.model;

import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private Customer user;
	

	public CurrentUser(Customer user) {
		super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()));
		this.user = user;
	}

	public Customer getUser() {
		return user;
	}

	public Long getId() {
		return user.getIdCustomer();
	}

	public Role getRole() {
		return user.getRole();
	}

}