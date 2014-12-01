package za.co.emergelets.xplain2me.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.webapp.component.ManagerDashboardForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.helper.ManagerDashboardControllerHelper;

@Controller
@RequestMapping(RequestMappings.MANAGER_DASHBOARD)
public class ManagerDashboardController extends GenericController {
    
    // the helper class
    private final ManagerDashboardControllerHelper helper;
    
    // the form and context
    private ManagerDashboardForm form;
    private UserContext context;
    
    public ManagerDashboardController(){
        helper = new ManagerDashboardControllerHelper();
    }
    
    @RequestMapping(value = RequestMappings.MANAGER_DASHBOARD_BROWSE, 
            method = RequestMethod.GET)
    public ModelAndView displayDashboardPage(HttpServletRequest request) {
        
        // get the form - if any
        form = (ManagerDashboardForm)getFromSessionScope(request,
                ManagerDashboardForm.class);
        
        if (form == null) {
            form = new ManagerDashboardForm();
        }
        
        // get the user context
        context = (UserContext)getFromSessionScope(
                request, UserContext.class);
        
        // get the audit trail for this user - last 15
        // activities
        helper.populateRecentAuditTrailByUser(form, 
                context.getProfile().getUser().getUsername(), 
                15);
        
        // save to the session scope
        saveToSessionScope(request, form);
        
        
        // return the view
        return createModelAndView(Views.APP_MANAGER_DASHBOARD);
        
        
    }
    
}
