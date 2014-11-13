package za.co.emergelets.xplain2me.webapp.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.mail.EmailSender;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAO;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.emergelets.xplain2me.dao.SubjectDAO;
import za.co.emergelets.xplain2me.dao.SubjectDAOImpl;
import za.co.emergelets.xplain2me.dao.TutorRequestDAO;
import za.co.emergelets.xplain2me.dao.TutorRequestDAOImpl;
import za.co.emergelets.xplain2me.entity.AcademicLevel;
import za.co.emergelets.xplain2me.entity.Subject;
import za.co.emergelets.xplain2me.entity.TutorRequestSubject;
import za.co.emergelets.xplain2me.webapp.component.RequestTutorForm;
import za.co.emergelets.xplain2me.webapp.controller.helper.RequestTutorControllerHelper;

@Controller
@SessionAttributes
public class RequestTutorController extends GenericController {
    
    /**
     * Logger
     */
    private static Logger LOG = 
            Logger.getLogger(RequestTutorController.class.getName(), null);
    
    // data access objects
    private SubjectDAO subjectDAO;
    private AcademicLevelDAO academicLevelDAO;
    private TutorRequestDAO tutorRequestDAO;
    
    // controller helper
    private RequestTutorControllerHelper helper;
    
    public RequestTutorController() {
        // helpers
        this.helper = new RequestTutorControllerHelper();
        
        // DAOs
        this.subjectDAO = new SubjectDAOImpl();
        this.academicLevelDAO = new AcademicLevelDAOImpl();
        this.tutorRequestDAO = new TutorRequestDAOImpl();
    }
    
    /**
     * Returns the main form to request a tutor
     * @param request
     * @return 
     */
    @RequestMapping(value = "/request-a-tutor", method = RequestMethod.GET)
    public ModelAndView showRequestTutorFormPage(HttpServletRequest request) {
        
        // destroy sessions
        removeFromSessionScope(request, RequestTutorForm.class);
        
        // instantiate the form
        RequestTutorForm form = new RequestTutorForm();
        
        // get the subjects
        List<Subject> subjects = subjectDAO.getAllSubjects();
        SortedMap<Long, Subject> subjectsMap = new TreeMap<>();
        
        for (Subject subject : subjects) {
            subjectsMap.put(subject.getId(), subject);
        }
         
        form.setSubjects(subjectsMap);
        subjects = null;
        
        // get the academic levels
        List<AcademicLevel> academicLevels = academicLevelDAO.getAllAcademicLevels();
        SortedMap<Long, AcademicLevel> academicLevelsMap = new TreeMap<>();
        
        for (AcademicLevel level : academicLevels) {
            academicLevelsMap.put(level.getId(), level);
        }
        
        form.setAcademicLevels(academicLevelsMap);
        academicLevels = null;
        
        // save the form in the session
        saveToSessionScope(request, form);
        
        // return model and view
        return createModelAndView("request-a-tutor");
        
    }
    
    /**
     * Handles the form submission for a request for
     * a tutor and redirects to another handler
     * 
     * @param request
     * @param form
     * @return 
     */
    @RequestMapping(value = "/request-a-tutor", method = RequestMethod.POST)
    public ModelAndView handleRequestTutorFormSubmission(HttpServletRequest request) {
        
        // seek the form from the session
        RequestTutorForm form = (RequestTutorForm)getFromSessionScope(request, 
                RequestTutorForm.class);
        
        // check that the form is populated
        if (form == null) {
            return sendRedirect("/request-a-tutor");
        }
        
        // capture the reCAPTCHA data
        form.setReCaptchaChallenge(request.getParameter("recaptcha_challenge_field"));
        form.setReCaptchaResponse(request.getParameter("recaptcha_response_field"));
        
        // retrieve the form data
        form.setTutorRequest(helper.createTutorRequestInstance(request, form));
        
        // save the form to the session
        saveToSessionScope(request, form);
        
        // redirect to another handler
        return sendRedirect("/process-request-for-tutor");
    }
    
