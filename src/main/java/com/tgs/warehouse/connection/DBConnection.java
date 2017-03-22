package com.tgs.warehouse.connection;


import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

	
	public static Connection openConnection(){
		
		Connection connection = null;
		
		try {
			connection = ConnectionPool.getDBCP2Connection();
			System.out.println("Connected with Connection Pool");
		} catch (SQLException e) {

			System.out.println("Cannot connect to DB");
			e.printStackTrace();
			return null;
		}
		
		return connection;	
	}
	  
	
	public static void closeConnection(Connection connection){
		
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("Cannot close connection...");
				e.printStackTrace();
			}
			System.out.println("Connection closed");
		}
	}
	
	
}

