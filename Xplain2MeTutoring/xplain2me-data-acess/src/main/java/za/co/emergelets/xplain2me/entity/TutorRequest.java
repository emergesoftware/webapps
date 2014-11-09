package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "tutor_request")
public class TutorRequest implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_request_id")
    private long id;
    
    // Personal Details
    @Column(name = "tutor_request_first_names", nullable = false)
    private String firstNames;
    
    @Column(name = "tutor_request_last_name", nullable = false)
    private String lastName;
    
    @Column(name = "tutor_request_gender", nullable = false)
    private boolean gender;
    
    // Contact Information
    @Column(name = "tutor_request_email_address", nullable = false, unique = true)
    private String emailAddress;
    
    @Column(name = "tutor_request_contact_number", nullable = false, unique = true)
    private String contactNumber;
    
    @Column(name = "tutor_request_street_address", nullable = false)
    private String physicalAddress;
    
    @Column(name = "tutor_request_city", nullable = false)
    private String city;
    
    @Column(name = "tutor_request_suburb", nullable = false)
    private String suburb;
    
    @Column(name = "tutor_request_area_code")
    private String areaCode;
    
    //Academic Information
    @OneToOne(targetEntity = AcademicLevel.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "academic_level_id", nullable = false)
    private AcademicLevel gradeLevel;
    
    @OneToMany(targetEntity = TutorRequestSubject.class, fetch = FetchType.EAGER, 
            mappedBy = "tutorRequest")
    private List<TutorRequestSubject> subjects;
    
    @Column(name = "tutor_request_additional_information")
    private String additionalInformation;
    
    // Terms of Service Agreement
    @Column(name = "tutor_request_agreed_to_terms_of_service")
    private boolean agreeToTermsOfService;
    
    public TutorRequest() {
        this.agreeToTermsOfService = true;
        this.subjects = null;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public AcademicLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(AcademicLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public List<TutorRequestSubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TutorRequestSubject> subjects) {
        this.subjects = subjects;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public boolean getAgreeToTermsOfService() {
        return agreeToTermsOfService;
    }

    public void setAgreeToTermsOfService(boolean agreeToTermsOfService) {
        this.agreeToTermsOfService = agreeToTermsOfService;
    }    

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
    
    
}
