package za.co.emergelets.xplain2me.dao;

import java.util.List;
import za.co.emergelets.xplain2me.entity.ProfileType;
import za.co.emergelets.xplain2me.entity.ProfileTypeUrlPermissions;

public interface ProfileTypeUrlPermissionsDAO {
    
    /**
     * GETS A LIST OF ALL THE USER PROFILE
     * PERMITTED URLS WITHIN THE PORTAL.
     * 
     * @param profileType
     * @return
     * @throws DataAccessException 
     */
    public List<ProfileTypeUrlPermissions> getUserProfileUrlPermissions(ProfileType profileType)
            throws DataAccessException;
    
}
