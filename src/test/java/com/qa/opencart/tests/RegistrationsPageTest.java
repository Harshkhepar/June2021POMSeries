package com.qa.opencart.tests;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ExcelUtil;

public class RegistrationsPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		regPage = loginPage.nagigateToRegisterPage();
	}

	// utility for generating random email id
	public String getRandomEmail() {
		Random random = new Random();// this Random class is already available in java
		String email = "testautomation"+random.nextInt(5000)+"@gmail.com";
		System.out.println(email);
		return email;
	}
	
//	@DataProvider
//	public Object[][] getRegTestData() {
//		return new Object[][]{
//	                 {"Bhumika", "Patel", 
//	                                "bhumika345@gmail.com", "9876543211", "bhumika@123", "yes"},
//	                 {"Tom", "Peter", 
//                                  "tomqq@gmail.com", "9876543211", "tommy@123", "no"},
//	                 {"Lisa", "ann", 
//                                  "lisaa@gmail.com", "9876543211", "lisa@123", "yes"},
	
//	};
//	}

//	@Test
//	public void registrationTest() {
//		Assert.assertTrue(
//				regPage.accountRegistration("Bhumika", "Patel", 
//						"bhumika345@gmail.com", "9876543211", "bhumika@123", "yes")
//				);
//
//	}
	
	
	@DataProvider
	public Object[][] getRegTestData() {
		return ExcelUtil.getTestData(Constants.REGISTER_SHEET_NAME);
	}
	
	@Test(dataProvider = "getRegTestData")
	public void registrationTest(String firstName, String lastName, String telephone, String password,
			String subscribe) {
		Assert.assertTrue(
				regPage.accountRegistration(firstName, lastName, 
						getRandomEmail(), telephone, password, subscribe)
				);

	}

}