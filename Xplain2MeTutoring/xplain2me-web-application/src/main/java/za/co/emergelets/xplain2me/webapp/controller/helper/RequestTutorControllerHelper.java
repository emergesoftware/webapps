package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import za.co.emergelets.util.EmailAddressValidator;
import za.co.emergelets.util.ReCaptchaUtil;
import za.co.emergelets.util.VerificationCodeGenerator;
import za.co.emergelets.util.mail.EmailSender;
import za.co.emergelets.util.mail.EmailTemplateFactory;
import za.co.emergelets.util.mail.EmailTemplateType;
import za.co.emergelets.xplain2me.entity.Subject;
import za.co.emergelets.xplain2me.entity.TutorRequest;
import za.co.emergelets.xplain2me.entity.TutorRequestSubject;
import za.co.emergelets.xplain2me.webapp.component.RequestTutorForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

public class RequestTutorControllerHelper extends GenericController {
    
    private static final Logger LOG = 
            Logger.getLogger(RequestTutorControllerHelper.class.getName(), null);
    
    public RequestTutorControllerHelper() {
    }
    
    /**
     * Asynchronously sends an email to 
     * the app manager about a new student
     * or tutor request.
     * @param form
     */
    public void sendTutorRequestNotificationEmailAsync(final RequestTutorForm form) {
        
        if (form == null) return;
        
        try {
            
            new Thread(
                    new Runnable() {

                @Override
                public void run() {
                    TutorRequest request = form.getTutorRequest();
                    String body = "";

                    // setup the current date
                    String date = new SimpleDateFormat(
                            "dd-MMM-yyyy HH:mm").format(new Date());

                    // resolve the gender
                    String gender = "Male";
                    if (request.isGender() == false)
                        gender = "Female";

                    // resolve the subjects
                    StringBuilder subjects = new StringBuilder();
                    for (TutorRequestSubject _subject : request.getSubjects()) {
                        subjects.append("<li>").append(_subject.getSubject().getName()).append("</li>\n");
                    }

                    // the subject
                    String subject = "New Tutor Request | Xplain2Me Tutoring Services";

                    // template injections
                    Map<String, Object> values = new HashMap<>();
                    values.put("date_completed", date);
                    values.put("last_name", request.getLastName());
                    values.put("first_names", request.getFirstNames());
                    values.put("gender", gender);
                    values.put("email_address", request.getEmailAddress());
                    values.put("contact_number", request.getContactNumber());
                    values.put("street_address", request.getPhysicalAddress());
                    values.put("suburb", request.getSuburb());
                    values.put("city", request.getCity());
                    values.put("area_code", request.getAreaCode());
                    values.put("academic_level", request.getGradeLevel().getDescription());
                    values.put("subjects", subjects);
                    values.put("additional_information", request.getAdditionalInformation());
                    values.put("reference_number", request.getReferenceNumber());

                    try {
                        // the message body
                        body = EmailTemplateFactory.injectValuesIntoEmailTemplate(
                                EmailTemplateFactory.getTemplateByType(EmailTemplateType
                                        .NotifyNewTutorRequest),
                                values);
                    } catch (IOException ex) {
                        Logger.getLogger(RequestTutorControllerHelper.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
            
                    // send email
                    EmailSender emailSender = new EmailSender();
                    emailSender.setToAddress(EmailSender.APP_MANAGER_EMAIL_ADDRESS);
                    emailSender.setSubject(subject);
                    emailSender.setHtmlBody(true);
                    emailSender.sendEmail(body);
                }
            }
            ).start();
            
        }
        
        catch (Exception e) {
            LOG.log(Level.SEVERE, "... email sending failed: {0}", 
                    e.getMessage());
        }
        
    }
    
    /**
     * Sends an email to the applicant
     * to notify that their request for a tutor
     * has been received.
     * 
     * @param form 
     */
    public void sendApplicantReceiptNotificationEmailAsync(final RequestTutorForm form) {
        
        if (form == null) return;
        
        new Thread(
                new Runnable(){

                    @Override
                    public void run() {
                        
                        // resolve the first provided
                        // first name
                        String firstName = form.getTutorRequest()
                                .getFirstNames().split(" ")[0];

                        // set the subject
                        String subject = "Do not reply | Tutor Request | Xplain2Me Tutoring Services";

                        // set the body of the email
                        String body = "\n" +
                               "Howdy, " + firstName + "\n\n" +
                               "Thank you for your interest in our tutoring services. \n" +
                               "We have received your request for a tutor and we will be \n" +
                               "in contact very soon. \n\n" + 
                               "Your reference number for this request is: " 
                                        + form.getTutorRequest().getReferenceNumber() + "\n" + 
                               "Use your reference number whenever communicating about your \n" +
                               "tutor request application.\n\n" +
                               "Do not hesitate to contact us - find our details from our\n" + 
                               "website: http:" + "//" + "www." + "xplain2me.co.za/\n\n" + 
                               "Yours truly, \n" +
                               "Xplain2Me Tutoring Services\n\n";

                        EmailSender emailSender = new EmailSender();
                        emailSender.setToAddress(form.getTutorRequest().getEmailAddress());
                        emailSender.setSubject(subject);
                        emailSender.setHtmlBody(false);
                        
                        emailSender.sendEmail(body);
                        
                    }
                }
        ).start();
        
    }
    
    /**
     * Sends the verification code to the
     * user using email async
     * 
     * @param form 
     */
    public void sendUserVerificationCodeUsingEmailAsync(final RequestTutorForm form) {
        
        if (form == null) return;
        
        new Thread(
           new Runnable() {

            @Override
            public void run() {
                // resolve the first provided
                // first name
                String firstName = form.getTutorRequest()
                        .getFirstNames().split(" ")[0];
                
                // set the subject
                String subject = "Do not reply | Verification Code | Xplain2Me Tutoring Services";
                
                // body
                String body = "";
                
                // injection values
                Map<String, Object> values = new HashMap<>();
                values.put("name", firstName);
                values.put("verification_code", form.getVerificationCode());
                values.put("request_type", "- Request a tutor");

                try {
                    // set the body of the email
                    body = EmailTemplateFactory.injectValuesIntoEmailTemplate(
                            EmailTemplateFactory
                                    .getTemplateByType(EmailTemplateType.SendUserVerificatioCode),
                            values);
                } catch (IOException ex) {
                    LOG.severe(" ... html template not found ... "); 
                }
                
                EmailSender emailSender = new EmailSender();
                emailSender.setToAddress(form.getTutorRequest().getEmailAddress());
                emailSender.setSubject(subject);
                emailSender.setHtmlBody(true);
                
                emailSender.sendEmail(body);
            }
        }
        
        ).start();
        
    }
    
    /**
     * Reads off the http request parameter values
     * grabs the values and creates an instance of the
     * TutorRequest
     * 
     * @param request
     * @param form
     * @return 
     */
    public TutorRequest createTutorRequestInstance(HttpServletRequest request, 
            RequestTutorForm form) {
        
        TutorRequest instance = new TutorRequest();

        instance.setLastName(getParameterValue(request, "lastName"));
        instance.setFirstNames(getParameterValue(request, "firstNames"));
        instance.setGender(Boolean.parseBoolean(getParameterValue(request, "gender")));
        instance.setEmailAddress(getParameterValue(request, "emailAddress"));
        instance.setContactNumber(getParameterValue(request, "contactNumber"));
        instance.setPhysicalAddress(getParameterValue(request, "physicalAddress"));
        instance.setSuburb(getParameterValue(request, "suburb"));
        instance.setCity(getParameterValue(request, "city"));
        instance.setAreaCode(getParameterValue(request, "areaCode")); 
        instance.setGradeLevel(form.getAcademicLevels().get(
                Long.parseLong(getParameterValue(request, "gradeLevel"))));
        
        instance.setSubjects(new ArrayList<TutorRequestSubject>());
        String[] subjectIds = getParameterValues(request, "subjects");
        Subject subject = null;
        TutorRequestSubject tutorRequestSubject = null;
        
        for (String id : subjectIds) {
            
            subject = form.getSubjects().get(Long.parseLong(id));
            tutorRequestSubject = new TutorRequestSubject();
            tutorRequestSubject.setSubject(subject); 
            tutorRequestSubject.setTutorRequest(instance); 
            
            instance.getSubjects().add(tutorRequestSubject);
            
        }
        
        instance.setAdditionalInformation(getParameterValue(request, "additionalInformation"));
        instance.setAgreeToTermsOfService(true);
        
        return instance;
    }
    
    /**
     * Validates the personal information
     * 
     * @param form
     * @return 
     */
    public boolean validatePersonalnformation(RequestTutorForm form) {
        
        if (form == null) return false;
        
        int count = 0;
        
        if (form.getTutorRequest().getLastName() == null || 
                form.getTutorRequest().getLastName().length() < 2) {
            count++;
            form.getErrorsEncountered().add("Last Name is too short.");
        }
        
        if (form.getTutorRequest().getFirstNames() == null || 
                form.getTutorRequest().getFirstNames().length() < 2) {
            count++;
            form.getErrorsEncountered().add("First Names is too short.");
        }
        
        return (count == 0);
        
    }
    
    /**
     * Validates the address details
     * 
     * @param form
     * @return 
     */
    public boolean validateAddressInformation(RequestTutorForm form) {
        
        if (form == null) return false;
        
        int count = 0;
        
        if (form.getTutorRequest().getPhysicalAddress() == null || 
                form.getTutorRequest().getPhysicalAddress().length() < 5) {
            count++;
            form.getErrorsEncountered().add("Physical Address is too short.");
        }
        
        if (form.getTutorRequest().getSuburb() == null || 
                form.getTutorRequest().getSuburb().length() < 3) {
            count++;
            form.getErrorsEncountered().add("Suburb is too short.");
        }
        
        if (form.getTutorRequest().getCity() == null || 
                form.getTutorRequest().getCity().length() < 3) {
            count++;
            form.getErrorsEncountered().add("City is too short.");
        }
        
        if (form.getTutorRequest().getAreaCode() == null || 
                form.getTutorRequest().getAreaCode().length() < 4) {
            count++;
            form.getErrorsEncountered().add("Area Code is too short.");
        }
        
        return (count == 0);
    }
    
    /**
     * Validates the contact details.
     * 
     * @param form
     * @return 
     */
    public boolean validateContactInformation(RequestTutorForm form) {
        if (form == null)
            return false;
        
        int count = 0;
        
        if (form.getTutorRequest().getContactNumber() == null ||
                form.getTutorRequest().getContactNumber().isEmpty()) {
            count++;
            form.getErrorsEncountered().add("Contact Number does not appear authentic.<br/>");
        }
        
        if (form.getTutorRequest().getEmailAddress() == null || 
                !EmailAddressValidator.isEmailAddressValid(
                        form.getTutorRequest().getEmailAddress())) {
            count++;
            form.getErrorsEncountered().add("Email Address does not appear authentic.<br/>");
        }
        
        return (count == 0);
    }
    
    /**
     * Validates to check that the request for a tutor
     * is completely unique
     * 
     * @param tutorRequestDAO
     * @param form
     * @return 
     */
    /* public boolean isTutorRequestCompletelyUnique(TutorRequestDAO tutorRequestDAO, 
            RequestTutorForm form) {
        
        if (form == null || tutorRequestDAO == null)
            return false;
        
        boolean isCompletelyUnique = tutorRequestDAO
                .isTutorRequestCompletelyUnique(form.getTutorRequest());
        
        if (!isCompletelyUnique)
            form.getErrorsEncountered().add(
                    "It looks like you have submitted a "
                            + "request for a tutor before.");
        
        
        return isCompletelyUnique;
    } */
    
    /**
     * Verify the reCAPTCHA code
     * 
     * @param request
     * @param form
     * @return 
     */
    public boolean verifyReCaptchaCode(HttpServletRequest request, RequestTutorForm form) {
        
        String challenge = form.getReCaptchaChallenge();
        String uresponse = form.getReCaptchaResponse();
        
        return ReCaptchaUtil.verifyReCaptchaCode(request, challenge, 
                uresponse, form.getErrorsEncountered());
        
    }
    
    /**
     * Generates a verification code and
     * the date the code was generated
     * 
     * @param form 
     */
    public void generateRandomVerificationCode(RequestTutorForm form) {
        
        if (form == null) {
            return;
        }
        
        form.setVerificationCode(VerificationCodeGenerator.generateVerificationCode());
        form.setDateGeneratedVerificationCode(new Date());
        
    }
    
}
