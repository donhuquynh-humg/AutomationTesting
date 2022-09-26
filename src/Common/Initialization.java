package Common;

import java.lang.reflect.Method;

import org.apache.log4j.Level;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

public abstract class Initialization {
	protected String imagesPath;
	public WebDriver driver = null;
	Utilities utils;
	protected String browser;
	
	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() throws Exception{
		utils = new Utilities();
	}
		
	@BeforeClass(alwaysRun = true)
	@Parameters("browser")
	public void beforeClass(String browserTest) throws Exception{
		browser = browserTest;
	}
	
	@AfterMethod(alwaysRun = true) 
	public void afterMethod(ITestResult result, Method method){
		if (result.getStatus() == ITestResult.FAILURE || result.getStatus() == ITestResult.SKIP) {
			Utilities.printWithTestID(result.toString(), Level.ERROR);
			Utilities.captureScreen(driver, method.getName());
		}
	}
	
}
