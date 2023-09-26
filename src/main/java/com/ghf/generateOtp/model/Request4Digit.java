package com.ghf.generateOtp.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request4Digit {

	private String username;

	private String typeOfAuth;
	
	private String mobileNumber="0";
	
	private String emailId="";
	
	private String outSource;
	
	
	private String product;
	
	private String name;
	
	private String utmSource="";
	private String utmMedium="";
	private String utmCampaign="";
	private String utmTerm="";
	private String utmContent="";
	
	@JsonProperty
	private boolean isSetPassword;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTypeOfAuth() {
		return typeOfAuth;
	}

	public void setTypeOfAuth(String typeOfAuth) {
		this.typeOfAuth = typeOfAuth;
	}

	public Request4Digit(String username, String typeOfAuth) {
		super();
		this.username = username;
		this.typeOfAuth = typeOfAuth;
	}

	
	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Request4Digit() {
		super();
	}

	public String getOutSource() {
		return outSource;
	}

	public void setOutSource(String outSource) {
		this.outSource = outSource;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUtmSource() {
		return utmSource;
	}

	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}

	public String getUtmMedium() {
		return utmMedium;
	}

	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}

	public String getUtmCampaign() {
		return utmCampaign;
	}

	public void setUtmCampaign(String utmCampaign) {
		this.utmCampaign = utmCampaign;
	}

	public String getUtmTerm() {
		return utmTerm;
	}

	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}

	public String getUtmContent() {
		return utmContent;
	}

	public void setUtmContent(String utmContent) {
		this.utmContent = utmContent;
	}

	public boolean isSetPassword() {
		return isSetPassword;
	}

	public void setSetPassword(boolean isSetPassword) {
		this.isSetPassword = isSetPassword;
	}

	

}
