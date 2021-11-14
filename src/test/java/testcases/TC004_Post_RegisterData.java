package testcases;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import Utilities.RestUtils;
import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
public class TC004_Post_RegisterData extends TestBase {
	String empName=RestUtils.empName();
	String empJob=RestUtils.empJob();
	@BeforeClass
	public void RegisterEmployeeData() throws InterruptedException {
		logger.info("************* TC004_Post_RegisterData started *************");
		RestAssured.baseURI="https://reqres.in/api";
		httpRequest=RestAssured.given();
		//JSONObject is a class that represents a simple JSON,We can add key value pairs
		JSONObject requestParams=new JSONObject();
		requestParams.put("name", empName);
		requestParams.put("job", empJob);
		//Adding a header that checks that request body is JSON
		httpRequest.header("Content-Type","application/json");
		//httpRequest.header("Retry-After",10000);
		//Add the JSON to the body of the request
		httpRequest.body(requestParams.toJSONString());
		
		response=httpRequest.request(Method.POST,"/users");
		Thread.sleep(5000);		
	}
	@Test
	public void validationsonPostRegisterData() {
		test=extent.createTest("Validations on post register data");
		
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Validating response body"+"</b>",ExtentColor.WHITE));
		String responseBody=response.getBody().asString();
		System.out.println("Response body is :"+responseBody);
		test.log(Status.INFO,MarkupHelper.createLabel("Response body is: "+responseBody, ExtentColor.WHITE));
		Assert.assertEquals(responseBody.contains(empName),true);
		test.log(Status.INFO,MarkupHelper.createLabel("Validated: "+empName+"successfully ", ExtentColor.WHITE));
		Assert.assertEquals(responseBody.contains(empJob),true);
		test.log(Status.INFO,MarkupHelper.createLabel("Validated: "+empJob+"successfully ", ExtentColor.WHITE));
		test.log(Status.INFO,MarkupHelper.createLabel("Response body is validated", ExtentColor.WHITE));
	
		logger.info("************* Checking Status Code *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Validating status code"+"</b>",ExtentColor.WHITE));
		int statusCode=response.getStatusCode();
		logger.info("Status code===> "+statusCode);
		test.log(Status.INFO,MarkupHelper.createLabel("Status code value is: "+statusCode,ExtentColor.WHITE));
		Assert.assertTrue(statusCode==201);
		test.log(Status.INFO,MarkupHelper.createLabel("Status code validated successfully as"+statusCode,ExtentColor.WHITE));
	
		logger.info("************* Checking Response Time *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Response Time"+"</b>",ExtentColor.WHITE));
		long responseTime=response.getTime(); //Getting Response time
		logger.info("Response time===> "+responseTime);
		if(responseTime>2000) {
			logger.warn("Response time is greater then 2000");
		}
		test.log(Status.INFO,MarkupHelper.createLabel("Validate response time is greater than 2000",ExtentColor.WHITE));
		Assert.assertTrue(responseTime<2000);
		test.log(Status.INFO,MarkupHelper.createLabel("Response time is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Status Line *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Status Line"+"</b>",ExtentColor.WHITE));
		String statusLine=response.getStatusLine(); //Getting Status Line
		logger.info("Status Line===> "+statusLine);
		test.log(Status.INFO,MarkupHelper.createLabel("Status line is: "+statusLine,ExtentColor.WHITE));
		Assert.assertEquals(statusLine,"HTTP/1.1 201 Created");
		test.log(Status.INFO,MarkupHelper.createLabel("Status line is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Content Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Content Type"+"</b>",ExtentColor.WHITE));
		String contentType=response.header("Content-Type"); //Getting Content Type Header
		logger.info("Content Type is ===> "+contentType);
		test.log(Status.INFO,MarkupHelper.createLabel("Value of content type is"+contentType,ExtentColor.WHITE));
		Assert.assertEquals(contentType,"application/json; charset=utf-8");
		test.log(Status.INFO,MarkupHelper.createLabel("Content type header is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Server Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Server Type"+"</b>",ExtentColor.WHITE));
		String serverType=response.header("Server"); //Getting Content Type Header
		logger.info("Server Type is ===> "+serverType);
		test.log(Status.INFO,MarkupHelper.createLabel("Value of server type: "+serverType,ExtentColor.WHITE));
		Assert.assertEquals(serverType,"cloudflare");
		test.log(Status.INFO,MarkupHelper.createLabel("Server type header is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Connection type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Connection type"+"</b>",ExtentColor.WHITE));
		String connection=response.header("Connection"); //Getting Content Type Header
		logger.info("Conection type is ===> "+connection);
		test.log(Status.INFO,MarkupHelper.createLabel("Value of connection type: "+connection,ExtentColor.WHITE));
		Assert.assertEquals(connection,"keep-alive");
		test.log(Status.INFO,MarkupHelper.createLabel("Connection type header is validated",ExtentColor.WHITE));
	}
	@AfterClass
	public void teardown() {
		logger.info("************* Finished TC004_Post_RegisterData*************");
	}
	@AfterMethod
	public void testCheck(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			String methodname=result.getMethod().getMethodName();
			String logtext="<b>"+"Test Case: - "+methodname.toUpperCase()+"Failed"+"</b>";
			Markup m=MarkupHelper.createLabel(logtext,ExtentColor.RED);
			test.fail(m);
		}
		else if(result.getStatus()==ITestResult.SKIP) {
			String methodname=result.getMethod().getMethodName();
			String logtext="<b>"+"Test Case: - "+methodname.toUpperCase()+"SKIPPED"+"</b>";
			Markup m=MarkupHelper.createLabel(logtext,ExtentColor.YELLOW);
			test.skip(m);
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			String methodname=result.getMethod().getMethodName();
			String logtext="<b>"+"Test Case: - "+methodname.toUpperCase()+"SUCCESS"+"</b>";
			Markup m=MarkupHelper.createLabel(logtext,ExtentColor.GREEN);
			test.pass(m);
		}
	}

}
