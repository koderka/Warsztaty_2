package pl.koderka.Programming_School;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	
	private Connection dbConnection = null;
	
	public DbConnection() {
		
		try {
			this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/programming_school?useSSL=false", "root", "coderslab");	
		} 
		catch(SQLException e) {	
			e.printStackTrace(); 
		}
	}
	
	public Connection getConnection() {
		return this.dbConnection;
	}

	public void closeConnection() {

		if(this.dbConnection != null) {
			try {
				this.dbConnection.close();
			} 
			catch (SQLException e) { 
				e.printStackTrace(); 
			}
		}
	}
}
