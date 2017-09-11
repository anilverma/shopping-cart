package com.shopping.cart.app.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customers")
public class Customer implements java.io.Serializable {

	private Long idCustomer;
	private String lastName;
	private String firstName;
	private String username;
	private String password;
	private List<Order> orderses = new ArrayList<Order>();
	private List<Cart> cartses = new ArrayList<Cart>();
	private Role role;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Customer() {
	}

	public Customer(Long idCustomer, String lastName, String firstName, String username, String password) {
		this.idCustomer = idCustomer;
		this.lastName = lastName;
		this.firstName = firstName;
		this.username = username;
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idcustomer", unique = true, nullable = false)
	public Long getIdCustomer() {
		return this.idCustomer;
	}

	public void setIdCustomer(Long idcustomer) {
		this.idCustomer = idcustomer;
	}

	@Column(name = "last_name", nullable = false, length = 50)
	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "first_name", nullable = false, length = 50)
	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "username", unique = true, nullable = false, length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 256)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Order> getOrderses() {
		return this.orderses;
	}

	public void setOrderses(List<Order> orderses) {
		this.orderses = orderses;
	}

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	@Cascade(CascadeType.SAVE_UPDATE)
	public List<Cart> getCartses() {
		return this.cartses;
	}

	public void setCartses(List<Cart> cartses) {
		this.cartses = cartses;
	}

}
