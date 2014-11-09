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
@Table(name = "person")
public class Person implements Serializable {
    
    /*
    Table: person
    
    Columns:    
        person_id int not null unique default nextval('person_id_sequence'),
        person_last_name varchar(128) not null,
        person_first_names varchar(256) not null,
        person_date_of_birth timestamp without time zone not null,
        person_id_number varchar(32) not null unique,
        gender_id varchar(1) not null,
        citizenship_id int not null,
        contact_detail_id int not null,
        physical_address_id int not null,
        user_name varchar(32) not null
    */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private long id;
    
    @Column(name = "person_last_name", nullable = false)
    private String lastName;
    
    @Column(name = "person_first_names", nullable = false)
    private String firstNames;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "person_date_of_birth", nullable = false)
    private Date dateOfBirth;
    
    @Column(name = "person_id_number", nullable = false, unique = true)
    private String identityNumber;
    
    @OneToOne(targetEntity = Gender.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "gender_id", nullable = false)
    private Gender gender;
    
    @OneToOne(targetEntity = Citizenship.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "citizenship_id", nullable = false)
    private Citizenship citizenship;
    
    @OneToOne(targetEntity = ContactDetail.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_detail_id", nullable = false)
    private ContactDetail contactDetail;
    
    @OneToOne(targetEntity = PhysicalAddress.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "physical_address_id", nullable = false)
    private PhysicalAddress physicalAddress;
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name", nullable = false)
    private User user;
    
    public Person() {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Citizenship getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Citizenship citizenship) {
        this.citizenship = citizenship;
    }

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

    public PhysicalAddress getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(PhysicalAddress physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    
}
