package za.co.xplain2me.bo.validation;

import java.util.List;
import za.co.xplain2me.entity.User;
import za.co.xplain2me.webapp.component.PasswordSet;

public interface UserValidationRule {
    
    public static final int USERNAME_MIN_LENGTH = 8;
    public static final int USERNAME_MAX_LENGTH = 24;
    
    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 24;
    
    /**
     * Validates the username against the 
     * validation rules.
     * 
     * @param username
     * @return 
     */
    public List<String> validateUsername(String username);
    
    /**
     * Validates the password
     * 
     * @param password
     * @return 
     */
    public List<String> validatePassword(String password);
    
    /**
     * Validates an updated user
     * 
     * @param userPerformingAction
     * @param updatedUser
     * @param passwordSet
     * @return 
     */
    public List<String> validateOwnUpdatedUser(User userPerformingAction,
            User updatedUser,
            PasswordSet passwordSet);
    
}
