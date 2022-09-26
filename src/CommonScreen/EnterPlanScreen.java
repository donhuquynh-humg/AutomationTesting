package CommonScreen;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import Common.Constant;
import Common.Utilities;

public class EnterPlanScreen {
	public static String enterPlanBtnXpath 			= "//button//span[contains(text(),'Nhập Kế Hoạch')]";

	// Title
	public static String titleXpath 				= "//h2//span[contains(text(),'Thêm mới Cập nhật kế hoạch')]";
	// Text field
	public static String officeCBId					= "listDataInspectionOrg-combo-box";
	public static String objectCBId 				= "listDataInspectedOrgMultiple-combo-box";
	public static String personTxtName 				= "detailInspectionOrg";
	public static String startDateTxtId 			= "startDate-picker-dialog";
	public static String endDateTxtId 				= "endDate-picker-dialog";
	public static String estimatedStartTimeTxtId 	= "estimatedStartTime-picker-dialog";
	public static String estimatedEndTimeTxtId		= "estimatedEndTime-picker-dialog";
	public static String contentCBId	 			= "asynchronous-demo";
	
	// List of values for officeCB
	public static final String OFFICE_VALUE1        = "Thanh tra Huyện An Dương";
	public static final String OFFICE_VALUE2        = "Chi cục Thuế quận Hồng Bàng";
	public static final String OFFICE_VALUE3        = "Chi cục Thuế quận Kiến An";
	
	// List of values for objectCB
	public static final String OBJECT_VALUE1        = "Game Netzon";
	public static final String OBJECT_VALUE2        = "Văn phòng Ủy ban nhân dân thành phố";
	public static final String OBJECT_VALUE3        = "Xã Đồng Bài";
		
	// List of values for contentCB
	public static final String CONTENT_VALUE1        = "Thanh tra công tác cán bộ; chi thường xuyên, chi đầu tư xây dựng cơ bản; thực hiện nhiệm vụ do Giám đốc Sở giao";
	public static final String CONTENT_VALUE2        = "DU phong 6";
	public static final String CONTENT_VALUE3        = "Quản lý, sử dụng ngân sách, đầu tư XDCB tại xã";
	
	public static final String TEST_TEXT        	 = "test";
	public static final String LESS_THAN_MIN_DATE 	 = "31/12/1899";
	public static final String MIN_DATE 			 = "01/01/1990";
	public static final String MAX_DATE 			 = "01/01/2100";
	public static final String GREATER_THAN_MAX_DATE = "02/01/2100";
	
	// Button
	public static String closeIconXpath 			 = "//button//span//span[contains(text(),'close')]";
	public static String cancelBtnXpath 			 = "//button//span[contains(text(),'Hủy')]";	
	public static String draftSaveBtnXpath			 = "//button//span[contains(text(),'Lưu nháp')]";
	public static String saveBtnXpath				 = "//button//span[contains(text(),'Lưu và gửi')]";
	
	public static String dateErrMsgXpath 			 = "//p[contains(text(),'MSG')]";
	public static String msgXpath 					 = "//div[@class='Toastify']//div//div//div";
	public static String successMsg					 = "Thêm thành công";
	
	// Error message
	public static String officeEmptyMsg		  		 = "Đơn vị thanh tra/kiểm tra không được để trống";
	public static String objectEmptyMsg 			 = "Đối tượng thanh tra không được để trống";
	public static String contentEmptyMsg 			 = "Nội dung kế hoạch không được để trống.";
	public static String startDateEmptyMsg 			 = "Niên độ (từ ngày) không được để trống";
	public static String estimatedStartTimeEmptyMsg  = "Thời gian dự kiến (từ ngày) không được để trống";
	public static String dateLessThanMinValueMsg 	 = "Ngày không được phép nhỏ hơn giá trị nhỏ nhất";
	public static String dateGreaterThanMaxValueMsg  = "Ngày không được phép lớn hơn giá trị lớn nhất";
	public static String endDateLessThanStartDateMsg = "Ngày kết thúc không được trước ngày bắt đầu";
		
	public static WebDriver openScreen(String browser) {
		WebDriver driver = null;
		if (!browser.isEmpty()) {
			driver = Utilities.getDriver(browser);
			driver.get(Constant.BASE_URL);
			Utilities.waitForElementClickable(driver, By.xpath(LoginScreen.loginBtnXpath), Constant.WAIT_CLICKABLE);
			LoginScreen.loginAndWait(driver, Constant.BASE_EMAIL, Constant.BASE_PASSWORD, By.xpath(HomeScreen.avatarIconXpath));
			Utilities.mouseHoverAction(driver, By.xpath(HomeScreen.linkXpath.replace("LINK", HomeScreen.PLAN)));
			Utilities.clickObscuredElement(driver, HomeScreen.linkXpath.replace("LINK", HomeScreen.ADMINISTRATION), EnterPlanScreen.enterPlanBtnXpath, Constant.WAIT_ELEMENT_EXIST);
			Utilities.clickObscuredElement(driver, By.xpath(EnterPlanScreen.enterPlanBtnXpath), By.xpath(EnterPlanScreen.closeIconXpath), Constant.WAIT_ELEMENT_EXIST);
		}
		return driver;
	}
		
