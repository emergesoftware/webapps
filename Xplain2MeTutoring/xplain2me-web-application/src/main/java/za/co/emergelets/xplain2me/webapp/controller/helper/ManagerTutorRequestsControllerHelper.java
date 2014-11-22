package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import za.co.emergelets.xplain2me.dao.SystemAuditManager;
import za.co.emergelets.xplain2me.dao.TutorRequestDAO;
import za.co.emergelets.xplain2me.dao.TutorRequestDAOImpl;
import za.co.emergelets.xplain2me.entity.TutorRequest;
import za.co.emergelets.xplain2me.webapp.component.ManagerTutorRequestsForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.enumerations.TutorRequestsType;

public class ManagerTutorRequestsControllerHelper {
   
    // the logger 
    private static final Logger LOG = 
            Logger.getLogger(ManagerTutorRequestsControllerHelper.class.getName(), null);
    
    // the data access objects
    private TutorRequestDAO tutorRequestDAO;
    
    public ManagerTutorRequestsControllerHelper() {
        initialiseDataAccessObjects();
    }
    
    /**
     * initialization for Data Access Objects
     */
    private void initialiseDataAccessObjects() {
        this.tutorRequestDAO = new TutorRequestDAOImpl();
    }
    
    /**
     * Marks the tutor request as being read
     * 
     * @param request
     * @param id
     * @param form
     * @param context
     * @return 
     */
    public boolean markTutorRequestAsRead(HttpServletRequest request, long id, 
            ManagerTutorRequestsForm form, UserContext context) {
        
        if (id < 1 || form == null || context == null) 
            return false;
        
        // get the unread request
        TutorRequest item = form.getUnreadTutorRequests().get(id);
        
        if (item != null) {
            
            // update as being read
            item.setReceived(true);
            tutorRequestDAO.updateTutorRequest(item);
            
            // create an audit
            SystemAuditManager.logAuditAsync(SystemAuditManager.MARK_TUTOR_REQUEST_AS_READ, 
                    context.getPerson().getUser(), id, null, request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 0, true);
            
            return true;
            
        }
        else {
            return false;
        }
    }
    
    /**
     * Populates the tutor requests by
     * type (read or unread)
     * 
     * @param type
     * @param form 
     */
    public void populateTutorRequestsByType(TutorRequestsType type, 
            ManagerTutorRequestsForm form) {
        
        if (form == null || type == null) {
            LOG.warning("Either form or tutor request type is NULL - " + 
                    "did not populate tutor requests.");
            return;
        }
        
        // configure the data access objects
        
        
        // if the type is unread...
        if (type == TutorRequestsType.Unread) {
            
            // clear all unread tutor requests
            if (form.getUnreadTutorRequests() != null)
                form.getUnreadTutorRequests().clear();
            else
                form.setUnreadTutorRequests(
                        new TreeMap<Long, TutorRequest>());
            
            // yield the unread tutor requests
            for (TutorRequest request : tutorRequestDAO.getUnreadTutorRequests()) {
                form.getUnreadTutorRequests().put(request.getId(), request);
            }
            
        }
        
        // if the type is read...
        
    }
}
