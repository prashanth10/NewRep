package com.selenium.mobile.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.vodafone.framework.common.AppiumDriverUtil;
import com.vodafone.framework.dto.UserObj;
import com.vodafone.framework.utility.PropertyElementReader;
import com.vodafone.mobile.pages.factory.VodafoneMobilePageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class MobileTestCase {
	public static AppiumDriver driver;
	private AppiumDriverUtil appiumutil = new AppiumDriverUtil();
	private String platform;
	private VodafoneMobilePageFactory pageFactory;
	protected ExtentReports reporter;
	public static UserObj usr = null;
	protected static PropertyElementReader elementReader = PropertyElementReader.getInstance();
	public VodafoneMobilePageFactory getPageFactory() {
		return pageFactory;
	}

	@Parameters("platform")
	@BeforeClass(alwaysRun = true)
	public void initializeDrivers(String plt, ITestContext context) throws IOException {
		platform = plt;
		if (platform.equalsIgnoreCase("android")) {
			System.out.println("In android");
			driver = appiumutil.getAndroidDriver();
			context.setAttribute("driver", driver);
		}
		context.setAttribute("driver", driver);
		pageFactory = new VodafoneMobilePageFactory(driver);
	}
	public AppiumDriver getDriver() {
		if ("android".equalsIgnoreCase(platform)) {
			return ((AndroidDriver) driver);
		} else if ("Perfecto_Android".equalsIgnoreCase(platform)) {
			return ((AndroidDriver) driver);
		}
		else {
			return null;
		}
	}

	public void setDriver(AppiumDriver driver) {
		this.driver = driver;
	}

	@AfterClass(alwaysRun = true)
	public void closeApp() {
		if ("android".equals(platform)) {
			Reporter.log("in after class", true);
			((AndroidDriver) driver).resetApp();
			driver.quit();
		} 
	}

	private static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

}