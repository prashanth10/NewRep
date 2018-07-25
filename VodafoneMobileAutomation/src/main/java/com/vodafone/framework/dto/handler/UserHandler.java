package com.vodafone.framework.dto.handler;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.vodafone.framework.dto.UserObj;

public class UserHandler {
	private static UserHandler _instance;
	private static InputStream in;

	private static final String FILEPATH_PROD = "src/test/resources/usr.properties";
	private static Properties users = new Properties();
	private static Map<String, UserObj> usersMap = null;
	private static String myenv = "PROD";

	public static UserHandler getInstance() {
		myenv = "PROD";
		if (_instance == null) {
			_instance = new UserHandler();
		}
		return _instance;
	}

	private UserHandler() {
		loadUserProperties();
	}

	private void loadUserProperties() {
		try {
			if (myenv.equals("PROD")) {
				in = new FileInputStream(System.getProperty("user.dir") + "/" + FILEPATH_PROD);
			} 
			users.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, UserObj> getUserMap() {
		if (usersMap == null) {
			usersMap = new HashMap<String, UserObj>();
			UserObj user = null;
			for (String key : users.stringPropertyNames()) {
				String value = users.getProperty(key);

				String[] credentials = value.split(",");
				user = new UserObj();
				user.setUserName(credentials[0]);
				user.setPassword(credentials[1]);
				usersMap.put(key, user);
			}
		}
		return usersMap;
	}
}
