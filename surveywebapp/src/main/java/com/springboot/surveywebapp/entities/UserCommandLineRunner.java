package com.springboot.surveywebapp.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner{
	
    private static final Logger log=LoggerFactory.getLogger(UserCommandLineRunner.class);

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		userRepository.save(new User("Imtiaz","Admin"));
		userRepository.save(new User("Jahangeer","User"));
		userRepository.save(new User("Nisar","Admin"));
		userRepository.save(new User("Zeeshan","User"));
		
		
		
		for(User user:userRepository.findAll()) {
			log.info(user.toString());
		}
		
		log.info("Admins users are........");
		log.info("------------------------");
		
		for(User user:userRepository.findByRole("Admin")) {
			log.info(user.toString());
		}
		
	}

}
