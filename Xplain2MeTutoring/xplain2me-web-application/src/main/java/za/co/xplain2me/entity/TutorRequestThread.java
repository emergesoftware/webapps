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

@Entity
@Table(name = "tutor_request_thread")
public class TutorRequestThread implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tutor_request_thread_id", nullable = false, unique = true)
    private long id;
    
    @OneToOne(targetEntity = TutorRequest.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_request_id", nullable = false)
    private TutorRequest request;
    
    @Column(name = "tutor_request_thread_message", nullable = false)
    private String message;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tutor_request_thread_timestamp", nullable = false)
    private Date timestamp;
    
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "tutor_request_thread_is_edited", nullable = false)
    private boolean edited;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tutor_request_thread_date_edited")
    private Date dateEdited;
    
    @Column(name = "tutor_request_thread_is_deleted", nullable = false)
    private boolean deleted;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JoinColumn(name = "tutor_request_thread_date_deleted")
    private Date dateDeleted;
    
    public TutorRequestThread() {
        this.edited = false;
        this.deleted = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TutorRequest getRequest() {
        return request;
    }

    public void setRequest(TutorRequest request) {
        this.request = request;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isEdited() {
        return edited;
    }

    public void setEdited(boolean edited) {
        this.edited = edited;
    }

    public Date getDateEdited() {
        return dateEdited;
    }

    public void setDateEdited(Date dateEdited) {
        this.dateEdited = dateEdited;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }
    
}
