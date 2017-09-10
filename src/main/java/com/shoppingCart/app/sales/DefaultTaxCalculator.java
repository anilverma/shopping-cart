package com.shoppingCart.app.sales;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class DefaultTaxCalculator implements TaxCalculator{

	public BigDecimal calculateTax(BigDecimal amount,BigDecimal tax) {
		
		return amount.multiply(tax);
	}
}
