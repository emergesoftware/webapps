package za.co.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.xplain2me.bo.UserPasswordBO;
import za.co.xplain2me.bo.impl.UserPasswordBOImpl;
import za.co.xplain2me.bo.validation.UserValidationRule;
import za.co.xplain2me.bo.validation.impl.UserValidationRuleImpl;
import za.co.xplain2me.util.SHA256Encryptor;
import za.co.xplain2me.util.SaltGenerator;
import za.co.xplain2me.dao.EventTypes;
import za.co.xplain2me.dao.SystemAuditManager;
import za.co.xplain2me.dao.UserDAO;
import za.co.xplain2me.dao.UserDAOImpl;
import za.co.xplain2me.entity.ContactDetail;
import za.co.xplain2me.entity.Person;
import za.co.xplain2me.entity.PhysicalAddress;
import za.co.xplain2me.entity.User;
import za.co.xplain2me.webapp.component.AlertBlock;
import za.co.xplain2me.webapp.component.PasswordSet;
import za.co.xplain2me.webapp.component.ProfileManagementForm;
import za.co.xplain2me.webapp.component.UserContext;
import za.co.xplain2me.webapp.controller.helper.ProfileManagementControllerHelper;

@Controller
public class ProfileManagementController extends GenericController implements Serializable {
    
    private static final Logger LOG = 
            Logger.getLogger(ProfileManagementController.class.getName(), null);
    
    public static final int VIEW_PROFILE_MODE = 0;
    public static final int EDIT_PROFILE_MODE = 1;
    
    public static final int RESULT_USER_CREDENTIALS_UPDATE_SUCCESS = 200;
    public static final int RESULT_PERSONAL_DETAILS_UPDATE_SUCCESS = 201;
    public static final int RESULT_CONTACT_DETAILS_UPDATE_SUCCESS = 202;
    public static final int RESULT_RESIDENTIAL_ADDRESS_UPDATE_SUCCESS = 203;
    public static final int RESULT_ERRORS_OCCURED = 300;
 
    @Autowired
    private ProfileManagementControllerHelper helper;
    
    public ProfileManagementController() {
    }
    
    @RequestMapping(value = RequestMappings.MY_PROFILE_EDIT, 
            method = RequestMethod.GET)
    public ModelAndView showEditOwnProfileDetailsPage(HttpServletRequest request) {
        
        LOG.info(" ... show edit own profile details page ...");
        
        // check for the form
        // from the session scope
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        if (form == null) 
            form = new ProfileManagementForm();
        else form.resetForm();
        
        // get the user context from the
        // session scope
        UserContext context = (UserContext)getFromSessionScope(request, 
                UserContext.class);
        
        // get the profile
        form.setProfile(context.getProfile());
        
        // determine the mode (view or edit)
        String mode = getParameterValue(request, "mode");
        if ((mode == null || mode.isEmpty()) || 
                Integer.parseInt(mode) == VIEW_PROFILE_MODE) {
            form.setProfileMode(VIEW_PROFILE_MODE);
        }
        
        else if (Integer.parseInt(mode) == EDIT_PROFILE_MODE) {
            
            // populate the gender list
            helper.populateGenderTypes(form);
            //populate the citizenship list
            helper.populateCitizenshipTypes(form);
            
            form.setProfileMode(EDIT_PROFILE_MODE);
        }
        
        // resolve any alerts 
        helper.resolveAnyAlerts(request);
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        // return a view
        if (form.getProfileMode() == EDIT_PROFILE_MODE) {
            return createModelAndView(Views.EDIT_OWN_PROFILE);
        }
        else
            return createModelAndView(Views.VIEW_OWN_PROFILE);
    }
    
    /**
     * HANDLES THE REQUEST TO UPDATE THE 
     * USER LOGIN CREDENTIALS
     * 
     * @param request
     * @return 
     * @throws java.lang.Exception 
     */
    @RequestMapping(value = RequestMappings.EDIT_MY_CREDENTIALS, 
            method = RequestMethod.POST, 
            params = {"username", "currentPassword", 
                    "newPassword", "reEnterNewPassword"})
    public ModelAndView updateUserCredentialsRequest(HttpServletRequest request) 
    
