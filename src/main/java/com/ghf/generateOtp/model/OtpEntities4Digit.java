package com.ghf.generateOtp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "OtpEntities_4digits")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpEntities4Digit {

	@Transient
	private boolean status;

	@Transient
	private String message;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Sr_No")
	private long serialNumber;

	@Column(name = "Exp_Time", columnDefinition = "TIMESTAMP")
	private LocalDateTime expiryTime;

	@Column(name = "OTP")
	private String otp;

	@Column(name = "Email_Id")
	private String emailId;

	@Column(name = "Mobile_Number")
	private long mobileNumber;

	@Column(name = "Customer_Id")
	private String customerId;

	@Column(name = "Otp_Validation_Time" , columnDefinition = "TIMESTAMP")
	private LocalDateTime otpValidationTime;

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

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

	

	public LocalDateTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalDateTime expiryTime) {
		this.expiryTime = expiryTime;
	}

	

	public LocalDateTime getOtpValidationTime() {
		return otpValidationTime;
	}

	public void setOtpValidationTime(LocalDateTime otpValidationTime) {
		this.otpValidationTime = otpValidationTime;
	}

	public OtpEntities4Digit(boolean status, String message, String otp) {
		super();
		this.status = status;
		this.message = message;
		this.otp = otp;

	}

	public OtpEntities4Digit() {
		super();
	}

	
}
