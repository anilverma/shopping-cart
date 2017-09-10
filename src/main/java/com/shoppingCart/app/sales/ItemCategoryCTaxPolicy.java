package com.shoppingCart.app.sales;

import java.math.BigDecimal;

import com.shoppingCart.app.model.LineItem;

public class ItemCategoryCTaxPolicy implements TaxPolicy {

	private String taxPolicyName;
	private BigDecimal taxRate;

	public ItemCategoryCTaxPolicy(String taxPolicyName, BigDecimal taxRate) {
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
		if (item.getProduct().getCategory().getIdCategory().equals(3)) {
			return true;
		} else {
			return false;
		}
	}

}
