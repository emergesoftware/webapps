package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import za.co.emergelets.util.DateTimeUtils;
import za.co.emergelets.util.NumericUtils;
import za.co.emergelets.util.SHA256Encryptor;
import za.co.emergelets.xplain2me.bo.validation.ContactDetailValidationRule;
import za.co.emergelets.xplain2me.bo.validation.PersonalInformationValidationRule;
import za.co.emergelets.xplain2me.bo.validation.PhysicalAddressValidationRule;
import za.co.emergelets.xplain2me.bo.validation.impl.ContactDetailValidationRuleImpl;
import za.co.emergelets.xplain2me.bo.validation.impl.PersonalInformationValidationRuleImpl;
import za.co.emergelets.xplain2me.bo.validation.impl.PhysicalAddressValidationRuleImpl;
import za.co.emergelets.xplain2me.dao.CitizenshipDAO;
import za.co.emergelets.xplain2me.dao.CitizenshipDAOImpl;
import za.co.emergelets.xplain2me.dao.ContactDetailDAO;
import za.co.emergelets.xplain2me.dao.ContactDetailDAOImpl;
import za.co.emergelets.xplain2me.dao.GenderDAO;
import za.co.emergelets.xplain2me.dao.GenderDAOImpl;
import za.co.emergelets.xplain2me.dao.PersonDAO;
import za.co.emergelets.xplain2me.dao.PersonDAOImpl;
import za.co.emergelets.xplain2me.dao.PhysicalAddressDAO;
import za.co.emergelets.xplain2me.dao.PhysicalAddressDAOImpl;
import za.co.emergelets.xplain2me.dao.UserDAO;
import za.co.emergelets.xplain2me.dao.UserDAOImpl;
import za.co.emergelets.xplain2me.dao.UserSaltDAO;
import za.co.emergelets.xplain2me.dao.UserSaltDAOImpl;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.ContactDetail;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.entity.Person;
import za.co.emergelets.xplain2me.entity.PhysicalAddress;
import za.co.emergelets.xplain2me.entity.User;
import za.co.emergelets.xplain2me.entity.UserSalt;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.PasswordSet;
import za.co.emergelets.xplain2me.webapp.component.ProfileManagementForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;
import za.co.emergelets.xplain2me.webapp.controller.ProfileManagementController;

@Component
public class ProfileManagementControllerHelper extends GenericController implements Serializable {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(ProfileManagementControllerHelper.class.getName(), null);
    
    // data access objects
    private GenderDAO genderDAO;
    private CitizenshipDAO citizenshipDAO;
    private UserSaltDAO userSaltDAO;
    private UserDAO userDAO;
    private PersonDAO personDAO;
    private ContactDetailDAO contactDetailDAO;
    private PhysicalAddressDAO physicalAddressDAO;
    
    // business objects or rules
    private PersonalInformationValidationRule personValidationRules;
    private ContactDetailValidationRule contactDetailValidationRules;
    private PhysicalAddressValidationRule addressValidationRules;
    
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
        this.personDAO = new PersonDAOImpl();
        this.contactDetailDAO = new ContactDetailDAOImpl();
        this.physicalAddressDAO = new PhysicalAddressDAOImpl();
        
