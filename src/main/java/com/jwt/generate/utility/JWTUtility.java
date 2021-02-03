package com.jwt.generate.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import java.time.Instant;
import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class JWTUtility {
	private static final String TokenIssuerLocal = "localTokenIssuer";
	private static final String TokenIssuerDi = "diTokenIssuer";
	private static final String TokenIssuerDp = "dpTokenIssuer";
	private static final String TokenIssuerDt = "dtTokenIssuer";

	private static String scope = "cmg-cps";
	private static String motoScope = "cmg-moto";
	private static String ivrScope = "cmg-moto lambda";
	private static String AUTHORIZED_PARTY = "azp";

	// secret keys for different environments
	private static String localSecretKey = "localSecretKey";
	private static String diSecretKey = "diSecretKey";
	private static String dpSecretKey = "dpSecretKey";
	private static String dtSecretKey = "dtSecretKey";

	public static String generateCpsJwt(String env) {
		System.out.println("Checking -- online | method");
		String[] audience = new String[1];
		Map<String, Object> claimsMap = new LinkedHashMap<>();
		Map<String, Object> header = new LinkedHashMap<>();
		audience[0] = "cmg-cps-service";

		claimsMap.put("scope", scope);
		claimsMap.put("iss", getIssuer(env));
		claimsMap.put("aud", audience);
		claimsMap.put(AUTHORIZED_PARTY, "cmg-cps-uihandler");
		Instant now = Instant.now();
		header.put("alg", "HS256");
		header.put("typ", "JWT");

		String jwt = Jwts.builder().setHeader(header).setClaims(claimsMap).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(now.plusSeconds(86400)))
				.signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(getSecretKey())).compact();
		return jwt;
	}

	public static String generateMotoJwt(String env) {
		System.out.println("Checking -- pcipal | method");
		String[] audience = new String[1];
		Map<String, Object> claimsMap = new LinkedHashMap<>();
		Map<String, Object> header = new LinkedHashMap<>();
		// audience[0] = "cmg-moto-service";
		audience[0] = "cmg-moto-notification";

		claimsMap.put("scope", motoScope);
		claimsMap.put("iss", getIssuer(env));
		claimsMap.put("username", "cmg-moto-payment");
		claimsMap.put("password", "secret");
		claimsMap.put("grant_type", "cmg_client_credentials");
		claimsMap.put("aud", audience);
		Instant now = Instant.now();
		header.put("alg", "HS256");
		header.put("typ", "JWT");

		String jwt = Jwts.builder().setHeader(header).setClaims(claimsMap).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(now.plusSeconds(86400)))
				.signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(getSecretKey())).compact();
		return jwt;
	}

	public static String generateIvrJwt(String env) {
		System.out.println("Checking -- ivr | method");
		String[] audience = new String[2];
		Map<String, Object> claimsMap = new LinkedHashMap<>();
		Map<String, Object> header = new LinkedHashMap<>();
		audience[0] = "cmg-moto-service";
		audience[1] = "cmg-moto-notification";

		claimsMap.put("scope", ivrScope);
		claimsMap.put("iss", getIssuer(env));
		claimsMap.put("username", "cmg-moto-payment");
		claimsMap.put("password", "secret");
		claimsMap.put("grant_type", "cmg_client_credentials");
		claimsMap.put("aud", audience);
		Instant now = Instant.now();
		header.put("alg", "HS256");
		header.put("typ", "JWT");

		String jwt = Jwts.builder().setHeader(header).setClaims(claimsMap).setIssuedAt(Date.from(Instant.now()))
				.setExpiration(Date.from(now.plusSeconds(86400)))
				.signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(getSecretKey())).compact();
		return jwt;
	}

	private static String getSecretKey() {
		String env = "targetEnvironment";
		switch (env) {
		case "local":
			return localSecretKey;
		case "di":
			return diSecretKey;
		case "dp":
			return dpSecretKey;
		case "dt":
			return dtSecretKey;
		default:
			throw new IllegalArgumentException();
		}
	}

	private static String getIssuer(String env) {
		System.out.println("Environnment -- "+env.toLowerCase());
		switch (env.toLowerCase()) {
		case "local":
			return TokenIssuerLocal;
		case "di":
			return TokenIssuerDi;
		case "dp":
			return TokenIssuerDp;
		case "dt":
			return TokenIssuerDt;
		default:
			throw new IllegalArgumentException();
		}
	}
}
