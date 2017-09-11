package com.shopping.cart.app.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shopping.cart.app.model.Cart;

@Repository
public class CartDaoImp implements CartDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Cart findBy(Long idCart,Long idUser) {
		String queryString = "FROM Cart WHERE idCart = :idCart and idCustomer = :idCustomer";
		return (Cart) sessionFactory.getCurrentSession()
								.createQuery(queryString)
								.setParameter("idCart", idCart)
								.setParameter("idCustomer", idUser)
								.uniqueResult();
	}

	@Override
	public Long save(Cart cart) {
		return (Long) sessionFactory.getCurrentSession().save(cart);
	}

	@Override
	public void update(Cart cart) {
		sessionFactory.getCurrentSession().update(cart);
	}
}
