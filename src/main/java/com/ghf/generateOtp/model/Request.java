package com.ghf.generateOtp.model;

public class Request {
	private long mob_number=0;
	private String email="";
	private String otp;
	
	
	//Smtp email
	private String emailTemplate="";
	private String smsTemplate="";
	private String subject="";
	
	

	public long getMob_number() {
		return mob_number;
	}
	public void setMob_number(long mob_number) {
		this.mob_number = mob_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getEmailTemplate() {
		return emailTemplate;
	}
	public void setEmailTemplate(String emailTemplate) {
		this.emailTemplate = emailTemplate;
	}
	public String getSmsTemplate() {
		return smsTemplate;
	}
	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Request() {
		super();
	}
	public Request(long i, String email, String emailTemplate, String smsTemplate, String subject) {
		super();
		this.mob_number = i;
		this.email = email;
		this.emailTemplate = emailTemplate;
		this.smsTemplate = smsTemplate;
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "Request [mob_number=" + mob_number + ", email=" + email + ", otp=" + otp + ", emailTemplate="
				+ emailTemplate + ", smsTemplate=" + smsTemplate + ", subject=" + subject + "]";
	}
	
	
	
	
	

}
