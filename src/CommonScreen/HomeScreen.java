package CommonScreen;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import Common.Constant;
import Common.Utilities;

public class HomeScreen {
	// Links
	public static String avatarIconXpath 			= "//img[@src='/assets/images/avatar.jpg']";
	public static String linkXpath 					= "//a[contains(text(),'LINK')]";
	public static final String PLAN 				= "Kế hoạch";
	public static final String ADMINISTRATION 		= "Lập kế hoạch thanh tra hành chính";
	public static String logoutLinkXpath 			= "//span[contains(text(),'Logout')]";
	
	public static WebDriver openScreen(String browser) {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			Utilities.waitForElementClickable(driver, By.xpath(LoginScreen.loginBtnXpath), Constant.WAIT_CLICKABLE);
			LoginScreen.loginAndWait(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD, By.xpath(HomeScreen.avatarIconXpath));
		}
		return driver;
	}
	
	public static void logout(WebDriver driver) {
		Utilities.clickObscuredElement(driver, HomeScreen.avatarIconXpath, HomeScreen.logoutLinkXpath, Constant.WAIT_ELEMENT_EXIST);
		Utilities.clickObscuredElement(driver, HomeScreen.logoutLinkXpath, LoginScreen.loginBtnXpath, Constant.WAIT_ELEMENT_EXIST);
	}
}
