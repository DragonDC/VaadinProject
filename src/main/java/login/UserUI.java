package login;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import domain.Book;
import domain.User;
import service.BookManager;
import service.UserManager;
import validator.MyValidator;

@Theme("mytheme")
@Widgetset("Proj1.MyAppWidgetset")
public class UserUI extends UI {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "user";
	
	BookManager bookManager = new BookManager();
	MyValidator myValidator = new MyValidator();
	UserManager userManager = new UserManager();
	Book book = new Book();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	if(userManager.isLogged()){
    		int userId = userManager.getUserId();
    		User user = new User();
    		user = userManager.getUserInformation(userId);
    		
    		final VerticalLayout layout = new VerticalLayout();
            layout.setWidth("100%");
            layout.setSizeFull();
            layout.setMargin(true);
            layout.setSpacing(true);
            
            GridLayout grid = new GridLayout(6, 6);
    		grid.setWidth("100%");
    		Button logout = new Button("Logout");
    		Button addBook = new Button("Add new Book");
            Button userBooks = new Button("Your books");
            Button mainPage = new Button("Main Page");
            Label userInf = new Label("User Information");
            Label firstName = new Label("First name: " + user.getFirstName());
            Label lastName = new Label("Last name: " + user.getLastName());
            Label email = new Label("Email: " + user.getEmail());
            
            Link link = new Link("Logged as: " + userManager.getUsername(), new ExternalResource("http://localhost:8080/user"));
            grid.addComponent(mainPage, 0,0);
            grid.addComponent(userBooks, 1, 0);
	        grid.addComponent(addBook, 2, 0);
	        grid.addComponent(logout, 4, 0);
	        grid.addComponent(link,5,0);
	        grid.addComponent(userInf,3,2);
	        grid.addComponent(firstName,3,3);
	        grid.addComponent(lastName,3,4);
	        grid.addComponent(email,3,5);
	        	        
	        grid.setColumnExpandRatio(0, 0.05f);
	        grid.setColumnExpandRatio(1, 0.05f);
	        grid.setColumnExpandRatio(2, 0.05f);
	        grid.setColumnExpandRatio(3, 0.70f);
	        grid.setColumnExpandRatio(4, 0.05f);
	        grid.setColumnExpandRatio(5, 0.10f);


	        layout.addComponents(grid);
            setContent(layout);
            
            mainPage.addClickListener(new ClickListener() {

    			private static final long serialVersionUID = 1L;

    			@Override
    			public void buttonClick(ClickEvent event) {    
    				Page.getCurrent().setLocation("/mainpage");
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
    	else{
    		Page.getCurrent().setLocation("/mainpage");
    	}
    }

    @WebServlet(urlPatterns = {"/user/*"}, name = "UserServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = UserUI.class, productionMode = false)
    public static class UserServlet extends VaadinServlet {
    }

}