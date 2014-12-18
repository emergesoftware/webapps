package za.co.emergelets.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.NumericUtils;
import za.co.emergelets.xplain2me.webapp.component.TutorJobApplicationForm;
import za.co.emergelets.xplain2me.webapp.controller.helper.TutorJobApplicationControllerHelper;

@Controller
public class TutorJobApplicationController extends GenericController implements Serializable {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(TutorJobApplicationController.class.getName(), null);
    
    @Autowired
    private TutorJobApplicationControllerHelper helper;
    
    public TutorJobApplicationController() {
    }
    
    @RequestMapping(value = RequestMappings.BROWSE_TUTOR_JOB_APPLICATIONS, 
                    method = RequestMethod.GET)
    public ModelAndView showTutorJobApplicationsBrowserPage(HttpServletRequest request) {
        
        // check for the form
        // from the session scope
        TutorJobApplicationForm form = (TutorJobApplicationForm)
                getFromSessionScope(request, TutorJobApplicationForm.class);
        if (form == null) {
            form = new TutorJobApplicationForm();
        }
        
        // check for the page number
        String pageNumber = getParameterValue(request, "page");
        if (pageNumber == null || pageNumber.isEmpty() || 
                NumericUtils.isNaN(pageNumber)) {
            form.setCurrentPageNumber(1);
        }
        
        else {
            form.setCurrentPageNumber(Integer.parseInt(pageNumber));
        }
        
        // load the become tutor requests
        helper.loadTutorJobApplications(form);
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        // return a view
        return createModelAndView(Views.BROWSE_TUTOR_JOBS_APPLICATIONS);
    }
}