        this.personValidationRules = new PersonalInformationValidationRuleImpl();
        this.contactDetailValidationRules = new ContactDetailValidationRuleImpl();
        this.addressValidationRules = new PhysicalAddressValidationRuleImpl();
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
                alertBlock.addAlertBlockMessage("The current password entered does not match "
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

    /**
     * Resolves any alerts
     * 
     * @param request 
     */
    public void resolveAnyAlerts(HttpServletRequest request) {
        
        String resultCode = getParameterValue(request, "result");
        
        if (resultCode == null || 
            resultCode.isEmpty() ||
            NumericUtils.isNaN(resultCode)) {
            
            LOG.warning(" ... no result code found in the "
                    + "request url ...");
            return;
        }
        
        AlertBlock alertBlock = new AlertBlock();
        
        switch (Integer.parseInt(resultCode)) {
            
            case ProfileManagementController.RESULT_USER_CREDENTIALS_UPDATE_SUCCESS:
                
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE);
                alertBlock.addAlertBlockMessage("Your User Credentials were "
                        + "updated successfully.");
                break;
                
            case ProfileManagementController.RESULT_PERSONAL_DETAILS_UPDATE_SUCCESS:
                
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE);
                alertBlock.addAlertBlockMessage("Your Personal Details were "
                        + "updated successfully.");
                break;
                
            case ProfileManagementController.RESULT_CONTACT_DETAILS_UPDATE_SUCCESS:
                
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE);
                alertBlock.addAlertBlockMessage("Your Contact Details were "
                        + "updated successfully.");
                break;
                
