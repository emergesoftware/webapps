package za.co.emergelets.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAO;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.emergelets.xplain2me.dao.ProvinceDAO;
import za.co.emergelets.xplain2me.dao.ProvinceDAOImpl;
import za.co.emergelets.xplain2me.dao.SubjectDAO;
import za.co.emergelets.xplain2me.dao.SubjectDAOImpl;
import za.co.emergelets.xplain2me.entity.AcademicLevel;
import za.co.emergelets.xplain2me.entity.Province;
import za.co.emergelets.xplain2me.entity.Subject;
import za.co.emergelets.xplain2me.webapp.component.RequestQuoteForm;
import za.co.emergelets.xplain2me.webapp.controller.helper.RequestQuoteControllerHelper;

@Controller
public class RequestQuoteController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(
            RequestQuoteController.class.getName(), null);
    
    // data access objects
    private AcademicLevelDAO academicLevelDAO;
    private ProvinceDAO provinceDAO;
    private SubjectDAO subjectDAO;
    
    // helper class
    private RequestQuoteControllerHelper helper;
    
    public RequestQuoteController() {
        this.academicLevelDAO = new AcademicLevelDAOImpl();
        this.provinceDAO = new ProvinceDAOImpl();
        this.subjectDAO = new SubjectDAOImpl();
        
        this.helper = new RequestQuoteControllerHelper();
    }
    
    @RequestMapping(value = "/quote-request", method = RequestMethod.GET)
    public ModelAndView displayQuoteRequestPage(HttpServletRequest request) {
        
        // invalidate any existing session
        invalidateCurrentSession(request);
        
        // create the form
        RequestQuoteForm form = new RequestQuoteForm();
        
        // get the academic levels
        List<AcademicLevel> academicLevels = academicLevelDAO.getAllAcademicLevels();
        for (AcademicLevel level : academicLevels) {
            form.getAcademicLevels().put(level.getId(), level);
        }
        
        // get the provinces
        List<Province> provinces = provinceDAO.getAllProvinces();
        for (Province province : provinces) {
            form.getProvinces().put(province.getId(), province);
        }
        
        // get the subjects
        List<Subject> subjects = subjectDAO.getAllSubjects();
        for (Subject subject : subjects) {
            form.getSubjects().put(subject.getId(), subject);
        }
        
        // save the form to the session
        saveToSessionScope(request, form);
        
        return createModelAndView("quote-request");
        
    }
    
    @RequestMapping(value = "/quote-request", method = RequestMethod.POST)
    public ModelAndView handleQuoteRequestSubmission(HttpServletRequest request) {
        
        // check the session
        RequestQuoteForm form = (RequestQuoteForm)getFromSessionScope(request, 
                RequestQuoteForm.class);
        
        if (form == null) {
            LOG.warning("... no active session found...");
            invalidateCurrentSession(request);
            return sendRedirect("/quote-request");
        }
        
        helper.assignHttpParametersToRequestQuoteForm(request, form); 
        
        if ( 
            // validate the reCaptcha challenge
            helper.verifyReCaptchaChallenge(request, form) &&
            // validate the quote request information
            helper.validateRequestQuoteInformation(form)) {
            
            // invalidate the session
            invalidateCurrentSession(request);
            
            // send an email to app manager
            helper.sendQuoteRequestEmailAsync(form);
            
            // return the view
            return createModelAndView("quote-request-submitted");
        }
        
        else {
        
            // save the session
            saveToSessionScope(request, form);
            
            // return the view
            return createModelAndView("quote-request"); 
        }
        
    }
    
}
