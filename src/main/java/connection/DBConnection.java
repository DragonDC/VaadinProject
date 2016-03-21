package connection;

import java.sql.*;

import com.vaadin.ui.Notification;

public class DBConnection {
	static String driver = "org.postgresql.Driver";
	static String url = "jdbc:postgresql://localhost:5432/VaadinProject";
	static String user   = "postgres";
	static String password = "damianoss";
	
	public static Connection conn(){
		
		try{
			Class.forName(driver);
		} catch (ClassNotFoundException e){
			System.out.println("Error: unable to load class!");
			e.printStackTrace();
		} 

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			Notification.show("Problem with connection. Try refresh the page", Notification.Type.WARNING_MESSAGE);
			System.out.println("Error in connection with Databse!");
			e.printStackTrace();
		}
		
		return connection;
	}
}
