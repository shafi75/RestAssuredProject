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
public class TC005_PUT_UpdateData extends TestBase {
	String empName=RestUtils.empName();
	String empJob=RestUtils.empJob();
	@BeforeClass
	public void UpdateEmployeeData() throws InterruptedException {
		logger.info("************* TC005_PUT_UpdateData started *************");
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
		
		response=httpRequest.request(Method.POST,"/users/"+empId);
		Thread.sleep(5000);		
	}
	@Test
	public void validationOnPutUserData() {
		test=extent.createTest("Validations on update user data");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Validating response body"+"</b>",ExtentColor.WHITE));
		String responseBody=response.getBody().asString();
		System.out.println("Response body is :"+responseBody);
		Assert.assertEquals(responseBody.contains(empName),true);
		test.log(Status.INFO,MarkupHelper.createLabel("Validate Employee name is: "+empName,ExtentColor.WHITE));
		Assert.assertEquals(responseBody.contains(empJob),true);
		test.log(Status.INFO,MarkupHelper.createLabel("Validate Employee job is: "+empJob,ExtentColor.WHITE));
	
		logger.info("************* Checking Status Code *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Validating status code"+"</b>",ExtentColor.WHITE));
		int statusCode=response.getStatusCode();
		logger.info("Status code===> "+statusCode);
		test.log(Status.INFO,MarkupHelper.createLabel("Value of status code is: "+statusCode,ExtentColor.WHITE));
		Assert.assertTrue(statusCode==201);
		test.log(Status.INFO,MarkupHelper.createLabel("Status code is validated",ExtentColor.WHITE));
		
		logger.info("************* Checking Response Time *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Validating status code"+"</b>",ExtentColor.WHITE));
		long responseTime=response.getTime(); //Getting Response time
		logger.info("Response time===> "+responseTime);
		if(responseTime>2000) {
			logger.warn("Response time is greater then 2000");
		}
		Assert.assertTrue(responseTime<2000);
		test.log(Status.INFO,MarkupHelper.createLabel("Response time is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Status Line *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Status Line"+"</b>",ExtentColor.WHITE));
		String statusLine=response.getStatusLine(); //Getting Status Line
		logger.info("Status Line===> "+statusLine);
		test.log(Status.INFO,MarkupHelper.createLabel("Status line is: "+statusLine,ExtentColor.WHITE));
		Assert.assertEquals(statusLine,"HTTP/1.1 201 Created");
		test.log(Status.INFO,MarkupHelper.createLabel("Status line is validated successfully",ExtentColor.WHITE));
	
		logger.info("************* Checking Content Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Content Type"+"</b>",ExtentColor.WHITE));
		String contentType=response.header("Content-Type"); //Getting Content Type Header
		logger.info("Content Type is ===> "+contentType);
		test.log(Status.INFO,MarkupHelper.createLabel("Content type header is: "+contentType,ExtentColor.WHITE));
		Assert.assertEquals(contentType,"application/json; charset=utf-8");
		test.log(Status.INFO,MarkupHelper.createLabel("Content type header is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Server Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Server Type"+"</b>",ExtentColor.WHITE));
		String serverType=response.header("Server"); //Getting Content Type Header
		logger.info("Server Type is ===> "+serverType);
		test.log(Status.INFO,MarkupHelper.createLabel("Server type: "+serverType,ExtentColor.WHITE));
		Assert.assertEquals(serverType,"cloudflare");
		test.log(Status.INFO,MarkupHelper.createLabel("Server type header is validated",ExtentColor.WHITE));

		logger.info("************* Checking Connection type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Connection type"+"</b>",ExtentColor.WHITE));
		String connection=response.header("Connection"); //Getting Content Type Header
		logger.info("Connection type is ===> "+connection);
		test.log(Status.INFO,MarkupHelper.createLabel("Connection type: "+connection,ExtentColor.WHITE));
		Assert.assertEquals(connection,"keep-alive");
		test.log(Status.INFO,MarkupHelper.createLabel("Connection type is validated",ExtentColor.WHITE));
	}
	@AfterClass
	public void teardown() {
		logger.info("************* Finished TC005_PUT_UpdateData*************");
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
