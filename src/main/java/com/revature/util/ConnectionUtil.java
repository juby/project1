package com.revature.util;

import java.io.*;
import java.sql.*;

public class ConnectionUtil {
	public static Connection getConnection() throws SQLException, IOException {
		/*
		 * TODO: Un-hardcode connection properties
		Properties prop = new Properties();
		InputStream in = new FileInputStream("connection.properties");
		prop.load(in);
		
		String url = prop.getProperty("url");
		String user = prop.getProperty("user");
		String password = prop.getProperty("password");
		*/

		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hotel", "p4ssw0rd");
	}
}