package service;

import java.sql.*;

import connection.DBConnection;
import domain.User;
import hashGenerator.HashGenerator;

public class RegistrationManager {

	Connection conn = DBConnection.conn();
	
	public boolean checkUserRegistration(User user){
		String email = user.getEmail();
		String username = user.getUsername();
			
		int count = 0;
		try {
			Class.forName("org.postgresql.Driver");
			String sqlCommand = "SELECT COUNT(*) AS rowcount FROM account WHERE username = ? OR email = ? ";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, email);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if(count==0){
			registerUser(user);
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public void registerUser(User user){
		try {
			Class.forName("org.postgresql.Driver");
			String sqlCommand = "INSERT INTO account(first_name , last_name, username, password, email, date_of_birth , created_on) "
		               		  + "VALUES (?, ?, ?, ?, ?, ?, ?);";
			
			java.util.Date dateOfBirth = user.getDateOfBirth();
			java.sql.Date sqlDate = new java.sql.Date(dateOfBirth.getTime());		
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getUsername());
			preparedStatement.setString(4, HashGenerator.generateSHA1(user.getPassword()));
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setDate(6, sqlDate);
			preparedStatement.setDate(7, java.sql.Date.valueOf(java.time.LocalDate.now()));
			preparedStatement.executeUpdate();
			
			
			
			} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
