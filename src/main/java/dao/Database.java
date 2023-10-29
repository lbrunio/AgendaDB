package dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {

	private static Connection connector = null;

	public static String typeDB = null;

	private Database() {
		try {
			Properties properties = new Properties();
			properties.load(new FileReader("properties.database.prop"));

			typeDB = properties.getProperty("db");
			String driver = properties.getProperty("driver");
			String dsn = properties.getProperty("dsn");
			String user = properties.getProperty("user", "");
			String pass = properties.getProperty("pass", "");

			Class.forName(driver);

			connector = DriverManager.getConnection(dsn, user, pass);

		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (connector == null)
			new Database();

		return connector;
	}

	public static void close() {
		if (connector != null) {
			try {
				connector.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
