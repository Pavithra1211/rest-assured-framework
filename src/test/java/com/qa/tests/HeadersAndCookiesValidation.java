package com.qa.tests;

import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HeadersAndCookiesValidation {
	
	
	/**
	 * Verify the headers using get call
	 */
	@Test
	public void testHeaders() {
		
		Response response = given().get("https://reqres.in/api/users?page=2");
		
		System.out.println("content-type -> "+response.getHeader("content-type"));
		
		
		Headers headers = response.getHeaders();
		
		for(Header h: headers){
			
			System.out.println(h.getName()+"\t"+h.getValue());
		}
			
	}
	
	/**
	 * Verify the cookies using get call
	 */
	@Test
	public void testCookies() {
		
		Response response = given().get("https://reqres.in/api/users?page=2");
		
		System.out.println("Cookie Present -> \n"+response.getCookies());
					
	}
	
	
	/**
	 * Verify the detailed cookies using get call
	 */
	@Test
	public void testDetailedCookies() {
		
		Response response = given().get("https://reqres.in/api/users?page=2");
		
		System.out.println("Cookie Present -> \n"+response.getDetailedCookie("__cfduid"));
		
		Cookie c = response.getDetailedCookie("__cfduid");
		
		System.out.println(c.getPath());
		System.out.println(c.hasExpiryDate());
		
		
			
	}
	
	/**
	 * To Set multi cookies in get request
	 */
	@Test
	public void testSetMultiCookies() {
		
		//Set multi cookie
		given().cookie("key","value1","value2");
		
		
		//To set detailed cookie
		Cookie cookie = new Cookie.Builder("name1","value1").setSecured(true).setComment("Some Comment").build();
		Cookie cookie1 = new Cookie.Builder("name2","value2").setSecured(true).setComment("Some Comment2").build();
		
		Cookies cookies = new Cookies(cookie1, cookie);
		
		given().cookies(cookies).when().get("/cookies").then().statusCode(200);
		
		
		
		
			
	}
	
	

}
