package za.co.xplain2me.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tutor_request")
public class TutorRequest implements Serializable {
    
    public static final int SEARCH_BY_REFERENCE_NUMBER = 1;
    public static final int SEARCH_BY_REQUEST_ID = 2;
    public static final int SEARCH_BY_EMAIL_ADDRESS = 3;
    public static final int SEARCH_BY_CONTACT_NUMBER = 4;
    
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
    @Column(name = "tutor_request_email_address", nullable = false)
    private String emailAddress;
    
    @Column(name = "tutor_request_contact_number", nullable = false)
    private String contactNumber;
    
    @Column(name = "tutor_request_street_address", nullable = false)
    private String physicalAddress;
    
    @Column(name = "tutor_request_city", nullable = false)
    private String city;
    
    @Column(name = "tutor_request_suburb", nullable = false)
    private String suburb;
    
    @Column(name = "tutor_request_area_code")
    private String areaCode;
    
    @OneToOne(targetEntity = AcademicLevel.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "academic_level_id", nullable = false)
    private AcademicLevel gradeLevel;
    
    @OneToMany(targetEntity = TutorRequestSubject.class, fetch = FetchType.EAGER, 
            mappedBy = "tutorRequest")
    private List<TutorRequestSubject> subjects;
    
    @Column(name = "tutor_request_additional_information")
    private String additionalInformation;
    
    @Column(name = "tutor_request_agreed_to_terms_of_service")
    private boolean agreeToTermsOfService;
    
    @Column(name = "tutor_request_received", nullable = false)
    private boolean received;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tutor_request_date_received")
    private Date dateReceived;
    
    @Column(name = "tutor_request_request_ref_number", nullable = false)
    private String referenceNumber;
    
    public TutorRequest() {
        this.agreeToTermsOfService = true;
        this.subjects = null;
    }
    
    /**
     * Generates a reference number
     * 
     * @param request
     * @return 
     */
    public static String generateReferenceNumber(TutorRequest request) {
        
        if (request == null || 
                request.getId() == 0)
            return null;
        
        final String REFERENCE_PREFIX = "TR";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date today = new Date();
        
        String id = null;
        
        if (String.valueOf(request.getId()).length() < 3) {
            if (String.valueOf(request.getId()).length() == 1) 
                id = "00" + request.getId();
            else if (String.valueOf(request.getId()).length() == 2)
                id = "0" + request.getId();
        }
        
        else {
            id = String.valueOf(request.getId());
        }
        
        return REFERENCE_PREFIX + format.format(today) 
                + id;
        
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

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @Override
    public String toString() {
        return "TutorRequest{" + "id=" + id + ", firstNames=" + firstNames + ", lastName=" + lastName + ", gender=" + gender + ", emailAddress=" + emailAddress + ", contactNumber=" + contactNumber + ", physicalAddress=" + physicalAddress + ", city=" + city + ", suburb=" + suburb + ", areaCode=" + areaCode + ", gradeLevel=" + gradeLevel + ", subjects=" + subjects + ", additionalInformation=" + additionalInformation + ", agreeToTermsOfService=" + agreeToTermsOfService + ", received=" + received + ", dateReceived=" + dateReceived + ", referenceNumber=" + referenceNumber + '}';
    }
    
    
}
