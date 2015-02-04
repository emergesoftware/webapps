package za.co.xplain2me.webapp.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.xplain2me.dao.AcademicLevelDAO;
import za.co.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.xplain2me.dao.BecomeTutorRequestDAOImpl;
import za.co.xplain2me.dao.CitizenshipDAO;
import za.co.xplain2me.dao.CitizenshipDAOImpl;
import za.co.xplain2me.dao.GenderDAO;
import za.co.xplain2me.dao.GenderDAOImpl;
import za.co.xplain2me.dao.SubjectDAO;
import za.co.xplain2me.dao.SubjectDAOImpl;
import za.co.xplain2me.webapp.component.BecomeTutorForm;
import za.co.xplain2me.webapp.controller.helper.BecomeTutorControllerHelper;

@Controller
public class BecomeTutorController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(
            BecomeTutorController.class.getName(), null);
    
    // the controller helper
    @Autowired
    private BecomeTutorControllerHelper helper;
    
    public BecomeTutorController() {
    }
    
    /**
     * Delivers the become a tutor webpage
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.BECOME_A_TUTOR, method = RequestMethod.GET)
    public ModelAndView displayBecomeTutorPage(HttpServletRequest request) {
        
        // invalidate the session
        invalidateCurrentSession(request);
        
        // the data access objects
        CitizenshipDAO citizenshipDAO = new CitizenshipDAOImpl();
        GenderDAO genderDAO = new GenderDAOImpl();
        SubjectDAO subjectDAO = new SubjectDAOImpl();
        
        // create and initialise
        BecomeTutorForm form = new BecomeTutorForm();
        helper.populateCitizenshipEntries(form, citizenshipDAO);
        helper.populateGenderEntries(form, genderDAO);
        helper.populateSubjects(form, subjectDAO);
        
        // save form to the session
        saveToSessionScope(request, form);
        
        return createModelAndView(Views.BECOME_A_TUTOR);
        
    }
    
    /**
     * Handles the request to process a 
     * tutor job application.
     * 
     * @param request
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = RequestMappings.BECOME_A_TUTOR, method = RequestMethod.POST)
    public ModelAndView handleBecomeTutorRequest(HttpServletRequest request) 
            
            throws Exception {
        
        // check if the form is in the session
        BecomeTutorForm form = (BecomeTutorForm)getFromSessionScope(
                request, BecomeTutorForm.class);
        
        if (form == null) {
            
            LOG.warning("... no active session found ...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.BECOME_A_TUTOR +
                    "?unauthorized-access=yes");
        }
        
        // check the maximum upload size
        if (helper.validateHttpPostContentLength(request, form)) {
        
            // retrieve the form data
            form.setBecomeTutorRequest(helper.createBecomeTutorRequestInstance(request, form));
            // save the form to the session
            saveToSessionScope(request, form);
            // send redirect to process this request
            return sendRedirect(RequestMappings.PROCESS_TUTOR_JOB_APPLICATION);
        }
        
        else {
            // save the form to the session
            saveToSessionScope(request, form);
            // return to the webpage
            return createModelAndView(Views.BECOME_A_TUTOR);
        }
        
    }
    
    /**
     * Processes the tutor job application
     * 
     * @param request
     * @return
     * @throws ParseException 
     */
    @RequestMapping(value = RequestMappings.PROCESS_TUTOR_JOB_APPLICATION, 
            method = RequestMethod.GET)
    public ModelAndView processTutorJobApplicationRequest(HttpServletRequest request) 
            throws ParseException {
        
        // get the form from the session
        BecomeTutorForm form = (BecomeTutorForm)getFromSessionScope(request,
                BecomeTutorForm.class);
        if (form == null || 
                form.getBecomeTutorRequest() == null) {
            
            LOG.warning("... invalid request ...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.BECOME_A_TUTOR);
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
            return createModelAndView(Views.BECOME_A_TUTOR_VERIFICATION);
          
        }
        
        else {
            // save form to the session
            saveToSessionScope(request, form);
            
            // return a view
            return createModelAndView(Views.BECOME_A_TUTOR);
        }
    }
    
    /**
     * Processes the verification of the tutor
     * job application.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.VERIFY_BECOME_A_TUTOR_REQUEST, 
            method = RequestMethod.POST)
    public ModelAndView verifyAndCompleteTutorJobApplicationRequest(HttpServletRequest request) {
        
        // get the form from the session
        BecomeTutorForm form = (BecomeTutorForm)getFromSessionScope(request,
                BecomeTutorForm.class);
        if (form == null || 
                form.getBecomeTutorRequest() == null) {
            
            LOG.warning("... invalid request ...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.BECOME_A_TUTOR);
        }
        
        // clear all errors
        form.getErrorsEncountered().clear();
        
        // data access object
        BecomeTutorRequestDAO becomeTutorRequestDAO = 
                new BecomeTutorRequestDAOImpl();
        
        if (// verify the verification code
            helper.checkVerificationCode(request, form) &&
            // check if the applicant is completely unique
            helper.isTutorApplicationCompletelyUnique(becomeTutorRequestDAO, form)) {
            
            // invalidate the current sesison
            invalidateCurrentSession(request);

            // save the information to the database
            becomeTutorRequestDAO.saveBecomeTutorRequest(
                    form.getBecomeTutorRequest(), 
                    form.getSubjectsTutoredBefore(), 
                    form.getSupportingDocuments());

            // start a thread to send the receipt of tutor application
            // to the user
            helper.sendUserReceiptOfApplicationEmailAsync(form);

            // start a thread to send an email to the 
            // manager about this new request for a tutor
            helper.sendTutorApplicationNotificationEmailAsync(form);

            // return the page for the results
            return createModelAndView(Views.BECOME_A_TUTOR_SUBMITTED);

        }
        
        else {
        
            // save the form to the session
            saveToSessionScope(request, form);
            
            // return the page for the application
            return createModelAndView(Views.BECOME_A_TUTOR_VERIFICATION);
        
        }
        
    }
}
