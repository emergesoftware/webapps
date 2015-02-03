package za.co.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "tutor")
public class Tutor implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tutor_id", nullable = false, unique = true)
    private long id;
    
    @OneToOne(targetEntity = Profile.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;
    
    @OneToOne(targetEntity = BecomeTutorRequest.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "become_tutor_request_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private BecomeTutorRequest request;
    
    public Tutor() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public BecomeTutorRequest getRequest() {
        return request;
    }

    public void setRequest(BecomeTutorRequest request) {
        this.request = request;
    }
    
}
