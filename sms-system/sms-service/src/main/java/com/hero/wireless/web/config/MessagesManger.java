package com.hero.wireless.web.config;

import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.math.NumberUtils;

import com.drondea.wireless.util.SuperLogger;

public abstract class MessagesManger {
	private static ResourceBundle exceptionRb = getResourceBundle("ExceptionMessages");
	private static ResourceBundle systemRb = getResourceBundle("SystemMessages");

	private static ResourceBundle getResourceBundle(String resourceName) {
		ClassLoader loader = null;
		try {
			URL url = new URL(Thread.currentThread().getContextClassLoader().getResource("").toString());
			URL[] urls = { url };
			loader = new URLClassLoader(urls);
			ResourceBundle bundle = ResourceBundle.getBundle(resourceName,
					Locale.getDefault(), loader);
			return bundle;
		} catch (Exception ex) {
			SuperLogger.error(ex.getMessage(), ex);
		}
		return null;
	}

	public static void init() {
		SuperLogger.debug("初始化MessagesManger");
	}

	public static String getExceptionMessages(String key) {
		return getString(exceptionRb, key);
	}

	public static String getExceptionMessages(String key, Object... parm) {
		return getString(exceptionRb, key, parm);
	}

	public static String getSystemMessages(String key) {
		return getString(systemRb, key);
	}

	public static String getSystemMessages(String key, Object... parm) {
		return getString(systemRb, key, parm);
	}

	public static String getSystemMessages(SystemKey key) {
		return getString(systemRb, key.toString());
	}

	public static String getSystemMessages(SystemKey key, Object... parm) {
		return getString(systemRb, key.toString(), parm);
	}

	public static Integer getSystemMessagesInt(SystemKey key) {
		return getInt(systemRb, key.toString());
	}

	public static Integer getInt(ResourceBundle resourceBundle, String key) {
		try {
			return NumberUtils.createInteger(resourceBundle.getString(key));
		} catch (MissingResourceException e) {
			return null;
		}
	}

	public static String getString(ResourceBundle resourceBundle, String key) {
		try {
			return resourceBundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	public static String getString(ResourceBundle resourceBundle, String key,
			Object... parm) {
		try {
			return MessageFormat.format(resourceBundle.getString(key), parm);
		} catch (MissingResourceException e) {
			return key;
		}
	}
}
