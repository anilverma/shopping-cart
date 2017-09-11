package com.shopping.cart.app.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.cart.app.dao.CartDao;
import com.shopping.cart.app.dao.OrderDao;
import com.shopping.cart.app.dao.ProductDao;
import com.shopping.cart.app.exception.ProductNotFoundException;
import com.shopping.cart.app.model.Cart;
import com.shopping.cart.app.model.LineItem;
import com.shopping.cart.app.model.Order;
import com.shopping.cart.app.model.Product;
import com.shopping.cart.app.model.Order.BuilderOrder;
import com.shopping.cart.app.sales.ItemCategoryATaxPolicy;
import com.shopping.cart.app.sales.SalesEngine;
import com.shopping.cart.app.sales.SalesPolicy;
import com.shopping.cart.app.sales.TaxCalculator;
import com.shopping.cart.app.util.OrderStatus;

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
	public Long save(Cart cart, Long idProduct, Integer quantity) throws ProductNotFoundException {
		if(idProduct!=null) {
			Product product = productDao.findBy(idProduct);
			if(product !=null) {
				cart.getLinesItems().add(new LineItem(cart, product, quantity, product.getPrice()));
				updateCartWithTax(cart);
			}else {
				throw new ProductNotFoundException();
			}
		}
		return cartDao.save(cart);

	}

	@Override
	public void add(Long idUser, Long idCart, Long idProduct, Integer quantity) {
		Cart cart = cartDao.findBy(idCart, idUser);
		Product product = productDao.findBy(idProduct);
		if(product !=null) {
			cart.getLinesItems().add(new LineItem(cart, product, quantity, product.getPrice()));
			updateCartWithTax(cart);
			cartDao.update(cart);
		}
		
	}

	@Override
	public Order ordered(Long idCart,Long idUser) {
		Cart cart = cartDao.findBy(idCart,idUser);
		
		Order order = new BuilderOrder().setCustomer(cart.getCustomer()).setOrdered(new Date())
				.setStatus(OrderStatus.NEW.toString()).setTotal(cart.getTotal()).setTax(cart.getTax())
				.setLinesItems(cart.getLinesItems()).build();
		 orderDao.save(order);
		 return order;
	}

	public void updateCartWithTax(Cart cart) {
		salesEngine.applyTaxPolicies(cart);

	}

}
