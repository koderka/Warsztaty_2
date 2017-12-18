package pl.koderka.Programming_School;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Exercise {
	private	int	id;
	private	String title;
	private	String description;
	
	public Exercise() {
	}
	public Exercise(String title, String description) {
		this.title = title;
		this.description = description;
	}
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String toString() {
		return "Zadanie - id: " + this.id + ", tytuł: " 
		+ this.title + ", opis: " + this.description;
	}
	public void saveToDB(Connection connection) throws SQLException {
		if (this.id	== 0) {
			String sql = "INSERT INTO exercise(title, description) VALUES (?, ?)";
			String generatedColumns[] =	{ "ID" };
			PreparedStatement preparedStatement;
			preparedStatement =	connection.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next())	{
					this.id	= rs.getInt(1);
				}
		}
		else {
			String sql = "UPDATE exercise SET title=?, description=? WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, this.title);
			preparedStatement.setString(2, this.description);
			preparedStatement.setInt(3, this.id);
			preparedStatement.executeUpdate();
		}
	}
	static public Exercise loadExerciseById(Connection connection, int id) throws SQLException {
		String sql = "SELECT * FROM exercise WHERE id=?";
		PreparedStatement preparedStatement;
		preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title = resultSet.getString("title");
			loadedExercise.description = resultSet.getString("description");
			return loadedExercise;
		}
		return null;
	}
	static public Exercise[] loadAllExercises(Connection connection) throws SQLException {
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
		String sql = "SELECT * FROM	exercise";
		PreparedStatement preparedStatement;
		preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Exercise loadedExercise = new Exercise();
			loadedExercise.id = resultSet.getInt("id");
			loadedExercise.title = resultSet.getString("title");
			loadedExercise.description = resultSet.getString("description");
			exercises.add(loadedExercise);
		}
		Exercise[] eArray = new Exercise[exercises.size()];
		eArray = exercises.toArray(eArray);
		return eArray;
	}
	public void delete(Connection connection) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM exercise WHERE id = ?";
			PreparedStatement preparedStatement;
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
}