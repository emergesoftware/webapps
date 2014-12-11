package za.co.emergelets.xplain2me.webapp.controller;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.web.servlet.ModelAndView;

public abstract class GenericController {

    // the HTTP session
    private HttpSession session;

    /**
     * Constructor
     */
    protected GenericController() {
        this.session = null;
    }
    
    /**
     * Validates the session - checks if
     * there is a valid user session that
     * exists
     * 
     * @param request
     * @return 
     */
    protected boolean validateSession(HttpServletRequest request) {

        session = request.getSession(true);
        Date creationTime = new Date(session.getCreationTime());

        if (Minutes.minutesBetween(new DateTime(creationTime), 
                new DateTime(new Date())).getMinutes() > 15) {
            invalidateCurrentSession(request);
            return false;
        }
        
        return true;
    }
    
    /**
     * Creates an instance of the ModelAndView object
     * 
     * @param modelObject
     * @param modelName
     * @param viewName
     * @return 
     */
    protected ModelAndView createModelAndView(String modelName, Object modelObject, 
            String viewName) {
        return new ModelAndView(viewName, modelName, modelObject);
    }
    
    /**
     * Creates a model and view object
     * 
     * @param viewName
     * @return 
     */
    protected ModelAndView createModelAndView(String viewName) {
        return new ModelAndView(viewName);
    }
    
     /**
     * serializes the object to the session,
     * and then returns the session ID
     * 
     * @param request
     * @param object 
     * @return The session ID 
     * 
     */
    protected String saveToSessionScope(HttpServletRequest request, Object object) {

        if (object == null)
            return "";

        session = request.getSession(true);
        session.setAttribute(object.getClass().getName(), object);
        session.setMaxInactiveInterval(900);
        return session.getId();
        
    }
    
    /**
     * Gets the object serialized to the
     * current session
     * 
     * @param request
     * @param objectClass
     * @return 
     */
    protected Object getFromSessionScope(HttpServletRequest request, Class objectClass) {

        if (objectClass == null)
            return null;

        session = request.getSession(true);
        Object object = session.getAttribute(objectClass.getName());
        return object;
    }
    
    /**
     * Removes a bound object from the session
     * 
     * @param request
     * @param objectClass 
     */
    protected void removeFromSessionScope(HttpServletRequest request, Class objectClass) {

        if (objectClass == null)
            return;

        session = request.getSession(true);
        
        if (session.getAttribute(objectClass.getName()) != null)
            session.removeAttribute(objectClass.getCanonicalName());
        
    }
    
    /**
     * Checks if the specified parameter does exist
     * 
     * @param request
     * @param parameterName
     * @return 
     */
    protected boolean checkParameter(HttpServletRequest request, String parameterName) {
        return (request.getParameter(parameterName) != null && 
                request.getParameter(parameterName).isEmpty() == false);
    }
    
    
    /**
     * Gets the value carried by the parameter specified
     * 
     * @param request
     * @param parameterName
     * @return 
     */
    protected String getParameterValue(HttpServletRequest request, String parameterName) {
        if (parameterName == null)
            return null;

        return request.getParameter(parameterName);
    }
    
    /**
     * Gets the value carried by the parameter specified
     * 
     * @param request
     * @param parameterName
     * @param convertNullToEmpty
     * @return 
     */
    protected String getParameterValue(HttpServletRequest request, String parameterName, 
            boolean convertNullToEmpty) {
        
        String value = getParameterValue(request, parameterName);
        if (value == null) value = "";
        
        return value;
    }
    
    /**
     * Gets the values in an array, carried by the specified
     * parameter
     * 
     * @param request
     * @param parameterName
     * @return 
     */
    protected String[] getParameterValues(HttpServletRequest request, String parameterName) {
        if (parameterName == null)
            return null;
        return request.getParameterValues(parameterName);
    }
    
    /**
     * Invalidates this current session and then 
     * removes all objects bound to it.
     * 
     * @param request 
     */
    protected void invalidateCurrentSession(HttpServletRequest request) {
        
        session = request.getSession(true);
        session.invalidate();
        
    }
    
    /**
     * Sends a redirect
     * @param path
     * @return 
     */
    protected ModelAndView sendRedirect(String path) {

        return createModelAndView("redirect:" + path);
    }
    
    /**
     * Serialises the object to the request scope
     * @param request
     * @param object 
     */
    protected void saveToRequestScope(HttpServletRequest request, Object object) {
        if (object == null)
            return;
        request.setAttribute(object.getClass().getName(), object);
    }
    
    /**
     * Gets the object instance from the 
     * request scope
     * 
     * @param request
     * @param c
     * @return 
     */
    protected Object getFromRequestScope(HttpServletRequest request, Class c) {
        if (c == null)
            return null;
        Object object = request.getAttribute(c.getName());
        return object;
    }


}
