package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.AcademicLevel;
import za.co.emergelets.xplain2me.entity.Subject;
import za.co.emergelets.xplain2me.entity.TutorRequest;

@Component
public class RequestTutorForm implements Serializable {
    
    // tutor request
    private TutorRequest tutorRequest;
    // academic levels
    private SortedMap<Long, AcademicLevel> academicLevels;
    // 
    private SortedMap<Long, Subject> subjects;
    // errors encountered
    private List<String> errorsEncountered;
    
    // verification fields
    private long verificationCode;
    private Date dateGeneratedVerificationCode;
    
    // reCAPTCHA fields
    private String reCaptchaChallenge;
    private String reCaptchaResponse;
   
    
    public RequestTutorForm() {
        this.errorsEncountered = new ArrayList<String>();
    }

    public TutorRequest getTutorRequest() {
        return tutorRequest;
    }

    public void setTutorRequest(TutorRequest tutorRequest) {
        this.tutorRequest = tutorRequest;
    }

    public List<String> getErrorsEncountered() {
        return errorsEncountered;
    }

    public void setErrorsEncountered(List<String> errorsEncountered) {
        this.errorsEncountered = errorsEncountered;
    }

    public SortedMap<Long, AcademicLevel> getAcademicLevels() {
        return academicLevels;
    }

    public void setAcademicLevels(SortedMap<Long, AcademicLevel> academicLevels) {
        this.academicLevels = academicLevels;
    }

    public SortedMap<Long, Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(SortedMap<Long, Subject> subjects) {
        this.subjects = subjects;
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

    
}
