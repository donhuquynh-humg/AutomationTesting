package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

public class LoginScreen {
	// List of fields in Login screen
	// Text field
	public static String usernameTxtName		= "email";
	public static String passwordTxtName		= "password";
	// Button
	public static String loginBtnXpath				= "//button";
	
	// Error message
	public static String errorMsgXpath 				= "//p[contains(text(),'MSG')]";
	
	// List of messages
	public static String wrongAccountMsg			= "Tài khoản hoặc mật khẩu không đúng. Mời bạn đăng nhập lại";
	public static String enterUsernameMsg			= "Tài khoản không đươc để trống";
	public static String enterPasswordMsg			= "Mật khẩu không được để trống";
	
	public static WebDriver openScreen(String browser) {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			Utilities.waitForElementClickable(driver, By.xpath(LoginScreen.loginBtnXpath), Constant.WAIT_CLICKABLE);
		}
		return driver;
	}
	
	public static void loginAndWait(WebDriver driver, String userName, String passWord, By newLocator) {
		Utilities.inputValueAndValidate(driver, By.name(LoginScreen.usernameTxtName), userName, userName);
		Utilities.inputValueAndValidate(driver, By.name(LoginScreen.passwordTxtName), passWord, passWord);
		// Click login button
		Utilities.clickObscuredElement(driver, By.xpath(LoginScreen.loginBtnXpath), newLocator, Constant.WAIT_ELEMENT_EXIST*3);
	}
	
	// Login success
	public static void loginSuccess(WebDriver driver, String userName, String passWord) {
		loginAndWait(driver, userName, passWord, By.xpath(HomeScreen.avatarIconXpath));
	}
	
	// Login fail and check error message
	public static void loginFail(WebDriver driver, String userName, String passWord, String expectedErrorMess) {
		if (expectedErrorMess.equals(LoginScreen.wrongAccountMsg)) {
			loginAndWait(driver, userName, passWord, By.xpath(LoginScreen.loginBtnXpath));
			Utilities.assertAlertMessage(driver, expectedErrorMess, Constant.WAIT_ELEMENT_EXIST);
		}
		else {
			loginAndWait(driver, userName, passWord, By.xpath(errorMsgXpath.replace("MSG", expectedErrorMess)));
		}
	}
		
}
