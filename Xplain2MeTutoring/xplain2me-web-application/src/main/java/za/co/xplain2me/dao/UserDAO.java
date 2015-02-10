package za.co.xplain2me.dao;

import za.co.xplain2me.entity.User;
import za.co.xplain2me.entity.UserSalt;

public interface UserDAO {
    
    public User getUser(String username);
    
    /**
     * CHECKS IF THE USERNAME IS
     * COMPLETELY UNIQUE
     * AND NOT USED BY ANOTHER
     * EXISTING USER.
     * 
     * @param username
     * @return
     * @throws DataAccessException 
     */
    public boolean isUsernameUnique(String username)
            throws DataAccessException;
    
    /**
     * UPDATES THE USER ENTITY
     * @param user
     * @return
     * @throws DataAccessException 
     */
    public User updateUser(User user)
            throws DataAccessException;
    
    /**
     * UPDATES THE USER PASSWORD + 
     * USER SALT SIMALTENOUSLY
     * 
     * @param username
     * @param newPassword
     * @param saltValue
     * @return
     * @throws DataAccessException 
     */
    public boolean updateUserPassword(String username, String newPassword, String saltValue)
            throws DataAccessException;
}
