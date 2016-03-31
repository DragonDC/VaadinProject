package login;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import service.BookManager;
import service.LoginManager;
import service.UserManager;


@Theme("mytheme")
@Widgetset("Proj1.MyAppWidgetset")
public class LoginUI extends UI {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "login";
	UserManager userManager = new UserManager();
	LoginManager loginManager = new LoginManager();
	BookManager bookManager = new BookManager();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
		if(!userManager.isLogged()){
			
			GridLayout grid = new GridLayout(6, 6);
			grid.setWidth("100%");
			
			Button login = new Button("Login");
	        Button registration = new Button("Registration");
	        Button mainPage = new Button("Main Page");
			
			grid.addComponent(mainPage, 0,0);
	        grid.addComponent(login, 4, 0);
	        grid.addComponent(registration, 5, 0);
	        grid.setColumnExpandRatio(0, 0.05f);
	        grid.setColumnExpandRatio(1, 0.05f);
	        grid.setColumnExpandRatio(2, 0.05f);
	        grid.setColumnExpandRatio(3, 0.70f);
	        grid.setColumnExpandRatio(4, 0.05f);
	        grid.setColumnExpandRatio(5, 0.10f);
			
			
			FormLayout form = new FormLayout();
			form.setCaption("Login");
			form.setSpacing(true);
			form.setMargin(new MarginInfo(true, true, true, false));
			form.setSizeUndefined();
	        
	        FieldGroup fieldGroup = new FieldGroup();
	   
	        TextField username = new TextField("Username");
	        username.setRequired(true);
	        fieldGroup.bind(username, "username");
	        username.setNullRepresentation("");
	        username.setWidth("300px");
	
	        PasswordField password = new PasswordField("Password");
	        password.setRequired(true);
	        fieldGroup.bind(password, "password");
	        password.setNullRepresentation("");
	        password.setWidth("300px");
	        StringLengthValidator slv = new StringLengthValidator("The password must have min. 8 letters)", 8, 100, true);
	        password.addValidator(slv);
	
	        Button loginBtn = new Button("Login");
	       
	        form.addComponents(username, password, loginBtn);
	        
	        grid.addComponent(form,3,1,3,5);
	        grid.setComponentAlignment(form, Alignment.BOTTOM_CENTER);
	        
	        VerticalLayout viewLayout = new VerticalLayout();
			viewLayout.setSizeFull();
			viewLayout.setSpacing(true);
			viewLayout.setMargin(true);
			viewLayout.addComponent(grid);

			viewLayout.setComponentAlignment(grid, Alignment.TOP_CENTER);
			viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
			
			setContent(viewLayout);
			
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
			
			loginBtn.addClickListener(new ClickListener() {
	
				private static final long serialVersionUID = 1L;
	
				@Override
				public void buttonClick(ClickEvent event) {
					boolean ifSucceed = false;
			     
		        	ifSucceed = loginManager.checkUserLogin(username.getValue(), password.getValue());
		        	//Notification.show(Boolean.toString(ifSucceed), Notification.Type.TRAY_NOTIFICATION);
		        	if(ifSucceed == true){
		        		userManager.logUser(username.getValue());
		        		userManager.setUserId();
		        		bookManager.setBookId(0);
			        	Page.getCurrent().setLocation("/mainpage");
			        	
			        }
			        else{
			        	Notification.show("Wrong username or password!", Notification.Type.WARNING_MESSAGE);
			        }
			        
			        
				}
			});
		}
		else
			Page.getCurrent().setLocation("/mainpage");
    }

    @WebServlet(urlPatterns = {"/login/*"}, name = "LoginServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = LoginUI.class, productionMode = false)
    public static class LoginServlet extends VaadinServlet {
    }

}
