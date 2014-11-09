package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.util.ArrayList;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.dao.UserSaltDAO;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.entity.ProfileType;
import za.co.emergelets.xplain2me.entity.UserSalt;
import za.co.emergelets.xplain2me.webapp.component.LoginForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

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
     * @param profile
     * @return 
     */
    public ModelAndView redirectToRelevantDashboardPage(Profile profile) {  
        
        String redirectUrl = null;
            
        if (profile.getProfileType().getId() == ProfileType.DEFAULT_PROFILE)
            redirectUrl = "/portal/admin/dashboard/browse";

        else if (profile.getProfileType().getId() == ProfileType.APP_MANAGER_PROFILE)
            redirectUrl = "/portal/manager/dashboard/browse";

        else if (profile.getProfileType().getId() == ProfileType.TUTOR_PROFILE)
            redirectUrl = "/portal/tutor/dashboard/browse";

        else if (profile.getProfileType().getId() == ProfileType.STUDENT_PROFILE)
            redirectUrl = "/portal/student/dashboard/browse";

        else
            redirectUrl = "/index";

        return sendRedirect(redirectUrl);
    }
}
