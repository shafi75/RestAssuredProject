package testcases;
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

import base.TestBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
public class TC001_Get_All_Employees extends TestBase {
	@BeforeClass
	public void getAllEmployees() throws InterruptedException {
		logger.info("************* Started TC001_Get_All_Employees *************");
		RestAssured.baseURI="https://reqres.in/api";
		httpRequest=RestAssured.given();
		response=httpRequest.request(Method.GET,"/users?page=2");
		Thread.sleep(5000);		
	}
	
	@Test
	public void checkValidationOnBody() {
		test=extent.createTest("Check Validation on Get All Employee Test");
		test.log(Status.INFO,MarkupHelper.createLabel("********* Checking Response Body *********",ExtentColor.WHITE));
		logger.info("************* Checking Response Body *************");
		String responseBody=response.getBody().asString();
		logger.info("Response body===> "+responseBody);
		Assert.assertTrue(responseBody!=null);
		test.log(Status.INFO,MarkupHelper.createLabel("Response body is not null",ExtentColor.WHITE));
		test.log(Status.INFO,MarkupHelper.createLabel("Response body is validated ",ExtentColor.WHITE));
		
		logger.info("************* Checking Status Code *************");
		test.log(Status.INFO,MarkupHelper.createLabel("****** Checking Status Code *******",ExtentColor.WHITE));
		int statusCode=response.getStatusCode();
		test.log(Status.INFO,MarkupHelper.createLabel("Status code is :"+statusCode,ExtentColor.WHITE));
		logger.info("Status code===> "+statusCode);
		Assert.assertTrue(statusCode==200);
		test.log(Status.INFO,MarkupHelper.createLabel("Validate successfully that status code is 200",ExtentColor.WHITE));
		test.log(Status.INFO,MarkupHelper.createLabel("****** Status code is validated successfully *******",ExtentColor.WHITE));
		
		logger.info("************* Checking Response Time *************");
		test.log(Status.INFO,MarkupHelper.createLabel("******* Checking Response Time *******",ExtentColor.WHITE));
		long responseTime=response.getTime(); //Getting Response time
		test.log(Status.INFO,MarkupHelper.createLabel("Response time is :"+responseTime,ExtentColor.WHITE));
		logger.info("Response time===> "+responseTime);
		if(responseTime>2000) {
			logger.warn("Response time is greater then 2000");
		}
		test.log(Status.INFO,MarkupHelper.createLabel("Check if response time is greater than 2000:"+responseTime,ExtentColor.WHITE));
		Assert.assertTrue(responseTime<4000);
		
		logger.info("************* Checking Status Line *************");
		test.log(Status.INFO,MarkupHelper.createLabel("**** Checking Status Line ****"+responseTime,ExtentColor.WHITE));
		String statusLine=response.getStatusLine(); //Getting Status Line
		logger.info("Status Line===> "+statusLine);
		test.log(Status.INFO,MarkupHelper.createLabel("Status Line "+statusLine,ExtentColor.WHITE));
		Assert.assertEquals(statusLine,"HTTP/1.1 200 OK");
		test.log(Status.INFO,MarkupHelper.createLabel("**** Status line is validated ****"+responseTime,ExtentColor.WHITE));
		
		logger.info("************* Checking Content Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("**** Checking Content Type ****"+responseTime,ExtentColor.WHITE));
		String contentType=response.header("Content-Type"); //Getting Content Type Header
		test.log(Status.INFO,MarkupHelper.createLabel("Checking header content type "+contentType,ExtentColor.WHITE));
		logger.info("Content Type is ===> "+contentType);
		test.log(Status.INFO,MarkupHelper.createLabel("Content type "+contentType,ExtentColor.WHITE));
		Assert.assertEquals(contentType,"application/json; charset=utf-8");
		test.log(Status.INFO,MarkupHelper.createLabel("Validation on content type is successful",ExtentColor.WHITE));
		
		logger.info("************* Checking Content Encoding *************");
		test.log(Status.INFO,MarkupHelper.createLabel("****** Checking Content Encoding ******"+contentType,ExtentColor.WHITE));
		String contentEncoding=response.header("content-encoding"); //Getting Content Type Header
		logger.info("Server Type is ===> "+contentEncoding);
		test.log(Status.INFO,MarkupHelper.createLabel("Content encoding value: "+contentEncoding,ExtentColor.WHITE));
		Assert.assertEquals(contentEncoding,"gzip");
		test.log(Status.INFO,MarkupHelper.createLabel("Validation on content encoding is successful"+contentType,ExtentColor.WHITE));
	}
	@AfterClass
	public void teardown() {
		logger.info("************* Finished TC001_Get_All_Employees*************");
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