	public static void enterPlan(WebDriver driver, String office, String object, String person, String startDate, String endDate, String estimatedStartTime, String estimatedEndTime, String content) {
		Utilities.selectComboBox(driver, By.id(EnterPlanScreen.officeCBId), office);
		Utilities.selectComboBox(driver, By.id(EnterPlanScreen.objectCBId), object);
		Utilities.inputValueAndValidate(driver, By.name(EnterPlanScreen.personTxtName), person, person);
		Utilities.inputValueAndValidateDate(driver, By.id(EnterPlanScreen.startDateTxtId), startDate, startDate);
		Utilities.inputValueAndValidateDate(driver, By.id(EnterPlanScreen.endDateTxtId), endDate, endDate);
		Utilities.inputValueAndValidateDate(driver, By.id(EnterPlanScreen.estimatedStartTimeTxtId), estimatedStartTime, estimatedStartTime);
		Utilities.inputValueAndValidateDate(driver, By.id(EnterPlanScreen.estimatedEndTimeTxtId), estimatedEndTime, estimatedEndTime);
		Utilities.selectComboBox(driver, By.id(EnterPlanScreen.contentCBId), content);
	}
	
	public static void enterPlanWithMultiObject(WebDriver driver, String office, ArrayList<String> listObject, String person, String startDate, String endDate, String estimatedStartTime, String estimatedEndTime, String content) {
		Utilities.selectComboBox(driver, By.id(EnterPlanScreen.officeCBId), office);
		Utilities.selectMultiInComboBox(driver, By.id(EnterPlanScreen.objectCBId), listObject);
		Utilities.inputValueAndValidate(driver, By.name(EnterPlanScreen.personTxtName), person, person);
		Utilities.inputValueAndValidateDate(driver, By.id(EnterPlanScreen.startDateTxtId), startDate, startDate);
		Utilities.inputValueAndValidateDate(driver, By.id(EnterPlanScreen.endDateTxtId), endDate, endDate);
		Utilities.inputValueAndValidateDate(driver, By.id(EnterPlanScreen.estimatedStartTimeTxtId), estimatedStartTime, estimatedStartTime);
		Utilities.inputValueAndValidateDate(driver, By.id(EnterPlanScreen.estimatedEndTimeTxtId), estimatedEndTime, estimatedEndTime);
		Utilities.selectComboBox(driver, By.id(EnterPlanScreen.contentCBId), content);
	}
		
	// Enter plan and save then check message
	public static void savePlan(WebDriver driver, String office, String object, String person, String startDate, String endDate, String estimatedStartTime, String estimatedEndTime, String content, String expectMsg) {
		EnterPlanScreen.enterPlan(driver, office, object, person, startDate, endDate, estimatedStartTime, estimatedEndTime, content);
		Utilities.click(driver, By.xpath(EnterPlanScreen.saveBtnXpath));
		Utilities.assertTextValueVisible(driver, By.xpath(msgXpath), expectMsg);
	}
	
	// Enter plan and draft save then check message
	public static void draftSavePlan(WebDriver driver, String office, String object, String person, String startDate, String endDate, String estimatedStartTime, String estimatedEndTime, String content, String expectMsg) {
		EnterPlanScreen.enterPlan(driver, office, object, person, startDate, endDate, estimatedStartTime, estimatedEndTime, content);
		Utilities.click(driver, By.xpath(EnterPlanScreen.draftSaveBtnXpath));
		Utilities.assertTextValueVisible(driver, By.xpath(msgXpath), expectMsg);
	}

	// Enter plan and save then check message
	public static void savePlanWithMultiObject(WebDriver driver, String office, ArrayList<String> listObject, String person, String startDate, String endDate, String estimatedStartTime, String estimatedEndTime, String content, String expectMsg) {
		EnterPlanScreen.enterPlanWithMultiObject(driver, office, listObject, person, startDate, endDate, estimatedStartTime, estimatedEndTime, content);
		Utilities.click(driver, By.xpath(EnterPlanScreen.saveBtnXpath));
		Utilities.assertTextValueVisible(driver, By.xpath(msgXpath), expectMsg);
	}
	
	// Enter plan and draft save then check message
	public static void draftSavePlanWithMultiObject(WebDriver driver, String office, ArrayList<String> listObject, String person, String startDate, String endDate, String estimatedStartTime, String estimatedEndTime, String content, String expectMsg) {
		EnterPlanScreen.enterPlanWithMultiObject(driver, office, listObject, person, startDate, endDate, estimatedStartTime, estimatedEndTime, content);
		Utilities.click(driver, By.xpath(EnterPlanScreen.draftSaveBtnXpath));
		Utilities.assertTextValueVisible(driver, By.xpath(msgXpath), expectMsg);
	}
	
	// Check date 
	public static void checkDate(WebDriver driver, By locator, String date, String expectErrMsg) {
		Utilities.inputValueAndValidateDate(driver, locator, date, date);
		Utilities.assertElementVisible(expectErrMsg, By.xpath(EnterPlanScreen.dateErrMsgXpath.replace("MSG", expectErrMsg)));
	}
	
	// Check multi date 
	public static void checkMultiDate(WebDriver driver, By locator1, String date1, By locator2, String date2, String expectErrMsg) {
		Utilities.inputValueAndValidateDate(driver, locator1, date1, date1);
		Utilities.inputValueAndValidateDate(driver, locator2, date2, date2);
		Utilities.assertElementVisible(expectErrMsg, By.xpath(EnterPlanScreen.dateErrMsgXpath.replace("MSG", expectErrMsg)));
	}
	
	// Check close dialog
	public static void checkCloseDialog(WebDriver driver, By locator) {
		Utilities.clickObscuredElementToNotVisible(driver, locator, locator, Constant.WAIT_ELEMENT_NOT_EXIST);
	}
}
