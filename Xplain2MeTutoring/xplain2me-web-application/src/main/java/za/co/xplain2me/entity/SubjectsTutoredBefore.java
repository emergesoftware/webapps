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

@Entity
@Table(name = "subjects_tutored_before")
public class SubjectsTutoredBefore implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "subjects_tutored_before_id", nullable = false,
            unique = true)
    private long id;
    
    @OneToOne(targetEntity = Subject.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    @OneToOne(targetEntity = BecomeTutorRequest.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "become_tutor_request_id", nullable = false)
    private BecomeTutorRequest request;
    
    public SubjectsTutoredBefore() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BecomeTutorRequest getRequest() {
        return request;
    }

    public void setRequest(BecomeTutorRequest request) {
        this.request = request;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    
    
}
