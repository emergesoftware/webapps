package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.dao.AuditDAO;
import za.co.emergelets.xplain2me.dao.AuditDAOImpl;
import za.co.emergelets.xplain2me.entity.Audit;
import za.co.emergelets.xplain2me.webapp.component.DashboardForm;

@Component
public class DashboardControllerHelper implements Serializable {
    
    private final AuditDAO auditDAO;
    
    public DashboardControllerHelper() {
        this.auditDAO = new AuditDAOImpl();
    }
    
    public void populateRecentAuditTrailByUser(DashboardForm form, 
            String username, int limit) {
        if (username == null || limit < 1) {
            return;
        }
        
        // get the audit trail from the data
        // store
        List<Audit> auditTrail = auditDAO
                .getLatestAuditTrailByUserLimited(username, limit);
                
        // set only if not null - the audit trail
        if (auditTrail != null)
            form.setAuditTrail(auditTrail);
        
        else {
            if (form.getAuditTrail() == null)
                form.setAuditTrail(new ArrayList<Audit>());
        }
    }
    
}
