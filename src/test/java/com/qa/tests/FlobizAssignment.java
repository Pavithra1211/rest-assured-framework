package com.qa.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import junit.framework.Assert;


public class FlobizAssignment {
	
	public Properties prop = null;
	public FileInputStream fileInput = null;
	
	
	@BeforeTest
	public void loadResources() throws Exception {
		
		fileInput = new FileInputStream("./configs/URLs.properties");
		prop = new Properties();
		prop.load(fileInput);
	}
	
	/**
	 * Verifying whether the get call is success and the response contains all the issues
	 * 
	 */
	@Test
	public void testListAll() {
		given().
			header("Content-Type","application/json").
			and().
			header("x-apikey","5da6fb5d3cbe87164d4bb35d").
		when().			
			get(prop.getProperty("ListAllIssues")).
		then().
			statusCode(200).
			assertThat().body("size()", greaterThan(0));
			
	}
	
		
		
	/**
	 * Verifying whether the get call is success and the response contains the same issue id passed
	 * 
	 */
	@Test
	public void testIssueById() {
		given().
			header("Content-Type","application/json").
			and().
			header("x-apikey","5da6fb5d3cbe87164d4bb35d").
		when().			
			get(prop.getProperty("IssueByID")).
		then().
			statusCode(200).
			assertThat().body("_id", equalTo("588893fbf54b5f59000003ce"));
			
	}
	
	/**
	 * Verifying whether the get call is success and the response contains the issues which has fromemail "knutmt@gmail.com"
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testIssueByFromEmail() throws Exception {
		
		Object object=null;
		JSONArray arrayObj=null;
		
		String s = 	given().header("Content-Type","application/json").
				and().
				header("x-apikey","5da6fb5d3cbe87164d4bb35d").
			when().			
				get(prop.getProperty("IssueByQueryParam")).		
		then().
		statusCode(200).
		log().body().extract().asString();		
		
		JSONParser jsonParser=new JSONParser();
		object=jsonParser.parse(s);
		arrayObj=(JSONArray) object;
		
		for(int i=0;i<arrayObj.size();i++) 
		{
		JSONObject Issues = (JSONObject) arrayObj.get(i);
		String fromemail = (String) Issues.get("fromemail");
		
		System.out.println(fromemail);
		
		Assert.assertEquals(true, fromemail.contentEquals("knutmt@gmail.com"));		
		
		}			
	}
	
	/**
	 * Verifying whether post call is success with invalid issue id and printing the response
	 * 
	 */
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
			post(prop.getProperty("CreateIssue")).
		then().
			assertThat().statusCode(201).log().all();
			
	}	
	
	/**
	 * Update the data using put method and verify the status and body
	 * 
	 */
	@Test
	public void testModifyIssue() {		
		
		given().
			header("Content-Type", "application/json").
			and().
			header("x-apikey", "5da6fb5d3cbe87164d4bb35d").
			body("{\"title\": \"New Issue!\"}").
		when().			
			put(prop.getProperty("ModifyIssue")).
		then().
			statusCode(200).
			and().
			body("title", equalTo("New Issue!"));			
	}
	
	/**
	 * Verifying whether the post call is success and printing the response
	 * 
	 */
	@Test
	public void testCreateIssueErrorMessage() {
		
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
			post(prop.getProperty("CreateIssue")).
		then().
			assertThat().statusCode(500).and().
			assertThat().statusLine("HTTP/1.1 500 Internal Server Error").
			body("message", equalTo("ProcessTerminatedError: cancel after 1 retries!")).
			body("name", equalTo("Error"));
			
	}	
	
	/**
	 * Update the data using put method and verify the status and body
	 * 
	 */
	@Test
	public void testModifyIssueErrorMessage() {		
		
		given().
			header("Content-Type", "application/json").
			and().
			header("x-apikey", "5da6fb5d3cbe87164d4bb35d").
			body("{\"title\": \"New Issue!\"}").
		when().			
			put(prop.getProperty("ModifyIssue")).
		then().
			assertThat().statusCode(500).and().
			assertThat().statusLine("HTTP/1.1 500 Internal Server Error").
			body("message", equalTo("Nothing was updated. Check Query.")).
			body("name", equalTo("Error"));		
	}
	
	
	
}
