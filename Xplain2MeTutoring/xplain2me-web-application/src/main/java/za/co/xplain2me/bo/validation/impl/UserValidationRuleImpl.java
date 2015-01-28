package za.co.xplain2me.bo.validation.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;
import za.co.xplain2me.bo.validation.UserValidationRule;
import za.co.xplain2me.dao.UserSaltDAO;
import za.co.xplain2me.dao.UserSaltDAOImpl;
import za.co.xplain2me.entity.User;
import za.co.xplain2me.entity.UserSalt;
import za.co.xplain2me.util.SHA256Encryptor;
import za.co.xplain2me.webapp.component.PasswordSet;

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

    @Override
    public List<String> validateOwnUpdatedUser(User userPerformingAction, 
            User updatedUser, PasswordSet password) {
        
        List<String> errors = new ArrayList<>();
        
        if (userPerformingAction == null || 
                updatedUser == null) {
            return null;
        }
        
        // check if the username has a valid length
        if ((updatedUser.getUsername() == null || updatedUser.getUsername().length() < 8 || 
                updatedUser.getUsername().length() > 32)) {
            errors.add("The username must be between "
                    + "8 and 32 characters in length.");
        }
        
        if (password != null) {
            
            // create the data access object
            UserSaltDAO userSaltDAO = new UserSaltDAOImpl();
            
            // get the salt value from the 
            // data store
            UserSalt salt = userSaltDAO.getUserSalt(userPerformingAction.getUsername());
            // hash the password along with the salt
            String originalPassword = null;
            try {
                originalPassword = SHA256Encryptor.computeSHA256(password.getCurrentPassword(), 
                        salt.getValue());
            }
            catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                LOG.severe("... could not hash the password ...");
            }

            // if the hashing failed or the original password
            // does not match the entered current password
            if (originalPassword == null || 
                    !originalPassword.equals(userPerformingAction.getPassword())) {
                errors.add("The current password entered does not match "
                        + "your original password.");
            }

            // check if the new passwords entered do match
            if (!password.getEnteredPassword().equals(password.getReEnteredPassword())) {
                errors.add("The new entered and the re-entered "
                        + "passwords do not match.");
            }
        }
        
        return errors;
        
    }
    
}
