/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.web.servlet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author user
 */
public abstract class GenericController {
    
    protected GenericController() {
    }
    
    /**
     * Creates an instance of the ModelAndView object
     * 
     * @param modelObject
     * @param modelName
     * @param viewName
     * @return 
     */
    protected ModelAndView createModelAndView(Object modelObject, 
            String modelName, String viewName) {
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
    protected String saveToSession(HttpServletRequest request, Object object) {
        
        HttpSession session = request.getSession(true);
        session.setAttribute(object.getClass().getCanonicalName(), object);
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
    protected Object getFromSession(HttpServletRequest request, Class objectClass) {
        HttpSession session = request.getSession(true);
        Object object = session.getAttribute(objectClass.getCanonicalName());
        
        return object;
    }
    
    /**
     * Removes a bound object from the session
     * 
     * @param request
     * @param objectClass 
     */
    protected void removeFromSession(HttpServletRequest request, Class objectClass) {
        
        HttpSession session = request.getSession(true);
        
        if (session.getAttribute(objectClass.getCanonicalName()) != null)
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
        return (request.getParameter(parameterName) != null);
    }
    
    /**
     * Gets the value carried by the parameter specified
     * 
     * @param request
     * @param parameterName
     * @return 
     */
    protected String getParameterValue(HttpServletRequest request, String parameterName) {
        return request.getParameter(parameterName);
    }
    
    /**
     * Invalidates this current session and then 
     * removes all objects bound to it.
     * 
     * @param request 
     */
    protected void invalidateCurrentSession(HttpServletRequest request) {
        
        HttpSession session = request.getSession(true);
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
    
    
}
