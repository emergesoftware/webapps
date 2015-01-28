package za.co.xplain2me.bo;

public interface UserPasswordBO {
    
    /**
     * Generates a random and secure 
     * salt value.
     * 
     * @return 
     */
    public String generateRandomSaltValue();
    
    /**
     * Generates a hashed password from the 
     * provided plain text password and the
     * salt value.
     * 
     * @param password
     * @param saltValue
     * @return 
     * @throws java.lang.Exception 
     */
    public String generateHashedPassword(String password, String saltValue) throws Exception;
   
    
}
