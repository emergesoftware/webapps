package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.UserSalt;
import za.co.xplain2me.model.SearchUserProfileType;

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
     * @param currentProfileTypeId
     * @return
     * @throws DataAccessException 
     */
    public List<Profile> getUserAuthorisedProfiles(long currentProfileTypeId)
            throws DataAccessException;
    
    /**
     * Gets the user profile - only if the current
     * user profile allows.
     * 
     * @param profileId
     * @param currentProfileTypeId
     * @return
     * @throws DataAccessException 
     */
    public Profile getProfileById(long profileId, long currentProfileTypeId)
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
    public Profile verifyOwnUserProfile(String identityNumber, String emailAddress, 
            String verificationCode)
            throws DataAccessException;
    
    /**
     * Updates the profile entity.
     * 
     * @param profile
     * @return
     * @throws DataAccessException 
     */
    public Profile mergeProfile(Profile profile) throws DataAccessException;
    
    /**
     * Deletes the user profile.
     * 
     * @param profileToDelete
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public boolean deleteUserProfile(Profile profileToDelete, Profile profilePerformingAction)
            throws DataAccessException;
    
    /**
     * Search user profiles
     * 
     * @param type
     * @param searchValue
     * @param restrainedProfileTypeId
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public List<Profile> searchUserProfile(SearchUserProfileType type, Object searchValue,
            Profile profilePerformingAction) throws DataAccessException;
    
}
