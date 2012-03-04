package key.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class KeyProperties {

	private final static String FILENAME = "key-client.properties";

	private final static String DB_HOST = "db.host";
	private final static String DB_USERNAME = "db.username";
	private final static String DB_PASSWORD = "db.password";
	private final static String DB_INSTANCE = "db.instance";
	private final static String DB_DATABASE = "db.database";

	private final static Properties properties = new Properties();

	private KeyProperties() {
	}

	public static final String getDbHost() {
		return getProperties().getProperty(DB_HOST);
	}

	public static final String getDbUsername() {
		return getProperties().getProperty(DB_USERNAME);
	}

	public static final String getDbPassword() {
		return getProperties().getProperty(DB_PASSWORD);
	}

	public static final String getDbInstance() {
		return getProperties().getProperty(DB_INSTANCE);
	}

	public static final String getDbDatabase() {
		return getProperties().getProperty(DB_DATABASE);
	}

	public static final void saveDbProperties(String dbHost, String dbUsername, String dbPassword, String dbInstance, String dbDatabase) {
		getProperties().setProperty(DB_HOST, dbHost);
		getProperties().setProperty(DB_USERNAME, dbUsername);
		getProperties().setProperty(DB_PASSWORD, dbPassword);
		getProperties().setProperty(DB_INSTANCE, dbInstance);
		getProperties().setProperty(DB_DATABASE, dbDatabase);
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
