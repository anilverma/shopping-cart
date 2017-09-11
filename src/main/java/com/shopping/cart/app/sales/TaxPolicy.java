package com.shopping.cart.app.sales;

import java.math.BigDecimal;

import com.shopping.cart.app.model.LineItem;

public interface TaxPolicy {
	
	public boolean isEligible(LineItem item);
	public BigDecimal getTaxRate();
	public String getTaxPolicyName();
	
}
