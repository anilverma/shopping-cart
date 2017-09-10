package com.shoppingCart.app.sales;

import java.math.BigDecimal;

import com.shoppingCart.app.model.LineItem;

public interface TaxPolicy {
	
	public boolean isEligible(LineItem item);
	public BigDecimal getTaxRate();
	public String getTaxPolicyName();
	
}
