package com.vodafone.mobile.pages;

import java.util.logging.Logger;

import org.openqa.selenium.By;

import com.vodafone.framework.utility.PropertyElementReader;

import io.appium.java_client.AppiumDriver;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Vodafone {
	private final static Logger LOGGER = Logger.getLogger(Vodafone.class.getName());
	private PropertyElementReader elementReader = PropertyElementReader.getInstance();

	private AppiumDriver driver;

	public Vodafone(AppiumDriver driver) {
		super();
		this.driver = driver;
	}

	public void loginToApp() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.findElement(By.xpath(elementReader.getPropertyElement("Login"))).click();
		
	}

}
