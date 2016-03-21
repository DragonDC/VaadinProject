package registration;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import domain.User;
import service.RegistrationManager;
import validator.MyValidator;


@Theme("mytheme")
@Widgetset("Proj1.MyAppWidgetset")
public class RegistrationUI extends UI {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "registrationtest";
	
	private RegistrationManager registrationManager = new RegistrationManager();
	MyValidator myValidator = new MyValidator();
	User bean = new User();
	FormLayout form = new FormLayout();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
			
		form.setCaption("Registration");
		form.setSpacing(true);
		form.setMargin(new MarginInfo(true, true, true, false));
		form.setSizeUndefined();

        TextField firstName = new TextField("First Name");
        firstName.setWidth("300px");
        firstName.addValidator(myValidator.new TextFirstNameValidator());
        firstName.setImmediate(true);
        
        TextField lastName = new TextField("Last Name");
        lastName.addValidator(myValidator.new TextLastNameValidator());
        lastName.setWidth("300px");
        lastName.setImmediate(true);
      
        TextField username = new TextField("Username");
        username.addValidator(myValidator.new UsernameValidator());
        username.setWidth("300px");
        username.setImmediate(true);

        DateField dateOfBirth = new DateField("Date of birth");
        dateOfBirth.addValidator(myValidator.new DateValidator());
        dateOfBirth.setWidth("300px");
        dateOfBirth.setImmediate(true);
        

        TextField email = new TextField("Email");
        email.setInputPrompt("Email (eg. email.sth@gmail.com)");
        email.addValidator(myValidator.new EmailValidator());
        email.setWidth("300px");
        email.setImmediate(true);
     
        PasswordField password = new PasswordField("Password");
        password.setWidth("300px");
        password.addValidator(myValidator.new PasswordValidator());
        password.setImmediate(true);
 
        PasswordField confirmPassword = new PasswordField("Confirm password");
        confirmPassword.setWidth("300px");
        confirmPassword.addValidator(myValidator.new PasswordValidator());
        confirmPassword.setImmediate(true);
        
        Button registerBtn = new Button("Rejestracja");
       
        form.addComponents(firstName, lastName, username, email, dateOfBirth, password, confirmPassword, registerBtn);
        
        VerticalLayout viewLayout = new VerticalLayout(form);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		viewLayout.setImmediate(true);		
		
		setContent(viewLayout);
		
		registerBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				boolean ifSucceed = false;
		        
		        try {
		        	firstName.validate();
		        	lastName.validate();
		        	username.validate();
		        	email.validate();
		        	dateOfBirth.validate();
		        	password.validate();
		        	confirmPassword.validate();
		        	
		        	bean.setFirstName(firstName.getValue());
			        bean.setLastName(lastName.getValue());
			        bean.setUsername(username.getValue());
			        bean.setDateOfBirth(dateOfBirth.getValue());
			        bean.setEmail(email.getValue());
			        bean.setPassword(password.getValue());
			        
			        String p1 = password.getValue();
			        String p2 = confirmPassword.getValue();
			        if(!p1.equals(p2)){
			        	Notification.show("Password and confirm are different!", Notification.Type.WARNING_MESSAGE);
			        	password.setValue("");
			        	confirmPassword.setValue("");
			        }
			        else{
			        	ifSucceed = registrationManager.checkUserRegistration(bean);
			        	Notification.show(Boolean.toString(ifSucceed), Notification.Type.TRAY_NOTIFICATION);
			        	if(ifSucceed == true){
				        	Page.getCurrent().setLocation("/mainPage");
				        }
				        else{
				        	username.setValue("");
				        	email.setValue("");
				        	password.setValue("");
				        	confirmPassword.setValue("");
				        	Notification.show("Username or email already exists!", Notification.Type.WARNING_MESSAGE);
				        }
			        }
	
		        } catch (InvalidValueException e) {
		        	Notification.show("There are mistakes in form!", Notification.Type.WARNING_MESSAGE);
	            }
		        
		        
			}
		});
    }
    

    @WebServlet(urlPatterns = {"/registration/*"}, name = "RegistrationTestServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = RegistrationUI.class, productionMode = false)
    public static class RegistrationTestServlet extends VaadinServlet {
    }

}
