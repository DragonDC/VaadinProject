package login;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Item;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;

import book.EditBookUI;
import domain.Book;
import service.BookManager;
import service.UserManager;
import validator.MyValidator;
import validator.MyValidator.TextFirstNameValidator;

@Theme("mytheme")
@Widgetset("Proj1.MyAppWidgetset")
public class UserBooksUI extends UI {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "addbook";
	
	BookManager bookManager = new BookManager();
	MyValidator myValidator = new MyValidator();
	UserManager userManager = new UserManager();
	Book book = new Book();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	if(userManager.isLogged()){
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
            Link link = new Link("Logged as: " + userManager.getUsername(), new ExternalResource("http://localhost:8080/user"));
            grid.addComponent(mainPage, 0,0);
            grid.addComponent(userBooks, 1, 0);
	        grid.addComponent(addBook, 2, 0);
	        grid.addComponent(logout, 4, 0);
	        grid.addComponent(link,5,0);
	        
	        grid.setColumnExpandRatio(0, 0.05f);
	        grid.setColumnExpandRatio(1, 0.05f);
	        grid.setColumnExpandRatio(2, 0.05f);
	        grid.setColumnExpandRatio(3, 0.70f);
	        grid.setColumnExpandRatio(4, 0.05f);
	        grid.setColumnExpandRatio(5, 0.10f);


            ArrayList<Book> books = new ArrayList<Book>();
            books = bookManager.getBookList();
            
            Table table = new Table("Your books");
            IndexedContainer container1 = new IndexedContainer();
            container1.addContainerProperty("Nr.", Integer.class, "");
            container1.addContainerProperty("Title", String.class, "");
            container1.addContainerProperty("Author first name", String.class, "");
            container1.addContainerProperty("Author last name", String.class, "");
            container1.addContainerProperty("Book description", String.class, "");
            container1.addContainerProperty("Edit", Button.class, "");
            container1.addContainerProperty("Delete", Button.class, "");
            int i=1;
            for (Book book : books) {
                Item item = container1.addItem(book);
                Button edit = new Button("Edit");
                Button delete = new Button("Delete");
                item.getItemProperty("Nr.").setValue(i);
                item.getItemProperty("Title").setValue(book.getTitle());
                item.getItemProperty("Author first name").setValue(book.getAuthorFirstName());
                item.getItemProperty("Author last name").setValue(book.getAuthorLastName());
                item.getItemProperty("Book description").setValue(book.getDescription());
                item.getItemProperty("Edit").setValue(edit);
                item.getItemProperty("Delete").setValue(delete);
                i++;
                
                edit.addClickListener(new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        int bookId = book.getId();
                        bookManager.setBookId(bookId);
                        Page.getCurrent().setLocation("/editbook");         				      		
                    }
                });
                
                delete.addClickListener(new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
//                    	int id = book.getId();
//                    	bookManager.deleteBook(id);
//                    	init(vaadinRequest);
                    	

                        Window subWindow = new Window("Warning");
                        subWindow.setHeight("200px");
                        subWindow.setWidth("200px");
                        VerticalLayout subContent = new VerticalLayout();
                        subContent.setMargin(true);
                        subWindow.setContent(subContent);
                        
                        Button yes = new Button("Yes");
                        Button no = new Button("No");
                        subContent.addComponent(new Label("Are you sure?"));
                        subContent.addComponent(yes);
                        subContent.addComponent(no);
                        subWindow.center();
                        addWindow(subWindow);
                        yes.addClickListener(new Button.ClickListener() {
                            public void buttonClick(ClickEvent event) {        
                            	int id = book.getId();
                            	bookManager.deleteBook(id);
                            	init(vaadinRequest);  
                            	subWindow.close();
                            }
                        });
                        
                        no.addClickListener(new Button.ClickListener() {
                            public void buttonClick(ClickEvent event) {
                            	subWindow.close();      				      		
                            }
                        });
                        
                        
                    }
                });
                
            }
            table.setContainerDataSource(container1);
            table.setColumnWidth("Book description", 200);

//            Table table2 = new Table("Table 2, automation with BeanItemContainer");
//            BeanItemContainer<Book> container2 = new BeanItemContainer<Book>(books);
//            table2.setContainerDataSource(container2);
            grid.addComponent(table,0,2,5,5);
            grid.setComponentAlignment(table, Alignment.MIDDLE_CENTER);
            
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

    @WebServlet(urlPatterns = {"/userbooks/*"}, name = "UserBooksServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = UserBooksUI.class, productionMode = false)
    public static class UserBooksServlet extends VaadinServlet {
    }

}