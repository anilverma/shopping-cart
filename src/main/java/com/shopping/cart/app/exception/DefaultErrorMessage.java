package com.shopping.cart.app.exception;

import java.io.Serializable;

public class DefaultErrorMessage implements Serializable{
	
	private String status;
	private String errorMessage;
	private String statusCode;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public DefaultErrorMessage(String status, String statusCode, String errorMessage) {
		this.status=status;
		this.statusCode=statusCode;
		this.errorMessage=errorMessage;
	}

}
