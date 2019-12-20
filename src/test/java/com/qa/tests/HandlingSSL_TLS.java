package com.qa.tests;

import static io.restassured.RestAssured.given;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class HandlingSSL_TLS {
	
	@BeforeClass
	public void setUp() {
		RestAssured.useRelaxedHTTPSValidation();
		
	}
	
	/**
	 * If server has invalid certificate, it will throw SSLpeerUnverifiedException 
	 * 
	 * To handle this we can relax the certificate condition so that the exception will not appear
	 */
	
	@Test
	public void testSSL() {
		given().
		get("https://reqres.in/api/users?page=2").
		then().
		statusCode(200);
		
		//or
		
		given().relaxedHTTPSValidation().
		get("https://reqres.in/api/users?page=2").
		then().
		statusCode(200);
		
		
	}
	
	/**
	 * To relax the TLS certificate
	 * We can the parameter of certificate type as well
	 */
	
	@Test
	public void testTLS() {
		
		
		given().relaxedHTTPSValidation("TLS").
		get("https://reqres.in/api/users?page=2").
		then().
		statusCode(200);
		
		
	}

}
