package za.co.emergelets.xplain2me.dao;

import java.util.List;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.entity.UserSalt;

public interface ProfileDAO {
    
    /**
     * Authenticates the username and password
     * against an existing user profile.
     * 
     * @param username
     * @param password
     * @return
     * @throws DataAccessException 
     */
    public Profile authenticateProfile(String username, String password)
            throws DataAccessException;
    
    /**
     * Gets a list of all user profiles that the
     * current user is allowed to manage or view.
     * 
     * @param currentUserProfileId
     * @return
     * @throws DataAccessException 
     */
    public List<Profile> getUserAuthorisedProfiles(long currentUserProfileId)
            throws DataAccessException;
    
    /**
     * Persists a new Profile entity
     * 
     * @param profile
     * @param userSalt
     * @return
     * @throws DataAccessException 
     */
    public Profile persistProfile(Profile profile, UserSalt userSalt) throws DataAccessException;
    
    /**
     * Verifies the user account in the data store
     * and activates the user profile for login.
     * 
     * @param identityNumber
     * @param emailAddress
     * @param verificationCode
     * @return
     * @throws DataAccessException 
     */
    public Profile verifyOwnUserProfile(String identityNumber, String emailAddress, long verificationCode)
            throws DataAccessException;
}
