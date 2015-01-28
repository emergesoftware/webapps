package za.co.xplain2me.webapp.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import za.co.xplain2me.entity.ProfileType;
import za.co.xplain2me.webapp.component.UserContext;
import za.co.xplain2me.webapp.controller.RequestMappings;

@Component
public class DefaultAuthenticationInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger LOG = 
            Logger.getLogger(DefaultAuthenticationInterceptor.class.getName(), null);
    
    private static final String URL_PREFIX = "/portal/";
  
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
            response.sendRedirect(request.getContextPath() 
                    + RequestMappings.LOGOUT  
                    + "?rand=" + System.currentTimeMillis());
            
            return false;
        }
        
        // check if the user is allowed to access this url
        String requestUrl = request.getRequestURL().toString();
        ProfileType profileType = userContext.getProfile().getProfileType();
        
        LOG.info("... request url: " + requestUrl + "\n" + 
                "... profile type: " + profileType.getId() 
                + " [" + profileType.getDescription() + "]");
        
        if (userAllowedToAccessUrl(requestUrl, userContext) == false) {
            
           LOG.warning("... user not allowed to access this url ...");
           
           response.sendRedirect(request.getContextPath() 
                   + RequestMappings.UNAUTHORIZED_ACCESS 
                   + "?rand=" + System.currentTimeMillis());
           
           return false;
        }
        
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
            Object handler, ModelAndView modelAndView) throws Exception {
        
        super.postHandle(request, response, handler, modelAndView);

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
    private boolean userAllowedToAccessUrl(String requestUrl, UserContext context) {
        
        if (requestUrl == null || context == null)
            return false; 
        
        return context.getPermittedUrls().contains(
                requestUrl.substring(requestUrl.indexOf(URL_PREFIX)));
    }
    
}
