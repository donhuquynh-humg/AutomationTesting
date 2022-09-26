package TestSuite;

import java.io.IOException;
import java.lang.reflect.Method;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.Constant;
import Common.Initialization;
import Common.Utilities;
import CommonScreen.HomeScreen;
import CommonScreen.LoginScreen;


public class Logout extends Initialization{	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception{	
		driver = HomeScreen.openScreen(browser);
		Utilities.testID = method.getName();
	}
		
	@Test()
	public void MODULE3_01() throws IOException{
		HomeScreen.logout(driver);
	}	
	
	@AfterMethod()  
	public void tearDownMethod(ITestResult result, Method method){
		afterMethod(result, method);
		Utilities.closeDriver(driver);
	}
}
