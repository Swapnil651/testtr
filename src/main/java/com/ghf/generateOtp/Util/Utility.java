package com.ghf.generateOtp.Util;


import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ghf.generateOtp.model.OtpEntities4Digit;
import com.ghf.generateOtp.model.Request;
import com.ghf.generateOtp.model.Request4Digit;
import com.ghf.generateOtp.model.Response;
import com.ghf.generateOtp.model.Response4Digit;

@Component
public class Utility {

	

	/*
	 * public static void main(String a[]) throws ParseException { Utility u = new
	 * Utility(); // System.out.println("current time : " + u.getCurrentTime()); //
	 * System.out.println("expiry time : " + u.getExpiryTime(u.getCurrentTime()));
	 * u.generateOtpValue(); }
	 */
	public LocalDateTime getCurrentTime() throws ParseException {
		SimpleDateFormat utcFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		utcFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		String utcTime = utcFormatter.format(new Date());

		Date utcTimeInstance = utcFormatter.parse(utcTime);
		SimpleDateFormat indianFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		indianFormatter.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
		String indianTime = indianFormatter.format(utcTimeInstance);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		LocalDateTime datetime1 = LocalDateTime.parse(indianTime, formatter);

		return datetime1;
	}

	public LocalDateTime getExpiryTime(Properties propertyConfiguration,LocalDateTime localDateTime) throws ParseException {
		LocalDateTime expiryTime = localDateTime.plusMinutes(Long.parseLong(propertyConfiguration.getProperty("expiryTime")));
		return expiryTime;
	}

	public String generateOtpValue(Properties propertyConfiguration) {
		int number;
		Random rnd = new Random();
		if ( Integer.parseInt(propertyConfiguration.getProperty("otpDigit")) == 6 ) {
			number = rnd.nextInt(999999);
			return String.format("%06d", number);
		} else {
			number = rnd.nextInt(9999);
			System.out.println("else: " + Integer.valueOf(String.format("%04d", number)));
			return String.format("%04d", number);
		}
	}
	
	public boolean isValidNumber(long phoneNumber) {
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(String.valueOf(phoneNumber));
		return (m.find() && m.group().equals(String.valueOf(phoneNumber)));
	}
	public boolean isValidNumber2(String phoneNumber) {
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
		Matcher m = p.matcher(String.valueOf(phoneNumber));
		return (m.find() && m.group().equals(String.valueOf(phoneNumber)));
	}

