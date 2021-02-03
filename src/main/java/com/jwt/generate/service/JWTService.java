package com.jwt.generate.service;




public interface JWTService {

	public String generateJWTToken(String env, String application);
}
