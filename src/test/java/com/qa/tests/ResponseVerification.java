package com.qa.tests;

import static io.restassured.RestAssured.given;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.Test;

import groovyjarjarantlr.LexerSharedInputState;
import io.restassured.matcher.ResponseAwareMatcher;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;


public class ResponseVerification {

	
	/**
	 * Verifying status line
	 *
	 */
	@Test
	public void testStauslineInResponse() {
		
		given().get("https://reqres.in/api/user/2").then().assertThat().statusCode(200);
		given().get("https://reqres.in/api/user/2").then().assertThat().statusLine("HTTP/1.1 200 OK");
		//given().get("https://reqres.in/api/user/2").then().assertThat().statusLine()
	}
	
	/**
	 * Verifying headers
	 *
	 */
	@Test
	public void testHeadersInResponse() {
		
		given().get("https://reqres.in/api/user/2").then().assertThat().header("x-powered-by", "Express");
		given().get("https://reqres.in/api/user/2").then().assertThat().headers("x-powered-by", "Express","Content-type","application/json; charset=utf-8");
		
	}
	
	/**
	 * Cookie changes every time
	 * Test should fail
	 */
	@Test
	public void testCookiesInResponse() {
		
		given().get("https://reqres.in/api/user/2").then().assertThat().cookie("__cfduid", "ddfb7fa7cebeb7d4d698f28dad04e6a881575996976");
		
		
	}
	
	/**
	 * Verify the performance of the call
	 */
	@Test
	public void testResponseTime() {
		
		long time = given().get("https://reqres.in/api/user/2").time(); // by default time in milliseconds
		given().get("https://reqres.in/api/user/2").then().time(lessThan(5L),TimeUnit.SECONDS);
		long timeS = given().get("https://reqres.in/api/user/2").timeIn(TimeUnit.SECONDS);
		
		System.out.println(time);
		System.out.println(timeS);
		
		
	}
}
