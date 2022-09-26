package TestSuite;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Common.Initialization;
import Common.Utilities;
import CommonScreen.EnterPlanScreen;


public class EnterPlan extends Initialization{	
	final String CURRENT_DATE = Utilities.getCurrentDate("dd/MM/yyyy");
	
	@BeforeMethod()
	public void setUpMethod(Method method) throws Exception{	
		driver = EnterPlanScreen.openScreen(browser);
		Utilities.testID = method.getName();
	}
		
	@Test()
	public void MODULE2_01() throws IOException{
		EnterPlanScreen.savePlan(driver, "", EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.officeEmptyMsg);
	}	

	@Test()
	public void MODULE2_02() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE2, EnterPlanScreen.successMsg);
	}	
	
	@Test()
	public void MODULE2_03() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE2, "", "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.objectEmptyMsg);
	}	
	
	@Test()
	public void MODULE2_04() throws IOException{
		ArrayList<String> listObject = new ArrayList<>();
		listObject.add(EnterPlanScreen.OBJECT_VALUE1);
		listObject.add(EnterPlanScreen.OBJECT_VALUE2);
		listObject.add(EnterPlanScreen.OBJECT_VALUE3);
		EnterPlanScreen.savePlanWithMultiObject(driver, EnterPlanScreen.OFFICE_VALUE1, listObject, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE2, EnterPlanScreen.successMsg);

	}	

	@Test()
	public void MODULE2_05() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE3, EnterPlanScreen.OBJECT_VALUE2, EnterPlanScreen.TEST_TEXT, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, "", EnterPlanScreen.contentEmptyMsg);
	}	
	
	@Test()
	public void MODULE2_06() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE3, EnterPlanScreen.OBJECT_VALUE2, EnterPlanScreen.TEST_TEXT, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.startDateEmptyMsg);
	}	
	
	@Test()
	public void MODULE2_07() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE3, EnterPlanScreen.OBJECT_VALUE3, EnterPlanScreen.TEST_TEXT, CURRENT_DATE, CURRENT_DATE, "", CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.estimatedStartTimeEmptyMsg);
	}
	
	@Test()
	public void MODULE2_08() throws IOException{
		EnterPlanScreen.checkDate(driver, By.id(EnterPlanScreen.startDateTxtId), EnterPlanScreen.LESS_THAN_MIN_DATE, EnterPlanScreen.dateLessThanMinValueMsg);
	}
	
	@Test()
	public void MODULE2_09() throws IOException{
		EnterPlanScreen.checkDate(driver, By.id(EnterPlanScreen.startDateTxtId), EnterPlanScreen.GREATER_THAN_MAX_DATE, EnterPlanScreen.dateGreaterThanMaxValueMsg);
	}
	
	@Test()
	public void MODULE2_10() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, "", CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_11() throws IOException{
		EnterPlanScreen.checkMultiDate(driver, By.id(EnterPlanScreen.startDateTxtId), EnterPlanScreen.MAX_DATE, By.id(EnterPlanScreen.endDateTxtId), EnterPlanScreen.MIN_DATE, EnterPlanScreen.endDateLessThanStartDateMsg);
	}
	
	@Test()
	public void MODULE2_12() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", EnterPlanScreen.MIN_DATE, EnterPlanScreen.MIN_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_13() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE2, EnterPlanScreen.OBJECT_VALUE2, "", CURRENT_DATE, EnterPlanScreen.MAX_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_14() throws IOException{
		EnterPlanScreen.checkDate(driver, By.id(EnterPlanScreen.estimatedStartTimeTxtId), EnterPlanScreen.LESS_THAN_MIN_DATE, EnterPlanScreen.dateLessThanMinValueMsg);
	}
	
	@Test()
	public void MODULE2_15() throws IOException{
		EnterPlanScreen.checkDate(driver, By.id(EnterPlanScreen.estimatedStartTimeTxtId), EnterPlanScreen.GREATER_THAN_MAX_DATE, EnterPlanScreen.dateGreaterThanMaxValueMsg);
	}
	
	@Test()
	public void MODULE2_16() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, EnterPlanScreen.MAX_DATE, EnterPlanScreen.MIN_DATE, "", EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_17() throws IOException{
		EnterPlanScreen.checkMultiDate(driver, By.id(EnterPlanScreen.estimatedStartTimeTxtId), EnterPlanScreen.MAX_DATE, By.id(EnterPlanScreen.estimatedEndTimeTxtId), EnterPlanScreen.MIN_DATE, EnterPlanScreen.endDateLessThanStartDateMsg);
	}
	
	@Test()
	public void MODULE2_18() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.MIN_DATE, EnterPlanScreen.MIN_DATE, EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_19() throws IOException{
		EnterPlanScreen.savePlan(driver, EnterPlanScreen.OFFICE_VALUE2, EnterPlanScreen.OBJECT_VALUE2, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.MAX_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_20() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, "", EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.officeEmptyMsg);
	}	

	@Test()
	public void MODULE2_21() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE2, EnterPlanScreen.successMsg);
	}	
	
	@Test()
	public void MODULE2_22() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE2, "", "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.objectEmptyMsg);
	}	
	
	@Test()
	public void MODULE2_23() throws IOException{
		ArrayList<String> listObject = new ArrayList<>();
		listObject.add(EnterPlanScreen.OBJECT_VALUE1);
		listObject.add(EnterPlanScreen.OBJECT_VALUE3);
		EnterPlanScreen.draftSavePlanWithMultiObject(driver, EnterPlanScreen.OFFICE_VALUE1, listObject, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE2, EnterPlanScreen.successMsg);
	}	

	@Test()
	public void MODULE2_24() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE3, EnterPlanScreen.OBJECT_VALUE2, EnterPlanScreen.TEST_TEXT, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, "", EnterPlanScreen.contentEmptyMsg);
	}	
	
	@Test()
	public void MODULE2_25() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE3, EnterPlanScreen.OBJECT_VALUE2, EnterPlanScreen.TEST_TEXT, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.startDateEmptyMsg);
	}	
	
	@Test()
	public void MODULE2_26() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE3, EnterPlanScreen.OBJECT_VALUE3, EnterPlanScreen.TEST_TEXT, CURRENT_DATE, CURRENT_DATE, "", CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.estimatedStartTimeEmptyMsg);
	}
	
	@Test()
	public void MODULE2_27() throws IOException{
		EnterPlanScreen.checkDate(driver, By.id(EnterPlanScreen.endDateTxtId), EnterPlanScreen.LESS_THAN_MIN_DATE, EnterPlanScreen.dateLessThanMinValueMsg);
	}
	
	@Test()
	public void MODULE2_28() throws IOException{
		EnterPlanScreen.checkDate(driver, By.id(EnterPlanScreen.endDateTxtId), EnterPlanScreen.GREATER_THAN_MAX_DATE, EnterPlanScreen.dateGreaterThanMaxValueMsg);
	}
	
	@Test()
	public void MODULE2_29() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, "", CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_30() throws IOException{
		EnterPlanScreen.checkMultiDate(driver, By.id(EnterPlanScreen.startDateTxtId), EnterPlanScreen.MAX_DATE, By.id(EnterPlanScreen.endDateTxtId), EnterPlanScreen.MIN_DATE, EnterPlanScreen.endDateLessThanStartDateMsg);
	}
	
	@Test()
	public void MODULE2_31() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", EnterPlanScreen.MIN_DATE, EnterPlanScreen.MIN_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_32() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE2, EnterPlanScreen.OBJECT_VALUE2, "", CURRENT_DATE, EnterPlanScreen.MAX_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_33() throws IOException{
		EnterPlanScreen.checkDate(driver, By.id(EnterPlanScreen.estimatedEndTimeTxtId), EnterPlanScreen.LESS_THAN_MIN_DATE, EnterPlanScreen.dateLessThanMinValueMsg);
	}
	
	@Test()
	public void MODULE2_34() throws IOException{
		EnterPlanScreen.checkDate(driver, By.id(EnterPlanScreen.estimatedEndTimeTxtId), EnterPlanScreen.GREATER_THAN_MAX_DATE, EnterPlanScreen.dateGreaterThanMaxValueMsg);
	}
	
	@Test()
	public void MODULE2_35() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, EnterPlanScreen.MAX_DATE, EnterPlanScreen.MIN_DATE, "", EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_36() throws IOException{
		EnterPlanScreen.checkMultiDate(driver, By.id(EnterPlanScreen.estimatedStartTimeTxtId), EnterPlanScreen.MAX_DATE, By.id(EnterPlanScreen.estimatedEndTimeTxtId), EnterPlanScreen.MIN_DATE, EnterPlanScreen.endDateLessThanStartDateMsg);
	}
	
	@Test()
	public void MODULE2_37() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE1, EnterPlanScreen.OBJECT_VALUE1, "", CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.MIN_DATE, EnterPlanScreen.MIN_DATE, EnterPlanScreen.CONTENT_VALUE3, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_38() throws IOException{
		EnterPlanScreen.draftSavePlan(driver, EnterPlanScreen.OFFICE_VALUE2, EnterPlanScreen.OBJECT_VALUE2, "", CURRENT_DATE, CURRENT_DATE, CURRENT_DATE, EnterPlanScreen.MAX_DATE, EnterPlanScreen.CONTENT_VALUE1, EnterPlanScreen.successMsg);
	}
	
	@Test()
	public void MODULE2_39() throws IOException{
		EnterPlanScreen.checkCloseDialog(driver, By.xpath(EnterPlanScreen.closeIconXpath));
	}
	
	@Test()
	public void MODULE2_40() throws IOException{
		EnterPlanScreen.checkCloseDialog(driver, By.xpath(EnterPlanScreen.cancelBtnXpath));
	}
	
	@AfterMethod()  
	public void tearDownMethod(ITestResult result, Method method){
		afterMethod(result, method);
		Utilities.closeDriver(driver);
	}
}
