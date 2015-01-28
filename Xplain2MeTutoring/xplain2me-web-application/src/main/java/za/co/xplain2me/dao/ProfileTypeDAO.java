package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.ProfileType;

public interface ProfileTypeDAO {
    
    /**
     * Gets a list of all profile types
     * 
     * @return
     * @throws DataAccessException 
     */
    public List<ProfileType> getAllProfileTypes() throws DataAccessException;
    
    /**
     * Gets a profile type by it's ID.
     * 
     * @param id
     * @return
     * @throws DataAccessException 
     */
    public ProfileType getProfileTypeById(long id) throws DataAccessException;
    
    /**
     * Gets a list of profile types that are authorised by the
     * profile to be accessible.
     * 
     * @param profile
     * @return
     * @throws DataAccessException 
     */
    public List<ProfileType> getProfileTypesAuthorisedByProfile(Profile profile)
            throws DataAccessException;
    
}
