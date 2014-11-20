package za.co.emergelets.xplain2me.webapp.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import za.co.emergelets.xplain2me.entity.ProfileType;
import za.co.emergelets.xplain2me.webapp.component.UserContext;

@Component
public class DefaultAuthenticationInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger LOG = 
            Logger.getLogger(DefaultAuthenticationInterceptor.class.getName(), null);
    
    private static final String URL_PREFIX = "/portal/{0}/";
    private static final Map<Long, String> 
            URL_PROFILE_TYPE_ACCESS = new HashMap<>();
    static {
        
        URL_PROFILE_TYPE_ACCESS.put(new Long(ProfileType.DEFAULT_PROFILE), 
                URL_PREFIX.replace("{0}", "admin"));
        URL_PROFILE_TYPE_ACCESS.put(new Long(ProfileType.APP_MANAGER_PROFILE), 
                URL_PREFIX.replace("{0}", "manager"));
        URL_PROFILE_TYPE_ACCESS.put(new Long(ProfileType.TUTOR_PROFILE), 
                URL_PREFIX.replace("{0}", "tutor"));
        URL_PROFILE_TYPE_ACCESS.put(new Long(ProfileType.STUDENT_PROFILE), 
                URL_PREFIX.replace("{0}", "student"));
        
    }
   
    private UserContext userContext;
    private HttpSession session;
    
    public DefaultAuthenticationInterceptor() {
        super();
        
        this.userContext = null;
    }
    
    @Override
    public boolean preHandle(HttpServletRequest request,
	   HttpServletResponse response, Object handler) throws Exception {
        
        LOG.info("... evaluate the default authenticaiton... ");
        
        session = request.getSession(true);
        
        // get the user context from the session - 
        // to determine if the user is logged in
        userContext = (UserContext)session.getAttribute(
                UserContext.class.getName());
        if (userContext == null) {
            
            LOG.warning("... no user session was found... "); 
            
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        
        // check if the user is allowed to access this url
        String requestUrl = request.getRequestURL().toString();
        ProfileType profileType = userContext.getProfile().getProfileType();
        
        LOG.info("... request url: " + requestUrl + "\n" + 
                "... profile type: " + profileType.getId() 
                + " [" + profileType.getDescription() + "]");
        
        if (userAllowedToAccessUrl(requestUrl, profileType.getId()) == false) {
           LOG.warning("... user not allowed to access this url ...");
           response.sendRedirect(request.getContextPath() + "/login "  
                   + "?invalid_access=1");
           return false;
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
            Object handler, ModelAndView modelAndView) throws Exception {
        
        super.postHandle(request, response, handler, modelAndView);
        
        /*
            modify the HTTP headers - for
            security purposes to:
        
            Cache-Control: no-cache, no-store, must-revalidate
            Pragma: no-cache
            Expires: 0
        */
        
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache"); 
        response.setHeader("Expires", "0");
        
    }
    
    
    
    /**
     * Determines if the user profile is allowed to
     * access this url
     * 
     * @param requestUrl
     * @param profileType
     * @return 
     */
    private boolean userAllowedToAccessUrl(String requestUrl, long profileType) {
        
        if (requestUrl == null || profileType < 100)
            return false; 
        String prefix = URL_PROFILE_TYPE_ACCESS.get(profileType); 
        return requestUrl.contains(prefix);
        
    }
    
}