    /**
     * Processes the form submission for request for a 
     * tutor
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/process-request-for-tutor", method = RequestMethod.GET)
    public ModelAndView processRequestTutorFormSubmission(HttpServletRequest request) {
        
        // attempt to retrieve the form from the 
        // request scope
        RequestTutorForm form = (RequestTutorForm)getFromSessionScope(
                request, RequestTutorForm.class);
        if (form == null) {
            return sendRedirect("/request-a-tutor");
        }
         
        // clear all existing errors enocuntered
        form.setErrorsEncountered(new ArrayList<String>());
        
        // perform necessary validations on the
        // sections of the information submitted
        if (
                // personal information validation
                helper.validatePersonalnformation(form) &&
                // contact information validation
                helper.validateContactInformation(form) &&
                // address validation
                helper.validateAddressInformation(form) &&
                // reCAPTCHA code validation
                helper.verifyReCaptchaCode(request, form)) {
            
            // generate a verification code
            helper.generateRandomVerificationCode(form);
            
            // save the form to the current sesison
            saveToSessionScope(request, form);
            
            // start a thread to send the verification code
            // to the user
            helper.sendUserVerificationCodeUsingEmailAsync(form);
            
            // return the page for the verification code
            return createModelAndView("request-a-tutor-verification");
            
        }
        
        else {
        
            // return a view
            return createModelAndView("request-a-tutor-submitted");
        }
        
    }
    
    /**
     * Handles the verification request
     * 
     * @param request
     * @param verificationCode
     * @return 
     */
    @RequestMapping(value = "/verify-tutor-request", method = RequestMethod.POST, 
            params = {"verificationCode"})
    public ModelAndView verifyTutorRequestSubmission(
            HttpServletRequest request,
            @RequestParam(value = "verificationCode")String verificationCode) {
        
        // get the form
        RequestTutorForm form = (RequestTutorForm)getFromSessionScope(request, 
                RequestTutorForm.class);
        
        if (form == null) {
            invalidateCurrentSession(request);
            return sendRedirect("/request-a-tutor");
        }
        
        // clear all existing errors
        form.setErrorsEncountered(new ArrayList<String>());
        
        // check that the date is still within 5 minutes
        Date date = form.getDateGeneratedVerificationCode();
        if (date == null || 
                Seconds.secondsBetween(new DateTime(date), 
                        new DateTime(new Date())).getSeconds() > (60 * 5)) {
            
            form.getErrorsEncountered().add("The verification time window has expired.");
            // clear the verification code
            form.setVerificationCode(0);
            form.setDateGeneratedVerificationCode(null);
            // save to the session scope
            saveToSessionScope(request, form);
            
            return createModelAndView("request-a-tutor-verification");
        }
        
        // check the verification code parameter
        if (verificationCode == null || verificationCode.length() != 9) {
            
            form.getErrorsEncountered().add("The verification is too short.");
            // save to the session scope
            saveToSessionScope(request, form);
            // return the page for the verification code
            return createModelAndView("request-a-tutor-verification");
        }
        
        else {
            
            // compare the verification code
            if ((Long.parseLong(verificationCode) == form.getVerificationCode() &&
                RequestTutorControllerHelper.isNaN(verificationCode) == false)) {
                
                // check if this tutor request is completely unique
                /*if (helper.isTutorRequestCompletelyUnique(tutorRequestDAO, form) == false) {
                    return createModelAndView("request-a-tutor-submitted");
                } 
                
                else { */
                
                // generate the date this was received
                // and assign a temporary ref number
                form.getTutorRequest().setReferenceNumber("");
                form.getTutorRequest().setDateReceived(new Date());
                form.getTutorRequest().setReceived(false);
                
                // save the data 
                // into the database
                tutorRequestDAO.saveTutorRequest(form.getTutorRequest());

                // start a thread to send an email to the 
                // manager about this new request for a tutor
                helper.sendTutorRequestNotificationEmailAsync(form);

                // start a thread to send an email to the applicant to
                // confirm that the application was received
                helper.sendApplicantReceiptNotificationEmailAsync(form);
                //}
                
            }
            
            else {
                
                form.getErrorsEncountered().add("The verification does not match.");
                // save to the session scope
                saveToSessionScope(request, form);
                // return the page for the verification code
                return createModelAndView("request-a-tutor-verification");
                
            }
            
        }
        
        return createModelAndView("request-a-tutor-submitted");
        
    }
     
}
