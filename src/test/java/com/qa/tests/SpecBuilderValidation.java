package com.qa.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderValidation {

	ResponseSpecification response;
	RequestSpecification request;
	
	@BeforeClass	
	public void setUpResSpec() {
		
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectStatusCode(200);
		builder.expectContentType("application/json; charset=utf-8");	
		builder.expectHeader("server", "cloudflare");
		builder.expectBody("data.id.size()", is(6));
		response = builder.build();
	}
	
	@BeforeClass	
	public void setUpReqSpec() {
		
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.addHeader("pragma", "no-cache");
		builder.addHeader("server", "cloudflare");
		request = builder.build();
	}
	
	
	@Test
	public void testresponse() {
		
		given().
		spec(request).
		when().
		get("https://reqres.in/api/users/").
		then().
		spec(response);

		
	}
}
