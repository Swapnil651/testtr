package com.ghf.generateOtp.service;

import java.util.List;

import java.util.Properties;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ghf.generateOtp.Util.Utility;
import com.ghf.generateOtp.model.OtpEntities;
import com.ghf.generateOtp.model.Request;
import com.ghf.generateOtp.model.Response;
import com.ghf.generateOtp.repository.OtpOperationRepositoryI;

@Service
public class OtpOperationService implements OtpOperationServiceI {

	@Autowired
	private OtpOperationRepositoryI otpOperationRepositoryI;

	@Autowired
	private Utility utility;

	@Override
	public Response otpGeneration(Properties propertyConfiguration,Logger log,Request request) {
		OtpEntities otpEntities;
		boolean isSmsSuccess = false;
		boolean isEmailSuccess = false;
		try {
			if (utility.isValidNumber(request.getMob_number()) || utility.isValidEmail(request.getEmail())) 
			{
				otpEntities=new OtpEntities(); 
				//otpEntities=null;
				//List<OtpEntities> otpEntity;
				//List<OtpEntities> otpEntity = otpOperationRepositoryI.findByUser(request.getMobile(), request.getEmail());
				
				//if (otpEntity.size() == 0) {
					
					System.out.println("inside if");
					//otpEntities = otpEntity.get(0);
					otpEntities.setMobilenumber(request.getMob_number());
					otpEntities.setEmailid(request.getEmail());
					otpEntities.setOtpgenerationtime(utility.getCurrentTime());
					System.out.println("current time utility.getcurrtime---->"+utility.getCurrentTime()+"expiry---->"+utility.getExpiryTime(propertyConfiguration, otpEntities.getOtpgenerationtime()));
					otpEntities.setOtpexpirytime(utility.getExpiryTime(propertyConfiguration,otpEntities.getOtpgenerationtime()));
					otpEntities.setOtp(utility.generateOtpValue(propertyConfiguration));
					if (utility.isValidEmail(request.getEmail())) {
						System.out.println("calling the email service "+otpEntities.getOtp());
						isEmailSuccess = utility.callEmailService(propertyConfiguration,request, otpEntities.getOtp());
						//isEmailSuccess=true;
					}
					if (utility.isValidNumber(request.getMob_number())) {
						//isSmsSuccess=true;
						System.out.println("calling the SMS service "+otpEntities.getOtp()+otpEntities.getMobilenumber());
						isSmsSuccess = utility.callSmsService(propertyConfiguration,request, otpEntities.getOtp());
					}
					otpOperationRepositoryI.save(otpEntities);
					List<OtpEntities > o1 = (List<OtpEntities>) otpOperationRepositoryI.findAll();
					System.out.println(" Customer OTP Details : "+o1);
				//} 
				//else {
					
				//}
				/*else {
					System.out.println("inside else");
					otpEntities = new OtpEntities();
					otpEntities.setMobilenumber(request.getMobile());
					otpEntities.setEmailid(request.getEmail());
					otpEntities.setOtpgenerationtime(utility.getCurrentTime());
					otpEntities.setOtpexpirytime(utility.getExpiryTime(propertyConfiguration,otpEntities.getOtpgenerationtime()));
					otpEntities.setOtp(utility.generateOtpValue(propertyConfiguration));
					if (utility.isValidEmail(request.getEmail())) {
						System.out.println("calling the email service "+otpEntities.getOtp());
						isEmailSuccess = utility.callEmailService(propertyConfiguration,request, otpEntities.getOtp());
						isEmailSuccess=true;
					}
					if (utility.isValidNumber(request.getMobile())) {
						isSmsSuccess=true;
						//isSmsSuccess = utility.callSmsService(propertyConfiguration,request, otpEntities.getOtp());
					}
					otpOperationRepositoryI.save(otpEntities);
					
				}*/
				System.out.println(isEmailSuccess);
				System.out.println(isSmsSuccess);
			if (isSmsSuccess || isEmailSuccess) {
				//if (isEmailSuccess) {
					System.out.println("OTP "+otpEntities.getOtp());
					log.info("Successfull...........");
					return new Response(true, "OTP generated Successfully");
				}
				else {
					log.info("Services currently unavailable...........");
					return new Response(false, "Services currently unavailable.");
				}
			} else {
				log.info("Please provide proper input...........");
				return new Response(false, "Please provide proper input");
			}

		} 
	catch (Exception e) {
			log.info("Exception...........{}",e);
			e.printStackTrace();
			return new Response(false, "Unable to generate OTP");
		}
	}
		
	

