package za.co.emergelets.xplain2me.dao;

import za.co.emergelets.xplain2me.entity.UserSalt;

public interface UserSaltDAO {
    
    public UserSalt getUserSalt(String username)
            throws DataAccessException;
    
}
