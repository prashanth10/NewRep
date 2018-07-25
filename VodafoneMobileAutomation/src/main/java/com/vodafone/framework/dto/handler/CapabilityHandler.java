package com.vodafone.framework.dto.handler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.vodafone.framework.dto.Capability;
import com.vodafone.framework.dto.Capability;

public class CapabilityHandler {

	private static CapabilityHandler _instance;
	private static InputStream in;
	private static final String FILEPATH = "src/test/resources/AppiumCapability.properties";
	private static Properties Capability = new Properties();
	private static Capability cap = null;

	static {
		try {
			in = new FileInputStream(System.getProperty("user.dir") + "/" + FILEPATH);
			Capability.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static CapabilityHandler getInstance() {
		if (_instance == null) {
			_instance = new CapabilityHandler();
		}
		return _instance;
	}

	public static Capability getCapability() {
		if (cap == null) {
			cap = new Capability();
			cap.setBrowserName(Capability.getProperty("browserName"));
			cap.setDeviceName(Capability.getProperty("deviceName"));
			cap.setAutomationName(Capability.getProperty("automationName"));
			cap.setAppPackage(Capability.getProperty("appPackage"));
			cap.setBundleId(Capability.getProperty("bundleId"));
			cap.setDeviceType(Capability.getProperty("deviceType"));
			cap.setPlatform(Capability.getProperty("platform"));
			cap.setVersion(Capability.getProperty("version"));
			cap.setDevice(Capability.getProperty("device"));
			cap.setPlatformName(Capability.getProperty("platformName"));
			cap.setPlatformVersion(Capability.getProperty("platformVersion"));
			cap.setApplicationName(Capability.getProperty("appName"));
		}

		return cap;
	}

}
