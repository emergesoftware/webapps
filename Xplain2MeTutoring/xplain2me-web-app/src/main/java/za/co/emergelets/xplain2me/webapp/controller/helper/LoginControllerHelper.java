package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.dao.UserSaltDAO;
import za.co.emergelets.xplain2me.entity.ProfileTypeUrlPermissions;
import za.co.emergelets.xplain2me.entity.UserSalt;
import za.co.emergelets.xplain2me.webapp.component.LoginForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;
import za.co.emergelets.xplain2me.webapp.controller.RequestMappings;

@Component
public class LoginControllerHelper extends GenericController {
    
    public LoginControllerHelper() {
    }
    
    /**
     * Clears all form errors
     * 
     * @param form 
     */
    public void clearFormErrorsEncountered(LoginForm form) {
        if (form != null) {
            
            if (form.getErrorsEncountered() == null)
                form.setErrorsEncountered(new ArrayList<String>());
            
            form.getErrorsEncountered().clear();
        }
    }
    
    /**
     * Accumulates the login attempts
     * @param form 
     */
    public void accumulateLoginAttempts(LoginForm form) {
        if (form != null) {
            form.setLoginAttempts(form.getLoginAttempts() + 1);
        }
    }
    
    /**
     * Determines if the user is currently locked
     * from logging in
     * 
     * @param form
     * @return 
     */
    public boolean isUserBlockedFromLoggingIn(LoginForm form) {
        
        if (form == null) return true;
        
        return (form.getLoginAttempts() >= LoginForm.MAX_LOGIN_ATTEMPTS || 
                form.isBlocked());
        
    }
    
    /**
     * Gets the salt value for the user
     * 
     * @param form
     * @param userSaltDAO
     * @return 
     */
    public String getSaltValue(LoginForm form, UserSaltDAO userSaltDAO) {
        
        if (form == null || userSaltDAO == null)
            return null;
        
        UserSalt salt = userSaltDAO.getUserSalt(form.getUsername()); 
        if (salt != null) {
            return salt.getValue();
        }
        
        return null;
        
    } 
    
    /**
     * Redirects to the relevant dashboard page
     * 
     * @return 
     */
    public ModelAndView redirectToRelevantDashboardPage() {  
        
        String redirectUrl = RequestMappings.DASHBOARD_OVERVIEW;
        return sendRedirect(redirectUrl);
    }
    
    /**
     * PREPARES A LIST OF URLS THAT THE CURRENT
     * LOGGED IN USER IS ALLOWED TO ACCESS IN THE
     * WEB APPLICATION.
     * 
     * @param context 
     */
    public void buildUserAllowedUrlsList(UserContext context) {
        
        List<ProfileTypeUrlPermissions> permissions = 
                context.getProfileTypeUrlPermissions();
        
        if (permissions != null && permissions.isEmpty() == false) {
            
            List<String> allowedUrls = new ArrayList<>();
            
            for (ProfileTypeUrlPermissions permission : permissions) {
                allowedUrls.add(permission.getMenuItem().getRelativeUrl());
            }
            
            context.setPermittedUrls(allowedUrls);
        }
        
    }
}
