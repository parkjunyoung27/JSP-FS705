package com.FS705.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static Connection dbconn() {
		Connection conn = null;
		String url = "jdbc:mariadb://220.70.33.29:3306/hpaaycim2";
		try {
			
		Class.forName("org.mariadb.jdbc.Driver");
		conn = DriverManager.getConnection(url, "hpaaycim2","01234567");
		}catch(Exception e ) {
			e.printStackTrace();
		}
		return conn;
		
	}
	
}
