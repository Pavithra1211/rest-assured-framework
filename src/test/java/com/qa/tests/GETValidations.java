package com.qa.tests;

import static io.restassured.RestAssured.given;
import java.util.List;
import static org.hamcrest.Matchers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class GETValidations {
	
	/**
	 * Simply checking the status code
	 *
	 */
	@Test
	public void testStatusCode() {
		
		given().
			get("https://restcountries.eu/rest/v2/name/colombia").			
		then().statusCode(200);
			
	}
	

	/**
	 * Checking the status code and logging the whole response
	 *
	 */
	@Test
	public void testLogging() {
		
		given().
			get("https://reqres.in/api/user/2").			
		then().
			statusCode(200).
			log().all();
			
	}
	
	/**
	 * Checking the equal to function
	 *
	 */
	@Test
	public void testEqualToFunction() {
		
		given().
			get("https://reqres.in/api/user/2").			
		then().
			body("data.name", equalTo("fuchsia rose"));
			
	}
	
	/**
	 * Checking the output with JSON Array methods
	 * @throws ParseException 
	 * 
	 */
	@Test
	public void testJSONarray() throws ParseException   {		
				
		String s = given().
		get("https://restcountries.eu/rest/v2/name/colombia").			
		then().
		statusCode(200).
		log().body().extract().asString();
		
		Object object=null;
		JSONArray arrayObj=null;
		JSONParser jsonParser=new JSONParser();
		object=jsonParser.parse(s);
		arrayObj=(JSONArray) object;
		System.out.println("RESPONSE BODY\n\n");		
		System.out.println(arrayObj.get(0));
		
		for(int i=0;i<arrayObj.size();i++) 
		{
		JSONObject users = (JSONObject) arrayObj.get(i);
		System.out.println("Users -> "+users);//This prints every block - one json object
		String region = (String) users.get("region");
		System.out.println("The region for Colombia "+region);
		
		}
	}
	
	/**
	 * Verifying multiple contents using hamcrest matcher library
	 * When the expected value is integer, we should not use double quotes
	 */
	@Test
	public void testHasItems() {
		given().
			get("https://reqres.in/api/users?page=2").
		then().
			body("data.id", hasItems(7,8));
			
	}
	
	/**
	 * Passing open weather app id
	 * 
	 */
	@Test
	public void testHeaderParameter() {
		given().
			pathParam("APIKEY","4c1220bced802c52c53d8162893e162b").
			queryParam("zip","94040").
			//pathParam("userid",2.5).
		when().			
			get("https://samples.openweathermap.org/data/2.5/weather&APPID={APIKEY}").
		then().
			statusCode(200).log().all();
		
		//get("https://samples.openweathermap.org/data/2.5/weather?zip=94040,us&APPID={APIKEY}").
		
					
	}
	
	/**
	 * Verifying query parameter
	 * 
	 */
	@Test
	public void testQueryParameter() {
		given().
			header("appid","b6907d289e10d714a6e88b30761fae22").
			param("zip",94040).
		when().			
			get("https://samples.openweathermap.org/data/2.5/weather").
		then().
			statusCode(200);
			
	}
	
	/**
	 * Verifying path parameter
	 * 
	 */
	@Test
	public void testPathParameter() {
		given().
			header("appid","94e379c0ad140f15b31d85bebd79e43e").
			queryParam("zip",94040).
			pathParam("userid",2.5).
		when().			
			get("https://samples.openweathermap.org/data/{userid}/weather").
		then().
			statusCode(200).
			log().all();
			
	}
	
	/**
	 * Validating header from get call
	 * 
	 */
	@Test
	public void testHeader() {		
		given().
			expect().
	    headers("Content-Length", "482").
			when().
		get("http://www.thomas-bayer.com/sqlrest/");		
	}
	
	/**
	 * Validating xml response
	 * 
	 */
	@Test
	public void testxmlOutput() {
		given().		
			get("http://www.thomas-bayer.com/sqlrest/").
		then().
			statusCode(200).
		and().
			body("resource.ITEMList", equalTo("ITEM"));		
			
	}
	
	
	
	/**
	 * Validating the response with root path
	 * 
	 */
	
	@Test
	public void testWithRoot() {
			given().
		when().			
			get("https://samples.openweathermap.org/data/2.5/weather?zip=94040,us&APPID=4c1220bced802c52c53d8162893e162b").
		then().
		    root("wind").
			body("deg",is(340));
			
	}
	
	/**
	 * Validating the extraction of data using path
	 * 
	 */
	
	@Test
	public void testExractDetailWithPath() {
		String url = given().
					
				when()
					.get("https://jsonplaceholder.typicode.com/photos/1").
				then().
					body("albumId",is(1))
				.extract().path("url");
		
		given().when().get(url).then().assertThat().statusCode(200).and().log().all();
			
	}
	
	/**
	 * Extract response for further use
	 * 
	 */
	
	@Test
	public void testExractResponse() {
		Response res = given().
				when().
					get("https://jsonplaceholder.typicode.com/photos/1").
				then().			
					extract().response();
		
		System.out.println("Album id"+"\t"+res.path("albumId"));
		System.out.println("Content Type"+"\t"+res.contentType());
		
		
			
	}
	
	
	/**
	 * To set multivalue parameter
	 * When there is multiple values to be set for the same key in parameters, this can be used
	 * 
	 */
	
	@Test
	public void testSetMultivalueParameter() {
		
		List<String> values = new ArrayList<String>();
		values.add("1");
		values.add("2");
		
				given().
				param("A", values).
				when().
					get("https://jsonplaceholder.typicode.com/photos/1").
				then().			
					extract().response();
		
		
	}
	
	/**
	 * To set path parameter
	 * 
	 **/
	
	@Test
	public void testSetPathParameter() {
	
				given().
				pathParam("number", 1).
				when().
					get("https://jsonplaceholder.typicode.com/photos/{number}/").
				then().	
				statusCode(200);
		
		
	}
	
	/**
	 * To set cookies in request
	 * 
	 **/
	
	@Test
	public void testSetCookies() {
	
				given().
				cookie("__cfduid","1").
				when().
					get("https://jsonplaceholder.typicode.com/photos/1/").
				then().	
				statusCode(200);
		
		
	}
	
	
				
			
	

}
