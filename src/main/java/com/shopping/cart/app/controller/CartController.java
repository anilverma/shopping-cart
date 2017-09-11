package com.shopping.cart.app.controller;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.cart.app.exception.ProductNotFoundException;
import com.shopping.cart.app.model.Cart;
import com.shopping.cart.app.model.Order;
import com.shopping.cart.app.service.CartService;

@RestController
public class CartController {

	@Autowired
	CartService cartService;

	@RequestMapping(value = "/users/carts", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> create(@RequestBody Cart cart, @RequestParam("idProduct") Long idProduct,
			@RequestParam("quantity") Integer quantity, HttpServletRequest request) throws URISyntaxException, ProductNotFoundException {
		Long id = cartService.save(cart,idProduct,quantity);
		HttpHeaders header = new HttpHeaders();
		header.setLocation(new URI(request.getRequestURL() + "/" + id.toString()));
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/users/{idUser}/carts/{idCart}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Void> addProduct(@PathVariable("idCart") Long idCart,
			@PathVariable("idUser") Long idUser, @RequestParam("idProduct") Long idProduct,
			@RequestParam("quantity") Integer quantity) {
		cartService.add(idUser, idCart, idProduct, quantity);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/users/{idUser}/carts/{idCart}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Order> ordered(@PathVariable("idUser") Long idUser,
			@PathVariable("idCart") Long idCart, HttpServletRequest request) throws URISyntaxException {
		Order order = cartService.ordered(idCart, idUser);
		HttpHeaders header = new HttpHeaders();
		header.setLocation(new URI(request.getRequestURL() + "/" + order.getIdorder().toString()));
		
		return new ResponseEntity<Order>(order,header, HttpStatus.CREATED);
	}

}
