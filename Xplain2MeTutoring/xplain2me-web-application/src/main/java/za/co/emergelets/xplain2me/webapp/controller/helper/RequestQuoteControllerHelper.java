package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import za.co.emergelets.util.EmailAddressValidator;
import za.co.emergelets.util.EmailSender;
import za.co.emergelets.util.ReCaptchaUtil;
import za.co.emergelets.xplain2me.entity.Subject;
import za.co.emergelets.xplain2me.webapp.component.RequestQuoteForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;


public class RequestQuoteControllerHelper extends GenericController {
    
    private static final Logger LOG = 
            Logger.getLogger(RequestQuoteControllerHelper.class.getName(), null);
    
    public RequestQuoteControllerHelper() {
    }
    
    /**
     * Pulls the HTTP parameter values
     * into the form 
     * 
     * @param request
     * @param form 
     */
    public void assignHttpParametersToRequestQuoteForm(HttpServletRequest request, 
            RequestQuoteForm form) {
        
        if (form == null) return;
        
        form.setLastName(getParameterValue(request, "lastName"));
        form.setFirstNames(getParameterValue(request, "firstNames"));
        form.setEmailAddress(getParameterValue(request, "emailAddress"));
        form.setContactNumber(getParameterValue(request, "contactNumber"));
        form.setStreetAddress(getParameterValue(request, "streetAddress"));
        
        long academicLevelId = Long.parseLong(getParameterValue(request, 
                "academicLevel"));
        form.setAcademicLevel(form.getAcademicLevels().get(academicLevelId));
        
        long provinceId = Long.parseLong(getParameterValue(request, "province"));
        form.setProvince(form.getProvinces().get(provinceId));
        
        form.setNumberOfLessonsRequired(Integer.parseInt(getParameterValue(request, 
                "numberOfLessonsRequired")));
        
        String[] subjectsSelected = getParameterValues(request, "subjects");
        List<Subject> subjects = new ArrayList<>();
        for (String subjectId : subjectsSelected) {
            subjects.add(form.getSubjects().get(Long.parseLong(subjectId)));
        }
        
        form.setSelectedSubjects(subjects);
        
    }
    
    /**
     * Validates the personal information 
     * @param form
     * @return 
     */
    public boolean validateRequestQuoteInformation(RequestQuoteForm form) {
        int count = 0;
        
        if (form == null) return false;
        
        if (form.getLastName() == null || form.getLastName().isEmpty()) {
            count++;
            form.getErrorsEncountered().add("Last Name: value too short.");
        }
        
        if (form.getFirstNames() == null || form.getFirstNames().isEmpty()) {
            count++;
            form.getErrorsEncountered().add("First Names: value too short.");
        }
        
        if (form.getEmailAddress() == null || 
                EmailAddressValidator.isEmailAddressValid(form.getEmailAddress()) == false) {
            count++;
            form.getErrorsEncountered().add("Email Address: does not appear authentic.");
        }
        
        if (form.getContactNumber() == null || 
                form.getContactNumber().isEmpty()) {
            count++;
            form.getErrorsEncountered().add("Contact Number: value too short.");
        }
        
        if (form.getStreetAddress() == null || 
                form.getStreetAddress().isEmpty()) {
            count++;
            form.getErrorsEncountered().add("Physical Address: value too short.");
        }
        
        if (form.getAcademicLevel() == null) {
            count++;
            form.getErrorsEncountered().add("Academic Level: did not select an option.");
        }
        
        if (form.getSelectedSubjects() == null || form.getSelectedSubjects().isEmpty()) {
            count++;
            form.getErrorsEncountered().add("Subjects: did not select at least ONE option.");
        }
        
        if (form.getNumberOfLessonsRequired() == 0) {
            count++;
            form.getErrorsEncountered().add("Number Of Lessons Required: must be at least ONE.");
        }
        
        if (form.getProvince() == null) {
            count++;
            form.getErrorsEncountered().add("Province: did not select an option.");
        }
        
        return (count == 0);
    }
    
    /**
     * Verifies the reCAPTCHA challenge
     * 
     * @param request
     * @param form
     * @return 
     */
    public boolean verifyReCaptchaChallenge(HttpServletRequest request, RequestQuoteForm form) {
        
        String challenge = request.getParameter("recaptcha_challenge_field");
        String userResponse = request.getParameter("recaptcha_response_field");
        
        return ReCaptchaUtil.verifyReCaptchaCode(request, challenge, userResponse, 
                form.getErrorsEncountered());
    }
    
    /**
     * Sends an email for the quote request
     * 
     * @param form 
     */
    public void sendQuoteRequestEmailAsync(final RequestQuoteForm form) {
        
        try {
        
            new Thread(
                    new Runnable() {

                @Override
                public void run() {
                    
                    String subject = "New Quote Request | Xplain2Me Tutoring";
                    String sender = "wchigwaza@yahoo.com";

                    StringBuilder subjects = new StringBuilder();
                    for (Subject item : form.getSelectedSubjects())
                        subjects.append(" - ").append(item.getName()).append("\n");

                    StringBuilder body = new StringBuilder();
                    body.append("Howdy, \n\n")

                        .append("Someone has requested a quote from you.\n")
                        .append("Below are the details of this quote request:\n\n")

                        .append("First Names: \t\t").append(form.getFirstNames()).append("\n")
                        .append("Last Name: \t\t").append(form.getLastName()).append("\n")
                        .append("Email Address: \t\t").append(form.getEmailAddress()).append("\n")
                        .append("Contact Number: \t\t").append(form.getContactNumber()).append("\n")
                        .append("Physical Address: \t\t").append(form.getStreetAddress()).append("\n")
                        .append("Student Level: \t\t").append(form.getAcademicLevel().getDescription()).append("\n")
                        .append("Subjects: \t\t").append(subjects.toString())
                        .append("Number of Lessons: \t\t").append(form.getNumberOfLessonsRequired()).append("\n")
                        .append("\n\n");

                    EmailSender email = new EmailSender();
                    email.sendEmail(sender, subject, body.toString());
                    
                }
            }
            ).start();
            
            
        }
        
        catch (Exception e) {
            LOG.log(Level.SEVERE, "Error while sending email: {0}", e.getMessage()); 
        }
        
    }
}
