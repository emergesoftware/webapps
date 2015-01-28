package za.co.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "lesson")
public class Lesson implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id", nullable = false, unique = true)
    private long id;
    
    @OneToOne(targetEntity = Profile.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_profile_id", nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private Profile tutorProfile;
    
    @OneToOne(targetEntity = Profile.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "student_profile_id", nullable = false)
    private Profile studentProfile;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lesson_scheduled_date_and_time", nullable = false)
    private Date scheduledDateAndTime;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lesson_actual_date_and_time")
    private Date actualDateAndTime;
    
    @Column(name = "lesson_scheduled_length_of_lesson", nullable = false)
    private double scheduledLengthOfLesson;
    
    @Column(name = "lesson_actual_length_of_lesson", nullable = false)
    private double actualLengthOfLesson;
    
    @OneToOne(targetEntity = Subject.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    
    @OneToOne(targetEntity = AcademicLevel.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "academic_level_id", nullable = false)
    private AcademicLevel academicLevel;
    
    @OneToOne(targetEntity = LessonStatus.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "lesson_status_id", nullable = false)
    private LessonStatus lessonStatus;
    
    @Column(name = "lesson_reasons_for_postponement_or_cancellation")
    private String reasonsForPostponementOrCancellation;
    
    @Column(name = "lesson_content_covered")
    private String contentCoveredDuringLesson;
    
    @Column(name = "lesson_learner_performance_and_problem_areas")
    private String learnerPerformanceAndProblemAreas;
    
    @Column(name = "lesson_actions_taken_to_solve_problem_areas")
    private String actionsTakenOnSolvingProblemAreas;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lesson_last_updated")
    private Date lastUpdated;
    
    @Column(name = "lesson_claimed", nullable = false)
    private boolean claimed;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lesson_date_claimed")
    private Date dateClaimed;
    
    @OneToOne(targetEntity = TutorClaimDocument.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_claim_document_id", nullable = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private TutorClaimDocument tutorClaimDocument;
    
    public Lesson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Profile getTutorProfile() {
        return tutorProfile;
    }

    public void setTutorProfile(Profile tutorProfile) throws IllegalAccessException {
        
        if (tutorProfile != null && 
                tutorProfile.getProfileType().getId() != ProfileType.TUTOR_PROFILE)
            throw new IllegalAccessException("A tutor profile is required to be assigned - "
                    + "no other profile is allowed.");
            
        this.tutorProfile = tutorProfile;
    }

    public Profile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(Profile studentProfile) throws IllegalAccessException {
        
        if (studentProfile != null && 
                studentProfile.getProfileType().getId() != ProfileType.STUDENT_PROFILE)
            throw new IllegalAccessException("A student profile is required to be assigned - "
                    + "no other profile is allowed.");
        
        this.studentProfile = studentProfile;
    }

    public Date getScheduledDateAndTime() {
        return scheduledDateAndTime;
    }

    public void setScheduledDateAndTime(Date scheduledDateAndTime) {
        this.scheduledDateAndTime = scheduledDateAndTime;
    }

    public Date getActualDateAndTime() {
        return actualDateAndTime;
    }

    public void setActualDateAndTime(Date actualDateAndTime) {
        this.actualDateAndTime = actualDateAndTime;
    }

    public double getScheduledLengthOfLesson() {
        return scheduledLengthOfLesson;
    }

    public void setScheduledLengthOfLesson(double scheduledLengthOfLesson) {
        this.scheduledLengthOfLesson = scheduledLengthOfLesson;
    }

    public double getActualLengthOfLesson() {
        return actualLengthOfLesson;
    }

    public void setActualLengthOfLesson(double actualLengthOfLesson) {
        this.actualLengthOfLesson = actualLengthOfLesson;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public LessonStatus getLessonStatus() {
        return lessonStatus;
    }

    public void setLessonStatus(LessonStatus lessonStatus) {
        this.lessonStatus = lessonStatus;
    }

    public String getReasonsForPostponementOrCancellation() {
        return reasonsForPostponementOrCancellation;
    }

    public void setReasonsForPostponementOrCancellation(String reasonsForPostponementOrCancellation) {
        this.reasonsForPostponementOrCancellation = reasonsForPostponementOrCancellation;
    }

    public String getContentCoveredDuringLesson() {
        return contentCoveredDuringLesson;
    }

    public void setContentCoveredDuringLesson(String contentCoveredDuringLesson) {
        this.contentCoveredDuringLesson = contentCoveredDuringLesson;
    }

    public String getLearnerPerformanceAndProblemAreas() {
        return learnerPerformanceAndProblemAreas;
    }

    public void setLearnerPerformanceAndProblemAreas(String learnerPerformanceAndProblemAreas) {
        this.learnerPerformanceAndProblemAreas = learnerPerformanceAndProblemAreas;
    }

    public String getActionsTakenOnSolvingProblemAreas() {
        return actionsTakenOnSolvingProblemAreas;
    }

    public void setActionsTakenOnSolvingProblemAreas(String actionsTakenOnSolvingProblemAreas) {
        this.actionsTakenOnSolvingProblemAreas = actionsTakenOnSolvingProblemAreas;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public AcademicLevel getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(AcademicLevel academicLevel) {
        this.academicLevel = academicLevel;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public Date getDateClaimed() {
        return dateClaimed;
    }

    public void setDateClaimed(Date dateClaimed) {
        this.dateClaimed = dateClaimed;
    }

    public TutorClaimDocument getTutorClaimDocument() {
        return tutorClaimDocument;
    }

    public void setTutorClaimDocument(TutorClaimDocument tutorClaimDocument) {
        this.tutorClaimDocument = tutorClaimDocument;
    }
    
    

}
