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
import CommonScreen.LoginScreen;


public class Login extends Initialization{	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception{	
		driver = LoginScreen.openScreen(browser);
		Utilities.testID = method.getName();
	}
		
	@Test()
	public void MODULE1_01() throws IOException{
		LoginScreen.loginSuccess(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD);
	}	

	@Test()
	public void MODULE1_02() throws IOException{
		LoginScreen.loginFail(driver, "", Constant.BASE_PASSWORD, LoginScreen.enterUsernameMsg);
	}	
	
	@Test()
	public void MODULE1_03() throws IOException{
		LoginScreen.loginFail(driver, Constant.BASE_EMAIL, "", LoginScreen.enterPasswordMsg);
	}	
	
	@Test()
	public void MODULE1_04() throws IOException{
		LoginScreen.loginFail(driver, "demo_test", Constant.BASE_PASSWORD, LoginScreen.wrongAccountMsg);
	}	

	@Test()
	public void MODULE1_05() throws IOException{
		LoginScreen.loginFail(driver, Constant.BASE_EMAIL, "1234567", LoginScreen.wrongAccountMsg);
	}	
	
	@Test()
	public void MODULE1_06() throws IOException{
		LoginScreen.loginFail(driver, "demo_test", "1234567", LoginScreen.wrongAccountMsg);
	}	
	
	@AfterMethod()  
	public void tearDownMethod(ITestResult result, Method method){
		afterMethod(result, method);
		Utilities.closeDriver(driver);
	}
}
