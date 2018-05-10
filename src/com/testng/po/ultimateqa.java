package com.testng.po;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ultimateqa {

	public static WebDriver driver;
	public Logger logger;

	String sauceMode;

	@BeforeTest
	public void browserSetup() throws IOException {
		logger = Logger.getLogger("ultimateqa");
		PropertyConfigurator.configure("log4j.properties");

		readPropertyFile();

		if (sauceMode.equalsIgnoreCase("off")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\prokarma\\eclipse-workspace\\SampleAutomation\\drivers\\chromedriver.exe");
			driver = new ChromeDriver();

			logger.info("Selenium Webdriver Script in Chrome browser");
		} else if (sauceMode.equalsIgnoreCase("on")) {
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability("platform", "Windows 10");
			caps.setCapability("version", "latest");
			driver = new RemoteWebDriver(new URL(SampleSauceTest.URL), caps);
		} else {
			logger.info("Saucelab property values is wrong. Please check the config.proprties");
		}

		driver.manage().window().maximize();
		driver.get("https://www.ultimateqa.com/simple-html-elements-for-automation/");

	}

	private void readPropertyFile() throws IOException {
		Properties properties = new Properties();
		FileInputStream in = new FileInputStream("config.properties");
		try {
			properties.load(in);
			sauceMode = properties.getProperty("sauceMode");
			System.out.println(sauceMode);
		} catch (IOException e) {
			e.printStackTrace();
		}
		in.close();

	}

	@Test(priority = 1)
	public void buttonClick1() {
		/*
		 * Button click and validate the text in next page
		 */
		driver.findElement(By.xpath("//a[@class='et_pb_promo_button et_pb_button']")).click();
		logger.info("Button clicked");

		String text = "Button success";
		List<WebElement> list = driver.findElements(By.xpath("//h1[contains(text(),'" + text + "')]"));
		Assert.assertEquals(list.size() > 0, true);
		System.out.println(list.get(0).getText() + ": Text Validated");

		driver.navigate().back();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,900)");

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	}

	@AfterTest
	public void closeBrowser() {
		driver.quit();
	}
}
