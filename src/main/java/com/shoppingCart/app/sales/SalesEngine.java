package com.shoppingCart.app.sales;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shoppingCart.app.model.Cart;
import com.shoppingCart.app.model.LineItem;

@Component
public class SalesEngine {

	@Autowired
	private SalesPolicy salesPolicy;
	
	@Autowired
	private TaxCalculator taxCalculator;
	
	@PostConstruct
	public void init() {
		salesPolicy.addTaxPolicies(new ItemCategoryATaxPolicy("CategoryA", new BigDecimal(0.2)));
		salesPolicy.addTaxPolicies(new ItemCategoryBTaxPolicy("CategoryB", new BigDecimal(0.1)));
		salesPolicy.addTaxPolicies(new ItemCategoryCTaxPolicy("CategoryC", new BigDecimal(0.0)));

	}
	
	
	public Cart applyTaxPolicies(Cart cart) {
		BigDecimal totalTax =BigDecimal.ZERO;
		BigDecimal totalAmount =BigDecimal.ZERO;
		
		
		for(LineItem item : cart.getLinesItems()) {
			totalAmount = totalAmount.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
			for(TaxPolicy taxPolicy : salesPolicy.getTaxPolicies()) {
				if(taxPolicy.isEligible(item)) {
					totalTax = totalTax.add(taxCalculator.calculateTax(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())),taxPolicy.getTaxRate()));
				}
			}
		}
		cart.setTax(totalTax);
		cart.setTotal(totalAmount.add(totalTax));
		cart.setSubtotal(totalAmount);
		return cart;
	}
	
}
