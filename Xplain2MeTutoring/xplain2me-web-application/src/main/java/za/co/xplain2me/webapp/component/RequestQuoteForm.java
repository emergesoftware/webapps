package za.co.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import za.co.xplain2me.entity.AcademicLevel;
import za.co.xplain2me.entity.Province;
import za.co.xplain2me.entity.Subject;

@Component
public class RequestQuoteForm implements Serializable {
    
    private String firstNames;
    private String lastName;
    private String contactNumber;
    private String emailAddress;
    private String streetAddress;
    private Province province;
    private AcademicLevel academicLevel;
    private int numberOfLessonsRequired;
    private List<Subject> selectedSubjects;
    
    private Map<Long, AcademicLevel> academicLevels;
    private Map<Long, Province> provinces;
    private Map<Long, Subject> subjects;
    
    private List<String> errorsEncountered;
    
    public RequestQuoteForm() {
        
        this.academicLevels = new HashMap<>();
        this.provinces = new HashMap<>();
        this.subjects = new HashMap<>();
        
        this.numberOfLessonsRequired = 5;
        
        this.errorsEncountered = new ArrayList<>();
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public Map<Long, AcademicLevel> getAcademicLevels() {
        return academicLevels;
    }

    public void setAcademicLevels(Map<Long, AcademicLevel> academicLevels) {
        this.academicLevels = academicLevels;
    }

    public Map<Long, Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(Map<Long, Province> provinces) {
        this.provinces = provinces;
    }

    public List<String> getErrorsEncountered() {
        return errorsEncountered;
    }

    public void setErrorsEncountered(List<String> errorsEncountered) {
        this.errorsEncountered = errorsEncountered;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public int getNumberOfLessonsRequired() {
        return numberOfLessonsRequired;
    }

    public void setNumberOfLessonsRequired(int numberOfLessonsRequired) {
        this.numberOfLessonsRequired = numberOfLessonsRequired;
    }

    public Map<Long, Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(Map<Long, Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSelectedSubjects() {
        return selectedSubjects;
    }

    public void setSelectedSubjects(List<Subject> selectedSubjects) {
        this.selectedSubjects = selectedSubjects;
    }
    
    
}
