package za.co.emergelets.xplain2me.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.SHA256Encryptor;
import za.co.emergelets.xplain2me.dao.PersonDAO;
import za.co.emergelets.xplain2me.dao.PersonDAOImpl;
import za.co.emergelets.xplain2me.dao.ProfileDAO;
import za.co.emergelets.xplain2me.dao.ProfileDAOImpl;
import za.co.emergelets.xplain2me.dao.SystemAuditManager;
import za.co.emergelets.xplain2me.dao.UserSaltDAO;
import za.co.emergelets.xplain2me.dao.UserSaltDAOImpl;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.webapp.component.LoginForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.helper.LoginControllerHelper;

@Controller
public class LoginController extends GenericController {
    
    // the form
    private LoginForm form;
    
    // data access objects
    private final UserSaltDAO userSaltDAO;
    private final ProfileDAO profileDAO;
    private final PersonDAO personDAO;
    
    // the controller helper class
    private final LoginControllerHelper helper;
    
    public LoginController() {
        
        this.userSaltDAO = new UserSaltDAOImpl();
        this.profileDAO = new ProfileDAOImpl();
        this.personDAO = new PersonDAOImpl();
        
        this.helper = new LoginControllerHelper();
    }
    
    /**
     * Displays the login page
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView displayLoginPage(HttpServletRequest request) {
        
        // check if the user is already logged in
        UserContext userContext = (UserContext)getFromSessionScope(
                request, UserContext.class);
        if (userContext != null 
                && userContext.getProfile() != null) {
            
            removeFromSessionScope(request, LoginForm.class);
            return helper.redirectToRelevantDashboardPage(userContext.getProfile());
            
        }
        
        // get the form from the session
        form = (LoginForm)getFromSessionScope(request, LoginForm.class);
        if (form == null) {
            form = new LoginForm();
        }
        
        // serialize form to the session
        saveToSessionScope(request, form);
        
        // return the view
        return createModelAndView("login");
        
    }
    
    /**
     * Handles the request to login
     * 
     * @param request
     * @param username
     * @param password
     * @return 
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView handleLoginRequest(HttpServletRequest request, 
            @RequestParam(value = "username")String username, 
            @RequestParam(value = "password")String password) {
        
        // check if there is a session
        form = (LoginForm)getFromSessionScope(request, LoginForm.class);
        if (form == null) {
            return sendRedirect("/login");
        }
        
        // clear all errors
        helper.clearFormErrorsEncountered(form);
        
        // check if the user is not blocked
        if (helper.isUserBlockedFromLoggingIn(form)) {
            
            form.getErrorsEncountered().add("You have exhausted your "
                    + "chances of loggin in.");
            
            saveToRequestScope(request, form);
            return createModelAndView("login");
        }
        
        // check if the username and password are not null
        if ((username == null || username.isEmpty()) || 
                (password == null || password.isEmpty())) {
            form.getErrorsEncountered().add("Either your username and/or password "
                    + "were not provided.");
            helper.accumulateLoginAttempts(form);
            
            saveToRequestScope(request, form);
            return createModelAndView("login");
        }
        
        // collect the username and password
        form.setUsername(username);
        form.setPassword(password); 
        
        // save to the session scope
        saveToRequestScope(request, form);
        
        // redirect to another url handler
        return sendRedirect("/process-login-request");
        
    }
    
    /**
     * Processes the login request
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/process-login-request", method = RequestMethod.GET)
    public ModelAndView processLoginRequest(HttpServletRequest request) {
        
        // check the session
        form = (LoginForm)getFromSessionScope(request, LoginForm.class);
        if (form == null) {
            return sendRedirect("/login");
        }
        
        // clear all errors
        helper.clearFormErrorsEncountered(form); 
        
        // check again, if the user is allowed to login
        if (helper.isUserBlockedFromLoggingIn(form)) {
            
            form.getErrorsEncountered().add("You have exhausted your "
                    + "chances of loggin in.");
            
            saveToRequestScope(request, form);
            return createModelAndView("login");
        }
        
        // retrieve the salt value for hashing the password
        String salt = helper.getSaltValue(form, userSaltDAO);
        
        if (salt == null || salt.isEmpty()) {
            form.getErrorsEncountered().add("It looks like we are "
                    + "unable to authenticate you. Contact your "
                    + "administrator if this continues.");
            helper.accumulateLoginAttempts(form); 
            
            saveToRequestScope(request, form);
            return createModelAndView("login");
        }
        
        // perform a SHA-256 to hash the password
        String hashCode = null;
        try {
            hashCode = SHA256Encryptor.computeSHA256(form.getPassword(), salt);
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            // silent catch
            helper.accumulateLoginAttempts(form);
        }
        
        // authenticate the user
        Profile profile = profileDAO.authenticateProfile(
                form.getUsername(), hashCode);
        
        if (profile == null) {
            
            form.getErrorsEncountered().add("You do not have any "
                    + "active access into the system or either your "
                    + "username and/or password is incorrect.");
            helper.accumulateLoginAttempts(form); 
            
            saveToRequestScope(request, form);
            return createModelAndView("login");
        }
        
        else {
            
            // invalidate the login session
            invalidateCurrentSession(request);
            
            // create a new user context
            UserContext context = new UserContext();
            context.setProfile(profile); 
            context.setTimeUserLoggedIn(new Date());
            
            // get the person details
            context.setPerson(personDAO.getPerson(profile.getUser().getUsername()));
            
            // save the context to the session
            saveToSessionScope(request, context);
            
            // log the login audit async
            SystemAuditManager.logAuditAsync(SystemAuditManager.LOGIN_EVENT, profile.getUser(), 0, 
                    null, request.getRemoteAddr(), request.getHeader("User-Agent"), 0, true);
            
            // redirect to the appropriate page
            return helper.redirectToRelevantDashboardPage(profile);
                
        }
        
    }
    
    /**
     * Destroys all sessions and
     * logouts out the user.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView handleLogoutRequest(HttpServletRequest request) {
        invalidateCurrentSession(request);
        return sendRedirect("/login");
    }
    
}
