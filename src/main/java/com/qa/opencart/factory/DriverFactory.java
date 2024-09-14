package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;// public we apply so that this driver is used throughout the class
	public static String highlight;
	private OptionsManager optionsManager;
	private Properties prop;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	/**
	 * This method is used to initilize the driver
	 * 
	 * @param browserName
	 * @return
	 */
	//public WebDriver initDriver(String browserName) {
	public WebDriver initDriver(Properties prop) {
//		if(prop.getProperty("browser") == null) {
//			String browserName = System.getProperty("browser");
//		}
		String browserName = prop.getProperty("browser");// getproperty is a method in properties class
		highlight = prop.getProperty("highlight");// mentioned in config.properties

		System.out.println("browser name is : " + browserName);
		optionsManager = new OptionsManager(prop);
		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("chrome");
			} else {
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}

		}

		else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();

			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				init_remoteDriver("firefox");
			} else {
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}

		} else if (browserName.equalsIgnoreCase("safari")) {
			tlDriver.set(new SafariDriver());
		}

		else {
			System.out.println("please pass the right browserName : " + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();

		getDriver().get(prop.getProperty("url"));// same as in browserName

		return getDriver();

	}

	private void init_remoteDriver(String browserName) {

		System.out.println("running test on remote with browser: " + browserName);

		if (browserName.equals("chrome")) {
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability(ChromeOptions.CAPABILITY, optionsManager.getChromeOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else if (browserName.equals("firefox")) {
			DesiredCapabilities caps = DesiredCapabilities.firefox();
			caps.setCapability(FirefoxOptions.FIREFOX_OPTIONS, optionsManager.getFirefoxOptions());
			try {
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), caps));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}

	}

	public WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * This method is used to initilalize the properties on the basis of given env
	 * name
	 * 
	 * @return
	 */
	
//	public Properties initProperties() {
//		Properties prop = null;
//		
//		try {
//		FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
//		// FileInputStream will make the connection with that file 
//		Properties prop = new Properties();// this will help to load the Properties from config.properties file
//	     prop.load(ip);
//		}
//		catch(FileNotFoundException e) {
//			e.printStackTrace();
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//		return prop;
//	}
	// from above code we are creating the below code
	
	
	
	public Properties initProperties() {
		prop = null;
		FileInputStream ip = null;

		String env = System.getProperty("env");// mvn clean install -Denv="qa"

		try {
			if (env == null) {
				System.out.println("Running on Environment: PROD env....");
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				// FileInputStream will make the connection with that file 

			}

			else {
				System.out.println("Running on Environment: " + env);

				switch (env) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;

				default:
					System.out.println("No ENV found.....");
					throw new Exception("NOENVFOUNDEXCEPTION");

				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			prop = new Properties();
			prop.load(ip);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}

	/**
	 * take screenshot
	 */

	public String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);

		try {
			FileUtils.copyFile(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}

}