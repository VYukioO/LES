package br.edu.fatec.les.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
		String driverName = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost/les?useTimezone=true&serverTimezone=UTC";
		String user = "root";
		String password = "root";
		
		try {
			Class.forName(driverName);
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException | ClassNotFoundException e) {
			return null;
		}
	}
	
	public static boolean closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.err.println("[ERRO] : closeConnection(Connection conn)");
				return false;
			}
		}
		return true;
	}
	
	public static void closeConnection(Connection conn, PreparedStatement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				System.err.println("[ERRO] : closeConnection(Connection conn, PreparedStatement stmt)");
			}
		}
		closeConnection(conn);
	}
	
	public static void closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				System.err.println("[ERRO] : closeConnection(Connection conn, PreparedStatement stmt, ResultSet rs)");
			}
		}
		closeConnection(conn, stmt);
	}

}
