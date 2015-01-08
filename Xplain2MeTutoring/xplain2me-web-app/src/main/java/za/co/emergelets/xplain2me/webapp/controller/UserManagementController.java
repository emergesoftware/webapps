package za.co.emergelets.xplain2me.webapp.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.SHA256Encryptor;
import za.co.emergelets.util.mail.EmailSender;
import za.co.emergelets.util.mail.EmailTemplateFactory;
import za.co.emergelets.util.mail.EmailTemplateType;
import za.co.emergelets.xplain2me.bo.validation.ContactDetailValidationRule;
import za.co.emergelets.xplain2me.bo.validation.PersonalInformationValidationRule;
import za.co.emergelets.xplain2me.bo.validation.PhysicalAddressValidationRule;
import za.co.emergelets.xplain2me.bo.validation.UserValidationRule;
import za.co.emergelets.xplain2me.bo.validation.impl.ContactDetailValidationRuleImpl;
import za.co.emergelets.xplain2me.bo.validation.impl.PersonalInformationValidationRuleImpl;
import za.co.emergelets.xplain2me.bo.validation.impl.PhysicalAddressValidationRuleImpl;
import za.co.emergelets.xplain2me.bo.validation.impl.UserValidationRuleImpl;
import za.co.emergelets.xplain2me.dao.CitizenshipDAO;
import za.co.emergelets.xplain2me.dao.CitizenshipDAOImpl;
import za.co.emergelets.xplain2me.dao.EventTypes;
import za.co.emergelets.xplain2me.dao.GenderDAO;
import za.co.emergelets.xplain2me.dao.GenderDAOImpl;
import za.co.emergelets.xplain2me.dao.ProfileDAO;
import za.co.emergelets.xplain2me.dao.ProfileDAOImpl;
import za.co.emergelets.xplain2me.dao.ProfileTypeDAO;
import za.co.emergelets.xplain2me.dao.ProfileTypeDAOImpl;
import za.co.emergelets.xplain2me.dao.SystemAuditManager;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.ContactDetail;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.entity.Person;
import za.co.emergelets.xplain2me.entity.PhysicalAddress;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.entity.ProfileType;
import za.co.emergelets.xplain2me.entity.User;
import za.co.emergelets.xplain2me.entity.UserSalt;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.component.UserManagementForm;
import za.co.emergelets.xplain2me.webapp.controller.helper.UserManagementControllerHelper;

@Controller
public class UserManagementController extends GenericController {
    
    private static final Logger LOG = 
            Logger.getLogger(UserManagementController.class.getName(), null);
    
    @Autowired
    private UserManagementControllerHelper helper;
    
    // result parameter values
    public static final int ADD_NEW_USER_VALIDATION_ERROR_OCCURED = 300;
    public static final int ADD_NEW_USER_INTERNAL_ERROR_OCCURED = 301;
    public static final int ADD_NEW_USER_SUCCESSFUL = 200;
    
    
    public UserManagementController() {
    }
    