            case ProfileManagementController.RESULT_RESIDENTIAL_ADDRESS_UPDATE_SUCCESS:
                
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE);
                alertBlock.addAlertBlockMessage("Your Residential Address was "
                        + "updated successfully.");
                break;
                
            case ProfileManagementController.RESULT_ERRORS_OCCURED:
                
                AlertBlock object = (AlertBlock)getFromSessionScope(request, 
                        AlertBlock.class);
                
                if (object != null) {
                    alertBlock = object;
                    removeFromSessionScope(request, AlertBlock.class);
                }
                
                break;
        }
        
        // ONLY SAVE TO THE REQUEST SCOPE TO 
        // IF THERE WAS ANY MESSAGES
        if (alertBlock.getAlertBlockMessages().isEmpty() == false) 
            saveToRequestScope(request, alertBlock);
        
    }

    /**
     * CREATES AN UPDATED PERSON
     * ENTITY
     * 
     * @param form
     * @param originalPerson
     * @param lastName
     * @param firstNames
     * @param dateOfBirth
     * @param gender
     * @param citizenship
     * @return 
     */
    public Person createUpdatedPerson(ProfileManagementForm form, 
            Person originalPerson, String lastName, 
            String firstNames, String dateOfBirth, 
            String gender, String citizenship) {
        
        Person updatedPerson = new Person();
        // transfer unchanged properties
        updatedPerson.setId(originalPerson.getId());
        updatedPerson.setIdentityNumber(originalPerson.getIdentityNumber());
        updatedPerson.setUser(originalPerson.getUser());
        updatedPerson.setPhysicalAddress(originalPerson.getPhysicalAddress());
        updatedPerson.setContactDetail(originalPerson.getContactDetail());
        
        // update with the new data
        updatedPerson.setLastName(lastName);
        updatedPerson.setFirstNames(firstNames);
        updatedPerson.setDateOfBirth(DateTimeUtils.parseDate(dateOfBirth));
        updatedPerson.setGender(form.getGender().get(gender));
        updatedPerson.setCitizenship(form.getCitizenship()
                .get(Long.parseLong(citizenship)));
        
        return updatedPerson;
    }
    
    /**
     * VALIDATES THE UPDATED PERSON
     * ENTITY
     * 
     * @param person
     * @param alertBlock
     * @return 
     */
    public boolean validateUpdatedPerson(Person person, AlertBlock alertBlock) {
        
        if (person == null || alertBlock == null) {
            LOG.warning(" ... person / alert block is null ...");
            return false;
        }
        
        List<String> errors = personValidationRules
                .validatePersonalInformation(person);
        
        if (errors == null || errors.isEmpty()) {
            return true;
        }
        
        else {
            
            for (String error : errors) 
                alertBlock.addAlertBlockMessage(error);
            
            return false;
        }
    }

    /**
     * UPDATES THE PERSON ENTITY
     * INTO THE DATA STORE.
     * 
     * @param updatedPerson 
     */
    public void updatePerson(Person updatedPerson) {
        if (updatedPerson == null) {
            LOG.warning(" .. update person failed - null ...");
            return;
        }
        
        personDAO.updatePerson(updatedPerson);
    }

    /**
     * CREATES AN INSTANCE OF THE
     * UPDATED CONTACT DETAIL ENTITY.
     * 
     * @param request
     * @param originalContactDetail
     * @return 
     */
    public ContactDetail createUpdatedContactDetail(HttpServletRequest request, 
            ContactDetail originalContactDetail) {
        
        if (originalContactDetail == null) {
            return null;
        }
        
        ContactDetail updatedContactDetail = new ContactDetail();
        // copy the unchangeables from the original 
        updatedContactDetail.setId(originalContactDetail.getId());
        //setup the updated 
        updatedContactDetail.setEmailAddress(getParameterValue(request, "emailAddress"));
        updatedContactDetail.setCellphoneNumber(getParameterValue(request, "contactNumber"));
        
        return updatedContactDetail;
    
    }

    /**
     * VALIDATES THE CONTACT DETAIL (UPDATED)
     * ENTITY.
     * 
     * @param updatedContactDetail
     * @param alertBlock
     * @return 
     */
    public boolean validateUpdatedContactDetail(ContactDetail updatedContactDetail, 
            AlertBlock alertBlock) {
        
        List<String> errors = new ArrayList<>();
        
        // validate the contact information
        errors.addAll(contactDetailValidationRules
                .validateContactDetail(updatedContactDetail));
        
        if (errors.isEmpty()) {
            return true;
        }
        
        else {
            for (String error : errors) {
                alertBlock.addAlertBlockMessage(error);
            }

            return false;
        }
    }

    /**
     * VALIDATES THE CONTACT DETAIL
     * ENTITY IN THE DATA STORE.
     * 
     * @param updatedContactDetail
     * @return 
     */
    public boolean updateContactDetails(ContactDetail updatedContactDetail) {
        if (updatedContactDetail == null) {
            LOG.warning(" ... could not initiate contact detail update ...");
            return false;
        }
        
        return (contactDetailDAO.updateContactDetail(
                updatedContactDetail) != null);
    }

    /**
     * CREATES AN INSTANCE OF THE 
     * UPDATED PHYSICAL ADDRESS ENTITY.
     * 
     * @param request
     * @param originalAddress
     * @return 
     */
    public PhysicalAddress createUpdatedPhysicalAddress(HttpServletRequest request, 
            PhysicalAddress originalAddress) {
        
        if (originalAddress == null) {
            LOG.warning(" ... original physical address is null ...");
            return null;
        }
        
        PhysicalAddress updatedAddress = new PhysicalAddress();
        // copy the unchangable properties
        updatedAddress.setId(originalAddress.getId());
        // get the updated properties
        updatedAddress.setAddressLine1(getParameterValue(request, "physicalAddressLine1"));
        updatedAddress.setAddressLine2(getParameterValue(request, "physicalAddressLine2"));
        updatedAddress.setSuburb(getParameterValue(request, "suburb"));
        updatedAddress.setCity(getParameterValue(request, "city"));
        updatedAddress.setAreaCode(getParameterValue(request, "areaCode"));
        
        return updatedAddress;
        
    }

    public boolean validateUpdatedPhysicalAddress(PhysicalAddress updatedAddress, 
            AlertBlock alertBlock) {
        
        if (updatedAddress == null || alertBlock == null) {
            return false;
        }
        
        List<String> errors = new ArrayList<>();
        errors.addAll(addressValidationRules.isPhysicalAddressValid(updatedAddress));
        
        if (errors.isEmpty()) return true;
        else {
        
            for (String error : errors) {
                alertBlock.addAlertBlockMessage(error);
            }
            
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            return false;
        }
        
    }

    public boolean updatePhysicalAddress(PhysicalAddress updatedAddress) {
        
        if (updatedAddress == null) return false;
        
        return (physicalAddressDAO.updatePhysicalAddress(
                updatedAddress) != null);
        
    }
}
