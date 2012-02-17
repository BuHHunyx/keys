package key.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class KeyProperties {

	private final static String FILENAME = "key-client.properties";

	private final static String DB_URL = "db.url";
	private final static String DB_USERNAME = "db.username";
	private final static String DB_PASSWORD = "db.password";

	private final static Properties properties = new Properties();

	private KeyProperties() {
	}

	public static final String getDbUrl() {
		return getProperties().getProperty(DB_URL);
	}

	public static final String getDbUsername() {
		return getProperties().getProperty(DB_USERNAME);
	}

	public static final String getDbPassword() {
		return getProperties().getProperty(DB_PASSWORD);
	}

	public static final void saveDbProperties(String dbUrl, String dbUsername, String dbPassword) {
		getProperties().setProperty(DB_URL, dbUrl);
		getProperties().setProperty(DB_USERNAME, dbUsername);
		getProperties().setProperty(DB_PASSWORD, dbPassword);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(FILENAME); 
			getProperties().store(fos, null);
		} catch (IOException e) {
			throw new KeyException("Ошибка сохранения настроек", e);
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private static final Properties getProperties() {
		if (properties.size() == 0) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(FILENAME); 
				properties.load(fis);
			} catch (IOException e) {
				throw new KeyException("Ошибка загрузки настроек", e);
			} finally {
				if (null != fis) {
					try {
						fis.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return properties;
	}
}
