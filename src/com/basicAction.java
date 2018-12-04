package com;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class basicAction {
	public static WebDriver driver;
	
	public static WebElement xpath(String xpath)
	{
		return driver.findElement(By.xpath(xpath));
	}

	public static WebElement id(String id)
	{
		return driver.findElement(By.id(id));
	}
	
	@BeforeTest
	public void openBrowser() throws InterruptedException, IOException 
	{
		System.setProperty("webdriver.chrome.driver","C:\\Users\\Alkes\\workspace\\Bank\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test(priority=1)
	public void appLogin()
	{
		driver.navigate().to("http://zero.webappsecurity.com/index.html");
	   	 if(xpath("//div[@class='wrapper']/div/div/div/a").isDisplayed())
	   	 {
	   		System.out.println("Page loaded"); 
	   	 }
	   		 
	   	 else
	   	 {
	   		System.out.println("Test Case Failed"); 
	   	 }
	   	 id("signin_button").click();
	   	 id("user_login").clear();
	   	 id("user_login").sendKeys("username");
	   	 id("user_password").clear();
	   	 id("user_password").sendKeys("password");
	   	 xpath("//div[@class='form-inputs']/following::div/input").click();
	   	 if(xpath("//li[@id='account_summary_tab']/a").isDisplayed())
	   	 {
	   		 System.out.println("App logged in successful");
	   	 }
	   	 else
	   	 {
	   		 System.out.println("Test case fail");
	   	 }
	}
	
	@Test(priority=2)
	public void reteriveAccountBalance()
	{
		int cash1 = Integer.parseInt(xpath("//div[@class='offset2 span8']/div[1]/div/table/tbody/tr[1]//td[3]").getText().replace("$", ""));
		int cash2 = Integer.parseInt(xpath("//div[@class='offset2 span8']/div[1]/div/table/tbody/tr[2]//td[3]").getText().replace("$", ""));
		int totalAmount = cash1+cash2;
		System.out.println("Total Amount = "+totalAmount);
	}
	
	@Test(priority=3)
	public void clickHyperlink() throws InterruptedException
	{
		for(int i=1;i<=3;i++)
		{
			xpath("//div[@class='offset2 span8']/div["+i+"]/div/table/tbody/tr[1]//td[1]/a").click();
			WebDriverWait pageHeader = new WebDriverWait(driver, 60);
			pageHeader.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='offset2 span8']/div[1]/div[1]/h2")));
			if (xpath("//div[@class='offset2 span8']/div[1]/div[1]/h2").isDisplayed()) 
			{
				System.out.println("Page redirect successfully");
				
			} 
			else 
			{
				System.out.println("Test case Fail");
				
			}
			driver.navigate().back();
			Thread.sleep(2000);
		}
		
	}
	
	@Test(priority=4)
	public void verifiyContent()
	{
		xpath("//li[@id='account_activity_tab']/a").click();
		String panelName = xpath("//div[@class='offset2 span8']/div[1]/div[1]/h2").getText();
		if(panelName.equalsIgnoreCase("Show Transactions"))
		{
			System.out.println("Content Matched");
		}
		else
		{
			System.out.println("Test case Fail");
		}
	}
	
	@Test(priority=5)
	public void getWithdrawalAmount() throws InterruptedException
	{
		id("aa_accountId").click();
		Thread.sleep(3500);
		xpath("//select[@id='aa_accountId']/option[2]").click();
		Thread.sleep(3500);
		xpath("//div[@class='offset2 span8']/div[1]/div[1]/h2").click();
		for(int i=2;i<=3;i++)
		{
			String Description = xpath("//div[@id='all_transactions_for_account']/table/tbody/tr["+i+"]/td[2]").getText();
			String Amount = xpath("//div[@id='all_transactions_for_account']/table/tbody/tr["+i+"]/td[4]").getText();
			System.out.println(Description+" = "+Amount);
		}
	}
	
	@AfterTest
	public void closeBrowser()
	{
		driver.close();
	}

}
