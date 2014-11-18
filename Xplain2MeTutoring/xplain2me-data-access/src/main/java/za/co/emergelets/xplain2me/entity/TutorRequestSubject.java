/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.emergelets.xplain2me.entity;

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
@Table(name = "tutor_request_subjects")
public class TutorRequestSubject implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tutor_request_subjects_id")
    private long id;
    
    @OneToOne(targetEntity = TutorRequest.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_request_id", nullable = false)
    private TutorRequest tutorRequest;
    
    @OneToOne(targetEntity = Subject.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    public TutorRequestSubject() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TutorRequest getTutorRequest() {
        return tutorRequest;
    }

    public void setTutorRequest(TutorRequest tutorRequest) {
        this.tutorRequest = tutorRequest;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
    
    
    
}
