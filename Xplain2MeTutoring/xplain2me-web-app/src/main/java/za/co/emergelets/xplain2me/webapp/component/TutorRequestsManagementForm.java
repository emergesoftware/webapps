package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.Audit;
import za.co.emergelets.xplain2me.entity.TutorRequest;

@Component
public class TutorRequestsManagementForm implements Serializable {
    
    // map for all unread tutor requests
    private Map<Long, TutorRequest> unreadTutorRequests;
    // list of latest audits
    private List<Audit> auditTrail;
    
    // search data
    private String searchKeyword;
    private int searchType;
    private Map<Long, TutorRequest> searchResults;
    private int showResultsAnswer;
    
    public TutorRequestsManagementForm() {
        resetForm();
    }
    
    public final void resetForm() {
        this.auditTrail = null;
        this.unreadTutorRequests = null;
        
        this.searchKeyword = null;
        this.searchType = 0;
        this.searchResults = null;
        this.showResultsAnswer = 0;
    }

    public Map<Long, TutorRequest> getUnreadTutorRequests() {
        if (unreadTutorRequests == null)
            unreadTutorRequests = new TreeMap<>();
            
        return unreadTutorRequests;
    }

    public void setUnreadTutorRequests(Map<Long, TutorRequest> unreadTutorRequests) {
        this.unreadTutorRequests = unreadTutorRequests;
    }

    public List<Audit> getAuditTrail() {
        if (auditTrail == null)
            auditTrail = new ArrayList<>();
            
        return auditTrail;
    }

    public void setAuditTrail(List<Audit> auditTrail) {
        this.auditTrail = auditTrail;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public int getSearchType() {
        return searchType;
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public Map<Long, TutorRequest> getSearchResults() {
        if (searchResults == null)
            searchResults = new TreeMap<>();
            
        return searchResults;
    }

    public void setSearchResults(Map<Long, TutorRequest> searchResults) {
        this.searchResults = searchResults;
    }

    public int getShowResultsAnswer() {
        return showResultsAnswer;
    }

    public void setShowResultsAnswer(int showResultsAnswer) {
        this.showResultsAnswer = showResultsAnswer;
    }
    
}
