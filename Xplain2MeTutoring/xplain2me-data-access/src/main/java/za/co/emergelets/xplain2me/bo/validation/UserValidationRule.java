package za.co.emergelets.xplain2me.bo.validation;

import java.util.List;

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
    
}
