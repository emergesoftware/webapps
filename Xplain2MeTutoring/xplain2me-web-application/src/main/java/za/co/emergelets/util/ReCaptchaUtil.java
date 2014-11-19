package za.co.emergelets.util;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import za.co.emergelets.xplain2me.webapp.component.BecomeTutorForm;

public final class ReCaptchaUtil {
    
    public static final String RECAPTCHA_API_KEY = 
            "6Lcc9v0SAAAAAJbb8OSE5AWCbzzCkhxcvdbc6kZq";
    public static final String RECAPTCHA_JS_KEY = 
            "6Lcc9v0SAAAAANB3hwUIZUAZDUC2rrLYGG8kChuj";
    
    /**
     * Verifies the reCAPTCHA Code
     * 
     * @param request
     * @param challenge
     * @param userResponse
     * @param errorsEncountered
     * @return 
     */
    public static boolean verifyReCaptchaCode(HttpServletRequest request, 
            String challenge, String userResponse,
            List<String> errorsEncountered) {
        
        String remoteAddr = request.getRemoteAddr();
        
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey(RECAPTCHA_API_KEY);
        
        if ((challenge == null || challenge.isEmpty()) || 
                (userResponse == null || userResponse.isEmpty())) {
            
            if (errorsEncountered != null)
                errorsEncountered.add("The CATCHA code is not correct.");
            
            return false;
        }
        
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, 
                challenge, userResponse);

        boolean isValid = reCaptchaResponse.isValid();
        
        if (isValid == false) {
            
            if (errorsEncountered != null)
                errorsEncountered.add("The CAPTCHA Code is not correct");
        }
        
        return isValid;
    }
    
}
