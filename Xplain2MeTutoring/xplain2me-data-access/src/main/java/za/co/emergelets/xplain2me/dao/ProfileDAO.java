package za.co.emergelets.xplain2me.dao;

import za.co.emergelets.xplain2me.entity.Profile;

public interface ProfileDAO {
    
    public Profile authenticateProfile(String username, String password)
            throws DataAccessException;
    
}
