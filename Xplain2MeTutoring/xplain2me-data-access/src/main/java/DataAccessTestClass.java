
import java.util.ArrayList;
import java.util.List;
import za.co.emergelets.xplain2me.dao.AuditDAO;
import za.co.emergelets.xplain2me.dao.AuditDAOImpl;
import za.co.emergelets.xplain2me.dao.EventTypes;
import za.co.emergelets.xplain2me.dao.TutorRequestDAO;
import za.co.emergelets.xplain2me.dao.TutorRequestDAOImpl;
import za.co.emergelets.xplain2me.entity.Audit;
import za.co.emergelets.xplain2me.entity.TutorRequest;

public class DataAccessTestClass {
    
    public static void main(String... args) throws Exception {
        
        long startTime = System.currentTimeMillis();
        
        List<Audit> auditTrail = null;
        String username = "washington";
        int limit = 10;
        
        List<Long> eventTypes = new ArrayList<>();
        eventTypes.add(EventTypes.MARK_TUTOR_REQUEST_AS_READ_EVENT);
        eventTypes.add(EventTypes.DELETE_TUTOR_REQUEST_EVENT);
        
        AuditDAO dao = new AuditDAOImpl();
        auditTrail = dao.getLatestAuditTrailByUser(username, eventTypes, limit);
        
        if (auditTrail != null) {
            for (Audit audit : auditTrail)
                System.out.println(" --> " + audit.toString());
        }
        
        System.out.println("-------------------------------------\n\n");
        
        auditTrail = dao.getLatestAuditTrailByUserLimited(username, limit);
        
        if (auditTrail != null) {
            for (Audit audit : auditTrail)
                System.out.println(" --> " + audit.toString());
        }
        
        System.out.println("----------------------------------------------\n\n");
        
        TutorRequestDAO dao2 = new TutorRequestDAOImpl();
        List<TutorRequest> tutorRequests = dao2.getUnreadTutorRequests();
        
        if (tutorRequests != null) {
            
            TutorRequest tutorRequest = null;
            
            for (TutorRequest request : tutorRequests) {
                tutorRequest = request;
                System.out.println(" --> " + request.toString());
            }
            
            if (tutorRequest != null) {
                
                System.out.println(" ... update tutor request: " + tutorRequest.getId());
                tutorRequest.setReceived(false);
                dao2.updateTutorRequest(tutorRequest);
                
                System.out.println(" ... deleting tutor request: " + tutorRequest.getId());
                dao2.deleteTutorRequest(tutorRequest);
                
                
            }
        }
        
        long endTime = System.currentTimeMillis();
        
        System.out.println(" --- total seconds taken: " + (endTime - startTime)/1000);
        
    }
    
   
    
}
