package com.ghf.generateOtp.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ghf.generateOtp.model.OtpEntities;

@Repository
public interface OtpOperationRepositoryI extends CrudRepository<OtpEntities,Long> {

	/*@Query(value = "SELECT * FROM otp_entities u WHERE u.mobile_number = ?1 AND u.email_id = ?2",  nativeQuery = true)
	public List<OtpEntities> findByUser(long mobileNumber, String emailId);
	
	//@Query(value = "SELECT * FROM otpentities u WHERE u.mobilenumber = ?1 AND u.emailid = ?2",  nativeQuery = true)
	@Query("SELECT u from OtpEntities u where u.mobilenumber=:mobileNumber and u.emailid=:emailId")
	public List<OtpEntities> findByUser1(long mobileNumber, String emailId);*/

	/*
	@Query(value = "SELECT * FROM otpentities u WHERE u.mobilenumber = ?1 AND u.emailid = ?2",  nativeQuery = true)
	public List<OtpEntities> findByUser(long mobileNumber, String emailId);
	*/
	
	//rahul
	/*
	@Query(value = "SELECT * FROM customer u WHERE u.mob_number = ?1 OR u.email_id = ?1",  nativeQuery = true)
	public List<OtpEntities> findByUser1(String input);
	*/
	
	@Query(value = "SELECT * FROM otpentities u WHERE u.mobilenumber = ?1 AND u.emailid = ?2 OR u.otp = ?3",  nativeQuery = true)
	public List<OtpEntities> findByUSerOTP(long mobileNumber, String emailId, String otp);
	
	/*
	@Query(value = "SELECT * FROM otpentities u WHERE u.otp = ?1",  nativeQuery = true)
	public List<OtpEntities> findByUSerOTP1(String otp);
	*/
	

}
