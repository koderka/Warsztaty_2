package pl.koderka.Programming_School;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class UserMain {

	public static void main(String[] args) {
		
		User user1 = new User("Alicja Makota", "alamakota@gmail.com", "kotmaale");
		User[] users = new User[0];
	
		DbConnection connection1 = new DbConnection();
		
//		try {
//			user1.saveToDB(connection1.getConnection());
//			
//			user1 = User.loadUserById(connection1.getConnection(), 1);
//			System.out.println(user1);
//			user1.delete(connection1.getConnection());
//			
//			users = User.loadAllUsers(connection1.getConnection());
//			System.out.println(Arrays.toString(users));
//		} 
//		catch (SQLException e) {	
//			e.printStackTrace();
//		}	
		
//		UserGroup userGroup1 = new UserGroup("Java");
//		UserGroup[] userGroups = new UserGroup[0];
//		
//		try {
//			userGroup1.saveToDB(connection1.getConnection());
//			userGroups = UserGroup.loadAllUserGroups(connection1.getConnection());
//			System.out.println(Arrays.toString(userGroups));
//		}
//		catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		Date date1 = Date.valueOf("2017-11-04");
		Date date2 = Date.valueOf("2017-11-20");

		
		Solution solution1 = new Solution(date1, date2, "Zadanie z wyjątków", 1, 1);
		Solution[] solutions = new Solution[0];
		
		try {
			solution1.saveToDB(connection1.getConnection());
			solutions = Solution.loadAllSolutions(connection1.getConnection());
			System.out.println(Arrays.toString(solutions));
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
