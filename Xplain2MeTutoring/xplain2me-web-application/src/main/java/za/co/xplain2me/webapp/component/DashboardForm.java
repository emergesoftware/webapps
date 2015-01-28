package za.co.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import za.co.xplain2me.entity.Audit;

@Component
public class DashboardForm implements Serializable {
    
    // the new tutor requests
    private List<Audit> auditTrail;
    
    public DashboardForm() {
        this.auditTrail = new ArrayList<>();
    }

    public List<Audit> getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(List<Audit> auditTrail) {
        this.auditTrail = auditTrail;
    }
    
}
