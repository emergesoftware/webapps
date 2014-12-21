package za.co.emergelets.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.NumericUtils;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAOImpl;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
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
    
    /**
     * Shows the browser page for all the
     * tutor job applications made.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.BROWSE_TUTOR_JOB_APPLICATIONS, 
                    method = RequestMethod.GET)
    public ModelAndView showTutorJobApplicationsBrowserPage(HttpServletRequest request) {
        
        LOG.info("... show tutor job applications browser page ...");
        
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
    
    /**
     * Shows the page with all the details of the
     * tutor employment application.
     * 
     * @param request
     * @param id
     * @return 
     */
    @RequestMapping(value = RequestMappings.VIEW_TUTOR_JOB_APPLICATION_DETAILS, 
            method = RequestMethod.GET, 
            params = "id")
    public ModelAndView viewAllTutorJobApplicationDetails(HttpServletRequest request, 
            @RequestParam(value = "id")String id) {
        
        LOG.info("... show tutor job application details ...");
        
        // variables
        AlertBlock alertBlock = null;
        BecomeTutorRequest becomeTutorRequest = null;
        
        // check if the parameter is valid
        if (id == null || id.isEmpty() || 
            NumericUtils.isNaN(id) || Long.parseLong(id) < 1) {
            
            LOG.warning(" ... the id param is not valid ...");
            
            // create an alert block and save to the
            // request scope
            alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR,
                    "The tutor job application ID does not appear to be valid.");
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView(Views.VIEW_TUTOR_JOB_APPLICATION_DETAILS);
        }
        
        // pull the tutor job application from the 
        // data store
        BecomeTutorRequestDAO dao = new BecomeTutorRequestDAOImpl();
        becomeTutorRequest = dao.getBecomeTutorRequest(Long.parseLong(id));
        
        // if no entity was found
        if (becomeTutorRequest == null) {
            
            LOG.warning(" ... tutor job application with the ID could not be found ...");
            
            // create an alert block and save to the
            // request scope
            alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR,
                    "The tutor job application with the specified "
                            + "sequence ID could not be found.");
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView(Views.VIEW_TUTOR_JOB_APPLICATION_DETAILS);
        }
        
        else {
            
            // save the entity to the request
            // scope
            saveToRequestScope(request, becomeTutorRequest);
            
            // return a view
            return createModelAndView(Views.VIEW_TUTOR_JOB_APPLICATION_DETAILS);
        }
        
    }
}
