package za.co.emergelets.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.SHA256Encryptor;
import za.co.emergelets.util.SaltGenerator;
import za.co.emergelets.xplain2me.dao.EventTypes;
import za.co.emergelets.xplain2me.dao.SystemAuditManager;
import za.co.emergelets.xplain2me.entity.User;
import za.co.emergelets.xplain2me.entity.UserSalt;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.PasswordSet;
import za.co.emergelets.xplain2me.webapp.component.ProfileManagementForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.helper.ProfileManagementControllerHelper;

@Controller
public class ProfileManagementController extends GenericController implements Serializable {
    
    private static final Logger LOG = 
            Logger.getLogger(ProfileManagementController.class.getName(), null);
    
    public static final int VIEW_PROFILE_MODE = 0;
    public static final int EDIT_PROFILE_MODE = 1;
 
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
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        // return a view
        if (form.getProfileMode() == EDIT_PROFILE_MODE) {
            return createModelAndView("profile", form.getProfile(), Views.EDIT_OWN_PROFILE);
        }
        else
            return createModelAndView(Views.VIEW_OWN_PROFILE);
    }
    
    /**
     * HANDLES THE REQUEST TO UPDATE THE 
     * USER LOGIN CREDENTIALS
     * 
     * @param request
     * @param username
     * @param currentPassword
     * @param newPassword
     * @param reEnterNewPassword
     * @return 
     * @throws java.lang.Exception 
     */
    @RequestMapping(value = RequestMappings.EDIT_MY_CREDENTIALS, 
            method = RequestMethod.POST, 
            params = {"username", "currentPassword", 
                    "newPassword", "reEnterNewPassword"})
    public ModelAndView updateUserCredentialsRequest(
            HttpServletRequest request, 
            @RequestParam(value = "username")String username,
            @RequestParam(value = "currentPassword")String currentPassword,
            @RequestParam(value = "newPassword")String newPassword, 
            @RequestParam(value = "reEnterNewPassword")String reEnterNewPassword) 
    
    throws Exception {
        
        // check for the form 
        // in the session
        ProfileManagementForm form = (ProfileManagementForm)
                getFromSessionScope(request, ProfileManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... no form found in the session scope ...");
            return sendRedirect(RequestMappings.MY_PROFILE_EDIT + 
                "?mode=0" + 
                "&rand=" + System.currentTimeMillis());
        }
        
        // pull the original user
        User originalUser = form.getProfile().getPerson().getUser();
        
        // create the updated user
        User updatedUser = helper.getUpdatedUserInformation(request, form);
        PasswordSet passwordSet = form.getPasswordSet();
        
        // the alert block
        AlertBlock alertBlock = new AlertBlock();
        
        // perform some validations first
        if (helper.validateUpdatedUser(form, updatedUser, passwordSet, alertBlock)) {
            
            // if the password has to be updated
            if (passwordSet != null) {
                
                // generate a new salt value
                String saltValue = SaltGenerator.generateSaltValue();
                
                // create a new password
                String hashedPassword = SHA256Encryptor.computeSHA256(passwordSet
                        .getEnteredPassword(), saltValue);
                
                // update the passwords
                helper.updateUserPassword(originalUser.getUsername(), hashedPassword,
                        saltValue);
                
                // log an event
                SystemAuditManager.logAuditAsync(EventTypes.UPDATE_OWN_USER_PASSWORD, 
                        originalUser, 0, null, 
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
                helper.updateUsername(updatedUser);
                
                // log an audit
                SystemAuditManager.logAuditAsync(EventTypes.UPDATE_OWN_USERNAME, 
                        originalUser, 0, null, 
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
                "?mode=1" + 
                "&rand=" + System.currentTimeMillis());
        }
        
        else {
            
            // save the alert block to
            // the request scope
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            saveToRequestScope(request, alertBlock);
            
            // some validations failed
            return createModelAndView(Views.EDIT_OWN_PROFILE);
            
        }
    }
}
