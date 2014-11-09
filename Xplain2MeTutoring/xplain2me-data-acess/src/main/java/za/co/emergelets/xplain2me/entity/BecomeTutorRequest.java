package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "become_tutor_request")
public class BecomeTutorRequest implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "become_tutor_request_id")
    private long id;
    
    @Column(name = "become_tutor_request_last_name", nullable = false)
    private String lastName;
    
    @Column(name = "become_tutor_request_first_names", nullable = false)
    private String firstNames;
    
    @Column(name = "become_tutor_request_contact_number", nullable = false, 
            unique = true)
    private String contactNumber;
    
    @Column(name = "become_tutor_request_email_address", nullable = false, 
            unique = true)
    private String emailAddress;
    
    @Column(name = "become_tutor_request_street_address", nullable = false)
    private String streetAddress;
    
    @Column(name = "become_tutor_request_suburb", nullable = false)
    private String suburb;
    
    @Column(name = "become_tutor_request_city", nullable = false)
    private String city;
    
    @Column(name = "become_tutor_request_area_code", nullable = false)
    private String areaCode;
    
    @Column(name = "become_tutor_request_id_number", nullable = false, 
            unique = true)
    private String identityNumber;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "become_tutor_request_date_of_birth", nullable = false)
    private Date dateOfBirth;
    
    @OneToOne(targetEntity = Citizenship.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "citizenship_id", nullable = false)
    private Citizenship citizenship;
    
    @OneToOne(targetEntity = Gender.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;
    
    @Column(name = "agreed_to_terms_of_service", nullable = false)
    private boolean agreedToTermsOfService;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "become_tutor_request_submitted", nullable = false)
    private Date dateSubmitted;
    
    public BecomeTutorRequest() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstNames() {
        return firstNames;
    }

    public void setFirstNames(String firstNames) {
        this.firstNames = firstNames;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Citizenship getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Citizenship citizenship) {
        this.citizenship = citizenship;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isAgreedToTermsOfService() {
        return agreedToTermsOfService;
    }

    public void setAgreedToTermsOfService(boolean agreedToTermsOfService) {
        this.agreedToTermsOfService = agreedToTermsOfService;
    }

    public Date getDateSubmitted() {
        return dateSubmitted;
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
    
    
}
