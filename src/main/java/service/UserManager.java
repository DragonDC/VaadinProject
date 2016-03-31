package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;

import connection.DBConnection;
import domain.Book;
import domain.User;

public class UserManager {
	Connection conn = DBConnection.conn();
	
	public boolean isLogged(){
		if(VaadinSession.getCurrent().getSession().getAttribute("user") != null)
        	return true;
    	else
    		return false;
    }
	
	public String getUsername(){		
		if(isLogged()){
			String username = String.valueOf(VaadinSession.getCurrent().getSession().getAttribute("user"));
        	return username;
		}
    	else{
    		return null;
    	}
	}
	
	public void logUser(String username){
		VaadinSession.getCurrent().getSession().setAttribute("user", username);
	}
	
	public void logoutUser(){
		VaadinService.getCurrentRequest().getWrappedSession().invalidate(); 
	}
	
	public void setUserId(){
		try {
			Class.forName("org.postgresql.Driver");
			String sqlCommand = "SELECT user_id FROM account WHERE username=?";
			String value = null;
				
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setString(1, getUsername());
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()){
                value = rs.getString(1);;
			}
			VaadinSession.getCurrent().getSession().setAttribute("userId", value);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public User getUserInformation(int id){
		User user = new User();
		try {
			Class.forName("org.postgresql.Driver");		
			String sqlCommand = "SELECT first_name, last_name, email FROM account WHERE user_id=?";		
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()){
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public int getUserId(){
		int userId = Integer.parseInt(String.valueOf(VaadinSession.getCurrent().getSession().getAttribute("userId")));
		return userId;
	}
	
}
