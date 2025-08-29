package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	private final String serverName = "ADMIN-PC";
	private final String dbName = "23110198_BuiNhatDuong_BTT2";
	private final String portNumber = "1433";
	private final String instance = "";
	private final String userID = "sa";
	private final String password = "1";

	public Connection getConnection() throws Exception {
		String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true";
;
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url, userID, password);
	}
}
