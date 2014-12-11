package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import za.co.emergelets.util.SHA256Encryptor;
import za.co.emergelets.xplain2me.dao.CitizenshipDAO;
import za.co.emergelets.xplain2me.dao.CitizenshipDAOImpl;
import za.co.emergelets.xplain2me.dao.GenderDAO;
import za.co.emergelets.xplain2me.dao.GenderDAOImpl;
import za.co.emergelets.xplain2me.dao.UserDAO;
import za.co.emergelets.xplain2me.dao.UserDAOImpl;
import za.co.emergelets.xplain2me.dao.UserSaltDAO;
import za.co.emergelets.xplain2me.dao.UserSaltDAOImpl;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.entity.User;
import za.co.emergelets.xplain2me.entity.UserSalt;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.PasswordSet;
import za.co.emergelets.xplain2me.webapp.component.ProfileManagementForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

@Component
public class ProfileManagementControllerHelper extends GenericController implements Serializable {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(ProfileManagementControllerHelper.class.getName(), null);
    
    private GenderDAO genderDAO;
    private CitizenshipDAO citizenshipDAO;
    private UserSaltDAO userSaltDAO;
    private UserDAO userDAO;
    
    /**
     * Constructor
     */
    public ProfileManagementControllerHelper() {
        this.initialize();
    }
    
    /**
     * INITIALISATION
     */
    private void initialize() {
        this.genderDAO = new GenderDAOImpl();
        this.citizenshipDAO = new CitizenshipDAOImpl();
        this.userSaltDAO = new UserSaltDAOImpl();
        this.userDAO = new UserDAOImpl();
    }

    /**
     * POPULATES THE LIST FOR GENDER TYPES
     * 
     * @param form 
     */
    public void populateGenderTypes(ProfileManagementForm form) {
        if (form == null) {
            LOG.warning("... gender types not loaded - form is null ...");
            return;
        }
        
        TreeMap<String, Gender> map = new TreeMap<>();
        
        for (Gender gender : genderDAO.getAllGender()) {
            map.put(gender.getId(), gender);
        }
        
        form.setGender(map);
    }

    /**
     * POPULATES THE FORM WITH THE
     * TYPES OF CITIZENSHIP
     * 
     * @param form 
     */
    public void populateCitizenshipTypes(ProfileManagementForm form) {
        
        if (form == null) {
            LOG.warning("... citizenship types not loaded - form is null ...");
            return;
        }
        
        TreeMap<Long, Citizenship> map = new TreeMap<>();
        
        for (Citizenship citizenship : citizenshipDAO.getAllCitizenships()) {
            map.put(citizenship.getId(), citizenship);
        }
        
        form.setCitizenship(map);
    }
    
    /**
     * GETS THE UPDATED USER FROM THE
     * HTTP REQUEST
     * 
     * @param request
     * @param form
     * @return 
     */
    public User getUpdatedUserInformation(HttpServletRequest request, ProfileManagementForm form) {
        
        if (form == null) {
            LOG.warning(" ... form is null ...");
            return null;
        }
        
        // get the original user
        User originalUser = form.getProfile().getPerson().getUser();
        // create a new updated user
        User updatedUser = new User();
        // set the fields that remain unchanged
        updatedUser.setId(originalUser.getId());
        updatedUser.setActive(originalUser.isActive());
        updatedUser.setAdded(originalUser.getAdded());
        updatedUser.setDeactivated(originalUser.getDeactivated());
        updatedUser.setPassword(originalUser.getPassword());
        
        // collect the new username
        updatedUser.setUsername(getParameterValue(request, "username"));
        
        // check if the password was also updated - 
        // then collect
        PasswordSet password = null;
        
        if (getParameterValue(request, "currentPassword").isEmpty() == false &&
            getParameterValue(request, "newPassword").isEmpty() == false && 
            getParameterValue(request, "reEnterNewPassword").isEmpty() == false) {
            
            // set the password set
            password = new PasswordSet();
            password.setCurrentPassword(getParameterValue(request, "currentPassword"));
            password.setEnteredPassword(getParameterValue(request, "newPassword"));
            password.setReEnteredPassword(getParameterValue(request, "reEnterNewPassword"));
            
            form.setPasswordSet(password);
        }
        
        return updatedUser;
        
    }
    
    /**
     * Validate the updated user
     * @param form
     * @param updatedUser
     * @param password
     * @param alertBlock
     * @return 
     */
    public boolean validateUpdatedUser(ProfileManagementForm form, 
            User updatedUser, PasswordSet password, AlertBlock alertBlock) {
        
        if (alertBlock == null) {
            LOG.warning(" ... alert block is null ..."); 
            return false;
        }
        
        int counter = 0;
        
        // check if the username has a valid length
        if (updatedUser != null &&
                (updatedUser.getUsername() == null || updatedUser.getUsername().length() < 8 || 
                updatedUser.getUsername().length() > 32)) {
            counter++;
            alertBlock.addAlertBlockMessage("The username must be between "
                    + "8 and 32 characters in length.");
        }
        
        if (password != null) {
            
            // get the salt value from the 
            // data store
            UserSalt salt = userSaltDAO.getUserSalt(form.getProfile().getPerson()
                    .getUser().getUsername());
            // hash the password along with the salt
            String originalPassword = null;
            try {
                originalPassword = SHA256Encryptor.computeSHA256(password.getCurrentPassword(), 
                        salt.getValue());
            }
            catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                LOG.severe("... could not hash the password ...");
            }

            // if the hashing failed or the original password
            // does not match the entered current password
            if (originalPassword == null || 
                    !originalPassword.equals(form.getProfile().getPerson()
                    .getUser().getPassword())) {
                counter++;
                alertBlock.addAlertBlockMessage("The current password entered does not match your "
                        + "your original password.");
            }

            // check if the new passwords entered do match
            if (!password.getEnteredPassword().equals(password.getReEnteredPassword())) {
                counter++;
                alertBlock.addAlertBlockMessage("The new entered and the re-entered "
                        + "passwords do not match.");
            }
        }
        
        return (counter == 0);
    }
    
    /**
     * UPDATES THE USER'S PASSWORD
     * 
     * @param username
     * @param newPassword
     * @param saltValue
     * @return 
     */
    public boolean updateUserPassword(String username, String newPassword, String saltValue) {
        
        if ((username == null || username.isEmpty()) || 
                (newPassword == null || newPassword.isEmpty()) || 
                (saltValue == null || saltValue.isEmpty())) {
            
            LOG.warning(" either the username, new password or salt value salt is null ");
            return false;
        }
        
        return userDAO.updateUserPassword(username, newPassword, saltValue);
    }

    /**
     * CHECKS IF THE UPDATED USERNAME IS
     * COMPLETELY UNIQUE
     * 
     * @param newUsername
     * @param oldUsername
     * @return 
     */
    public boolean isUsernameCompletelyUnique(String newUsername, String oldUsername) {
        if (newUsername == null || 
                oldUsername == null) {
            LOG.warning(" empty usernames ... not checking.");
            return false;
        }
        
        return userDAO.isUsernameUnique(newUsername);
    }

    public User updateUsername(User updatedUser) {
        if (updatedUser == null) {
            LOG.warning(" user is null ...");
            return null;
        }
        
        return userDAO.updateUser(updatedUser);
    }
}
