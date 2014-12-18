package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.bo.validation.EmailAddressValidator;
import za.co.emergelets.util.ReCaptchaUtil;
import za.co.emergelets.util.mail.EmailSender;
import za.co.emergelets.util.mail.EmailTemplateFactory;
import za.co.emergelets.util.mail.EmailTemplateType;
import za.co.emergelets.xplain2me.entity.Subject;
import za.co.emergelets.xplain2me.webapp.component.RequestQuoteForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

@Component
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
        
        if (form == null) return;
        
            new Thread(
                new Runnable() {

                @Override
                public void run() {
                    
                    try {
                        // the subject
                        String subject = "New Quote Request | Xplain2Me Tutoring";

                        // resolve the subjects
                        StringBuilder subjects = new StringBuilder();                    
                        for (Subject item : form.getSelectedSubjects())
                            subjects.append("<li>").append(item.getName()).append("</li>\n");
                        
                        // setup the current date
                        String date = new SimpleDateFormat(
                            "dd-MMM-yyyy HH:mm").format(new Date());
                        
                        // map values for injection into template
                        Map<String, Object> values = new HashMap<>();
                        values.put("date_completed", date);
                        values.put("last_name", form.getLastName());
                        values.put("first_names", form.getFirstNames());
                        values.put("email_address", form.getEmailAddress());
                        values.put("contact_number", form.getContactNumber());
                        values.put("physical_address", form.getStreetAddress());
                        values.put("province", form.getProvince().getDescription());
                        values.put("academic_level", form.getAcademicLevel().getDescription());
                        values.put("subjects", subjects.toString());
                        values.put("number_of_lessons_required", form.getNumberOfLessonsRequired());
                        
                        // prepare html body
                        String body = EmailTemplateFactory.injectValuesIntoEmailTemplate(
                                EmailTemplateFactory.getTemplateByType(
                                                    EmailTemplateType.NotifyNewQuoteRequest), 
                                values);
                               
                        EmailSender emailSender = new EmailSender();
                        emailSender.setToAddress(EmailSender.APP_MANAGER_EMAIL_ADDRESS);
                        emailSender.setSubject(subject);
                        emailSender.setHtmlBody(true);
                        
                        emailSender.sendEmail(body);
                    }
                    
                    catch (IOException e) {
                        LOG.log(Level.SEVERE, "... could not send email, error: {0}",
                                e.getMessage());
                    }
                    
                    
                }
            }
        ).start();
            
    }
}
