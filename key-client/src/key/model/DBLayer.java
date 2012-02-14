package key.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class DBLayer {

	private final static String DRIVER = "org.apache.derby.jdbc.ClientDriver";
	private final static String DB_URL = "jdbc:derby://localhost:1527/db;create=true";
	
	private DBLayer() {
	}

	public static final String save() {
		try {
			Class.forName(DRIVER).newInstance();
			Connection conn = DriverManager.getConnection(DB_URL, new Properties());
			System.out.println(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
