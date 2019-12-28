package com.qa.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class Authentication {
	
	
	/*
	 * To set up a username and password for all the tests in the class or in the classes which are extends this class.
	 * So that we don't need to set the credentials for each test
	 */
	@BeforeClass
	public void setup() {
		RestAssured.authentication = RestAssured.preemptive().basic("ToolsQA", "TestPassword");
	}
	
	
	/*
	 * Preemptive Basic Authentication:
	 * 
	 * This will send the basic authentication even before the server sends
	 * unauthorized response. It reduces the overhead of making additional connections
	 */
	//@Test
	public void testPreemptiveAuth() {
		given().
		auth().
		preemptive().
		basic("ToolsQA","TestPassword").
		when().
		get("http://restapi.demoqa.com/authentication/CheckForAuthentication").
		then().statusCode(200);
	}
	
	
	/*
	 * Challenged Authentication:
	 * 
	 * Restassured will not supply the credentials unless the server explicitly asked for it.
	 * Upon additional request the basic credentials will be sent through headers
	 */
	
	@Test
	public void testBeforeClassPreemptiveAuth() {
		
		given().
		get("http://restapi.demoqa.com/authentication/CheckForAuthentication").
		then().statusCode(200);
		
	}
	
	/*
	 * An HTTP cookie is a small piece of data that the server sends to web browser
	 * The browser may store the cookie and send it back to server with the next request to maintain the login sessions
	 */
	
	@Test
	public void testCookieValidation() {
		
		Response response = given().
							header("Content-Type", "application/json").
							body("{\"title\": \"New Issue!\"}").
							post("/url");
		
		String sessionID = response.getCookies().get("SessionID");
		System.out.println(sessionID);
				
	}

}
