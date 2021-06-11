package com.hero.wireless.web.config;

import com.drondea.wireless.util.SuperLogger;
import org.apache.commons.lang.math.NumberUtils;

import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author liuyanning
 */
public class CassandraProperties {

	private static ResourceBundle  CASSANDRA_RB = getResourceBundle("cassandra");

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


	public static String getUserName() {
		return getString(CASSANDRA_RB, "user-name");
	}

	public static String getUserPassword() {
		return getString(CASSANDRA_RB, "user-password");
	}

	public static String getContactPoints() {
		return getString(CASSANDRA_RB, "contact-points");
	}

	public static Integer getConnectTimeout() {
		return getInt(CASSANDRA_RB, "connect-timeout");
	}


	public static Integer getReadTimeout() {
		return getInt(CASSANDRA_RB, "read-timeout");
	}

	public static Integer getPoolQueueSize() {
		return getInt(CASSANDRA_RB, "pool-queue-size");
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
