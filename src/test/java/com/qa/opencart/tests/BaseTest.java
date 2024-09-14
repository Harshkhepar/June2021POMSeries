package com.qa.opencart.tests;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegistrationsPage;
import com.qa.opencart.pages.ResultsPage;

public class BaseTest {

	WebDriver driver;
	Properties prop;
	DriverFactory df;
	
	SoftAssert softAssert;
	

	LoginPage loginPage;
	AccountsPage accPage;
	ResultsPage resultsPage;
	ProductInfoPage productInfoPage;
	RegistrationsPage regPage;

	@BeforeTest
	// I have to call the initDriver method from DriverFactory class
	public void setUp() {
		
		softAssert = new SoftAssert();
		df = new DriverFactory();
		prop = df.initProperties();// to read the properties
	//	driver = df.initDriver("chrome");// df.initDriver("chrome") this return the driver 
	// driver got initialized inside the DriverFactory method initDriver and we are passing this driver to BaseTest class driver.
	// then BaseTest will give this driver to other class	
		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);// to call the method of this class in LoginPageTest class 
		                                  // loginPage.java class constructor need driver as a parameter
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}

}