package com.ghf.generateOtp.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ghf.generateOtp.Util.Utility;
import com.ghf.generateOtp.model.OtpEntities4Digit;
import com.ghf.generateOtp.model.Request;
import com.ghf.generateOtp.model.Request4Digit;
import com.ghf.generateOtp.model.Response;
import com.ghf.generateOtp.model.Response4Digit;

import com.ghf.generateOtp.service.OtpOperationService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1")
public class OtpOperation implements OtpOperationI {


	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private OtpOperationService otpOperationService;
	
	

	@Autowired
	private Utility utility;
	
	@GetMapping("/healthcheck")
	public String healthCheck(){
		log.info("Calling /healthcheck.......");
		return "Health Check Clear";
	}
	
	@Override
	@PostMapping(path="/generateOtp")
	public Response generateOtp(@RequestBody Request request) {
		System.out.println("enetered into OTP GENERATION");
		System.out.println("request" + request);
		try {
		log.info("2nd Step inside generateOtp");
		log.info("Calling /generateOtp.......mobile_no={},email={}",request.getMob_number(),request.getEmail());
		Properties p = new Properties();
		
		String filepath = System.getProperty("os.name").contains("Windows")
				? "src/main/resources/application-local.properties"
				: "/home/ec2-user/GenerateOtp/application.properties";
		//	: "classpath:application.properties";
		
		     System.out.println("filepath"+ filepath);     

		File file1 = ResourceUtils.getFile(filepath);
	    System.out.println("file1"+ file1);     
	     
	    // log.info("file1 has been successfully run");
		InputStream is = new FileInputStream(file1);
	     //InputStream is = OtpOperation.class.getResourceAsStream("/application-local.properties");
	     System.out.println("is"+ is);     
	   //  log.info("IS has been successfully run");
	     
		p.load(is);
		
		log.info("pload has been successfully run");
		//rahul
		//if()
		return otpOperationService.otpGeneration(p,log,request);
		}catch (Exception e) {
			log.info("Exception..............{}",e);
			return new Response(false, "Something went wrong");
		}
	}
	
	@Override
	@PostMapping(path="/validateOtp")
	public Response validateOtp(@RequestBody Request request) {
		System.out.println("request"+ request);
		try {
			log.info("Calling /validateOtp.......mobile={},email={}",request.getMob_number(),request.getEmail());
			Properties p = new Properties();
			String filepath = System.getProperty("os.name").contains("Windows")
					? "src/main/resources/application.properties"
							: "/home/ec2-user/GenerateOtp/application.properties";
					//: "/home/ec2-user/generateOtpGeneric/target/classes/application.properties";
					//: "/home/ec2-usersrc/main/resources/application.properties";
			File file1 = ResourceUtils.getFile(filepath);
			InputStream is = new FileInputStream(file1);
			p.load(is);
			return otpOperationService.otpValidation(log,request);
		}catch (Exception e) {
			log.info("Exception..............{}",e);
			return new Response(false, "Something went wrong");
		}
		
	}
	
	
	
	
	
}
