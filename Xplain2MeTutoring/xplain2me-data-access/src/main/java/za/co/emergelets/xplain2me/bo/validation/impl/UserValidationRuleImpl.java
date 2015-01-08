package za.co.emergelets.xplain2me.bo.validation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import za.co.emergelets.xplain2me.bo.validation.UserValidationRule;

public class UserValidationRuleImpl implements UserValidationRule {

    private static final Logger LOG = 
            Logger.getLogger(UserValidationRuleImpl.class.getName(), null);
    
    public UserValidationRuleImpl() {
    }
    
    @Override
    public List<String> validateUsername(String username) {
        
        List<String> errors = new ArrayList<>();
        
        // check if the username is within the required length of [8 - 24]
        if (username == null || username.trim().length() < USERNAME_MIN_LENGTH || 
                username.trim().length() > USERNAME_MAX_LENGTH)
            errors.add("The username nust have " + USERNAME_MIN_LENGTH + " - " 
                    + USERNAME_MAX_LENGTH + " number of characters.");
        
        // check if the username is alphanumeric
        if (username != null && 
                !StringUtils.isAlphanumeric(username.trim()))
            errors.add("The username must be alphanumeric - contain "
                    + "both numbers and letters.");
        
        return errors;
        
    }

    @Override
    public List<String> validatePassword(String password) {
        
        List<String> errors = new ArrayList<>();
        
        // check if the password meets the required length
        // range
        if (password == null || password.length() < USERNAME_MIN_LENGTH || 
                password.length() > USERNAME_MAX_LENGTH)
            errors.add("The password nust have " + USERNAME_MIN_LENGTH + " - " 
                    + USERNAME_MAX_LENGTH + " number of characters.");
        
        // check if the password is alphanumeric
        if (password != null && 
                !StringUtils.isAlphanumeric(password))
            errors.add("The password must be alphanumeric - contain "
                    + "both numbers and letters.");
        
        return errors;
    }
    
}
