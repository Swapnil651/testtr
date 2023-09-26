package com.ghf.generateOtp.service;

import java.util.Properties;

import org.slf4j.Logger;

import com.ghf.generateOtp.model.Request;
import com.ghf.generateOtp.model.Response;

public interface OtpOperationServiceI {

	public Response otpGeneration(Properties propertyConfiguration,Logger log,Request request);
	
	public Response otpValidation(Logger log,Request request);
	
}
