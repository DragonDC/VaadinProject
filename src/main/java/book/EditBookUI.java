package book;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.Reindeer;

import domain.Book;
import service.BookManager;
import service.UserManager;
import validator.MyValidator;


@Theme("mytheme")
@Widgetset("Proj1.MyAppWidgetset")
public class EditBookUI extends UI {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "editbook";
	
	BookManager bookManager = new BookManager();
	MyValidator myValidator = new MyValidator();
	UserManager userManager = new UserManager();
	
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	if(userManager.isLogged() && bookManager.getBookId() != 0){
    		int bookId = bookManager.getBookId();
    		Book book = bookManager.getBook(bookId);
    		BeanItem<Book> item = new BeanItem<Book>(book);
    		bookManager.setBookId(0);
	    	FormLayout form = new FormLayout();
			form.setSpacing(true);
			form.setMargin(new MarginInfo(true, true, true, false));
			form.setSizeUndefined();
			
			GridLayout grid2 = new GridLayout(6, 6);
    		grid2.setWidth("100%");
    		Button logout = new Button("Logout");
    		Button addBook = new Button("Add new Book");
            Button userBooks = new Button("Your books");
            Button mainPage = new Button("Main Page");
            Link link = new Link("Logged as: " + userManager.getUsername(), new ExternalResource("http://localhost:8080/user"));
            grid2.addComponent(mainPage, 0,0);
            grid2.addComponent(userBooks, 1, 0);
	        grid2.addComponent(addBook, 2, 0);
	        grid2.addComponent(logout, 4, 0);
	        grid2.addComponent(link,5,0);
	        
	        grid2.setColumnExpandRatio(0, 0.05f);
	        grid2.setColumnExpandRatio(1, 0.05f);
	        grid2.setColumnExpandRatio(2, 0.05f);
	        grid2.setColumnExpandRatio(3, 0.70f);
	        grid2.setColumnExpandRatio(4, 0.05f);
	        grid2.setColumnExpandRatio(5, 0.10f);
	        grid2.setColumnExpandRatio(5, 0.10f);
	    	
			GridLayout grid = new GridLayout(1, 6);
			grid.setWidth("500px");
			grid.setHeight("700px");
			
			Label labelHeadAddBook = new Label();
			labelHeadAddBook.setValue("Edit book");
			labelHeadAddBook.setWidth(null);
			labelHeadAddBook.addStyleName("labelHeadAddBook");
			
	        TextField title = new TextField("Title");
	        title.addValidator(myValidator.new RequiredTextValidator());
	        title.setImmediate(true);
	        title.setWidth("500px");
	        title.setValue(item.getBean().getTitle());
	
	        TextField authorFirstName = new TextField("Author first name");
	        authorFirstName.addValidator(myValidator.new TextFirstNameValidator());
	        authorFirstName.setImmediate(true);
	        authorFirstName.setWidth("500px");
	        authorFirstName.setValue(item.getBean().getAuthorFirstName());
	        
	        TextField authorLastName = new TextField("Author last name");
	        authorLastName.addValidator(myValidator.new TextFirstNameValidator());
	        authorLastName.setImmediate(true);
	        authorLastName.setWidth("500px");
	        authorLastName.setValue(item.getBean().getAuthorLastName());
	
	        TextArea description = new TextArea("Description");
	        description.setImmediate(true);
	        description.setHeight("300px");
	        description.setWidth("500px");
	        description.setValue(item.getBean().getDescription());
	        
	        Button saveBtn = new Button("Zapisz");
	        saveBtn.setWidth("250px");
	        
	        grid.addComponent(labelHeadAddBook, 0, 0);
	        grid.addComponent(title, 0, 1);
	        grid.addComponent(authorFirstName, 0, 2);
	        grid.addComponent(authorLastName, 0, 3);
	        grid.addComponent(description, 0, 4);
	        grid.addComponent(saveBtn, 0, 5);
	       
	        grid.setComponentAlignment(labelHeadAddBook, Alignment.MIDDLE_CENTER);
	        grid.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
	        grid.setComponentAlignment(authorFirstName, Alignment.MIDDLE_CENTER);
	        grid.setComponentAlignment(authorLastName, Alignment.MIDDLE_CENTER);
	        grid.setComponentAlignment(description, Alignment.MIDDLE_CENTER);
	        grid.setComponentAlignment(saveBtn, Alignment.MIDDLE_CENTER);
	        
	        form.addComponents(grid);
	        
	        grid2.addComponent(form,0,2,5,5);
	        grid2.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
	        
	        VerticalLayout viewLayout = new VerticalLayout();
			viewLayout.setSizeFull();
			viewLayout.addComponent(grid2);
			viewLayout.setComponentAlignment(grid2, Alignment.MIDDLE_CENTER);
			viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
			viewLayout.setMargin(true);
			viewLayout.setSpacing(true);
			
			setContent(viewLayout);
			
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
			
			saveBtn.addClickListener(new ClickListener() {
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void buttonClick(ClickEvent event) {
					try {
			        	title.validate();
			        	authorFirstName.validate();
			        	authorLastName.validate();
			        	description.validate();
						
						book.setTitle(title.getValue());
						book.setAuthorFirstName(authorFirstName.getValue());
						book.setAuthorLastName(authorLastName.getValue());
						book.setDescription(description.getValue());
						bookManager.updateBook(book);
						Notification.show("Added new book", Notification.Type.TRAY_NOTIFICATION);	
						Page.getCurrent().setLocation("/userbooks");
		
			        } catch (InvalidValueException e) {
			        	Notification.show("There are mistakes in form!", Notification.Type.WARNING_MESSAGE);
		            }
			        
			        
				}
			});
    	}
    	else{
    		Page.getCurrent().setLocation("/mainpage");
    	}
    }

    @WebServlet(urlPatterns = {"/editbook/*"}, name = "EditBookServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = EditBookUI.class, productionMode = false)
    public static class EditBookServlet extends VaadinServlet {
    }
}