    /**
     * Returns a webpage to browse all the users that the current
     * logged in user in this session is authorised to manage or view.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.BROWSE_EXISTING_USERS, 
            method = RequestMethod.GET)
    public ModelAndView showUsersBrowserPage(HttpServletRequest request) {
        
        LOG.info(" ... showing the users browser page ...");
        
        // get the form from the session scope
        // else create new if non existent
        UserManagementForm form = (UserManagementForm)getFromSessionScope(request,
                UserManagementForm.class);
        if (form == null) 
            form = new UserManagementForm();
        
        // get the current logged in user
        // from the session
        UserContext context = (UserContext)getFromSessionScope(request,
                UserContext.class);
        
        // get the list of all user profiles
        // that the current logged in user
        // is allowed to manage
        form.setUserProfiles(null);
        helper.populateUserAuthorisedProfiles(form, context);
        
        // save the form in the 
        // session scope
        saveToSessionScope(request, form);
        
        // resolve any parameters passed
        // through the request
        helper.resolveBrowseExistingUsersParameters(request);
        
        // return a view
        return createModelAndView(Views.BROWSE_EXISTING_USERS);
    }
    
    /**
     * Returns the webpage to add a new user.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.ADD_NEW_USER, 
            method = RequestMethod.GET)
    public ModelAndView showAddNewUserPage(HttpServletRequest request) {
        
        LOG.info(" ... showing page to add a new user ...");
        
        // get the form from the session scope
        // else create new if non existent
        UserManagementForm form = (UserManagementForm)getFromSessionScope(request,
                UserManagementForm.class);
        if (form == null) 
            form = new UserManagementForm();
        else form.resetForm();
        
        // get the current logged in user
        // from the session
        UserContext context = (UserContext)getFromSessionScope(request,
                UserContext.class);
        
        // populate the profile types that
        // the current user is able to add
        ProfileTypeDAO profileTypeDao = new ProfileTypeDAOImpl();
        List<ProfileType> profileTypes = profileTypeDao
                .getProfileTypesAuthorisedByProfile(context.getProfile());
        
        // if no profile types were found that this user is allowed to
        // add then send an error
        if (profileTypes == null || profileTypes.isEmpty()) {
            
            // save the form to the session
            // scope
            saveToSessionScope(request, form);
            
            // create an error alert and save to
            // the request scope
            saveToRequestScope(request, 
                    new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR,
                    "You have no authorisation to add any user profile to the "
                            + "system."));
            
            // return a view
            return createModelAndView(Views.ADD_NEW_USER);
        }
        
        // populate the profile types
        form.setProfileTypes(new TreeMap<Long, ProfileType>());
        for (ProfileType type : profileTypes)
            form.getProfileTypes().put(type.getId(), type);
            
        // populate the citizenships
        CitizenshipDAO citizenshipDao = new CitizenshipDAOImpl();
        form.setCitizenships(new TreeMap<Long, Citizenship>());
        for (Citizenship citizenship : citizenshipDao.getAllCitizenships())
            form.getCitizenships().put(citizenship.getId(), citizenship);
        
        // populate the gender
        GenderDAO genderDao = new GenderDAOImpl();
        form.setGender(new TreeMap<String, Gender>());
        for (Gender gender : genderDao.getAllGender())
            form.getGender().put(gender.getId(), gender);
        
        // save the form to the session
        // scope
        saveToSessionScope(request, form);
        
        // resolve any additional parameters
        helper.resolveAddNewUserAdditionalParameters(request);
        
        // return a view
        return createModelAndView(Views.ADD_NEW_USER);
        
    }
    
    /**
     * Processes the request to add a new user
     * to the system.
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = RequestMappings.ADD_NEW_USER, 
            method = RequestMethod.POST)
    public ModelAndView processCreateNewUserRequest(HttpServletRequest request) 
            throws Exception {
        
        LOG.info(" ... processing request to add a new user ...");
        
        // variables
        HashMap<String, String> parameters = null;
        
        // get the form from the session scope
        // else create new if non existent
        UserManagementForm form = (UserManagementForm)getFromSessionScope(request,
                UserManagementForm.class);
        if (form == null) {
            
            // create parameters
            parameters = new HashMap<>();
            parameters.put("invalid-request", "1");
            
            // send redirect
            return sendRedirect(RequestMappings.ADD_NEW_USER, parameters);
        }
        
        // collect the user profile type
        ProfileType profileType = helper.collectUserProfileType(request, form);
        // collect the personal information
        Person person = helper.collectPerson(request, form);
        // collect the contact detail
        ContactDetail contactDetail = helper.collectContactDetail(request, form);
        //collect the residential address
        PhysicalAddress address = helper.collectPhysicalAddress(request, form);
        // collect the user 
        User user = helper.collectUser(request, form);
        
        // perform business validation
        List<String> errorsEncountered = new ArrayList<>();
        // valiate the personal information
        PersonalInformationValidationRule personValidationRule = 
                new PersonalInformationValidationRuleImpl();
        errorsEncountered.addAll(personValidationRule.validatePersonalInformation(person));
        // validate the contact details
        ContactDetailValidationRule contactDetailValidationRule = 
                new ContactDetailValidationRuleImpl();
        errorsEncountered.addAll(contactDetailValidationRule
                .isContactNumberValid(contactDetail.getCellphoneNumber()));
        errorsEncountered.addAll(contactDetailValidationRule
                .isEmailAddressValid(contactDetail.getEmailAddress()));
        // validate the physical address
        PhysicalAddressValidationRule physicalAddressValidationRule = 
                new PhysicalAddressValidationRuleImpl();
        errorsEncountered.addAll(physicalAddressValidationRule.isPhysicalAddressValid(address));
        // validate the username
        UserValidationRule userValidationRule = new UserValidationRuleImpl();
        errorsEncountered.addAll(userValidationRule.validateUsername(user.getUsername()));
        
        // if there are errors encountered
        if (!errorsEncountered.isEmpty()) {
            
            // create an alert block and 
            // append the errors
            AlertBlock alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            
            for (String error : errorsEncountered)
                alertBlock.addAlertBlockMessage(error);
            
            // save the alert block to the 
            // session scope
            saveToSessionScope(request, alertBlock);
            
            // send redirect
            parameters = new HashMap<>();
            parameters.put("result", String.valueOf(ADD_NEW_USER_VALIDATION_ERROR_OCCURED));
            
            return sendRedirect(RequestMappings.ADD_NEW_USER, parameters);
            
        }
        
        // create new date
        Date now = new Date();
        
        // Generate the verification code
        Random random = new SecureRandom();
        int verificationCode = 1000 + random.nextInt(8999);
        
        // generate a random password
        int randomPasswordInt = 100000 + random.nextInt(899999);
        String saltValue = String.valueOf(System.currentTimeMillis());
        String tempPassword = user.getUsername() + randomPasswordInt;
        
        // fix up the user
        user.setActive(false);
        user.setAdded(now);
        user.setDeactivated(null);
        user.setPassword(SHA256Encryptor.computeSHA256(tempPassword, saltValue));
        
        // create a password salt
        UserSalt passwordSalt = new UserSalt();
        passwordSalt.setUser(user);
        passwordSalt.setValue(saltValue);
        passwordSalt.setDateCreated(now);
        
        // if no errors were found - restructure the entities
        person.setPhysicalAddress(address);
        person.setContactDetail(contactDetail);
        person.setUser(user);
        
        // create new profile
        Profile profile = new Profile();
        profile.setDateCreated(now);
        profile.setVerified(false);
        profile.setVerificationCode(String.valueOf(verificationCode));
        profile.setProfileType(profileType);
        profile.setPerson(person);
        
        // persist the profile to the
        // datastore
        ProfileDAO profileDao = new ProfileDAOImpl();
        profile = profileDao.persistProfile(profile, passwordSalt);
        
        // if persistence failed
        if (profile == null || profile.getId() == 0) {
            
            // create a new alert block
            AlertBlock alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_WARNING, 
                        "An internal error occured that prevented the new "
                                + "user from being created. Please try again.");
            
            // save the alert block to the 
            // session scope
            saveToSessionScope(request, alertBlock);
            
            // send redirect
            parameters = new HashMap<>();
            parameters.put("result", String.valueOf(ADD_NEW_USER_INTERNAL_ERROR_OCCURED));
            
            return sendRedirect(RequestMappings.ADD_NEW_USER, parameters);
            
        }
        
        // get the current user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // log an audit 
        SystemAuditManager.logAuditAsync(
                EventTypes.CREATE_NEW_USER_PROFILE, 
                context.getProfile().getPerson().getUser(), 
                profile.getId(), 
                null, 
                request.getRemoteAddr(), 
                request.getHeader("User-Agent"), 
                0, true);
        
        // build the verification link
        StringBuilder link = new StringBuilder();
        link.append("http").append("://")
                .append("portal.xplain2me.co.za")
                .append(request.getContextPath())
                .append(RequestMappings.VERIFY_OWN_USER_PROFILE);
        
        // set up email template injection
        // values
        final Map<String, Object> values = new HashMap<>();
        values.put("name", profile.getPerson().getFirstNames().split(" ")[0]);
        values.put("verification_code", profile.getVerificationCode());
        values.put("link", link.toString());
        
        // send the new user an email for
        // verification instructions
        final String emailAddress = profile.getPerson().getContactDetail()
                .getEmailAddress().toLowerCase();
        
        new Thread(
                new Runnable() {

            @Override
            public void run() {
                
                try {
                    
                    EmailSender email = new EmailSender();
                    email.setSubject("Added As New User | Xplain2Me Tutoring"); 
                    email.setToAddress(emailAddress);
                    email.setHtmlBody(true);

                    email.sendEmail(EmailTemplateFactory.injectValuesIntoEmailTemplate(
                                        EmailTemplateFactory.getTemplateByType(
                                            EmailTemplateType.SendNewUserVerificationInstructions), 
                                    values));
                }
                catch (IOException io) {
                    LOG.log(Level.WARNING," the instructions email to:{0}"
                            + " failed to " + "be sent, error: {1}", 
                            new Object[] { emailAddress, io.getMessage() });
                }
            }
        }
        ).start();
        
        // send redirect
        parameters = new HashMap<>();
        parameters.put("result", String.valueOf(ADD_NEW_USER_SUCCESSFUL));
        parameters.put("profile-id", String.valueOf(profile.getId()));
        parameters.put("profile-type", profile.getProfileType().getDescription());
        
        return sendRedirect(RequestMappings.ADD_NEW_USER, parameters);
    }
}
