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
public class TC002_Get_SingleEmployeeData extends TestBase{
	@BeforeClass
	public void getSingleEmployee() throws InterruptedException {
		logger.info("************* TC002_Get_SingleEmployeeData *************");
		RestAssured.baseURI="https://reqres.in/api";
		httpRequest=RestAssured.given();
		response=httpRequest.request(Method.GET,"/users/"+empId);
		Thread.sleep(5000);		
	}
	@Test
	public void validateSingleEmployeeData() {
		test=extent.createTest("Validations on Get Single Employee Data");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Response Body"+"</b>",ExtentColor.WHITE));
		logger.info("************* Checking Response Body *************");
		String responseBody=response.getBody().asString();
		logger.info("Response body===> "+responseBody);
		test.log(Status.INFO,MarkupHelper.createLabel("Response body is "+responseBody,ExtentColor.WHITE));
		Assert.assertTrue(responseBody.contains(empId));
		test.log(Status.INFO,MarkupHelper.createLabel("Response body is validated",ExtentColor.WHITE));
		
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Status Code"+"</b>",ExtentColor.WHITE));
		logger.info("************* Checking Status Code *************");
		int statusCode=response.getStatusCode();
		logger.info("Status code===> "+statusCode);
		test.log(Status.INFO,MarkupHelper.createLabel("Status code is "+statusCode,ExtentColor.WHITE));
		Assert.assertTrue(statusCode==200);
		test.log(Status.INFO,MarkupHelper.createLabel("Status code is validated",ExtentColor.WHITE));
		
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Response time"+"</b>",ExtentColor.WHITE));
		logger.info("************* Checking Response Time *************");
		long responseTime=response.getTime(); //Getting Response time
		logger.info("Response time===> "+responseTime);
		test.log(Status.INFO,MarkupHelper.createLabel("Response time is "+responseTime,ExtentColor.WHITE));
		if(responseTime>2000) {
			logger.warn("Response time is greater then 2000");
		}
		Assert.assertTrue(responseTime<2000);
		test.log(Status.INFO,MarkupHelper.createLabel("Response time is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Status Line *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Status Line"+"</b>",ExtentColor.WHITE));
		String statusLine=response.getStatusLine(); //Getting Status Line
		logger.info("Status Line===> "+statusLine);
		test.log(Status.INFO,MarkupHelper.createLabel("Status Line is"+statusLine,ExtentColor.WHITE));
		Assert.assertEquals(statusLine,"HTTP/1.1 200 OK");
		test.log(Status.INFO,MarkupHelper.createLabel("Status Line is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Content Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Content Type"+"</b>",ExtentColor.WHITE));
		String contentType=response.header("Content-Type"); //Getting Content Type Header
		logger.info("Content Type is ===> "+contentType);
		test.log(Status.INFO,MarkupHelper.createLabel("Content type is"+contentType,ExtentColor.WHITE));
		Assert.assertEquals(contentType,"application/json; charset=utf-8");
		test.log(Status.INFO,MarkupHelper.createLabel("Content type header is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Connection Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Connection Type"+"</b>",ExtentColor.WHITE));
		String connectionType=response.header("Connection"); //Getting Content Type Header
		logger.info("Connection Type is ===> "+connectionType);
		test.log(Status.INFO,MarkupHelper.createLabel("Conection type is"+connectionType,ExtentColor.WHITE));
		Assert.assertEquals(connectionType,"keep-alive");
		test.log(Status.INFO,MarkupHelper.createLabel("Conection type header is validated",ExtentColor.WHITE));
	
		logger.info("************* Checking Server Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Server Type"+"</b>",ExtentColor.WHITE));
		String serverType=response.header("Server"); //Getting Content Type Header
		logger.info("Server Type is ===> "+serverType);
		Assert.assertEquals(serverType,"cloudflare");
		test.log(Status.INFO,MarkupHelper.createLabel("Server type header is validated",ExtentColor.WHITE));
	}
	@AfterClass
	public void teardown() {
		logger.info("************* Finished TC002_Get_SingleEmployeeData*************");
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
