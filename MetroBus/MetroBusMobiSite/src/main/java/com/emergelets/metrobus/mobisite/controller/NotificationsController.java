package com.emergelets.metrobus.mobisite.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = NotificationsController.MAPPING)
public class NotificationsController extends GenericController {
    
    // the root request mapping
    public static final String MAPPING = "/notifications/*";
    
    // the notifcations page
    public static final String NOTIFICATIONS_MAIN_PAGE = "notifications-main";
    
    /**
     * Constructor
     */
    public NotificationsController(){
    }
    
    /**
     * Returns the main notifications page
     * @param request
     * @return 
     */
    @RequestMapping(value = "main", method = RequestMethod.GET)
    public ModelAndView displayNotificationsPage(HttpServletRequest request) {
        
        return createModelAndView(NOTIFICATIONS_MAIN_PAGE);
        
    }
    
}
