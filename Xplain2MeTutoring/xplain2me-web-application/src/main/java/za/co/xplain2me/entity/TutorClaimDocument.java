package za.co.xplain2me.entity;

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tutor_claim_document")
public class TutorClaimDocument implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tutor_claim_document_id", nullable = false, unique = true)
    private long id;
    
    @OneToOne(targetEntity = Tutor.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "tutor_id", nullable = false)
    private Tutor tutor;
    
    @Column(name = "tutor_claim_document_amount", nullable = false)
    private double claimAmount;
    
    @Column(name = "tutor_claim_document_disputed", nullable = false)
    private boolean disputed;
    
    @Column(name = "tutor_claim_document_dispute_message")
    private String disputeMessage;
    
    @Column(name = "tutor_claim_document_approved", nullable = false)
    private boolean approved;
    
    @Column(name = "tutor_claim_document_paid_out", nullable = false)
    private boolean paidOut;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tutor_claim_document_date_completed", nullable = false)
    private Date dateCompleted;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tutor_claim_document_date_paid_out")
    private Date datePaidOut;
    
    @Column(name = "tutor_claim_document_cancelled", nullable = false)
    private boolean cancelled;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "tutor_claim_document_date_cancelled")
    private Date dateCancelled;
    
    @Column(name = "tutor_claim_document_cancellation_reason")
    private String reasonForCancellation;
    
    @Column(name = "tutor_claim_document_no_of_lessons", nullable = false)
    private int numberOfLessons;
    
    public TutorClaimDocument() {
        this.claimAmount = 0.0d;
        this.disputed = false;
        this.approved = false;
        this.paidOut = false;
        this.numberOfLessons = 0;
        this.cancelled = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public boolean isDisputed() {
        return disputed;
    }

    public void setDisputed(boolean disputed) {
        this.disputed = disputed;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isPaidOut() {
        return paidOut;
    }

    public void setPaidOut(boolean paidOut) {
        this.paidOut = paidOut;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public Date getDatePaidOut() {
        return datePaidOut;
    }

    public void setDatePaidOut(Date datePaidOut) {
        this.datePaidOut = datePaidOut;
    }

    public int getNumberOfLessons() {
        return numberOfLessons;
    }

    public void setNumberOfLessons(int numberOfLessons) {
        this.numberOfLessons = numberOfLessons;
    }

    public String getDisputeMessage() {
        return disputeMessage;
    }

    public void setDisputeMessage(String disputeMessage) {
        this.disputeMessage = disputeMessage;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Date getDateCancelled() {
        return dateCancelled;
    }

    public void setDateCancelled(Date dateCancelled) {
        this.dateCancelled = dateCancelled;
    }

    public String getReasonForCancellation() {
        return reasonForCancellation;
    }

    public void setReasonForCancellation(String reasonForCancellation) {
        this.reasonForCancellation = reasonForCancellation;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
