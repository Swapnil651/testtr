package com.ghf.generateOtp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(value = { "listOfMobileNumber" })
public class Response4Digit {

	private boolean status;
	private String message;
	private boolean first_Login;
	private String role;
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Response4Digit(boolean status, String message, boolean first_Login,String role) {
		super();
		this.status = status;
		this.message = message;
		this.first_Login = first_Login;
		this.role = role;
	}
	
	public Response4Digit(boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public Response4Digit() {
		super();
	}
	
	
	public boolean isFirst_Login() {
		return first_Login;
	}
	public void setFirst_Login(boolean first_Login) {
		this.first_Login = first_Login;
	}
	
	
	
}