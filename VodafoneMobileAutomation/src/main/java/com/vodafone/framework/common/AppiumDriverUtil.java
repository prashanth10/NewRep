package com.vodafone.framework.common;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.vodafone.framework.dto.Capability;
import com.vodafone.framework.dto.handler.CapabilityHandler;

import io.appium.java_client.android.AndroidDriver;

public class AppiumDriverUtil {

	private AndroidDriver driver = null;
	private static Capability capability = CapabilityHandler.getInstance().getCapability();

	public AndroidDriver getAndroidDriver() {
		if (driver == null) {
			/*
			 * File appDir = new File(capability.getFilePath()); File app = new
			 * File(appDir, capability.getApplicationName());
			 */
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("device", capability.getDevice());
			capabilities.setCapability("platform", capability.getPlatform());
			capabilities.setCapability("version", capability.getVersion());
			capabilities.setCapability("newCommandTimeout", "300");
			capabilities.setCapability("deviceType", capability.getDeviceType());
			capabilities.setCapability("deviceName", capability.getDeviceName());
			capabilities.setCapability("platformName", capability.getPlatformName());
			// capabilities.setCapability("app", app.getAbsolutePath());
			capabilities.setCapability("app-package", capability.getAppPackage());
			capabilities.setCapability("app-activity", capability.getAppActivity());
			try {
				driver = new AndroidDriver(new URL(capability.getAppiumServerPath()), capabilities);
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return driver;
	}

	// Just to check
	private static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

	private static String getCurrentContextHandle(RemoteWebDriver driver) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		String context = (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
		return context;
	}

	private static List<String> getContextHandles(RemoteWebDriver driver) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		List<String> contexts = (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
		return contexts;
	}
	//

}