package com.shopping.cart.app.dao;

import com.shopping.cart.app.model.Cart;

public interface CartDao {

	Cart findBy(Long idCart,Long idUser);
	Long save(Cart cart);
	void update(Cart cart);
	
}
