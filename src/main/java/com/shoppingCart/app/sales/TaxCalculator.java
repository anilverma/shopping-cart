package com.shoppingCart.app.sales;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
@Component
public interface TaxCalculator {

	public BigDecimal calculateTax(BigDecimal amount,BigDecimal tax);
}
