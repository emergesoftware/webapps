package za.co.emergelets.xplain2me.webapp.component;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.AcademicLevel;
import za.co.emergelets.xplain2me.entity.AcademicLevelsTutoredBefore;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;
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
    private Map<Long, AcademicLevel> academicLevels;
    private List<Gender> gender;
    
     // verification fields
    private long verificationCode;
    private Date dateGeneratedVerificationCode;
    
    // reCAPTCHA fields
    private String reCaptchaChallenge;
    private String reCaptchaResponse;
    
    private List<AcademicLevelsTutoredBefore> academicLevelsTutoredBefore;
    private List<BecomeTutorSupportingDocument> supportingDocuments;
    
    private List<File> emailAttachments;
    
    public BecomeTutorForm() {
        this.citizenships = new ArrayList<>();
        this.gender = new ArrayList<>();
        this.academicLevels = new TreeMap<>();
        
        this.academicLevelsTutoredBefore = new ArrayList<>();
        this.supportingDocuments = new ArrayList<>();
        
        this.errorsEncountered = new ArrayList<>();
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

    public Map<Long, AcademicLevel> getAcademicLevels() {
        return academicLevels;
    }

    public void setAcademicLevels(Map<Long, AcademicLevel> academicLevels) {
        this.academicLevels = academicLevels;
    }

    public List<AcademicLevelsTutoredBefore> getAcademicLevelsTutoredBefore() {
        return academicLevelsTutoredBefore;
    }

    public void setAcademicLevelsTutoredBefore(List<AcademicLevelsTutoredBefore> academicLevelsTutoredBefore) {
        this.academicLevelsTutoredBefore = academicLevelsTutoredBefore;
    }

    public List<BecomeTutorSupportingDocument> getSupportingDocuments() {
        return supportingDocuments;
    }

    public void setSupportingDocuments(List<BecomeTutorSupportingDocument> supportingDocuments) {
        this.supportingDocuments = supportingDocuments;
    }

    public List<File> getEmailAttachments() {
        return emailAttachments;
    }

    public void setEmailAttachments(List<File> emailAttachments) {
        this.emailAttachments = emailAttachments;
    }
    
    
}
