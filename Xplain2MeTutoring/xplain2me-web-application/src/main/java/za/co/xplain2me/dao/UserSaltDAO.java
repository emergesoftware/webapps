package za.co.xplain2me.dao;

import za.co.xplain2me.entity.UserSalt;

public interface UserSaltDAO {
    
    public UserSalt getUserSalt(String username)
            throws DataAccessException;
    
}
