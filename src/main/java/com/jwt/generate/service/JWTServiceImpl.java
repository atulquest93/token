package com.jwt.generate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.generate.utility.JWTUtility;

@Service
public class JWTServiceImpl implements JWTService {

	@Autowired
	private JWTUtility jwtUtility;

	@Override
	public String generateJWTToken(String env, String application) {

		switch (application) {
		case "online":
			return "token"+":"+application+":"+env;
//			return jwtUtility.generateCpsJwt(env);
		case "pcipal":
			return "token"+":"+application+":"+env;
//			return jwtUtility.generateMotoJwt(env);
		case "ivr":
			return "token"+":"+application+":"+env;
//			return jwtUtility.generateIvrJwt(env);
		default:
			throw new IllegalArgumentException();
		}

	}

}
