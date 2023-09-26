package com.ghf.generateOtp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "otpentities")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OtpEntities {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String otp;
	private long mobilenumber;
	private String emailid;
	private LocalDateTime otpgenerationtime;
	private LocalDateTime otpexpirytime;
	
	
	public OtpEntities() {
		super();
		// TODO Auto-generated constructor stub
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	

	public long getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(long mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public LocalDateTime getOtpgenerationtime() {
		return otpgenerationtime;
	}
	public void setOtpgenerationtime(LocalDateTime otpgenerationtime) {
		this.otpgenerationtime = otpgenerationtime;
	}
	public LocalDateTime getOtpexpirytime() {
		return otpexpirytime;
	}
	public void setOtpexpirytime(LocalDateTime otpexpirytime) {
		this.otpexpirytime = otpexpirytime;
	}
	
	
	@Override
	public String toString() {
		return "OtpEntities [id=" + id + ", otp=" + otp + ", mobilenumber=" + mobilenumber + ", emailid=" + emailid
				+ ", otpgenerationtime=" + otpgenerationtime + ", otpexpirytime=" + otpexpirytime + "]";
	}
	
	
	
}
