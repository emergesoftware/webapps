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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "profile")
public class Profile implements Serializable {
    
    /*
    
    Table: profile
    
    Columns:
        profile_id int not null unique default nextval('profile_id_sequence'),
        profile_type_id int not null,
        profile_created timestamp without time zone not null default now(),
        user_name varchar(32) not null,
        profile_verified boolean not null default false,
        profile_verification_code text
    
    */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private long id;
    
    @OneToOne(targetEntity = ProfileType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_type_id", nullable = false)
    private ProfileType profileType;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "profile_created", nullable = false)
    private Date dateCreated;
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_name")
    private User user;
    
    @Column(name = "profile_verified", nullable = false)
    private boolean verified;
    
    @Column(name = "profile_verification_code")
    private String verificationCode;
    
    public Profile() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
     
}
