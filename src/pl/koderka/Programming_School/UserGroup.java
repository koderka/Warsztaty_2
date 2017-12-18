package pl.koderka.Programming_School;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserGroup {
	
	private	int	id;
	private	String name;
	
	public UserGroup() {
	}
	public UserGroup(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String toString() {
		return "Dane grupy użytkowników - id: " + this.id + ", nazwa: " + this.name;		
	}
	public void saveToDB(Connection connection) throws SQLException {
		if (this.id	== 0) {
			String sql = "INSERT INTO user_group (name) VALUES (?)";
			String generatedColumns[] =	{ "ID" };
			PreparedStatement preparedStatement;
			preparedStatement =	connection.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.name);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())	{
					this.id	= rs.getInt(1);
				}
		}
		else {
			String sql = "UPDATE user_group SET name = ? WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, this.name);
			preparedStatement.setInt(2, this.id);
			preparedStatement.executeUpdate();
		}
	}
	static public UserGroup loadUserGroupById(Connection connection, int id) throws SQLException {
		String sql = "SELECT * FROM user_group WHERE id=?";
		PreparedStatement preparedStatement;
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			UserGroup loadedUserGroup = new UserGroup();
			loadedUserGroup.id = resultSet.getInt("id");
			loadedUserGroup.name = resultSet.getString("name");
			return loadedUserGroup;
		}
		return null;
	}
	static public UserGroup[] loadAllUserGroups(Connection connection) throws SQLException {
		ArrayList<UserGroup> userGroups = new ArrayList<UserGroup>();
		String sql = "SELECT * FROM	user_group";
		PreparedStatement preparedStatement;
		preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			UserGroup loadedUserGroup = new UserGroup();
			loadedUserGroup.id = resultSet.getInt("id");
			loadedUserGroup.name = resultSet.getString("name");
			userGroups.add(loadedUserGroup);
		}
		UserGroup[] ugArray = new UserGroup[userGroups.size()];
		ugArray = userGroups.toArray(ugArray);
		return ugArray;
	}
	public void delete(Connection connection) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM user_group WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

}
