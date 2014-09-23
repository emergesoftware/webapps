package com.emergelets.metrobus.mobisite.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = AboutController.MAPPING)
public class AboutController extends GenericController {
    
    // the root mapping
    public static final String MAPPING = "/about/*";
    
    // the JSP views
    public static final String ABOUT_APP_PAGE = "about-app";
    
    public AboutController() {
    }
    
    @RequestMapping(value = "app", method = RequestMethod.GET)
    public ModelAndView displayAboutPage(HttpServletRequest request) {
        return createModelAndView(ABOUT_APP_PAGE);
    }
    
}
