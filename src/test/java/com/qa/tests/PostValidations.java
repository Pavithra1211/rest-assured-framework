package com.qa.tests;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import com.qa.Payloads.PojoPayloads;

import io.restassured.response.Response;

public class PostValidations {
	
	
	@Test
	public void simplePost() {
		
		given().
		param("name","morpheus").
		param("job", "Leader").
		when().
		post("https://reqres.in/api/users").
		then().
		assertThat().statusCode(201).log().all();
		
		
		
	}
	
	@Test
	public void testCreateIssue() {
		
		Map<String, String> postdata = new HashMap<String, String>();
		postdata.put("description", "This is a new issue");
		postdata.put("fromemail", "knutmt@gmail.com");
		postdata.put("title", "New Issue");
	
	
		given().
			header("Content-Type", "application/json").
			and().
			header("x-apikey", "5da6fb5d3cbe87164d4bb35d").
			body(postdata).
		when().			
			post("https://flobizhiring-57e6.restdb.io/rest/issues").
		then().
			assertThat().statusCode(201).log().all();
			
	}	
	
	@Test
	public void testComplexPayload() throws Exception{
		
		String json = new PojoPayloads().JiraTask();
		
		Response response = given().
				header("Content-Type", "application/json").
				body(json).
				post("/url");

		String sessionID = response.getCookies().get("SessionID");
		System.out.println(sessionID);		
	}
	
	

}
