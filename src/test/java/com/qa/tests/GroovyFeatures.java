package com.qa.tests;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static io.restassured.path.json.JsonPath.*;
import java.util.List;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class GroovyFeatures {
	
	
	/**
	 * Validating the response against pre-defined schema
	 * 
	 */
	@Test	
	public static void testSchema() {
		
		given().
		when().
			get("https://reqres.in/api/users").
		then().body(matchesJsonSchemaInClasspath("reqres.json"));
	}
	
	/**
	 * Verify the sum of first name
	 */
	@Test
	public void testHasItems() {
		
		given().
			get("https://reqres.in/api/users?page=2").
		then().
			body("data.first_name*.length().sum()", greaterThan(36));
			
	}
	
	/**
	 * Get the list of name nodes from the response
	 */
	@Test
	public void testListOfNodes() {
		
		String response = given().get("https://reqres.in/api/users?page=2").asString();
		
		List<String> listName = from(response).getList("data.first_name");
		
		System.out.println(listName);
		
		System.out.println(listName.contains("Lindsay"));
		
			
	}
	
	
	/**
	 * Get the name which has characters greater than 10 from the list of name nodes from the response
	 * Groovy has implicit variable called it which represents the current item in the list
	 */
	@Test
	public void testCountNodes() {
		
		String response = given().get("https://reqres.in/api/users?page=2").asString();
		
		List<String> listName = from(response).getList("data.findAll{ it.first_name.length()>6}.first_name");
		
		System.out.println(listName);
		
		System.out.println(listName.contains("Lindsay"));
		
			
	}
	
	/**
	 * Extract the response as string and fetching the data from string for further details
	 */
	@Test
	public void testJsonPath() {
		
		String response = given().get("https://reqres.in/api/users?page=2").asString();
		
		JsonPath jsonpath = new JsonPath(response).setRoot("data");
		List<String> lst = jsonpath.get("first_name");
		
		System.out.println(lst.contains("Lindsay"));
		
			
	}
	
	
	

}
