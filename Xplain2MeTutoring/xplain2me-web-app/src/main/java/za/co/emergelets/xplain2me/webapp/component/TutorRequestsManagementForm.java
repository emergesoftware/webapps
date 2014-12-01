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
    
    public TutorRequestsManagementForm() {
        this.unreadTutorRequests = new TreeMap<>();
        this.auditTrail = new ArrayList<>();
    }

    public Map<Long, TutorRequest> getUnreadTutorRequests() {
        return unreadTutorRequests;
    }

    public void setUnreadTutorRequests(Map<Long, TutorRequest> unreadTutorRequests) {
        this.unreadTutorRequests = unreadTutorRequests;
    }

    public List<Audit> getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(List<Audit> auditTrail) {
        this.auditTrail = auditTrail;
    }
    
}
