package com.shoppingCart.app.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingCart.app.dao.CartDao;
import com.shoppingCart.app.dao.OrderDao;
import com.shoppingCart.app.dao.ProductDao;
import com.shoppingCart.app.model.Cart;
import com.shoppingCart.app.model.LineItem;
import com.shoppingCart.app.model.Product;
import com.shoppingCart.app.sales.ItemCategoryATaxPolicy;
import com.shoppingCart.app.sales.SalesEngine;
import com.shoppingCart.app.sales.SalesPolicy;
import com.shoppingCart.app.sales.TaxCalculator;
import com.shoppingCart.app.util.OrderStatus;
import com.shoppingCart.app.model.Order;
import com.shoppingCart.app.model.Order.BuilderOrder;

@Service
@Transactional
public class CartServiceImp implements CartService {

	@Autowired
	SalesEngine salesEngine;
	
	@Autowired
	CartDao cartDao;

	@Autowired
	ProductDao productDao;
	
	@Autowired
	OrderDao orderDao;

	@Override
	public Long save(Cart cart) {
		//updateCartWithTax(cart);
		return cartDao.save(cart);
		
	}

	@Override
	public void add(Long idCart, Long idProduct, Integer quantity) {
		Cart cart = cartDao.findBy(idCart);
		Product product = productDao.findBy(idProduct);
		cart.getLinesItems().add(new LineItem(cart, product, quantity, product.getPrice()));
		updateCartWithTax(cart);
		cartDao.update(cart);
	}

	@Override
	public Long ordered(Long idCart) {
		Cart cart = cartDao.findBy(idCart);
		
		Order order = new BuilderOrder()
				.setCustomer(cart.getCustomer())
				.setOrdered(new Date())
				.setStatus(OrderStatus.NEW.toString())
				.setTotal(cart.getTotal())
				.setTax(cart.getTax())
				.setLinesItems(cart.getLinesItems())
				.build();
		return orderDao.save(order);
	}
	
	public void updateCartWithTax(Cart cart) {
		salesEngine.applyTaxPolicies(cart);
		
	}

}