    throws Exception {
        
        // check for the form 
        // in the session
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + VIEW_PROFILE_MODE + 
                "&rand=" + System.currentTimeMillis());
        }
        
        // pull the original user
        User originalUser = form.getProfile().getPerson().getUser();
        // create the updated user
        User updatedUser = helper.getUpdatedUserInformation(request, form);
        PasswordSet passwordSet = form.getPasswordSet();
        // the alert block
        AlertBlock alertBlock = new AlertBlock();
        // the user validation rules
        UserValidationRule userValidationRule = new UserValidationRuleImpl();
        // perform some validations first
        List<String> errorsEncountered = userValidationRule
                .validateOwnUpdatedUser(originalUser, updatedUser, passwordSet);
        
        // if no errors were encountered
        if (errorsEncountered.isEmpty()) {
            
            // if the password has to be updated
            if (passwordSet != null) {
                
                //generate a new hashed password
                UserPasswordBO userPasswordBo = new UserPasswordBOImpl();
                String saltValue = userPasswordBo.generateRandomSaltValue();
                String hashedPassword = userPasswordBo
                        .generateHashedPassword(passwordSet.getEnteredPassword(), saltValue);
                
                // update the passwords
                UserDAO userDAO = new UserDAOImpl();
                userDAO.updateUserPassword(originalUser.getUsername(), 
                        hashedPassword, saltValue);
                
                // log an event
                SystemAuditManager.logAuditAsync(EventTypes.UPDATE_OWN_USER_PASSWORD, 
                        originalUser, 
                        originalUser.getId(), null, 
                        request.getRemoteAddr(), 
                        request.getHeader("User-Agent"), 
                        0, true);
                
                // set the new password
                passwordSet.setNewPassword(hashedPassword);
                updatedUser.setPassword(hashedPassword);
                
            }
            
            // if the old username changed
            if (!originalUser.getUsername().equalsIgnoreCase(updatedUser.getUsername()) &&
                    helper.isUsernameCompletelyUnique(updatedUser.getUsername(), 
                            originalUser.getUsername())) {
                
                // update the user 
                UserDAO userDAO = new UserDAOImpl();
                userDAO.updateUser(updatedUser);
                
                // log an audit
                SystemAuditManager.logAuditAsync(EventTypes.UPDATE_OWN_USERNAME, 
                        originalUser, 
                        originalUser.getId(), null, 
                        request.getRemoteAddr(), 
                        request.getHeader("User-Agent"), 
                        0, true);
                
                // update the user context
                UserContext context = (UserContext)
                        getFromSessionScope(request, UserContext.class);
                
                context.getProfile().getPerson().setUser(updatedUser);
                
                // save to the 
                // session scope
                saveToSessionScope(request, context);
                
            }
            
            form.getProfile().getPerson().setUser(updatedUser);
            
            // save the form to the
            // session scope
            saveToSessionScope(request, form);
            
            // send redirect
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + EDIT_PROFILE_MODE +
                "&result=" + RESULT_USER_CREDENTIALS_UPDATE_SUCCESS +
                "&rand=" + System.currentTimeMillis());
        }
        
        else {
            
            // save the alert block to
            // the session scope
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            saveToSessionScope(request, alertBlock);
            
            // some validations failed
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + EDIT_PROFILE_MODE +
                "&result=" + RESULT_ERRORS_OCCURED +
                "&rand=" + System.currentTimeMillis());
            
        }
    }
    
    /**
     * HANDLES THE REQUEST TO
     * UPDATE THE PERSONAL INFORMATION
     * 
     * @param request
     * @param lastName
     * @param firstNames
     * @param dateOfBirth
     * @param gender
     * @param citizenship
     * @return 
     */
    @RequestMapping(value = RequestMappings.MY_PROFILE_EDIT, 
            method = RequestMethod.POST,
            params = {"lastName", "firstNames", "dateOfBirth",
                      "gender", "citizenship"})
    public ModelAndView updatePersonalInformationRequest(
            HttpServletRequest request,
            @RequestParam("lastName")String lastName,
            @RequestParam("firstNames")String firstNames,
            @RequestParam("dateOfBirth")String dateOfBirth,
            @RequestParam("gender")String gender,
            @RequestParam("citizenship")String citizenship) {
        
        // check for the form 
        // in the session
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + VIEW_PROFILE_MODE +
                "&rand=" + System.currentTimeMillis());
        }
        
        // user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // Pull the original person
        Person originalPerson = form.getProfile().getPerson();
        
        // Create the updated person
        Person updatedPerson = helper.createUpdatedPerson(form, originalPerson, 
                lastName, firstNames, dateOfBirth, gender, citizenship);
        
        // the alert block
        AlertBlock alertBlock = new AlertBlock();
        
        if (helper.validateUpdatedPerson(updatedPerson, alertBlock)) {
            
            // update the person 
            helper.updatePerson(updatedPerson);

            // log an audit
            SystemAuditManager.logAuditAsync(EventTypes.UPDATE_OWN_PERSONAL_INFORMATION, 
                    context.getProfile().getPerson().getUser(), 
                    context.getProfile().getPerson().getId(), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);

            // update the user context
            context.getProfile().setPerson(updatedPerson);

            // save to the 
            // session scope
            saveToSessionScope(request, context);
            
            // update in the form
            form.getProfile().setPerson(updatedPerson);
            
            // save the form to the
            // session scope
            saveToSessionScope(request, form);
            
            // send redirect
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + EDIT_PROFILE_MODE +
                "&result=" + RESULT_PERSONAL_DETAILS_UPDATE_SUCCESS +
                "&rand=" + System.currentTimeMillis());
        }
        
        else {
            
            // save the alert block to
            // the session scope
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            saveToSessionScope(request, alertBlock);
            
            // some validations failed
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + EDIT_PROFILE_MODE +
                "&result=" + RESULT_ERRORS_OCCURED +
                "&rand=" + System.currentTimeMillis());
            
        }
    }
    
    
    @RequestMapping(value = RequestMappings.MY_PROFILE_EDIT, 
            method = RequestMethod.POST,
            params = {"emailAddress", "contactNumber"})
    public ModelAndView updateContactDetailsRequest(HttpServletRequest request) {
        
        // check for the form 
        // in the session
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + VIEW_PROFILE_MODE +
                "&rand=" + System.currentTimeMillis());
        }
        
        // user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // the original contact detail
        ContactDetail originalContactDetail = form.getProfile()
                .getPerson().getContactDetail();
        
        // create the updated contact detail
        ContactDetail updatedContactDetail = helper
                .createUpdatedContactDetail(request, originalContactDetail);
        
        // the alert block
        AlertBlock alertBlock = new AlertBlock();
        
        if (helper.validateUpdatedContactDetail(updatedContactDetail, alertBlock)) {
            
            // update the contact details
            helper.updateContactDetails(updatedContactDetail);
            
            // log an audit
            SystemAuditManager.logAuditAsync(EventTypes.UPDATE_OWN_CONTACT_DETAILS, 
                    context.getProfile().getPerson().getUser(), 
                    updatedContactDetail.getId(), null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);
            
            // update the user context with the
            // updated contact details
            context.getProfile().getPerson().setContactDetail(updatedContactDetail);
            saveToSessionScope(request, context);
            
            // update the form in the
            // current session
            form.getProfile().getPerson().setContactDetail(updatedContactDetail);
            saveToSessionScope(request, form);
            
            // send redirect
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + EDIT_PROFILE_MODE +
                "&result=" + RESULT_CONTACT_DETAILS_UPDATE_SUCCESS +
                "&rand=" + System.currentTimeMillis());
            
        }
        
        else {
        
            // save the alert block to
            // the session scope
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            saveToSessionScope(request, alertBlock);
            
            // some validations failed
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + EDIT_PROFILE_MODE +
                "&result=" + RESULT_ERRORS_OCCURED +
                "&rand=" + System.currentTimeMillis());
        }
        
    }
    
    @RequestMapping(value = RequestMappings.MY_PROFILE_EDIT, 
            method = RequestMethod.POST,
            params = {"physicalAddressLine1", 
                      "physicalAddressLine2",
                      "suburb", 
                      "city", 
                      "areaCode"})
    public ModelAndView updatePhysicalAddressRequest(HttpServletRequest request) {
        
        // check for the form 
        // in the session
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + VIEW_PROFILE_MODE +
                "&rand=" + System.currentTimeMillis());
        }
        
        // user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // the original physical address
        PhysicalAddress originalAddress = form.getProfile().getPerson()
                .getPhysicalAddress();
        
        // create the updated physical address
        PhysicalAddress updatedAddress = helper
                .createUpdatedPhysicalAddress(request, originalAddress);
        
        // the alert block
        AlertBlock alertBlock = new AlertBlock();
        
        if (helper.validateUpdatedPhysicalAddress(updatedAddress, alertBlock)) {
            
            // update the physical address
            helper.updatePhysicalAddress(updatedAddress);
            
            // log an audit
            SystemAuditManager.logAuditAsync(EventTypes.UPDATE_OWN_RESIDENTIAL_ADDRESS, 
                    context.getProfile().getPerson().getUser(), 
                    updatedAddress.getId(), null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);
            
            // update the user context with the
            // updated physical address
            context.getProfile().getPerson().setPhysicalAddress(updatedAddress);
            saveToSessionScope(request, context);
            
            // update the form in the
            // current session
            form.getProfile().getPerson().setPhysicalAddress(updatedAddress);
            saveToSessionScope(request, form);
            
            // send redirect
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + EDIT_PROFILE_MODE +
                "&result=" + RESULT_RESIDENTIAL_ADDRESS_UPDATE_SUCCESS +
                "&rand=" + System.currentTimeMillis());
            
        }
        
        else {
        
            // save the alert block to
            // the session scope
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            saveToSessionScope(request, alertBlock);
            
            // some validations failed
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=" + EDIT_PROFILE_MODE +
                "&result=" + RESULT_ERRORS_OCCURED +
                "&rand=" + System.currentTimeMillis());
        }
        
    }
}
