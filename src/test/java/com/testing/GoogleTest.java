package com.testing;

import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Testing.
 */
public class GoogleTest {
	WebDriver driver;
	
	@Test
	public void testGoogle(){
		WebDriver driver = new FirefoxDriver();
		driver.get("http://google.com");
		driver.quit();		
	}
	
}
