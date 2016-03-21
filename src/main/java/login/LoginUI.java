package login;

import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import service.LoginManager;


@Theme("mytheme")
@Widgetset("Proj1.MyAppWidgetset")
public class LoginUI extends UI {
	private static final long serialVersionUID = 1L;
	public static final String NAME = "logintest";
	
	LoginManager loginManager = new LoginManager();
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
		
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
        
        Label  errLabel = new Label();
        errLabel.setCaptionAsHtml(true);
        form.addComponents(username, password, loginBtn, errLabel);
        
        VerticalLayout viewLayout = new VerticalLayout(form);
		viewLayout.setSizeFull();
		viewLayout.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
		viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
		
		setContent(viewLayout);
		
		loginBtn.addClickListener(new ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Boolean failed = false;
				boolean ifSucceed = false;
		        errLabel.setCaption("");
		     
	        	ifSucceed = loginManager.checkUserLogin(username.getValue(), password.getValue());
	        	Notification.show(Boolean.toString(ifSucceed), Notification.Type.TRAY_NOTIFICATION);
	        	if(ifSucceed == true){
	        		getSession().setAttribute("user", username);
		        	//Page.getCurrent().setLocation("/mainPage");
		        }
		        else{
		        	errLabel.setCaption(errLabel.getCaption() + "<br/>" + "Wrong username or password!");
		        }
		        
		        
			}
		});
    }

    @WebServlet(urlPatterns = {"/login/*"}, name = "LoginTestServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = LoginUI.class, productionMode = false)
    public static class LoginTestServlet extends VaadinServlet {
    }

}
