package za.co.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.xplain2me.webapp.component.ErrorForm;

@Controller
public class ErrorController extends GenericController 
                                implements Serializable {
    
    private static final Logger LOG = 
           Logger.getLogger(ErrorController.class.getName(), null);
    
    private ErrorForm form;
    
    public ErrorController() {
    }
    
    
    /**
     * SHOWS THE USER UNAUTHORZED PAGE
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.UNAUTHORIZED_ACCESS, 
            method = RequestMethod.GET)
    public ModelAndView unauthorisedAccessPage(HttpServletRequest request) {
        
       
        // check the form in the
        // request scope
        form = (ErrorForm)getFromRequestScope(request, ErrorForm.class);
        if (form == null)
            form = new ErrorForm();
        else
            form.resetForm();
        
        // set the properties
        form.setErrorTitle("Unauthorised Access"); 
        form.setErrorDescription("Looks like you are not allowed to access this resource. " +
                        "You do not have the sufficient permissions to perform or " +
                        "access this resource.");
        form.setReturnUrl(null);
        
        // save the form to the request
        // scope
        saveToRequestScope(request, form);
        
        // return a view
        return createModelAndView(Views.GENERIC_ERROR_PAGE);
        
    }
    
}
