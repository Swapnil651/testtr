package com.ghf.generateOtp.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ghf.generateOtp.model.OtpEntities4Digit;
import com.ghf.generateOtp.repository.Otp4DigitRepository;

@Service
public class Otp4DigitService {

	@Autowired
	private Otp4DigitRepository otpRepo;

	public List<OtpEntities4Digit> searchByMobileNumber(long mobilenumber) {
		return otpRepo.findByMobileNumber(mobilenumber);
	}

	public List<OtpEntities4Digit> searchByEmailId(String emailid) {
		return otpRepo.findByEmailId(emailid);
	}

	public List<OtpEntities4Digit> searchByCustomerId(String customerid) {
		return otpRepo.findByCustomerId(customerid);
	}

	public OtpEntities4Digit add(OtpEntities4Digit otpEntities) {
		return otpRepo.save(otpEntities);
	}

}
