package za.co.emergelets.xplain2me.webapp.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.NumericUtils;
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
import za.co.emergelets.xplain2me.dao.ContactDetailDAO;
import za.co.emergelets.xplain2me.dao.ContactDetailDAOImpl;
import za.co.emergelets.xplain2me.dao.EventTypes;
import za.co.emergelets.xplain2me.dao.GenderDAO;
import za.co.emergelets.xplain2me.dao.GenderDAOImpl;
import za.co.emergelets.xplain2me.dao.PersonDAO;
import za.co.emergelets.xplain2me.dao.PersonDAOImpl;
import za.co.emergelets.xplain2me.dao.PhysicalAddressDAO;
import za.co.emergelets.xplain2me.dao.PhysicalAddressDAOImpl;
import za.co.emergelets.xplain2me.dao.ProfileDAO;
import za.co.emergelets.xplain2me.dao.ProfileDAOImpl;
import za.co.emergelets.xplain2me.dao.ProfileTypeDAO;
import za.co.emergelets.xplain2me.dao.ProfileTypeDAOImpl;
import za.co.emergelets.xplain2me.dao.SystemAuditManager;
import za.co.emergelets.xplain2me.dao.UserDAO;
import za.co.emergelets.xplain2me.dao.UserDAOImpl;
import za.co.emergelets.xplain2me.dao.UserSaltDAO;
import za.co.emergelets.xplain2me.dao.UserSaltDAOImpl;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.ContactDetail;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.entity.Person;
import za.co.emergelets.xplain2me.entity.PhysicalAddress;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.entity.ProfileType;
import za.co.emergelets.xplain2me.entity.User;
import za.co.emergelets.xplain2me.entity.UserSalt;
import za.co.emergelets.xplain2me.webapp.ObjectFactory;
import za.co.emergelets.xplain2me.webapp.ObjectFactoryImpl;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.ProfileManagementForm;
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
    public ModelAndView createNewUserProfile(HttpServletRequest request) 
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
                .validateContactDetail(contactDetail));
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
        values.put("username", user.getUsername());
        
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
    
    /**
     * Delivers the page to edit a user's profile
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.EDIT_USER_PROFILE, 
            method = RequestMethod.GET)
    public ModelAndView showEditUserProfilePage(HttpServletRequest request) {
        
        LOG.info(" .. deliver the edit user profile page ...");
        
        // variables
        Map<String, String> parameters = null;
        
        // check if the user-id parameter exists
        String profileId = getParameterValue(request, "profile-id");
        if (profileId == null || profileId.isEmpty() || 
                NumericUtils.isNaN(profileId)) {
            
            LOG.warning("... the profile-id parameter is not in the HTTP request ..."); 
            
            // send a redirect to the dashboard
            parameters = new HashMap<>();
            parameters.put("invalid-request", "1");
            
            return sendRedirect(RequestMappings.DASHBOARD_OVERVIEW, parameters);
        }
        
        // check the form
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        if (form == null)
            form = new ProfileManagementForm();
        else
            form.resetForm();
        
        // get the gender types
        GenderDAO genderDao = new GenderDAOImpl();
        form.setGender(new TreeMap<String, Gender>());
        for (Gender gender : genderDao.getAllGender())
            form.getGender().put(gender.getId(), gender);
        
        // get the citizenship
        CitizenshipDAO citizenshipDao = new CitizenshipDAOImpl();
        form.setCitizenship(new TreeMap<Long, Citizenship>()); 
        for (Citizenship citizenship : citizenshipDao.getAllCitizenships())
            form.getCitizenship().put(citizenship.getId(), citizenship);
        
        // get the current user context
        UserContext context = (UserContext)getFromSessionScope(request,
                UserContext.class);
        
        // get the user profile with the 
        // id from the data store
        ProfileDAO profileDao = null;
        
        if (form.getProfile() == null || 
                form.getProfile().getId() != Long.parseLong(profileId)) {
            
            profileDao = new ProfileDAOImpl();
            form.setProfile(profileDao.getProfileById(Long.parseLong(profileId),
                context.getProfile().getProfileType().getId()));
        }
        
        // if no profile was found
        if (form.getProfile() == null) {
            
            // create an error alert into the
            // request scope
            saveToRequestScope(request, new AlertBlock(
                    AlertBlock.ALERT_BLOCK_WARNING, 
                    "It could be that the profile you are looking for does not " +
                    "exist or you are attempting to access a profile above your " + 
                    "allowed privileges."));
            
        }
        
        // check if there is any alerts in the
        // session - then transfer to request scope
        AlertBlock alertBlock = (AlertBlock)getFromSessionScope(
                request, AlertBlock.class);
        if (alertBlock != null) {
            removeFromSessionScope(request, AlertBlock.class);
            saveToRequestScope(request, alertBlock);
        }
        
        // save the form to the session scope
        saveToSessionScope(request, form);
            
        // return a view
        return createModelAndView(Views.EDIT_USER_PROFILE);
        
    }
    
    /**
     * HANDLES THE REQUEST TO
     * UPDATE THE PERSONAL INFORMATION
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.EDIT_USER_PROFILE, 
            method = RequestMethod.POST,
            params = {"lastName", "firstNames", "dateOfBirth",
                      "gender", "citizenship"})
    public ModelAndView updatePersonalInformation(HttpServletRequest request) {
        
        // check for the form 
        // in the session
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        // if no session was found - send redirect
        // to the user's dashboard
        if (form == null) {
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.BROWSE_EXISTING_USERS);
        }
        
        // the object factory
        ObjectFactory factory = new ObjectFactoryImpl(request);
        // get the current user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // pull the original person
        Person originalPerson = form.getProfile().getPerson();
        // create the updated person
        Person updatedPerson = factory.createPersonEntity(
                factory.createCitizenshipEntity(), factory.createGenderEntity());
        
        // update the person ID, contact details,
        // address and user
        updatedPerson.setId(originalPerson.getId());
        updatedPerson.setContactDetail(originalPerson.getContactDetail());
        updatedPerson.setPhysicalAddress(originalPerson.getPhysicalAddress());
        updatedPerson.setUser(originalPerson.getUser());
        
        // validate personal information against the rules
        PersonalInformationValidationRule rules = new PersonalInformationValidationRuleImpl();
        List errorsEncountered = rules.validatePersonalInformation(updatedPerson);
        
        // if validation passed
        if (errorsEncountered.isEmpty()) {
            
            // update the person in the
            // data store
            PersonDAO personDao = new PersonDAOImpl();
            updatedPerson = personDao.updatePerson(updatedPerson);

            // log an audit
            SystemAuditManager.logAuditAsync(EventTypes.AMEND_USER_PROFILE, 
                    context.getProfile().getPerson().getUser(), 
                    form.getProfile().getId(), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);

            // update in the form
            form.getProfile().setPerson(updatedPerson);
            
            // create an alert 
            // to the session scope
            saveToSessionScope(request, new AlertBlock()
                .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                .addAlertBlockMessage("The user profile personal information " +
                                      "was updated successfully."));
        }
        
        else {
            
            // save the alert block to
            // the session scope
            saveToSessionScope(request, new AlertBlock()
                .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                .setAlertBlockMessages(errorsEncountered));
        }
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        // send a redirect
        Map<String, String> parameters = new HashMap<>();
        parameters.put("profile-id", String.valueOf(form.getProfile().getId()));
        return sendRedirect(RequestMappings.EDIT_USER_PROFILE, parameters);
    }
    
    /**
     * Processes the request to update the
     * contact details of a user.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.EDIT_USER_PROFILE,
            method = RequestMethod.POST,
            params = {"emailAddress", "contactNumber"})
    public ModelAndView updateContactDetails(HttpServletRequest request) {
        
        // check for the form 
        // in the session
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        // if no session was found - send redirect
        // to the user's dashboard
        if (form == null) {
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.BROWSE_EXISTING_USERS);
        }
        
        // create an instance of the object
        // factory
        ObjectFactory factory = new ObjectFactoryImpl(request);
        // get the current user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // get the original contact detail entity
        ContactDetail originalContactDetail = form.getProfile()
                .getPerson().getContactDetail();
        // create an instance of the updated
        // contact detail
        ContactDetail updatedContactDetail = factory.createContactDetailEntity();
        // copy some details onto the updated
        // contact detail
        updatedContactDetail.setId(originalContactDetail.getId()); 
        
        // create an instance of the validation rules
        ContactDetailValidationRule rule = new ContactDetailValidationRuleImpl();
        // validate the updated contact detail
        List<String> errorsEncountered = rule.validateContactDetail(updatedContactDetail);
        
        // if no errors occured
        if (errorsEncountered.isEmpty()) {
            
            // update the contact detail
            // in the data store
            ContactDetailDAO contactDetailDao = new ContactDetailDAOImpl();
            updatedContactDetail = contactDetailDao.updateContactDetail(updatedContactDetail);
            
            // log an audit
            SystemAuditManager.logAuditAsync(EventTypes.AMEND_USER_PROFILE, 
                    context.getProfile().getPerson().getUser(), 
                    form.getProfile().getId(), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);
            
            // update the contact details in the
            // form
            form.getProfile().getPerson().setContactDetail(updatedContactDetail); 
            
            // create an alert 
            // to the session scope
            saveToSessionScope(request, new AlertBlock()
                .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                .addAlertBlockMessage("The user profile contact details " +
                                      "were updated successfully."));
        }
        
        else {
            
            // save the alert block to
            // the session scope
            saveToSessionScope(request, new AlertBlock()
                .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                .setAlertBlockMessages(errorsEncountered));
            
        }
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        // send a redirect
        Map<String, String> parameters = new HashMap<>();
        parameters.put("profile-id", String.valueOf(form.getProfile().getId()));
        return sendRedirect(RequestMappings.EDIT_USER_PROFILE, parameters);
        
    }
    
    /**
     * Processes the request to update the residential
     * address of a user.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.EDIT_USER_PROFILE, 
            method = RequestMethod.POST,
            params = {"physicalAddressLine1", 
                      "physicalAddressLine2",
                      "suburb", 
                      "city", 
                      "areaCode"})
    public ModelAndView updatePhysicalAddress(HttpServletRequest request) {
        
        // check for the form 
        // in the session
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        // if no session was found - send redirect
        // to the user's dashboard
        if (form == null) {
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.BROWSE_EXISTING_USERS);
        }
        
        // create an instance of the object
        // factory
        ObjectFactory factory = new ObjectFactoryImpl(request);
        // get the current user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // get the original physical address
        PhysicalAddress originalAddress = form.getProfile()
                .getPerson().getPhysicalAddress();
        // create an instance of the updated 
        // physical address
        PhysicalAddress updatedAddress = factory.createPhysicalAddressEntity();
        // update the ID
        updatedAddress.setId(originalAddress.getId());
        
        // create an instance of the validation rules
        PhysicalAddressValidationRule rule = new PhysicalAddressValidationRuleImpl();
        List<String> errorsEncountered = rule.isPhysicalAddressValid(updatedAddress);
        
        // if there were no errors
        // encountered
        if (errorsEncountered.isEmpty()) {
            
            // update the physical address in the 
            // data store
            PhysicalAddressDAO dao = new PhysicalAddressDAOImpl();
            updatedAddress = dao.updatePhysicalAddress(updatedAddress);
            
            // log an event
            SystemAuditManager.logAuditAsync(EventTypes.AMEND_USER_PROFILE, 
                    context.getProfile().getPerson().getUser(), 
                    form.getProfile().getId(), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);
            
            // update the form with the new
            // updated entity
            form.getProfile().getPerson().setPhysicalAddress(updatedAddress);
            
            // create an alert 
            // to the session scope
            saveToSessionScope(request, new AlertBlock()
                .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                .addAlertBlockMessage("The user profile physical address " +
                                      "was updated successfully."));
        }
        
        else {
            
            // save the alert block to
            // the session scope
            saveToSessionScope(request, new AlertBlock()
                .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                .setAlertBlockMessages(errorsEncountered));
            
        }
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        // send a redirect
        Map<String, String> parameters = new HashMap<>();
        parameters.put("profile-id", String.valueOf(form.getProfile().getId()));
        return sendRedirect(RequestMappings.EDIT_USER_PROFILE, parameters);
        
    }

    /**
     * Delivers a webpage showing full details of the
     * user profile.
     * 
     * @param request
     * @param profileId
     * @return 
     */
    @RequestMapping(value = RequestMappings.VIEW_USER_PROFILE, 
            method = RequestMethod.GET, 
            params = { "profile-id" })
    public ModelAndView showUserProfileDetails(
            HttpServletRequest request, 
            @RequestParam(value = "profile-id")String profileId) {
        
        // validate the request parameters
        if (profileId == null || profileId.isEmpty() || 
                NumericUtils.isNaN(profileId)) {
            
            // create an alert
            saveToRequestScope(request, new AlertBlock()
                            .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                            .addAlertBlockMessage("The user profile account with the "
                                    + "specified ID does not appear authentic."));
            // return a view
            return createModelAndView(Views.VIEW_USER_PROFILE);
        }
        
        // get the user context
        UserContext context = (UserContext)getFromSessionScope(request, 
                UserContext.class);
        
        // get the specified profile from the
        // data store
        ProfileDAO profileDao = new ProfileDAOImpl();
        Profile profile = profileDao.getProfileById(Long.parseLong(profileId),
                context.getProfile().getProfileType().getId());
        
        // if no profile was not found
        if (profile == null) {
            
            // create an alert
            // to the request scope
            saveToRequestScope(request, new AlertBlock()
                            .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                            .addAlertBlockMessage("The user profile account with the " +
                                    "specified ID does not exist or you're not " + 
                                    "authorised to view this user profile.")); 
        }
        
        else {
            
            // save the profile to the 
            // request scope
            saveToRequestScope(request, profile);
            
        }
        
        // return a view
        return createModelAndView(Views.VIEW_USER_PROFILE);
    }
    
    /**
     * Delivers the page to change the user profile type
     * of a user account.
     * 
     * @param request
     * @param profileId
     * @return 
     */
    @RequestMapping(value = RequestMappings.CHANGE_USER_PROFILE_TYPE, 
            method = RequestMethod.GET, 
            params = { "profile-id" })
    public ModelAndView showChangeUserProfileTypePage(
            HttpServletRequest request,
            @RequestParam(value = "profile-id")String profileId) {
        
        // check if there is any form
        // in the session scope
        UserManagementForm form = (UserManagementForm)
                getFromSessionScope(request, UserManagementForm.class);
        if (form == null)
            form = new UserManagementForm();
        else
            form.resetForm();
        
        //check if the parameter is valid
        if (profileId == null || profileId.isEmpty() || 
                NumericUtils.isNaN(profileId)) {
            
            LOG.warning(" the profileId does not appear to be valid ..."); 
            
            // create an alert onto the
            // request scope
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                    .addAlertBlockMessage("The profile sequence ID does not appear to "
                            + "have a valid format."));
            // save the form to the
            // session scope
            saveToSessionScope(request, form);
            
            // return a view
            return createModelAndView(Views.CHANGE_USER_ACCOUNT_PROFILE_TYPE); 
        }
        
        // get the user context from the
        // session scope
        UserContext context = (UserContext)getFromSessionScope(request,
                UserContext.class);
        
        // get the profile from the 
        // data store
        ProfileDAO profileDao = new ProfileDAOImpl();
        Profile profile = profileDao.getProfileById(Long.parseLong(profileId),
                context.getProfile().getProfileType().getId());
        
        // if there is no profile found
        if (profile == null) {
            
            // create an alert onto the
            // request scope
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                    .addAlertBlockMessage("There is no user profile that could be found or " + 
                            "you are not permitted to access this user profile."));
            
        }
        
        // if a profile was found 
        else {
        
            // get the list of profile types
            ProfileTypeDAO profileTypeDao = new ProfileTypeDAOImpl();
            List<ProfileType> profileTypes = profileTypeDao
                    .getProfileTypesAuthorisedByProfile(context.getProfile());
            
            // if the list of profile types is empty...
            if (profileTypes == null || profileTypes.isEmpty()) {
                
                // create an alert onto the
                // request scope
                saveToRequestScope(request, new AlertBlock()
                                .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                                .addAlertBlockMessage("You are not allowed to change a profile " + 
                                        "type of any user on the system."));
            }
            
            else {
                
                // save the profile and the types
                // onto the form
                form.setProfile(profile);
                
                form.setProfileTypes(new TreeMap<Long, ProfileType>()); 
                for (ProfileType type : profileTypes)
                    form.getProfileTypes().put(type.getId(), type);
                
            }
            
        }
        
        // resolve any alerts in the
        // session scope
        AlertBlock alertBlock = (AlertBlock)
                getFromSessionScope(request, AlertBlock.class);
        if (alertBlock != null) {
            
            // get the alert block from the request scope
            AlertBlock alertBlockFromRequestScope = (AlertBlock)
                    getFromRequestScope(request, AlertBlock.class);
            
            // create a new one onto the 
            //request scope if none
            if (alertBlockFromRequestScope == null) {
                saveToRequestScope(request, alertBlock);
            }
            // else append the messages to the 
            // scoped alert block
            else {
                for (String message : alertBlock.getAlertBlockMessages())
                    alertBlockFromRequestScope.addAlertBlockMessage(message);
                
                saveToRequestScope(request, alertBlockFromRequestScope);
            }
            
            // remove alert block from the session
            // scope
            removeFromSessionScope(request, AlertBlock.class);
        }
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);

        // return a view
        return createModelAndView(Views.CHANGE_USER_ACCOUNT_PROFILE_TYPE);
    }
    
    /**
     * Processes the request to change the user's profile type
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.CHANGE_USER_PROFILE_TYPE, 
            method = RequestMethod.POST)
    public ModelAndView changeUserProfileType(HttpServletRequest request) {
        
        // check if there is a form in the
        // session scope
        UserManagementForm form = (UserManagementForm)
                getFromSessionScope(request, UserManagementForm.class);
        
        // if no session was found - send redirect
        // to the user's dashboard
        if (form == null || form.getProfile() == null) {
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.BROWSE_EXISTING_USERS);
        }
        
        // get the user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // get the request parameters
        ProfileType updatedProfileType = form.getProfileTypes()
                .get(Long.parseLong(getParameterValue(request, "profileType")));
        String password = getParameterValue(request, "password");
        
        // validate the parameters
        List<String> errorsEncountered = new ArrayList<>();
        
        // validate the password length
        if (password == null || 
                (password.length() < 6 || password.length() > 24))
            errorsEncountered.add("The password provided must be between "
                    + "6 - 24 characters long.");
        
        // check if the password entered is
        // matching
        UserSaltDAO userSaltDao = new UserSaltDAOImpl();
        UserSalt userSalt = userSaltDao.getUserSalt(
                context.getProfile().getPerson().getUser().getUsername());
        
        if (userSalt == null) {
            errorsEncountered.add("You do not seem to have any legitimate"
                    + " access onto the system.");
        }
        
        else {
            String hashedPassword = helper.computeSHA256Hashword(password, userSalt.getValue());
            if (!hashedPassword.equals(context.getProfile().getPerson().getUser().getPassword())) {
                errorsEncountered.add("The password provided does not match your profile account.");
            }
        }
        
        if (updatedProfileType == null) {
            errorsEncountered.add("The selected profile type is not valid or does not "
                    + "exist within your scope.");
        }
        
        // if there were errors encountered
        if (!errorsEncountered.isEmpty()) {
            
            // create an alert onto the
            // session scope
            saveToSessionScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                    .setAlertBlockMessages(errorsEncountered));
            
            // send a redirect 
            return sendRedirect(RequestMappings.CHANGE_USER_PROFILE_TYPE + 
                    "?profile-id=" + form.getProfile().getId()); 
        }
        
        // get the original profile from the 
        // form
        Profile profile = form.getProfile();
        // update the profile type
        profile.setProfileType(updatedProfileType);
        
        // save the profile to the 
        // data store
        ProfileDAO profileDao = new ProfileDAOImpl();
        profileDao.mergeProfile(profile);
        
        // log an event
        SystemAuditManager.logAuditAsync(EventTypes.ASSIGN_USER_TO_PROFILE, 
                    context.getProfile().getPerson().getUser(), 
                    form.getProfile().getId(), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        // create an alert onto the
        // session scope
        saveToSessionScope(request, new AlertBlock()
                .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                .addAlertBlockMessage("The user profile account was re-assigned "
                        + "to the new profile type successfully.")); 
        
        // send s redirect
        return sendRedirect(RequestMappings.CHANGE_USER_PROFILE_TYPE + 
                    "?profile-id=" + form.getProfile().getId());
        
    }
    
    /**
     * Delivers the webpage to activate or block a user profile
     * @param request
     * @param profileId
     * @return 
     */
    @RequestMapping(value = RequestMappings.ACTIVATE_OR_BLOCK_USER, 
            method = RequestMethod.GET, 
            params = { "profile-id" })
    public ModelAndView showActivateOrBlockUserPage(
            HttpServletRequest request, 
            @RequestParam(value = "profile-id")String profileId) {
        
        // check if there is any form
        // in the session scope
        UserManagementForm form = (UserManagementForm)
                getFromSessionScope(request, UserManagementForm.class);
        if (form == null)
            form = new UserManagementForm();
        else
            form.resetForm();
        
        //check if the parameter is valid
        if (profileId == null || profileId.isEmpty() || 
                NumericUtils.isNaN(profileId)) {
            
            LOG.warning(" the profileId does not appear to be valid ..."); 
            
            // create an alert onto the
            // request scope
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                    .addAlertBlockMessage("The profile sequence ID does not appear to "
                            + "have a valid format."));
            // save the form to the
            // session scope
            saveToSessionScope(request, form);
            
            // return a view
            return createModelAndView(Views.ACTIVATE_OR_BLOCK_USER); 
        }
        
        // get the user context from the
        // session scope
        UserContext context = (UserContext)getFromSessionScope(request,
                UserContext.class);
        
        // get the profile from the 
        // data store
        ProfileDAO profileDao = new ProfileDAOImpl();
        Profile profile = profileDao.getProfileById(Long.parseLong(profileId),
                context.getProfile().getProfileType().getId());
        
        // if there is no profile found
        if (profile == null) {
            
            // create an alert onto the
            // request scope
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                    .addAlertBlockMessage("There is no user profile that could be found or " + 
                            "you are not permitted to access this user profile."));
            
        }
        
        // if a profile was found 
        else {

            // save the profile and the types
            // onto the form
            form.setProfile(profile);
            
        }
        
        // resolve any alerts in the
        // session scope
        AlertBlock alertBlock = (AlertBlock)
                getFromSessionScope(request, AlertBlock.class);
        if (alertBlock != null) {  
            
            // create a new one onto the 
            //request scope if none
            saveToRequestScope(request, alertBlock);
            
            // remove alert block from the session
            // scope
            removeFromSessionScope(request, AlertBlock.class);
        }
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        return createModelAndView(Views.ACTIVATE_OR_BLOCK_USER);
    }
    
    /**
     * Processes the request to activate or block a user.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.ACTIVATE_OR_BLOCK_USER, 
            method = RequestMethod.POST)
    public ModelAndView activateOrBlockUserProfile(HttpServletRequest request) {
        
        // check if there is a form in the
        // session scope
        UserManagementForm form = (UserManagementForm)
                getFromSessionScope(request, UserManagementForm.class);
        
        // if no session was found - send redirect
        // to the user's dashboard
        if (form == null || form.getProfile() == null) {
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.BROWSE_EXISTING_USERS);
        }
        
        // get the user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        
        // validate the parameters
        String activateOrBlockUser = getParameterValue(request, "activateOrBlockUser");
        
        if (activateOrBlockUser == null || 
                activateOrBlockUser.isEmpty()) { 
            
            // create an alert onto the
            // session scope
            saveToSessionScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                    .addAlertBlockMessage("You did not tick off if this " + 
                            "user should be active or blocked."));
            
            // send a redirect 
            return sendRedirect(RequestMappings.ACTIVATE_OR_BLOCK_USER + 
                    "?profile-id=" + form.getProfile().getId()); 
            
        }
        
        // set the user as active or blocked
        boolean userUpdated = false;
        long eventType = 0L;
        String message = "The user account profile was successfully set to: ";
        
        User user = form.getProfile().getPerson().getUser();
        
        if (activateOrBlockUser.equalsIgnoreCase("activate")) {
            
            userUpdated = true;
            eventType = EventTypes.ACTIVATE_USER_PROFILE;
            message += "ACTIVE.";
            
            user.setActive(true);
            user.setDeactivated(null);
            
        }
        
        else if (activateOrBlockUser.equalsIgnoreCase("block")) {
            
            userUpdated = true;
            eventType = EventTypes.DEACTIVATE_USER_PROFILE;
            message += "INACTIVE.";
            
            user.setActive(false);
            user.setDeactivated(new Date());
        }
        
        // update the user in the
        // data store
        if (userUpdated) {
            
            UserDAO userDao = new UserDAOImpl();
            userDao.updateUser(user);
            
            // log an event
            SystemAuditManager.logAuditAsync(eventType, 
                    context.getProfile().getPerson().getUser(), 
                    user.getId(), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);
            
            // create new alert into the
            // session scope
            saveToSessionScope(request, new AlertBlock() 
                        .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                        .addAlertBlockMessage(message));
           
        }
        
        // if the user was not updated
        else {
            
            // create new alert into the
            // session scope
            saveToSessionScope(request, new AlertBlock() 
                        .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                        .addAlertBlockMessage("Could not update the user as no "
                                + "selection was made."));
            
        }
        
        
        return sendRedirect(RequestMappings.ACTIVATE_OR_BLOCK_USER + 
                "?profile-id=" + form.getProfile().getId());
    }
    
}
