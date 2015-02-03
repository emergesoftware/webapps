package za.co.xplain2me.entity;

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
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "student")
public class Student implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "student_id", nullable = false, unique = true)
    private long id;
    
    @OneToOne(targetEntity = Profile.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = false, unique = true)
    private Profile profile;
    
    @Column(name = "student_grade", nullable = false)
    private int grade;
    
    @OneToOne(targetEntity = School.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "school_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private School school;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "student_date_added", nullable = false)
    private Date dateAdded;
    
    @OneToOne(targetEntity = TutorRequest.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_request_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private TutorRequest request;
    
    public Student() {
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public TutorRequest getRequest() {
        return request;
    }

    public void setRequest(TutorRequest request) {
        this.request = request;
    }
    
    
    
}
