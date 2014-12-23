package za.co.emergelets.xplain2me.model;

import java.io.Serializable;
import java.util.Date;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;

public class TutorJobApproval implements Serializable {
    
    private Date dateOfInterview;
    private Date timeOfInterview;
    private String location;
    private String additionalNotes;
    private BecomeTutorRequest attachedJobApplication;
    private Date dateApprovalSent;
    
    public TutorJobApproval() {
    }

    public Date getDateOfInterview() {
        return dateOfInterview;
    }

    public void setDateOfInterview(Date dateOfInterview) {
        this.dateOfInterview = dateOfInterview;
    }

    public Date getTimeOfInterview() {
        return timeOfInterview;
    }

    public void setTimeOfInterview(Date timeOfInterview) {
        this.timeOfInterview = timeOfInterview;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public BecomeTutorRequest getAttachedJobApplication() {
        return attachedJobApplication;
    }

    public void setAttachedJobApplication(BecomeTutorRequest attachedJobApplication) {
        this.attachedJobApplication = attachedJobApplication;
    }

    public Date getDateApprovalSent() {
        return dateApprovalSent;
    }

    public void setDateApprovalSent(Date dateApprovalSent) {
        this.dateApprovalSent = dateApprovalSent;
    }
    
    
    
}
