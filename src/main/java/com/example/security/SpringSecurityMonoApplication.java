package com.example.security;

import com.example.security.common.model.payload.MailRequest;
import com.example.security.common.service.MailService;
import com.example.security.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringSecurityMonoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityMonoApplication.class, args);
	}

	@Autowired
	private MailService mailService;

	@Override
	public void run(String... args) throws Exception {
		String randomNumber = CommonUtils.randomNumber(100000, 999999);
		MailRequest otpMail = MailRequest.builder().to("distributor50@yopmail.com")
				.body("<h1>"+randomNumber+"</h1>").subject("OTP Mail").build();

		mailService.sendEmail(otpMail);
	}
}
