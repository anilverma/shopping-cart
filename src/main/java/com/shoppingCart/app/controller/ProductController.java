package com.shoppingCart.app.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

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

import com.shoppingCart.app.exception.ProductNotFoundException;
import com.shoppingCart.app.model.Customer;
import com.shoppingCart.app.model.Product;
import com.shoppingCart.app.service.ProductService;


@RestController
public class ProductController {

	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Product>> getProducts() throws ProductNotFoundException {
		List<Product> products = productService.findAll();
		return new ResponseEntity<List<Product>> (products, HttpStatus.FOUND);
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> addProduct(@RequestBody Product product, HttpServletRequest request) 
			throws URISyntaxException, NoSuchAlgorithmException {
		Long id = productService.addProduct(product);
		HttpHeaders header = new HttpHeaders();
		header.setLocation(new URI(request.getRequestURL() + "/" + id.toString()));
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/products/{idProduct}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Product> getBy(@PathVariable("idProduct") Long idProduct) throws ProductNotFoundException  {
		Product product = productService.findBy(idProduct);
		return new ResponseEntity<Product> (product, HttpStatus.OK);
	}

	

	@RequestMapping(value = "/products/{category}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Product>> getByCategory(@PathVariable("category") String category) throws ProductNotFoundException  {
		List<Product> products = productService.findByCategory(category);
		return new ResponseEntity<List<Product>> (products, HttpStatus.OK);
	}

}
