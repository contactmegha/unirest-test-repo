package APItest;

import java.util.Arrays;

import org.testng.annotations.Test;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
 

public class GenetalTests {
	@Test
	public void requestFunc() {
		HttpResponse<JsonNode> response = Unirest.post("http://dummy.restapiexample.com/api/v1/create")
			      .header("accept", "application/json")
			      //.queryString("apiKey", "123")
			      .field("name", "test")
			      .field("salary", "123")
			      .field("age", "23")
			      .asJson();
		 System.out.println(response.getBody().toString());
	}
	
	@Test
	public void routeParameter() {
		HttpResponse<String> response = Unirest.get("http://httpbin.org/{fruit}")
	     .routeParam("fruit", "apple")
	     .asString();
		System.out.println(response.getBody().toString());
	}
	
	@Test
	public void defaultBaseURL() {
		Unirest.config().defaultBaseUrl("http://homestar.com");
	    
		 HttpResponse<String> str=  Unirest.get("/runner").asString();
		 System.out.println(str.getBody().toString());
	}
	@Test
	public void queryParameter() {
		HttpResponse<String> response =Unirest.get("http://httpbin.org")
        .queryString("fruit", "apple")
        .queryString("droid", "R2D2")
        .asString();
	}
	@Test
	public void queryParameter2() {
		Unirest.get("http://httpbin.org")
        .queryString("fruit", Arrays.asList("apple", "orange"))
       // .queryString(ImmutableMap.of("droid", "R2D2", "beatle", "Ringo"))
        .asString();
	}
	@Test
	public void headers() {
		Unirest.get("http://httpbin.org")
        .header("Accept", "application/json")
        .header("x-custom-header", "hello")
        .asString();
	}
	@Test
	public void basicAuth() {
		Unirest.get("http://httpbin.org")
        .basicAuth("user", "password1!")
        .asString();
	}
	@Test
	public void entityBody() {
		Unirest.post("http://httpbin.org")
        .body("This is the entire body")
        .asEmpty();
	}
	@Test
	public void entityBody2() {
		Unirest.post("http://httpbin.org")
        .header("Content-Type", "application/json")
        //.body(new SomeUserObject("Bob"))
        .asEmpty();
	}
	public void patchBodies() {
		Unirest.jsonPatch("http://httpbin.org")
        .add("/fruits/-", "Apple")
        .remove("/bugs")
        .replace("/lastname", "Flintstone")
        .test("/firstname", "Fred")
        .move("/old/location", "/new/location")
        .copy("/original/location", "/new/location")
        .asJson();
	}
}


