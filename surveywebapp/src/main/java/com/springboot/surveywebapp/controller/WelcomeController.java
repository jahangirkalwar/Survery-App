package com.springboot.surveywebapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.surveywebapp.configuration.BasicConfiguration;
import com.springboot.surveywebapp.service.WelcomeService;

@RestController
public class WelcomeController {

	@Autowired
	WelcomeService welcomeService;
	
	@Autowired
	private BasicConfiguration configuration;
	
	@GetMapping("/welcome")
	public String welcome() {
		return welcomeService.retrieveWelcomeMessage();
	}
	
	@GetMapping("/dynamic-configuration")
	public Map dynamicConfiguration () {
		Map map= new HashMap();
		map.put("message", configuration.getMessage());
		map.put("number", configuration.getNumber());
		map.put("value", configuration.isValue());
		
		return map;
	}
}
