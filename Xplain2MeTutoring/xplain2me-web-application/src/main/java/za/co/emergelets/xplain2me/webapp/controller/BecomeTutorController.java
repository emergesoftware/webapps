package za.co.emergelets.xplain2me.webapp.controller;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.EmailSender;
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
    private CitizenshipDAO citizenshipDAO;
    private GenderDAO genderDAO;
    private AcademicLevelDAO academicLevelDAO;
    private BecomeTutorRequestDAO becomeTutorRequestDAO;
    
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
    public ModelAndView processTutorJobApplicationRequest(HttpServletRequest request) throws ParseException {
        
        // get the form from the session
        BecomeTutorForm form = (BecomeTutorForm)getFromSessionScope(request,
                BecomeTutorForm.class);
        if (form == null || 
                form.getBecomeTutorRequest() == null) {
            
            LOG.warning("... invalid request ...");
            invalidateCurrentSession(request);
            return sendRedirect("/become-a-tutor?error=yes");
        }
        
        // clear all errors - if any
        form.getErrorsEncountered().clear();
        
        if (    // validate the personal information
                helper.validatePersonalInformation(form) && 
                // validate the contact information 
                helper.validateContactInformation(form) &&
                // validate the address information
                helper.validateAddressInformation(form) &&
                // validate the reCATCHA challenge
                helper.verifyReCaptchaCode(request, form) &&
                // check if the tutor application 
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
            new Thread(new BecomeTutorController
                    .AsyncApplicationReceiptNotifier(form)).start();
            
            // start a thread to send an email to the 
                    // manager about this new request for a tutor
            new Thread(new BecomeTutorController
                            .AsyncNewApplicantNotifier(form)).start();
            
            // return the page for the results
            return createModelAndView("become-a-tutor-submitted");
            
        }
        
        else {
        
            // save the form to the session
            saveToSessionScope(request, form);
            
            // return the page for the application
            return createModelAndView("become-a-tutor");
        }
    }
    
    /**
     * Asynchronous thread to send an email to the 
     * applicant on receipt of the 
     */
    private class AsyncApplicationReceiptNotifier implements Runnable {
        
        private BecomeTutorForm form;
        private EmailSender emailSender;
        
        private AsyncApplicationReceiptNotifier() {
            this.form = null;
            this.emailSender = null;
        }
        
        public AsyncApplicationReceiptNotifier(BecomeTutorForm form) {
            this();
            this.form = form;
            this.emailSender = new EmailSender();
        }
        
        @Override
        public void run() {
            
            String subject = null, 
                    body = null;
            
            if (this.form != null) {
                
                // resolve the first provided
                // first name
                String firstName = form.getBecomeTutorRequest()
                        .getFirstNames().split(" ")[0];
                
                // set the subject
                subject = "Do not reply | Tutor Employment Request | Xplain2Me Tutoring Services";
                
                // set the body of the email
                body = "\n" +
                       "Howdy, " + firstName + "\n\n" +
                       "Thank you for your interest in joining our team of passionate tutors. \n" +
                       "We have received your request for a tutor employment application and we will be \n" +
                       "in contact with you very soon. \n\n" + 
                       "Do not hesitate to contact us - find our details from our\n" + 
                       "website: http:" + "//" + "xplain2me.co.za/\n\n" + 
                       "Yours truly, \n" +
                       "Xplain2Me Tutoring Services\n\n";
                
                emailSender.sendEmail(form.getBecomeTutorRequest().getEmailAddress(), 
                        subject, body);
                
            }
            
            
            
        }
        
    }
    
    /**
     * Asynchronously sends an email to 
     * the app manager about a new student
     * or tutor request.
     */
    private class AsyncNewApplicantNotifier implements Runnable {
        
        private static final String SEND_TO = "wchigwaza@yahoo.com";
        
        private final BecomeTutorForm form;
        private final EmailSender emailSender;
        
        private AsyncNewApplicantNotifier(){
            this.form = null;
            this.emailSender = null;
        }
        
        public AsyncNewApplicantNotifier(BecomeTutorForm form) {
            this.form = form;
            this.emailSender = new EmailSender();
        }
        
        @Override
        public void run() {
            
            String subject = null,
                   body = null;
            
            if (form != null) {
                
                // setup the current date
                String date = new SimpleDateFormat(
                        "dd-MMM-yyyy HH:mm").format(new Date());
                
                // the subject
                subject = "New Tutor Employment Application | Xplain2Me Tutoring Services";
                
                // the message body
                body = "\n" + 
                       "Howdy, \n\n" + 
                       "A new request for a tutor employment application was completed on " + date + ".\n" +
                       "Below are the details of this request:\n\n" + 
                       "Last Name: " + form.getBecomeTutorRequest().getLastName().toUpperCase() + "\n" +
                       "First Name(s): " + form.getBecomeTutorRequest().getFirstNames().toUpperCase() + "\n" + 
                       "Gender: " + form.getBecomeTutorRequest().getGender().getDescription() + "\n" +
                       "Email Address: " + form.getBecomeTutorRequest().getEmailAddress() + "\n" + 
                       "Contact Number: " + form.getBecomeTutorRequest().getContactNumber() + "\n" + 
                       "Identity Number: " + form.getBecomeTutorRequest().getIdentityNumber() + "\n" +
                       "Citizenship: " + form.getBecomeTutorRequest().getCitizenship().getDescription() + "\n" +
                       "Street Address: " + form.getBecomeTutorRequest().getStreetAddress().toUpperCase() + "\n" + 
                       "Suburb: " + form.getBecomeTutorRequest().getSuburb().toUpperCase() + "\n" + 
                       "City: " + form.getBecomeTutorRequest().getCity().toUpperCase() + "\n" + 
                       "Area Code: " + form.getBecomeTutorRequest().getAreaCode() + "\n" + 
                       "\n";
                
                emailSender.sendEmail(SEND_TO, subject, body);
            }
            
            
        }
        
    }
}
