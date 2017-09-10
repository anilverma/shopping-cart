package com.shoppingCart.app.sales;

import java.math.BigDecimal;

import com.shoppingCart.app.model.LineItem;

public class ItemCategoryATaxPolicy implements TaxPolicy {

	private String taxPolicyName;
	private BigDecimal taxRate;

	public ItemCategoryATaxPolicy(String taxPolicyName, BigDecimal taxRate) {
		this.taxPolicyName = taxPolicyName;
		this.taxRate = taxRate;
	}

	public String getTaxPolicyName() {
		return taxPolicyName;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public boolean isEligible(LineItem item) {
		if (item.getProduct().getCategory().getIdCategory().equals(1)) {
			return true;
		} else {
			return false;
		}
	}

}
