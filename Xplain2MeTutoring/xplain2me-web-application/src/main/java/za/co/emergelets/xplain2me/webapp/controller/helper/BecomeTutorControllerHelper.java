package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import za.co.emergelets.util.CellphoneNumberValidator;
import za.co.emergelets.util.EmailAddressValidator;
import za.co.emergelets.util.SouthAfricanIdentityTool;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.emergelets.xplain2me.dao.CitizenshipDAO;
import za.co.emergelets.xplain2me.dao.GenderDAO;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.webapp.component.BecomeTutorForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

public class BecomeTutorControllerHelper extends GenericController {
    
    private static final Logger LOG = 
            Logger.getLogger(BecomeTutorControllerHelper.class.getName(), null);
    
    public BecomeTutorControllerHelper() {
    }
    
    /**
     * Populates the different
     * citizenship entries from the data source
     * 
     * @param form
     * @param dao 
     */
    public void populateCitizenshipEntries(BecomeTutorForm form, CitizenshipDAO dao) {
        if (form == null || dao == null) return;
        form.setCitizenships(dao.getAllCitizenships());
    }
    
    /**
     * Populates the entries 
     * for gender from repository
     * 
     * @param form
     * @param dao 
     */
    public void populateGenderEntries(BecomeTutorForm form, GenderDAO dao) {
        if (form == null || dao == null) return;
        form.setGender(dao.getAllGender());
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
    public BecomeTutorRequest createBecomeTutorRequestInstance(HttpServletRequest request, 
            BecomeTutorForm form) {
        
        BecomeTutorRequest instance = new BecomeTutorRequest();

        instance.setLastName(getParameterValue(request, "lastName"));
        instance.setFirstNames(getParameterValue(request, "firstNames"));
        
        // resolve gender 
        String genderId = getParameterValue(request, "gender");
        for (Gender gender : form.getGender()) {
            if (gender.getId().equalsIgnoreCase(genderId)) {
                instance.setGender(gender);
                break;
            }
        }
        
        // resolve citizenship
        String citizenshipId = getParameterValue(request, "citizenship");
        for (Citizenship citizenship : form.getCitizenships()) {
            if (citizenship.getId() == Integer.parseInt(citizenshipId)) {
                instance.setCitizenship(citizenship);
                break;
            }
        }
        
        // resolve date of birth
        String dateOfBirthString = getParameterValue(request, "dateOfBirth");
        try {
            Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd")
                    .parse(dateOfBirthString);
            
            instance.setDateOfBirth(dateOfBirth);
        }
        catch (ParseException e) {
            instance.setDateOfBirth(new Date());
        }
        
        instance.setIdentityNumber(getParameterValue(request, "idNumber"));
        instance.setEmailAddress(getParameterValue(request, "emailAddress"));
        instance.setContactNumber(getParameterValue(request, "contactNumber"));
        instance.setStreetAddress(getParameterValue(request, "streetAddress"));
        instance.setSuburb(getParameterValue(request, "suburb"));
        instance.setCity(getParameterValue(request, "city"));
        instance.setAreaCode(getParameterValue(request, "areaCode")); 
        
        instance.setAgreedToTermsOfService(true);
        instance.setDateSubmitted(new Date());
        
        return instance;
    }
      
    
    /**
     * Validates the personal information of the
     * tutor applicant
     * 
     * @param form
     * @return 
     */
    public boolean validatePersonalInformation(BecomeTutorForm form) {
        
        LOG.info("validationg the personal information for the "
                + "tutor application form...");
        
        if (form == null) {
            LOG.warning("The form is NULL"); 
            return false;
        }
        
        BecomeTutorRequest request = 
                form.getBecomeTutorRequest();
        int count = 0;
        
         if (request.getLastName() == null || 
                request.getLastName().length() < 2) {
            count++;
            form.getErrorsEncountered().add("Last Name is too short.");
        }
        
        if (request.getFirstNames() == null || 
                request.getFirstNames().length() < 2) {
            count++;
            form.getErrorsEncountered().add("First Names is too short.");
        }
        
        if (request.getIdentityNumber() == null || 
                SouthAfricanIdentityTool.isValid(request.getIdentityNumber()) == false) {
            count++;
            form.getErrorsEncountered().add("Identity Number does not appear authentic.");
        }
        
        Date now = new Date();
        
        if (request.getDateOfBirth() == null || 
                request.getDateOfBirth().after(now)) {
            count++;
            form.getErrorsEncountered().add("Date of birth has invalid format. Use: YYYY-MM-DD");
        }
        
        
        return (count == 0);
        
    }
    
    /**
     * Validates the contact information of the
     * tutor applicant
     * @param form
     * @return 
     */
    public boolean validateContactInformation(BecomeTutorForm form) {
        if (form == null)
            return false;
        
        BecomeTutorRequest request = 
                form.getBecomeTutorRequest();
        int count = 0;
        
        if (request.getContactNumber() == null || 
                !CellphoneNumberValidator.isCellphoneNumberValid(
                        "27" + request.getContactNumber().substring(1))) {
            count++;
            form.getErrorsEncountered().add("Contact Number does not appear authentic.");
        }
        
        if (request.getEmailAddress() == null || 
                !EmailAddressValidator.isEmailAddressValid(
                        request.getEmailAddress())) {
            count++;
            form.getErrorsEncountered().add("Email Address does not appear authentic.");
        }
        
        return (count == 0);
    }
    
    /**
     * Validates the address details
     * 
     * @param form
     * @return 
     */
    public boolean validateAddressInformation(BecomeTutorForm form) {
        
        if (form == null) return false;
        
        BecomeTutorRequest request = form.getBecomeTutorRequest();
        int count = 0;
        
        if (request.getStreetAddress() == null || 
                request.getStreetAddress().length() < 5) {
            count++;
            form.getErrorsEncountered().add("Street Address is too short.");
        }
        
        if (request.getSuburb() == null || 
                request.getSuburb().length() < 3) {
            count++;
            form.getErrorsEncountered().add("Suburb is too short.");
        }
        
        if (request.getCity() == null || 
                request.getCity().length() < 3) {
            count++;
            form.getErrorsEncountered().add("City is too short.");
        }
        
        if (request.getAreaCode() == null || 
                request.getAreaCode().length() < 4) {
            count++;
            form.getErrorsEncountered().add("Area Code is too short.");
        }
        
        return (count == 0);
    }
    
    /**
     * Determines if tutor application is
     * completely unique
     * 
     * @param becomeTutorRequestDAO
     * @param form
     * @return 
     */
    public boolean isTutorApplicationCompletelyUnique(BecomeTutorRequestDAO becomeTutorRequestDAO, 
            BecomeTutorForm form) {
        
        if (form == null || becomeTutorRequestDAO == null)
            return false;
        
        boolean isCompletelyUnique = becomeTutorRequestDAO
                .isTutorApplicationUnique(form.getBecomeTutorRequest());
        
        if (!isCompletelyUnique)
            form.getErrorsEncountered().add(
                    "It looks like you have submitted a "
                            + "request for tutor job application.");
        
        return isCompletelyUnique;
    }
    
    /**
     * Verifies the reCAPCTHA
     * challenge
     * 
     * @param request
     * @param form
     * @return 
     */
    public boolean verifyReCaptchaCode(HttpServletRequest request, BecomeTutorForm form) {
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
}
