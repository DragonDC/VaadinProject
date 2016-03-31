package mainPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.Statement;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import connection.DBConnection;
import service.BookManager;
import service.UserManager;

/**
 *
 */
@Theme("mytheme")
@Widgetset("Proj1.MyAppWidgetset")
public class MainPageUI extends UI {
	private static final long serialVersionUID = 1L;

	UserManager userManager = new UserManager();
	BookManager bookManager = new BookManager();
	
	public static final String NAME = "mainpage";
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        GridLayout grid = new GridLayout(6, 6);
		grid.setWidth("100%");
		grid.setHeight("900px");
        
        Button login = new Button("Login");
        Button registration = new Button("Registration");
        Button logout = new Button("Logout");
        Button addBook = new Button("Add new Book");
        Button userBooks = new Button("Your books");
        Button mainPage = new Button("Main Page");
        Link link = new Link("Logged as: " + userManager.getUsername(), new ExternalResource("http://localhost:8080/user"));
        Label mainContent = new Label("Aplikacja przeznaczona dla osób lubiących dużo czytać i nie pamiętających co już do tej pory przeczytały! D:");
        Image image = new Image();
        image.setWidth("400px");
        image.setHeight("400px");
        
        image.setSource(new ExternalResource("http://wegemaluch.pl/images/wm_kolorowanki/molumen_Old_book.png"));
        
        if(userManager.isLogged()){
        	grid.addComponent(mainPage, 0,0);
            grid.addComponent(userBooks, 1, 0);
	        grid.addComponent(addBook, 2, 0);
	        grid.addComponent(logout, 4, 0);
	        grid.addComponent(link,5,0);
	        grid.addComponent(mainContent, 3,1);
	        grid.addComponent(image, 3, 2);
	        grid.setColumnExpandRatio(0, 0.05f);
	        grid.setColumnExpandRatio(1, 0.05f);
	        grid.setColumnExpandRatio(2, 0.05f);
	        grid.setColumnExpandRatio(3, 0.70f);
	        grid.setColumnExpandRatio(4, 0.05f);
	        grid.setColumnExpandRatio(5, 0.15f);
	        grid.setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);
	        grid.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        }
        else{
        	grid.addComponent(mainPage, 0,0);
	        grid.addComponent(login, 4, 0);
	        grid.addComponent(registration, 5, 0);
	        grid.addComponent(mainContent, 3,1);
	        grid.addComponent(image, 3, 2);
	        grid.setColumnExpandRatio(0, 0.25f);
	        grid.setColumnExpandRatio(1, 0.05f);
	        grid.setColumnExpandRatio(2, 0.05f);
	        grid.setColumnExpandRatio(3, 0.70f);
	        grid.setColumnExpandRatio(4, 0.05f);
	        grid.setColumnExpandRatio(5, 0.10f);
	        grid.setComponentAlignment(mainContent, Alignment.MIDDLE_CENTER);
	        grid.setComponentAlignment(image, Alignment.MIDDLE_CENTER);
        }
        
        TextField text = new TextField();
        if(userManager.isLogged() && VaadinSession.getCurrent().getSession().getAttribute("bookId")!=null)
        	text.setValue(Integer.toString(bookManager.getBookId()));
        	//text.setValue((String) VaadinSession.getCurrent().getSession().getAttribute("bookId"));
        
        layout.addComponent(grid);
        layout.setMargin(true);
        layout.setSpacing(true);
		layout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);     
        //layout.addComponents(text, destroyBookId);
		
        setContent(layout);
        
        mainPage.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {    
				Page.getCurrent().setLocation("/mainpage");
			}
		});
                     
        login.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {    
				Page.getCurrent().setLocation("/login");
				       
			}
		});
        
        registration.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {    
				Page.getCurrent().setLocation("/registration");
				       
			}
		});
        
        logout.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {    
				userManager.logoutUser();
				Page.getCurrent().setLocation("/mainpage");
			}
		});
        
        addBook.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {    
				Page.getCurrent().setLocation("/addbook");
			}
		});
        
        userBooks.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {    
				Page.getCurrent().setLocation("/userbooks");
			}
		});
        
    }
    

    @WebServlet(urlPatterns = {"/mainpage/*", "/VAADIN/*"}, name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MainPageUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
