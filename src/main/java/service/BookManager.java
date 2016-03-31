package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.vaadin.server.VaadinSession;
import connection.DBConnection;
import domain.Book;

public class BookManager {
	Connection conn = DBConnection.conn();
	UserManager userManager = new UserManager();
	
	public void addBook(Book book){
			
		try {
			Class.forName("org.postgresql.Driver");
			String sqlCommand = "INSERT INTO book(title , author_first_name,  author_last_name, description, user_id) "
		               		  + "VALUES (?, ?, ?, ?, ?);";
				
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setString(1, book.getTitle());
			preparedStatement.setString(2, book.getAuthorFirstName());
			preparedStatement.setString(3, book.getAuthorLastName());
			preparedStatement.setString(4, book.getDescription());
			preparedStatement.setInt(5, userManager.getUserId());

			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public ArrayList<Book> getBookList(){
		ArrayList<Book> books = new ArrayList<Book>(); 
		try {
			Class.forName("org.postgresql.Driver");		
			String sqlCommand = "SELECT book_id, title, author_first_name, author_last_name, description  FROM book WHERE user_id=?";		
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, userManager.getUserId());
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()){
				Book book = new Book();
	            book.setId(rs.getInt("book_id"));
	            book.setTitle(rs.getString("title"));
	            book.setAuthorFirstName(rs.getString("author_first_name"));
	            book.setAuthorLastName(rs.getString("author_last_name"));
	            book.setDescription(rs.getString("description"));
	            books.add(book);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return books;
	}
	
	public void deleteBook(int id){
		try {
			Class.forName("org.postgresql.Driver");		
			String sqlCommand = "DELETE FROM book WHERE book_id = ?";		
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void updateBook(Book book){
		try {
			Class.forName("org.postgresql.Driver");		
			String sqlCommand = "UPDATE book SET title = ?, author_first_name = ?, author_last_name = ?, description = ?  WHERE book_id = ?";		
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setString(1,book.getTitle());
			preparedStatement.setString(2,book.getAuthorFirstName());
			preparedStatement.setString(3,book.getAuthorLastName());
			preparedStatement.setString(4,book.getDescription());
			preparedStatement.setInt(5,book.getId());
			preparedStatement.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Book getBook(int id){
		Book book = new Book();
		try {
			Class.forName("org.postgresql.Driver");		
			String sqlCommand = "SELECT book_id, title, author_first_name, author_last_name, description FROM book WHERE book_id=?";		
			PreparedStatement preparedStatement = conn.prepareStatement(sqlCommand);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			
			if(rs.next()){
	            book.setId(rs.getInt("book_id"));
	            book.setTitle(rs.getString("title"));
	            book.setAuthorFirstName(rs.getString("author_first_name"));
	            book.setAuthorLastName(rs.getString("author_last_name"));
	            book.setDescription(rs.getString("description"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return book;
	}
	
	public void setBookId(int id){
		VaadinSession.getCurrent().getSession().setAttribute("bookId", id);
	}
	
	public int getBookId(){
		int bookId = Integer.parseInt(String.valueOf(VaadinSession.getCurrent().getSession().getAttribute("bookId")));
		return bookId;
	}
	
	public void destroyBookIdSession(){
		VaadinSession.getCurrent().getSession().removeAttribute("bookId");
	}
	
}
