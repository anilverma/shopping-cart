package com.shopping.cart.app.exception;

import java.util.Locale;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BusinessExceptionHandler extends AbstractExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	DefaultErrorMessage handleProductNotFoundException() {

		String error = getResourceBundle().getMessage("product.not.found", null, Locale.getDefault());

		return new DefaultErrorMessage("RS00230", "SYSTEM_ERROR", error);

	}

	@ExceptionHandler(CartNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	DefaultErrorMessage handleCartNotFoundException() {

		String error = getResourceBundle().getMessage("cart.not.found", null, Locale.getDefault());

		return new DefaultErrorMessage("RS00230", "SYSTEM_ERROR", error);

	}

}