	public Response otpValidation(Logger log,Request request) {
		log.info("Inside otpValidation service...........{}");
		System.out.println("Customer OTP Details1 : "+request.getMob_number()+" "+request.getEmail()+" "+request.getOtp());

		try {
			
			if (utility.isValidNumber(request.getMob_number()) || utility.isValidEmail(request.getEmail())) 
			{
				System.out.println("inside try catch");
				System.out.println("Customer OTP Details1 : "+request.getMob_number()+" "+request.getEmail()+" "+request.getOtp());
				
				//only for reference/////
				/*List<OtpEntities> otpEntities = otpOperationRepositoryI.findByUser(request.getMobile_no(), request.getEmail());
				System.out.println(" Customer V-OTP Details2 : "+otpEntities);
				*/
				
				List<OtpEntities> otpEntities1 = otpOperationRepositoryI.findByUSerOTP(request.getMob_number(),request.getEmail(),request.getOtp());
				System.out.println(" Customer Details with OTP and mobile EMmail : "+otpEntities1);
				
				
				/*List<OtpEntities> otpEntities2 = otpOperationRepositoryI.findByUSerOTP1(request.getOtp());
				
				System.out.println(" Customer V-OTP Details3 : "+otpEntities2);*/
				
				//String c= request.getOtp();
				
				OtpEntities otpEntity;
				/*
				if (otpEntities1.size() == 1) {
					otpEntity = otpEntities1.get(0);
					//otpEntity = otpEntities.get(otpEntities.size()-1);
					
					System.out.println(" Customer V-OTP Details1 inside if  : "+otpEntity);
				//otpEntity = otpEntities.get(request.getOtp());
				System.out.println(" Customer V-OTP Details1 : "+otpEntity);
					if (request.getOtp().equals(otpEntity.getOtp())) {
						System.out.println(" Customer V-OTP Details1 : "+request.getOtp()+" "+otpEntity.getOtp());
						if (otpEntity.getOtpgenerationtime().isBefore(utility.getCurrentTime())
								&& otpEntity.getOtpexpirytime().isAfter(utility.getCurrentTime())) {
							log.info("Successfull...........{}");
							return new Response(true, "OTP Validated Successfully");
						} else {
							log.info("Otp expired...........{}");
							return new Response(false, "OTP Expired");
						}
					}
				} 
				*/
				
			//	else {
					//rahul
				if(otpEntities1.size() != 0) {
					for(int i=0; i<=otpEntities1.size();i++) {
						log.info("Inside Rahul's for loop for otp validation");
						log.info("current-->"+utility.getCurrentTime());
						
						if(request.getOtp().equals(otpEntities1.get(i).getOtp())) {
							log.info("this was a valid otp");
							System.out.println(" The otp matched with the entered one--> "+request.getOtp()+" "+otpEntities1.get(i).getOtp());
						if (otpEntities1.get(i).getOtpgenerationtime().isBefore(utility.getCurrentTime())
								&& otpEntities1.get(i).getOtpexpirytime().isAfter(utility.getCurrentTime())) {
							
							log.info("this is a valid otp & not expired");
							log.info("Successfull...........{}");
							return new Response(true, "OTP Validated Successfully");
						}else {
							log.info("The otp entered is outdated and expried !!");
							return new Response(false,"Expried OTP !");
						}
					
						}
						/*
						else {
							log.info("Entered a wrong otp !");
							return new Response(false, "Wrong OTP Entered !!");
						*/
						}
					}else {
						log.info("Entered a wrong otp !");
						return new Response(false, "Wrong OTP Entered !!");
						}
					
					
				}
		//	}
		} catch (Exception e) {
			//e.printStackTrace();
			log.info("Exception...........{}",e);
			return new Response(false, "Something went wrong while validating OTP");
		}
		log.info("Unable to Validate OTP either mobile or email is not correect...........{}");
		return new Response(false, "Unable to Validate OTP");
	}

}
