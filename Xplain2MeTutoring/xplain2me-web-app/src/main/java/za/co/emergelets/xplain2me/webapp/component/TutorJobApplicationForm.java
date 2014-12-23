package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.Audit;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;

@Component
public class TutorJobApplicationForm implements Serializable {
    
    private TreeMap<Long, BecomeTutorRequest> tutorJobApplications;
    private int currentPageNumber;
    private boolean cannotGoForward;
    
    private BecomeTutorRequest tutorJobApplication;
    private List<BecomeTutorSupportingDocument> supportingDocuments;
    
    private List<Audit> auditTrail;
    
    public TutorJobApplicationForm() {
        resetForm();
    }
    
    public final void resetForm() {
        this.tutorJobApplications = null;
        this.currentPageNumber = 1;
        this.cannotGoForward = false;
        
        this.tutorJobApplication = null;
        this.supportingDocuments = null;
        
        this.auditTrail = null;
    }

    public TreeMap<Long, BecomeTutorRequest> getTutorJobApplications() {
        if (tutorJobApplications == null)
            tutorJobApplications = new TreeMap<>();
        
        return tutorJobApplications;
    }

    public void setTutorJobApplications(TreeMap<Long, BecomeTutorRequest> tutorJobApplications) {
        this.tutorJobApplications = tutorJobApplications;
    }

    public int getCurrentPageNumber() {
        if (currentPageNumber < 1)
            currentPageNumber = 1;
        
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public boolean isCannotGoForward() {
        return cannotGoForward;
    }

    public void setCannotGoForward(boolean cannotGoForward) {
        this.cannotGoForward = cannotGoForward;
    }

    public BecomeTutorRequest getTutorJobApplication() {
        return tutorJobApplication;
    }

    public void setTutorJobApplication(BecomeTutorRequest tutorJobApplication) {
        this.tutorJobApplication = tutorJobApplication;
    }

    public List<BecomeTutorSupportingDocument> getSupportingDocuments() {
        return supportingDocuments;
    }

    public void setSupportingDocuments(List<BecomeTutorSupportingDocument> supportingDocuments) {
        this.supportingDocuments = supportingDocuments;
    }

    public List<Audit> getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(List<Audit> auditTrail) {
        this.auditTrail = auditTrail;
    }
    
}
