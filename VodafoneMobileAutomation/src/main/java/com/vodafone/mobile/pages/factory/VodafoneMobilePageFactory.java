package com.vodafone.mobile.pages.factory;

import com.vodafone.mobile.pages.Vodafone;

import io.appium.java_client.AppiumDriver;

public class VodafoneMobilePageFactory {
    
    private AppiumDriver driver;

	public VodafoneMobilePageFactory(AppiumDriver driver) {
       this.driver = driver;
       vodafone = new Vodafone(driver);
    }

	private Vodafone vodafone ;//= new ChannelEDP(driver);
	
	
	public AppiumDriver getDriver() {
		return driver;
	}

	public void setDriver(AppiumDriver driver) {
		this.driver = driver;
	}

	public Vodafone getVodafone() {
		return vodafone;
	}

	public void setVodafone(Vodafone channeledp) {
		this.vodafone = vodafone;
	}

}
