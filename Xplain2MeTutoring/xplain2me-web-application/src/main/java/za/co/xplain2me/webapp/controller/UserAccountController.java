package za.co.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.xplain2me.util.NumericUtils;
import za.co.xplain2me.util.ReCaptchaUtil;
import za.co.xplain2me.util.SHA256Encryptor;
import za.co.xplain2me.bo.validation.EmailAddressValidator;
import za.co.xplain2me.bo.validation.UserValidationRule;
import za.co.xplain2me.bo.validation.impl.UserValidationRuleImpl;
import za.co.xplain2me.dao.EventTypes;
import za.co.xplain2me.dao.ProfileDAO;
import za.co.xplain2me.dao.ProfileDAOImpl;
import za.co.xplain2me.dao.SystemAuditManager;
import za.co.xplain2me.dao.UserDAO;
import za.co.xplain2me.dao.UserDAOImpl;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.webapp.component.AlertBlock;
import za.co.xplain2me.webapp.component.UserContext;

@Controller
public class UserAccountController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger
            .getLogger(UserAccountController.class.getName(), null);
    
    public UserAccountController() {
    }
    
    /**
     * Delivers a webpage to verify own (new) user profile - 
     * users must not be logged in.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.VERIFY_OWN_USER_PROFILE, 
            method = RequestMethod.GET)
    public ModelAndView showVerifyOwnNewUserProfilePage(HttpServletRequest request) {
        
        LOG.info("... deliver webapge to verify new own user profile ...");
        
        // check if the url is being accessed by a logged in user
        // - if so, restrict access
        if ((UserContext)getFromSessionScope(request,
                UserContext.class) != null) { 
            LOG.warning("... the user context is still active ...");
            return sendRedirect(RequestMappings.UNAUTHORIZED_ACCESS);
        }
        
        // check if there is alert block in 
        // the session - then transfer to the
        // request session
        AlertBlock alertBlock = (AlertBlock)getFromSessionScope(request,
                AlertBlock.class);
        
        if (alertBlock != null && 
                alertBlock.getAlertBlockMessages() != null &&
                !alertBlock.getAlertBlockMessages().isEmpty()) {
            
            removeFromSessionScope(request, AlertBlock.class);
            saveToRequestScope(request, alertBlock);
        }
        
        
        return createModelAndView(Views.VERIFY_NEW_OWN_USER_PROFILE);
    }
    
    /**
     * Processes the request to verify and
     * activate the user profile account.
     * 
     * @param request
     * @param identityNumber
     * @param emailAddress
     * @param verificationCode
     * @param newPassword
     * @param reEnteredNewPassword
     * @param reCaptchaChallenge
     * @param reCaptchaResponse
     * @return 
     * @throws java.lang.Exception 
     */
    @RequestMapping(value = RequestMappings.VERIFY_OWN_USER_PROFILE, 
            method = RequestMethod.POST, 
            params = {"idNumber", "emailAddress", "verificationCode", 
                    "newPassword", "reEnterNewPassword",
                    "recaptcha_challenge_field", "recaptcha_response_field"})
    public ModelAndView processVerifyNewOwnUserProfileAccountRequest(
            HttpServletRequest request,
            @RequestParam(value = "idNumber")String identityNumber,
            @RequestParam(value = "emailAddress")String emailAddress,
            @RequestParam(value = "verificationCode")String verificationCode,
            @RequestParam(value = "newPassword")String newPassword,
            @RequestParam(value = "reEnterNewPassword")String reEnteredNewPassword,
            @RequestParam(value = "recaptcha_challenge_field")String reCaptchaChallenge,
            @RequestParam(value = "recaptcha_response_field")String reCaptchaResponse) throws Exception {
        
        // variables
        AlertBlock alertBlock = null;
        Map<String, String> parameters = null;
        List<String> errorsEncountered = new ArrayList<>();
        
        // validate the reCAPTCHA challenge
        if (ReCaptchaUtil.verifyReCaptchaCode(request, reCaptchaChallenge,
                reCaptchaResponse, null) == false) {
            errorsEncountered.add("The reCAPTCHA Challenge response did not match.");
        }
        
        // validate the new password
        UserValidationRule userValidationRule = new UserValidationRuleImpl();
        errorsEncountered.addAll(userValidationRule.validatePassword(newPassword));
        
        // check if both the passwords match
        if ((newPassword != null && reEnteredNewPassword != null) && 
                !newPassword.equals(reEnteredNewPassword)) {
            errorsEncountered.add("The new password does not "
                    + "match the re-entered new password.");
        }
        
        // validate the ID number
        if (identityNumber == null || identityNumber.isEmpty()) 
            errorsEncountered.add("The Identity or Passport number is required.");
        
        // validate the email address
        if (emailAddress == null || 
                EmailAddressValidator.isEmailAddressValid(emailAddress) == false)
            errorsEncountered.add("The Email Address does not appear authentic.");
        
        // validate the verification code
        if (verificationCode == null || verificationCode.isEmpty() || 
                NumericUtils.isNaN(verificationCode))
            errorsEncountered.add("The Verification Code does not appear authentic.");
        
        
        // check if there is any errors
        if (errorsEncountered.isEmpty() == false) {
            
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            
            for (String error : errorsEncountered)
                alertBlock.addAlertBlockMessage(error);
            
            // save the alert block to the session
            // scope
            saveToSessionScope(request, alertBlock);
            
            // send redirect
            parameters = new HashMap<>();
            parameters.put("result", "PRE-VERIFICATION-FAILED");
            
            return sendRedirect(RequestMappings.VERIFY_OWN_USER_PROFILE, parameters);
        }
        
        // verify the user against the data in the
        // data store
        ProfileDAO profileDao = new ProfileDAOImpl();
        Profile verifiedProfile = profileDao.verifyOwnUserProfile(identityNumber, 
                emailAddress, verificationCode);
        
        // if not verified - send error
        if (verifiedProfile == null) {
            
            // create new alert into session scope
            saveToSessionScope(request, new AlertBlock(
                    AlertBlock.ALERT_BLOCK_ERROR,
                    "Profile account verification failed - it appears as though your "
                    + "details are incorrect or you are performing an "
                    + "illegitimate process."));
            
            // send redirect
            parameters = new HashMap<>();
            parameters.put("result", "PROFILE-ACTIVATION-FAILED");
            
            return sendRedirect(RequestMappings.VERIFY_OWN_USER_PROFILE, parameters);
        }
        
        else {
            
            // log an audit
            SystemAuditManager.logAuditAsync(EventTypes.VERIFY_OWN_PROFILE, 
                    verifiedProfile.getPerson().getUser(), 
                    verifiedProfile.getId(), null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);
            
            // generate new salt value
            String saltValue = System.currentTimeMillis() + "";
            
            // generate new password
            String hashedPassword = SHA256Encryptor.computeSHA256(newPassword, saltValue);
            
            // update the user's new password
            UserDAO userDao = new UserDAOImpl();
            boolean passwordUpdated = userDao.updateUserPassword(
                    verifiedProfile.getPerson().getUser().getUsername(), 
                    hashedPassword, saltValue);
            
            // if password was updated successfully
            if (passwordUpdated) {
            
                // log an event
                SystemAuditManager.logAuditAsync(EventTypes.UPDATE_OWN_USER_PASSWORD, 
                    verifiedProfile.getPerson().getUser(), 
                    verifiedProfile.getPerson().getUser().getId(), null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, true);
                
                // save the alert block to the
                // session scope
                saveToSessionScope(request, new AlertBlock(
                        AlertBlock.ALERT_BLOCK_INFORMATIVE,
                        "Profile verification was successful. You may proceed to "
                                + "login."));

                // send redirect
                parameters = new HashMap<>();
                parameters.put("result", "PROFILE-ACTIVATED");

            }
            
            else {
                
                // save the alert block to the
                // session scope
                saveToSessionScope(request, new AlertBlock(
                        AlertBlock.ALERT_BLOCK_WARNING,
                        "Profile verification was successful, however your password "
                        + "could not be updated, please contact your "
                        + "administrator to fix this problem."));

                // send redirect
                parameters = new HashMap<>();
                parameters.put("result", "PASSWORD-UPDATE-FAILED");

                
            }
            
            return sendRedirect(RequestMappings.VERIFY_OWN_USER_PROFILE, parameters);
            
        }
        
    }

}

