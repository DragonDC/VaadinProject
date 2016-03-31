package validator;

import java.util.Date;

import com.vaadin.client.ui.Field;
import com.vaadin.data.Validator;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.PasswordField;

import domain.User;

public class MyValidator implements Validator {

	private static final long serialVersionUID = 1L;
	User user = new User();

	public class TextFirstNameValidator implements Validator {
        @Override
        public void validate(Object value) throws InvalidValueException {
        	String text = (String) value;
        	if(text == "")
            	throw new InvalidValueException("Must be filled");
        	else if (!(text.matches("[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłóńśźż]*")))
                throw new InvalidValueException("Letters only accepted. The first letter must be capital.");
            
        }
 
    }
	
	public class TextLastNameValidator implements Validator {
        @Override
        public void validate(Object value) throws InvalidValueException {
        	String text = (String) value;
        	if (!(text.matches("[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząćęłóńśźż]*")) && text.length()>0)
                throw new InvalidValueException("Letters only accepted. The first letter must be capital.");
            
        }
 
    }
	
	public class RequiredTextValidator implements Validator{
		@Override
        public void validate(Object value) throws InvalidValueException {
        	String text = (String) value;
        	if(text == "")
            	throw new InvalidValueException("Must be filled"); 
        }
	}
	
	public class UsernameValidator implements Validator {
		@Override
        public void validate(Object value) throws InvalidValueException {
        	String text = (String) value;
        	if(text == "")
            	throw new InvalidValueException("Must be filled");
        	else if (!(text.matches("^[^\\s]{3,15}$")))
                throw new InvalidValueException("Username length must be beetween 3 and 15 symbols");
            
        }
	}
	
	public class EmailValidator implements Validator {
		@Override
        public void validate(Object value) throws InvalidValueException {
        	String email = (String) value;
        	if(email == "")
            	throw new InvalidValueException("Must be filled");
        	else if (!(email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")))
                throw new InvalidValueException("You must provide a valid email");
            
        }
	}
	
	public class DateValidator implements Validator {
		 @Override
         public void validate(Object value) throws InvalidValueException {
             Date date = (Date) value;
             Date now = new Date(); 
             if (date!=null && date.after(now))
                 throw new InvalidValueException("Date after now!");
         }
	}
	
	public class PasswordValidator implements Validator {
		@Override
		public void validate(Object value) throws InvalidValueException {
			String password = (String) value;
        	if (password.length() < 8)
                throw new InvalidValueException("Password must contain min. 8 symbols!");

        }
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		
	}

}
