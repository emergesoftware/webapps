package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.Gender;

@Component
public class BecomeTutorForm implements Serializable {
    
    // the tutor application request
    private BecomeTutorRequest becomeTutorRequest;
    
    // the errors list
    private List<String> errorsEncountered;
    
    // lists for the drop downs
    private List<Citizenship> citizenships;
    private List<Gender> gender;
    
     // verification fields
    private long verificationCode;
    private Date dateGeneratedVerificationCode;
    
    // reCAPTCHA fields
    private String reCaptchaChallenge;
    private String reCaptchaResponse;
    
    public BecomeTutorForm() {
        this.citizenships = new ArrayList<Citizenship>();
        this.gender = new ArrayList<Gender>();
        
        this.errorsEncountered = new ArrayList<String>();
    }

    public List<Citizenship> getCitizenships() {
        return citizenships;
    }

    public void setCitizenships(List<Citizenship> citizenships) {
        this.citizenships = citizenships;
    }

    public List<Gender> getGender() {
        return gender;
    }

    public void setGender(List<Gender> gender) {
        this.gender = gender;
    }

    public BecomeTutorRequest getBecomeTutorRequest() {
        return becomeTutorRequest;
    }

    public void setBecomeTutorRequest(BecomeTutorRequest becomeTutorRequest) {
        this.becomeTutorRequest = becomeTutorRequest;
    }

    public long getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(long verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getDateGeneratedVerificationCode() {
        return dateGeneratedVerificationCode;
    }

    public void setDateGeneratedVerificationCode(Date dateGeneratedVerificationCode) {
        this.dateGeneratedVerificationCode = dateGeneratedVerificationCode;
    }

    public String getReCaptchaChallenge() {
        return reCaptchaChallenge;
    }

    public void setReCaptchaChallenge(String reCaptchaChallenge) {
        this.reCaptchaChallenge = reCaptchaChallenge;
    }

    public String getReCaptchaResponse() {
        return reCaptchaResponse;
    }

    public void setReCaptchaResponse(String reCaptchaResponse) {
        this.reCaptchaResponse = reCaptchaResponse;
    }

    public List<String> getErrorsEncountered() {
        return errorsEncountered;
    }

    public void setErrorsEncountered(List<String> errorsEncountered) {
        this.errorsEncountered = errorsEncountered;
    }
    
    
}
