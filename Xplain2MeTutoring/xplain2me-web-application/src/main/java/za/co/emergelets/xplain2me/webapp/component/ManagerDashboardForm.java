package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.TutorRequest;

@Component
public class ManagerDashboardForm implements Serializable {
    
    // the new tutor requests
    private List<TutorRequest> newTutorRequests;
    
    public ManagerDashboardForm() {
    }
    
}
