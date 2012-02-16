package key.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBLayer {

	private final static String DRIVER = "org.apache.derby.jdbc.ClientDriver";
	private final static String DB_URL = "jdbc:derby://localhost:1527/db;create=true";

	private final static String SQL_CREATE_SET = 
			"CREATE TABLE SETS (" 
			+ "ID BIGINT NOT NULL,"
			+ "DATE_CREATED TIMESTAMP," 
			+ "COMMENT VARCHAR(255)," 
			+ "DATE_FROM TIMESTAMP," 
			+ "DATE_TO TIMESTAMP," 
			+ "PRIMARY KEY (ID))";

	private final static String SQL_CREATE_KEY = 
			"CREATE TABLE KEYS ("
			+ "ID BIGINT NOT NULL," 
			+ "SET_ID BIGINT NOT NULL," 
			+ "HASH VARCHAR(16)," 
			+ "PRIMARY KEY (ID))";

	private static Connection connection;

	private DBLayer() {
	}

	public static final void create() {
		try {
			getConnection().createStatement().execute(SQL_CREATE_SET);
			getConnection().createStatement().execute(SQL_CREATE_KEY);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static final String save() {
		return null;
	}

	private static final Connection getConnection() {
		if (null == connection) {
			try {
				Class.forName(DRIVER).newInstance();
				connection = DriverManager.getConnection(DB_URL, new Properties());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return connection;
	}

}
