package com.jwt.generate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.generate.service.JWTService;

@RestController
public class JWTController {
	
	@Autowired
	private JWTService jwtService;

	@RequestMapping("/token/{envrionment}/{application}")
	public String getJWTToken(@PathVariable String envrionment, @PathVariable String application) {
		return jwtService.generateJWTToken(envrionment, application);
	}
}