	public boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pattern = Pattern.compile(emailRegex);
		if (email == null) {
			return false;
		}
		return pattern.matcher(email).matches();
	}
	
	public boolean callEmailService(Properties propertyConfiguration, Request request, String otp) throws Exception {
		Response response = new Response();
		try {
			URL url;
			if(!request.getEmailTemplate().isEmpty() && !request.getSubject().isEmpty()) {
				System.out.println("if block");
				System.out.println(propertyConfiguration.getProperty("smtpEmailServiceUrl"));
				url = new URL(propertyConfiguration.getProperty("smtpEmailServiceUrl"));
			}else {
				System.out.println("else block");
			    url = new URL(propertyConfiguration.getProperty("emailServiceUrl"));
			}
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setDoOutput(true);
			String jsonInputString = "";
			if(!request.getEmailTemplate().isEmpty() && !request.getSubject().isEmpty()) {
				ObjectMapper mapper = new ObjectMapper();
			
				jsonInputString = mapper.writeValueAsString(new Request(0, request.getEmail(), request.getEmailTemplate().replace("#otp", otp), "", request.getSubject()));
			  
			}else {
			 jsonInputString = "{\r\n" + 
					"  \"personalizations\" : [ {\r\n" + 
					"    \"recipient\" : \""+request.getEmail()+"\"\r\n" + 
					"  } ],\r\n" + 
					"  \"subject\" : \""+propertyConfiguration.getProperty("emailSubject")+"\",\r\n" + 
					"  \"content\" : \""+otp+" "+propertyConfiguration.getProperty("emailContent")+"."+"\"\r\n" + 
					"}";
			}
			System.out.println("Email String : "+jsonInputString);
			System.out.println("Email service URL : "+url);
			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
				System.out.println(" Input : "+input);
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder resp = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					resp.append(responseLine.trim());
					System.out.println(" Resp : "+resp);
				}
				Gson gson = new Gson();
				response = gson.fromJson(resp.toString(), Response.class);
				//response = gson.fromJson(jsonInputString, Response.class);
				System.out.println("Res======"+response.getMessage());
				if (response.isStatus()) {
					return true;
				}
			} catch (IOException ex) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
	public boolean callSmsService(Properties propertyConfiguration,Request request,String otp) throws Exception {

		
		Response response = new Response();

		try {

			List<Long> listOfMobilenumbers = new ArrayList<>();
			listOfMobilenumbers.add(request.getMob_number());

			URL url =  new URL(propertyConfiguration.getProperty("smsServiceUrl"));
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setDoOutput(true);
			String jsonInputString = "";
			if(!request.getSmsTemplate().isEmpty()) {
				jsonInputString = "{\r\n" + 
						"\"listOfMobileNumbers\":[\""+request.getMob_number()+"\"],	\r\n" + 
						"\"content\":\""+request.getSmsTemplate().replace("#otp", otp)+"\"\r\n" + 
						
						"}";
			}else {
				jsonInputString = "{\r\n" + 
						"\"listOfMobileNumbers\":[\""+request.getMob_number()+"\"],	\r\n" + 
						"\"content\":\""+otp+" "+propertyConfiguration.getProperty("smsContent")+"\"\r\n" + 
						
					
						"}";
			}
			System.out.println("SMS String"+jsonInputString);
			System.out.println("SMS Service URL "+url);
			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
				System.out.println(" Input : "+input);
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder resp = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					resp.append(responseLine.trim());
					System.out.println("Resp : "+resp);
				}
				Gson gson = new Gson();
				response = gson.fromJson(resp.toString(), Response.class);
				System.out.println("Sms response==="+response.getMessage());
				if (response.isStatus()) {
					return true;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean call4DigitSmsService(Properties p, String mobileNumber, String content) throws Exception {

		Response response = new Response();

		try {

			
			List<String> listOfMobilenumbers = new ArrayList<>();
			listOfMobilenumbers.add(mobileNumber);

    
			URL url = new URL(p.getProperty("smsServiceUrl"));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setDoOutput(true);
			String jsonInputString = "{\"listOfMobileNumbers\":[\""+mobileNumber+"\"],   \r\n" + 
					"\"content\":\""+content+"\"\r\n" + 
					"}";
			
			System.out.println("JSON!)(*#)@)*(#)@(==========="+jsonInputString);
			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder resp = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					resp.append(responseLine.trim());
				}
				Gson gson = new Gson();
				response = gson.fromJson(resp.toString(), Response.class);
				if (response.isStatus()) {
					return true;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public  boolean call4DigitEmailService(Properties p, String emailid, String otp) throws Exception {

		Response response = new Response();
		try {

			System.out.println(p.getProperty("emailServiceUrl"));
			URL url = new URL(p.getProperty("emailServiceUrl"));
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-Type", "application/json; utf-8");
			con.setDoOutput(true);
			String jsonInputString = "{\r\n" + 
					"        \"personalizations\":[{\"recipient\":\""+emailid+"\"}],\r\n" + 
					"        \"content\":\""+p.getProperty("emailcontent") + otp+"\",\r\n" + 
					"        \"subject\":\""+"One time password"+"\"\r\n" + 
					"}";

			System.out.println("Email content======"+jsonInputString);
			try (OutputStream os = con.getOutputStream()) {
				byte[] input = jsonInputString.getBytes("utf-8");
				os.write(input, 0, input.length);
			}
			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
				StringBuilder resp = new StringBuilder();
				String responseLine = null;
				while ((responseLine = br.readLine()) != null) {
					resp.append(responseLine.trim());
				}
				Gson gson = new Gson();
				response = gson.fromJson(resp.toString(), Response.class);
				if (response.isStatus()) {
					return true;
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	public  OtpEntities4Digit getOtpEntities(List<OtpEntities4Digit> s) {

		OtpEntities4Digit s2 = null;
		for (OtpEntities4Digit s1 : s) {
			s2 = s1;
		}
		return s2;
	}
	public Long getSerialNumber(List<OtpEntities4Digit> s) {

		OtpEntities4Digit s2 = null;
		for (OtpEntities4Digit s1 : s) {
			s2 = s1;
		}
		if (s2 == null || s2.getSerialNumber() == 0L) {
			return 0L;
		}
		return s2.getSerialNumber();
	}
	
	public  Response4Digit saveOutSourceLeadInformation(Properties p, String username, Request4Digit req) throws Exception {
		Request4Digit request = new Request4Digit();
		request.setUsername(username);
		request.setOutSource(req.getOutSource());
		request.setProduct(req.getProduct());
		request.setName(req.getName());
		request.setUtmSource(req.getUtmSource());
		request.setUtmMedium(req.getUtmMedium());
		request.setUtmCampaign(req.getUtmCampaign());
		request.setUtmTerm(req.getUtmTerm());
		request.setUtmContent(req.getUtmContent());
		Response4Digit res = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		URL url = new URL(p.getProperty("saveOutSourceLeadUrl"));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);
		String jsonInputString = mapper.writeValueAsString(request);

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			Gson gson = new Gson();
			res = gson.fromJson(response.toString(), Response4Digit.class);

			if (res.isStatus()) {
				return res;
			}
		}

		return res;
	}
	
	public  Response4Digit savePartnerOnboardingDetails(Properties p, String username,String emailId) throws Exception {
		Request4Digit request = new Request4Digit();
		request.setUsername(username);
		request.setEmailId(emailId);
		Response4Digit res = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		URL url = new URL(p.getProperty("savepartnerOnboarding"));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);
		String jsonInputString = mapper.writeValueAsString(request);

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			Gson gson = new Gson();
			res = gson.fromJson(response.toString(), Response4Digit.class);

			if (res.isStatus()) {
				return res;
			}
		}

		return res;
	}
	
	
	public  Response4Digit updatePartnerOnboardingDetails(Properties p, String username) throws Exception {
		Request4Digit request = new Request4Digit();
		request.setUsername(username);
	//	request.setEmailId(emailId);
		Response4Digit res = null;
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		URL url = new URL(p.getProperty("updatePartnerOnboarding"));
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);
		String jsonInputString = mapper.writeValueAsString(request);

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			Gson gson = new Gson();
			res = gson.fromJson(response.toString(), Response4Digit.class);

			if (res.isStatus()) {
				return res;
			}
		}

		return res;
	}
}
