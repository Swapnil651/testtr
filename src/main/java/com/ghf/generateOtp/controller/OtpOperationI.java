package com.ghf.generateOtp.controller;

import com.ghf.generateOtp.model.Request;
import com.ghf.generateOtp.model.Response;

public interface OtpOperationI {

	public Response generateOtp(Request request);
	
	public Response validateOtp(Request request);
}
