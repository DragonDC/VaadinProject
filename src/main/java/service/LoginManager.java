package service;

import java.sql.*;

import connection.DBConnection;
import domain.User;
import hashGenerator.HashGenerator;

public class LoginManager {

	Connection conn = DBConnection.conn();
	
	public boolean checkUserLogin(String username, String password){		
		int count = 0;
		try {
			String sqlCommand = "SELECT COUNT(*) AS rowcount FROM account WHERE username = ? AND password = ? ";
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, HashGenerator.generateSHA1(password));
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			count = rs.getInt("rowcount");
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		if(count==0)
			return false;
		else
			return true;
		
	}
	
}
