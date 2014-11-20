package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.TutorRequest;

@Component
public class ManagerTutorRequestsForm implements Serializable {
    
    // map for all unread tutor requests
    private Map<Long, TutorRequest> unreadTutorRequests;
    
    public ManagerTutorRequestsForm() {
        this.unreadTutorRequests = new TreeMap<>();
    }

    public Map<Long, TutorRequest> getUnreadTutorRequests() {
        return unreadTutorRequests;
    }

    public void setUnreadTutorRequests(Map<Long, TutorRequest> unreadTutorRequests) {
        this.unreadTutorRequests = unreadTutorRequests;
    }
    
    
    
}
