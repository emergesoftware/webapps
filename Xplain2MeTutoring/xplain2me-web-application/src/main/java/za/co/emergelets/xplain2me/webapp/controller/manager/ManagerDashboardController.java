package za.co.emergelets.xplain2me.webapp.controller.manager;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

@Controller
@RequestMapping("/portal/manager/dashboard/*")
public class ManagerDashboardController extends GenericController {
    
    public ManagerDashboardController(){
    }
    
    @RequestMapping(value = "browse", method = RequestMethod.GET)
    public ModelAndView displayDashboardPage(HttpServletRequest request) {
        
        UserContext context = (UserContext)getFromSessionScope(
                request, UserContext.class);
        if (context == null) {
            invalidateCurrentSession(request);
            return sendRedirect("/login");
        }
        
        return createModelAndView("app-manager/dashboard");
        
        
    }
    
}
