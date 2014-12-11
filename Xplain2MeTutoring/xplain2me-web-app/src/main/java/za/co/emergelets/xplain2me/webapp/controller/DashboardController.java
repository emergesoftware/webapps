package za.co.emergelets.xplain2me.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.webapp.component.DashboardForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.helper.DashboardControllerHelper;

@Controller
public class DashboardController extends GenericController {
    
    // the helper class
    @Autowired
    private DashboardControllerHelper helper;
    
    public DashboardController(){
    }
    
    @RequestMapping(value = RequestMappings.DASHBOARD_OVERVIEW, 
            method = RequestMethod.GET)
    public ModelAndView displayDashboardPage(HttpServletRequest request) {
        
        // get the form - if any
        DashboardForm form = (DashboardForm)getFromSessionScope(request,
                DashboardForm.class);
        
        if (form == null) {
            form = new DashboardForm();
        }
        
        // get the user context
        UserContext context = (UserContext)getFromSessionScope(
                request, UserContext.class);
        
        // get the audit trail for this user - last 15
        // activities
        helper.populateRecentAuditTrailByUser(form, 
                context.getProfile().getPerson().getUser().getUsername(), 
                15);
        
        // save to the session scope
        saveToSessionScope(request, form);
        
        
        // return the view
        return createModelAndView(Views.USER_DASHBOARD);
        
        
    }
    
}
