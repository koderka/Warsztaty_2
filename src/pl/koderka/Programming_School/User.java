package pl.koderka.Programming_School;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class User {
	
	private	int	id;
	private	String username;
	private	String password;
	private	String email;
	
	public	User() {
	}
	public User(String username, String email, String password)	{
		this.username = username;
		this.email = email;
		this.setPassword(password);
	}
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password,	BCrypt.gensalt());;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String toString() {
		return "Dane użytkownika o id: " + this.id + ", imię i nazwisko: " 
		+ this.username + ", hasło: " + this.password + ", e-mail: " + this.email;		
	}
	public void saveToDB(Connection connection) throws SQLException {
		if (this.id	== 0) {
			String sql = "INSERT INTO user(username, password, email) VALUES (?, ?, ?)";
			String generatedColumns[] =	{ "ID" };
			PreparedStatement preparedStatement;
			preparedStatement =	connection.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.password);
			preparedStatement.setString(3, this.email);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())	{
					this.id	= rs.getInt(1);
				}
		}
		else {
			String sql = "UPDATE user SET username=?, password=?, email=? WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.password);
			preparedStatement.setString(3, this.email);
			preparedStatement.setInt(4, this.id);
			preparedStatement.executeUpdate();
		}
	}
	static public User loadUserById(Connection connection, int id) throws SQLException {
		String sql = "SELECT * FROM user WHERE id=?";
		PreparedStatement preparedStatement;
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			return loadedUser;
		}
		return null;
	}
	static public User[] loadAllUsers(Connection connection) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM	user";
		PreparedStatement preparedStatement;
		preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.email = resultSet.getString("email");
			loadedUser.password = resultSet.getString("password");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}
	public void delete(Connection connection) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM user WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
}

