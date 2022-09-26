package Common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Utilities {
	
	public static WebDriver userADriver;
	// Test output folder
	public static String resultFolder = "./test-output";
	// List of driver
	public static String IEDriverPath = "./libs/IEDriverServer.exe";
	public static String EdgeDriverPath = "./libs/MicrosoftWebDriver.exe";
	public static String ChromeDriverPath = "./libs/chromedriver.exe";
	public static String MSEdgeDriverPath = "./libs/msedgedriver.exe";
	// Log function
	public static final Logger logger = Logger.getLogger(Utilities.class);
	public static final String LOGGER_CONF_PATH= "./src/log4j.properties";
	
	public static String testID="NotSet";
	
	public Utilities() {
		
		try {
			// Initialize log
			Properties prop = new Properties();
			prop.load(new FileInputStream(LOGGER_CONF_PATH)); // FileInputStream 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh-mm");
			resultFolder = "./result/" + sdf.format(Calendar.getInstance().getTime()) + "/";
			prop.setProperty("log4j.appender.file.File", resultFolder +"ResultLog.log");
			prop.store(new FileOutputStream(LOGGER_CONF_PATH), null);
		} catch (IOException e) {
			e.printStackTrace();
		} // FileOutputStream
		PropertyConfigurator.configure(LOGGER_CONF_PATH);
	}
	
	// Write information to log file
	public static void printWithTestID(String msg, Level level, Throwable...e ){
		switch (level.toInt()) {
			case Level.DEBUG_INT:
				logger.debug("[TestID:" + testID + "]" + msg);
				break;
			case Level.INFO_INT:
				logger.info("[TestID:" + testID + "]" + msg);
				break;
			case Level.ERROR_INT:
				if(0==e.length) {
					logger.error("[TestID:" + testID + "]" + msg);
				}else {
					logger.error("[TestID:" + testID + "]" + msg, e[0]);
				}
				break;
			default:
				Assert.fail("Wrong log level");
				break;
		}
	}
	
	
	/**
	 * Assert fail test case
	 * @param driver: Web driver
	 * @param message: Error message
	 */
	public static void assertFail(WebDriver driver, String message) {
		Assert.fail(message);
		printWithTestID(message, Level.ERROR);
		LocalDateTime now = LocalDateTime.now(); 
		captureScreen(driver, testID + now.toString());
	}
	
	/**
	 * Open one web URL
	 * @param driver: web driver
	 * @param link: URL of web page need to open
	 */
	public void navigateTo(WebDriver driver, String link) {
		try {
			driver.get(link);
		}catch(Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	/**
	 * Close browser
	 * @param driver: Web driver
	 */
	public static void closeDriver(WebDriver driver) {
		try {
			driver.quit();
		} catch (Exception e) {
			printWithTestID( e.getMessage(), Level.ERROR);
		}
	}
	
	// Sleep time
	public static void wait(double waitSecond) {
		try {
			Thread.sleep((long)(waitSecond*1000));
		} catch (InterruptedException e) {
			printWithTestID(e.toString(), Level.ERROR);
		}
	}
	
	/**
	 * Wait for element invisible during timeout
	 * @param driver: Web driver
	 * @param locator: xpath or id, class,..of GUI element which need to invisible
	 * @param timeInSecond: time out (second)
	 * @return
	 */
	public static boolean waitForElementInvisibility(WebDriver driver, By locator, int timeInSecond) {
		boolean isSuccess = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeInSecond);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			isSuccess = true;
		}catch(Exception e) {
			printWithTestID(e.toString(), Level.ERROR);
		}
		return isSuccess;
	}
	
	/**
	 * Wait for element visible during timeout
	 * @param driver: Web driver
	 * @param locator: xpath or id, class,..of GUI element which need to visible
	 * @param timeInSecond: time out (second)
	 * @return
	 */
	public static boolean waitForElementVisibility(WebDriver driver, By locator, int timeInSecond) {
		boolean isSuccess = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeInSecond);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			isSuccess = true;
		}catch(Exception e) {
			printWithTestID(e.toString(), Level.ERROR);
		}
		return isSuccess;
	}
	
	
	/**
	 * Wait for element visible during timeout. In this case, timeout =  Constant.WAIT_ELEMENT_EXIST
	 * @param driver: Web driver
	 * @param locator: xpath or id, class,..of GUI element which need to visible
	 * @param timeInSecond: time out (second)
	 * @return
	 */
	public static boolean waitForElementVisibility(WebDriver driver, By locator) {
		boolean isSuccess = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Constant.WAIT_ELEMENT_EXIST);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			isSuccess = true;
		}catch(Exception e) {
			printWithTestID(e.toString(), Level.ERROR);
		}
		return isSuccess;
	}
	
	/**
	 * Wait for element can clickable
	 * @param driver: Web driver
	 * @param locator: xpath or id, class,..of GUI element which need to visible
	 * @param timeInSecond: time out (second)
	 * @return
	 */
	public static boolean waitForElementClickable(WebDriver driver, By locator, int timeInSecond) {
		boolean isSuccess = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, timeInSecond);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		}catch(Exception e) {
			printWithTestID(e.toString(), Level.ERROR);
		}
		return isSuccess;
	}
	
	   /**
     * Click one element and wait one element visible one time
     * @param driver: Web driver
     * @param elementXPath: xpath of GUI element which need to visible
     * @param expectedElement: Xpath of Expected element will be displayed after click above element
     * @param waitSecond: wait time
     */
    public static void clickObscuredElement(WebDriver driver, By locator, By newLocator, double waitSecond) {
    	long startTime = System.currentTimeMillis();
        long timeout = System.currentTimeMillis() - startTime;
        int waitClick = (int)(Constant.WAIT_INTERVAL*4);
        waitForElementClickable(driver, locator, waitClick/2);
        while (timeout < waitClick * 1000) {
            try {
            	 driver.findElement(locator).click();
                 break;
            } catch(Exception e) {
            	printWithTestID(e.toString(), Level.ERROR);
            }
            timeout = System.currentTimeMillis() - startTime;
        }
        int waitExist = (int)(waitSecond*1000 - (System.currentTimeMillis() - startTime))/1000;
        new WebDriverWait(((WebDriver)driver), waitExist).until(ExpectedConditions.visibilityOfElementLocated(newLocator));
        assertElementVisible(driver, newLocator);
    }
	
	
    /**
     * Click one element and wait one element visible, if this element is not visible, click again
     * @param driver: Web driver
     * @param locator: xpath or id, class,..of GUI element which need to visible
     * @param newLocator: Expected element will be displayed after click above element
     * @param waitSecond: wait time
     */
    public static void clickObscuredElementManyTimes(WebDriver driver, By locator, By newLocator, double waitSecond) {
    	 long startTime = System.currentTimeMillis();
         long timeout = System.currentTimeMillis() - startTime;
         waitForElementClickable(driver, locator, 1);
         while (timeout < waitSecond * 1000) {
             try {
             	 driver.findElement(locator).click();
                 new WebDriverWait(((WebDriver)driver), 3).until(ExpectedConditions.visibilityOfElementLocated(newLocator));
                 break;
             } catch(Exception e) {
             }
             timeout = System.currentTimeMillis() - startTime;
         }
         assertElementVisible(driver, newLocator);
    }
    
    
    /**
     * Click one element and wait one element visible, if this element is not visible, click again
     * @param driver: Web driver
     * @param elementXPath: xpath of GUI element which need to visible
     * @param expectedElement: Xpath of Expected element will be displayed after click above element
     * @param waitSecond: wait time
     */
    public static void clickObscuredElement(WebDriver driver, String elementXPath, String expectedElement, int timeInSecond) {
    	clickObscuredElement(driver, By.xpath(elementXPath), By.xpath(expectedElement), timeInSecond);
    }
    
    
    /**
     * Get enable status of one web element
     * @param driver: Web driver
     * @param element: Web element need to check status
     * @return
     */
    public static boolean getElementEnableStatus(WebDriver driver, WebElement element) {
    	String className = element.getAttribute("class");
		boolean isEnableStatus = element.isEnabled();
		boolean isEnableByClass = className.contains("disable");
		// Element is enabled if class contains "disable" or attribute contains "disable"
		return (isEnableStatus || isEnableByClass);
    }
    
    /**
     * Click one element and wait one expected element disappear
     * @param driver: Web driver
     * @param elementLocator: xpath or id, class,..of GUI element which need to click
     * @param expectedElementLocator: xpath or id, class,..of one expected element which need to disappear
     * @param timeInSecond: wait time
     * @return
     */
    public static void clickObscuredElementToNotVisible(WebDriver driver, By locator, By expectedElementLocator, int timeInSecond) {
    	long startTime = System.currentTimeMillis();
        long timeout = System.currentTimeMillis() - startTime;
        waitForElementClickable(driver, locator, 1);
        while (timeout < timeInSecond * 1000) {
            try {
            	driver.findElement(locator).click();
                new WebDriverWait(((WebDriver)driver), 3).until(ExpectedConditions.invisibilityOfElementLocated(expectedElementLocator));
                break;
            } catch(Exception e) {
            	printWithTestID(e.getMessage(), Level.ERROR);
            }
            timeout = System.currentTimeMillis() - startTime;
        }
        assertElementNotVisible(driver, expectedElementLocator);
    }
    
    /**
     * Click one element and wait one expected element disappear
     * @param driver: Web driver
     * @param elementXPath: xpath of GUI element which need to click
     * @param expectedElement: xpath of one expected element which need to disappear
     * @param timeInSecond: wait time
     * @return
     */
    public static void clickObscuredElementToNotVisible(WebDriver driver, String elementXPath, String expectedElement, int timeInSecond) {
    	clickObscuredElementToNotVisible(driver, By.xpath(elementXPath), By.xpath(expectedElement), timeInSecond);
    }
    
    
	/**
	 * Input data into edit field
	 * @param driver: Web driver
	 * @param locator: xpath or id, class,..of GUI element which need to input data
	 * @param inputData: data need to input
	 */
	public static void sendKeys(WebDriver driver, By locator, String inputData) {
		waitForElementVisibility(driver, locator);
		try {
			WebElement element = driver.findElement(locator);
			element.clear();
			if (!inputData.isEmpty()) {
				element.sendKeys(inputData);
			}
		}catch(Exception e) {
			Assert.fail("Could not input data: " + e.getMessage());
		}
	}
	
	/**
	 * Input data into edit field
	 * @param driver: Web driver
	 * @param xpath: xpath of GUI element which need to input data
	 * @param inputData: data need to input
	 */
	public static void sendKeys(WebDriver driver, String xpath, String inputData) {
		sendKeys(driver, By.xpath(xpath), inputData);
	}
	
	
	/**
	 * Clear input data
	 * @param driver: Web driver
	 * @param locator: Xpath or id, class,..of input field
	 */
	public static void clearInput(WebDriver driver, By locator) {
		try {
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.CONTROL + "a");
			element.sendKeys(Keys.DELETE);
//			element.clear();
		}catch(Exception e) {
			Assert.fail("Could not clear data");
		}
	}
	
	/**
	 * Input data into field and validate data after input
	 * @param driver: Web driver
	 * @param locator: Xpath or id, class,..of input field
	 * @param inputData: data need to input to field
	 * @param expectedValue: Expected value
	 */
	public static void inputValueAndValidate(WebDriver driver, By locator, String inputData, String expectedValue) {
		if (inputData != null) {
			sendKeys(driver, locator, inputData);
			assertInputValue(driver, locator, expectedValue);		
		}
	}
	
	// Input value into field then press Enter and validate
	public static void inputValueAndValidateDate(WebDriver driver, By locator, String inputData, String expectedValue) {
		if (inputData != null) {
			clearInput(driver, locator);
			sendKeys(driver, locator, inputData);
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.ENTER);
			assertInputValue(driver, locator, expectedValue);		
		}
	}
	
	// Input value into field and validate upper case text
	public static void inputValueAndValidateUppercase(WebDriver driver, By locator, String inputData, String expectedValue) {
		sendKeys(driver, locator, inputData);
		assertInputValue(driver, locator, expectedValue);	
		Utilities.assertAttributeValue(driver, locator, "style", "text-transform: uppercase;");
	}
	
	// Input password to field and validate
	public static void inputPasswordAndValidate(WebDriver driver, By locator, String inputData, String expectedValue) {
		WebElement element = driver.findElement(locator);
		inputValueAndValidate(driver, locator, inputData, expectedValue);	
		Assert.assertEquals(element.getAttribute("type"), "password", "This field is not password type");
	}
	
	// Click element
	public static void click(WebDriver driver, By locator) {
		try {
			WebElement clickedElement = driver.findElement(locator);
			clickedElement.click();
		}
		catch(Exception e) {
			Assert.fail("Could not click element: " + locator + ": " + e.getMessage());
		}
		
	}
	
	/**
	 * Select listbox and validate
	 * @param driver:Web driver
	 * @param locatorListbox: Xpath or ID, class, ...of listbox item
	 * @param selectedItem: The item will be selected in listbox
	 */
	public static void selectListBox(WebDriver driver, By locatorListbox, String selectedItem) {
		// Find list box
		waitForElementVisibility(driver, locatorListbox);
		WebElement listbox = driver.findElement(locatorListbox);
		// Select item
		Select select = new Select(listbox);
		select.selectByVisibleText(selectedItem);
		wait(Constant.WAIT_INTERVAL*2);
		assertSelectedItemInListbox(driver, locatorListbox, selectedItem);
	}
	
	/**
	 * Validate selected value of list box
	 * @param driver: Web driver
	 * @param locatorListbox: Xpath or ID, class, ...of listbox item
	 * @param selectedItem: The item will be selected in listbox
	 */
	public static void assertSelectedItemInListbox(WebDriver driver, By locatorListbox, String selectedItem) {
		String actualSelectItem = "";
		// Find listbox
		WebElement listbox = driver.findElement(locatorListbox);
		// Get value of selected item
		String value = listbox.getAttribute("value");
		// Select item
		Select select = new Select(listbox);
		List<WebElement> options = select.getOptions();
		for (int i = 0; i<options.size(); i++) {
			if (options.get(i).getAttribute("value").equals(value)) {
				actualSelectItem = options.get(i).getText();
				break;
			}
		}
		Assert.assertEquals(actualSelectItem, selectedItem, "Selected item in listbox is not correct. Expect is " + selectedItem + " but actual is " + actualSelectItem);
	}
	
	/**
	 * Validate items in list box and validate selected item
	 * @param driver: Web driver
	 * @param locatorListbox: Xpath or ID, class, ...of listbox item
	 * @param valueList: List of items in listbox
	 * @param selectedItem: The item will be selected in listbox
	 */
	public static void assertItemInListbox(WebDriver driver, By locatorListbox, String[] valueList, String selectedItem) {
		String actualSelectItem = "";
		// Find listbox
		WebElement listbox = driver.findElement(locatorListbox);
		// Get value of selected item
		String value = listbox.getAttribute("value");
		// Select item
		Select select = new Select(listbox);
		// Validate all value of list box
		List<WebElement> options = select.getOptions();
		Assert.assertEquals(options.size(), valueList.length);
		for (int i = 0; i<options.size(); i++) {
			Assert.assertEquals(options.get(i).getText(), valueList[i]);
		}
		for (int i = 0; i<options.size(); i++) {
			if (options.get(i).getAttribute("value").equals(value)) {
				actualSelectItem = options.get(i).getText();
				break;
			}
		}
		Assert.assertEquals(actualSelectItem, selectedItem, "Selected item in listbox is not correct. Expect is " + selectedItem + " but actual is " + actualSelectItem);
	}
	
	
	/**
	 * Validate one element exists
	 * @param driver
	 * @param locator: Xpath or id, or class,...of an element
	 * @param searchTimeOut: Timeout
	 */
	public static void assertElementExists(WebDriver driver, By locator, int searchTimeOut) {
		String msg = "";
		try{
			WebDriverWait wait = new WebDriverWait(driver, searchTimeOut);
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			
			msg = "Element with locator is  " + locator + " exists";
			Assert.assertTrue(true, msg);
		}catch(Exception e) {
			msg = "Element with locator " + locator + " doesn't exist";  
			Assert.fail(msg);
		}
		
	}
	
	/**
	 * Validate one element doesn't exist
	 * @param driver
	 * @param locator: Xpath or id, or class,...of an element
	 * @param searchTimeOut: Timeout
	 */
	public static void assertElementNotExists(WebDriver driver, By locator, int searchTimeOut) {
		String msg = "";
		
		try{
			WebDriverWait wait = new WebDriverWait(driver, searchTimeOut);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
			msg = "Element with locator " + locator + " doesn't exists";
			Assert.assertTrue(true, msg);
		}catch(Exception e) {
			msg = "Element with locator " + locator + " still exist";
			Assert.fail(msg);
		}
		
	}
	
	/**
	 * Validate one element visible
	 * @param object: Web driver or web element which contains tested element
	 * @param locator: Xpath or id, or class,...of an element
	 */
	public static void assertElementNotVisible(Object object, By locator) {
		WebElement element = null;
		try{
			if(object instanceof WebDriver) {
				element = ((WebDriver)object).findElement(locator);
			} else if (object instanceof WebElement) {
				element = ((WebElement)object).findElement(locator);
			}	
			
		}catch(Exception e) {
			
		}
		if (element != null) {
			assertElementNotVisible(element);
		}
	}
	
	/**
	 * Validate one element visible
	 * @param object: Web driver or web element which contains tested element
	 * @param locator: Xpath or id, or class,...of an element
	 */
	public static void assertElementVisible(Object object, By locator) {
		try{
			WebElement element = null;
			if(object instanceof WebDriver) {
				element = ((WebDriver)object).findElement(locator);
			} else if (object instanceof WebElement) {
				element = ((WebElement)object).findElement(locator);
			}	
			assertElementVisible(element);
		}catch(Exception e) {
			Assert.fail("Element with locator " + locator + " doesn't exist");
		}	
	}
	
	/**
	 * Validate one element invisible
	 * @param element: Web element need to check invisible
	 */
	public static void assertElementNotVisible(WebElement element) {
		Assert.assertFalse(element.isDisplayed(), element.toString() + " is still displayed");	
	}
	
	/**
	 * Validate one element visible
	 * @param element: Web element need to check visible
	 */
	public static void assertElementVisible(WebElement element) {
		Assert.assertTrue(element.isDisplayed(), element.toString() + " is not displayed");	
	}
	
	/**
	 * Validate one element enable
	 * @param driver: Web driver
	 * @param id: Id of tested element
	 */
	public static void assertElementEnableByID(WebDriver driver, String id) {
		String msg = "";
		try {
			WebElement element = driver.findElement(By.id(id));
			Assert.assertEquals(element.isEnabled(), true);
		} catch (Exception e) {
			msg = "Element with id " + id + " doesn't enable"; 
			Assert.fail(msg);			
		}		
	}
	
	/**
	 * Validate one element is enabled
	 * @param driver: Web driver
	 * @param xpath: xpath of tested element
	 */
	public static void assertElementEnable(WebDriver driver, String xpath) {
		String msg = "";
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			Assert.assertEquals(element.isEnabled(), true);
		} catch (Exception e) {
			msg = "Element " + xpath + " doesn't enable";
			Assert.fail(msg);			
		}		
	}
	
	/**
	 * Validate one element is disabled
	 * @param driver: Web driver
	 * @param id: id of tested element
	 */
	public static void assertElementDisableByID(WebDriver driver, String id) {
		String msg = "";
		try {
			WebElement element = driver.findElement(By.id(id));
			if (!element.isEnabled()) {
				Assert.assertFalse(element.isEnabled(), "Element with id " + id + " is not enabled");
			}
			else {
				boolean isReadonly = false;
				String checkReadonly = "return document.getElementById('" + element.getAttribute("id") + "').readOnly.toString();";
				isReadonly = Boolean.parseBoolean(executeJavaScript(driver, checkReadonly));
				boolean actualEnable = (isReadonly == false) && (element.isEnabled() == true);
				Assert.assertFalse(actualEnable, "Element with id " + id + " is not enabled");
			}
		} catch (Exception e) {
			msg = "Element with id " + id + " doesn't exist";
			Assert.fail(msg);
		}
	}
	
	/**
	 * Validate that element is disabled
	 * @param driver: Web driver
	 * @param xpath: Xpath of checked element
	 */
	public static void assertElementDisable(WebDriver driver, String xpath) {
		String msg = "";
		try {
			WebElement element = driver.findElement(By.xpath(xpath));
			Assert.assertEquals(element.isEnabled(), false);
		} catch (Exception e) {
			msg = "Element " + xpath + " doesn't exist"; 
			Assert.fail(msg);
		}
	}
	/**
	 * Validate text valued of checked element is same with expected value
	 * @param driver: Web driver
	 * @param locator: Xpath or id, class, ...of checked element
	 * @param expectedValue: Expected text value of checked element
	 */
	public static void assertTextValue(Object object, By locator, String expectedValue) {
		String textValue = null;
		WebElement element = null;
		if (object instanceof WebDriver) {
			element = ((WebDriver)object).findElement(locator);
		} else if (object instanceof WebElement) {
			element = ((WebElement)object).findElement(locator);
		}
		textValue = element.getText();
		assertString(expectedValue, textValue);
	}
	
	/**
	 * Validate text valued of checked element is same with expected value and this element is visible
	 * @param driver: Web driver
	 * @param locator: Xpath or id, class, ...of checked element
	 * @param expectedValue: Expected text value of checked element
	 */
	public static void assertTextValueVisible(WebDriver driver, By locator, String expectedValue) {
		boolean isVisible = waitForElementVisibility(driver, locator, Constant.WAIT_ELEMENT_EXIST);
		Assert.assertEquals(isVisible, true, locator + "is not visible");
		assertTextValue(driver, locator, expectedValue);
	}
	
	/**
	 * Validate value of text field 
	 * @param object: Ancestor of checked element. It is web driver or web element
	 * @param locator: Xpath or id, class,..of text field
	 * @param expectedValue: Expected value of text field
	 */
	public static void assertInputValue(Object object, By locator, String expectedValue) {
		String textValue = null;
		WebElement element = null;
		if (object instanceof WebDriver) {
			element = ((WebDriver)object).findElement(locator);
		} else if (object instanceof WebElement) {
			element = ((WebElement)object).findElement(locator);
		}
		textValue = element.getAttribute("value");
		assertString(expectedValue, textValue);
	}
	/**
	 * Validate value of text field by using java script
	 * @param driver: web driver
	 * @param locator: Xpath or id, class,..of text field
	 * @param expectedValue: Expected value of text field
	 */
	public static void assertInputValueByJavaScript(WebDriver driver, By locator, String expectedValue) {
		String textValue = null;
		WebElement element = driver.findElement(locator);
		JavascriptExecutor javaScriptExecutor = (JavascriptExecutor)driver;
		textValue = (String) javaScriptExecutor.executeScript("return arguments[0].value", element);
		assertString(expectedValue, textValue);
		Assert.assertTrue(element.isDisplayed(), locator + " is not displayed");
	}
	
	/**
	 * Validate value of text field is same with expected value and validate this field is visible
	 * @param driver: Web driver
	 * @param locator: Xpath or id, class,..of text field
	 * @param expectedValue: Expected value of text field
	 */
	public static void assertInputValueVisible(WebDriver driver, By locator, String expectedValue) {
		String textValue = null;
		WebElement element = driver.findElement(locator);
		textValue = element.getAttribute("value");
		assertString(expectedValue, textValue);
		Assert.assertTrue(element.isDisplayed(), locator + " is not displayed");
	}
	
	/**
	 * Validate attribute value of an element is same with expected value
	 * @param driver: Web driver
	 * @param locator: Xpath or id, class,..of checked element
	 * @param attribute: attribute need to check value
	 * @param expectedValue: Expected value of checked attribute
	 */	
	public static void assertAttributeValue(Object object, By locator, String attribute, String expectedValue) {
		String attributeValue = null;
		WebElement element = null;
		if (object instanceof WebDriver) {
			element = ((WebDriver)object).findElement(locator);
		} else if (object instanceof WebElement) {
			element = ((WebElement)object).findElement(locator);
		}
		attributeValue = element.getAttribute(attribute);
		assertString(expectedValue, attributeValue);
	}

	/**
	 * Validate attribute value of an element is same with expected value
	 * @param driver: Web driver
	 * @param id: id of checked element
	 * @param attribute: attribute need to check value
	 * @param expectedValue: Expected value of checked attribute
	 */	
	public static void assertAttributeValueByID(Object object, String id, String attribute, String expectedValue) {
		String attributeValue = null;
		WebElement element = null;
		if (object instanceof WebDriver) {
			element = ((WebDriver)object).findElement(By.id(id));
		} else if (object instanceof WebElement) {
			element = ((WebElement)object).findElement(By.id(id));
		}
		attributeValue = element.getAttribute(attribute);
		assertString(expectedValue, attributeValue);
	}
	
	/**
	 * Validate expected value with actual value
	 * @param expectedValue: Expected value
	 * @param actualValue: actual value
	 */
	public static void assertString(WebDriver driver, String expectedValue, String actualValue) {
		String msg = "";
		if(expectedValue == actualValue && actualValue == null) {
			msg = "Value is correct: " + expectedValue;
			Assert.assertTrue(true, msg);
		}
		else if(expectedValue.equals(actualValue)) {	
			msg = "Value is correct: " + expectedValue;
			Assert.assertTrue(true, msg);
		}else {
			msg = "Value is not correct: Expected: \"" + expectedValue + "\"; Actual: \"" + actualValue + "\"";
			assertFail(driver, msg);
			
		}
	}
	
	public static void assertString(String expectedValue, String actualValue) {
		String msg = "";
		if(expectedValue == actualValue && actualValue == null) {
			msg = "Value is correct: " + expectedValue;
			Assert.assertTrue(true, msg);
		}
		else if(expectedValue.equals(actualValue)) {	
			msg = "Value is correct: " + expectedValue;
			Assert.assertTrue(true, msg);
		}else {
			msg = "Value is not correct: Expected: \"" + expectedValue + "\"; Actual: \"" + actualValue + "\"";
			Assert.fail(msg);
		}
	}
	
	public void assertValueExistInStringArray(WebDriver driver, String[] strArray, String expectedValue, String testcase) {
		String msg = "";
		for(int i = 0; i < strArray.length; i++) {
			if(strArray[i].equals(expectedValue)) {
				msg = "String "+ expectedValue +" exists in the expected array";
				Assert.assertTrue(true, msg);
				break;
			}else if(i == strArray.length - 1) {
				msg = "String "+ expectedValue +" doesn't exist in the expected array";
				Assert.fail(msg);
		  	}
		}
	}
	
	/**
	 * Capture screen for UI element
	 * @param object: UI element: web driver or web element
	 * @param testcase: test case name
	 */
	public static void captureScreen(Object object, String testcase){
		File scrFile = null;
		try {
			if (object instanceof WebDriver) {
				scrFile = ((TakesScreenshot)((WebDriver)object)).getScreenshotAs(OutputType.FILE);
			} else if (object instanceof WebElement) {
				scrFile = ((TakesScreenshot)((WebElement)object)).getScreenshotAs(OutputType.FILE);
			}	
			try {
				String filePath = resultFolder + testcase + ".jpg";
				Files.copy(scrFile.toPath(),new File(filePath).toPath(),  StandardCopyOption.REPLACE_EXISTING);
				// Files.copy(scrFile, new File(resultFolder + "/img/" + testcase + ".jpg"));
			} catch (IOException e) {
				//Assert.fail("Could not capture screen: " + e.getMessage());
			}
		} catch (Exception e) {
			printWithTestID(e.getMessage(), Level.ERROR, e);
		}
	}


	public static void checkSrcImage(WebDriver driver, By locator, Boolean isEmpty) {
		assertElementVisible(driver, locator);
//		WebElement element = driver.findElement(locator);
//		String src = element.getAttribute("src");
//		String actualImgName = src.substring(src.lastIndexOf("/") + 1, src.length());
	}
	
	public static boolean isElementExist(Object driver, String elementXPath, int waitSecond) {
		boolean flag = false;
		try {
			if(driver instanceof WebDriver) {
				(new WebDriverWait(((WebDriver)driver), waitSecond)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXPath)));
			}else if (driver instanceof RemoteWebDriver) {
				(new WebDriverWait(((RemoteWebDriver)driver), waitSecond)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXPath)));
			}
			flag = true;
		}catch(Exception e) {

		}
		return flag;
	}
	
	public static void clickUsingActions(WebDriver driver, WebElement elementToClick) {
		Actions act = new Actions(driver);
		act.moveToElement(elementToClick).click().build().perform();
	}
	
	public static void clickUsingXpath(WebDriver driver, String xPath) {
		WebElement elementToClick = driver.findElement(By.xpath(xPath));
		Actions act = new Actions(driver);
		act.moveToElement(elementToClick).click().build().perform();
	}
	
	// Get WebDriver based on browser type
	public static WebDriver getDriverOneTime(String browserType) {
		WebDriver driver = null;
//		killBrowser(browserType);
		switch (browserType) {
			case Constant.EDGE_BROWSER: {
				System.setProperty("webdriver.edge.driver", EdgeDriverPath);
				driver = new EdgeDriver();		
				break;
			}
			case Constant.CHROME_BROWSER: {
				System.setProperty("webdriver.chrome.driver", ChromeDriverPath);
				driver = new ChromeDriver();		
				break;
			}
			case Constant.FIREFOX_BROWSER: {
				//	
			}
			case Constant.MSEDGE_BROWSER:
				System.setProperty("webdriver.edge.driver", MSEdgeDriverPath);
				driver = new EdgeDriver();		
				break;
				
			case Constant.IE_BROWSER: {
				System.setProperty("webdriver.ie.driver", IEDriverPath);
				driver = new InternetExplorerDriver();	
				break;
			}
			default:			
		}
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		return driver;
	}
	
	// Initialize driver and try again if initialize fail 
	public static WebDriver getDriver(String browserType) {
		WebDriver driver;
		try {
			driver = getDriverOneTime(browserType);
		} catch (Exception e) {
			driver = getDriverOneTime(browserType);
		}
		return driver;
	}
	
	// Kill any process
	public static void killProcess(String process) {
		// Default browser is IE
		String cmd = "taskkill /F /IM "  + process  + " /T";
		try {
			Runtime.getRuntime().exec(cmd);
			wait(Constant.WAIT_INTERVAL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	// Kill browser
	public static void killBrowser(String browserType) {
		switch (browserType) {
			case Constant.EDGE_BROWSER: {
				killProcess("MicrosoftEdge.exe");
				killProcess("MicrosoftWebDriver.exe");
				break;
			}
			case Constant.MSEDGE_BROWSER: {
				killProcess("msedgedriver.exe");
				killProcess("msedge.exe");
				break;
			}
			case Constant.CHROME_BROWSER: {
				killProcess("chrome.exe");	
				killProcess("chromedriver.exe");
				break;
			}
			case Constant.FIREFOX_BROWSER: {
				killProcess("firefox.exe");
				break;
			}
			case Constant.IE_BROWSER: {
				killProcess("iexplore.exe");
				break;
			}
			default:			
		}
	}
		
	// Move to child window
	public static void moveToChildWindow(WebDriver driver) {
		// It will return the parent window name as a String
		String parent=driver.getWindowHandle();
		for(String winHandle : driver.getWindowHandles()){
			if (!winHandle.equals(parent))
			{
				driver.switchTo().window(winHandle);
				break;
			}
		}
	}
	
	// Move to specific window by title
	public static void moveToSpecificWindowByTitle(WebDriver driver, String title) {
		for(String winHandle : driver.getWindowHandles()){
			if (!driver.getTitle().equals(title)) {
				driver.switchTo().window(winHandle);
			} else {
				break;
			}		
		}
	}
	
	// Move to specific window by index
	public static void moveToSpecificWindowByIndex(WebDriver driver, int index) {
		ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(index));
		refreshScreen(driver);
	}
	
	// Open new tab
	public static void openNewTab(WebDriver webDriver, String url, int position) {
		((JavascriptExecutor) webDriver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<>(webDriver.getWindowHandles());
        webDriver.switchTo().window(tabs.get(position));
        webDriver.get(url);
	}
	
	// Execute javascript
	public static String executeJavaScript(WebDriver driver, String javaScript) {
		Utilities.wait(Constant.WAIT_INTERVAL*2);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String value = null;
        try {
        	value = (String) js.executeScript(javaScript);
        } catch (Exception e) {
			Assert.fail("Could not execute java script: " + javaScript);
		}
        return value;
	}
		
	public static String getHostAddress() throws Exception {
//		InetAddress inetAddress = InetAddress.getLocalHost();
//		return inetAddress.getHostAddress();
		try(final DatagramSocket socket = new DatagramSocket()){
			socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
			return socket.getLocalAddress().getHostAddress();
		}
	}
	
	/**
	 * Verify text value in one date format
	 * @param format: Date format
	 * @param textValue: The text value need to verify
	 */
	public static void verifyDateFormat(String format, String textValue) {
		textValue = textValue.trim();
		DateFormat formatter = new SimpleDateFormat(format);
		formatter.setLenient(false);
		try {
		    formatter.parse(textValue);
		} catch (ParseException e) {
		    Assert.fail(textValue + " has incorrect date format: " + format);
		}
	}
	
	// Refresh screen by press F5 key
	public static void refreshScreen(WebDriver driver) {
//		driver.findElement(By.xpath("//body")).sendKeys(Keys.F5);
		driver.get(driver.getCurrentUrl());
//		Utilities.wait(Constant.WAIT_REFRESH_SCREEN);
	}
	
	public static String getCurrentDate(String format) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		LocalDateTime now = LocalDateTime.now();  
		return dtf.format(now);
	}
	
	public static String setTimeWithCurrentDate(String time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(time.split(":")[0]));
		calendar.set(Calendar.MINUTE, Integer.valueOf(time.split(":")[1]));
		return formatter.format(calendar.getTime());
	}
	
	public static String setTimeWithCurrentDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Calendar calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}
	

	
	public static void clickCheckBox(WebDriver driver, By locator, boolean isSelected) {
		WebElement chkBox = driver.findElement(locator);
		if (isSelected) {
			if (!chkBox.isSelected()) {
				chkBox.click();
			}
		}
		else {
			if (chkBox.isSelected()) {
				chkBox.click();
			}
		}
	}
	
	public static void assertAlertMessage(WebDriver driver, String expectedMsg, int timeInSecond){
		if(isAlertPresent(driver, timeInSecond)){
	        Alert alert = driver.switchTo().alert();
	        assertString(driver, expectedMsg, alert.getText());
//	        alert.accept();
	    }
	    else {
	    	assertFail(driver, "Not exist alert");
	    }
	}
	
	public static boolean isAlertPresent(WebDriver driver, int timeInSecond){
	      try{
	    	  WebDriverWait wait = new WebDriverWait(driver, timeInSecond);
			  wait.until(ExpectedConditions.alertIsPresent());
	          driver.switchTo().alert();
	          return true;
	      }catch(NoAlertPresentException ex){
	          return false;
	      }
	}
	
	public static void mouseHoverAction(WebDriver driver, By locator) {
		waitForElementVisibility(driver, locator);
		try {
			WebElement element = driver.findElement(locator);
			Actions act = new Actions(driver);
			act.moveToElement(element).perform();
		}catch(Exception e) {
			Assert.fail("Could not mouse hover: " + e.getMessage());
		}
	}
	
	public static void selectComboBox(WebDriver driver, By locator, String selectedItem) {
		if (selectedItem != "") {
		    Utilities.click(driver, locator);
			Utilities.inputValueAndValidate(driver, locator, selectedItem, selectedItem);
			Utilities.wait(Constant.WAIT_REFRESH_SCREEN);
			WebElement element = driver.findElement(locator);
			element.sendKeys(Keys.DOWN);
			element.sendKeys(Keys.ENTER);
		}
	}
	
	public static void selectMultiInComboBox(WebDriver driver, By locator, ArrayList<String> listSelectedItem) {
		for(String selectedItem : listSelectedItem) {
			selectComboBox(driver, locator, selectedItem);
		}
	}
}
