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
import io.restassured.path.json.JsonPath;
public class TC006_Delete_UserRecord extends TestBase{
	@BeforeClass
	public void deleteUserData() throws InterruptedException {
		logger.info("************* TC006_Delete_UserRecord started *************");
		RestAssured.baseURI="https://reqres.in/api";
		httpRequest=RestAssured.given();
		response=httpRequest.request(Method.GET,"/users");
		
		//First get the JSON path Object from the GET Response
		JsonPath jsonPathEvaluator=response.jsonPath();
		
		//Capture id
		int empid=jsonPathEvaluator.get("data[0].id");
		System.out.println("The id that is deleted "+empid);
		response=httpRequest.request(Method.DELETE,"/users/"+empid);
		
		Thread.sleep(4000);
	}
	@Test
	public void validationsonDeleteRequest() {
		test=extent.createTest("Validations on Delete User Data");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Validate response body"+"</b>", ExtentColor.WHITE));
		String responseBody=response.getBody().asString();
		System.out.println("Response Body is"+responseBody);
		test.log(Status.INFO,MarkupHelper.createLabel("Response body is validated successfully", ExtentColor.WHITE));
	
		logger.info("************* Checking Status Code *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Status Code"+"</b>", ExtentColor.WHITE));
		int statusCode=response.getStatusCode();
		logger.info("Status code===> "+statusCode);
		test.log(Status.INFO,MarkupHelper.createLabel("Status code value is: "+statusCode, ExtentColor.WHITE));
		Assert.assertTrue(statusCode==204);
		test.log(Status.INFO,MarkupHelper.createLabel("Status code is validated successfully", ExtentColor.WHITE));
	
		logger.info("************* Checking Status Line *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Status Line"+"</b>", ExtentColor.WHITE));
		String statusLine=response.getStatusLine(); //Getting Status Line
		logger.info("Status Line===> "+statusLine);
		test.log(Status.INFO,MarkupHelper.createLabel("Status line is: "+statusLine, ExtentColor.WHITE));
		Assert.assertEquals(statusLine,"HTTP/1.1 204 No Content");
		test.log(Status.INFO,MarkupHelper.createLabel("Status line is validated ", ExtentColor.WHITE));
	
		logger.info("************* Checking Server Type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Server Type"+"</b>", ExtentColor.WHITE));
		String serverType=response.header("Server"); //Getting Content Type Header
		logger.info("Server Type is ===> "+serverType);
		test.log(Status.INFO,MarkupHelper.createLabel("Server type is: "+serverType, ExtentColor.WHITE));
		Assert.assertEquals(serverType,"cloudflare");
		test.log(Status.INFO,MarkupHelper.createLabel("Server type is validated ", ExtentColor.WHITE));
	
		logger.info("************* Checking Connection type *************");
		test.log(Status.INFO,MarkupHelper.createLabel("<b>"+"Checking Connection type"+"</b>", ExtentColor.WHITE));
		String connection=response.header("Connection"); //Getting Content Type Header
		logger.info("Content Encoding is ===> "+connection);
		test.log(Status.INFO,MarkupHelper.createLabel("Connection type is: "+connection, ExtentColor.WHITE));
		Assert.assertEquals(connection,"keep-alive");
		test.log(Status.INFO,MarkupHelper.createLabel("Connection type is validated ", ExtentColor.WHITE));
	}
	@AfterClass
	public void teardown() {
		logger.info("************* Finished TC006_Delete_UserRecord*************");
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
