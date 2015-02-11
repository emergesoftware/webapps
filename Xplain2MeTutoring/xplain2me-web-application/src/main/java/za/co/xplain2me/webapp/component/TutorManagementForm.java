package za.co.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.entity.Tutor;

@Component
public class TutorManagementForm implements Serializable {
    
    private TreeMap<Long, Tutor> tutors;
    private TreeMap<Long, Subject> subjectsToChooseFrom;
    private TreeMap<Long, Subject> subjectsAlreadyAssigned;
    
    private int browseStartingFrom;
    private int browseLimitPerPage;
    public static final int LIMIT_RESULTS_PER_PAGE_TO = 20;
    
    public TutorManagementForm() {
        this.resetForm();
    }
    
    public final void resetForm() {
        this.tutors = null;
        this.subjectsAlreadyAssigned = null;
        this.subjectsToChooseFrom = null;
        this.browseStartingFrom = 0;
        this.browseLimitPerPage = LIMIT_RESULTS_PER_PAGE_TO;
    }

    public TreeMap<Long, Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(TreeMap<Long, Tutor> tutors) {
        this.tutors = tutors;
    }

    public TreeMap<Long, Subject> getSubjectsToChooseFrom() {
        return subjectsToChooseFrom;
    }

    public void setSubjectsToChooseFrom(TreeMap<Long, Subject> subjectsToChooseFrom) {
        this.subjectsToChooseFrom = subjectsToChooseFrom;
    }

    public TreeMap<Long, Subject> getSubjectsAlreadyAssigned() {
        return subjectsAlreadyAssigned;
    }

    public void setSubjectsAlreadyAssigned(TreeMap<Long, Subject> subjectsAlreadyAssigned) {
        this.subjectsAlreadyAssigned = subjectsAlreadyAssigned;
    }

    public int getBrowseStartingFrom() {
        return browseStartingFrom;
    }

    public void setBrowseStartingFrom(int browseStartingFrom) {
        this.browseStartingFrom = browseStartingFrom;
    }

    public int getBrowseLimitPerPage() {
        return browseLimitPerPage;
    }

    public void setBrowseLimitPerPage(int browseLimitPerPage) {
        this.browseLimitPerPage = browseLimitPerPage;
    }
    
    
}
