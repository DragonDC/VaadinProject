package domain;

import java.util.Date;
import java.util.List;

public class User {
	
	private int id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	private String password;
	private Date dateOfBirth;
	private Date dateOfRegistration;
	private Date dateOfLastLogin;
	private List<Book> books;
	
	public User(String firstName, String lastName, String username, String email, String password, Date dateOfBirth, 
				Date dateOfRegistration, Date dateOfLastLogin) {
		//super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.dateOfRegistration = dateOfRegistration;
		this.dateOfLastLogin = dateOfLastLogin;
	}

	public User() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getYob() {
		return getDateOfBirth();
	}

	public void setYob(Date dateOfBirth) {
		this.setDateOfBirth(dateOfBirth);
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDateOfLastLogin() {
		return dateOfLastLogin;
	}

	public void setDateOfLastLogin(Date dateOfLastLogin) {
		this.dateOfLastLogin = dateOfLastLogin;
	}

	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	public void setDateOfRegistration(Date dateOfRegistration) {
		this.dateOfRegistration = dateOfRegistration;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(java.util.Date date) {
		this.dateOfBirth = date;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
