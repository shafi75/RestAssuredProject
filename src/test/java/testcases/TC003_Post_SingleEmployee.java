package testcases;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import Utilities.RestUtils;
import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
public class TC003_Post_SingleEmployee extends TestBase{
	String empName=RestUtils.empName();
	String empSal=RestUtils.empSal();
	String empAge=RestUtils.empAge();
	@BeforeClass
	public void createEmployeeData() throws InterruptedException {
		logger.info("************* TC002_Post_CreateEmployee started *************");
		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
		httpRequest=RestAssured.given();
		//JSONObject is a class that represents a simple JSON,We can add key value pairs
		JSONObject requestParams=new JSONObject();
		requestParams.put("name", empName);
		requestParams.put("salary", empSal);
		requestParams.put("age", empAge);
		
		//Adding a header that checks that request body is JSON
		httpRequest.header("Content-Type","application/json");
		//httpRequest.header("Retry-After",10000);
		//Add the JSON to the body of the request
		httpRequest.body(requestParams.toJSONString());
		
		response=httpRequest.request(Method.POST,"/create");
		Thread.sleep(60000);		
	}
	@Test
	public void CheckResponseBody() {
		String responseBody=response.getBody().asString();
		System.out.println("Response body is :"+responseBody);
		Assert.assertEquals(responseBody.contains(empName),true);
		Assert.assertEquals(responseBody.contains(empSal),true);
		Assert.assertEquals(responseBody.contains(empAge),true);
	}
	@Test
	public void CheckStatusCode() {
		int statusCode=response.getStatusCode();
		System.out.println("Status Code is:"+statusCode);
		Assert.assertEquals(statusCode,200);	
	}
	@Test
	public void CheckStatusLine() {
		String statusLine=response.getStatusLine();
		System.out.println("Status Line is:"+statusLine);
		Assert.assertEquals(statusLine,"HTTP/1.1 200 OK");	
	}
	@Test
	public void CheckServerType() {
		String serverType=response.header("Server");
		System.out.println("Server is:"+serverType);
		Assert.assertEquals(serverType,"cloudflare");	
	}
	@Test
	public void CheckContentEncoding() {
		String contentEncoding=response.header("Content-Encoding");
		System.out.println("Content Encoding is:"+contentEncoding);
		Assert.assertEquals(contentEncoding,"gzip");	
	}
	
	@AfterClass
	public void teardown() {
		logger.info("************* TC002_Post_CreateEmployee is finished *************");
	}
	

}
