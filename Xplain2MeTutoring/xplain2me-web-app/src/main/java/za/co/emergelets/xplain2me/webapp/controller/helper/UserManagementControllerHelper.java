package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import za.co.emergelets.util.DateTimeUtils;
import za.co.emergelets.xplain2me.dao.ProfileDAO;
import za.co.emergelets.xplain2me.dao.ProfileDAOImpl;
import za.co.emergelets.xplain2me.entity.ContactDetail;
import za.co.emergelets.xplain2me.entity.Person;
import za.co.emergelets.xplain2me.entity.PhysicalAddress;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.entity.ProfileType;
import za.co.emergelets.xplain2me.entity.User;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.component.UserManagementForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;
import za.co.emergelets.xplain2me.webapp.controller.UserManagementController;

@Component
public class UserManagementControllerHelper extends GenericController implements Serializable {
    
    private static final Logger LOG = 
            Logger.getLogger(UserManagementControllerHelper.class.getName(), null);
    
    public UserManagementControllerHelper() {
    }
    
    /**
     * Populates the form with the user profiles that the current
     * user in the session context is allowed to view or manage.
     * 
     * @param form
     * @param context 
     */
    public void populateUserAuthorisedProfiles(UserManagementForm form, UserContext context) {
        
        if (form == null || context == null) {
            LOG.warning(" ... either the form or the user context is null ...");
            return;
        }
        
        List<Profile> profiles = null;
        long currentUserProfileId = context.getProfile().getId();
        ProfileDAO dao = new ProfileDAOImpl();
        
        profiles = dao.getUserAuthorisedProfiles(currentUserProfileId);
        
        if (profiles != null) {
            
            TreeMap<Long, Profile> map = new TreeMap<>();
            for (Profile profile : profiles) 
                map.put(profile.getId(), profile);
            form.setUserProfiles(map);
        }
        
    }

    /**
     * Resolves any parameters 
     * passed onto the browse users request.
     * 
     * @param request 
     */
    public void resolveBrowseExistingUsersParameters(HttpServletRequest request) {
        
    }
    
    /**
     * Collects the profile type parameters
     * 
     * @param request
     * @param form
     * @return 
     */
    public ProfileType collectUserProfileType(HttpServletRequest request, UserManagementForm form) {
        
        if (request == null || form == null) {
            LOG.warning(" ... the HTTP servlet request or form is null ...");
            return null;
        }
        
        else {
            
            Long id = Long.parseLong(getParameterValue(request, "profileType"));
            ProfileType type = form.getProfileTypes().get(id);
            return type;
        }
        
    }

    /**
     * Creates an instance of the Person entity
     * from the params in the HTTP request
     * 
     * @param request
     * @param form
     * @return 
     */
    public Person collectPerson(HttpServletRequest request, UserManagementForm form) {
        
        if (request == null || form == null) {
            LOG.warning(" ... the HTTP servlet request or form is null ...");
            return null;
        }
        
        Person person = new Person();
        person.setLastName(getParameterValue(request, "lastName"));
        person.setFirstNames(getParameterValue(request, "firstNames"));
        person.setDateOfBirth(DateTimeUtils.parseDate(getParameterValue(request, "dateOfBirth")));
        person.setGender(form.getGender().get(getParameterValue(request, "gender")));
        person.setIdentityNumber(getParameterValue(request, "idNumber"));
        person.setCitizenship(form.getCitizenships().get(
                Long.parseLong(getParameterValue(request, "citizenship"))));
        
        return person;
    }
    
    /**
        * Creates an instance of the ContactDetail entity
     * from the params in the HTTP request 
     * @param request
     * @param form
     * @return 
     */
    public ContactDetail collectContactDetail(HttpServletRequest request, UserManagementForm form) {
        if (request == null || form == null) {
            LOG.warning(" ... the HTTP servlet request or form is null ...");
            return null;
        }
        
        ContactDetail detail = new ContactDetail();
        detail.setCellphoneNumber("0" + getParameterValue(request, "contactNumber"));
        detail.setEmailAddress(getParameterValue(request, "emailAddress"));
        return detail;
    }

    /**
     * Creates an instance of the PhysicalAddress entity
     * from the params in the HTTP request
     * 
     * @param request
     * @param form
     * @return 
     */
    public PhysicalAddress collectPhysicalAddress(HttpServletRequest request, UserManagementForm form) {
        
        if (request == null || form == null) {
            LOG.warning(" ... the HTTP servlet request or form is null ...");
            return null;
        }
        
        PhysicalAddress address = new PhysicalAddress();
        address.setAddressLine1(getParameterValue(request, "physicalAddressLine1"));
        address.setAddressLine2(getParameterValue(request, "physicalAddressLine2"));
        address.setSuburb(getParameterValue(request, "suburb"));
        address.setCity(getParameterValue(request, "city"));
        address.setAreaCode(getParameterValue(request, "areaCode"));
        
        return address;
    }

    /**
     * Creates an instance of the User entity
     * from the params in the HTTP request
     * @param request
     * @param form
     * @return 
     */
    public User collectUser(HttpServletRequest request, UserManagementForm form) {
        
        if (request == null || form == null) {
            LOG.warning(" ... the HTTP servlet request or form is null ...");
            return null;
        }
        
        User user = new User();
        user.setUsername(getParameterValue(request, "username"));
        return user;
        
    }

    /**
     * Resolves any additional parameters
     * for adding new user.
     * 
     * @param request 
     */
    public void resolveAddNewUserAdditionalParameters(HttpServletRequest request) {
        
        String result = getParameterValue(request, "result");
        
        if (result == null || result.isEmpty()) {
            LOG.info(" ... no parameter value found for param: result ...");
            return;
        }
        
        AlertBlock alertBlock = null;
        
        switch (Integer.parseInt(result)) {
            
            case UserManagementController.ADD_NEW_USER_VALIDATION_ERROR_OCCURED:
            case UserManagementController.ADD_NEW_USER_INTERNAL_ERROR_OCCURED:
                
                alertBlock = (AlertBlock)getFromSessionScope(request, AlertBlock.class);
                removeFromSessionScope(request, AlertBlock.class);
                break;
                
            case UserManagementController.ADD_NEW_USER_SUCCESSFUL:
                String profileId = getParameterValue(request, "profile-id");
                String profileType = getParameterValue(request, "profile-type");
                
                alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_INFORMATIVE, 
                            "The user profile: " + profileType + " has been created successfully. "
                            + "The sequence or reference number is: " + profileId + 
                            ". The new user must now go to their email inbox and"
                            + " follow the instruction therein to complete the"
                            + "verification process.");
                break;
        }
        
        if (alertBlock != null && alertBlock.getAlertBlockMessages() != null 
                && !alertBlock.getAlertBlockMessages().isEmpty()) {
            
            saveToRequestScope(request, alertBlock);
        }
    }
    
    
}
