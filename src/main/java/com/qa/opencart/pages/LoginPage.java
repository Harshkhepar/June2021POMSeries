package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.utils.Constants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {

	private WebDriver driver;// we make private bcoz i don't want anyone can access this driver- this driver is only for this class
	// suppose you will create the object of this class then you will access this driver -no i don't want this
	private ElementUtil elementUtil;
	
	//Every page has specific template: the things we have to maintain
	//1. private By locators: First thing we need to do is to maintain private "By" locators
	private By emailId = By.id("input-email");// we make locators private bcoz test class is calling this java class methods
	// and this java class methods are using these locators so our locators are not exposed to test class
	// this is the encapsulation 
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.xpath("//div[@class='form-group']//a[text()='Forgotten Password']");
	private By header = By.cssSelector("div#logo h1 a");
	private By registerLink = By.linkText("Register");

	//2. constructor:we can't create the private constructor bcoz then we can't create the object of this class
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		elementUtil = new ElementUtil(driver);
	}

	//3. page actions/ page methods/ functionality/behavior of the page/ no assertion- and it always be public

//	public String getLoginPageTitle() {
//		return driver.getTitle();
//	}
//	public String getPageHeaderText() {
//		return driver.findElement(header).getText();
//	}
//	public boolean isForgotPwdLinkExist() {
//		return driver.findElement(forgotPwdLink).isDisplayed();
//	}
//	public AccountsPage doLogin(String un, String pwd) {
//		driver.findElement(emailId).sendKeys(un);
//		driver.findElement(password).sendKeys(pwd);
//		driver.findElement(loginBtn).click();
//	}
	
	@Step("getting login page title....")
	public String getLoginPageTitle() {
		return elementUtil.waitForTitleIs(Constants.LOGIN_PAGE_TITLE, 5);
	}

	@Step("getting login page header text....")
	public String getPageHeaderText() {
		return elementUtil.doGetText(header);
	}

	@Step("checking forgot pwd link is displayed on login page....")
	public boolean isForgotPwdLinkExist() {
		return elementUtil.doIsDisplayed(forgotPwdLink);
	}

	@Step("login to application with username {0} and password {1}")
	public AccountsPage doLogin(String un, String pwd) {
		System.out.println("============"+un + ":"+ pwd + "=================");
		elementUtil.doSendKeys(emailId, un);
		elementUtil.doSendKeys(password, pwd);
		elementUtil.doClick(loginBtn);
		// then method which is responsible to move to next has responsibility to return the next landing page object
		return new AccountsPage(driver);
	}
	
	@Step("navigating to reg page....")
	public RegistrationsPage nagigateToRegisterPage() {
		elementUtil.doClick(registerLink);
		return new RegistrationsPage(driver);
	}

}