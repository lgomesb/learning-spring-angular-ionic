package com.learning.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.learning.cursomc.services.DBService;
import com.learning.cursomc.services.EmailService;
import com.learning.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	@Value("spring.jpa.hibernate.ddl-auto")
	private String strategy;
	
	@Bean
	public boolean instantiateDatabase() throws Exception {
		
		if(!"create".equals(strategy)) 
			return false;
		
		dbService.instantiateTestDatabase();		
		return true;
	}
	
	@Bean
	public EmailService emailService() { 
		return new SmtpEmailService();
	}
	
}
