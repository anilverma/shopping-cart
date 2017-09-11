package com.shopping.cart.app.sales;

import java.math.BigDecimal;

import com.shopping.cart.app.model.LineItem;

public class ItemCategoryBTaxPolicy implements TaxPolicy {

	private String taxPolicyName;
	private BigDecimal taxRate;

	public ItemCategoryBTaxPolicy(String taxPolicyName, BigDecimal taxRate) {
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
		if (item.getProduct().getCategory().getIdCategory().equals(2)) {
			return true;
		} else {
			return false;
		}
	}

}
