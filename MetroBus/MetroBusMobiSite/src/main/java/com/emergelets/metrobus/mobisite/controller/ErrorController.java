/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emergelets.metrobus.mobisite.controller;

import com.emergelets.metrobus.mobisite.component.WebPageInfo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController extends GenericController {
    
    public ErrorController() {
    }
    
    @RequestMapping(value = "/error-occured", method = RequestMethod.GET)
    public ModelAndView displayErrorPage(HttpServletRequest request) {
        
        webPageInfo = new WebPageInfo();
        webPageInfo.setTitle("Error Occured");
        
        saveToRequestScope(request, webPageInfo);
        
        return createModelAndView("error/default-error");
    }
    
}
