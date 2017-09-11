package com.shopping.cart.app.sales;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class SalesPolicy {

	List<TaxPolicy> taxPolicies = new ArrayList<>();

	public List<TaxPolicy> getTaxPolicies() {
		return taxPolicies;

	}

	public void addTaxPolicies(TaxPolicy taxPolicy) {
		taxPolicies.add(taxPolicy);

	}
}
