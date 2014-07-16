package za.co.inkunzi.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "contact_detail")
public class ContactDetail implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_detail_id_seq")
    @SequenceGenerator(name = "contact_detail_id_seq", sequenceName = "contact_detail_id_seq",
            allocationSize = 1)
    @Column(name = "contact_detail_id")
    private int contactDetailId;
    
    @Column(name = "home_tel_no")
    private String homeTelephoneNo;
    
    @Column(name = "work_tel_no")
    private String workTelephoneNo;
    
    @Column(name = "fax_no")
    private String faxNumber;
    
    @Column(name = "mobile_no", nullable = false, unique = true)
    private String mobileNumber;
    
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    public ContactDetail() {
    }

    public int getContactDetailId() {
        return contactDetailId;
    }

    public void setContactDetailId(int contactDetailId) {
        this.contactDetailId = contactDetailId;
    }

    public String getHomeTelephoneNo() {
        return homeTelephoneNo;
    }

    public void setHomeTelephoneNo(String homeTelephoneNo) {
        this.homeTelephoneNo = homeTelephoneNo;
    }

    public String getWorkTelephoneNo() {
        return workTelephoneNo;
    }

    public void setWorkTelephoneNo(String workTelephoneNo) {
        this.workTelephoneNo = workTelephoneNo;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    
    
}
