package com.ghf.generateOtp.repository;


import java.util.List;






import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ghf.generateOtp.model.OtpEntities4Digit;




@Repository
public interface Otp4DigitRepository extends JpaRepository<OtpEntities4Digit, Long> {




	public List<OtpEntities4Digit> findByMobileNumber(long mobilenumber);

	public List<OtpEntities4Digit> findByEmailId(String emailid);

	public List<OtpEntities4Digit> findByCustomerId(String customerid);
}
