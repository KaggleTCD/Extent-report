package feature;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class FreeCRMTest {
	
	public WebDriver webDriver;
	public ExtentReports extent;
	public ExtentTest logger;
	
	@BeforeTest
	public void setExtent() {
		
		extent = new ExtentReports(System.getProperty("C:\\Selenium\\extentreport")+"\\test-output\\ExtentReport.html",true);
		
	}
	
	
	@AfterTest
	public void endReport() {
		
		extent.flush();
		extent.close();
	}
	
	public static String getScreenShot(WebDriver driver,String screenshotName) throws IOException {
		
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("C:\\Selenium\\extentreport")+"\\FailedTestsScreenShot\\"+screenshotName+dateName+".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source,finalDestination);
		return destination;
	}
	@BeforeMethod
	public void setUp() {
		
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		webDriver=new ChromeDriver();
		webDriver.manage().window().maximize();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
		webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		webDriver.get("https://www.freecrm.com/");
	}
	
	@Test
	public void freeCrmTitleTest() {
		logger = extent.startTest("freecrmtest");
		String title = webDriver.getTitle();
	Assert.assertEquals(title, "#1 Free CRM customer relationship management software cloud");
	}
	
	@AfterMethod
	public void tearDown(ITestResult iTestResult) throws IOException {
		
		if(iTestResult.getStatus()==ITestResult.FAILURE){
			
			logger.log(LogStatus.FAIL, "Test Result is "+iTestResult.getName());
			logger.log(LogStatus.FAIL, "Test Result is "+iTestResult.getThrowable());
			
			String screenShot = FreeCRMTest.getScreenShot(webDriver, iTestResult.getName());
			logger.log(LogStatus.FAIL, logger.addScreenCapture(screenShot));
			logger.log(LogStatus.FAIL, logger.addScreencast(screenShot));
		}
		
		else if(iTestResult.getStatus()==ITestResult.SKIP) {
			
			logger.log(LogStatus.SKIP, "Test Result is "+iTestResult.getName());
			logger.log(LogStatus.SKIP, "Test Result is "+iTestResult.getThrowable());
		}
		else {
			
			logger.log(LogStatus.PASS, "Test Result is "+iTestResult.getName());
			logger.log(LogStatus.PASS, "Test Result is "+iTestResult.getThrowable());
		}
		extent.endTest(logger);
		webDriver.quit();
	}

}
