package com.testng.po;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ultimateqa {

	public static WebDriver driver;
	public Logger logger;

	@BeforeTest
	public void browserSetup() {
		logger = Logger.getLogger("ultimateqa");
		PropertyConfigurator.configure("log4j.properties");
		
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\prokarma\\eclipse-workspace\\SampleAutomation\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.ultimateqa.com/simple-html-elements-for-automation/");
		driver.manage().window().maximize();
		logger.info("Selenium Webdriver Script in Chrome browser");
	}

	@BeforeTest
	public void loggerSetup() {
		
	}

	@Test(priority=1)
	public void buttonClick1() {
		/*
		 * Button click and validate the text in next page
		 */
		driver.findElement(By.xpath("//a[@class='et_pb_promo_button et_pb_button']")).click();
		logger.info("Button clicked");

		String text = "Button success";
		List<WebElement> list = driver.findElements(By.xpath("//h1[contains(text(),'" + text + "')]"));
		System.out.println(list.get(0).getText() + ": Text Validated");
		Assert.assertEquals(list.size() > 0, true);

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
