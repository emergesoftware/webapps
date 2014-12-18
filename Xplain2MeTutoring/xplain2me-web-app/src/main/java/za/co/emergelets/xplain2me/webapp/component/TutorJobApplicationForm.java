package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;

@Component
public class TutorJobApplicationForm implements Serializable {
    
    private TreeMap<Long, BecomeTutorRequest> tutorJobApplications;
    private int currentPageNumber;
    
    public TutorJobApplicationForm() {
        resetForm();
    }
    
    public final void resetForm() {
        this.tutorJobApplications = null;
        this.currentPageNumber = 1;
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
    
    
    
}
