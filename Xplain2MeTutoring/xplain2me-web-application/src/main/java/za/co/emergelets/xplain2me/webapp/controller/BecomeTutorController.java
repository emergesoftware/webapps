package za.co.emergelets.xplain2me.webapp.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAO;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAOImpl;
import za.co.emergelets.xplain2me.dao.CitizenshipDAO;
import za.co.emergelets.xplain2me.dao.CitizenshipDAOImpl;
import za.co.emergelets.xplain2me.dao.GenderDAO;
import za.co.emergelets.xplain2me.dao.GenderDAOImpl;
import za.co.emergelets.xplain2me.webapp.component.BecomeTutorForm;
import za.co.emergelets.xplain2me.webapp.controller.helper.BecomeTutorControllerHelper;

@Controller
public class BecomeTutorController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(
            BecomeTutorController.class.getName(), null);
    
    // the controller helper 
    private final BecomeTutorControllerHelper helper;
    
    // data access objects
    private final CitizenshipDAO citizenshipDAO;
    private final GenderDAO genderDAO;
    private final AcademicLevelDAO academicLevelDAO;
    private final BecomeTutorRequestDAO becomeTutorRequestDAO;
    
    public BecomeTutorController() {
        
        helper = new BecomeTutorControllerHelper();
        
        citizenshipDAO = new CitizenshipDAOImpl();
        genderDAO = new GenderDAOImpl();
        becomeTutorRequestDAO = new BecomeTutorRequestDAOImpl();
        academicLevelDAO = new AcademicLevelDAOImpl();
        
    }
    
    @RequestMapping(value = "/become-a-tutor", method = RequestMethod.GET)
    public ModelAndView displayBecomeTutorPage(HttpServletRequest request) {
        
        // invalidate the session
        invalidateCurrentSession(request);
        
        // create and initialise
        BecomeTutorForm form = new BecomeTutorForm();
        helper.populateCitizenshipEntries(form, citizenshipDAO);
        helper.populateGenderEntries(form, genderDAO);
        helper.populateAcademicLevels(form, academicLevelDAO);
        
        // save form to the session
        saveToSessionScope(request, form);
        
        return createModelAndView("become-a-tutor");
        
    }
    
    @RequestMapping(value = "/become-a-tutor", method = RequestMethod.POST)
    public ModelAndView handleBecomeTutorRequest(HttpServletRequest request) 
            
            throws Exception {
        
        // check if the form is in the session
        BecomeTutorForm form = (BecomeTutorForm)getFromSessionScope(
                request, BecomeTutorForm.class);
        
        if (form == null) {
            LOG.warning("... no active session found ...");
            invalidateCurrentSession(request);
            return sendRedirect("/become-a-tutor?unauthorized-access=yes");
        }
        
        // check the maximum upload size
        if (helper.validateHttpPostContentLength(request, form)) {
        
            // retrieve the form data
            form.setBecomeTutorRequest(helper.createBecomeTutorRequestInstance(request, form));
            // save the form to the session
            saveToSessionScope(request, form);
            // send redirect to process this request
            return sendRedirect("/process-tutor-job-application");
        }
        
        else {
            // save the form to the session
            saveToSessionScope(request, form);
            // return to the webpage
            return createModelAndView("become-a-tutor");
        }
        
    }
    
    @RequestMapping(value = "/process-tutor-job-application", method = RequestMethod.GET)
    public ModelAndView processTutorJobApplicationRequest(HttpServletRequest request) 
            throws ParseException {
        
        // get the form from the session
        BecomeTutorForm form = (BecomeTutorForm)getFromSessionScope(request,
                BecomeTutorForm.class);
        if (form == null || 
                form.getBecomeTutorRequest() == null) {
            
            LOG.warning("... invalid request ...");
            invalidateCurrentSession(request);
            return sendRedirect("/become-a-tutor");
        }
        
        // clear all errors - if any
        form.getErrorsEncountered().clear();
        
        if (// validate the reCATCHA challenge
                helper.verifyReCaptchaCode(request, form) &&
                // validate the personal information
                helper.validatePersonalInformation(form) && 
                // validate the contact information 
                helper.validateContactInformation(form) &&
                // validate the address information
                helper.validateAddressInformation(form) &&
                // validate the priort tutoring experience
                helper.validateTutorPriorTutoringExperienceInformation(form)) {
            
            // generate a verification code for the user
            helper.generateVerificationCode(form); 
            
            // send the verification 
            helper.sendVerificationCodeToUserAsync(form);
            
            // save the session
            saveToSessionScope(request, form);
            
            // return a view
            return createModelAndView("become-a-tutor-verification");
          
        }
        
        else {
            // save form to the session
            saveToSessionScope(request, form);
            
            // return a view
            return createModelAndView("become-a-tutor");
        }
    }
    
    
    @RequestMapping(value = "/verify-become-a-tutor-request", method = RequestMethod.POST)
    public ModelAndView verifyAndCompleteTutorJobApplicationRequest(HttpServletRequest request) {
        
        // get the form from the session
        BecomeTutorForm form = (BecomeTutorForm)getFromSessionScope(request,
                BecomeTutorForm.class);
        if (form == null || 
                form.getBecomeTutorRequest() == null) {
            
            LOG.warning("... invalid request ...");
            invalidateCurrentSession(request);
            return sendRedirect("/become-a-tutor");
        }
        
        // clear all errors
        form.getErrorsEncountered().clear();
        
        
        if (// verify the verification code
            helper.checkVerificationCode(request, form) &&
            // check if the applicant is completely unique
            helper.isTutorApplicationCompletelyUnique(becomeTutorRequestDAO, form)) {
            
            // invalidate the current sesison
            invalidateCurrentSession(request);

            // save the information to the database
            becomeTutorRequestDAO.saveBecomeTutorRequest(
                    form.getBecomeTutorRequest(), 
                    form.getAcademicLevelsTutoredBefore(), 
                    form.getSupportingDocuments());

            // start a thread to send the receipt of tutor application
            // to the user
            helper.sendUserReceiptOfApplicationEmailAsync(form);

            // start a thread to send an email to the 
            // manager about this new request for a tutor
            helper.sendTutorApplicationNotificationEmailAsync(form);

            // return the page for the results
            return createModelAndView("become-a-tutor-submitted");

        }
        
        else {
        
            // save the form to the session
            saveToSessionScope(request, form);
            
            // return the page for the application
            return createModelAndView("become-a-tutor-verification");
        
        }
        
    }
}
