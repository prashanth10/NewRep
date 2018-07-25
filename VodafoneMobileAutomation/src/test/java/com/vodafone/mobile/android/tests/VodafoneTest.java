package com.vodafone.mobile.android.tests;

import java.util.logging.Logger;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.selenium.mobile.base.MobileTestCase;

@SuppressWarnings("unused")
public class VodafoneTest extends MobileTestCase {
	@BeforeClass
	public void loginToApp(){
		getPageFactory().getVodafone().loginToApp();
	}
	private final static Logger LOGGER = Logger.getLogger(VodafoneTest.class
			.getName());
	@Test(priority=1,testName = "Log in to Vodaphone", description = "Log in to Vodaphone", enabled = true, groups = {})
	public void validateLastRecharge() throws Exception {
		LOGGER.info("Executing validateTransaction");
	}
}
