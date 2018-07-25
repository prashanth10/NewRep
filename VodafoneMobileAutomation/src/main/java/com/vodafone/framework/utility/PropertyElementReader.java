package com.vodafone.framework.utility;

import static org.testng.Assert.fail;

import java.util.Properties;

import org.testng.Reporter;

public class PropertyElementReader {

	private static PropertyElementReader _instance;
	private static Properties baseElements = new Properties();
	public static PropertyElementReader getInstance() {
		if (_instance == null) {
			_instance = new PropertyElementReader();
		}
		return _instance;
	}

	private PropertyElementReader() {
		loadPropertyElements();
	}

	public String getPropertyElement(String key) {
		if ((baseElements != null) && (baseElements.containsKey(key))) {
			return baseElements.getProperty(key);
		}

		fail("Could not find property element for key '" + key + "'");
		return null;
	}

	private void loadPropertyElements() {
		try {
			baseElements.load(getClass().getResourceAsStream("/elements/baseElements.properties"));
		} catch (Exception e) {
			Reporter.log("Could not load base element file.", 3);
		}
	}
}