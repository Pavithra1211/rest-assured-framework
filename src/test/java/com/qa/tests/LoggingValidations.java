package com.qa.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;

public class LoggingValidations {
	
	@BeforeClass
	  public void responseSpec () {
	    
	    RestAssured.baseURI="https://maps.googleapis.com";
	    
	 }
	  
	  @Test
	  public void loggingTest() {
	      
	      given()
	         .param("query", "Churchgate Station")
	         .param("key","XYZ" )
	         .when()
	         .get("/maps/api/place/textsearch/json")
	         .then()
	         .log().ifError().assertThat().statusCode(200);
	                 
	  }

}
