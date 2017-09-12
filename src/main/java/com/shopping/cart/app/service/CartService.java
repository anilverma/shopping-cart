package com.shopping.cart.app.service;

import com.shopping.cart.app.exception.CartNotFoundException;
import com.shopping.cart.app.exception.ProductNotFoundException;
import com.shopping.cart.app.model.Cart;
import com.shopping.cart.app.model.Order;

public interface CartService {

	Long save(Cart cart, Long idProduct, Integer quantity) throws ProductNotFoundException;
	void add(Long idUser,Long idCart, Long idProduct, Integer quantity) throws CartNotFoundException, ProductNotFoundException;
	Order ordered(Long idCart,Long idUser) throws CartNotFoundException;

}
