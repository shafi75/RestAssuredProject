package base;
import java.time.Duration;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.util.concurrent.RateLimiter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
public class TestBase {
	public static RequestSpecification httpRequest;
	public static Response response;
	public RateLimiter throttle = RateLimiter.create(1.0);
	//Hard coded -Input for GET details of Single Employee & update employee
	public String empId="2";
	public static Logger logger;
	public static ExtentHtmlReporter htmlreporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	@BeforeSuite
	public void setUp() {
		logger=Logger.getLogger("EmployeeRestAPI");//added Logger
		PropertyConfigurator.configure("log4j.properties");//added logger
		logger.setLevel(Level.DEBUG);
		htmlreporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"\\src\\test\\resources\\Reports\\apireport.html");
		htmlreporter.config().setEncoding("urf-8");
		htmlreporter.config().setDocumentTitle("API Automation Report");
		htmlreporter.config().setReportName("Automation Tests Results");
		htmlreporter.config().setTheme(Theme.STANDARD);
		extent=new ExtentReports();
		extent.attachReporter(htmlreporter);
		extent.setSystemInfo("Automation Tester","shafaat");
		extent.setSystemInfo("Organization","HCL Technologies");
		extent.setSystemInfo("Build","BHUI");
	}
	@AfterTest
	public void endReport() {
		extent.flush();
	}
		
}
