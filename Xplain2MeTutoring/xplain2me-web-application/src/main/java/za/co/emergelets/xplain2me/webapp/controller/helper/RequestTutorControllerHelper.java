package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import za.co.emergelets.util.CellphoneNumberValidator;
import za.co.emergelets.util.EmailAddressValidator;
import za.co.emergelets.xplain2me.dao.TutorRequestDAO;
import za.co.emergelets.xplain2me.entity.Subject;
import za.co.emergelets.xplain2me.entity.TutorRequest;
import za.co.emergelets.xplain2me.entity.TutorRequestSubject;
import za.co.emergelets.xplain2me.webapp.component.RequestTutorForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

public class RequestTutorControllerHelper extends GenericController {
    
    public RequestTutorControllerHelper() {
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
                !CellphoneNumberValidator.isCellphoneNumberValid(
                        "27" + form.getTutorRequest().getContactNumber().substring(1))) {
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
    public boolean isTutorRequestCompletelyUnique(TutorRequestDAO tutorRequestDAO, 
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
    }
    
    /**
     * Verify the reCAPTCHA code
     * 
     * @param request
     * @param form
     * @return 
     */
    public boolean verifyReCaptchaCode(HttpServletRequest request, RequestTutorForm form) {
        
        String remoteAddr = request.getRemoteAddr();
        
        ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
        reCaptcha.setPrivateKey("6LfJjvsSAAAAAGO6AP2DK_l_v_B82jMHPKpXLMPh");

        String challenge = form.getReCaptchaChallenge();
        String uresponse = form.getReCaptchaResponse();
        
        if ((challenge == null || challenge.isEmpty()) || 
                (uresponse == null || uresponse.isEmpty())) {
            form.getErrorsEncountered().add("The CATCHA code is not correct.");
            return false;
        }
        
        ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, 
                challenge, uresponse);

        boolean isValid = reCaptchaResponse.isValid();
        if (isValid == false)
            form.getErrorsEncountered().add("The CAPTCHA Code is not correct");
        
        return isValid;
        
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
        
        Random random = new Random(System.currentTimeMillis());
        form.setVerificationCode(100000000 + random.nextInt(899999999));
        form.setDateGeneratedVerificationCode(new Date());
        
    }
    
    /**
     * Checks if the string value is
     * not a number or is.
     * 
     * @param value
     * @return 
     */
    public static boolean isNaN(String value) {
        if (value == null || value.isEmpty())
            return true;
        try {
            Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            return true;
        }
        
        return false;
    }
    
}
